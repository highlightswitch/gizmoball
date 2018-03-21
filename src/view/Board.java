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
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public  class Board extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private int width;
	private int height;
	private IModel iModel;
	private String mode;

	Board(int w, int h, IModel m) {
		// Observe changes in Model
		m.addObserver(this);
		width = w;
		height = h;
		iModel = m;
		this.setOpaque(true);
	}

	// Fix onscreen size
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}

	public void setModel(IModel model){
		iModel = model;
		iModel.addObserver(this);
		repaint();
	}

	public IModel getModel(){
		return iModel;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		ArrayList<Drawable> drawableObjects = iModel.getDrawables();
		for(Drawable drawable : drawableObjects) {
			DrawingData data = drawable.getDrawingData();
			draw(data, g2, true);
		}

        if(getMode().equals("Build")) {

			this.setBackground(new Color(Color.ORANGE.getRed(), Color.ORANGE.getGreen()-20, Color.ORANGE.getBlue()+20));

			//draws grid-lines
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(0.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{10.0f}, 0.0f));

            for (int i = 0; i < 20; i++) {
                g2.drawLine(i * 25, 0, i * 25, width);
            }
            for (int i = 0; i < 20; i++) {
                g2.drawLine(0, i * 25, height, i * 25);
            }

			g2.drawLine(20 * 25 -1, 0, 20 * 25 -1, width);
			g2.drawLine(0, 20 * 25 -1, height, 20 * 25 -1);


		} else {
			this.setBackground(new Color(Color.PINK.getRed(), Color.PINK.getGreen()-20, Color.PINK.getBlue()-20));
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
		if(data != null){
			//If the data exists, loop through and draw the polygons and circles of this shape

			Color color = getColorOfData(data);
			ArrayList<ArrayList<Double[]>> polygons = data.getPolygonsData();

			for(int i = 0; i < polygons.size(); i++){
				ArrayList<Double[]> polyData = polygons.get(i);
				//Create the path of the polygon.
				//Path is used instead of Polygon since Polygon can't handle doubles
				Path2D path = new Path2D.Double();
				//Set the starting point to the first point of the shape
				path.moveTo(polyData.get(0)[0], polyData.get(0)[1]);
				//For each point after the starting point, draw a line to it.
				for (int j = 1; j < polyData.size(); j++) {
					path.lineTo(polyData.get(j)[0], polyData.get(j)[1]);
				}
				//Draw a line back to the starting point
				path.closePath();

				if(data.drawPolygonsDifferentColours() && i % 2 == 1)
					color = getDifferentColour(color);

				//Draw the scaled up shape
				drawShape(path, color, g2, fill);
			}

			for (Double[] circleData : data.getCirclesData()) {
				//Create an ellipse using its rectangular bounds
				Ellipse2D circle = new Ellipse2D.Double(circleData[0] - circleData[2], circleData[1] - circleData[2], 2 * circleData[2], 2 * circleData[2]);

				//Draw the scaled up shape
				drawShape(circle, color, g2, fill);
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

	private Color getColorOfData(DrawingData data){
		return new Color(data.getRedValue(), data.getGreenValue(), data.getBlueValue());
	}

	private Color getDifferentColour(Color color){
		int r = color.getRed() > 225 ? color.getRed() - 30 : color.getRed() + 30;
		int g = color.getGreen() > 225 ? color.getGreen() - 30 : color.getGreen() + 30;
		int b = color.getBlue() > 225 ? color.getBlue() - 30 : color.getBlue() + 30;
		return new Color(r,g,b);
	}

    private Shape toPixels(Shape shape){
		return AffineTransform.getScaleInstance(25, 25).createTransformedShape(shape);
	}

	void updateMode(String m){
        mode = m;
    }

    private String getMode(){
	    return mode;
    }

    @Override
	public void update(Observable arg0, Object arg1) {
			repaint();
		}
}
