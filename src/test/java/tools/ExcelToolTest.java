package tools;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

/**
 * Created by tina on 16/4/28.
 */
public class ExcelToolTest {

    ExcelTool eTool;
    @Before
    public void setUp() throws Exception {
        eTool = new ExcelTool( "testFiles/testTar.xlsx" );

    }

    @Test
    public void readFromExcel() throws Exception {
        Map<Integer, Integer> aType = new HashMap<Integer, Integer>() {
            {
                put( 0, 0 );
                put( 1, 0 );
                put( 2, 0 );
            }
        };
        Map<Integer, Integer> bType = new HashMap<Integer, Integer>() {
            {
                put( 4, 0 );
            }
        };

        List<Map<Integer, List>> result = eTool.readFromExcel( aType, bType, 2 );
        assertEquals( 2, result.size() );
        List<Double> aList = result.get( 0 ).get( 1 );  // 21, 22, 23
        System.out.println( aList );

    }

    @Test
    public void writeIntoExcel() throws Exception {
        Map<Integer, Integer> aType = new HashMap<Integer, Integer>() {
            {
                put( 0, 1 );
                put( 1, 0 );
                put( 3, 1 );
            }
        };
        Map<Integer, Integer> bType = new HashMap<Integer, Integer>() {
            {
                put( 5, 1 );
            }
        };

        Map<Integer, List> data1 = new HashMap<Integer, List>();
        List<String> list1 = new ArrayList<String>( asList( "hi00", "hi01", "hi02") );
        List<Double> list2 = new ArrayList<Double>( asList( 10.0, 11.0, 12.0 ));
        List<String> list3 = new ArrayList<String>( asList( "hi20", "hi21", "hi22") );
        data1.put( 0, list1 );
        data1.put( 1, list2 );
        data1.put( 3, list3 );

        Map<Integer, List> data2 = new HashMap<Integer, List>();
        List<String> list4 = new ArrayList<String>( asList( "hi30", "hi31", "hi32") );
        data2.put( 5, list4 );

        List<Map<Integer, Integer>> inputTypeList = new LinkedList<Map<Integer, Integer>>();
        inputTypeList.add( aType );
        inputTypeList.add( bType );
        List<Map<Integer, List>> inputDataList = new LinkedList<Map<Integer, List>>();
        inputDataList.add( data1 );
        inputDataList.add( data2 );

        Map<Integer, String> title = new HashMap<Integer, String>() {
            {
                put( 0, "col1" );
                put( 1, "col2" );
                put( 3, "col3" );
                put( 5, "col4" );
            }
        };

        assertEquals( true, eTool.writeIntoExcel( title, inputTypeList, inputDataList ) );
    }
}