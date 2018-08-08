package com.hteos.admin.web;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

/**
 * @author LIQIU
 * @date 2018-6-25
 **/
@Controller
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "/index.html";
    }
}
