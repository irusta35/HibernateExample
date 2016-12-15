package com.erp.app;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class DAOEmployeeImpl implements IDAOEmployee{ 

	public SessionFactory getSessionFactory() {
		Configuration configuration = new Configuration().configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties());
		SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
		return sessionFactory;
	}

	public void add(Employee e) {
		Session session = getSessionFactory().openSession();
		session.beginTransaction();
		session.save(e);
		session.getTransaction().commit();
		session.close();
	}

	public List<Employee> getAll() {
		Session s = getSessionFactory().openSession();
		@SuppressWarnings("unchecked")
		List<Employee> employees = s.createQuery("FROM Employee").list();
		s.close();
		return employees;
	}

	public Employee findById(int id) {
		Session session = getSessionFactory().openSession();
		Employee e = (Employee) session.get(Employee.class, id);
		session.close();
		return e;
	}

	public void edit(Employee e) {
		Session session = getSessionFactory().openSession();
		session.beginTransaction();
		session.update(e);
		session.getTransaction().commit();
		session.close();
	}

	public void delete(int id) {
		Session session = getSessionFactory().openSession();
		session.beginTransaction();
		Employee e = (Employee) session.get(Employee.class, id);
		session.delete(e);
		session.getTransaction().commit();
		session.close();
	}
}
