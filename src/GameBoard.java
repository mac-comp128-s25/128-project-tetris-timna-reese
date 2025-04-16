public class GameBoard {
    private boolean[][] collisionTracker;
    public GameBoard() {
        collisionTracker = new boolean[Main.CANVAS_WIDTH / 10][Main.CANVAS_HEIGHT / 20];
    }
}
