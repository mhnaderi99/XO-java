import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {


    private static OutputStream out;
    private static InputStream in;

    @Override
    public void run() {
        try (ServerSocket server = new ServerSocket(2019)) {
            System.out.print("Server started.\nWaiting for a client ... ");

            try (Socket client = server.accept()) {
                System.out.println("client accepted!");
                while (true) {
                    out = client.getOutputStream();
                    in = client.getInputStream();
                    byte[] buffer = new byte[2048];
                    int read = in.read(buffer);
                    String inp = new String(buffer, 0, read);
                    proccess(inp);
                }
                //System.out.println("RECV: " + new String(buffer, 0, read));
            }

            //System.out.print("All messages sent.\nClosing client ... ");
        } catch (IOException ex) {
            System.err.println(ex);
        }
        System.out.print("done.\nClosing server ... ");
        System.out.println("done.");
    }

    public static void send(int x, int y) {
        String s = x + "," + y;
        try {
            out.write(s.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void proccess(String s) {
        String d[] = new String[2];
        d = s.split(",");
        int x = Integer.parseInt(d[0]);
        int y = Integer.parseInt(d[1]);
        Game.getGameFrame().doTurn(Game.getGameLoop().getTurn(), x, y, Game.OFFLINE);
    }
}
