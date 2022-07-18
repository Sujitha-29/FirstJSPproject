package com.chainsys.jspproject;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chainsys.jspproject.commonutil.ExceptionManager;
import com.chainsys.jspproject.commonutil.InvalidInputDataException;
import com.chainsys.jspproject.commonutil.Validator;
import com.chainsys.jspproject.dao.EmployeeDao;
import com.chainsys.jspproject.pojo.Employee;


/**
 * Servlet implementation class Employees
 */
@WebServlet("/Employees")
public class EmployeeServlet extends HttpServlet 
{
	String source="AddNewEmployee";
	String message="<h1>Error while "+source+"</h1>";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EmployeeServlet()
	{
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		response.getWriter().append("Served at: ").append(request.getContextPath());
		List<Employee> allEmployee = EmployeeDao.getAllEmployee();
		request.setAttribute("emplist", allEmployee);
		RequestDispatcher rd = request.getRequestDispatcher("/viewemp.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		
		if(request.getParameter("click").equals("ADD_EMP")) 
		{
			
			PrintWriter out = response.getWriter();
			Employee newemp = null;
			int empId = 0;
			String testname = null;
			try {
				newemp = new Employee();
				String id = request.getParameter("id");
				try {
					Validator.checkStringForParseInt(id);
				} catch (InvalidInputDataException err) {
					message +="Error in Emp id input</p>";
					String errorPage=ExceptionManager.handleException(err, source, message);
					out.print(errorPage);
					return;
				}
				empId = Integer.parseInt(id);
				try {
					Validator.CheckNumberForGreaterThanZero(empId);
				} catch (InvalidInputDataException err) {
					message +="Error in Emp id input</p>";
					String errorPage=ExceptionManager.handleException(err, source, message);
					out.print(errorPage);
					return;
				}
				newemp.setEmp_id(empId);
//--------------------------------
				String fname = request.getParameter("fname");
				testname = fname;
				try {
					Validator.checkStringOnly(testname);
				} catch (InvalidInputDataException e) {
					e.printStackTrace();
					return;
				}
				try {
					Validator.checklengthOfString(fname);
				} catch (InvalidInputDataException err) {
					message +="Error first name input</p>";
					String errorPage=ExceptionManager.handleException(err, source, message);
					out.print(errorPage);
				}
				newemp.setFirst_name(fname);
//-----------------------------------
				String lname = request.getParameter("lname");
				testname = fname;
				try {
					Validator.checkStringOnly(testname);
				} catch (InvalidInputDataException e) {
					e.printStackTrace();
					return;
				}
				try {
					Validator.checklengthOfString(lname);
				} catch (InvalidInputDataException err) {
					message +="Error in Last name input</p>";
					String errorPage=ExceptionManager.handleException(err, source, message);
                    out.print(errorPage);
                    return;
				}
				newemp.setLast_name(lname);
//----------------------------------			
				String email = request.getParameter("email");
				try {
					Validator.checkEmail(email);
				} catch (InvalidInputDataException err) {
					message +="Error in email input</p>";
					String errorPage=ExceptionManager.handleException(err, source, message);
                    out.print(errorPage);
					return;
				}
				newemp.setEmail(email);
//--------------------------------------			
				SimpleDateFormat hire_dateFormate = new SimpleDateFormat("dd/MM/yyyy");
				String emp_HireDate = request.getParameter("hdate");
				// Date hire_date=hire_dateFormate.parse(emp_HireDate);

				Date newDate = null;
				try {
					newDate = hire_dateFormate.parse(emp_HireDate);
				} catch (ParseException e) 
				{
					message +="Error Higher Date input</p>";
					String errorPage=ExceptionManager.handleException(e, source, message);
                    out.print(errorPage);
                    return;
				}

				newemp.setHire_date(newDate);
//----------------------------------------
				String jobId = request.getParameter("jobid");
				try {
					Validator.checkJobId(jobId);
				} catch (InvalidInputDataException err) 
				{
					message +="Error in Job id input</p>";
					String errorPage=ExceptionManager.handleException(err, source, message);
                    out.print(errorPage);
                    return;
				}
				newemp.setJob_id(jobId);
//---------------------------------------			
				String sal = request.getParameter("salary");
				try {
					Validator.checkStringForParseInt(sal);
				} catch (InvalidInputDataException err) {
					err.printStackTrace();
				}
				float salParse = Float.parseFloat(sal);
				try {
					Validator.CheckNumberForGreaterThanZero(salParse);
				} catch (InvalidInputDataException err)
				{
					message +="Error in Last name input</p>";
					String errorPage=ExceptionManager.handleException(err, source, message);
                    out.print(errorPage);
                    return;
				}
				newemp.setSalary(salParse);
//----------------------------------------------			
//				result = EmployeeDao.insertEmployee(newemp);
			} catch (Exception e) 
			{
				message +="Error in Last name input</p>";
				String errorPage=ExceptionManager.handleException(e, source, message);
                out.print(errorPage);
                return;
			}
		    int result = EmployeeDao.insertEmployee(newemp);
            request.setAttribute("addemp", result);
            RequestDispatcher rd = request.getRequestDispatcher("/insertemployee.jsp");
            rd.forward(request, response);
		} else if (request.getParameter("click").equals("UPDATE_EMP")) {
			doPut(request, response);
		} else if (request.getParameter("click").equals("DELETE_EMP")) {
			doDelete(request, response);
		}
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		PrintWriter out = response.getWriter();
		Employee newemp = null;
		int result = 0;
		int empId = 0;
		try {
			newemp = new Employee();
			String id = request.getParameter("id");
			try {
				Validator.checkStringForParseInt(id);
			} catch (InvalidInputDataException err) 
			{
				message +="Error in Emp id input</p>";
				String errorPage=ExceptionManager.handleException(err, source, message);
				out.print(errorPage);
				return;
			}
			empId = Integer.parseInt(id);
			try {
				Validator.CheckNumberForGreaterThanZero(empId);
			} catch (InvalidInputDataException err) 
			{
				message +="Error in Emp id input</p>";
				String errorPage=ExceptionManager.handleException(err, source, message);
				out.print(errorPage);
				return;
				
			}
			newemp.setEmp_id(empId);
//--------------------------------
			String fname = request.getParameter("fname");
			try {
				Validator.checkStringOnly(fname);
			} catch (InvalidInputDataException e) {
				message +="Error in first name input</p>";
				String errorPage=ExceptionManager.handleException(e, source, message);
				out.print(errorPage);
				return;
			}
			try {
				Validator.checklengthOfString(fname);
			} catch (InvalidInputDataException err) {
				message +="Error in first name input</p>";
				String errorPage=ExceptionManager.handleException(err, source, message);
				out.print(errorPage);
				return;
			}
			newemp.setFirst_name(fname);
//-----------------------------------
			String lname = request.getParameter("lname");
			try {
				Validator.checkStringOnly(lname);
			} catch (InvalidInputDataException e) {
				message +="Error in last name input</p>";
				String errorPage=ExceptionManager.handleException(e, source, message);
				out.print(errorPage);
				return;
			}
			try {
				Validator.checklengthOfString(lname);
			} catch (InvalidInputDataException err) {
				message +="Error in last name input</p>";
				String errorPage=ExceptionManager.handleException(err, source, message);
				out.print(errorPage);
				return;
			}
			newemp.setLast_name(lname);
//----------------------------------			
			String email = request.getParameter("email");
			try {
				Validator.checkEmail(email);
			} catch (InvalidInputDataException e) {
				message +="Error in email input</p>";
				String errorPage=ExceptionManager.handleException(e, source, message);
				out.print(errorPage);
				return;
				
			}
			newemp.setEmail(email);
//--------------------------------------			
			SimpleDateFormat hire_dateFormate = new SimpleDateFormat("dd/MM/yyyy");
			String emp_HireDate = request.getParameter("hdate");
			Date newDate = null;
			try {
				newDate = hire_dateFormate.parse(emp_HireDate);
			} catch (ParseException e)
			{
				message +="Error in Hire Date input</p>";
				String errorPage=ExceptionManager.handleException(e, source, message);
				out.print(errorPage);
				return;
			}

			newemp.setHire_date(newDate);
//----------------------------------------
			String jobId = request.getParameter("jobid");
			try {
				Validator.checkJobId(jobId);
			} catch (InvalidInputDataException err) {
				message +="Error in Job id input</p>";
				String errorPage=ExceptionManager.handleException(err, source, message);
				out.print(errorPage);
				return;
			}
			newemp.setJob_id(jobId);
//---------------------------------------			
			String sal = request.getParameter("salary");
			try {
				Validator.checkStringForParseInt(sal);
			} catch (InvalidInputDataException err) {
				message +="Error in salary input</p>";
				String errorPage=ExceptionManager.handleException(err, source, message);
				out.print(errorPage);
				return;
			}
			float salParse = Float.parseFloat(sal);
			try {
				Validator.CheckNumberForGreaterThanZero(salParse);
			} catch (InvalidInputDataException err) 
			{
				message +="Error in salary input</p>";
				String errorPage=ExceptionManager.handleException(err, source, message);
				out.print(errorPage);
				return;		
			}
			newemp.setSalary(salParse);
//----------------------------------------------			
			//result = EmployeeDao.updateEmployee(newemp);
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally {
		}
		result = EmployeeDao.updateEmployee(newemp);
        request.setAttribute("updateemp", result);
        RequestDispatcher rd = request.getRequestDispatcher("/updateemployee.jsp");
        rd.forward(request, response);
    }

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		PrintWriter out = response.getWriter();
		String id = null;
		Employee newemp = null;
		try {
		    newemp = new Employee();
			id = request.getParameter("id");
			Validator.checkStringForParseInt(id);
		} catch (InvalidInputDataException e)
		{
			message +="Error in Emp id input</p>";
			String errorPage=ExceptionManager.handleException(e, source, message);
			out.print(errorPage);
			return;
		}
		int emp_id = Integer.parseInt(id);
		try {
			Validator.CheckNumberForGreaterThanZero(emp_id);
		} catch (InvalidInputDataException e) {
			message +="Error in Emp id input</p>";
			String errorPage=ExceptionManager.handleException(e, source, message);
			out.print(errorPage);
			return;
			
		}
	    newemp.setEmp_id(emp_id);
		int result = EmployeeDao.deleteEmployee(emp_id);
        request.setAttribute("delemp", result);
        RequestDispatcher rd = request.getRequestDispatcher("/deleteemployee.jsp");
        rd.forward(request, response);
	}
}