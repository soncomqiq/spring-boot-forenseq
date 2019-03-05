package io.forensic.springboot.forenseqY;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

// Name In JSON Object Spring brings from methods' name

@Entity
@Table(name = "ForenseqY")
public class ForenseqY {

	@EmbeddedId
	private ForenseqYIdentity forenseqYIdentity;

	@Column(name = "_Type")
	private String type;

	@Column(name = "Genotype")
	private String genotype;

	@Column(name = "Sequence")
	private String sequence;

	public ForenseqY() {

	}

	public ForenseqY(ForenseqYIdentity forenseqYIdentity, String type, String genotype, String sequence) {
		super();
		this.forenseqYIdentity = forenseqYIdentity;
		this.type = type;
		this.genotype = genotype;
		this.sequence = sequence;
	}

	public ForenseqYIdentity getForenseqIdentity() {
		return forenseqYIdentity;
	}

	public void setForenseqIdentity(ForenseqYIdentity forenseqYIdentity) {
		this.forenseqYIdentity = forenseqYIdentity;
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
