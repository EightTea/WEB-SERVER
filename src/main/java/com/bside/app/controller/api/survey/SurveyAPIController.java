package com.bside.app.controller.api.survey;

import com.bside.app.domain.Answer;
import com.bside.app.domain.Question;
import com.bside.app.domain.Survey;
import com.bside.app.response.ApiResponse;
import com.bside.app.service.AnswerService;
import com.bside.app.service.QuestionService;
import com.bside.app.service.SurveyService;
import com.bside.app.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/survey")
public class SurveyAPIController {

    private final SurveyService surveyService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    private final S3Uploader s3Uploader;

    @Value("${config.baseDomain}")
    String baseDomain;

    @PostMapping("")
    public ApiResponse CreateSurvey( @ModelAttribute SurveyForm surveyForm ) throws Exception {

        Long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());

        String surveyTitle = surveyForm.getTitle();
        String surveyContent = surveyForm.getContent();

        List<Question> questionList = new ArrayList<Question>();

        ArrayList<String> questionContentList = surveyForm.getQuestionContentList();
        ArrayList<MultipartFile> questionFileList = surveyForm.getQuestionFileList();

        if ( questionContentList != null ) {
            for (int i = 0; i < questionContentList.size(); i++) {

                Question question = new Question();
                question.setContent(questionContentList.get(i));
                question.setNo(i + 1);

                // ????????? ?????? ??????
                if( questionFileList.get(i) != null ) {
                    MultipartFile mf = questionFileList.get(i);
                    if (mf != null && !mf.isEmpty()) {
                        String s3Url = s3Uploader.upload(mf, "static/upload");
                        question.setFileUrl(s3Url);
                    }
                }
                questionList.add(question);
            }
        }

        Long surveyId = surveyService.survey(surveyTitle, surveyContent, userId, questionList );

        Map map = new HashMap();
        map.put("survey_id" , surveyId.longValue() );
        map.put("qrcode_url",baseDomain + "/" + surveyId +"/view");

        return new ApiResponse(200, "????????? ?????? ??????", map);
    }

    @PostMapping("/{survey}/answer")
    public ApiResponse CreateAnswer (@PathVariable("survey") Long surveyNo , @RequestBody List<AnswerForm> answerForms){

        List<Answer> answers = new ArrayList<Answer>();

        for( int i = 0 ; i < answerForms.size() ; i ++ ){
            AnswerForm answerForm = answerForms.get(i);

            Answer answer = new Answer();

            answer.setSurveyId(surveyNo);
            answer.setQuestionId(answerForm.getQuestionId());
            answer.setComment(answerForm.getComment());

            answers.add(answer);
        }

        Long suerveryId = answerService.answers(answers);

        return new ApiResponse(200, "?????? ??????", null );
    }


    @GetMapping("")
    public ApiResponse getSurveyList (){
        Long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        log.info("/survey : " + userId);
        List<Survey> surveyList = surveyService.findServeys(userId);

        List<Object> surveyData = new ArrayList<>();
        surveyList.forEach( survey -> {
            Map<String, Object> data = new HashMap<>();
            long answerCnt = answerService.countAnswers(survey.getId());
            data.put("survey_id", survey.getId());
            data.put("start_data", survey.getStartDate());
            data.put("end_data", survey.getEndDate());
            data.put("status", survey.getStatus());
            data.put("title", survey.getTitle());
            data.put("answer_cnt", answerCnt);

            surveyData.add(data);
        });

        Map<String, Object> allData = new HashMap<>();
        allData.put("survey_list", surveyData);

        return new ApiResponse(200, "??????", allData);
    }

    @GetMapping("/{survey_id}")
    public ApiResponse getSurveyDetails (@PathVariable("survey_id") Long surveyId){

        String qrUrl = surveyService.findQrUrl(surveyId);

        List<Question> questionList = questionService.findQuestions(surveyId);
        List<Object> questions = new ArrayList<>();
        log.info("????????? question ????????? : " + questionList.size());

        questionList.forEach(question -> {
            Map<String, Object> data = new HashMap<>();
            data.put("question_id", question.getId());
            data.put("no", question.getNo());
            data.put("content", question.getContent());
            data.put("image", question.getFileUrl());
            questions.add(data);
        });

        Map<String, Object> allData = new HashMap<>();
        allData.put("survey_id", surveyId);
        allData.put("qrcode_url", qrUrl);
        allData.put("question", questions);

        return new ApiResponse(200, "??????", allData);
    }

    /**
     * ???????????? ?????? ?????????
     * @param pageable - ?????? 10??? ???????????? ?????????
     * @param surveyId
     * @param questionId
     * @return
     */

    @GetMapping("/{survey_id}/{question_id}")
    public ApiResponse getSurveyDetails (@PageableDefault(size = 10) Pageable pageable, @PathVariable("survey_id") Long surveyId, @PathVariable("question_id") Long questionId){
        Page<Answer> answerList = answerService.findAnswers(surveyId, questionId, pageable);
        Map<String, Object> data = new HashMap<>();
        data.put("answer", answerList.getContent());
        data.put("is_more", answerList.getTotalPages()-1 > pageable.getPageNumber());

        return new ApiResponse(200, "??????", data);
    }

    @PutMapping("/{survey_id}")
    public ApiResponse changeSurveyStatus(@PathVariable("survey_id") Long surveyId, @RequestBody UpdateSurveyForm surveyForm){
        log.info("change Status : " + surveyForm.getStatus());
        Integer integer = surveyService.updateSurveyStatus(surveyId, surveyForm.getStatus());
        log.info("updateResult : "+integer);
        return new ApiResponse(200, "??????", null);
    }
}
