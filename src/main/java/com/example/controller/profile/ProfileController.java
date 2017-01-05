package com.example.controller.profile;

import com.example.date.EULocalDateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

/**
 * Created by Janusz on 04.01.2017.
 */
@Controller
public class ProfileController {
    @RequestMapping("/profile")
    public String displayProfile(ProfileForm profileForm) {
        return "profile/profilePage";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST, params = {"save"})
    public String saveProfile(@Valid ProfileForm profileForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "profile/profilePage";
        }
        return "redirect:/profile";
    }

    @ModelAttribute("dateFormat")
    public String localFormat(Locale locale) {
        return EULocalDateFormatter.getPattern(locale);
    }

    @RequestMapping(value = "/profile", params = {"addTaste"})
    public String addRow(ProfileForm profileForm){
        profileForm.getTastes().add(null);
        return "profile/profilePage";
    }
    @RequestMapping(value = "/profile", params = {"removeTaste"})
    public String removeRow(ProfileForm profileForm, HttpServletRequest request){
        int rowId=Integer.valueOf(request.getParameter("removeTaste"));
        profileForm.getTastes().remove(rowId);
        return "profile/profilePage";
    }

}
