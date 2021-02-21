public class ServerGame extends Game {


    private Server server;

    public ServerGame(Server server) {
        super();
        this.server = server;
    }

    @Override
    public void start() {
        gameLoop = new GameLoop();
        gameFrame = new GameFrame(Game.ONLINE_SERVER);
        gameFrame.draw();
    }

    public Server getServer() {
        return server;
    }
}
