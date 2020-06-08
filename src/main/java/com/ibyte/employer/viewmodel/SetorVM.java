package com.ibyte.employer.viewmodel;

public class SetorVM {
    private Long id;
	private String name;

	protected SetorVM() {}

	public SetorVM(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public SetorVM(String name) {
		this.name = name;
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
}