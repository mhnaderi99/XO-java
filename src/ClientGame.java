public class ClientGame extends Game {

    private Client client;

    public ClientGame(Client client){
        super();
        this.client = client;
    }

    @Override
    public void start() {
        gameLoop = new GameLoop();
        gameFrame = new GameFrame(Game.ONLINE_CLIENT);
        gameFrame.draw();
    }

    public Client getClient() {
        return client;
    }
}
