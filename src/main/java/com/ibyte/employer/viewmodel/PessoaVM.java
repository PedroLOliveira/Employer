package com.ibyte.employer.viewmodel;

public class PessoaVM {
    private Long id;
	private String first_name;
    private String last_name;
	private String career;
    private String department;

	protected PessoaVM() {}

	public PessoaVM(Long id, String first_name, String last_name, String career, String department) {
		this.id = id;
		this.first_name = first_name;
        this.last_name = last_name;
        this.career = career;
        this.department = department;
	}

	public PessoaVM(String first_name, String last_name, String career, String department) {
		this.first_name = first_name;
        this.last_name = last_name;
        this.career = career;
        this.department = department;
	}

	@Override
	public String toString() {
		return String.format(
				"{'id'='%d', 'first_name'='%s', 'last_name'='%s', 'career'='%s', 'department'='%s'}",
				id, first_name, last_name, career, department);
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
	public String getDepartment() {
		return department;
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
	public void setDepartment(String department) {
		this.department = department;
	}
}