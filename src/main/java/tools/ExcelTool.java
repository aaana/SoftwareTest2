package tools;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.*;
import java.util.*;

/**
 * Created by tina on 16/4/28.
 */
public class ExcelTool {

    private String fileDir;

    public ExcelTool( String fileDir ) {
        this.fileDir = fileDir;
    }


    // 选定工作表
    static Sheet getSheetByNum( Workbook book, int number ) {

        Sheet sheet = null;

        try
        {
            sheet = book.getSheetAt( number );
//          if( sheet == null )
//          {
//              sheet = book.createSheet( "Sheet" + number );
//          }
        } catch ( Exception e )
        {
            throw new RuntimeException( e.getMessage() );
        }
        return sheet;

    }


    // 选定excel文件
    public static Workbook getExcelWorkbook( String filePath ) throws IOException {
        Workbook book = null;
        File file  = null;
        FileInputStream fis = null;
        try {
            file = new File( filePath );
            if( !file.exists() )
            {
                throw new RuntimeException( "文件不存在" );
            }else
            {
                fis = new FileInputStream( file );
                book = WorkbookFactory.create( fis );
            }
        } catch ( Exception e )
        {
            throw new RuntimeException( e.getMessage() );
        } finally
        {
            if( fis != null )
            {
                fis.close();
            }
        }
        return book;

    }


    // 读文件 返回类型: List<Map<Integer, List>>
    public List<Map<Integer, List>> readFromExcel( Map<Integer, Integer> inputTypeMap, Map<Integer, Integer> expectedResultTypeMap, int startLine ) throws IOException {

        Workbook book = null;
        book = getExcelWorkbook( this.fileDir );
        Sheet sheet = getSheetByNum( book, 0 );
        System.out.println( "打开excel文件成功" );

        int numOfData = sheet.getLastRowNum();
        System.out.println( "共有" + numOfData + "个测试用例" );

        return readData( sheet, numOfData, startLine, inputTypeMap, expectedResultTypeMap );

    }

    public Map<Integer, List> initDataMap( Map<Integer, Integer> aMap, Map<Integer, List> outMap ) {

        int index = 1;
        for( Map.Entry<Integer, Integer> entry : aMap.entrySet() ) {
            int key = entry.getKey();
            int value = entry.getValue();

            if ( value == 0 ) {
                List<Double> list = new ArrayList<Double>();
                outMap.put( key, list );
            } else if ( value == 1 ) {
                List<String> list = new ArrayList<String>();
                outMap.put( key, list );
            } else if ( value == 2 ) {
                List<Boolean> list = new ArrayList<Boolean>();
                outMap.put( key, list );
            } else {
                return null;
            }

            index += 1;
        }
        return outMap;
    }

    public Map<Integer, List> setDataMap( Map<Integer, Integer> typeMap, Map<Integer, List> dataMap, Row row ) {

        for( Map.Entry<Integer, Integer> entry : typeMap.entrySet() ) {
            int key = entry.getKey();
            int value = entry.getValue();  //类型

            if ( value == 0 ) {
                Double attribute;
                if ( row.getCell( key ) == null )
                {
                    attribute = null;
                } else {
                    attribute = row.getCell( key ).getNumericCellValue();
                }
                List<Double> list = ( List<Double> ) dataMap.get( key );
                list.add( attribute );
                dataMap.remove( key );
                dataMap.put( key, list );
            } else if ( value == 1 ) {
                String attribute;
                if ( row.getCell( key ) == null )
                {
                    attribute = null;
                } else {
                    attribute = row.getCell( key ).getStringCellValue();
                }
                List<String> list = ( List<String> ) dataMap.get( key );
                list.add( attribute );
                dataMap.remove( key );
                dataMap.put( key, list );
            } else if ( value == 2 ) {
                Boolean attribute;
                if ( row.getCell( key ) == null )
                {
                    attribute = null;
                } else {
                    attribute = row.getCell( key ).getBooleanCellValue();
                }
                List<Boolean> list = ( List<Boolean> ) dataMap.get( key );
                list.add( attribute );
                dataMap.remove( key );
                dataMap.put( key, list );
            } else {
                return null;
            }
        }
        return dataMap;
    }

    public List<Map<Integer, List>> readData( Sheet sheet, int numOfData, int startLine, Map<Integer, Integer> inputTypeMap, Map<Integer, Integer> expectedResultTypeMap ) {

        Map<Integer, List> inputDataMap = new LinkedHashMap<Integer, List>();
        Map<Integer, List> expectedResultDataMap = new LinkedHashMap<Integer, List>();
        inputDataMap = initDataMap( inputTypeMap, inputDataMap );
        expectedResultDataMap = initDataMap( expectedResultTypeMap, expectedResultDataMap );

        for ( int i = startLine - 1; i < numOfData + startLine; i++ ) {
            Row row = sheet.getRow( i );

            if( row != null ) {
                inputDataMap = setDataMap( inputTypeMap, inputDataMap, row );
                expectedResultDataMap = setDataMap( expectedResultTypeMap, expectedResultDataMap, row );
            }
        }

        List<Map<Integer, List>> outputList = new ArrayList<Map<Integer, List>>();
        outputList.add( inputDataMap );
        outputList.add( expectedResultDataMap );

        return outputList;
    }


    // 写文件
    public boolean writeIntoExcel( Map<Integer, String> title, List<Map<Integer, Integer>> type, List<Map<Integer, List>> data) throws IOException {

        Workbook book = null;
        book = getExcelWorkbook( this.fileDir );
        Sheet sheet = getSheetByNum( book, 1 );

        int startLine = 0;

        // 写标题
        if ( title != null ) {
            Row row = sheet.createRow( 0 );
            for( Map.Entry<Integer, String> entry : title.entrySet() ) {
                int key = entry.getKey();
                String value = entry.getValue();

                row.createCell( key ).setCellValue( value );
            }
            startLine = 1;
        }

        // 写内容
        for ( int i = 0; i < data.get( 0 ).get( 0 ).size(); i++ ) {
            Row row = sheet.createRow( i + startLine );

            int index = 0;
            for ( Map<Integer, Integer> oneTypeMap : type ) {

                Map<Integer, List> oneDataMap = data.get( index );
                for( Map.Entry<Integer, Integer> entry : oneTypeMap.entrySet() ) {
                    int location = entry.getKey();
                    int valueType = entry.getValue();

                    if ( valueType == 0 ) {
                        List<Double> list = ( List<Double> )oneDataMap.get( location );
                        if ( list.get( i ) != null ) {
                            row.createCell( location ).setCellValue( list.get( i ) );
                        }

                    } else if ( valueType == 1 ) {
                        List<String> list = ( List<String> )oneDataMap.get( location );
                        if ( list.get( i ) != null ) {
                            row.createCell( location ).setCellValue( list.get( i ) );
                        }

                    } else if (valueType == 2 ) {
                        List<Boolean> list = ( List<Boolean> )oneDataMap.get( location );
                        if ( list.get( i ) != null ) {
                            row.createCell( location ).setCellValue( list.get( i ) );
                        }
                    } else {
                        return false;
                    }
                }
                index += 1;
            }
        }

        FileOutputStream outputStream = new FileOutputStream( fileDir );
        book.write(outputStream);
        outputStream.close();
        return true;
    }

}
