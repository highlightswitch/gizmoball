package view;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import model.*;
import model.gizmo.Ball;
import physics.LineSegment;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public  class Board extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	protected int width;
	protected int height;
	protected Model gm;

	public Board(int w, int h, Model m) {
		// Observe changes in Model
		m.addObserver(this);
		width = w;
		height = h;
		gm = m;
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}

	// Fix onscreen size
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

        for(Drawable tile : gm.getDrawableTiles()){
            //TODO: This will eventually paint a sprite in position instead of drawing a Shape.
            GameObject obj = tile.getShapeToDraw();
            if(obj != null){
                //If the object exists, draw it scaled up to pixel size.
                for (LineSegment ls : obj.getLines()) {
                    g2.draw(toPixels(ls.toLine2D()));
                }
            }
        }
		
		Ball b = gm.getBall();
		if (b != null) {
			g2.setColor(b.getColour());
			int x = (int) (b.getExactX() - b.getRadius());
			int y = (int) (b.getExactY() - b.getRadius());
			int width = (int) (2 * b.getRadius());
			g2.fillOval(x, y, width, width);
		}
	}


    public double toPixels(double coord){
	    return coord * 20;
    }

    public Line2D toPixels(Line2D line){
        return new Line2D.Double(
                toPixels(line.getX1()),
                toPixels(line.getY1()),
                toPixels(line.getX2()),
                toPixels(line.getY2())
        );
    }

    @Override
	public void update(Observable arg0, Object arg1) {
			repaint();
		}
	
}
