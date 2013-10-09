package Dominion;

import java.sql.SQLException;
import java.util.LinkedList;

import utilidades.leer;
import Persistence.ArticleDAO;
import Persistence.CrudDAO;
import Persistence.FollowsDAO;
import Persistence.MessageDAO;
import Persistence.NotificationDAO;
import Persistence.ReviewerTagDAO;
import Persistence.TagDAO;
import Persistence.UserDAO;

public class User {

	protected String email;
	protected String name;
	protected String second_name;
	protected String city;
	protected String birth_date;
	protected String password;
	protected String rol; 


	public User(String email, String password, String name, String sname, String city, String bdate){
		this.email=email;
		this.name=name;
		this.second_name=sname;
		this.city=city;
		this.birth_date=bdate;
		this.password=password;
	}
	public User(){

	}

	public User(String email) throws SQLException{
		CrudDAO<User> c = new UserDAO();
		setEmail(email); 
		User u = c.read(this);
		this.email=u.getEmail();
		this.name=u.getName();
		this.second_name=u.getSecond_name();
		this.city=u.getCity();
		this.birth_date=u.getBirth_date();
		this.password=u.getPassword();
	}



	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSecond_name() {
		return second_name;
	}
	public void setSecond_name(String second_name) {
		this.second_name = second_name;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getBirth_date() {
		return birth_date;
	}
	public void setBirth_date(String birth_date) {
		this.birth_date = birth_date;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
	@Override
	public String toString(){
		String s= getEmail()+": "+name+" "+second_name+", City: "+city+", Birth date:"+birth_date+", Rol:"+rol+", Password: "+password+"\n"; 
		return s;
	}


	public void edit_profile(User u) throws Exception{

		CrudDAO<User> c = new UserDAO();	

		c.update(u);

	}
	public LinkedList<Follows> view_contacts() throws SQLException{


		Follows f = new Follows();
		f.setFollower_mail(this.email);
		CrudDAO<Follows> c=new FollowsDAO();
		LinkedList<Follows> l = c.readAll(f);

		return l;

	}
	public void write_message(Message m) throws SQLException{

		CrudDAO<Notification> notificationdao = new NotificationDAO(); 
		CrudDAO<Message> messageDAO = new MessageDAO(); 

		Notification message_notification = new Notification(m.getEmail_to(), "New message from " + m.getEmail_from() +" ","UNREAD", m.getDate() ); 
		m.setEmail_from(this.getEmail());   
		notificationdao.create(message_notification); 
		messageDAO.create(m);	
	}
	public void add_contact(User u) throws SQLException{
		// Follow
		CrudDAO<Follows> followsdao = new FollowsDAO(); 
		Follows f = new Follows(); 
		f.setFollowed_mail(u.getEmail()); 
		f.setFollower_mail(this.getEmail()); 
		followsdao.create(f); 
		System.out.println(f.toString()); 
		// Notificacion
		CrudDAO<Notification> notificationdao = new NotificationDAO(); 
		Notification follower_notification = new Notification(u.getEmail(), "New follower: " + this.getEmail() +" ","UNREAD", leer.fecha() ); 
		notificationdao.create(follower_notification); 

	}

	public User log(String email, String password) throws Exception{

		User u = new User(email);

		if (!u.password.equals(password)) u=null;

		return u; 
	}

	public LinkedList<ReviewerTag> search_tag(String tag) throws SQLException {
		// Devolvemos una lista de IDs para no saturar el sistema con consultas
		// Pero lo suyo ser�a devolver una lista de Users
		Tag t = new Tag();
		t.setIssue(tag);
		CrudDAO<Tag> c = new TagDAO();
		t=c.read(t);

		ReviewerTag rt= new ReviewerTag();
		rt.setIdTag(t.getIdTag());
		CrudDAO<ReviewerTag> c2 = new ReviewerTagDAO();
		LinkedList<ReviewerTag> users = c2.readAll(rt);

		return users;
	}
	public LinkedList<Notification> get_notifications() throws SQLException{
		String notify=""; 
		CrudDAO<Notification> notificationdao = new NotificationDAO(); 

		Notification n = new Notification();
		n.setMail_notified(this.email);
		n.setState("UNREAD");

		LinkedList<Notification> notify_list = notificationdao.readAll(n);
		@SuppressWarnings("unchecked")
		LinkedList<Notification> notify_list2 = (LinkedList<Notification>) notify_list.clone();

		//		while(!notify_list.isEmpty())
		//			notify=notify+notify_list.remove().toString() + "\n"; 

		update_notifications(notify_list2); 

		return notify_list; 
	}
	public void update_notifications(LinkedList<Notification> notify_list) throws SQLException{

		CrudDAO<Notification> c = new NotificationDAO();
		while(!notify_list.isEmpty()){
			notify_list.element().setState("READ");
			c.update(notify_list.remove());
		}

	}
	public LinkedList<Message> get_Messages() throws SQLException{
		CrudDAO<Message> c = new MessageDAO();
		LinkedList<Message> message_list = new LinkedList<Message>();

		Message m= new Message();
		m.setEmail_to(this.email);
		message_list=c.readAll(m);

		return message_list; 
	}
	public User search_userById(String name2) {
		User aux = new User(); 
		CrudDAO<User> userdao = new UserDAO(); 
		aux.setEmail(name2);
		User a = null; 
		try {
			aux = new User(name2);
			a = userdao.read(aux); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return a;
	}
	public void deluser(User newFriend) {
		CrudDAO<User> userdao = new UserDAO(); 
		try {
			userdao.delete(newFriend);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}
	public void addTag(String tag) {
		CrudDAO<Tag> dao_tag = new TagDAO();
		Tag t = new Tag();
		t.setIssue(tag.toUpperCase());

		try {
			dao_tag.create(t);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public User getProfile(String friend) {

		User u=null; 

		try {
			u = new User(friend);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return u; 
	}
	public LinkedList<Article> getPublications(User userfriend) {

		CrudDAO<Article> articledao = new ArticleDAO(); 
		LinkedList<Article> al = new LinkedList<Article>(); 
		LinkedList<ArticleReview> arl = new LinkedList<ArticleReview>(); 

		Reviewer r = null; 
		Article a = new Article(); 
		ArticleReview ar = new ArticleReview(); 

		try {

			if (userfriend.getRol().equals("A")){	
				a.setId_author(userfriend.getEmail()); 
				al = articledao.readAll(a);	
			}
			else if (userfriend.getRol().equals("R")){
				r = new Reviewer(userfriend.getEmail());
				ar.setReviewer_mail(userfriend.getEmail());
				arl = r.get_resolved_reviews();
				while(!arl.isEmpty()){
					a = new Article(arl.remove().getIdArticle());
					al.add(a); 
				}
			}
			else{
				a = new Article(); 
				a.setId(-1); 
				a.setTitle("I don't think so");
				a.setBody("A Publisher does not have any article"); 
				al.add(a); 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return al;
	}



}




