import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class GameFrame extends JFrame {
    private JPanel mainPanel;
    private JPanel cellsPanel;
    private JPanel xPanel;
    private JPanel turnPanel;
    private JLabel turnLabel;
    private JPanel oPanel;
    private int mode;
    private ImagePanel[] os;
    private ImagePanel[] xs;
    private ImagePanel[] cells;


    public GameFrame(int mode) {
        super("Tic Tac Toe");
        this.mode = mode;
        setIconImage(new ImageIcon("src/images/icon.png").getImage());
        setResizable(false);
        setSize(800, 600);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(true);
        mainPanel.setBackground(Color.white);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        //mainPanel.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(40)));

        turnPanel = new JPanel(new GridLayout(1, 1));
        turnPanel.setOpaque(true);
        turnPanel.setBackground(Color.WHITE);
        turnPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 20, 10));

        String turn = "";
        if (mode == Game.OFFLINE){
            turn = "Turn: Player1";
        }
        else if (mode == Game.ONLINE_SERVER){
            turn = "Your Turn";
        }
        else if (mode == Game.ONLINE_CLIENT) {
            turn = "Opponent's Turn";
        }
        turnLabel = new JLabel(turn, SwingConstants.CENTER);
        turnLabel.setFont(new Font("Calibri", Font.BOLD, 20));
        turnPanel.add(turnLabel);

        int oNum = (int) Math.ceil((double) (Game.getDim() * Game.getDim()) / 2);
        int xNum = (int) Math.floor((double) (Game.getDim() * Game.getDim()) / 2);

        oPanel = new JPanel(new GridLayout(oNum, 1));
        xPanel = new JPanel(new GridLayout(xNum, 1));

        oPanel.setBackground(Color.WHITE);
        xPanel.setBackground(Color.WHITE);

        oPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(10, 0, 10, 40), "PLAYER 1"));
        xPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(10, 20, 10, 0), "PLAYER 2"));



        //oPanel.setBorder(BorderFactory.createEmptyBorder(10,0,10,40));
        //xPanel.setBorder(BorderFactory.createEmptyBorder(10,40,10,0));

        oPanel.setOpaque(true);
        xPanel.setOpaque(true);

        os = new ImagePanel[oNum];
        xs = new ImagePanel[xNum];

        for (int i = 0; i < oNum; i++) {
            os[i] = new ImagePanel();
            os[i].setImage("src/images/otoken.png");
            oPanel.add(os[i]);
        }
        for (int i = 0; i < xNum; i++) {
            xs[i] = new ImagePanel();
            xs[i].setImage("src/images/xtoken.png");
            xPanel.add(xs[i]);
        }

        cellsPanel = new JPanel(new GridLayout(Game.getDim(), Game.getDim()));
        cellsPanel.setOpaque(true);
        cellsPanel.setBackground(Color.WHITE);
        cellsPanel.setFocusable(false);

        cells = new ImagePanel[Game.getDim() * Game.getDim()];

        for (int i = 0; i < Game.getDim(); i++) {
            for (int j = 0; j < Game.getDim(); j++) {
                int r = 10, l = 10, t = 10, b = 10;
                if (i == 0) {
                    t = 0;
                }
                if (j == 0) {
                    l = 0;
                }
                if (i == Game.getDim() - 1) {
                    b = 0;
                }
                if (j == Game.getDim() - 1) {
                    r = 0;
                }

                cells[i * Game.getDim() + j] = new ImagePanel();
                cells[i * Game.getDim() + j].setBorder(BorderFactory.createMatteBorder(t, l, b, r, Color.BLACK));
                cellsPanel.add(cells[i * Game.getDim() + j]);
            }
        }

        mainPanel.add(turnPanel, BorderLayout.NORTH);
        mainPanel.add(oPanel, BorderLayout.WEST);
        mainPanel.add(cellsPanel, BorderLayout.CENTER);
        mainPanel.add(xPanel, BorderLayout.EAST);
        add(mainPanel, BorderLayout.CENTER);

        for (int i = 0; i < Game.getDim(); i++) {
            for (int j = 0; j < Game.getDim(); j++) {
                cells[i * Game.getDim() + j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                        for (int k = 0; k < Game.getDim(); k++) {
                            for (int l = 0; l < Game.getDim(); l++) {
                                if (e.getSource() == (cells[k * Game.getDim() + l])) {
                                    if (k >= 0 && k < Game.getDim() && l >= 0 && l < Game.getDim()) {
                                        if (Game.getGameLoop().getTable()[k][l] == 'B') {
                                            if (mode == Game.OFFLINE || (mode == Game.ONLINE_SERVER && Game.getGameLoop().getTurn().getSymbol() == 'O') ||
                                                    (mode == Game.ONLINE_CLIENT && Game.getGameLoop().getTurn().getSymbol() == 'X')) {
                                                doTurn(Game.getGameLoop().getTurn(), k, l, mode);
                                            }

                                        }
                                    }
                                }
                            }
                        }
                    }
                });
            }
        }

    }

    public void draw() {
        setVisible(true);
    }

    public void doTurn(Player player, int x, int y, int mod){
        if (mod == Game.ONLINE_SERVER){
            Server.send(x,y);
        }
        else if (mod == Game.ONLINE_CLIENT){
            Client.send(x,y);
        }
        Game.getGameLoop().getTable()[x][y] = player.getSymbol();
        player.decrease();
        if (player.getSymbol() == 'O') {
            os[player.getTokens()].setVisible(false);
        } else {
            xs[player.getTokens()].setVisible(false);
        }
        addToCell(player.getImgPath(), x, y);
        char result = Game.getGameLoop().gameOver();
        if (result != 'B') {
            //end
            if (result != 'D') {
                System.out.println(player.getName() + " WINS!");
                String msg = "";
                if (mode == Game.OFFLINE){
                    msg = player.getName() + "  Wins!";
                }
                else if ((mode == Game.ONLINE_SERVER && player.getSymbol() == 'O') || (mode == Game.ONLINE_CLIENT && player.getSymbol() == 'X')){
                    msg = "You Win!";
                }
                else if ((mode == Game.ONLINE_SERVER && player.getSymbol() == 'X') || (mode == Game.ONLINE_CLIENT && player.getSymbol() == 'O')){
                    msg = "You Lose!";
                }
                JOptionPane.showMessageDialog(this, msg);
                System.exit(0);
            } else {
                System.out.println("Draw!");
                JOptionPane.showMessageDialog(this, "Draw!");
                System.exit(0);
            }
            //dispose();
        } else {
            Game.getGameLoop().changeTurn();
            String turn = turnLabel.getText();
            if (turn == "Turn: Player1"){
                turn = "Turn: Player2";
            }
            else if (turn == "Turn: Player2"){
                turn = "Turn: Player1";
            }
            else if (turn == "Your Turn"){
                turn = "Opponent's Turn";
            }
            else if (turn == "Opponent's Turn"){
                turn = "Your Turn";
            }

            turnLabel.setText(turn);
        }
    }

    public void addToCell(String imgPath, int x, int y) {
        cells[x * Game.getDim() + y].setImage(imgPath);
    }

}
