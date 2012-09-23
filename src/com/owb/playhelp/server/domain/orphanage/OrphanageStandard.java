package com.owb.playhelp.server.domain.orphanage;

import java.io.Serializable;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.owb.playhelp.server.domain.Standard;

@SuppressWarnings("serial") 
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class OrphanageStandard implements Serializable  {

	private float health;
	private float education;
	private float nutrition;
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String id;   

   @SuppressWarnings("unused")
   @Persistent(mappedBy = "status")
   private Orphanage orphanage;
   
   public OrphanageStandard(){
   }
   public OrphanageStandard(float health,float education,float nutrition){
		this.health = health;
		this.education = education;
		this.nutrition = nutrition;
		checkRange();
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
