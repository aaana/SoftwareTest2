package client;

import org.junit.Before;
import org.junit.Test;
import tools.ExcelTool;

import java.io.IOException;
import java.util.*;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

/**
 * Created by tina on 16/4/28.
 */
public class ClientInfoTest {

    @Test
    public void equivalenceClassTest() throws Exception {
        ExcelTool eTool = new ExcelTool( "testFiles/equivalenceClassTest.xlsx" );

        Map<Integer, Integer> type1 = new HashMap<Integer, Integer>() {
            {
                put( 0, 0 );
                put( 1, 0 );
                put( 2, 0 );
                put( 3, 0 );
            }
        };
        Map<Integer, Integer> type2 = new HashMap<Integer, Integer>() {
            {
                put( 5, 0 );
                put( 6, 0 );
            }
        };

        List<Map<Integer, List>> result = eTool.readFromExcel( type1, type2, 2 );

        Map<Integer, List> input = result.get( 0 );
        List<Double> obyList = input.get( 0 );
        List<Double> otyList = input.get( 1 );
        List<Double> ctList = input.get( 2 );
        List<Double> tocList = input.get( 3 );

        Map<Integer, List> expected = result.get( 1 );
        List<Double> expectedUpdatedOtyList = expected.get( 6 );

        List<Double> actualUpdatedOtyList = new LinkedList<Double>();
        List<Boolean> judgements = new LinkedList<Boolean>();

        for ( int i = 0; i < obyList.size(); i++ ) {
            ClientInfo client = new ClientInfo( 1, obyList.get( i ), otyList.get( i ), tocList.get( i ) );
            boolean flag = client.updateInfo( ctList.get( i ) );
            if ( flag ) {
                double owed = client.getOwedThisYear() + client.getOwedBeforeYear();
                actualUpdatedOtyList.add( client.getOwedThisYear() + client.getOwedBeforeYear() );

                judgements.add( Math.abs( owed - expectedUpdatedOtyList.get( i ) ) < 0.00001 ? true : false );
            } else {
                actualUpdatedOtyList.add( null );
                judgements.add( expectedUpdatedOtyList.get( i ) == null ? true : false);
            }

        }

        Map<Integer, List> data1 = new HashMap<Integer, List>();
        data1.put( 0, expectedUpdatedOtyList );
        data1.put( 1, actualUpdatedOtyList );
        data1.put( 2, judgements );
        Map<Integer, Integer> type3 = new HashMap<Integer, Integer>() {
            {
                put( 0, 0 );
                put( 1, 0 );
                put( 2, 2 );
            }
        };

        assertEquals( true, writeBack( type3, data1, eTool) );
    }

    @Test
    public void boundaryTest() throws Exception {
        ExcelTool eTool = new ExcelTool( "testFiles/boundaryTest.xlsx" );

        Map<Integer, Integer> type1 = new HashMap<Integer, Integer>() {
            {
                put( 0, 0 );
                put( 1, 0 );
                put( 2, 0 );
                put( 3, 0 );
            }
        };
        Map<Integer, Integer> type2 = new HashMap<Integer, Integer>() {
            {
                put( 4, 0 );
                put( 5, 0 );
            }
        };

        List<Map<Integer, List>> result = eTool.readFromExcel( type1, type2, 2 );

        Map<Integer, List> input = result.get( 0 );
        List<Double> obyList = input.get( 0 );
        List<Double> otyList = input.get( 1 );
        List<Double> ctList = input.get( 2 );
        List<Double> tocList = input.get( 3 );

        Map<Integer, List> expected = result.get( 1 );
        List<Double> expectedUpdatedOtyList = expected.get( 5 );

        List<Double> actualUpdatedOtyList = new LinkedList<Double>();
        List<Boolean> judgements = new LinkedList<Boolean>();

        for ( int i = 0; i < obyList.size(); i++ ) {
            ClientInfo client = new ClientInfo( 1, obyList.get( i ), otyList.get( i ), tocList.get( i ) );
            boolean flag = client.updateInfo( ctList.get( i ) );
            if ( flag ) {
                double owed = client.getOwedThisYear() + client.getOwedBeforeYear();
                actualUpdatedOtyList.add( client.getOwedThisYear() + client.getOwedBeforeYear() );

                judgements.add( Math.abs( owed - expectedUpdatedOtyList.get( i ) ) < 0.00001 ? true : false );
            } else {
                judgements.add( expectedUpdatedOtyList.get( i ) == null ? true : false);
            }

        }

        Map<Integer, List> data1 = new HashMap<Integer, List>();
        data1.put( 0, expectedUpdatedOtyList );
        data1.put( 1, actualUpdatedOtyList );
        data1.put( 2, judgements );
        Map<Integer, Integer> type3 = new HashMap<Integer, Integer>() {
            {
                put( 0, 0 );
                put( 1, 0 );
                put( 2, 2 );
            }
        };

        assertEquals( true, writeBack( type3, data1, eTool) );
    }

    @Test
    public void decisionTreeTest() throws Exception {
        ExcelTool eTool = new ExcelTool( "testFiles/decisionTree.xlsx" );

        Map<Integer, Integer> type1 = new HashMap<Integer, Integer>() {
            {
                put( 0, 0 );
                put( 1, 0 );
                put( 2, 0 );
                put( 3, 0 );
            }
        };
        Map<Integer, Integer> type2 = new HashMap<Integer, Integer>() {
            {
                put( 4, 0 );
                put( 5, 0 );
            }
        };

        List<Map<Integer, List>> result = eTool.readFromExcel( type1, type2, 3 );

        Map<Integer, List> input = result.get( 0 );
        List<Double> obyList = input.get( 0 );
        List<Double> otyList = input.get( 1 );
        List<Double> ctList = input.get( 2 );
        List<Double> tocList = input.get( 3 );

        Map<Integer, List> expected = result.get( 1 );
        List<Double> expectedUpdatedOtyList = expected.get( 5 );

        List<Double> actualUpdatedOtyList = new LinkedList<Double>();
        List<Boolean> judgements = new LinkedList<Boolean>();

        for ( int i = 0; i < obyList.size(); i++ ) {
            ClientInfo client = new ClientInfo( 1, obyList.get( i ), otyList.get( i ), tocList.get( i ) );
            boolean flag = client.updateInfo( ctList.get( i ) );
            if ( flag ) {
                double owed = client.getOwedThisYear() + client.getOwedBeforeYear();
                actualUpdatedOtyList.add( client.getOwedThisYear() + client.getOwedBeforeYear() );

                judgements.add( owed == expectedUpdatedOtyList.get( i ) ? true : false );
            } else {
                judgements.add( expectedUpdatedOtyList.get( i ) == null ? true : false);
            }

        }

        Map<Integer, List> data1 = new HashMap<Integer, List>();
        data1.put( 0, expectedUpdatedOtyList );
        data1.put( 1, actualUpdatedOtyList );
        data1.put( 2, judgements );
        Map<Integer, Integer> type3 = new HashMap<Integer, Integer>() {
            {
                put( 0, 0 );
                put( 1, 0 );
                put( 2, 2 );
            }
        };

        assertEquals( true, writeBack( type3, data1, eTool) );
    }


    public boolean writeBack( Map<Integer, Integer> type3, Map<Integer, List> data1, ExcelTool eTool ) throws IOException {

        List<Map<Integer, Integer>> inputTypeList = new LinkedList<Map<Integer, Integer>>();
        inputTypeList.add( type3 );
        List<Map<Integer, List>> inputDataList = new LinkedList<Map<Integer, List>>();
        inputDataList.add( data1 );

        Map<Integer, String> title = new HashMap<Integer, String>() {
            {
                put( 0, "expectedExpence" );
                put( 1, "actualExpence" );
                put( 2, "result" );
            }
        };

        return eTool.writeIntoExcel( title, inputTypeList, inputDataList );

    }
}