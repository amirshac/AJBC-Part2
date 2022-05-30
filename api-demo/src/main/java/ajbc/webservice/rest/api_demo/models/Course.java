package ajbc.webservice.rest.api_demo.models;

import java.util.ArrayList;
import java.util.List;

public class Course {
	private final Long ID;
	private String name;
	private List<Student> students;
	private static long counter = 1000;

	public Course() {
		students = new ArrayList<Student>();
		this.ID = generateId();
	}
	
	private synchronized long generateId() {
		return counter++;
	}
	
	public Course(String name) {
		this.ID = generateId();
		this.name = name;
		students = new ArrayList<Student>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public Long getID() {
		return ID;
	}
	
	
	
	
}
