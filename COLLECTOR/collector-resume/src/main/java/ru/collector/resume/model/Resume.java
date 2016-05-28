package ru.collector.resume.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "RESUME")
public class Resume implements Serializable {

	private static final long serialVersionUID = 1L;

    @JsonProperty("id")
	@Id
    @Column(name = "ID")
    private String id;

    @JsonProperty("age")
    @Column(name = "AGE")
    private Long age;
    
    @JsonProperty("header")
    @Column(name = "PROFESSION")
    private String profession;
    
    @JsonProperty("experience")
    @Column(name = "EXPERIENCE")
    private String experience;
    
    @JsonProperty("skills")
    @Transient
    private String skills;

    @Lob
    @Column(name = "SKILLS")
    private byte[] blobSkills;

    @JsonProperty("mod_date")
    @Column(name = "MODIFICATION_DATE")
    private Date mod_date;

    @JsonProperty("sex")
    @Column(name = "GENDER")
    private Gender gender;
    
    @JsonProperty("marital_status")
	@Column(name = "MARITAL_SATUS")
    private MaritalStatus maritalStatus;
    
    @JsonProperty("education")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "EDUCATION_ID")
	private Education education;
    
    @JsonProperty("rubrics")
    @OneToMany(targetEntity = Rubric.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "RUBRIC_ID")
    private List<Rubric> rubrics;
		 
    @JsonProperty("contact")
    @Embedded
	private Contact contact;

    @JsonProperty("salary")
    @Column(name = "SALARY")
    private String salary;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getAge() {
		return age;
	}

	public void setAge(Long age) {
		this.age = age;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public byte[] getBlobSkills() {
		return blobSkills;
	}

	public void setBlobSkills(byte[] blobSkills) {
		this.blobSkills = blobSkills;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public Date getMod_date() {
		return mod_date;
	}

	public void setMod_date(Date mod_date) {
		this.mod_date = mod_date;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public MaritalStatus getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(MaritalStatus maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public Education getEducation() {
		return education;
	}

	public void setEducation(Education education) {
		this.education = education;
	}

	public List<Rubric> getRubrics() {
		return rubrics;
	}

	public void setRubrics(List<Rubric> rubrics) {
		this.rubrics = rubrics;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}
}
