package Persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import com.mysql.jdbc.PreparedStatement;

import Dominion.*;

public class TagDAO extends CrudDAO<Tag>{

    @Override
    public void create(Tag t) throws SQLException {
    	Broker bk; 
    	Conexion cn=null; 
    	PreparedStatement stmt; 
    	int r=0;
        try {
        	bk = Broker.get(); 
     		cn = bk.getBD();
     		stmt = (PreparedStatement) cn.prepareStatement("INSERT INTO Tag (issue) VALUES (?)");
        	
     		stmt.setString(1, t.getIssue());
     		
     		r=stmt.executeUpdate();
     		
		} catch (Exception e) {
			e.printStackTrace();
		}
        finally{
        	cn.close();
        }
    }

    @Override
    public void delete(Tag t)throws SQLException{
    	Broker bk; 
    	Conexion cn=null; 
    	PreparedStatement stmt; 
    	int r=0;
    	 try {
    		bk = Broker.get(); 
      		cn = bk.getBD();
      		stmt = (PreparedStatement) cn.prepareStatement("DELETE FROM Tag WHERE idTag=?");
      		
      		stmt.setInt(1, t.getIdTag());
      		r=stmt.executeUpdate();
     		
 			
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
    	finally{
    		cn.close();
    	}
    }
 
    @Override
    public Tag read(Tag t) throws SQLException {
    	Broker bk; 
    	Conexion cn=null; 
    	PreparedStatement stmt; 
    	ResultSet r=null;
    	try{
    		bk = Broker.get(); 
      		cn = bk.getBD();
      		stmt = (PreparedStatement) cn.prepareStatement("SELECT * FROM Tag WHERE issue=?");
      		
      		stmt.setString(1, t.getIssue());
      		
      		r=stmt.executeQuery();
      		 if (r != null) {
                 try {
                     while (r.next()) {
                         t.setIdTag(r.getInt("idTag"));
                     }
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
             }
      		r.close();
            
    	}
    	
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	finally{
    		cn.close();
    	}
    	if(!(t.getIdTag() > 0))
    		t=null;
    		
    	return t;
       
        
    }
    

    @Override
    public void update(Tag t)throws SQLException {
    	Broker bk; 
    	Conexion cn=null; 
    	PreparedStatement stmt; 
    	int r=0;
    	
		try {
			bk = Broker.get(); 
      		cn = bk.getBD();
      		stmt = (PreparedStatement) cn.prepareStatement("UPDATE Tag SET issue=? WHERE idTag=?");
      		
      		stmt.setString(1, t.getIssue());
      		stmt.setInt(2, t.getIdTag());
      		
      		r=stmt.executeUpdate();
      		
		} catch (Exception e) {
			e.printStackTrace();
		}     
		finally{
			cn.close();
		}
    }
    
    
    @Override
    public LinkedList<Tag> readAll(Tag t)throws SQLException{
    	
    	Broker bk; 
    	Conexion cn=null; 
    	PreparedStatement stmt; 
    	ResultSet r=null;
    	LinkedList<Tag> listTag = new LinkedList<Tag>();
    	
    	try{
    		bk = Broker.get(); 
      		cn = bk.getBD();
      		stmt = (PreparedStatement) cn.prepareStatement("SELECT * FROM Tag");
      		
      		r=stmt.executeQuery();
      		
      		 if (r != null) {
                 try {
                     while (r.next()) {
                     	Tag tg = new Tag();
                         tg.setIdTag(r.getInt("idTag"));
                         tg.setIssue(r.getString("issue"));
                         listTag.add(tg);
                     }
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
             }
             r.close();
      		
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	finally{
    		cn.close();
    	}
       
        return listTag;
    }
}