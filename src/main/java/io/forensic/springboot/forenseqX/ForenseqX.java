package io.forensic.springboot.forenseqX;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

// Name In JSON Object Spring brings from methods' name

@Entity
@Table(name = "ForenseqX")
public class ForenseqX {

	@EmbeddedId
	private ForenseqXIdentity forenseqXIdentity;

	@Column(name = "_Type")
	private String type;

	@Column(name = "Genotype")
	private String genotype;

	@Column(name = "Sequence")
	private String sequence;
	
	private String alignment;

	public ForenseqX() {

	}

	public ForenseqX(ForenseqXIdentity forenseqXIdentity, String type, String genotype, String sequence, String alignment) {
		super();
		this.forenseqXIdentity = forenseqXIdentity;
		this.type = type;
		this.genotype = genotype;
		this.sequence = sequence;
		this.alignment = alignment;
	}

	public ForenseqXIdentity getForenseqIdentity() {
		return forenseqXIdentity;
	}

	public void setForenseqIdentity(ForenseqXIdentity forenseqXIdentity) {
		this.forenseqXIdentity = forenseqXIdentity;
	}

	public String getAlignment() {
		return alignment;
	}

	public void setAlignment(String alignment) {
		this.alignment = alignment;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGenotype() {
		return genotype;
	}

	public void setGenotype(String genotype) {
		this.genotype = genotype;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

}
