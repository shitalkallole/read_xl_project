package com.netcracker;

import lombok.Data;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;


public class App
{
    public static void main( String[] args ) throws IOException, ClassNotFoundException, SQLException {
        try {
            Map<String, String> specificationDetails=null;
            Set<Map<String,String>> specificationSet= new HashSet<>();

            Map<String, String> characteristicsDetails  = null;
            Set<Map<String, String>> characteristicsSet = new HashSet<>();

            Map<String, Set>  specificationToCharacteristics  = new HashMap<String, Set>();


            File file=new File("C:\\Users\\shik0719\\Desktop\\service_specification.xlsx");
            FileInputStream fis=new FileInputStream(file);
            int j=0,except=1;

            XSSFWorkbook wb=new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(0);

            Iterator<Row> itr=sheet.iterator();

            while(itr.hasNext()){
                Row row = itr.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                j=0;
                if(except!=1) {
                    specificationDetails  = new HashMap<>();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();

                        switch (j) {
                            case 0:
                                specificationDetails.put("specificationId", cell.getStringCellValue());
                                break;
                            case 1:
                                specificationDetails.put("name", cell.getStringCellValue());
                                break;
                            case 2:
                                specificationDetails.put("status", cell.getStringCellValue());
                                break;
                            case 3:
                                specificationDetails.put("singleton", cell.getStringCellValue());
                                break;
                        }
                        j++;
                    }
                    specificationSet.add(specificationDetails);

                }
                else
                    except=2;
            }

            except=1;
            sheet = wb.getSheetAt(1);
            itr=sheet.iterator();
            while(itr.hasNext()){
                Row row = itr.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                j=0;
                if(except!=1) {
                    characteristicsDetails=new HashMap<>();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        switch (j) {
                            case 0:
                                characteristicsDetails.put("specificationId", cell.getStringCellValue());
                                break;
                            case 1:
                                characteristicsDetails.put("externalId",cell.getStringCellValue());
                                break;
                            case 2:
                                characteristicsDetails.put("name",cell.getStringCellValue());
                                break;
                            case 3:
                                characteristicsDetails.put("type",cell.getStringCellValue());
                                break;
                        }
                        j++;
                    }

                    characteristicsSet.add(characteristicsDetails);
                }
                else
                    except=2;
            }

            System.out.println("Specification set :");
            System.out.println(specificationSet);
            System.out.println("Characteristics set :");
            System.out.println(characteristicsSet);

            for (Map<String,String> record:specificationSet) {
                String key=record.get("specificationId");
                Set<Map<String,String>> ans=new HashSet<>();

                for (Map<String,String> charDetails:characteristicsSet) {
                    if(charDetails.get("specificationId").equals(key)){
                        ans.add(charDetails);
                    }
                }
                specificationToCharacteristics.put(key,ans);

            }

            System.out.println("Final Mapping :");
            System.out.println(specificationToCharacteristics);

        }
        catch (Exception e){
            System.out.println(e);
        }

    }
}
