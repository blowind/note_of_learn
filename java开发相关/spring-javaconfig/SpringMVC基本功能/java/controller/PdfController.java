package com.zxf.springmvc.controller;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.zxf.springmvc.model.User;
import com.zxf.springmvc.pdf.PdfExportService;
import com.zxf.springmvc.pdf.PdfView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import java.awt.*;


/**
 * @ClassName: PdfController
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2018/11/30 17:29
 * @Version: 1.0
 **/
@Controller
@RequestMapping("/pdf")
public class PdfController {

    /*提供具体的PdfExportService实现类*/
    private PdfExportService getExportService() {
        /*使用lambda表达式，本质上实现了匿名类的效果，即提供了PdfExportService的基本功能
        * 此处实现较简单，仅使用了document和model两个变量的内容*/
        return ((model, document, writer, request, response) -> {
            try{
                /*A4纸张大小*/
                document.setPageSize(PageSize.A4);
                /*标题*/
                document.addTitle("用户信息");
                /*换行*/
                document.add(new Chunk("\n"));
                /*表格，3列*/
                PdfPTable table = new PdfPTable(3);

                /*字体定义为蓝色加粗*/
                Font f8 = new Font();
                f8.setColor(Color.BLUE);
                f8.setStyle(Font.BOLD);

                /*单元格*/
                PdfPCell cell = null;
                /*标题*/
                cell = new PdfPCell(new Paragraph("id", f8));
                /*居中对齐*/
                cell.setHorizontalAlignment(1);
                table.addCell(cell);

                cell = new PdfPCell(new Paragraph("user_name", f8));
                cell.setHorizontalAlignment(1);
                table.addCell(cell);

                cell = new PdfPCell(new Paragraph("note", f8));
                cell.setHorizontalAlignment(1);
                table.addCell(cell);

                User user = (User)model.get("user");

                document.add(new Chunk("\n"));
                cell = new PdfPCell(new Paragraph(String.valueOf(1)));
                table.addCell(cell);
                cell = new PdfPCell(new Paragraph(user.getUserName()));
                table.addCell(cell);
                cell = new PdfPCell(new Paragraph(user.getNote()));
                table.addCell(cell);

                document.add(table);
            }catch (DocumentException de) {
                de.printStackTrace();
            }
        });
    }
    /*
    获取PDF文档作为返回结果，返回的view本质上要是AbstractPdfView类的子类，
    通过实现其buildPdfDocument方法，将文档的具体内容放入Document参数中
    http://localhost:8080/pdf/export?userName=myname&note=hereisnote
    */

    @GetMapping("/export")
    public ModelAndView exportPdf(String userName, String note) {
        User user = new User(userName, note);
        View view = new PdfView(getExportService());
        ModelAndView mv = new ModelAndView();
        mv.setView(view);
        mv.addObject("user", user);
        return mv;
    }

}
