package io.forensic.springboot.CEData;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CE_Data")
public class CEData {

	@EmbeddedId
	private CEDataIdentity id;
	
	@Column(name = "_Type")
	private String type;

	public CEData() {
		
	}

	public CEData(CEDataIdentity id,String type) {
		super();
		this.id = id;
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public CEDataIdentity getId() {
		return id;
	}

	public void setId(CEDataIdentity id) {
		this.id = id;
	}
	
}
