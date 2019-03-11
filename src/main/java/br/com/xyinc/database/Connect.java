package br.com.xyinc.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.xyinc.point_of_interest.PointOfInterest;

public class Connect
{	
	private Connection con = null;
	
	public Connect() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
         String url = "jdbc:mysql://localhost:3306/xy_inc?useTimezone=true&serverTimezone=UTC";
         String user = "root";
         String password = "Root@2019";

         Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
         con = DriverManager.getConnection(url, user, password);
	}

	public void closeConnection() throws SQLException
	{
		con.close();
	}
	
	public void insertPointOfInterest(String name, int coordinateX, int coordinateY)
	{
		try {
			PreparedStatement preparedStatement = con.prepareStatement("Insert into point_of_interest values (?,?,?)");
	       
	        preparedStatement.setString(1, name);
	        preparedStatement.setInt(2, coordinateX);
			preparedStatement.setInt(3, coordinateY);
	       
	        preparedStatement.executeUpdate();
        }
        catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Connect.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
	}
	
	public List<PointOfInterest> getAllPointOfInterests()
	{
		List<PointOfInterest> poiList = new ArrayList<PointOfInterest>();
		
        try {
        	PreparedStatement preparedStatement = con.prepareStatement("Select * from point_of_interest");
	        ResultSet rs = preparedStatement.executeQuery();
	
	        while (rs.next()) {
	        	PointOfInterest poi = new PointOfInterest();
	    	    poi.setName(rs.getString(1));
	    	    poi.setCoordinateX(rs.getInt(2));
	    	    poi.setCoordinateY(rs.getInt(3));
	    	    poiList.add(poi);
	        }
        }
        catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Connect.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        
        return poiList;
	}
	
}
