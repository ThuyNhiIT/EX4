package server;

import java.rmi.registry.LocateRegistry;

import javax.naming.Context;
import javax.naming.InitialContext;

import dao.CourseService;
import dao.StudentService;
import dao.impl.CourseImpl;
import dao.impl.StudentImpl;

public class Server {
	private static final String URL = "rmi://MSI:2003/";

	public static void main(String[] args) {
		try {
			
			CourseService courseService = new CourseImpl();
			StudentService studentService = new StudentImpl();
			
			Context context = new InitialContext();
			
			LocateRegistry.createRegistry(2003);
			
			context.bind(URL + "courseService", courseService);
			context.bind(URL + "studentService", studentService);
			
			System.out.println("Server is running...");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
