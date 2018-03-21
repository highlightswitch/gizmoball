package model.util;

import model.gizmo.GizmoType;

import java.util.List;

public class GizmoUtils {

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

	public static String getUnusedName(List<String> usedNames, GizmoType type){

		String newName = "gizmo_0";
		switch (type){
			case FLIPPER:
				newName = "flipper_0";
				break;
			case BALL:
				newName = "ball_0";
				break;
			case CIRCLE_BUMPER:
				newName = "circle_0";
				break;
			case SQUARE_BUMPER:
				newName = "square_0";
				break;
			case TRIANGLE_BUMPER:
				newName = "triangle_0";
				break;
			case ABSORBER:
				newName = "absorber_0";
				break;
		}

		while(usedNames.contains(newName)){
			String[] arr = newName.split("_");
			arr[arr.length -1] = String.valueOf(Integer.parseInt(arr[arr.length -1]) + 1);

			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < arr.length-1; i++)
				sb.append(arr[i]);
			sb.append("_");
			sb.append(arr[arr.length-1]);
			newName = sb.toString();
		}

		return newName;
	}

}
