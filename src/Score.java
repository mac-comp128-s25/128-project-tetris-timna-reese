import java.awt.Color;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsText;

public class Score {
    private int score = 4;
    GraphicsText scoreText = new GraphicsText();
    private CanvasWindow canvas;
    
    public Score(CanvasWindow canvas) {
        this.canvas = canvas;
    }

    public void updateScore(int num) {
        score+=num;
        scoreText.setText("SCORE: " + score);
    }
    
    public void drawScore() {
        scoreText.setText("SCORE: " + score);
        scoreText.setPosition(40, 740);
        scoreText.setFillColor(Color.WHITE);
        scoreText.setFilled(true);
        scoreText.setFontSize(15);
        canvas.add(scoreText);
    }

    public void drawFinalScore(){
        scoreText.setText("FINAL SCORE: " + score );
        scoreText.setPosition(40, Main.CANVAS_HEIGHT/2-50);
        scoreText.setFillColor(Color.WHITE);
        scoreText.setFilled(true);
        scoreText.setFontSize(20);
        canvas.add(scoreText);
    }

    public void resetScore(){
        score = 4; 
    }
}

