package com.ofweek.live.core.modules.audience.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.modules.audience.entity.Audience;
import com.ofweek.live.core.modules.room.entity.RoomChat;

@Service
public class AudienceExportService {
	
	private static final Logger logger = LoggerFactory.getLogger(AudienceExportService.class);

    private static final String NULL = "";
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    
    public synchronized void export(HttpServletResponse response, List<Audience> audience, String title, String fileName, String expType) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet worksheet = workbook.createSheet(title);
        List<String> header = new ArrayList<String>(Arrays.asList("公司", "姓名", "部门", "职位", "手机", "座机"));
        switch (expType) {
			case "download" : 
				header.addAll(Arrays.asList("下载资料", "下载时间"));
				this.buildReport(worksheet, header);
				this.fillReportDownloadAudience(worksheet, audience);
				break;
			case "chat" : 
				header.add("聊天记录");
				this.buildReport(worksheet, header);
				this.fillReportChatAudience(worksheet, audience);
				break;
			case "all" : 
				header.add("进入时间");
				this.buildReport(worksheet, header);
				this.fillReportAllAudience(worksheet, audience);
				break;
			default :
				break;
        }
        
        try {
	        response.setHeader("Content-Disposition", "inline; filename=" +  new String(fileName.getBytes("UTF-8"), "ISO8859-1"));
	        response.setContentType("application/vnd.ms-excel");
            ServletOutputStream outputStream = response.getOutputStream();
            worksheet.getWorkbook().write(outputStream);
            outputStream.flush();
        } catch (Exception e) {
            logger.error("export exception", e);
        }
    }

    private void fillReportDownloadAudience(HSSFSheet worksheet, List<Audience> audience) {
        HSSFCellStyle bodyCellStyle = worksheet.getWorkbook().createCellStyle();
        bodyCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        bodyCellStyle.setWrapText(true);

        for (int i = 0; i < audience.size(); i++) {
            HSSFRow row = worksheet.createRow(i + 1);
            Audience oa = audience.get(i);
            
            setCell(worksheet, oa, row, bodyCellStyle);
            
            HSSFCell cell7 = row.createCell(6);
            cell7.setCellValue(oa.getSpeakerData() != null ? oa.getSpeakerData().getName() : NULL);
            cell7.setCellStyle(bodyCellStyle);
           
            HSSFCell cell8 = row.createCell(7);
            cell8.setCellValue(oa.getSpeakerData() != null ? FORMAT.format(oa.getSpeakerData().getCreateDate()) : NULL);
            cell8.setCellStyle(bodyCellStyle);
        }
    }
    
    private void fillReportChatAudience(HSSFSheet worksheet, List<Audience> audience) {
        HSSFCellStyle bodyCellStyle = worksheet.getWorkbook().createCellStyle();
        bodyCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        bodyCellStyle.setWrapText(true);

        for (int i = 0; i < audience.size(); i++) {
            HSSFRow row = worksheet.createRow(i + 1);
            Audience oa = audience.get(i);
            
            setCell(worksheet, oa, row, bodyCellStyle);
            
            HSSFCell cell7 = row.createCell(6);
            String content = NULL;
            for (RoomChat chat : oa.getRoomChat().getChats()) {
            	content += chat.getContent() + "\r\n" + FORMAT.format(chat.getCreateDate()) + "\r\n";
            }
            cell7.setCellValue(content);
            cell7.setCellStyle(bodyCellStyle);
        }
    }
    
    private void fillReportAllAudience(HSSFSheet worksheet, List<Audience> audience) {
        HSSFCellStyle bodyCellStyle = worksheet.getWorkbook().createCellStyle();
        bodyCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        bodyCellStyle.setWrapText(true);

        for (int i = 0; i < audience.size(); i++) {
            HSSFRow row = worksheet.createRow(i + 1);
            Audience oa = audience.get(i);
            
            setCell(worksheet, oa, row, bodyCellStyle);
            
            HSSFCell cell7 = row.createCell(6);
            cell7.setCellValue(oa.getEnterDate() != null ? FORMAT.format(oa.getEnterDate()) : NULL);
            cell7.setCellStyle(bodyCellStyle);
            
        }
    }

    public void buildReport(HSSFSheet worksheet, List<String> header) {
        worksheet.setColumnWidth(0, 5000);
        worksheet.setColumnWidth(1, 5000);
        worksheet.setColumnWidth(2, 5000);
        worksheet.setColumnWidth(3, 5000);
        worksheet.setColumnWidth(4, 5000);
        worksheet.setColumnWidth(5, 5000);
        if (header.size() == 8) {
        	worksheet.setColumnWidth(6, 8000);
        	worksheet.setColumnWidth(7, 5000);
        } else {
        	worksheet.setColumnWidth(6, 5000);
        }
        buildHeaders(worksheet, header);
    }

    private void buildHeaders(HSSFSheet worksheet, List<String> header) {
        Font font = worksheet.getWorkbook().createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);

        HSSFCellStyle headerCellStyle = worksheet.getWorkbook().createCellStyle();
        headerCellStyle.setFillPattern(CellStyle.FINE_DOTS);
        headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        headerCellStyle.setWrapText(true);
        headerCellStyle.setFont(font);
        headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);

        HSSFRow rowHeader = worksheet.createRow(0);
        rowHeader.setHeight((short) 500);

        for (int i = 0; i < header.size(); i++) {
            HSSFCell cell = rowHeader.createCell(i);
            cell.setCellValue(header.get(i));
            cell.setCellStyle(headerCellStyle);
        }
    }
    
    private static void setCell(HSSFSheet worksheet, Audience oa, HSSFRow row, HSSFCellStyle bodyCellStyle) {
    	HSSFCell cell1 = row.createCell(0);
        String company = oa.getCompany();
        cell1.setCellValue(isNotNull(company) ? company : NULL);
        cell1.setCellStyle(bodyCellStyle);
        
        HSSFCell cell2 = row.createCell(1);
        String name = oa.getName();
        cell2.setCellValue(isNotNull(name) ? name : NULL);
        cell2.setCellStyle(bodyCellStyle);
        
        HSSFCell cell3 = row.createCell(2);
        String department = oa.getDepartment();
        cell3.setCellValue(isNotNull(department) ? department : NULL);
        cell3.setCellStyle(bodyCellStyle);
        
        HSSFCell cell4 = row.createCell(3);
        String job = oa.getJob();
        cell4.setCellValue(isNotNull(job) ? job : NULL);
        cell4.setCellStyle(bodyCellStyle);
        
        HSSFCell cell5 = row.createCell(4);
        String mobilePhone = oa.getMobilePhone();
        cell5.setCellValue(isNotNull(mobilePhone) ? mobilePhone : NULL);
        cell5.setCellStyle(bodyCellStyle);
        
        HSSFCell cell6 = row.createCell(5);
        String telephone = oa.getTelephone();
        cell6.setCellValue(isNotNull(telephone) ? telephone : NULL);
        cell6.setCellStyle(bodyCellStyle);
    }
    
    private static boolean isNotNull(String val) {
    	if (StringUtils.isNotBlank(val)) {
    		return true;
    	}
    	return false;
    }
}
