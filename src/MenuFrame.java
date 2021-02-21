import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MenuFrame extends JFrame {


    public MenuFrame(){
        super("Menu");
        setIconImage(new ImageIcon("src/images/icon.png").getImage());
        setResizable(false);
        setSize(400, 500);

        JPanel main = new JPanel(new BorderLayout());
        main.setOpaque(true);
        main.setBackground(Color.WHITE);
        main.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));

        JPanel logoPanel = new JPanel(new BorderLayout());
        logoPanel.setBackground(Color.WHITE);

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src/images/logo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Image dimg = img.getScaledInstance(300, 200, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(dimg);
        JLabel logo = new JLabel(imageIcon);

        logoPanel.add(logo, BorderLayout.SOUTH);
        logoPanel.setBorder(BorderFactory.createEmptyBorder(20,10,10,10));


        JPanel buttons = new JPanel(new GridLayout(3,1,10,10));
        buttons.setBackground(Color.WHITE);
        buttons.setOpaque(true);
        JButton create, join, play;
        play = new JButton("Play Offline");
        play.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Game game = new Game();
                game.start();
                dispatchEvent(new WindowEvent(MenuFrame.this, WindowEvent.WINDOW_CLOSING));

            }
        });

        create = new JButton("Create Server");
        create.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Server server = new Server();
                ServerGame serverGame = new ServerGame(server);
                //SwingUtilities.invokeLater(server);
                Thread t = new Thread(server);
                t.start();
                serverGame.start();
                dispatchEvent(new WindowEvent(MenuFrame.this, WindowEvent.WINDOW_CLOSING));
            }
        });

        join = new JButton("Join Server");
        join.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Client client = new Client();
                ClientGame clientGame = new ClientGame(client);
                //SwingUtilities.invokeLater(client);
                Thread t = new Thread(client);
                t.start();
                clientGame.start();
                dispatchEvent(new WindowEvent(MenuFrame.this, WindowEvent.WINDOW_CLOSING));
            }
        });

        play.setFont(new Font("Calibri", Font.BOLD, 30));
        create.setFont(new Font("Calibri", Font.BOLD, 30));
        join.setFont(new Font("Calibri", Font.BOLD, 30));

        play.setBackground(new Color(30,0,50));
        play.setForeground(Color.WHITE);
        play.setFocusable(false);

        create.setBackground(new Color(30,0,50));
        create.setForeground(Color.WHITE);
        create.setFocusable(false);

        join.setBackground(new Color(30,0,50));
        join.setForeground(Color.WHITE);
        join.setFocusable(false);

        buttons.add(play);
        buttons.add(create);
        buttons.add(join);
        main.add(logoPanel, BorderLayout.NORTH);
        main.add(buttons, BorderLayout.SOUTH);
        add(main);
    }

    public void draw(){
        this.setVisible(true);
    }
}
