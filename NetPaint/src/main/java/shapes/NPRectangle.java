package shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

public class NPRectangle extends NPShape {

    public NPRectangle(){
        super(new Rectangle());
    }

    @Override
    public void stroke(GraphicsContext graphicsContext) {
        Rectangle rectangle = (Rectangle) shape;

        rectangle.setX(startX);
        rectangle.setY(startY);

        rectangle.setWidth(Math.abs((stopX - rectangle.getX())));
        rectangle.setHeight(Math.abs((stopY - rectangle.getY())));

        if(rectangle.getX() > stopX) {
            rectangle.setX(stopX);
        }
        if(rectangle.getY() > stopY) {
            rectangle.setY(stopY);
        }

        graphicsContext.strokeRect(rectangle.getX(), rectangle.getY(),
                rectangle.getWidth(), rectangle.getHeight());
    }
}
