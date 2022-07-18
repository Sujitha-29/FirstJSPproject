<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import = "com.chainsys.jspproject.commonutil.Validator,com.chainsys.jspproject.commonutil.ExceptionManager"
	import = "com.chainsys.jspproject.commonutil.HTMLHelper,com.chainsys.jspproject.commonutil.InvalidInputDataException"
	import = "com.chainsys.jspproject.commonutil.LogManager,com.chainsys.jspproject.pojo.Employee"
	import ="java.text.ParseException,java.text.SimpleDateFormat,java.util.Date,com.chainsys.jspproject.dao.EmployeeDao"
	%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert Employee</title>
</head>
<body>
	<%
	String source="AddNewEmployee";
	String message="<h1>Error while "+source+"</h1>";
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
		%><h2><%=errorPage%></h2><%
		return;
	}
	empId = Integer.parseInt(id);
	try {
		Validator.CheckNumberForGreaterThanZero(empId);
	} catch (InvalidInputDataException err) {
		message +="Error in Emp id input</p>";
		String errorPage=ExceptionManager.handleException(err, source, message);
		%><h2><%=errorPage%></h2><%
		return;
	}
	newemp.setEmp_id(empId);
//--------------------------------
	String fname = request.getParameter("fname");
	testname = fname;
	try {
		Validator.checkStringOnly(testname);
	} catch (InvalidInputDataException e) {
		message +="Error in Emp id input</p>";
		String errorPage=ExceptionManager.handleException(e, source, message);
		%><h2><%=errorPage%></h2><%
		return;
	}
	try {
		Validator.checklengthOfString(fname);
	} catch (InvalidInputDataException err) {
		message +="Error first name input</p>";
		String errorPage=ExceptionManager.handleException(err, source, message);
		%><h2><%=errorPage%></h2><%
	}
	newemp.setFirst_name(fname);
//-----------------------------------
	String lname = request.getParameter("lname");
	testname = fname;
	try {
		Validator.checkStringOnly(testname);
	} catch (InvalidInputDataException e) {
		message +="Error in Last name input</p>";
		String errorPage=ExceptionManager.handleException(e, source, message);
		%><h2><%=errorPage%></h2><%
		return;
	}
	try {
		Validator.checklengthOfString(lname);
	} catch (InvalidInputDataException err) {
		message +="Error in Last name input</p>";
		String errorPage=ExceptionManager.handleException(err, source, message);
		%><h2><%=errorPage%></h2><%
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
		%><h2><%=errorPage%></h2><%
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
		%><h2><%=errorPage%></h2><%
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
		%><h2><%=errorPage%></h2><%
        return;
	}
	newemp.setJob_id(jobId);
//---------------------------------------			
	String sal = request.getParameter("salary");
	try {
		Validator.checkStringForParseInt(sal);
	} catch (InvalidInputDataException err) {
		message +="Error in Job id input</p>";
		String errorPage=ExceptionManager.handleException(err, source, message);
		%><h2><%=errorPage%></h2><%
	}
	float salParse = Float.parseFloat(sal);
	try {
		Validator.CheckNumberForGreaterThanZero(salParse);
	} catch (InvalidInputDataException err)
	{
		message +="Error in salary input</p>";
		String errorPage=ExceptionManager.handleException(err, source, message);
		%><h2><%=errorPage%></h2><%
        return;
	}
	newemp.setSalary(salParse);
//----------------------------------------------			
} catch (Exception e) 
{
	message +="Error in Last name input</p>";
	String errorPage=ExceptionManager.handleException(e, source, message);
	%><h2><%=errorPage%></h2><%
    return;
}
int result = EmployeeDao.insertEmployee(newemp);
%><h2><%=result%> row inserted</h2><%
%> 
</body>
</html>