package shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Ellipse;
import reactor.core.publisher.Mono;

import java.time.Duration;


public class NPOval extends NPShape {

    public NPOval() {
        super(new Ellipse());
    }

    @Override
    public void stroke(GraphicsContext graphicsContext) {
        // BlockHound test
       /* Mono.delay(Duration.ofSeconds(1))
                .doOnNext(it -> {
                    try {
                        Thread.sleep(10);
                    }
                    catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })
                .block(); */

        Ellipse oval = (Ellipse) shape;

        oval.setCenterX(startX);
        oval.setCenterY(startY);

        oval.setRadiusX(Math.abs(stopX - oval.getCenterX()));
        oval.setRadiusY(Math.abs(stopY - oval.getCenterY()));

        if(oval.getCenterX() > stopX) {
            oval.setCenterX(stopX);
        }
        if(oval.getCenterY() > stopY) {
            oval.setCenterY(stopY);
        }

        graphicsContext.strokeOval(oval.getCenterX(), oval.getCenterY(),
                oval.getRadiusX(), oval.getRadiusY());
    }
}
