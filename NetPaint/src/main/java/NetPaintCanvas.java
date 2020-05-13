

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import net.requests.ClearRequest;
import net.requests.NetRequest;
import net.requests.ShapeRequest;
import shapes.*;

import java.util.ArrayList;


public class NetPaintCanvas extends Pane {

    private NetPaintToolBar netPaintToolBar;
    private GraphicsContext graphics;
    private GraphicsContext bufferGraphics;
    private NPShape currentShape = null;
    private ArrayList<NPShape> shapeList = null;

    private Canvas canvas;
    private Canvas bufferCanvas;

    public NetPaintCanvas(NetPaintToolBar netPaintToolBar, int width, int height){

        this.netPaintToolBar = netPaintToolBar;
        this.setWidth(width);
        this.setHeight(height);
        this.shapeList = new ArrayList<>();
        this.canvas = new Canvas(width, height);
        this.bufferCanvas = new Canvas(width, height);
        init();



    }

    private void init(){

        graphics = canvas.getGraphicsContext2D();
        graphics.setLineWidth(1);
        bufferGraphics = bufferCanvas.getGraphicsContext2D();
        bufferGraphics.setLineWidth(1);

        this.getChildren().addAll(canvas, bufferCanvas);

        netPaintToolBar.onClearScreen().subscribe(i -> this.clearCanvas());
        netPaintToolBar.onColorPickerChange().subscribe(this::setStrokeColor);
        netPaintToolBar.onSliderChange().subscribe(value -> setLineWidth(value.doubleValue()));
        netPaintToolBar.onShapeSelected().subscribe(shape -> currentShape = shape);


        JavaFxObservable.eventsOf(this, MouseEvent.ANY)
                .filter(event -> currentShape != null)
                .subscribe(event -> {

            if(event.getEventType() == MouseEvent.MOUSE_PRESSED){
                currentShape = currentShape.newInstance();
                currentShape.setStart(event.getX(), event.getY());
            }

            else if(event.getEventType() == MouseEvent.MOUSE_DRAGGED){
                bufferGraphics.clearRect(0, 0, getWidth(), getHeight());
                currentShape.process(event.getX(), event.getY(), bufferGraphics);
            }

            else if(event.getEventType() == MouseEvent.MOUSE_RELEASED) {
                currentShape.setStop(event.getX(), event.getY());
                currentShape.stroke(bufferGraphics);

            }

        });

    }

    public void handleNetRequest(NetRequest netRequest){

        if(netRequest instanceof ShapeRequest){
            ShapeRequest request = (ShapeRequest) netRequest;
            NPShape shape = request.toShape();
            double lastLineWidth = graphics.getLineWidth();
            Paint lastColor = graphics.getStroke();

            setStrokeColor(Color.valueOf(request.color));
            setLineWidth(request.lineWidth);
            shape.stroke(graphics);
            this.shapeList.add(shape);

            setStrokeColor(lastColor);
            setLineWidth(lastLineWidth);
        }

        else if (netRequest instanceof ClearRequest){
            this.clearCanvas();
        }

    }

    public Observable<ShapeRequest> onShapeDraw(){
        return JavaFxObservable.eventsOf(this, MouseEvent.MOUSE_RELEASED)
                .filter(event -> currentShape != null)
                .map(event -> {
                    ShapeRequest request = new ShapeRequest(currentShape);
                    request.lineWidth = graphics.getLineWidth();
                    request.color = graphics.getStroke().toString();
                    return request;
                });
    }

    private void setStrokeColor(Paint color){
        graphics.setStroke(color);
        bufferGraphics.setStroke(color);
    }

    private void setLineWidth(double value){
        graphics.setLineWidth(value);
        bufferGraphics.setLineWidth(value);
    }

    public void clearCanvas(){
        graphics.clearRect(0, 0, getWidth(), getHeight());
        bufferGraphics.clearRect(0, 0, getWidth(), getHeight());
        shapeList.clear();
    }


}
