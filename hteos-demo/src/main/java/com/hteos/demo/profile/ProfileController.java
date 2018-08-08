package com.hteos.demo.profile;

import com.hteos.biz.user.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

/**
 * @author LIQIU
 * @date 2018-6-25
 **/
@Controller
public class ProfileController {

    @RequestMapping("/profile")
    public String profile(@SessionAttribute(name = "ACCOUNT", required = false) User user, Model model) {
        if (user != null) {
            model.addAttribute("ACCOUNT", user);
            return "profile/account";
        } else {
            return "profile/login";
        }
    }
}
