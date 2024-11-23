	package com.Project.CongNghePhanMem.Entity;
	
	import jakarta.persistence.Entity;
	import jakarta.persistence.GeneratedValue;
	import jakarta.persistence.GenerationType;
	import jakarta.persistence.Id;
	import jakarta.persistence.Table;
	import lombok.Data;
	
	@Entity
	@Data
	@Table(name = "Users")
	public class User {
	
	    @Id
	    @GeneratedValue(strategy= GenerationType.IDENTITY)
	    private int userId;
		private String email;
	    private String phone;
	    private String fullName;
	    private String address;
		private String password;
	    private String role;
	    
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
		
		public User(int userId, String email, String phone, String fullName, String address, String password) {
			super();
			this.userId = userId;
			this.email = email;
			this.phone = phone;
			this.fullName = fullName;
			this.address = address;
			this.password = password;
		}
		public User() {
			super();
		}
	    
	   
	}
