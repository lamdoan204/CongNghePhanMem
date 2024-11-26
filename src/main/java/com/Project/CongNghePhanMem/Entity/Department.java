package com.Project.CongNghePhanMem.Entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name ="departments")
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@OneToOne
	@JoinColumn(name = "userId")
	private User manager;
	
	@OneToMany
	private List<User> employee; 
	
	@JoinColumn(name = "brandId")
	private int brandId;	

	public void addEmployee(User employe) {
        if (employee != null) {
            employee.add(employe);
        }
    }
	public int getBrandId(){
		return this.brandId;
	}
	
}
