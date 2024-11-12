package com.Project.CongNghePhanMem.Entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class Employe_create_article_id implements Serializable{

    private int employee_id;
    private int article_id;
    public int getEmployee_id() {
        return employee_id;
    }
    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }
    public int getArticle_id() {
        return article_id;
    }
    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }
    public Employe_create_article_id(int employee_id, int article_id) {
        this.employee_id = employee_id;
        this.article_id = article_id;
    }

    
}
