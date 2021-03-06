package SocialNetwork.actions;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Dominion.Author;
import Persistence.AuthorDAO;
import Persistence.CrudDAO;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class Register extends ActionSupport{
	
	private String email;
	private String name;
	private String second_name;
	private String city;
	private String birth_date;
	private String password1;
	private String password2; 
	
	public String execute() {
		String result = SUCCESS; 
		Map<String, Object> session = ActionContext.getContext().getSession();  // Get session
		try {
			if(!password1.equals(password2)){
				result = ERROR;
				session.put("error_message", "Error registering: type same password and try again please");
			}
			else{
				Author u = new Author();
				CrudDAO<Author> userdao = new AuthorDAO(); 

				u.setEmail(this.email); 
				u.setPassword(this.password1); 
				u.setName(this.name); 
				u.setSecond_name(this.second_name); 
				u.setCity(this.city); 
				u.setBirth_date(this.birth_date); 
				u.setRol("A");
				u.setKarma(0); 
				
				userdao.create(u); 
				
				session.put("loggedin", true);
				session.put("role", 1);
				session.put("user", u);
			}
			return result; 
		}
		catch (Exception e) {
			session.put("error_message", "Error registering: retype(sorry) data and try again please");
			return ERROR;
		}
	}
	
	private static final long serialVersionUID = 1L;
	public void setEmail(String email) {
		this.email = email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSecond_name(String second_name) {
		this.second_name = second_name;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setBirth_date(String birth_date) {
		this.birth_date = birth_date;
	}

	public void setPassword1(String password) {
		this.password1 = password;
	}


	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	public void validate(){
		 
		if(email.length() <= 2)
			addFieldError("email", "Valid User Email is required");
		if (getPassword2().length() == 0) {
			addFieldError("password1", getText("A password is required"));
		}
		if(!password1.equals(password2)){
			addFieldError("password1", "Password confirmation error");
			
		}
		
	}
	
}
