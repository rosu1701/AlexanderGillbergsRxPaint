package shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Line;

public class NPLine extends NPShape {

    public NPLine() {
        super(new Line());
    }

    @Override
    public void stroke(GraphicsContext graphicsContext) {
        Line line = (Line) shape;

        line.setStartX(startX);
        line.setStartY(startY);

        line.setEndX(stopX);
        line.setEndY(stopY);

        graphicsContext.strokeLine(line.getStartX(), line.getStartY(),
                line.getEndX(), line.getEndY());
    }
}
