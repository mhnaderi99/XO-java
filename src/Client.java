import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client implements Runnable{

    private static InputStream in;
    private static OutputStream out;

    @Override
    public void run() {

        try {
            Socket server = new Socket("127.0.0.1", 2019);
            System.out.println("Connected to server.");
            while (true) {
                out = server.getOutputStream();
                in = server.getInputStream();
                byte[] buffer = new byte[2048];
                int read = in.read(buffer);
                String inp = new String(buffer, 0, read);
                proccess(inp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*

        try (Socket server = new Socket("127.0.0.1", 2019)) {
            System.out.println("Connected to server.");

            out = server.getOutputStream();
            in = server.getInputStream();
            byte[] buffer = new byte[2048];
            int read = in.read(buffer);
            String inp = new String(buffer, 0, read);
            proccess(inp);
            System.out.print("All messages sent.\nClosing ... ");
        } catch (IOException ex) {
            System.err.println(ex);
        }
        System.out.println("done.");
        */
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
