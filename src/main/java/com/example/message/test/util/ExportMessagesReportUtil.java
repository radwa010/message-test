package com.example.message.test.util;
import static com.example.message.test.util.Constants.ERROR_WHILE_WRITING_WITH_FILE;
import static com.example.message.test.util.Constants.ERROR_CREATING_SHEET;
import static com.example.message.test.util.Constants.X_AXIS;
import static com.example.message.test.util.Constants.Y_AXIS;
import static com.example.message.test.util.Constants.CHART_TITLE;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.stereotype.Component;

import com.example.message.test.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ExportMessagesReportUtil {
	
	 private ExportMessagesReportUtil() {}
	
	public static void generateMessagePerDayReport(Map<String, Integer> messagesPerDay) {
		//generate chart from message list
		JFreeChart barChart = generateChart(messagesPerDay);
		//export excel file
		generateExcelFile(messagesPerDay,barChart);
	}

	private static void generateExcelFile(Map<String, Integer> messagesPerDay, JFreeChart barChart) {
        // Generate the Excel file
        try( Workbook workbook = new XSSFWorkbook()) {
        		 Sheet sheet = workbook.createSheet(CHART_TITLE);
                 Row rowHeader = sheet.createRow(0);
                 rowHeader.createCell(0).setCellValue(X_AXIS);
                 rowHeader.createCell(1).setCellValue(Y_AXIS);

                 int rowNum = 1;
                 for (Map.Entry<String, Integer> entry : messagesPerDay.entrySet()) {
                     Row row = sheet.createRow(rowNum++);
                     row.createCell(0).setCellValue(entry.getKey());
                     row.createCell(1).setCellValue(entry.getValue());
                 }

                 // Auto-size the columns
                 sheet.autoSizeColumn(0);
                 sheet.autoSizeColumn(1);
                     
                 //draw chart in sheet
                 byte[] bytes = getChartAsByteArray(barChart);
                 int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
                 CreationHelper helper = workbook.getCreationHelper();
                 Drawing<?> drawing = sheet.createDrawingPatriarch();
                 ClientAnchor anchor = helper.createClientAnchor();
                 anchor.setCol1(0);
                 anchor.setRow1(3);
                 Picture picture = drawing.createPicture(anchor, pictureIdx);
                 picture.resize();
                 // Write the Excel file
                 exportExcelFile(workbook);               
             } catch (Exception e) {
             	log.error(e.getMessage());
             	throw new ResourceNotFoundException(ERROR_CREATING_SHEET, ERROR_CREATING_SHEET, e);
             }
}

	private static void exportExcelFile(Workbook workbook) {
		try (FileOutputStream fileOut = new FileOutputStream("Messages_Per_Day_Report.xlsx")) {
			workbook.write(fileOut);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ResourceNotFoundException(ERROR_WHILE_WRITING_WITH_FILE, ERROR_WHILE_WRITING_WITH_FILE, e);
		}
	}

	private static JFreeChart generateChart(Map<String, Integer> messagesPerDay) {
	
		  // Create dataset for the bar graph
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Integer> entry : messagesPerDay.entrySet()) {
            dataset.addValue(entry.getValue(), "Messages", entry.getKey());
        }

        // Create the bar chart
        JFreeChart chart =  ChartFactory.createBarChart(
                CHART_TITLE,
                Y_AXIS,
                X_AXIS,
                dataset
        );
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setMaximumBarWidth(0.1);  
        return chart;
	}
	
	   
	   private static byte[] getChartAsByteArray(JFreeChart chart) throws IOException {
	        // Convert JFreeChart to byte array
	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        ChartUtils.writeChartAsPNG(outputStream, chart, 800, 600);
	        outputStream.flush();
	        byte[] chartBytes = outputStream.toByteArray();
	        outputStream.close();
	        return chartBytes;
	    }
	

}
