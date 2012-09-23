package com.owb.playhelp.server.domain;

import java.io.Serializable;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.owb.playhelp.shared.StandardInfo;
 
public class Standard implements Serializable  {

	private float health;
	private float education;
	private float nutrition;
	   
	public Standard(){
	}
	public Standard(float health,float education,float nutrition){
		this.health = health;
		this.education = education;
		this.nutrition = nutrition;
		checkRange();
	}
	
	public StandardInfo toInfo(){
		StandardInfo resstd = new StandardInfo();
		
		resstd.setEducation(this.education);
		resstd.setHealth(this.health);
		resstd.setNutrition(this.nutrition);
		
		return resstd;
	}
	
	public void fromInfo(StandardInfo std){		
		this.setEducation(std.getEducation());
		this.setHealth(std.getHealth());
		this.setNutrition(std.getNutrition());
	}
	
	private void checkRange(){
		if (this.health < 0) this.health=0;
		if (this.health > 100) this.health=100;
		if (this.education < 0) this.education=0;
		if (this.education > 100) this.education=100;
		if (this.nutrition < 0) this.nutrition=0;
		if (this.nutrition > 100) this.nutrition=100;
	}
	
	public float getHealth(){
		return health;
	}
	public float getEducation(){
		return education;
	}
	public float getNutrition(){
		return nutrition;
	}

	public void setHealth(float health){
		this.health = health;
		if (health < 0) this.health = 0;
		if (health > 100) this.health = 100;
	}
	public void setEducation(float education){
		this.education = education;
		if (education < 0) this.education = 0;
		if (education > 100) this.education = 100;
	}
	public void setNutrition(float nutrition){
		this.nutrition = nutrition;
		if (nutrition < 0) this.nutrition = 0;
		if (nutrition > 100) this.nutrition = 100;
	}
	
	public void addHealth(float health){
		if (health < 0) return;
		this.health = this.health + health;
		if (this.health > 100) this.health = 100;
	}
	public void addEducation(float education){
		if (education < 0) return;
		this.education = this.education + education;
		if (this.education > 100) this.education = 100;
	}
	public void addNutrition(float nutrition){
		if (nutrition < 0) return;
		this.nutrition = this.nutrition + nutrition;
		if (this.nutrition > 100) this.nutrition = 100;
	}
	public void delHealth(float health){
		if (health < 0) return;
		this.health = this.health - health;
		if (this.health < 0) this.health = 0;
	}
	public void delEducation(float education){
		if (education < 0) return;
		this.education = this.education - education;
		if (this.education < 0) this.education = 0;
	}
	public void delNutrition(float nutrition){
		if (nutrition < 0) return;
		this.nutrition = this.nutrition - nutrition;
		if (this.nutrition < 0) this.nutrition = 0;
	}
	
}
