package com.thinkgem.jeesite.modules.live.audience.service;

import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.stereotype.Service;

import com.thinkgem.jeesite.modules.live.audience.vo.AudienceVo;

@Service
public class ExportAudienceService {
	
    private final static Logger logger = Logger.getLogger(ExportAudienceService.class);

    private static final String[] TITLE = { "用户名", "姓名", "公司名", "部门", "职位", "性别", "注册邮箱", "手机", "座机", "传真", "国家地区", "详细地址" };

    public synchronized void export(HttpServletResponse response, List<AudienceVo> datasource, String sheetname) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet worksheet = workbook.createSheet(sheetname);
        this.buildReport(worksheet);
        this.fillReport(worksheet, datasource);
        String filename = "live_audience.xls";
        response.setHeader("Content-Disposition", "inline; filename=" + filename);
        response.setContentType("application/vnd.ms-excel");
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            worksheet.getWorkbook().write(outputStream);
            outputStream.flush();
        } catch (Exception e) {
            logger.error("export failure", e);
        }
    }

    public void fillReport(HSSFSheet worksheet, List<AudienceVo> datasource) {
        HSSFCellStyle bodyCellStyle = worksheet.getWorkbook().createCellStyle();
        bodyCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        bodyCellStyle.setWrapText(true);

        for (int i = 0; i < datasource.size(); i++) {
            HSSFRow row = worksheet.createRow(i + 1);
            AudienceVo vo = datasource.get(i);

            HSSFCell cell1 = row.createCell(0);
            cell1.setCellValue(vo.getUser()!=null?vo.getUser().getAccount():null);
            cell1.setCellStyle(bodyCellStyle);

            HSSFCell cell2 = row.createCell(1);
            cell2.setCellValue(vo.getName());
            cell2.setCellStyle(bodyCellStyle);

            HSSFCell cell3 = row.createCell(2);
            cell3.setCellValue(vo.getCompany());
            cell3.setCellStyle(bodyCellStyle);

            HSSFCell cell4 = row.createCell(3);
            cell4.setCellValue(vo.getDepartment());
            cell4.setCellStyle(bodyCellStyle);

            HSSFCell cell5 = row.createCell(4);
            cell5.setCellValue(vo.getJob());
            cell5.setCellStyle(bodyCellStyle);

            HSSFCell cell6 = row.createCell(5);
            cell6.setCellValue(vo.getSex()==1?"男":(vo.getSex()==2)?"女":"保密");
            cell6.setCellStyle(bodyCellStyle);

            HSSFCell cell7 = row.createCell(6);
            cell7.setCellValue(vo.getUser()!=null?vo.getUser().getEmail():null);
            cell7.setCellStyle(bodyCellStyle);

            HSSFCell cell8 = row.createCell(7);
            cell8.setCellValue(vo.getMobilePhone());
            cell8.setCellStyle(bodyCellStyle);
            
            HSSFCell cell9 = row.createCell(8);
            cell9.setCellValue(vo.getTelephone());
            cell9.setCellStyle(bodyCellStyle);
            
            HSSFCell cell10 = row.createCell(9);
            cell10.setCellValue(vo.getFax());
            cell10.setCellStyle(bodyCellStyle);
            
            HSSFCell cell11 = row.createCell(10);
            String ct = vo.getCountry() == null ? "" : vo.getCountry();
            String pc = vo.getProvince() == null ? "" : vo.getProvince();
            cell11.setCellValue(ct + pc);
            cell11.setCellStyle(bodyCellStyle);
            
            HSSFCell cell13 = row.createCell(11);
            cell13.setCellValue(vo.getAddress());
            cell13.setCellStyle(bodyCellStyle);
        }
    }


    public void buildReport(HSSFSheet worksheet) {
        worksheet.setColumnWidth(0, 5000);
        worksheet.setColumnWidth(1, 5000);
        worksheet.setColumnWidth(2, 5000);
        worksheet.setColumnWidth(3, 5000);
        worksheet.setColumnWidth(4, 5000);
        worksheet.setColumnWidth(5, 5000);
        worksheet.setColumnWidth(6, 5000);
        worksheet.setColumnWidth(7, 5000);
        worksheet.setColumnWidth(8, 5000);
        worksheet.setColumnWidth(9, 5000);
        worksheet.setColumnWidth(10, 5000);
        worksheet.setColumnWidth(11, 5000);

        buildHeaders(worksheet);
    }

    private void buildHeaders(HSSFSheet worksheet) {
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

        for (int i = 0; i < TITLE.length; i++) {
            HSSFCell cell = rowHeader.createCell(i);
            cell.setCellValue(TITLE[i]);
            cell.setCellStyle(headerCellStyle);
        }
    }
}
