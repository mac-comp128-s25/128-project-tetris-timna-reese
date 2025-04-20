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
    Color[][] square;
    Color[][] line;
    Color[][] leftL;
    Color[][] rightL;
    Color[][] forwardS;
    Color[][] backwardS;
    Color[][] pyramid;
    Color [][] shape;
    
    List<Rectangle> rectangleList = new ArrayList<Rectangle>();
    int x;
    int y;
    

    public Tetromino(CanvasWindow canvas){
        this.x = 0;
        this.y = 0;
        this.canvas = canvas;
        this.square = new Color[][] {
            {null, null, null, null},
            {null, Color.YELLOW, Color.YELLOW, null},
            {null, Color.YELLOW, Color.YELLOW, null},
            {null, null, null, null}
        };

         this.line = new Color[][] {
            {null, null, null, null},
            {Color.CYAN, Color.CYAN, Color.CYAN, Color.CYAN},
            {null, null, null, null},
            {null, null, null, null}
        };

        this.leftL = new Color[][]{
            {null, null, null, null},
            {null, Color.BLUE, null, null},
            {null, Color.BLUE, Color.BLUE, Color.BLUE},
            {null, null, null, null}
        };

        this.rightL = new Color[][]{
            {null, null, null,null},
            {null, null, Color.ORANGE,null},
            {Color.ORANGE, Color.ORANGE, Color.ORANGE,null},
            {null, null, null,null}
        };

        this.forwardS = new Color[][]{
            {null, null, null, null},
            {null, null, Color.GREEN, Color.GREEN},
            {null, Color.GREEN, Color.GREEN, null},
            {null, null, null, null},
        };

        this.backwardS = new Color[][]{
            {null, null, null, null},
            {null, Color.RED, Color.RED, null},
            {null, null, Color.RED, Color.RED},
            {null, null, null, null},
        };

        this.pyramid = new Color[][]{
            {null, null, null, null},
            {null, null, Color.MAGENTA, null},
            {null, Color.MAGENTA, Color.MAGENTA, Color.MAGENTA},
            {null, null, null, null},
        };
        Color[][] [] tetrominoList = {square, line, leftL, rightL, forwardS, backwardS, pyramid};
        Random random = new Random();
        int index = random.nextInt(7);
        this.shape = tetrominoList[index];
        
    }

    public void createRectangle(int row, int column, Color color){
        Rectangle rect = new Rectangle(WIDTH*column,HEIGHT*row,WIDTH,HEIGHT);
        rect.setFillColor(color);
        rectangleList.add(rect);
        canvas.add(rect);
    }
    
    public void erase(){
        for(int i =0; i<rectangleList.size(); i++){
            canvas.remove(rectangleList.get(i));
        }
        rectangleList.clear();
    }

    public void draw(){
        for(int i = 0; i< shape.length; i++){
            for (int j = 0; j < shape[i].length; j++){
                if(shape[i][j] != null){
                    Color color = shape[i][j];
                    createRectangle(x+i,y+j, color);
                }
            }
        }          
    }

    public void moveDown(){
        x +=1;
    }

    public void moveRight(){
        y+=1;
    }

    public void moveLeft(){
        y-=1;
    }

    public void rotate(){
        Color [] [] newShape = new Color[4][4];
        for(int i = 0; i< shape.length; i++){
            for (int j = 0; j < shape[i].length; j++){
                newShape[j][3-i] = shape[i][j];
            }
        }
        shape = newShape;
    }

}
