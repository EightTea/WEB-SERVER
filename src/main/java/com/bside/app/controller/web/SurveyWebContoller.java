package com.bside.app.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/survey")
public class SurveyWebContoller {

    @RequestMapping("/{surveyId}/view")
    public String surveyView (){
        //TODO
        return "index";
    }
}
