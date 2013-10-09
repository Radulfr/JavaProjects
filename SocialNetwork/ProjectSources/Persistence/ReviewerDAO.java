package Persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import com.mysql.jdbc.PreparedStatement;

import Dominion.*;

public class ReviewerDAO extends CrudDAO<Reviewer>{

    @Override
    public void create(Reviewer revisor) throws SQLException {
    	
    	Broker bk; 
    	Conexion cn = null; 
    	PreparedStatement stmt;
     	try{
     		bk = Broker.get(); 
     		cn = bk.getBD();
     		
     		stmt = (PreparedStatement) cn.prepareStatement("INSERT INTO User (email, name, second_name, birth_date, city, pass, rol) VALUES (?, ?, ?, ?, ?, ?, ?)");
     		stmt.setString(1, revisor.getEmail());
     		stmt.setString(2, revisor.getName());
     		stmt.setString(3, revisor.getSecond_name());
     		stmt.setString(4, revisor.getBirth_date());
     		stmt.setString(5, revisor.getCity());
     		stmt.setString(6, revisor.getPassword());
     		stmt.setString(7, "R");
	        stmt.executeUpdate();
	        
	        stmt = (PreparedStatement) cn.prepareStatement("INSERT INTO Reviewer " +
	        		"(email, karma, VIP) VALUES (?, ?, ?)");
	        stmt.setString(1, revisor.getEmail());
	        stmt.setInt(2, revisor.getKarma());
	        stmt.setInt(3, revisor.getVIP());
	        stmt.executeUpdate();
	        
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			cn.close();
		}
    }

    @Override
    public void delete(Reviewer revisor) throws SQLException {
    	
    	Broker bk; 
    	Conexion cn = null; 
    	PreparedStatement stmt;
     	try{
     		bk = Broker.get(); 
     		cn = bk.getBD();
     		
     		stmt = (PreparedStatement) cn.prepareStatement("DELETE FROM Reviewer WHERE email=?");
     		stmt.setString(1, revisor.getEmail());
     		stmt.executeUpdate();
     		
     		
     		stmt = (PreparedStatement) cn.prepareStatement("DELETE FROM User WHERE email=?");
     		stmt.setString(1, revisor.getEmail());
     		stmt.executeUpdate();
     		
 		} catch (Exception e) {
 			e.printStackTrace();
 		}finally{
 			cn.close();
 		}
    }

    @Override
    public Reviewer read(Reviewer revisor) throws SQLException {
    	
    	Broker bk; 
    	Conexion cn = null; 
    	PreparedStatement stmt;
    	ResultSet rs = null;
    	
     	try{
     		bk = Broker.get(); 
     		cn = bk.getBD();
     		
     		stmt = (PreparedStatement) cn.prepareStatement("SELECT * FROM Reviewer WHERE email=?");
     		stmt.setString(1, revisor.getEmail());
     		rs = stmt.executeQuery();
     	}catch(Exception e){
     		e.printStackTrace();
     	}finally{
     		cn.close();
     	}
     	
        if (rs != null) {
            try {
                while (rs.next()) {
                    revisor.setKarma(rs.getInt("karma"));
                    revisor.setVIP(rs.getInt("VIP"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        rs.close();
        
        ResultSet rs2 = null;
        try{
	        bk = Broker.get(); 
	 		cn = bk.getBD();
	 		stmt = (PreparedStatement) cn.prepareStatement("SELECT * FROM User WHERE email=?");
	 		stmt.setString(1, revisor.getEmail());
	 		rs2 = stmt.executeQuery();
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	cn.close();
        }
 		
        if (rs2 != null) {
            try {
                while (rs2.next()) {
                    revisor.setName(rs2.getString("name"));
                    revisor.setSecond_name(rs2.getString("second_name"));
                    revisor.setBirth_date(rs2.getString("birth_date"));
                    revisor.setCity(rs2.getString("city"));
                    revisor.setPassword(rs2.getString("pass"));
					revisor.setRol(rs2.getString("rol"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        rs2.close();
        return revisor;
    }

    @Override
    public void update(Reviewer revisor) {
    	
    	Broker bk; 
    	Conexion cn = null; 
    	PreparedStatement stmt=null;

    	try{
    		bk = Broker.get(); 

    		cn = bk.getBD();

     		stmt = (PreparedStatement) cn.prepareStatement("UPDATE Reviewer SET karma=? WHERE email=?");
     		stmt.setInt(1, revisor.getKarma()); 
     		stmt.setString(2, revisor.getEmail()); 
     		
     		stmt.executeUpdate(); 
     		
     		stmt = (PreparedStatement) cn.prepareStatement("UPDATE User SET name=?, second_name=?, birth_date=?, city=?, pass=? WHERE email=?");
     		stmt.setString(1, revisor.getName()); 
     		stmt.setString(2, revisor.getSecond_name());
     		stmt.setString(3, revisor.getBirth_date());
     		stmt.setString(4, revisor.getCity()); 
     		stmt.setString(5, revisor.getPassword()); 
     		stmt.setString(6, revisor.getEmail()); 
     		
     		stmt.executeUpdate(); 

		} catch (SQLException | NoHayConexionesException e) {
			e.printStackTrace();
		}       
    }

	@Override
	public LinkedList<Reviewer> readAll(Reviewer obj) throws SQLException {
		
		Broker bk; 
    	Conexion cn = null; 
    	PreparedStatement stmt;
    	ResultSet rs = null;
    	
     	try{
     		bk = Broker.get(); 
     		cn = bk.getBD();
     		
     		stmt = (PreparedStatement) cn.prepareStatement("SELECT * FROM Reviewer");
     		rs = stmt.executeQuery();
     	}catch(Exception e){
     		e.printStackTrace();
     	}finally{
     		cn.close();
     	}
     	
    	LinkedList<Reviewer> listRev = new LinkedList<Reviewer>();
    	ResultSet rs2 = null;
        if (rs != null) {
            try {
                while (rs.next()) {
                	Reviewer a = new Reviewer();
                    a.setEmail(rs.getString("email"));
                    a.setKarma(rs.getInt("karma"));
                    a.setVIP(rs.getInt("VIP"));
                    try{
	                    bk = Broker.get(); 
	             		cn = bk.getBD();
	             		
	             		stmt = (PreparedStatement) cn.prepareStatement("SELECT * FROM User WHERE email=?");
	             		stmt.setString(1, a.getEmail());
	             		rs2 = stmt.executeQuery();
                    }catch(Exception e){
                    	e.printStackTrace();
                    }finally{
                    	cn.close();
                    }
                    
                    if (rs2 != null) {
                        try {
                            while (rs2.next()) {
                                a.setName(rs2.getString("name"));
                                a.setSecond_name(rs2.getString("second_name"));
                                a.setBirth_date(rs2.getString("birth_date"));
                                a.setCity(rs2.getString("city"));
                                a.setPassword(rs2.getString("pass"));
            					a.setRol(rs2.getString("rol"));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    rs2.close();
					listRev.add(a);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        rs.close();
        return listRev;
	}
}
