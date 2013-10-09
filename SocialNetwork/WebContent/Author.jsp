<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="/struts-tags" prefix="s" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import = "java.util.List" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Welcome</title>
<script language = "Javascript">

function ValidateForm(){
	var dt=document.sendform.upload
	if (dt.value==""){
		dt.focus()
		alert("Wait! We need an article!")
		return false
	}
    return true
 }

</script>
<link rel="stylesheet" href="Style.css" type="text/css" />
</head>
<body>
	<s:if test="#session.loggedin != 'true'">
            <jsp:forward page="/index.jsp" />
    </s:if>
<h1>Author: <s:property value="#session.user.getName()" /> <s:property value="#session.user.getSecond_name()" /></h1>
			<p align="right"><s:url id="link" action="logout" >
			</s:url>
			<s:a href="%{link}" >Logout</s:a></p>
<hr>

<h4>Notifications: </h4> 

<br/>
<s:if test="notifications.isEmpty()" >
	<table>
		<tr>
			<td>
				No notifications :)
			</td>
		</tr>	
	</table> 
</s:if>
<s:else>

	<table border="1">
		<s:iterator value="notifications" status="itStatus">
		<tr>
			<td align="center">
				<s:property value="getId()" />
			</td>
			<td align="center">
				<s:property value="getDate()" />
			</td>
			<td align="center">
				<s:property value="getNote()" />
			</td>
		</tr>
		</s:iterator>
	</table>

</s:else>
<br />
<hr>
<jsp:include page="header.jspf"></jsp:include>
<hr>

<br />
<h4>Send publication</h4>
<s:form name="sendform" action="sendpublications" method="post" enctype="multipart/form-data" onSubmit="return ValidateForm()">
	<s:textfield name="publicationname" label="Title"/>
	<s:textarea name="comentarticle" label="Comment" cols="30" rows="3"/>
	<s:select name="tag" label="Tag" headerValue="Select a tag" list='#session.tags'/>
	<s:file name="upload" label="Fichero a subir"/>
	<s:submit value="Send" />
</s:form>

<hr>
<h4>Get My Publications</h4>
<s:form action="getpublications">
<s:submit value="Get publications" />
<s:if test="results==true">
	<table border="1">
	<tr><td><b>Article Id</b></td><td><b>Title</b></td><td><b>Date</b></td><td><b>Your comment</b></td><td><b>State</b></td><td><b>Mark</b></td><td><b>Link</b></td></tr>
			<s:iterator value="publications" status="itStatus">
				<tr><td><s:property value="getId()" /></td><td><s:property value="getTitle()" /></td><td><s:property value="getPublishing_date()" /></td><td><s:property value="getComment_author()" /></td><td><s:property value="getState()" /></td><td><s:property value="getMark()" /></td>
				<td>
			<s:url id="link" action="articledetails" >
			<s:param name="articleid"><s:property value="getId()" /></s:param>
			</s:url>
			<s:a href="%{link}" >Details</s:a>
			</td></tr>
			</s:iterator>
		</table>
</s:if>
<s:else>
<h5>There is no articles</h5>
</s:else>
</s:form>
<br />

</body>
</html>