/**
 * Manages a single tetromino during gameplay.
 * 
 * Moves, rotates, checks for collisions, clears rows, and 
 * communicates with the canvas and score system. Each tetromino
 * is composed of four blocks represented as Rectangle objects.
 */
import edu.macalester.graphics.CanvasWindow;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.macalester.graphics.Rectangle;

public class Tetromino {
    public static final int WIDTH = Main.CANVAS_WIDTH / 10;
    public static final int HEIGHT = Main.CANVAS_HEIGHT / 20;
    private CanvasWindow canvas;
    private Color [][] shape;
    private List<Rectangle> rectangleList = new ArrayList<Rectangle>();
    private double row;
    private int col;
    private List<Rectangle> collisionList = new ArrayList<Rectangle>();
    private Score score;
    private int mode; 
    
    /**
     * Creates a new tetromino at the top of the board and selects a shape based on 
     * the current game mode.
     * 
     * @param canvas the canvas on which the tetromino will be drawn
     * @param score the score object for tracking points
     * @param mode difficulty level (0 = Easy, 1 = Hard, 2 = Extreme)
     */
    public Tetromino(CanvasWindow canvas, Score score, int mode){
        this.row = 0;
        this.col = 0;
        this.canvas = canvas;
        this.score = score;
        this.mode = mode; 
        newTetromino(mode);
    }

    /**
     * Creates a new tetromino shape based on the mode and places it on the canvas.
     * 
     * @param mode difficultiy level (0 = Easy, 1 = Hard, 2 = Extreme)
     * @return the newly generated tetromino shape
     */
    public Color[][] newTetromino(int mode){
        row = -1;
        col = 3;
        if (mode == 0){
            this.shape = TetrominoShapes.easyMode();
        }
        if (mode == 1){
            this.shape = TetrominoShapes.hardMode();
        }
        if (mode == 2){
            this.shape = TetrominoShapes.extremeMode();
        }
        draw(); 
        score.updateScore(4);
        return shape;
    }

    /**
     * Creates a single block of the tetromino and adds it to the canvas and rectangle list.
     * 
     * @param row the row position in grid coordinates
     * @param column the column position in grid coordinates
     * @param color the fill color of the block
     */
    public void createRectangle(int row, int column, Color color){
        Rectangle rect = new Rectangle(WIDTH*column,HEIGHT*row,WIDTH,HEIGHT);
        rect.setFillColor(color);
        rectangleList.add(rect);
        canvas.add(rect);
    }

    /**
     * Erases the current tetromino from the canvas by removing its rectangles.
     */
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

    /**
     * Draws the tetromino on the canvas based on its shape and position.
     */
    public void draw(){
        for(int i = 0; i< shape.length; i++){
            for (int j = 0; j < shape[i].length; j++){
                if(shape[i][j] != null){
                    Color color = shape[i][j];
                    createRectangle((int)row+i,col+j, color);
                }
            }
        }          
    }

    /**
     * Checks whether any block of the tetromino has reached the bottom of the board.
     * 
     * @return true if bottom collision is detected, false otherwise
     */
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

    /**
     * Checks whether any block of the tetromino has reached or gone above the top of the board.
     * 
     * @return true if top collision is detected, false otherwise
     */
    public boolean checkTopCollision() {
        for (Rectangle rect : rectangleList) {
            if (rect.getY() <= 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Moves the tetromino down by one row unless a collision is detected.
     */
    public void moveDown(){
        if (!checkAnyCollision())
            row+=1;
    }

    /**
     * Clears all stored tetromino data.
     */
    public void clearAll(){
        collisionList.clear();
        rectangleList.clear();
    }

    /**
     * Checks for collision with the bottom or placed blocks, and either ends the game
     * or places the tetromino and spawns a new one.
     * 
     * @return true if a collision occurred, false otherwise
     */
    public boolean checkAnyCollision(){
        if(checkBottomCollision() || checkBlockCollision()){
              if (overlap(rectangleList)){
                Main.setGameOver(true);
                return true;
            }
            collisionList.addAll(rectangleList);
            rectangleList.clear();
            newTetromino(mode);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Checks if any part of the tetromino is overlapping the top row of the board.
     * 
     * @param rectangles the rectangles to check
     * @return true if overlap is detected, false otherwise
     */
    private boolean overlap(List<Rectangle> rectangles) {
        for (Rectangle newRect : rectangles) {
                if (newRect.getY() < HEIGHT) {
                    return true;
                }
            }
            
        return false;
    }

    /**
     * Checks if the tetromino would collide with the left or right wall after a movement.
     * 
     * @param newY the proposed column position
     * @return true if wall collision would occur
     */
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

    /**
     * Checks if the tetromino would collide with any placed blocks below it.
     * 
     * @return true if block collision is detected, false otherwise
     */
    public boolean checkBlockCollision(){
        for (int i = 0; i<collisionList.size(); i++){
            for (int j = 0; j< rectangleList.size(); j++){
                double bottomCurrent = rectangleList.get(j).getY()+HEIGHT;
                double topPlaced = collisionList.get(i).getY();
                double sideCurrent = rectangleList.get(j).getX();
                double sidePlaced = collisionList.get(i).getX();
                if (bottomCurrent == topPlaced && sideCurrent == sidePlaced){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks for side collisions with other placed blocks.
     * 
     * @return 1 for right collision, 2 for left collision, 0 for none
     */
    public int checkSideCollision(){
        for (int i = 0; i<collisionList.size(); i++){
            for (int j = 0; j< rectangleList.size(); j++){
                int rowPlaced = (int) collisionList.get(i).getY()/HEIGHT;
                int rowCurrent = (int) rectangleList.get(j).getY()/HEIGHT;
                int colCurrent = (int) rectangleList.get(j).getX()/WIDTH;
                int colPlaced = (int) collisionList.get(i).getX()/WIDTH;
                if (rowPlaced == rowCurrent){
                    if(colCurrent+1 == colPlaced ){
                    return 1; 
                    }
                    if (colCurrent-1 == colPlaced){
                    return 2; 
                    }
                }
            }
        }
        return 0;
    }

    /**
     * Returns the current grid row of the given rectangle 
     * 
     * @param r the rectangle whose row position is being calculated
     * @return the row index (0-19) of the rectangle on the board
     */
    public int getCurrentRow(Rectangle r){
        return (int) r.getY() / HEIGHT;
    }

    /**
     * Gets the current floating-point vertical position of the tetromino's top.
     * 
     * @return the top row position (double) of the tetromino
     */
    public double getRow(){
        return row;
    }

    /**
     * Increases the tetromino's row position by a given amount 
     * 
     * @param down the amount to add to the current row position
     */
    public void setRow(double down){
        row += down;
    }

    /**
     * Moves the tetromino one cell to the right, if possible.
     */
    public void moveRight(){
        if (!wallCollision(col+1) ) {
            col+=1;
        }
    }

    /**
     * Moves the tetromino one cell to the left, if possible.
     */
    public void moveLeft(){
        if (!wallCollision(col-1)) {
            col-=1;
        }
    }

    /**
     * Rotates the tetromino clockwise, after checking for wall and block collisions.
     */
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
                    int newY = col + j;
                    int newX = (int) row + i;
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
                    int newCol = col + j;
                    int newRow = (int) row + i;
                    for (Rectangle r: collisionList){
                        int rRow = getRow(r);
                        int rCol = getColumn(r);
                        if(newRow == rRow && newCol == rCol){
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
 
    /**
     * Checks for and clears any completely filled rows.
     * Updates score and moves blocks above the cleared row(s) downward.
     */
    public void clearRow() {
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
        Set<Integer> keySet = collisionMap.keySet();
        
        List<Rectangle> rectanglesToClear = new ArrayList<Rectangle>();

        for (Integer key: keySet) {
            if(collisionMap.get(key).size()==10){
                List<Rectangle> rectangles = collisionMap.get(key);
                for(Rectangle rect: rectangles){
                    rectanglesToClear.add(rect);
                }
            }
        }
        moveRowDown(rectanglesToClear);
    }

    /**
     * Deletes full rows from the canvas and shifts blocks above downward.
     * 
     * @param clearRectangles list of rectangles to remove
     */
    public void moveRowDown(List<Rectangle> clearRectangles){
        Set<Integer> clearedRows = new HashSet<>();
        for (Rectangle rect: clearRectangles){
            int row = getCurrentRow(rect);
            clearedRows.add(row);
            collisionList.remove(rect);
            canvas.remove(rect);
        }

        List<Integer> sortedClearedRows = new ArrayList<>(clearedRows);
        Collections.sort(sortedClearedRows);

        for (Rectangle rect: collisionList){
            int rectRow = getCurrentRow(rect);
            int rowsBelow = 0;
            for (int clearedRow: sortedClearedRows){
                if (rectRow<clearedRow){
                    rowsBelow ++;
                }
            }
            if (rowsBelow > 0){
                rect.setY(rect.getY() + rowsBelow * Tetromino.HEIGHT);
            }
        }
        score.updateScore(100*clearedRows.size());
    }

}