package com.erp.app;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
//	Employee e = new Employee();
	//e.setName("Tito");
//		e.setAge(45);
		
		//add(e);
		Employee e = findById(2);
		e.setName("Julia Marin");
		e.setAge(35);
		update(e);

		getAll();
	}

	public static SessionFactory getSessionFactory() {
		Configuration configuration = new Configuration().configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties());
		SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
		return sessionFactory;
	}
	
	public static void add (Employee e){
		Session session = getSessionFactory().openSession();
		session.beginTransaction();
		// crear objeto empleado
		session.save(e);
		session.close();	
	}

	public static void getAll() {
		Session s = getSessionFactory().openSession();
		@SuppressWarnings("unchecked")
		List<Employee> employees = s.createQuery("FROM Employee").list();
		for(Employee e : employees) {
			System.out.println(e.toString());
		}		
		s.close();		
	}

	public static Employee findById (int id){
		Session session = getSessionFactory().openSession();
		Employee e = (Employee) session.get(Employee.class,id);
		session.close();
		return e;
	}

	public static void update (Employee e) {
		Session session = getSessionFactory().openSession();
		session.beginTransaction();
		session.update(e);
		session.getTransaction().commit();
		session.close();
	}
	
	public static void delete (int id){
		Session session = getSessionFactory().openSession();
		session.beginTransaction();
		Employee e = (Employee) session.get(Employee.class,id);
		session.delete(e);
		session.getTransaction().commit();
		session.close();
	}
}

