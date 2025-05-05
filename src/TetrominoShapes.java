import java.awt.Color;
import java.util.Random;

public class TetrominoShapes {
    
    public static final Color[][] square = new Color[][] {
        {null, null, null, null},
        {null, Color.YELLOW, Color.YELLOW, null},
        {null, Color.YELLOW, Color.YELLOW, null},
        {null, null, null, null}
    };

    public static final Color[][] line = new Color[][] {
        {null, null, null, null},
        {Color.CYAN, Color.CYAN, Color.CYAN, Color.CYAN},
        {null, null, null, null},
        {null, null, null, null}
    };

    public static final Color[][] leftL = new Color[][] {
        {null, null, null, null},
        {null, Color.BLUE, null, null},
        {null, Color.BLUE, Color.BLUE, Color.BLUE},
        {null, null, null, null}
    };

    public static final Color[][] rightL = new Color[][] {
        {null, null, null, null},
        {null, null, Color.ORANGE, null},
        {Color.ORANGE, Color.ORANGE, Color.ORANGE, null},
        {null, null, null, null}
    };

    public static final Color[][] forwardS = new Color[][] {
        {null, null, null, null},
        {null, null, Color.GREEN, Color.GREEN},
        {null, Color.GREEN, Color.GREEN, null},
        {null, null, null, null},
    };

    public static final Color[][] backwardS = new Color[][] {
        {null, null, null, null},
        {null, Color.RED, Color.RED, null},
        {null, null, Color.RED, Color.RED},
        {null, null, null, null},
    };

    public static final Color[][] pyramid = new Color[][] {
        {null, null, null, null},
        {null, null, Color.MAGENTA, null},
        {null, Color.MAGENTA, Color.MAGENTA, Color.MAGENTA},
        {null, null, null, null},
    };

    public static Color[][] getRandomTetromino(){
        Color[][] [] tetrominoList = {square, line, leftL, rightL, forwardS, backwardS, pyramid};
        Random random = new Random();
        int index = random.nextInt(7);
        Color[][] shape = tetrominoList[index];
        return shape;
    }

    public static Color[][] easyMode(){
        Color[][] [] tetrominoList = {square, line, leftL, rightL, forwardS, backwardS, pyramid};
        Random random = new Random();
        int index = random.nextInt(7);
        Color[][] shape = tetrominoList[index];
        return shape;
    }

    public static Color [][] hardMode(){
        Color[][] [] tetrominoList = {square, line, leftL, rightL, forwardS, backwardS, pyramid,
            backwardS, forwardS, square, leftL, rightL};
        Random random = new Random();
        int index = random.nextInt(12);
        Color[][] shape = tetrominoList[index];
        return shape;
    }

    public static Color [][] extremeMode(){
        Color[][] [] tetrominoList = {square, line, leftL, rightL, forwardS, backwardS, pyramid,
            forwardS, forwardS, leftL, leftL, square};
        Random random = new Random();
        int index = random.nextInt(12);
        Color[][] shape = tetrominoList[index];
        return shape;
    }
}