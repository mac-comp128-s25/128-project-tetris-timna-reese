/**
 * Main class that initialized and runs the Tetris game.
 * 
 * This class creates the game canvas, displays difficulty selection buttons,
 * handles user input (arrow keys and spacebar), and controls game flow including
 * starting, animating, and ending the game.
 * 
 * It uses the Tetromino, Score, and TetrominoShapes classes.
 */
import edu.macalester.graphics.*;
import edu.macalester.graphics.ui.Button;

import java.awt.Color;

public class Main {
    private CanvasWindow canvas;
    public static final int CANVAS_WIDTH = 350;
    public static final int SCORECANVAS_HEIGHT = 800;
    public static final int CANVAS_HEIGHT = 700;
    private Tetromino tetromino;
    private static boolean isGameOver = false;
    private boolean isStarted = false;
    private Score score;
    private int mode = 0;
    double moveDown = 0.03;
    
    /**
     * Constructs the Tetris game window, sets up score tracking,
     * user interface buttons, key controls, and animation.
     */
    public Main(){
        canvas = new CanvasWindow("Tetris", CANVAS_WIDTH, SCORECANVAS_HEIGHT);
        canvas.setBackground(Color.BLACK);
        score = new Score(canvas);
        
        setUpButtons();
        animateTetromino(canvas);

        canvas.onKeyDown((e) -> {
            if (isStarted){
            String key = e.getKey().toString();
            if (!isGameOver) {
                if (key.equals("DOWN_ARROW")){
                    tetromino.moveDown();
                    tetromino.erase();
                    tetromino.draw();
                }
                else if(key.equals("LEFT_ARROW")){
                    if(tetromino.checkSideCollision()!=2){
                    tetromino.erase();
                    tetromino.moveLeft();
                    tetromino.draw();
                    }
                }
                else if(key.equals("RIGHT_ARROW")){
                    if(tetromino.checkSideCollision()!=1){
                    tetromino.erase();
                    tetromino.moveRight();
                    tetromino.draw();
                    }
                }
                else if(key.equals("SPACE")){
                    tetromino.erase();
                    tetromino.rotate();
                    tetromino.draw();
                }
            }
        }
        });
    } 
    
    /**
     * Adds buttons to the screen that let the user choose between 
     * easy, hard, or exreme difficulty modes. Each mode sets a different
     * fall speed for the tetromino.
     */
    public void setUpButtons(){
        Button easyStartButton = new Button("Play Easy Mode");
        easyStartButton.setCenter(CANVAS_WIDTH/2, 140);

        Button hardStartButton = new Button("Play Hard Mode");
        hardStartButton.setCenter(CANVAS_WIDTH/2, 175);

        Button extremeStartButton = new Button("Play Extreme Mode");
        extremeStartButton.setCenter(CANVAS_WIDTH/2, 210);
        
        easyStartButton.onClick(() -> {
            startGame();
            canvas.remove(easyStartButton);
            canvas.remove(hardStartButton);
            canvas.remove(extremeStartButton);
        });
        canvas.add(easyStartButton);

        hardStartButton.onClick(() -> {
            mode = 1;
            moveDown = 0.05;
            startGame();
            canvas.remove(hardStartButton);
            canvas.remove(easyStartButton);
            canvas.remove(extremeStartButton);
        });
        canvas.add(hardStartButton);

        extremeStartButton.onClick(() -> {
            mode = 2;
            moveDown = 0.07;
            startGame();
            canvas.remove(hardStartButton);
            canvas.remove(easyStartButton);
            canvas.remove(extremeStartButton);
        });
        canvas.add(extremeStartButton);
    }
    
    /**
     * Starts a new game by drawing the grid, creating the first tetromino,
     * resetting the score, and marking the game as active.
     */
    public void startGame(){
        drawBoard(canvas);
        tetromino = new Tetromino(canvas, score, mode);
        tetromino.draw();
        score.resetScore();
        score.drawScore();
        isStarted = true;
    }
    
    /**
     * Sets game over status.
     * 
     * @param bool true to set game over, false to reset game over status
     */
    public static void setGameOver(boolean bool){
        isGameOver = bool;
    }
    
    /**
     * Animates the falling tetromino by calling the move logic.
     * Loop checks for collision and game over conditions.
     * 
     * @param canvas the game canvas to animate on
     */
    public void animateTetromino(CanvasWindow canvas){
        canvas.animate(() -> {
            if(isStarted){
            if(!isGameOver) {
                if(!tetromino.checkAnyCollision()){
                    tetromino.setRow(moveDown);
                    tetromino.erase();
                    tetromino.draw();
                    tetromino.clearRow();
                }
            }
            else{
                gameOver();
            }
            }
        });
    }

    /**
     * Draws the grid on the canvas to visually create a 10x20 grid.
     * 
     * @param canvas the canvas on which the grid will be drawn
     */
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
    
    /**
     * Handles the end of the game. Pauses the game, displays a red Game Over screen,
     * shows the final score, and provides a "Play Again" button
     */
    public void gameOver() {
        isStarted = false;
        canvas.pause(100);
        canvas.removeAll();
        canvas.setBackground(Color.RED);
        GraphicsText gameOver= new GraphicsText();
        GraphicsText youLose= new GraphicsText();
        gameOver.setText("Game Over");
        youLose.setText("You Lose!");
        gameOver.setPosition(30, CANVAS_HEIGHT/4);
        youLose.setPosition(40, CANVAS_HEIGHT/3);
        gameOver.setFontSize(55);
        youLose.setFontSize(55);
        canvas.add(gameOver);
        canvas.add(youLose);
        score.drawFinalScore();
        canvas.pause(1000);

        Button restartButton = new Button("Play Again");
        restartButton.setCenter(CANVAS_WIDTH /2.0, CANVAS_HEIGHT/2.0 +20);
        restartButton.onClick(() -> {
            isGameOver= false;
            canvas.removeAll();
            tetromino.clearAll();
            canvas.setBackground(Color.BLACK);
            setUpButtons();
        });
        canvas.add(restartButton);
    }
    
    /**
     * Entry point to run the Tetris game.
     * 
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        new Main();
    }
}

