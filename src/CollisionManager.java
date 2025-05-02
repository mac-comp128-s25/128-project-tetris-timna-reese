import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Rectangle;

public class CollisionManager {
    private List<Rectangle> collisionList = new ArrayList<Rectangle>();
    private CanvasWindow canvas;
    private Score score;

    public CollisionManager(CanvasWindow canvas, Score score){
        this.canvas = canvas;
        this.score = score;
    }

    public boolean checkBottomCollision(List<Rectangle> rectangleList) {
        for (int i = 0; i< rectangleList.size(); i++) {
            if (rectangleList.get(i).getY() >= Main.CANVAS_HEIGHT - Tetromino.HEIGHT) {
                return true;
            }
        }
        return false;
    }

    private boolean overlap(List<Rectangle> rectangleList) {
        for (Rectangle newRect : rectangleList) {
            if (newRect.getY() < Tetromino.HEIGHT) {
                return true;
            }
        }  
        return false;
    }

    public boolean checkBlockCollision(List<Rectangle> rectangleList){
        for (int i = 0; i<collisionList.size(); i++){
            for (int j = 0; j< rectangleList.size(); j++){
                double bottomCurrent = rectangleList.get(j).getY()+Tetromino.HEIGHT;
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

    public int checkSideCollision(List<Rectangle> rectangleList){
        for (int i = 0; i<collisionList.size(); i++){
            for (int j = 0; j< rectangleList.size(); j++){
                int rowPlaced = (int) collisionList.get(i).getY()/Tetromino.HEIGHT;
                int rowCurrent = (int) rectangleList.get(j).getY()/Tetromino.HEIGHT;
                int colCurrent = (int) rectangleList.get(j).getX()/Tetromino.WIDTH;
                int colPlaced = (int) collisionList.get(i).getX()/Tetromino.WIDTH;
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

    public boolean checkAnyCollision(List<Rectangle> rectangleList){
        if(checkBottomCollision(rectangleList) || checkBlockCollision(rectangleList)){
            if (overlap(rectangleList)){
                Main.setGameOver(true);
                return true;
            }
            collisionList.addAll(rectangleList);
            clearRow();
            return true;
        }
            return false;
    }
    
    public void moveRowDown(int rowCleared){
        for(Rectangle rect: collisionList){
            int rectRow = (int) rect.getY()/Tetromino.HEIGHT;
            if (rectRow < rowCleared){
                rect.setY(rect.getY()+Tetromino.HEIGHT);
            }
        }
        score.updateScore(100);
    }


    public void clearRow() {
        Map <Integer, List<Rectangle>> collisionMap = new HashMap<Integer, List<Rectangle>>();
        for (int i = 0; i < collisionList.size(); i++) {
           Rectangle rect = collisionList.get(i);
            int row = (int) rect.getY()/Tetromino.HEIGHT;
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
        
        for (Integer key: keySet) {
            if(collisionMap.get(key).size()==10){
                List<Rectangle> rectangles = collisionMap.get(key);
                for(Rectangle rect: rectangles){
                    collisionList.remove(rect);
                    canvas.remove(rect);
                }
                moveRowDown(key);
            }
        }
    }
 
    public List<Rectangle> getCollisionList(){
        return collisionList;
    }

    public void clearCollisionList(){
        collisionList.clear();
    }

}
