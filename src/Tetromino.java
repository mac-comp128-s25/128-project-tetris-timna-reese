import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.macalester.graphics.Rectangle;

public class Tetromino {
    public static final int WIDTH = Main.CANVAS_WIDTH / 10;
    public static final int HEIGHT = Main.CANVAS_HEIGHT / 20;
    List<Color> colorList;
    CanvasWindow canvas;
    


    public Tetromino(CanvasWindow canvas){
        Color [] colors = {Color.RED, Color.ORANGE,Color.YELLOW, Color.GREEN, Color.BLUE, Color.MAGENTA};
        this.canvas = canvas;
        Color [][] square = {
            {Color.YELLOW, Color.YELLOW},
            {Color.YELLOW, Color.YELLOW}
        };
        Color [][] line = {
            {Color.CYAN, Color.CYAN, Color.CYAN, Color.CYAN},
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null}
        };

        Color [][] leftL = {
            {Color.BLUE, null, null},
            {Color.BLUE, Color.BLUE, Color.BLUE},
            {null, null, null},
        };

        Color [][] rightL = {
            {null, null, Color.ORANGE},
            {Color.ORANGE, Color.ORANGE, Color.ORANGE},
            {null, null, null},
        };

        Color [][] forwardS = {
            {null, Color.GREEN, Color.GREEN},
            {Color.GREEN, Color.GREEN, null},
            {null, null, null},
        };

        Color [][] backwardS = {
            {Color.RED, Color.RED, null},
            {null, Color.RED, Color.RED},
            {null, null, null},
        };

        Color [][] pyramid = {
            {null, Color.MAGENTA, null},
            {Color.MAGENTA, Color.MAGENTA, Color.MAGENTA},
            {null, null, null},
        };
        Color[][] [] tetrominoList = {square, line, leftL, rightL, forwardS, backwardS, pyramid};
        Random random = new Random();
        int index = random.nextInt(6);
        // Color [][] tetromino = tetrominoList.get(index);

        buildBlock(pyramid);
    }


    public void createRectangle(int row, int column, Color color){
        Rectangle rect = new Rectangle(WIDTH*column,HEIGHT*row,WIDTH,HEIGHT);
        rect.setFillColor(color);
        canvas.add(rect);

    }

    public void buildBlock(Color[][] array){
        for(int i = 0; i< array.length; i++){
            for (int j = 0; j < array[i].length; j++){
                if(array[i][j] != null){
                    Color color = array[i][j];
                    createRectangle(i,j, color);
                }
            }
        }          
    }

}
