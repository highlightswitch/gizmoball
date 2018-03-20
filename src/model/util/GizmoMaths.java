package model.util;

public class GizmoMaths {

	public static double clamp(double val, double min, double max){
		if(val < min) return min;
		if(val > max) return max;
		return val;
	}

	public static int[] colourStringParser(String rgbString){
		int iOfStartR = rgbString.indexOf('=');
		int iOfEndR = rgbString.indexOf(",");
		int r = Integer.parseInt(rgbString.substring(iOfStartR+1, iOfEndR));

		int iOfStartG = rgbString.indexOf('=', iOfEndR);
		int iOfEndG = rgbString.indexOf(',', iOfStartG);
		int g = Integer.parseInt(rgbString.substring(iOfStartG+1, iOfEndG));


		int iOfStartB = rgbString.indexOf('=', iOfEndG);
		int iOfEndB = rgbString.indexOf(']', iOfStartB);
		int b = Integer.parseInt(rgbString.substring(iOfStartB+1, iOfEndB));

		return new int[]{r,g,b};
	}

}
