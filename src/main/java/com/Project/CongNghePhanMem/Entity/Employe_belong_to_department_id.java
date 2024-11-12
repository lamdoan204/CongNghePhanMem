package com.Project.CongNghePhanMem.Entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Employe_belong_to_department_id implements Serializable{
    @Column(name = "employee_id")
    private int empployee_id;
    @Column(name = "department_id")
    private int department_id;
    public int getEmpployee_id() {
        return empployee_id;
    }
    public void setEmpployee_id(int empployee_id) {
        this.empployee_id = empployee_id;
    }
    public int getDepartment_id() {
        return department_id;
    }
    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }
    public Employe_belong_to_department_id(int empployee_id, int department_id) {
        this.empployee_id = empployee_id;
        this.department_id = department_id;
    }
    
}
