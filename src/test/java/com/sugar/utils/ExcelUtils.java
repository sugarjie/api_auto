package com.sugar.utils;

import com.sugar.pojo.ApiCase;
import com.sugar.pojo.CellData;
import com.sugar.pojo.ExcelObject;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ExcelUtils {

    public static List<Object> read(String excelPath, int sheetIndex, Class clazz) {
        List<Object> datas = new ArrayList<>();
        InputStream inp = null;
        try {
            inp = ExcelObject.class.getResourceAsStream(excelPath);
            Workbook workbook = WorkbookFactory.create(inp);
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            //处理标题行
            Row titleRow = sheet.getRow(0);
            int lastCellNum = titleRow.getLastCellNum();
            String[] titles = new String[lastCellNum];
            for (int i = 0; i < lastCellNum; i++) {
                Cell cell = titleRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                cell.setCellType(CellType.STRING);
                titles[i] = cell.getStringCellValue();
            }
            // 处理数据行
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 1; i <= lastRowNum; i++) {
                Object object = clazz.newInstance();
                // 处理行号
                int rowNo = i + 1;
                String reflectRowNoMethodName = "setRowNo";
                Method rowNoMethod = clazz.getMethod(reflectRowNoMethodName, int.class);
                rowNoMethod.invoke(object, rowNo);
                Row dataRow = sheet.getRow(i);
                for (int j = 0; j < lastCellNum; j++) {
                    Cell cell = dataRow.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellType(CellType.STRING);
                    String reflectMethodName = "set" + titles[j];
                    Method method = clazz.getMethod(reflectMethodName, String.class);
                    String cellValue = ParamUtils.getFinalStr(cell.getStringCellValue());
                    method.invoke(object, cellValue);
                }
                datas.add(object);
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (inp != null) {
                try {
                    inp.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return datas;
    }

    public static void writeBatch(String sourcePath, String targetPath) {
        InputStream inp = null;
        OutputStream outputStream=null;
        try {
            inp = ExcelUtils.class.getResourceAsStream(sourcePath);
            Workbook workbook = WorkbookFactory.create(inp);
            List<CellData> cellDataList = ApiUtils.getCellDatas();
            Sheet sheet = null;
            int index = -999;
            for (CellData cellData : cellDataList) {
                if (index != cellData.getSheetIndex()) {
                    sheet = workbook.getSheetAt(cellData.getSheetIndex());
                }
                index = cellData.getSheetIndex();
                Row row = sheet.getRow(cellData.getRowNo() - 1);
                Cell cell = row.getCell(cellData.getColumnNo() - 1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                cell.setCellType(CellType.STRING);
                cell.setCellValue(cellData.getContent());
            }
            outputStream = new FileOutputStream(new File(targetPath));
            workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }if(inp!=null){
                try {
                    inp.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void main(String[] args) {
        List<Object> datas = read("/test_case.xlsx", 0, ApiCase.class);
        for (Object data : datas) {
            System.out.println(data);
        }
    }
}
