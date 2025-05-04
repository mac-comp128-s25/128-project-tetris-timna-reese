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

    
    public Main(){
        canvas = new CanvasWindow("Tetris", CANVAS_WIDTH, SCORECANVAS_HEIGHT);
        canvas.setBackground(Color.BLACK);
        Button startButton = new Button("Play Tetris");
        score = new Score(canvas);
        startButton.setCenter(CANVAS_WIDTH/2, CANVAS_HEIGHT/5);
        startButton.onClick(() -> {
            startGame();
            canvas.remove(startButton);
        });
        canvas.add(startButton);
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


    public void startGame(){
        
        drawBoard(canvas);
        tetromino = new Tetromino(canvas, score);
        tetromino.draw();
        score.resetScore();
        score.drawScore();
        isStarted = true;

    }

    public static void setGameOver(boolean bool){
        isGameOver = bool;
    }

    public void animateTetromino(CanvasWindow canvas){
        canvas.animate(() -> {
            if(isStarted){
            if(!isGameOver) {
                if(!tetromino.checkAnyCollision()){
                    tetromino.setRow(0.03);
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
            startGame();
        });
        canvas.add(restartButton);
    }

    public static void main(String[] args) {
        new Main();
    }
}

