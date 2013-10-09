<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin Throne</title>
<link rel="stylesheet" href="Style.css" type="text/css" />
</head>
<script language="JavaScript">
    function table_show(id) {
        var item_tr = document.getElementById("table_ms");
        document.getElementById("field_id").value=id;
        
        if (item_tr) { 
	        if (item_tr.style.display == "none") {
		        item_tr.style.display = "";

	        } else {
		        item_tr.style.display = "none";
	        }
        }
    }
	
</script>
<body>
	<h1>Hello Admin</h1>
	<p align="right">
		<s:url id="link" action="logout" >
		</s:url>
		<s:a href="%{link}" >Logout</s:a>
	</p>
<hr />
<h3>User management</h3>
<br />
		<s:form action="Adminbuscarusu">
		<s:textfield name="name" label="Search for ID" />
		<s:submit value="Search" ></s:submit>

		<s:if test="found==true">
		<hr />
				<h4>Found:</h4>	 
				<br />
				<table border="1">
					<tr>
						<td><s:property value="userfound" /></td>
						<td>
							<s:url id="link" action="AdminDelUser" >
							<s:param name="newfriend"><s:property value="userid" /></s:param>
							</s:url>
							<s:a href="%{link}" >Delete User</s:a>						
						</td>
						<td>
							<s:url id="link" action="AdminGetUserProfile" >
							<s:param name="newfriend"><s:property value="userid" /></s:param>
							</s:url>
							<s:a href="%{link}" >Edit User Data</s:a>						
						</td>
					</tr>
				
				</table>
				<br />
			
		</s:if>
	</s:form>
<h4>Add user</h4>
				<table border ="1">
					<tr>
						<td>
						<s:form action="AdminCreateUser">
							<s:textfield name="email" label="Email" />
							<s:textfield name="password" label="Password" />
							<s:textfield name="password_confirmation" label="Re-Type Password" />
							<s:textfield name="rol" label="Rol {A,E,R}" />
							<s:textfield name="tag" label="Tag (if any)" />
							<s:submit value="Submit" />
						</s:form>
						</td>
					</tr>
				</table>
				
<h4>Article Management</h4>

				<table border ="1">
					<tr>
						<td>
						<s:form action="AdminSearchArticle">
							<s:textfield name="articleId" label="Article ID" />
							<s:submit value="Submit" />
						</s:form>
						</td>
					</tr>
				</table>
				
				<table border="1">
				<s:if test="#session.articlefound.getId()>0" >
						<tr>
						
							<td>
								<b>Article ID</b>
							</td>
							<td>
								<b>Title</b>
							</td>
							<td>
								<b>Author</b>
							</td>
							<td>
								<b>State</b>
							</td>
							<td>
								<b>Delete</b>
							</td>
							<td>
								<b>Manual schedule</b>
							</td>
						</tr>
						<tr>
							<td>
								<s:property value="#session.articlefound.getId()" />
							</td>
							<td>
								<s:property value="#session.articlefound.getTitle()" />
							</td>
							<td>
								<s:property value="#session.articlefound.getId_author()" />
							</td>						
							<td>
								<s:property value="#session.articlefound.getState()" />
							</td>	
							<td>
								<s:url id="link" action="AdminDeleteArticle" >
								<s:param name="articleDel"><s:property value="#session.articlefound.getId()" /></s:param>
								</s:url>
								<s:a href="%{link}" >Yes</s:a>
							</td>
							<td>
								<a href="javascript:table_show('<s:property value="articleId" />')">Yes</a>
							</td>	
									
						</tr>
						</s:if>	
				</table>
				<div id="table_ms" style="display:none;">
				<s:form action="AdminManualSchedule">
					<tr>
						<td>
							<s:hidden id="field_id" name="field_id"/>
							<s:textfield name="manual_reviewer" label="Reviewer ID" />
							<s:submit value="Submit" />
						</td>
					</tr>
				</s:form>
				</div>
		<h4>Add Tag</h4>
		<table border ="1">
		<tr>
			<td>
				<s:form action="AdminAddTag">
					<s:textfield name="tag" label="Tag Name" />
					<s:submit value="Add Tag" />
				</s:form>
			</td>
		</tr>
		
			<s:if test="added==true">
			
						<script language="JavaScript">
							alert("Tag added"); 
						</script>
			</s:if>
		</table>
	
				
	
</body>
</html>