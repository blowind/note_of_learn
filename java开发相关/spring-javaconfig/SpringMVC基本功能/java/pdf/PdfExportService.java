package com.zxf.springmvc.pdf;

import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Document;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @ClassName: PdfExportService
 * @Description:  定义一个接口，所有实现本接口的类都可以提供定制化的PDF文档生成服务
 * @Author: ZhangXuefeng
 * @Date: 2018/11/30 17:20
 * @Version: 1.0
 **/
public interface PdfExportService {
    void make(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response);
}
