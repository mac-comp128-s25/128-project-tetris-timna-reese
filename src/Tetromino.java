import edu.macalester.graphics.CanvasWindow;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import edu.macalester.graphics.Rectangle;

public class Tetromino {
    public static final int WIDTH = Main.CANVAS_WIDTH / 10;
    public static final int HEIGHT = Main.CANVAS_HEIGHT / 20;
    private CanvasWindow canvas;
    private Color[][] square;
    private Color[][] line;
    private Color[][] leftL;
    private Color[][] rightL;
    private Color[][] forwardS;
    private Color[][] backwardS;
    private Color[][] pyramid;
    private Color [][] shape;
    private List<Rectangle> rectangleList = new ArrayList<Rectangle>();
    private double x;
    private int y;
    private List<Rectangle> collisionList = new ArrayList<Rectangle>();
    

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

        newTetromino();
    }

    public Color[][] newTetromino(){
        x = -1;
        y = 3;
        Color[][] [] tetrominoList = {square, line, leftL, rightL, forwardS, backwardS, pyramid};
        Random random = new Random();
        int index = random.nextInt(7);
        this.shape = tetrominoList[index];
        draw(); 
        return shape;
    }

    public void createRectangle(int row, int column, Color color){
        Rectangle rect = new Rectangle(WIDTH*column,HEIGHT*row,WIDTH,HEIGHT);
        if (checkTopCollision() && checkBlockCollision()) {
            System.out.println("top");
            canvas.removeAll();
            return;
        }
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
                    int intX = (int)x;
                    createRectangle(intX+i,y+j, color);
                }
            }
        }          
    }

    public boolean checkBottomCollision() {
        double bottom = Main.CANVAS_HEIGHT - HEIGHT;
        for (int i = 0; i< rectangleList.size(); i++) {
            double yval = rectangleList.get(i).getY();
            if (yval >= bottom) {
                return true;
            }
        }
        return false;
    }

    public boolean checkTopCollision() {
        double top = -1;
        for (int i = 0; i<rectangleList.size(); i++) {
            double yval = rectangleList.get(i).getY();
            if (yval <= top) {
                return true;
            }
        }
        return false;
    }

    public void moveDown(){
        if (!checkAnyCollision())
            x+=1;
    }

    public boolean checkAnyCollision(){
        if(checkBottomCollision() || checkBlockCollision()){
            collisionList.addAll(rectangleList);
            rectangleList.clear();
            newTetromino();
            draw();
            return true;
        }
        if(checkTopCollision()) {
            canvas.removeAll();
            return true;
        }
        else{
            return false;
        }
    }

    public boolean wallCollision(int newY) {
        for (int i=0; i < shape.length; i++) {
            for (int j=0; j< shape[i].length; j++) {
                if (shape[i][j] != null) {
                    int column = newY + j;
                    if (column < 0 || column >= 10) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean checkBlockCollision(){
        for (int i = 0; i<collisionList.size(); i++){
            for (int j = 0; j< rectangleList.size(); j++){
                double bottomCurrent = rectangleList.get(j).getY()+HEIGHT;
                double topPlaced = collisionList.get(i).getY();
                // double topCurrent = rectangleList.get(i).getY();
                double sideCurrent = rectangleList.get(j).getX();
                double sidePlaced = collisionList.get(i).getX();
                if (bottomCurrent == topPlaced && sideCurrent == sidePlaced){
                    return true;
                }
            }
        }
        return false;
    }

    public int checkSideCollision(){
        for (int i = 0; i<collisionList.size(); i++){
            for (int j = 0; j< rectangleList.size(); j++){
                int rowPlaced = (int) collisionList.get(i).getY()/HEIGHT;
                int rowCurrent = (int) rectangleList.get(j).getY()/HEIGHT;
                int colCurrent = (int) rectangleList.get(j).getX()/WIDTH;
                int colPlaced = (int) collisionList.get(i).getX()/WIDTH;
                if (rowPlaced == rowCurrent){
                    if(colCurrent+1 == colPlaced ){
                    return 1; //cannot move right
                    }
                    if (colCurrent-1 == colPlaced){
                    return 2; //cannot move left
                    }
                }
            }
        }
        return 0;
    }
    public int getCurrentRow(Rectangle r){
        return (int) r.getY() / HEIGHT;
    }

    public double getX(){
        return x;
    }

    public void setX(double down){
        x += down;
    }

    public void moveRight(){
        if (!wallCollision(y+1) ) {
            y+=1;
        }
    }

    public void moveLeft(){
        if (!wallCollision(y-1)) {
            y-=1;
        }
    }

    public void rotate(){
        Color [] [] newShape = new Color[4][4];
        for(int i = 0; i< shape.length; i++){
            for (int j = 0; j < shape[i].length; j++){
                newShape[j][3-i] = shape[i][j];
            }
        }
        int shiftX = 0;
        for(int i = 0; i< newShape.length; i++){
            for (int j = 0; j < newShape[i].length; j++){
                if (newShape[i][j] != null) {
                    int column = (int) y + j;
                    if(column < 0){
                        shiftX = Math.max(shiftX, -column);
                    }
                    else{
                        shiftX = Math.min(shiftX, 10 - column - 1);
                    }
                }
            }
        }
        y+= shiftX;
        //if one piece is outside, move whole piece to the right or whole piece to the left
        //put wallCollision check here
        shape = newShape;
    }
    // public void clearRow() {
    //    int rows = Main.CANVAS_HEIGHT / HEIGHT;
    //    int cols = Main.CANVAS_WIDTH / WIDTH;
    //    List<Rectangle> rowBlocks = new ArrayList<>();
    //    for (Rectangle r: collisionList) {
                
    //         }


    // }
    // public void moveRowDown(){
    //     int rows
    // }
    public void clearRow() {
        int rows = Main.CANVAS_HEIGHT / HEIGHT;
        int cols = Main.CANVAS_WIDTH / WIDTH;
        Map <Integer, List<Rectangle>> collisionMap = new HashMap<Integer, List<Rectangle>>();
        for (int i = 0; i < collisionList.size(); i++) {
           Rectangle rect = collisionList.get(i);
            int row = getCurrentRow(rect);
            if (collisionMap.containsKey(row)) {
                List<Rectangle> checker = collisionMap.get(row);
                checker.add(rect);
                collisionMap.replace(row, checker);
            }
            else {
                List<Rectangle> check = new ArrayList<Rectangle>();
                check.add(rect);
                collisionMap.put(row,check);
            }   
        }
         
    }

}