package com.Project.CongNghePhanMem.Entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name= "Employe_belong_to_department", schema="dbo")

public class Employe_belong_to_department {

    @EmbeddedId
    private Employe_belong_to_department_id id;

    public Employe_belong_to_department_id getId() {
        return id;
    }

    public void setId(Employe_belong_to_department_id id) {
        this.id = id;
    }

    public Employe_belong_to_department(Employe_belong_to_department_id id) {
        this.id = id;
    }
    
    
}
