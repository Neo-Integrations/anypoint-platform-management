package org.nint.export.excel;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class ExcelDataExporter {
	
	private ExcelDataExporter() {}

    public static void export(final List<Map<String, Object>> records, 
    							final String path,
    							final List<Map<String, Map<String, String>>> headers) throws IOException {
    	
    	final ExcelDataExporter exporter = new ExcelDataExporter();
    	
        final XSSFWorkbook workbook = new XSSFWorkbook();
        for(Map<String, Map<String, String>> eachHeader : headers) {
        	for(String key : eachHeader.keySet()) {
        		exporter.createNodeSheet(workbook, records, eachHeader.get(key), key);
        	}
        }
        exporter.save(workbook, path);
        workbook.close();
    }


    private void createNodeSheet(final Workbook workbook,
                            final List<Map<String, Object>> records,
                            final Map<String, String> headers,
                            final String sheetName) {

        if(workbook == null) throw new IllegalArgumentException("Workbook is null");
        final Sheet sheet = createSheet(workbook, sheetName);

        createHeader(workbook,
                sheet,
                headers,
                0);

        int index = 0;
        for(Map<String, Object> eachRecord : records) {	
        	List<Map> items = (List<Map>) eachRecord.get(sheetName);
        	
        	if(items != null && items.size() > 0) {
	        	for(Map item : items) {
	        		createRecord(workbook, sheet, headers, eachRecord, (index + 1), item);
	        		index++;
	        	}
        	} else {
	        	createRecord(workbook, sheet, headers, eachRecord, (index + 1), null);
	        	index++;
        	}
        }

    }

    private void createHeader(final Workbook workbook,
                              final Sheet sheet,
                              final Map<String, String> headerKeys,
                              final int recordIndex) {

        Row header = sheet.createRow(recordIndex);
        CellStyle headerStyle = createHeaderStyle(workbook, sheet);
        
        int index = 0;
        for(Map.Entry<String, String> entry : headerKeys.entrySet()) {
        	sheet.setColumnWidth(index, 4000);
        	this.createCell(header, entry.getValue(), index, headerStyle);
        	index++;
        	
        }
    }
    
    private void createRecord(final Workbook workbook,
            final Sheet sheet,
            final Map<String, String> headerKeys,
            final Map<String, Object> node,
            final int recordIndex,
            final Map item) {
    	
        Row record = sheet.createRow(recordIndex);
        CellStyle recordStyle = createRecordStyle(workbook, sheet);
       
        
        int index = 0;
        for(Map.Entry<String, String> entry : headerKeys.entrySet()) {
        	sheet.setColumnWidth(index, 4000);
        	
        	Object name = null;
        	if(entry.getKey().contains(":")) {
        		if(item != null) {
	        		String[] keys = entry.getKey().split(":");
	        		name = item.get(keys[1]);
        		}
        	} else {
        		name = node.get(entry.getKey());
        	}

        	this.createCell(record, String.valueOf(name), index, recordStyle);
        	index++;
        	
        }
    }

    private CellStyle createHeaderStyle(final Workbook workbook, final Sheet sheet) {

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFont(headerFont(workbook));
        return headerStyle;
    }

    private CellStyle createRecordStyle(final Workbook workbook, final Sheet sheet) {
        CellStyle recordStyle = workbook.createCellStyle();
        recordStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        recordStyle.setFont(recordFont(workbook));
        return recordStyle;
    }

    private Font headerFont(final Workbook workbook) {
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontName("Arial");
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());

        return headerFont;
    }


    private Font recordFont(final Workbook workbook) {
        Font recordFont = workbook.createFont();
        recordFont.setBold(false);
        recordFont.setFontName("Arial");
        recordFont.setFontHeightInPoints((short) 12);
        recordFont.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());

        return recordFont;
    }

    private Cell createCell(final Row row, final String name, final int index, final CellStyle style) {

        Cell cell = row.createCell(index);
        cell.setCellValue(name);
        cell.setCellStyle(style);

        return cell;
    }

    private Sheet createSheet(final Workbook workbook, final String sheetName) {
        final Sheet sheet = workbook.createSheet(sheetName);
        return sheet;
    }

    private void save(final Workbook workbook, String fileLocation) throws IOException {

        Path p = Paths.get(fileLocation);
        try(OutputStream writer = Files.newOutputStream(p)) {
            workbook.write(writer);
        }
        
    }
}
