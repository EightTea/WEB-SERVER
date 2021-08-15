package com.bside.app.controller.web;

import com.bside.app.controller.api.AnswerForm;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/survey")
public class SurveyWebContoller {

    private final QuestionService questionService;
    private final AnswerService answerService;

    @RequestMapping("/{surveyId}/view")
    public String surveyView (){
        return "index";
    }

    @GetMapping(value = "/{surveyId}/answer")
    public String answer (@PathVariable Long surveyId, Model model){

        List<Question> questionList = questionService.findQuestions(surveyId);

        model.addAttribute("surveyId",surveyId);
        model.addAttribute("questionList",questionList);
        model.addAttribute("answerFormList", new AnswerForm());

        return "answer";
    }

    @PostMapping(value = "/{surveyId}/answer")
    public String insertAnswer (@PathVariable Long surveyId, HttpServletRequest request){

        String[] questionIdStrArray = request.getParameterValues("questionId");
        long[] questionIds = Arrays.stream(questionIdStrArray).mapToLong(Long::parseLong).toArray();
        String[] questionComments = request.getParameterValues("comment");

        List<Answer> answers = new ArrayList<>();
        for( int i = 0 ; i < questionIds.length ; i ++  ){

            Answer answer = new Answer();
            answer.setSurveyId(surveyId);
            answer.setQuestionId(questionIds[i]);
            answer.setComment(questionComments[i]);
            answers.add(answer);
        }
        answerService.answers(answers);

        return "exit";
    }
}
