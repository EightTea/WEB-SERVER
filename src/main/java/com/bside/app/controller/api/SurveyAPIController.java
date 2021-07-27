package com.bside.app.controller.api;

import com.bside.app.domain.Answer;
import com.bside.app.domain.Question;
import com.bside.app.domain.Survey;
import com.bside.app.response.ApiResponse;
import com.bside.app.service.AnswerService;
import com.bside.app.service.AuthService;
import com.bside.app.service.QuestionService;
import com.bside.app.service.SurveyService;
import com.bside.app.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import org.json.*;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/survey")
public class SurveyAPIController {

    private final SurveyService surveyService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final AuthService authService;
    private final S3Uploader s3Uploader;

//    @Value("${config.baseDomain}")
    String baseDomain = "/";

    @PostMapping("")
    public ApiResponse CreateSurvey( @ModelAttribute SurveyForm surveyForm ) throws Exception {

        String surveyTitle = surveyForm.getTitle();
        String surveyContent = surveyForm.getContent();

        List<Question> questionList = new ArrayList<Question>();
        SurveyForm.QuestionRequest[] questionRequests = surveyForm.getQuestion();
        for( int i = 0 ; i < questionRequests.length ; i ++ ){

            SurveyForm.QuestionRequest questionRequest = questionRequests[i];

            Question question = new Question();
            question.setContent( questionRequest.getContent() );
            question.setNo( (i+1) );

            // 이미지 파일 처리
            MultipartFile mf = questionRequest.getFile();
            if( mf != null ) {
                String s3Url = s3Uploader.upload(mf, "static/upload");
                question.setFileUrl(s3Url);
            }
            questionList.add(question);
        }

        Long userId = 2L; // TODO token 을 통해서 userId 가져와야함
        Long surveyId = surveyService.survey(surveyTitle, surveyContent, userId, questionList );

        JSONObject data = new JSONObject();
        data.append("survey_id" , surveyId.longValue() );
        data.append("qrcode_url",baseDomain + "/" + surveyId +"/view");

        return new ApiResponse(200, "로그인 성공", data.toString());
    }
/*
    @PostMapping("/{survey}/answer")
    public ApiResponse CreateAnswer ( @PathVariable("survey") int surveyNo , @RequestBody AnswerForm answerForm){



        return new ApiResponse(200, "답변 성공", null );
    }


 */

    @GetMapping("")
    public ApiResponse getSurveyList (){
        String check = SecurityContextHolder.getContext().getAuthentication().toString();
        System.out.println("check = " + check);
        List<Survey> surveyList = surveyService.findServeys(1L);

        JSONArray allData = new JSONArray();
        surveyList.forEach( survey -> {
            JSONObject data = new JSONObject();
            data.put("survey_id", survey.getId());
            data.put("start_data", survey.getStartDate());
            data.put("end_data", survey.getEndDate());
            data.put("status", survey.getStatus());
            data.put("title", survey.getTitle());
            data.put("content", survey.getContent());
            allData.put(data);
        });

        return new ApiResponse(200, "성공", allData.toString());
    }

    @GetMapping("/{survey_id}")
    public ApiResponse getSurveyDetails (@PathVariable("survey_id") Long surveyId){

        String qrUrl = surveyService.findQrUrl(surveyId);

        List<Question> questionList = questionService.findQuestions(surveyId);
        JSONArray questions = new JSONArray();

        questionList.forEach(question -> {
            JSONObject data = new JSONObject();
            data.put("question_id", question.getId());
            data.put("no", question.getNo());
            data.put("content", question.getContent());
            data.put("image", question.getFileUrl());
            questions.put(data);
        });

        JSONObject allData = new JSONObject();
        allData.put("survey_id", surveyId);
        allData.put("qrcode_url", qrUrl);
        allData.put("question", questions);

        return new ApiResponse(200, "성공", allData.toString());
    }

    /**
     * 설문조사 답변 리스트
     * @param pageable - 현재 10개 기준으로 작성중
     * @param surveyId
     * @param questionId
     * @return
     */

    @GetMapping("/{survey_id}/{question_id}")
    public ApiResponse getSurveyDetails (@PageableDefault(size = 10) Pageable pageable, @PathVariable("survey_id") Long surveyId, @PathVariable("question_id") Long questionId){
        Page<Answer> answerList = answerService.findAnswers(surveyId, questionId, pageable);
        JSONObject data = new JSONObject();
        data.put("answer", answerList.getContent());
        data.put("is_more", answerList.getTotalPages() > pageable.getPageNumber());

        return new ApiResponse(200, "성공", data.toString());
    }
}
