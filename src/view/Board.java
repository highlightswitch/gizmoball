package view;

import main.Main;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
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
		g2.setColor(Color.WHITE);

		ArrayList<Drawable> drawableObjects = iModel.getDrawables();
		for(Drawable drawable : drawableObjects) {
			DrawingData data = drawable.getDrawingData();
			draw(data, g2, true);
		}

		//If debug mode is on, draw the GameObjects as well
		if(Main.debugMode){
			g2.setColor(Color.CYAN);
			ArrayList<Drawable> dataList = iModel.getDebugDrawables();
			for(Drawable drawable : dataList) {
				DrawingData data = drawable.getDrawingData();
				draw(data, g2, false);
			}
			g2.setColor(Color.WHITE);
		}

	}

	private void draw(DrawingData data, Graphics2D g2, boolean fill){
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
					if(fill)
						g2.fill(toPixels(path));
					else
						g2.draw(toPixels(path));
				}
			}

			for (Double[] circleData : data.getCirclesData()) {
				//Create an ellipse using its rectangular bounds
				Ellipse2D circle = new Ellipse2D.Double(circleData[0] - circleData[2], circleData[1] - circleData[2], 2 * circleData[2], 2 * circleData[2]);

				//Draw the scaled up shape
				if(fill)
					g2.fill(toPixels(circle));
				else
					g2.draw(toPixels(circle));
			}
		}
	}

    private Shape toPixels(Shape shape){
		return AffineTransform.getScaleInstance(25, 25).createTransformedShape(shape);
	}

    @Override
	public void update(Observable arg0, Object arg1) {
			repaint();
		}
}
