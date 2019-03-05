package io.forensic.springboot.person;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Person")
public class Person {

	@EmbeddedId
	private PersonIdentity id;
	
	private String PID;
	private String name;
	private String surname;
	private String Gender;
	private String Region;
	private int Age;
	private String Race;
	
	public Person() {
		
	}
	
	public Person(PersonIdentity id, String pID, String name, String surname, String gender, String region, int age,
			String race) {
		super();
		this.id = id;
		this.PID = pID;
		this.name = name;
		this.surname = surname;
		this.Gender = gender;
		this.Region = region;
		this.Age = age;
		this.Race = race;
	}

	public PersonIdentity getId() {
		return id;
	}

	public void setId(PersonIdentity id) {
		this.id = id;
	}

	public String getPID() {
		return PID;
	}

	public void setPID(String pID) {
		PID = pID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

	public String getRegion() {
		return Region;
	}

	public void setRegion(String region) {
		Region = region;
	}

	public int getAge() {
		return Age;
	}

	public void setAge(int age) {
		Age = age;
	}

	public String getRace() {
		return Race;
	}

	public void setRace(String race) {
		Race = race;
	}
	
}
