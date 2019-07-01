package com.danielkaras.smartlivingplan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomePageController {

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView showHomePage() {
        return new ModelAndView("home");
    }
}
