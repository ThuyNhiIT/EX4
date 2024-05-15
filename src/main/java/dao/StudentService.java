package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entity.Student;

public interface StudentService extends Remote{
	
	public boolean addStudent(Student student) throws RemoteException;
	public List<Student> findAll() throws RemoteException; // tìm tất cả
	public List<Student> findStudentsEnrolledIn(int year) throws RemoteException; // tìm theo năm học
	public List<Student> findStudentsEnrolledInCourse(String title) throws RemoteException; // tìm theo course
}
