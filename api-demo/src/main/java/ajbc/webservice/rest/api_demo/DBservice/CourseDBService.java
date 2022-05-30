package ajbc.webservice.rest.api_demo.DBservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ajbc.webservice.rest.api_demo.DB.MyDB;
import ajbc.webservice.rest.api_demo.models.Course;
import ajbc.webservice.rest.api_demo.models.Student;

public class CourseDBService {

	private MyDB db;
	private Map<Long, Course> courses;
	
	public CourseDBService() {
		db = MyDB.getInstance();
		courses = db.getCourses();
	}
	
	// returns all the student from the DB as a list
	public List<Course> getAllCourses(){
		return new ArrayList<Course>(courses.values());
	}
	
	//returns course by ID
	public Course getCourse(long id) {
		return courses.get(id);
	}
	
	// add new course to DB
	public Course addCourse(Course course) {
		courses.put(course.getID(), course);
		return course;
	}
	
	// update course details
	public Course updateCourse(long id, Course courseDetails) {
		if (courses.containsKey(id)) {
			Course updatedCourse = courses.get(id);
			updatedCourse.setName(courseDetails.getName());
			
			courses.put(id, updatedCourse);
			return updatedCourse;
		}
		
		return null;
	}
	
	// delete course from DB
	public Course deleteCourse(long id) {
		return courses.remove(id);
	}
	
}
