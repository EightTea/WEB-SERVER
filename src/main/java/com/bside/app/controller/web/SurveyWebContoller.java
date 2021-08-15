package com.bside.app.controller.web;

import com.bside.app.domain.Answer;
import com.bside.app.domain.Question;
import com.bside.app.domain.Survey;
import com.bside.app.repository.question.QuestionRepository;
import com.bside.app.service.AnswerService;
import com.bside.app.service.QuestionService;
import com.bside.app.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/survey")
public class SurveyWebContoller {

    private final QuestionService questionService;

    @RequestMapping("/{surveyId}/view")
    public String surveyView (){
        return "index";
    }

    @RequestMapping("/{surveyId}/answer")
    public String answer (@PathVariable Long surveyId, Model model){

        List<Question> questionList = questionService.findQuestions(surveyId);

        model.addAttribute("questionList",questionList);
        return "answer";
    }
}
