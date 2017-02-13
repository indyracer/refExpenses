package org.launchcode.refExpenses.models;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public abstract class AbstractEntity {
	
	private int uid;
	
	@Id
	@GeneratedValue
	@NotNull
	@Column(name = "uid", unique = true)
	public int getUid(){
		return uid;
	}
	
	protected void setUid(int uid){
		this.uid = uid;
	}
}
