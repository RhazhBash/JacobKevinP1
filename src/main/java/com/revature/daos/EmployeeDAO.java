package com.revature.daos;

import java.util.ArrayList;
import java.util.List;

import com.revature.models.Employee;

public class EmployeeDAO {

	public void saveUser(Employee employee) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// save the student object
			session.save(employee);
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")      
	public List<Employee> getAllEmployees(){       
	    try
	    {
	        return sessionFactory.getCurrentSession().createCriteria(Employee.class).list();
	    } catch (Exception e) {
	        return new ArrayList<>();
	    }
	}
	
	
	
	public boolean validate(String userName, String password) {

		Transaction transaction = null;
		Employee employee = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// get an user object
			
			String HQL = "FROM Employee U WHERE U.username = :userName";
			employee = (Employee) session.createQuery(HQL).setParameter("userName", userName).uniqueResult();

			if (employee != null && employee.getPassword().equals(password)) {
				return true;
			}
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}

}