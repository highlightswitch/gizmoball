package view;

import model.Drawable;
import model.GameObject;
import model.Model;
import physics.Circle;
import physics.LineSegment;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Observable;
import java.util.Observer;

public  class Board extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private int width;
	private int height;
	private Model gm;

	Board(int w, int h, Model m) {
		// Observe changes in Model
		m.addObserver(this);
		width = w;
		height = h;
		gm = m;
		this.setOpaque(false);
		this.setBorder(BorderFactory.createLineBorder(Color.WHITE));
	}

	// Fix onscreen size
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}

	public void setModel(Model model){ gm = model; }

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

        for(Drawable drawable : gm.getDrawables()){
            //TODO: This will eventually paint a sprite in position instead of drawing a Shape.
            GameObject obj = drawable.getShapeToDraw();
            if(obj != null){
                //If the object exists, draw it scaled up to pixel size.
                for (LineSegment ls : obj.getLines()) {
                    g2.draw(toPixels(ls.toLine2D()));
                }
                for (Circle circle : obj.getCircles()) {
                    g2.draw(toPixels(circle.toEllipse2D()));
                }
            }
        }

	}



    private double toPixels(double coord){
	    return coord * 25;
    }

    private Line2D toPixels(Line2D line){
        return new Line2D.Double(
                toPixels(line.getX1()),
                toPixels(line.getY1()),
                toPixels(line.getX2()),
                toPixels(line.getY2())
        );
    }

    private Ellipse2D toPixels(Ellipse2D ellipse) {

        Rectangle2D originalBounds = ellipse.getBounds2D();
        double newX = toPixels(originalBounds.getX());
        double newY = toPixels(originalBounds.getY());
        double newWidth = toPixels(originalBounds.getWidth());
        double newHeight = toPixels(originalBounds.getHeight());

//        System.out.println("original: " + originalBounds);
//        System.out.println("new:      " + newX + " " + newY + " " + newWidth + " " + newHeight);

        return new Ellipse2D.Double(newX, newY, newWidth, newHeight);
    }

    @Override
	public void update(Observable arg0, Object arg1) {
			repaint();
		}
}
