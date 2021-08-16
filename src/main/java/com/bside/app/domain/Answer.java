package com.bside.app.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Answer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long surveyId;
    private Long questionId;
    private Long targetSeq;
    private String comment;

    @CreatedDate
    private LocalDateTime date;
}
