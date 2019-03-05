package io.forensic.springboot.forenseq;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

// Name In JSON Object Spring brings from methods' name

@Entity
@Table(name = "forenseq")
public class Forenseq {

	@EmbeddedId
	private ForenseqIdentity forenseqIdentity;

	@Column(name = "_Type")
	private String type;

	@Column(name = "Genotype")
	private String genotype;

	@Column(name = "Sequence")
	private String sequence;

	public Forenseq() {

	}

	public Forenseq(ForenseqIdentity forenseqIdentity, String type, String genotype, String sequence) {
		super();
		this.forenseqIdentity = forenseqIdentity;
		this.type = type;
		this.genotype = genotype;
		this.sequence = sequence;
	}

	public ForenseqIdentity getForenseqIdentity() {
		return forenseqIdentity;
	}

	public void setForenseqIdentity(ForenseqIdentity forenseqIdentity) {
		this.forenseqIdentity = forenseqIdentity;
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
