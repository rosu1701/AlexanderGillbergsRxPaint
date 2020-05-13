package shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Shape;

public abstract class NPShape {

    protected Shape shape;
    public double startX = 0;
    public double startY = 0;
    public double stopX = 0;
    public double stopY = 0;

    public NPShape(Shape shape){
        this.shape = shape;
    }

    public void setStart(double x, double y){
        this.startX = x;
        this.startY = y;
    }
    public void setStop(double x, double y){
        this.stopX = x;
        this.stopY = y;
    }
    public abstract void stroke(GraphicsContext graphicsContext);

    public void process(double x, double y, GraphicsContext bufferGraphics){
        setStop(x, y);
        stroke(bufferGraphics);
    }

    public NPShape newInstance(){
        if (this instanceof NPRectangle) return new NPRectangle();
        if (this instanceof NPLine) return new NPLine();
        if (this instanceof NPOval) return new NPOval();
        if (this instanceof NPFreeHand) return new NPFreeHand();
        return null;
    }

}




