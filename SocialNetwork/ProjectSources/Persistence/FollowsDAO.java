package Persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import com.mysql.jdbc.PreparedStatement;

import Dominion.*;

public class FollowsDAO extends CrudDAO<Follows>{

    @Override
    public void create(Follows f)throws SQLException{
    	Broker bk; 
    	Conexion cn=null; 
    	PreparedStatement stmt; 
    	int r=0;
        try {
        	bk = Broker.get(); 
     		cn = bk.getBD();
     		stmt = (PreparedStatement) cn.prepareStatement("INSERT INTO Follows" +
    				" (follower_mail, followed_mail) VALUES (?,?)");
     		stmt.setString(1, f.getFollower_mail()); 
			stmt.setString(2, f.getFollowed_mail()); 
			
			r = stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
        finally{
        	cn.close();
        }
    }

    @Override
    public void delete(Follows f)throws SQLException{ 
    	Broker bk; 
    	Conexion cn=null; 
    	PreparedStatement stmt; 
    	int r=0;
    	 try {
    		bk = Broker.get(); 
      		cn = bk.getBD();
      		stmt = (PreparedStatement) cn.prepareStatement("DELETE FROM Follows WHERE follower_mail=? AND followed_mail=?");

      		stmt.setString(1, f.getFollower_mail()); 
			stmt.setString(2, f.getFollowed_mail()); 
			
			r = stmt.executeUpdate();
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
    	finally{
    		cn.close();
    	}
    }

    
    @Override
    public Follows read(Follows f) throws SQLException { // NO se usar�
    	Broker bk; 
    	Conexion cn=null; 
    	PreparedStatement stmt; 
    	ResultSet rs=null;
    	Follows FReturn = new Follows();
        
        try {
        	bk = Broker.get(); 
      		cn = bk.getBD();
      		stmt = (PreparedStatement) cn.prepareStatement("SELECT * FROM Follows WHERE follower_mail=? AND followed_mail=?");
      		
      		stmt.setString(1, f.getFollower_mail()); 
			stmt.setString(2, f.getFollowed_mail()); 
			
			rs = stmt.executeQuery();
			 if (rs != null) {
     	        try {
     	            while (rs.next()) {
     	                FReturn.setFollowed_mail(rs.getString("followed_mail"));
     	                FReturn.setFollower_mail(rs.getString("follower_mail"));
     	            }
     	        } catch (SQLException e) {
     	            e.printStackTrace();
     	        }
			 }
			 rs.close();
			 
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        finally{
        	 cn.close();
        	 
        }
        return FReturn;
        
    }
    
   

  
    @Override
    public void update(Follows f) {
    	//No se puede actualizar un seguidor. O se crea o se borra
    }


    @Override
    public LinkedList<Follows> readAll(Follows t)throws SQLException{
    	// Todos los que sigo
    	Broker bk; 
    	Conexion cn=null; 
    	PreparedStatement stmt; 
    	ResultSet rs;
    	LinkedList<Follows> listFol = new LinkedList<Follows>();
    	
    	try{
    		bk = Broker.get(); 
      		cn = bk.getBD();
      		stmt = (PreparedStatement) cn.prepareStatement("SELECT * FROM Follows WHERE follower_mail=?");
      		
      		stmt.setString(1, t.getFollower_mail()); 
      		
      		rs= stmt.executeQuery();
      		if (rs != null) {
                 try {
                     while (rs.next()) {
                     	Follows f = new Follows();
                         f.setFollowed_mail(rs.getString("followed_mail"));
                         f.setFollower_mail(rs.getString("follower_mail"));
                         listFol.add(f);
                     }
                 } catch (SQLException e) {
                     e.printStackTrace();
                 }
            }
      		rs.close();
      		
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	finally{
    		cn.close();
    	}
       
    	return listFol;
    }
}