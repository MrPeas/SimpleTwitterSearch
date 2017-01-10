package com.example.controller;

import com.example.controller.profile.UserProfileSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Janusz on 03.01.2017.
 */
@Controller
public class HomeController {

    UserProfileSession userProfileSession;

    @Autowired
    public HomeController(UserProfileSession userProfileSession) {
        this.userProfileSession = userProfileSession;
    }

    @RequestMapping("/")
    public String welcome() {
        if (userProfileSession.isTastes())
            return "redirect:/search/mixed;keywords=" + String.join(",", userProfileSession.toForm().getTastes());
        return "redirect:/profile";
    }

}
