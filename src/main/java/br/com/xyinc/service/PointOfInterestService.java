package br.com.xyinc.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import br.com.xyinc.database.Connect;
import br.com.xyinc.point_of_interest.PointOfInterest;

@Path("/pointOfInterest")
public class PointOfInterestService {

	@POST
	@Path("/insert")
	@Produces("application/json")
	public List<String> insert(@QueryParam("name") String name, @QueryParam("coordinateX") String coordinateX, @QueryParam("coordinateY") String coordinateY)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		List<String> result = new ArrayList<String>();
		int valueX, valueY;
		
		// Check if parameters are valid
		if(name == null || coordinateX == null || coordinateY == null) {
			result.add("Missing parameter.");
			return result;
		}
		
		// Check if parameters are numbers
		try
	    {
			valueX = Integer.parseInt(coordinateX);
		    valueY = Integer.parseInt(coordinateY);
	    }
	    catch (NumberFormatException nfe)
	    {
	    	result.add("Wrong parameter format. NumberFormatException.");
			return result;
	    }
		
		// Check if parameters are not negative
		if(valueX < 0 || valueY < 0) {
			result.add("Invalid parameter value. Negative numbers are not allowed.");
			return result;
		}
		
		// Search for all Point of Interest
		Connect connect = new Connect();
		connect.insertPointOfInterest(name, valueX, valueY);
        connect.closeConnection();
        
        // Return statement
        result.add("Point of Interest " + name + " inserted with success in database.");
		return result;
	}

	@GET
	@Path("/list")
	@Produces("application/json")
	public List<PointOfInterest> list() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		// Search for all Point of Interest
		Connect connect = new Connect();
		List<PointOfInterest> poiList = connect.getAllPointOfInterests();
        connect.closeConnection();
        
        // Return statement
        return poiList;
	}

	@GET
	@Path("/maxDistance")
	@Produces("application/json")
	public List<String> calculateDistance(@QueryParam("coordinateX") String coordinateX, @QueryParam("coordinateY") String coordinateY, @QueryParam("distance") String distance) 
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, NumberFormatException
	{
		List<String> result = new ArrayList<String>();
		int valueX, valueY, maxDistance;
		
		// Check if parameters are valid
		if(coordinateX == null || coordinateY == null || distance == null) {
			result.add("Missing parameter.");
			return result;
		}
		
		// Check if parameters are numbers
		try
	    {
			valueX = Integer.parseInt(coordinateX);
		    valueY = Integer.parseInt(coordinateY);
		    maxDistance = Integer.parseInt(distance);
	    }
	    catch (NumberFormatException nfe)
	    {
	    	result.add("Wrong parameter format. NumberFormatException.");
			return result;
	    }
		
		// Check if parameters are not negative
		if(valueX < 0 || valueY < 0 || maxDistance < 0) {
			result.add("Invalid parameter value. Negative numbers are not allowed.");
			return result;
		}
		
		// Search for all Point of Interest
		Connect connect = new Connect();
		List<PointOfInterest> poiList = connect.getAllPointOfInterests();
        connect.closeConnection();
        
        // Calculate distance between initial POI and retrieved POIs from database
        for(int p = 0; p < poiList.size(); p++) {
        	PointOfInterest poi = poiList.get(p);
        	int diffX = Math.abs(poi.getCoordinateX() - valueX);
        	int diffY = Math.abs(poi.getCoordinateY() - valueY);
        	if((diffX + diffY) <= maxDistance) {
        		result.add(poi.getName());
        	}
        }
        
        // Return statement
        return result;
	}
	
}
