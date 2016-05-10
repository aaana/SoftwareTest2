package tools;

import client.ClientInfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by tina on 16/4/30.
 */
public class SqliteTool {

    // 移动公司系统
    public void init() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:mobileDB.db");

            stmt = c.createStatement();
            String sql = "DELETE FROM CLIENTINFO";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO CLIENTINFO " +
                    "VALUES ( '100', 0, 0, 0 );";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO CLIENTINFO " +
                    "VALUES ( '101', 1, 1, 1 );";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO CLIENTINFO " +
                    "VALUES ( '102', 2, 2, 2 );";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO CLIENTINFO " +
                    "VALUES ( '103', 3, 3, 3 );";
            stmt.executeUpdate(sql);

            sql = "DELETE FROM CLIENTCOMMUNICATIONTIME";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO CLIENTCOMMUNICATIONTIME " +
                    "VALUES ( '100', 0 );";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO CLIENTCOMMUNICATIONTIME " +
                    "VALUES ( '101', 10 );";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO CLIENTCOMMUNICATIONTIME " +
                    "VALUES ( '102', 20 );";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO CLIENTCOMMUNICATIONTIME " +
                    "VALUES ( '103', 30 );";
            stmt.executeUpdate(sql);

            stmt.close();
            c.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:bankDB.db");

            stmt = c.createStatement();
            String sql = "DELETE FROM ACCOUNT";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO ACCOUNT " +
                    "VALUES ( 100, '111', 10  );";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO ACCOUNT " +
                    "VALUES ( 101, '111', 50 );";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO ACCOUNT " +
                    "VALUES ( 102, '111', 300 );";
            stmt.executeUpdate(sql);

            stmt.close();
            c.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public List<ClientInfo> getAllClientInfo() {

        Connection c = null;
        Statement stmt = null;
        List<ClientInfo> clientInfoList = new LinkedList<ClientInfo>();
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:mobileDB.db");

            stmt = c.createStatement();
            String sql = "SELECT * FROM CLIENTINFO;";
            ResultSet rs = stmt.executeQuery( sql );

            while ( rs.next() ) {
                ClientInfo client = new ClientInfo( Integer.parseInt( rs.getString( "phoneNumber") ), rs.getDouble( "owedBeforeYear"), rs.getDouble( "owedThisYear"), rs.getDouble( "TimeOutCount") );
                clientInfoList.add( client );
            }

            rs.close();
            stmt.close();
            c.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return clientInfoList;
    }

    public ClientInfo getClientInfo( String phoneNumber ) {

        Connection c = null;
        Statement stmt = null;
        ClientInfo client = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:mobileDB.db");

            stmt = c.createStatement();
            String sql = "SELECT * FROM CLIENTINFO Where phoneNumber = '" + phoneNumber + "';";
            ResultSet rs = stmt.executeQuery( sql );

            if ( rs.next() ) {
                client = new ClientInfo( Integer.parseInt( rs.getString( "phoneNumber") ), rs.getDouble( "owedBeforeYear"), rs.getDouble( "owedThisYear"), rs.getDouble( "TimeOutCount") );
            }

            rs.close();
            stmt.close();
            c.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return client;
    }

    public double getCommunicationTime( String id ) {

        Connection c = null;
        Statement stmt = null;
        double communicationTime = -1;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:mobileDB.db");

            stmt = c.createStatement();
            String sql = "SELECT communicationTime FROM CLIENTCOMMUNICATIONTIME WHERE phoneNumber = '" + id + "';";
            ResultSet rs = stmt.executeQuery( sql );

            communicationTime = rs.getDouble( 1 );

            rs.close();
            stmt.close();
            c.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return communicationTime;
    }

    public void clearCommunicationTime() {

        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:mobileDB.db");

            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "UPDATE CLIENTCOMMUNICATIONTIME set communicationTime = 0 ;";
            stmt.executeUpdate(sql);
            c.commit();

            stmt.close();
            c.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public void payDone(String accountId ) {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:mobileDB.db");

            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "UPDATE CLIENTINFO set owedBeforeYear = 0, owedThisYear = 0 where phoneNumber = " + accountId + ";";
            stmt.executeUpdate(sql);
            c.commit();

            stmt.close();
            c.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public void updateEveryMonth() {
        Connection c = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<ClientInfo> clientInfoList = new LinkedList<ClientInfo>();

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:mobileDB.db");

            stmt = c.createStatement();
            String sql = "SELECT * FROM CLIENTINFO;";
            rs = stmt.executeQuery( sql );

            while ( rs.next() ) {
                ClientInfo client = new ClientInfo( Integer.parseInt( rs.getString( "phoneNumber") ), rs.getDouble( "owedBeforeYear"), rs.getDouble( "owedThisYear"), rs.getDouble( "TimeOutCount") );
                clientInfoList.add( client );
            }

            stmt.close();
            c.close();

            for ( ClientInfo client :
                    clientInfoList ) {
                Double communicationTime = getCommunicationTime( String.valueOf( client.getClientId() ) );

                client.updateInfoEveryMonth( communicationTime );

                updateClientInfo( String.valueOf( client.getClientId() ), client.getOwedBeforeYear(), client.getOwedThisYear(), client.getTimeOutCount() );
            }
            clearCommunicationTime();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

    }

    public void updateClientInfo( String phoneNumber, double oBY, double oTY, double tOC) {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:mobileDB.db");

            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "UPDATE CLIENTINFO set owedBeforeYear = " + oBY + " , owedThisYear = " + oTY + " , timeOutCount = " + tOC + " where phoneNumber = '" + phoneNumber + "';";
            stmt.executeUpdate(sql);
            c.commit();

            stmt.close();
            c.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

    }


    // 银行付费系统
    public double verification( String aId, String aPw, double expectedCharge ) {

        Connection c = null;
        Statement stmt = null;
        int id = Integer.parseInt( aId );
        double balance = -1;


        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:bankDB.db");

            stmt = c.createStatement();
            String sql = "SELECT accountId, accountPw, accountBalance FROM ACCOUNT WHERE accountId = " + id + ";";
            ResultSet rs = stmt.executeQuery( sql );

            if ( rs.next() ) {
                if ( rs.getString( "accountPw" ).equals( aPw ) ) {
                    balance = rs.getDouble( "accountBalance" );
                    if( balance >= expectedCharge ) {
                        balance = balance - expectedCharge;
                    } else {
                        balance = -2;
                    }
                }
            }

            rs.close();
            stmt.close();
            c.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return balance;
    }

    public void updateBalance( String aId, String aPw, double balance ) {

        Connection c = null;
        Statement stmt = null;
        int id = Integer.parseInt( aId );

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:bankDB.db");

            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "UPDATE ACCOUNT set accountBalance = " + balance + " where accountId = " + aId + " AND accountPw = '" + aPw + "';";
            stmt.executeUpdate(sql);
            c.commit();

            stmt.close();
            c.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

}
