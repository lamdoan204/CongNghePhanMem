package com.Project.CongNghePhanMem.Entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Users")
public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String email;
    private String phone;
    private String fullName;
    private String address;
    private String password;
    private String role;
    private boolean accounNonLocked;
    private String image;

    @ManyToOne
    @JoinColumn(name = "department_id") // Tên cột khóa ngoại
    private Department department;
    
    @OneToMany(mappedBy = "manager") 
    private List<Department> managedDepartments;

    @Column(nullable = false, columnDefinition = "bit default 0")
    private boolean enabled = false;

    private String verificationCode;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public boolean isAccounNonLocked() {
        return accounNonLocked;
    }

    public void setAccounNonLocked(boolean accounNonLocked) {
        this.accounNonLocked = accounNonLocked;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public User(int userId, String email, String phone, String fullName, String address, String password,String image) {
        super();
        this.userId = userId;
        this.email = email;
        this.phone = phone;
        this.fullName = fullName;
        this.address = address;
        this.password = password;
        this.image = image;
    }

    public User() {
        super();
    }

}
