public class Game {

    private static final int dim = 3;
    public static final int OFFLINE = 1;
    public static final int ONLINE_SERVER = 2;
    public static final int ONLINE_CLIENT = 3;
    protected static GameLoop gameLoop;
    protected static GameFrame gameFrame;

    public static int getDim() {
        return dim;
    }

    public static GameFrame getGameFrame() {
        return gameFrame;
    }

    public static GameLoop getGameLoop() {
        return gameLoop;
    }

    public void start(){
        gameLoop = new GameLoop();
        gameFrame = new GameFrame(Game.OFFLINE);
        gameFrame.draw();
    }
}
