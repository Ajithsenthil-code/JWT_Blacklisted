package com.example.jwt.entity;

import java.util.Date;

import jakarta.persistence.*;
import jakarta.persistence.Table;

@Entity
@Table(name="blacklisted_tokens")
public class BlacklistedToken {
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY )
	private Long id;
	
	
	@Column(length = 1000)
	private String token;
	
	private Date blacklistedAt;
	
	public BlacklistedToken() {}
	
	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getToken() {
    	return token;
    }
    
    public void setToken(String token) {
    	this.token=token;
    }
    public Date getBlacklistedAt() {
    	return blacklistedAt;
    }
    
    public void setBlacklistedAt(Date blacklistedAt) {
    	this.blacklistedAt=blacklistedAt;
    }
   

}
