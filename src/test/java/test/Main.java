/*
 * @ (#) Main.java	1.0	Apr 1, 2024
 * 
 * Copyright (c) 2024 IUH. All rights reserved.
 */
package test;

import java.rmi.RemoteException;
import java.util.List;

import dao.CourseService;
import dao.StudentService;
import dao.impl.CourseImpl;
import dao.impl.StudentImpl;
import entity.Student;

public class Main {

	public static void main(String[] args) throws RemoteException {
//		Persistence.createEntityManagerFactory("jpa-mssql");
		
		CourseService courseDAO = new CourseImpl();
		StudentService studentDAO = new StudentImpl();
		
		courseDAO.countOnlineCourseEnrollment2()
				.entrySet()
				.forEach(entry -> {
					System.out.println("Course: " + entry.getKey());
					System.out.println("Number of students: " + entry.getValue());
				});
		
		
//		List<Course> courses = courseDAO.findByTitle("po");
//		courses.forEach(System.out::println);
		
//		List<Student> students = studentDAO.findStudentsEnrolledIn(2005);
//		students.forEach(System.out::println);
		
//		List<Student> students = studentDAO.findStudentsEnrolledInCourse("po");
//		students.forEach(System.out::println);
	}
}
