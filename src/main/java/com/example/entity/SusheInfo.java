package com.example.entity;

import javax.persistence.*;

@Table(name = "sushe_info")
public class SusheInfo  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(name = "name")
	private String name;
	@Column(name = "student")
	private String student;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStudent() {
		return student;
	}
	public void setStudent(String student) {
		this.student = student;
	}


    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return this.id;
    }

}
