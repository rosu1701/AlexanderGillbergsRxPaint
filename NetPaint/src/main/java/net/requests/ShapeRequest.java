package net.requests;
import javafx.geometry.Point2D;
import net.NetPoint;
import shapes.*;

import java.util.ArrayList;

public class ShapeRequest extends NetRequest {

    public static final int FREE_HAND = 1;
    public static final int LINE = 2;
    public static final int RECTANGLE = 3;
    public static final int OVAL = 4;

    public int shapeType = 0;
    public double startX = 0;
    public double startY = 0;
    public double stopX = 0;
    public double stopY = 0;

    public double lineWidth;
    public String color;

    private ArrayList<NetPoint> freeHandPoints;

    public ShapeRequest() {
    }

    public ShapeRequest(NPShape shape) {
        if (shape instanceof NPFreeHand) {
            shapeType = FREE_HAND;
            NPFreeHand freeHand = (NPFreeHand) shape;
            freeHandPoints = new ArrayList<>();
            for (int i = 0; i < freeHand.getPoints().size(); i++) {
                Point2D p = freeHand.getPoints().get(i);
                NetPoint np = new NetPoint();
                np.x = p.getX();
                np.y = p.getY();
                freeHandPoints.add(np);
            }
        } else {
            if (shape instanceof NPLine) shapeType = LINE;
            if (shape instanceof NPOval) shapeType = OVAL;
            if (shape instanceof NPRectangle) shapeType = RECTANGLE;

            this.startX = shape.startX;
            this.startY = shape.startY;
            this.stopX = shape.stopX;
            this.stopY = shape.stopY;
        }

    }

    public NPShape toShape() {
        NPShape shape = null;
        if (shapeType == FREE_HAND) {
            shape = new NPFreeHand();
            ArrayList<Point2D> points = new ArrayList<>();
            for (var np : freeHandPoints) {
                points.add(new Point2D(np.x, np.y));
            }
            ((NPFreeHand) shape).setPoints(points);
        } else {
            if (shapeType == LINE) shape = new NPLine();
            if (shapeType == OVAL) shape = new NPOval();
            if (shapeType == RECTANGLE) shape = new NPRectangle();

            shape.startX = startX;
            shape.startY = startY;
            shape.stopX = stopX;
            shape.stopY = stopY;
        }
        return shape;


    }

}