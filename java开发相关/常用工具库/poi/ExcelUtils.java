package com.poi.mypoi.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description 操作Excel的工具类
 * @date 2018/08/08 16:19
 */
public class ExcelUtils {
	public static final String OFFICE_EXCEL_2003_POSTFIX = ".xls";
	public static final String OFFICE_EXCEL_2007_POSTFIX = ".xlsx";
	public static final String FILE_EXTENSION_SPLITER = ".";


	public static Workbook getWorkbook(InputStream in, String filename) throws Exception {
		Workbook wb;
		String fileType = filename.substring(filename.lastIndexOf(FILE_EXTENSION_SPLITER));
		if(OFFICE_EXCEL_2003_POSTFIX.equals(fileType)) {
			wb = new HSSFWorkbook(in);
		}else if(OFFICE_EXCEL_2007_POSTFIX.equals(fileType)) {
			wb = new XSSFWorkbook(in);
		}else{
			throw new Exception("不支持的文件格式");
		}
		return wb;
	}

	public static Object getCellValue(Cell cell) {
		if(cell == null) {
			return "";
		}

		DecimalFormat df = new DecimalFormat("0.0000000000");

		switch (cell.getCellTypeEnum()) {
			case STRING:
				return cell.getRichStringCellValue().getString();
			case NUMERIC:
				if(DateUtil.isCellDateFormatted(cell)) {
					return cell.getDateCellValue();
				}else{
					return df.format(cell.getNumericCellValue());
				}

			case BOOLEAN:
				return cell.getBooleanCellValue();
			case BLANK:
				return "";
			default:
				return "";
		}
	}

	public static Map<String, List<List<Object>>> getAllDataFromFile(InputStream in, String filename) throws Exception {
		Map<String, List<List<Object>>> result = new HashMap<>();

		Workbook workbook = getWorkbook(in, filename);
		if(null == workbook) {
			throw new Exception("打开Excel文件失败");
		}

		for(int i = 0; i < workbook.getNumberOfSheets(); i++) {
			Sheet sheet = workbook.getSheetAt(i);
			if(sheet == null) {
				continue;
			}

			List<List<Object>> rowList = new ArrayList<>();
			for(int j = sheet.getFirstRowNum(); j < sheet.getLastRowNum()+1; j++) {
				Row row = sheet.getRow(j);
				if(row == null) {
					continue;
				}

				List<Object> cellList = new ArrayList<>();
				for(int k = row.getFirstCellNum(); k<row.getLastCellNum(); k++) {
					Cell cell = row.getCell(k);
					cellList.add(getCellValue(cell));
				}
				rowList.add(cellList);
			}

			result.put(sheet.getSheetName(), rowList);
		}
		return result;
	}

	public static void main(String[] argv) {
		try{
			String filePath = "D:\\activemq\\mypoi\\src\\main\\java\\com\\poi\\mypoi\\util\\aaa.xlsx";

			InputStream in = new FileInputStream(filePath);
			Map<String, List<List<Object>>> data = getAllDataFromFile(in, filePath);
			for(String key : data.keySet()) {
				for(List<Object> l : data.get(key)) {
					for(Object e : l) {
						System.out.println(e);
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
