package com.bside.app.domain;

public class ChildQuestion {

    private Long id;
    private Long parent_questions_id;
    private int parent_question_num;
    private String content;
    private String image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParent_questions_id() {
        return parent_questions_id;
    }

    public void setParent_questions_id(Long parent_questions_id) {
        this.parent_questions_id = parent_questions_id;
    }

    public int getParent_question_num() {
        return parent_question_num;
    }

    public void setParent_question_num(int parent_question_num) {
        this.parent_question_num = parent_question_num;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
