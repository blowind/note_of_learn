package com.zxf.springmvc.controller;

import com.zxf.springmvc.excel.ExcelExportService;
import com.zxf.springmvc.excel.ExcelView;
import com.zxf.springmvc.model.User;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

/**
 * @ClassName: ExcelController
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2018/12/1 10:12
 * @Version: 1.0
 **/
@Controller
@RequestMapping("/excel")
public class ExcelController {

    private ExcelExportService getExportService() {
        return ((model, workbook, request, response) -> {
            /*创建标签页*/
            Sheet sheet = workbook.createSheet("统计结果");
            /*设置标题栏风格*/
            CellStyle style = workbook.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.DASH_DOT_DOT);
            style.setBorderRight(BorderStyle.DASH_DOT_DOT);
            style.setBorderTop(BorderStyle.THIN);
            /*设置标题*/
            Row rowOne = sheet.createRow(0);
            rowOne.createCell(0).setCellValue("id");
            rowOne.createCell(1).setCellValue("userName");
            rowOne.createCell(2).setCellValue("note");
            rowOne.getCell(0).setCellStyle(style);
            rowOne.getCell(1).setCellStyle(style);
            rowOne.getCell(2).setCellStyle(style);
            /*填充数据*/
            User user = (User)model.get("user");
            Row rowTwo = sheet.createRow(1);
            rowTwo.createCell(0).setCellValue(11);
            rowTwo.createCell(1).setCellValue(user.getUserName());
            rowTwo.createCell(2).setCellValue(user.getNote());
            return;
        });
    }

    /*
    获取Excel 2008文档作为返回结果，返回的view本质上要是AbstractXlsxView类的子类，
    http://localhost:8080/excel/export?userName=myname&note=hereisnote
    */
    @GetMapping("/export")
    public ModelAndView export(String userName, String note) {
        User user = new User(userName, note);
        View view = new ExcelView(getExportService());
        ModelAndView mv = new ModelAndView();
        mv.setView(view);
        mv.addObject("user", user);
        return mv;
    }
}
