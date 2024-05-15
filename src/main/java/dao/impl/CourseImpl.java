package dao.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

import dao.CourseService;
import entity.Course;
import entity.OnlineCourse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class CourseImpl extends UnicastRemoteObject implements CourseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5247372137130205832L;
	private EntityManager em;

	public CourseImpl() throws RemoteException{
		em = Persistence.createEntityManagerFactory("jpa-mssql").createEntityManager();
	}

	@Override
	public boolean add(Course course) throws RemoteException{
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.persist(course);
			tx.commit();
			return true;
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean update(Course course) throws RemoteException{
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.merge(course);
			tx.commit();
			return true;
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}

		return false;
	}

	public boolean delete(int id) {
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			Course course = em.find(Course.class, id);
			em.remove(course);
			tx.commit();
			return true;
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public Course findByID(int id) throws RemoteException{
		return em.find(Course.class, id);
	}

	@Override
	public List<Course> findAll() throws RemoteException{
		return em.createNamedQuery("Course.findAll", Course.class).getResultList();
	}

	@Override
	public List<Course> findByTitle(String title) throws RemoteException{
		return em.createNamedQuery("Course.findByTitle", Course.class)
				.setParameter("title", "%"+title+"%")
				.getResultList();
	}

	@Override
	public Course findByTitle2(String title) throws RemoteException{
		return em.createQuery("select c from Course c where c.title = :title", Course.class)
				.setParameter("title", title)
//				.getSingleResult();
				.getResultList()
				.stream()
				.findFirst()
				.orElse(null);
	}


	@Override
	public Map<OnlineCourse, Integer> countOnlineCourseEnrollment() throws RemoteException{
		
		String query = "select ol.CourseID, count(*) as n\r\n"
				+ "from [dbo].[OnlineCourse] ol inner join [dbo].[Course] c on c.CourseID = ol.CourseID\r\n"
				+ "inner join [dbo].[StudentGrade] sg on sg.CourseID = c.CourseID\r\n"
				+ "group by  ol.CourseID\r\n"
				+ "order by n desc\r\n";
		
		List<?> list = em.createNativeQuery(query, Object.class).getResultList();
		
		return list.stream()
				.map(o -> (Object[]) o)
				.map(a -> {
					int courseID = (int) a[0];
					int count = (int) a[1];
					OnlineCourse oc = em.find(OnlineCourse.class, courseID);
					return Map.entry(oc, count);
//				}).collect(Collectors.toMap(Entry::getKey, Entry::getValue));// HashMap
				}).collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));// HashMap
	}
	@Override
	public Map<OnlineCourse, Long> countOnlineCourseEnrollment2() throws RemoteException{ // Sort by course title
		 
		String query = "select sg.course.id, count(*) from StudentGrade sg group by sg.course.id ";
		
		return em.createQuery(query, Object[].class)
				.getResultList().stream()
				.map(a -> {
					int courseID = (int) a[0];
					long count = (long) a[1];
					OnlineCourse oc = em.find(OnlineCourse.class, courseID);
					return oc != null? Map.entry(oc, count) : null;
				})
				.filter(e -> e != null)
				.collect(Collectors.toMap(Entry::getKey, 
						Entry::getValue, 
						(e1, e2) -> e1, 
						() -> new TreeMap<>(Comparator.comparing(OnlineCourse::getTitle).reversed())));// HashMap
	}

}
