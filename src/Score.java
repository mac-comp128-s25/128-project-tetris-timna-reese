/**
 * Handles score tracking and display for the Tetris game.
 * 
 * This class keeps track of the player's current score, updates it
 * when rows are cleared, and displays the current score on the game canvas,
 * as well as the final score on the Game Over screen. The score starts at 4
 * due to the initial tetromino being added.
 */
import java.awt.Color;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsText;

public class Score {
    private int score = 4;
    GraphicsText scoreText = new GraphicsText();
    private CanvasWindow canvas;
    
    /**
     * Constructs a score object tied to a specific canvas window.
     * 
     * @param canvas the CanvasWindow where the score will be drawn
     */
    public Score(CanvasWindow canvas) {
        this.canvas = canvas;
    }
    
    /**
     * Increases current score by the given amount and updates the score visually.
     * 
     * @param num the amount to add to the current score
     */
    public void updateScore(int num) {
        score+=num;
        scoreText.setText("SCORE: " + score);
    }
    
    /**
     * Displays the current score at the bottom of the canvas.
     */
    public void drawScore() {
        scoreText.setText("SCORE: " + score);
        scoreText.setPosition(40, 740);
        scoreText.setFillColor(Color.WHITE);
        scoreText.setFilled(true);
        scoreText.setFontSize(15);
        canvas.add(scoreText);
    }

    /**
     * Displays the final score on the canvas after the game is over.
     */
    public void drawFinalScore(){
        scoreText.setText("FINAL SCORE: " + score );
        scoreText.setPosition(40, Main.CANVAS_HEIGHT/2-50);
        scoreText.setFillColor(Color.WHITE);
        scoreText.setFilled(true);
        scoreText.setFontSize(20);
        canvas.add(scoreText);
    }

    /**
     * Resets the score to the defualt starting value of 4.
     * Needs to start at 4 because each tetromino is worth 4 points upon spawning.
     */
    public void resetScore(){
        score = 4; 
    }
}

