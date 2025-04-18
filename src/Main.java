import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.*;
import java.awt.Color;

public class Main {
    private CanvasWindow canvas;
    public static final int CANVAS_WIDTH = 500;
    public static final int CANVAS_HEIGHT = 700;
    private Tetromino tetromino;

    public Main(){
       canvas = new CanvasWindow("Tetris", CANVAS_WIDTH, CANVAS_HEIGHT);
       canvas.setBackground(Color.BLACK);
       for (int i = 0; i<= CANVAS_WIDTH; i +=(CANVAS_WIDTH/10)){
            Line verticalLine = new Line (i, 0, i, CANVAS_HEIGHT);
            verticalLine.setStrokeColor(Color.DARK_GRAY);
            canvas.add(verticalLine);
       }
       for (int i = 0; i<= CANVAS_HEIGHT; i +=(CANVAS_HEIGHT/20)){
            Line horizontalLine = new Line (0, i, CANVAS_WIDTH, i);
            horizontalLine.setStrokeColor(Color.DARK_GRAY);
            canvas.add(horizontalLine);
        }
        tetromino = new Tetromino(canvas);
        

    } 
    public static void main(String[] args) {
        new Main();
    }
}

