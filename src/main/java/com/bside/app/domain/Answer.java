package com.bside.app.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter
@Table(name = "answer")
@NoArgsConstructor
public class Answer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "survey_id")
    private Long surveyId;
    @Column(name = "question_id")
    private Long questionId;
    @Column(name = "target_seq")
    private Long targetSeq;
    @Column(name = "comment")
    private String comment;
    @Column(name = "date")
    private String date;
}
