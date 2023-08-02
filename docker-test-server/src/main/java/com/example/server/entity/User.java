package com.example.server.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter						
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity						
@Table(name="user_tb")
public class User {

	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY) 	
	@Column(name="user_id")
	private Long id;	
	
	@Column(nullable = false, length = 100, unique = true)
	private String username;	

	@Column(nullable = false, length = 100)
	private String password;
	
	private String roles;	
	
	
	public List<String> getRoleList(){
		if(this.roles.length() > 0) {
			return Arrays.asList(this.roles.split(","));
		}
		return new ArrayList<>();
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", roles=" + roles + "]";
	}
	

}