package com.bside.app.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QuestionAnswerContorller {

    @GetMapping("/")
    public String home(){
        return "intro";
    }

    @GetMapping("/question")
    public String question() { return "question01"; }
}
