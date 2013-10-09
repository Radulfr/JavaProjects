package SocialNetwork.actions;

import java.util.LinkedList;
import java.util.Map;

import Dominion.Article;
import Dominion.Author;
import Dominion.User;
import Persistence.AuthorDAO;
import Persistence.CrudDAO;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class GetPublications extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LinkedList<Article> publications; 

	private boolean results=false;
	
	public String execute() {
		Author a;
		User u;
		Map<String, Object> session = ActionContext.getContext().getSession();  // Get session
		try {

			u =  (User) session.get("user"); 
			CrudDAO<Author> autordao = new AuthorDAO(); 
			

			a = new Author(u);
			
			a = autordao.read(a); 
			
			publications = a.get_publications();
			if(!publications.isEmpty())
				results=true;
			session.put("results", results);

			return SUCCESS; 
		}
		catch (Exception e) {
			session.put("error_message", "Error getting publications: try again please");
			return ERROR;
		}
	}

	public LinkedList<Article> getPublications(){
		return this.publications;
	}
	public boolean getResults(){
		return this.results; 
	}

}
