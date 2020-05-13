package shapes;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.StrokeLineCap;

import java.util.ArrayList;



public class NPFreeHand extends NPShape{

    private boolean hasBegunPath = false;
    private ArrayList<Point2D> points = new ArrayList<>();

    public NPFreeHand() {
        super(null);
    }

    @Override
    public void process(double x, double y, GraphicsContext bufferGraphics){
        if (!hasBegunPath){
            bufferGraphics.beginPath();
            hasBegunPath = true;
        }
        bufferGraphics.setLineCap(StrokeLineCap.ROUND);
        points.add(new Point2D(x, y));
        bufferGraphics.lineTo(x, y);
        bufferGraphics.stroke();
        bufferGraphics.setLineCap(StrokeLineCap.SQUARE);
    }

    @Override
    public void stroke(GraphicsContext graphicsContext) {

        graphicsContext.beginPath();
        graphicsContext.setLineCap(StrokeLineCap.ROUND);
        for(Point2D point : points){
            graphicsContext.lineTo(point.getX(), point.getY());
            graphicsContext.stroke();
        }
        graphicsContext.closePath();
        graphicsContext.setLineCap(StrokeLineCap.SQUARE);
    }

    public ArrayList<Point2D> getPoints(){
        return points;
    }

    public void setPoints(ArrayList<Point2D> points){
        this.points = points;
    }

}
