package model;

import java.util.ArrayList;
import java.util.Arrays;

public class DrawableData {

	//An array list of polygons. A polygon is an array list
	//of x,y positions of the polygons that create a drawable object
	private ArrayList<ArrayList<Double[]>> polygonsData;

	//An array list of cx,cy,r data of the circles that create a drawable object
	private ArrayList<Double[]> circlesData;

	public boolean addPolygon(ArrayList<Double[]> p){
		for (ArrayList<Double[]> poly : polygonsData)
			if(poly.equals(p))
				return false;
		return polygonsData.add(p);
	}

	public boolean addCircle(Double[] c){
		for (Double[] circle : circlesData)
			if(Arrays.equals(circle, c))
				return false;
		return circlesData.add(c);
	}

	public ArrayList<ArrayList<Double[]>> getPolygonsData() {
		return polygonsData;
	}

	public ArrayList<Double[]> getCirclesData() {
		return circlesData;
	}
}
