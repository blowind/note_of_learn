package com.zxf.springmvc.excel;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @ClassName: ExcelView
 * @Description: Spring提供的抽象父类AbstractXlsxView已经生成Excel文档，与req/resp相关操作封装好了，实现接口方法仅仅是为了让用户定制Workbook里面的内容
 * @Author: ZhangXuefeng
 * @Date: 2018/12/1 9:56
 * @Version: 1.0
 **/
public class ExcelView extends AbstractXlsxView {
    private ExcelExportService excelExportService;

    public ExcelView(ExcelExportService service) {
        this.excelExportService = service;
    }

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        excelExportService.build(model, workbook, request, response);
    }
}
