package com.Project.CongNghePhanMem.Entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	
	@ManyToOne
    @JoinColumn(name = "user_id", unique = false)
    private User manager;
	
	@OneToMany
    private List<User> employee; 
	
	@OneToOne
	@JoinColumn(name = "brandId")
	private Brand brand;	

	public void addEmployee(User employe) {
        if (employee != null) {
            employee.add(employe);
        }
    }
	public void deleteEmployee(User employe){
		if(employee != null){
			employee.remove(employe);
		}
	}
	
	public List<User> getEmployee(){
		return this.employee;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getManager() {
		return manager;
	}
	public void setManager(User manager) {
		this.manager = manager;
	}
	public void setEmployee(List<User> employee) {
		this.employee = employee;
	}
	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	
	
	
}
