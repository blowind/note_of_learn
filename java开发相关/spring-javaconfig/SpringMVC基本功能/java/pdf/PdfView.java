package com.zxf.springmvc.pdf;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.web.servlet.view.document.AbstractPdfView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @ClassName: PdfView
 * @Description: Spring提供的抽象父类AbstractPdfView已经生成PDF文档，与req/resp相关操作封装好了，实现接口方法仅仅是为了让用户定制Document里面的内容
 * @Author: ZhangXuefeng
 * @Date: 2018/11/30 17:23
 * @Version: 1.0
 **/
public class PdfView extends AbstractPdfView {
    /*定义一个接口作为提供PDF定制化内容的代理，每个业务具体生成的PDF内容由实现类具体操作*/
    private PdfExportService pdfExportService;

    /*注入具体的实现类*/
    public PdfView(PdfExportService pdfExportService) {
        this.pdfExportService = pdfExportService;
    }

    /*抽象出来的业务相关方法，主要是往document写入具体的业务相关内容
    * 其中model是之前controller中已放入模型中的各种对象，document是已经生成好的PDF文档对象*/
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        pdfExportService.make(model, document, writer, request, response);
    }

}
