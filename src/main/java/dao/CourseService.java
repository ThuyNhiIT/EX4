package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import entity.Course;
import entity.OnlineCourse;

public interface CourseService extends Remote{
	
	public boolean add(Course course) throws RemoteException; // thêm 1 course
	public boolean update(Course course) throws RemoteException; // cập nhật 1 course
	public Course findByID(int id) throws RemoteException; // tìm theo id
	public List<Course> findAll() throws RemoteException; // tìm tất cả
	public List<Course> findByTitle(String title) throws RemoteException; // tim tuong doi
	public Course findByTitle2(String title) throws RemoteException; // tim tuyet doi
	public Map<OnlineCourse, Integer> countOnlineCourseEnrollment() throws RemoteException; // đếm số lượng học viên đăng ký 
	public Map<OnlineCourse, Long> countOnlineCourseEnrollment2() throws RemoteException; // đếm số lượng học viên đăng ký 
}
