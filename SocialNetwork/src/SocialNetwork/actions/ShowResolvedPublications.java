package SocialNetwork.actions;

import java.util.LinkedList;
import java.util.Map;

import Dominion.ArticleReview;
import Dominion.Reviewer;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ShowResolvedPublications extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LinkedList<ArticleReview> reviewList;
	
	public String execute() {
		Reviewer r = new Reviewer();
		Map<String, Object> session = ActionContext.getContext().getSession();  // Get session
		try {
			
			r = (Reviewer) session.get("user");
			this.reviewList = r.get_resolved_reviews();
			System.out.println(reviewList.toString()); 
			return SUCCESS; 
		}
		catch (Exception e) {
			session.put("error_message", "Error getting resolved publications: try again please");
			return ERROR;
		}
	}


	public LinkedList<ArticleReview> getReviewList(){
		return this.reviewList;
	}
	
	public boolean getFoundRes(){
		return !this.reviewList.isEmpty();
	}

}
