package controller;

import client.ClientInfo;
import tools.SqliteTool;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.AbstractCollection;
import java.util.Date;

/**
 * Created by tina on 16/5/2.
 */
public class Controller {

    private ClientInfo client = null;
    private SqliteTool sTool = new SqliteTool();

    String accountId;
    double expectedCharge;
    double paySuccess;

    public Controller(String phoneNumber ) {
        client = sTool.getClientInfo( phoneNumber );
    }

    public double getExpectedCharge() {
        return client.getOwedBeforeYear() + client.getOwedThisYear();
    }

    public double verification( String aId, String aPw, double expectedCharge ) {
        this.accountId = aId;
        this.expectedCharge = expectedCharge;
        this.paySuccess = sTool.verification( aId, aPw, expectedCharge );

        if ( this.paySuccess > 0 ) {
            sTool.updateBalance( aId, aPw, this.paySuccess );
            sTool.payDone( String.valueOf( client.getClientId() ) );
        }
        return paySuccess;
    }


    public static void updateEveryMonth() {
        SqliteTool sTool = new SqliteTool();
        sTool.updateEveryMonth();
    }

    public boolean print() throws IOException {
        Date dt = new Date();
        String time = new SimpleDateFormat( "yyyyMMddHHmmss" ).format( dt );
        String fileName = "printFiles/" +  client.getClientId() + "_" + time + ".txt";
        File file = new File( fileName );
        FileWriter pw;

        if( file.exists() || file.createNewFile() ) {
            pw = new FileWriter( file, true );
            pw.write( "手机用户: " + client.getClientId() + "\n" );
            pw.write( "支付用户: " + this.accountId + "\n" );
            pw.write( "支付金额: " + this.expectedCharge + "\n" );
            pw.write( "余额: " + this.paySuccess + "\n" );
            pw.close();
            return true;
        } else {
            return false;
        }
    }

}
