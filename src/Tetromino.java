import edu.macalester.graphics.CanvasWindow;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import edu.macalester.graphics.Rectangle;

public class Tetromino {
    public static final int WIDTH = Main.CANVAS_WIDTH / 10;
    public static final int HEIGHT = Main.CANVAS_HEIGHT / 20;
    private CanvasWindow canvas;
    private CollisionManager collisionManager;
    private Score score;
    private Color[][] shape;
    private List<Rectangle> rectangleList = new ArrayList<Rectangle>();
    private double rowPos;
    private int colPos;

    public Tetromino(CanvasWindow canvas, Score score, CollisionManager collisionManager){
        this.canvas = canvas;
        this.score = score;
        this.collisionManager = collisionManager;
    }

    public Color[][] newTetromino(){
        rowPos = -1;
        colPos = 3;
        this.shape = TetrominoShapes.getRandomTetromino();
        draw(); 
        score.updateScore(4);
        return shape;
    }

    public void draw(){
        rectangleList.clear();
        for(int i = 0; i< shape.length; i++){
            for (int j = 0; j < shape[i].length; j++){
                if(shape[i][j] != null){
                    Color color = shape[i][j];
                    createRectangle((int)rowPos+i,colPos+j, color);
                }
            }
        }          
    }

    public void createRectangle(int row, int column, Color color){
        Rectangle rect = new Rectangle(WIDTH*column,HEIGHT*row,WIDTH,HEIGHT);
        rect.setFillColor(color);
        rectangleList.add(rect);
        canvas.add(rect);
    }

    public void updatePosition(){
        erase();
        draw();
    }
    
    public void moveDown(){
            rowPos+=1;
    }

    public void moveRight(){
        if (!wallCollision(colPos+1) ) {
            colPos+=1;
        }
    }

    public void moveLeft(){
        if (!wallCollision(colPos-1)) {
            colPos-=1;
        }
    }


    public void erase(){
        for(int i =0; i<rectangleList.size(); i++){
            canvas.remove(rectangleList.get(i));
        }
        rectangleList.clear();
    }

    public int getRow(Rectangle rect){
        return (int) rect.getY()/HEIGHT;
    }

    public int getColumn(Rectangle rect){
        return (int) rect.getX()/WIDTH;
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
        for (Rectangle rect : rectangleList) {
            if (rect.getY() <= 0) {
                return true;
            }
        }
        return false;
    }


    public void clearAll(){
        collisionManager.clearCollisionList();
        rectangleList.clear();
    }

    public boolean checkAnyCollision(){
        if(collisionManager.checkAnyCollision(rectangleList)){
            rectangleList.clear();
            newTetromino();
            return true;
        }
        return false;
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

    public int checkSideCollision(){
        return collisionManager.checkSideCollision(rectangleList);
    }

    public int getCurrentRow(Rectangle r){
        return (int) r.getY() / HEIGHT;
    }

    public void rotate(){
        Color [] [] newShape = new Color[4][4];
        for(int i = 0; i< shape.length; i++){
            for (int j = 0; j < shape[i].length; j++){
                newShape[j][3-i] = shape[i][j];
            }
        }
        boolean isCollision = false; 
        for (int i = 0; i < newShape.length; i++) {
            for (int j = 0; j < newShape[i].length; j++) {
                if (newShape[i][j] != null) {
                    int newY = colPos + j;
                    int newX = (int) rowPos + i;
                    if (newY < 0) {
                        isCollision = true;
                        break;
                    } else if (newY >= 10) {
                        isCollision = true;
                        break;
                    }
                    if(newX>=20){
                        isCollision = true;
                        break;
                    }
                }
            }
        }

        for (int i = 0; i < newShape.length; i++) {
            for (int j = 0; j < newShape[i].length; j++) {
                if (newShape[i][j] != null) {
                    int col = colPos + j;
                    int row = (int) rowPos + i;
                    for(Rectangle r: collisionManager.getCollisionList()){
                        int rRow = getRow(r);
                        int rCol = getColumn(r);
                        if(row == rRow && col == rCol){
                            isCollision = true;
                            break;
                        }
                    }
                }
            }
        }
        if(!isCollision){
            shape = newShape;
        }
        
    }
    
    public List<Rectangle> getRectangleList(){
        return rectangleList;
    }

    public Color [][] getShape(){
        return shape;
    }

    public double getRowPos(){
        return rowPos;
    }

    public void setRowPos(double down){
        rowPos += down;
    }    

}