package com.zxf.springmvc.excel;

import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @InterfaceName: ExcelExportService
 * @Description: 统一定义一个Excel文档生成接口，所有实现本接口的类都可以提供定制化的Excel文档生成服务
 * @Author: ZhangXuefeng
 * @Date: 2018/12/1 10:05
 * @Version: 1.0
 **/
public interface ExcelExportService {
    void build(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception ;
}
