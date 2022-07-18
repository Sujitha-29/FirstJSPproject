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
<title>Delete Employee</title>
</head>
<body>
<%
String source="AddNewEmployee";
String message="<h1>Error while "+source+"</h1>";
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
	%><h2><%=errorPage%></h2><%
	return;
}
int emp_id = Integer.parseInt(id);
try {
	Validator.CheckNumberForGreaterThanZero(emp_id);
} catch (InvalidInputDataException e) {
	message +="Error in Emp id input</p>";
	String errorPage=ExceptionManager.handleException(e, source, message);
	%><h2><%=errorPage%></h2><%
	return;
	
}
newemp.setEmp_id(emp_id);
int result = EmployeeDao.deleteEmployee(emp_id);
%><h2><%=result%> row deleted</h2><%
%>
</body>
</html>