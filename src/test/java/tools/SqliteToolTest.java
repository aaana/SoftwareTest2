package tools;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by tina on 16/4/30.
 */
public class SqliteToolTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void init() throws Exception {
        SqliteTool sTool = new SqliteTool();

        sTool.init();
    }

    @Test
    public void getCommunicationTime() throws Exception {

        SqliteTool sTool = new SqliteTool();

        sTool.init();
        sTool.getCommunicationTime( "101" );

    }

    @Test
    public void payDown() throws Exception {

        SqliteTool sTool = new SqliteTool();

        sTool.init();
        sTool.payDone( "101" );
    }

    @Test
    public void updateEveryMonth() throws Exception {

        SqliteTool sTool = new SqliteTool();

        sTool.init();
        sTool.updateEveryMonth();
    }

    @Test
    public void clearCommunicationTime() throws Exception {

        SqliteTool sTool = new SqliteTool();

        sTool.init();
        sTool.clearCommunicationTime();
    }

    @Test
    public void verification() throws Exception {

        SqliteTool sTool = new SqliteTool();

        sTool.init();
        assertEquals( -1, sTool.verification( "100", "110", 10 ), 0.001 );
        assertEquals( -2, sTool.verification( "100", "111", 20 ), 0.001 );
        assertEquals( 5, sTool.verification( "100", "111", 5 ), 0.001 );
    }

    @Test
    public void updateBalance() throws Exception {

        SqliteTool sTool = new SqliteTool();

        sTool.init();
        sTool.updateBalance( "100", "111", 5 );
    }
}