import edu.macalester.graphics.*;
import edu.macalester.graphics.events.Key;

import java.awt.Color;


public class Main {
    private CanvasWindow canvas;
    public static final int CANVAS_WIDTH = 350;
    public static final int CANVAS_HEIGHT = 700;
    private Tetromino tetromino;

    public Main(){
        canvas = new CanvasWindow("Tetris", CANVAS_WIDTH, CANVAS_HEIGHT);
        canvas.setBackground(Color.BLACK);
        drawBoard(canvas);
        tetromino = new Tetromino(canvas);
        tetromino.draw();

        canvas.onKeyDown((e) -> {
            String key = e.getKey().toString();
            if (key.equals("DOWN_ARROW")){
                tetromino.erase();
                tetromino.moveDown();
                tetromino.draw();
            }
            else if(key.equals("LEFT_ARROW")){
                tetromino.erase();
                tetromino.moveLeft();
                tetromino.draw();
            }
            else if(key.equals("RIGHT_ARROW")){
                tetromino.erase();
                tetromino.moveRight();
                tetromino.draw();
            }
            else if(key.equals("SPACE")){
                tetromino.erase();
                tetromino.rotate();
                tetromino.draw();
            }
        });

    } 

    public void drawBoard(CanvasWindow canvas){
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
    }

    public static void main(String[] args) {
        new Main();
    }
}

