package model;

import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Arrays;

public class DrawingData {

	//An array list of polygons. A polygon is an array list
	//of x,y positions of the polygons that create a drawable object
	private ArrayList<ArrayList<Double[]>> polygonsData;

	//An array list of cx,cy,r data of the circles that create a drawable object
	private ArrayList<Double[]> circlesData;

	//The colour of the drawable object
	int r;
	int g;
	int b;

	public DrawingData(){
		polygonsData = new ArrayList<>();
		circlesData = new ArrayList<>();
	}

	public void addPolygon(ArrayList<Double[]> p){
		for (ArrayList<Double[]> poly : polygonsData)
			if(poly.equals(p))
				return;
		polygonsData.add(p);
	}

	public void addCircle(Double[] c){
		for (Double[] circle : circlesData)
			if(Arrays.equals(circle, c))
				return;
		circlesData.add(c);
	}

	public void setColour(String rgbString){

		int iOfStartR = rgbString.indexOf('=');
		int iOfEndR = rgbString.indexOf(",");
		this.r = Integer.parseInt(rgbString.substring(iOfStartR+1, iOfEndR));

		int iOfStartG = rgbString.indexOf('=', iOfEndR);
		int iOfEndG = rgbString.indexOf(',', iOfStartG);
		this.g = Integer.parseInt(rgbString.substring(iOfStartG+1, iOfEndG));


		int iOfStartB = rgbString.indexOf('=', iOfEndG);
		int iOfEndB = rgbString.indexOf(']', iOfStartB);
		this.b = Integer.parseInt(rgbString.substring(iOfStartB+1, iOfEndB));


		//TODO:Make parser into regex check
//		String regex = "[\\[r=(\\d{1,3}),g=(\\d{1,3}),b=(\\d{1,3})\\]]";
//
//		Matcher matcher = Pattern.compile(regex).matcher(str);
//		if(matcher.find() && matcher.groupCount() == 2) {
//
//		}
	}

	public ArrayList<ArrayList<Double[]>> getPolygonsData() {
		return polygonsData;
	}

	public ArrayList<Double[]> getCirclesData() {
		return circlesData;
	}

	public int getRedValue(){
		return r;
	}
	public int getGreenValue(){
		return g;
	}
	public int getBlueValue(){
		return b;
	}

	public DrawingData translate(double[] translation){
		ArrayList<ArrayList<Double[]>> newPolygonsData = new ArrayList<>();
		for(ArrayList<Double[]> poly : polygonsData){
			ArrayList<Double[]> newPolyPoints = new ArrayList<>();
			for(Double[] point : poly){
				newPolyPoints.add(new Double[]{point[0] + translation[0], point[1] + translation[1]});
			}
			newPolygonsData.add(newPolyPoints);
		}

		ArrayList<Double[]> newCirclesData = new ArrayList<>();
		for(Double[] circleData : circlesData){
			newCirclesData.add(new Double[]{circleData[0] + translation[0], circleData[1] + translation[1], circleData[2]});
		}

		polygonsData = newPolygonsData;
		circlesData = newCirclesData;

		return this;
	}

	public DrawingData translate(int[] translation) {
		return translate(new double[]{translation[0], translation[1]});
	}

	public DrawingData rotateAroundPivotByRadians(double[] pivot, double rotationAmount){
		AffineTransform rotation = AffineTransform.getRotateInstance(rotationAmount, pivot[0], pivot[1]);

		ArrayList<ArrayList<Double[]>> newPolygonsData = new ArrayList<>();
		for(ArrayList<Double[]> poly : polygonsData){
			ArrayList<Double[]> newPolyPoints = new ArrayList<>();
			for(Double[] point : poly){
				double[] newPoint = new double[2];
				rotation.transform(toPrimitive(point), 0, newPoint, 0, 1);
				newPolyPoints.add(toBoxed(newPoint));
			}
			newPolygonsData.add(newPolyPoints);
		}

		ArrayList<Double[]> newCirclesData = new ArrayList<>();
		for(Double[] circleData : circlesData){
			double[] newData = new double[3];
			rotation.transform(new double[]{circleData[0], circleData[1]}, 0, newData, 0, 1);
			newData[2] = circleData[2];
			newCirclesData.add(toBoxed(newData));
		}

		polygonsData = newPolygonsData;
		circlesData = newCirclesData;

		return this;

	}

	public DrawingData rotateAroundPivotByRadians(int[] pivot, double rotationAmount) {
		return rotateAroundPivotByRadians(new double[]{pivot[0], pivot[1]}, rotationAmount);
	}


	private Double[] toBoxed(double[] passed){
		Double[] boxed = new Double[passed.length];
		for(int i = 0; i < passed.length; i++){
			boxed[i] = passed[i];
		}
		return boxed;
	}

	private double[] toPrimitive(Double[] passed){
		double[] primitives = new double[passed.length];
		for(int i = 0; i < passed.length; i++){
			primitives[i] = passed[i];
		}
		return primitives;
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Circles \n");
		for (Double[] doubles : circlesData){
			sb.append("    ");
			sb.append(doubles[0] + " " + doubles[1] + " " + doubles[2] + "\n");
		}
		sb.append("\n");
		sb.append("Polygons \n");
		for (ArrayList<Double[]> poly : polygonsData){
			sb.append("    Poly \n");
			for (Double[] doubles : poly){
				sb.append("        ");
				sb.append(doubles[0] + " " + doubles[1] + "\n");
			}
		}

		return sb.toString();
	}

//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null || getClass() != obj.getClass())
//			return false;
//		DrawingData data = (DrawingData) obj;
//		return polygonsData == data.polygonsData && circlesData == data.circlesData;
//	}

}
