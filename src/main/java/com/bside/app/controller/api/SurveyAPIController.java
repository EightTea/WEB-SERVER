package com.bside.app.controller.api;

import com.bside.app.domain.JwtToken;
import com.bside.app.domain.Question;
import com.bside.app.domain.Survey;
import com.bside.app.response.ApiResponse;
import com.bside.app.service.SurveyService;
import com.bside.app.util.S3Uploader;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


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
    private final S3Uploader s3Uploader;

    @Value("${config.baseDomain}")
    String baseDomain;

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
                String s3Url = s3Uploader.upload(mf, "static" + File.pathSeparator + "upload");
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

    @PostMapping("/{survey}/answer")
    public ApiResponse CreateAnswer ( @PathVariable("survey") int surveyNo , @RequestBody AnswerForm answerForm){



        return new ApiResponse(200, "답변 성공", null );
    }

}
