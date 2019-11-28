package com.bkrwin.ufast.infra.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServletResponse;

import com.bkrwin.ufast.infra.util.CloseUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.util.CellRangeAddress;


public class ExcelUtil {

  private static final Log logger = LogFactory.getLog(ExcelUtil.class);


  public static HSSFWorkbook readXls(String filePath) {
    FileInputStream inputStream = null;
    HSSFWorkbook workBook = null;

    try {
      inputStream = new FileInputStream(filePath);
      if(null != inputStream) {
        workBook = new HSSFWorkbook(inputStream);
      }
    } catch (FileNotFoundException var8) {
      logger.error("readXls fail!", var8);
    } catch (IOException var9) {
      logger.error("readXls fail!", var9);
    } finally {
      CloseUtils.close(new AutoCloseable[]{inputStream});
    }

    return workBook;
  }

  public static void writeXls(HSSFWorkbook workBook, String path) {
    FileOutputStream outputStream = null;

    try {
      outputStream = new FileOutputStream(path);
      workBook.write(outputStream);
    } catch (FileNotFoundException var8) {
      logger.error("writeXls fail!", var8);
    } catch (IOException var9) {
      logger.error("writeXls fail!", var9);
    } finally {
      CloseUtils.close(new AutoCloseable[]{outputStream});
    }

  }

  public static InputStream getInputStream(HSSFWorkbook workBook) {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    ByteArrayInputStream inputStream = null;

    try {
      workBook.write(byteArrayOutputStream);
      byte[] e = byteArrayOutputStream.toByteArray();
      inputStream = new ByteArrayInputStream(e);
    } catch (IOException var7) {
      logger.error("Write excel fail!" + ExceptionUtils.getStackTrace(var7));
    } finally {
      CloseUtils.close(new AutoCloseable[]{inputStream});
      CloseUtils.close(new AutoCloseable[]{byteArrayOutputStream});
    }

    return inputStream;
  }

  public static ByteArrayOutputStream getByteArrayOutputStream(HSSFWorkbook workBook) {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    try {
      workBook.write(byteArrayOutputStream);
    } catch (IOException var6) {
      logger.error("Write excel fail!" + ExceptionUtils.getStackTrace(var6));
    } finally {
      CloseUtils.close(new AutoCloseable[]{byteArrayOutputStream});
    }

    return byteArrayOutputStream;
  }

  public static HSSFSheet getSheet(HSSFWorkbook workBook, String sheetName) {
    HSSFSheet sheet = workBook.getSheet(sheetName);
    if(null == sheet) {
      sheet = workBook.createSheet(sheetName);
    }

    return sheet;
  }

  public static HSSFRow getRow(HSSFSheet sheet, int rowIndex) {
    HSSFRow row = sheet.getRow(rowIndex);
    if(row == null) {
      row = sheet.createRow(rowIndex);
    }

    return row;
  }

  public static HSSFCell getCell(HSSFRow row, int colIndex) {
    HSSFCell cell = row.getCell(colIndex);
    if(cell == null) {
      cell = row.createCell(colIndex);
    }

    return cell;
  }

  public static HSSFCell getCell(HSSFSheet sheet, int rowIndex, int colIndex) {
    HSSFRow row = getRow(sheet, rowIndex);
    return getCell(row, colIndex);
  }

  public static void setSheetCellValue(HSSFSheet sheet, int rowIndex, int colIndex, String value, int cellType) {
    HSSFCell cell = getCell(sheet, rowIndex, colIndex);
    setCellValue(cell, value, cellType);
  }

  public static void setSheetCellValue(HSSFSheet sheet, int rowIndex, int colIndex, int value) {
    HSSFCell cell = getCell(sheet, rowIndex, colIndex);
    setCellValue(cell, value);
  }

  public static void setSheetCellValue(HSSFSheet sheet, int rowIndex, int colIndex, long value) {
    HSSFCell cell = getCell(sheet, rowIndex, colIndex);
    setCellValue(cell, value);
  }

  public static void setSheetCellValue(HSSFSheet sheet, int rowIndex, int colIndex, String value) {
    HSSFCell cell = getCell(sheet, rowIndex, colIndex);
    setCellValue(cell, value);
  }

  public static void setCellValue(HSSFCell cell, String value, int cellType) {
    cell.setCellType(cellType);
    switch(cell.getCellType()) {
      case 0:
        cell.setCellValue((double)Integer.parseInt(value));
        break;
      case 1:
        cell.setCellValue(value);
        break;
      case 2:
        cell.setCellFormula(value);
        break;
      default:
        cell.setCellValue(value);
    }

  }

  public static void setCellValue(HSSFCell cell, int value) {
    cell.setCellType(0);
    cell.setCellValue((double)value);
  }

  public static void setCellValue(HSSFCell cell, long value) {
    cell.setCellType(0);
    cell.setCellValue((double)value);
  }

  public static void setCellValue(HSSFCell cell, String value) {
    cell.setCellType(1);
    cell.setCellValue(value);
  }

  public static String getSheetCellValue(HSSFSheet sheet, int rowIndex, int colIndex) {
    HSSFCell cell = getCell(sheet, rowIndex, colIndex);
    return getCellStringValue(cell);
  }

  public static String getCellStringValue(HSSFCell cell) {
    String cellValue = "";
    switch(cell.getCellType()) {
      case 0:
        cellValue = String.valueOf(cell.getNumericCellValue());
        break;
      case 1:
        cellValue = cell.getStringCellValue();
        break;
      case 2:
        cell.setCellType(0);
        cellValue = String.valueOf(cell.getNumericCellValue());
      case 3:
      case 5:
      default:
        break;
      case 4:
        cellValue = String.valueOf(cell.getBooleanCellValue());
    }

    return cellValue;
  }

  public static void mergeCell(HSSFSheet sheet, int rowFrom, int rowTo, int columnFrom, int columnTo) {
    CellRangeAddress crAddress = new CellRangeAddress(rowFrom, rowTo, columnFrom, columnTo);
    sheet.addMergedRegion(crAddress);
  }

  public static void setCellStyle(HSSFWorkbook workBook, HSSFCell cell, short styleType) {
    HSSFCellStyle cellStyle = workBook.createCellStyle();
    cellStyle.setAlignment(styleType);
    cell.setCellStyle(cellStyle);
  }

  public static double calculateFormulaValue(HSSFWorkbook workBook, HSSFCell cell) {
    double retValue = 0.0D;
    if(cell.getCellType() == 2) {
      HSSFFormulaEvaluator evaluator = new HSSFFormulaEvaluator(workBook);
      CellValue cellValue = evaluator.evaluate(cell);
      retValue = cellValue.getNumberValue();
    }

    return retValue;
  }

  public static void resetFormulaCell(HSSFSheet sheet, int rowIndex, int colIndex) {
    HSSFCell hssfCell = getCell(sheet, rowIndex, colIndex);
    if(2 == hssfCell.getCellType()) {
      hssfCell.setCellFormula(hssfCell.getCellFormula());
    }

  }

  public static void resetFormulaCell(HSSFCell hssfCell) {
    if(2 == hssfCell.getCellType()) {
      hssfCell.setCellFormula(hssfCell.getCellFormula());
    }

  }

  public static int convertToColumnIndex(String letterName) {
    int column = -1;
    letterName = StringUtils.upperCase(letterName);

    for(int i = 0; i < letterName.length(); ++i) {
      char c = letterName.charAt(i);
      column = (column + 1) * 26 + c - 65;
    }

    return column;
  }

  public static String convertToLetterName(int columnIndex) {
    ++columnIndex;
    String strResult = "";
    int intRound = columnIndex / 26;
    int intMod = columnIndex % 26;
    if(intRound != 0) {
      strResult = String.valueOf((char)(intRound + 64));
    }

    strResult = strResult + String.valueOf((char)(intMod + 64));
    return strResult;
  }

  public static String convertToCellId(int columnIndex, int rowIndex) {
    return convertToLetterName(columnIndex) + (rowIndex + 1);
  }

  public static void delSheet(HSSFWorkbook workBook, String sheetName) {
    int sheetIndex = workBook.getSheetIndex(sheetName);
    workBook.removeSheetAt(sheetIndex);
  }

  public static void writeExcel(String fileName, byte[] bytes, HttpServletResponse response) throws Exception {
    try {
      response.setContentType("application/x-msdownload");
      response.setCharacterEncoding("UTF-8");
      response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1") + ".xls");
      response.setContentLength(bytes.length);
      response.getOutputStream().write(bytes);
      response.getOutputStream().flush();
      response.getOutputStream().close();
    } catch (IOException var4) {
      ;
    }

  }

}
