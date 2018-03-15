package view;

import main.Main;
import model.Drawable;
import model.DrawingData;
import model.IModel;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public  class Board extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private int width;
	private int height;
	private IModel iModel;

	Board(int w, int h, IModel m) {
		// Observe changes in Model
		m.addObserver(this);
		width = w;
		height = h;
		iModel = m;
		this.setOpaque(false);
		this.setBorder(BorderFactory.createLineBorder(Color.WHITE));
	}

	// Fix onscreen size
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}

	public void setModel(IModel model){
		iModel = model;
		iModel.addObserver(this);
	}

	public IModel getModel(){
		return iModel;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		ArrayList<Drawable> drawableObjects = iModel.getDrawables();
		for(Drawable drawable : drawableObjects) {
			DrawingData data = drawable.getDrawingData();
			draw(data, g2, true);
		}

		//If debug mode is on, draw the GameObjects as well
		if(Main.debugMode){
			ArrayList<Drawable> dataList = iModel.getDebugDrawables();
			for(Drawable drawable : dataList) {
				DrawingData data = drawable.getDrawingData();
				draw(data, g2, false);
			}
		}

	}

	private void draw(DrawingData data, Graphics2D g2, boolean fill){
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if(data != null){
			//If the data exists, loop through and draw the polygons and circles of this shape

			for(ArrayList<Double[]> polyData : data.getPolygonsData()){
				if(polyData.size() > 0) {

					//Create the path of the polygon.
					//Path is used instead of Polygon since Polygon can't handle doubles
					Path2D path = new Path2D.Double();
					//Set the starting point to the first point of the shape
					path.moveTo(polyData.get(0)[0], polyData.get(0)[1]);
					//For each point after the starting point, draw a line to it.
					for (int i = 1; i < polyData.size(); i++) {
						path.lineTo(polyData.get(i)[0], polyData.get(i)[1]);
					}
					//Draw a line back to the starting point
					path.closePath();

					//Draw the scaled up shape
					drawShape(path, stringToColor(data.getColourString()), g2, fill);
				}
			}

			for (Double[] circleData : data.getCirclesData()) {
				//Create an ellipse using its rectangular bounds
				Ellipse2D circle = new Ellipse2D.Double(circleData[0] - circleData[2], circleData[1] - circleData[2], 2 * circleData[2], 2 * circleData[2]);

				//Draw the scaled up shape
				drawShape(circle, stringToColor(data.getColourString()), g2, fill);
			}
		}
	}

	private void drawShape(Shape shape, Color color, Graphics2D g2, boolean fill){
		if(fill) {
			g2.setColor(color);
			g2.fill(toPixels(shape));
		}else {
			g2.setColor(Color.CYAN);
			g2.draw(toPixels(shape));
		}
	}

    private Shape toPixels(Shape shape){
		return AffineTransform.getScaleInstance(25, 25).createTransformedShape(shape);
	}

	private static Color stringToColor(final String value) {
		if (value == null) {
			return Color.black;
		}
		try {
			// get color by hex or octal value
			return Color.decode(value);
		} catch (NumberFormatException nfe) {
			// if we can't decode lets try to get it by name
			try {
				// try to get a color by name using reflection
				final Field f = Color.class.getField(value);

				return (Color) f.get(null);
			} catch (Exception ce) {
				// if we can't get any color return black
				return Color.black;
			}
		}
	}

    @Override
	public void update(Observable arg0, Object arg1) {
			repaint();
		}
}
