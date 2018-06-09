package com.zxf.bootsecurity.controller;

import com.zxf.bootsecurity.model.Msg;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {
    @RequestMapping(value = "/")
    public String defaultIndex(HttpServletRequest request, Model model) {
        System.out.println(request.getRemoteUser());
        Msg msg = new Msg("走默认/请求", "/请求内容", "额外信息，只对管理员显示");
        model.addAttribute("msg", msg);
        return "index";
    }

    @RequestMapping(value = "/index")
    public String index(Model model) {
        Msg msg = new Msg("走index请求", "index请求内容", "index请求的额外信息");
        model.addAttribute("msg", msg);
        return "index";
    }
}
