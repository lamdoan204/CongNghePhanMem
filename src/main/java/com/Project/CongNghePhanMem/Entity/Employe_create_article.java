package com.Project.CongNghePhanMem.Entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name= "Employe_create_article", schema= "dbo")
public class Employe_create_article {
    @EmbeddedId
    private Employe_create_article_id id;

    public Employe_create_article_id getId() {
        return id;
    }

    public void setId(Employe_create_article_id id) {
        this.id = id;
    }

    public Employe_create_article(Employe_create_article_id id) {
        this.id = id;
    }
}
