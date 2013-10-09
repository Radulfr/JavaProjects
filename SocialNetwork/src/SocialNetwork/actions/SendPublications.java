package SocialNetwork.actions;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import Dominion.Article;
import Dominion.ArticleTag;
import Dominion.Author;
import Dominion.Notification;
import Dominion.Sistema;
import Dominion.Tag;
import Dominion.User;
import Persistence.ArticleDAO;
import Persistence.ArticleTagDAO;
import Persistence.CrudDAO;
import Persistence.NotificationDAO;
import Persistence.TagDAO;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SendPublications extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String publicationname;
	private String comentarticle;
	private String tag;
	
	private File file;
	private String filename;
	
	public String execute() {
		Author a;
		User u;
		CrudDAO<Article> c = new ArticleDAO();
		Article Art = new Article();
		Map<String, Object> session = ActionContext.getContext().getSession();  // Get session
		try { 
			CrudDAO<ArticleTag> d = new ArticleTagDAO(); 
			CrudDAO<Tag> e = new TagDAO(); 
			Sistema s;
			
			ArticleTag t = new ArticleTag(); 
			u =  (User) session.get("user"); 
			a = new Author(u);

			Tag ta = new Tag(); 
			
			Art.setTitle(publicationname);
			Art.setComment_author(comentarticle);
			Art.setBody(filename);
			Art.setId_author(a.getEmail());
			Art.setPublishing_date(utilidades.leer.fecha());
			Art.setState("sent"); 
			
			ta.setIssue(tag); 
			ta=e.read(ta); 
			
			c.create(Art);
			t.setIdArticle(c.read(Art).getId());
			t.setIdTag(ta.getIdTag());
			d.create(t); 

			s = new Sistema(a); 
			s.PublicationsToReviewer(a,Art,ta.getIdTag()); 		
		}
		catch (Exception e) {
			session.put("error_message", "Error sending publication: check data and try again please");
			return ERROR;
		}
		
//		Uploading....
		String path = "pdfs/" + a.getEmail() + "/";
		File folder = new File(path);
		File serverfile = new File(path + this.filename);
		
		try{
			folder.mkdirs();
			InputStream in = new FileInputStream(this.file);
			OutputStream out = new FileOutputStream(serverfile);
                
			byte[] buf = new byte[1024];
			int len;
			while((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
			}

			in.close();
			out.close();
		} catch (Exception e){
			session.put("error_message", "Error updating file: select file and try again please");
			try{
				Art = c.read(Art);
				c.delete(Art);
			}catch(Exception e2){
				session.put("error_message", "Error deleting an artilce without file");
				return ERROR;
			}
			return ERROR;
		}
		
		return SUCCESS;
	}

	public void setPublicationname(String publicationname){
		this.publicationname=publicationname;
	}
	
	public void setComentarticle(String comentarticle){
		this.comentarticle=comentarticle;
	}
	
	public void setTag(String tag){
		this.tag = tag; 
	}
	
	public void setUpload(File file) {
		this.file = file;
	}

	public void setUploadFileName(String filename) {
		this.filename = filename;
	}
}
