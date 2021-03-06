package SocialNetwork.actions;

import java.util.*;
import Dominion.Publisher;
import Dominion.User;


import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DeleteReviewer extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String revdel; 
	
	public String execute() {
		Publisher p = new Publisher(); 
		User u;
		Map<String, Object> session = ActionContext.getContext().getSession();  // Get session
		try {

			u =  (User) session.get("user"); 

			p = new Publisher(u);
			p.delete_reviewer(this.revdel);

			return SUCCESS; 
		}
		catch (Exception e) {
			session.put("error_message", "Error deleting review: try again please");
			return ERROR;
		}
	}

	public void setRevdel(String revdel){
		this.revdel = revdel;
	}
}
