package com.revature.daos;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.revature.daos.EmployeeDAOInterface;

import com.revature.models.Employee;
import com.revature.utils.HibernateUtil;

public class EmployeeDAO implements EmployeeDAOInterface{


	public void addEmployeee(Employee employee) {
		
		//open a Session object to establish a DB connection
		Session ses = HibernateUtil.getSession(); //similar to opening a Connection with JDBC
		
		ses.save(employee); 
		
		HibernateUtil.closeSession();
		
		//This is the ENTIRE insert method - much cleaner than JDBC :)
		//no try/catch? well, we aren't really writing any SQL that could go wrong. Simply using sessions methods
		

	public void newEmployee(Employee employee) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSession()) {
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
	public List<Employee> getEmployees() {

        Session ses = HibernateUtil.getSession();

        List<Employee> employeeList = ses.createQuery("FROM Employee").list();

        HibernateUtil.closeSession();

        return employeeList;

    }
	
	public Employee findEmployeeById(int id){
		
		Session ses = HibernateUtil.getSession();
		
		Employee employee = ses.get(Employee.class, id);
		
		HibernateUtil.closeSession();
		
		return employee;
		
	}
	
	
	public boolean validate(String username, String password) {

		List<Employee> employees = getEmployees();
		
		for (int i=0; i<employees.size(); i++) {
			Employee temp = employees.get(i);
			if (username.equals(temp.getUsername())&&password.equals(temp.getPassword()))
				return true;
		}
		
		return false;
		
		//Implement EmployeeDAO to do login service
		//Potentially loop through the whole users table to check for a match?
		//Potentially return an employee instead of a bool?
	}
	
	public Employee getEmployeeByID(int EID) {
		List<Employee> employeeList=getEmployees();
		Employee emp = new Employee();
		
		for (int i=0; i<employeeList.size(); i++) {
			
			Employee temp=employeeList.get(i);
			int ID=temp.getID();
			if (ID==EID)
				return temp;
		}
		//Add code to notify user if an ID isn't found later
		return null;
	}
	
	//Add delete employee method later?

}