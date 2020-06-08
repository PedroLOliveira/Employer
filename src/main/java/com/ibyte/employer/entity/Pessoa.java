package com.ibyte.employer.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Pessoa {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String first_name;
    private String last_name;
	private String career;

	@ManyToOne(
		fetch = FetchType.LAZY
	)
	@JoinColumn(name = "department_id", referencedColumnName = "id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Setor departmentNavigation;

	protected Pessoa() {}

	public Pessoa(String first_name, String last_name, String career) {
		this.first_name = first_name;
        this.last_name = last_name;
        this.career = career;
	}

	public Pessoa(String first_name, String last_name, String career, Setor department) {
		this.first_name = first_name;
        this.last_name = last_name;
		this.career = career;
		this.departmentNavigation = department;
	}

	@Override
	public String toString() {
		return String.format(
				"{'id'='%d', 'first_name'='%s', 'last_name'='%s', 'career'='%s', 'department'='%s'}",
				id, first_name, last_name, career, departmentNavigation.getName());
	}
	
	public Long getId() {
		return id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public String getCareer() {
		return career;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public void setCareer(String career) {
		this.career = career;
	}
	
    public Setor getDepartmentNavigation() {
        return departmentNavigation;
    }
    public void setDepartmentNavigation(Setor department) {
        this.departmentNavigation = department;
		departmentNavigation.getEmployees().add(this);
    }
}