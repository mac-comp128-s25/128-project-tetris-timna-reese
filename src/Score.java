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
        scoreText.setCenter(Main.CANVAS_WIDTH / 2, 750);
        scoreText.setFillColor(Color.WHITE);
        scoreText.setFilled(true);
        canvas.add(scoreText);
    }

    public void resetScore(){
        score = 4; 
    }
}

