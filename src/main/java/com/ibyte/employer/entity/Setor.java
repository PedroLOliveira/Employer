package com.ibyte.employer.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class, 
  property = "name")
public class Setor {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(unique = true)
	private String name;

	@OneToMany(
        mappedBy = "departmentNavigation",
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
	)
	@JsonIgnore
    private Set<Pessoa> employeesNavigation;

	protected Setor() {}

	public Setor(String name) {
		this.name = name;
		employeesNavigation = new HashSet<>();
	}

	@Override
	public String toString() {
		return String.format(
				"{'id'='%d', 'name'='%s'}",
				id, name);
	}

	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Set<Pessoa> getEmployees() {
        return employeesNavigation;
    }
    public void setEmployees(Set<Pessoa> employees) {
        this.employeesNavigation = employees;
        for (Pessoa employee : employees) {
            employee.setDepartmentNavigation(this);
        }
    }
}