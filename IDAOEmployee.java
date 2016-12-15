package com.erp.app;

import java.util.List;

public interface IDAOEmployee {
	void add (Employee e);
	void edit (Employee e );
	void delete (int id );
	List<Employee> getAll();
	Employee findById(int id);
}

