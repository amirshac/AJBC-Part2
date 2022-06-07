package ajbc.webservice.rest.api_demo.resources;

import java.util.List;

import ajbc.webservice.rest.api_demo.DBservice.StudentDBService;
import ajbc.webservice.rest.api_demo.models.Course;
import ajbc.webservice.rest.api_demo.models.Student;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;


@Path("students")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentResource {
	
	StudentDBService studentDB = new StudentDBService();
	
	@GET
	public List<Student> getAllStudents() {
		return studentDB.getAllStudents();
	}
	
	@GET
	public List<Student> getStudentByAverage(@QueryParam("average") double average) {
		if (average>0)
			return studentDB.getStudentsByAverage(average);
		return studentDB.getAllStudents();
	}
	
	@GET
	@Path("/{id}")
	public Student getStudentByID(@PathParam("id") long id) {
		return studentDB.getStudentByID(id);
	}
	
	// add new student
	@POST
	public Student addStudent(Student student) {
		return studentDB.addStudent(student);
	}
	
	// update an existing student
	@PUT
	@Path("/{id}")
	public Student updateStudent(@PathParam("id")long id, Student student) {
		return studentDB.updateStudent(id, student);
	}
	
	@DELETE
	@Path("/{id}")
	public Student deleteStudent(@PathParam("id")long id) {
		return studentDB.deleteStudent(id);
	}
	
	@GET
	@Path("/{id}/courses")
	public Response getCoursesByStudentId(@PathParam("id")long id) {
		List<Course> courses = studentDB.getCoursesByStudentId(id);
		return Response.ok().entity(courses).build();
		
	}
}

