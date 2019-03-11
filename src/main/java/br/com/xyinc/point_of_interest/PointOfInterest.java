package br.com.xyinc.point_of_interest;

import java.io.Serializable;

public class PointOfInterest implements Serializable
{
	private static final long serialVersionUID = -3842844978617110554L;

	String name;
	int coordinate_x;
	int coordinate_y;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCoordinateX() {
		return coordinate_x;
	}

	public void setCoordinateX(int coordinate_x) {
		this.coordinate_x = coordinate_x;
	}

	public int getCoordinateY() {
		return coordinate_y;
	}

	public void setCoordinateY(int coordinate_y) {
		this.coordinate_y = coordinate_y;
	}

}
