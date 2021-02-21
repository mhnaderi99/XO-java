public class GameLoop {


    private Player player1;
    private Player player2;
    private char[][] table;
    private Player turn;

    public GameLoop(){
        player1 = new Player("Player1", 'O', (int) Math.ceil((double)(Game.getDim()*Game.getDim())/2), "src/images/o.png");
        player2 = new Player("Player2", 'X', (int) Math.floor((double)(Game.getDim()*Game.getDim())/2), "src/images/x.png");
        table = new char[Game.getDim()][Game.getDim()];
        for(int i = 0; i < Game.getDim(); i++){
            for(int j = 0; j < Game.getDim(); j++){
                table[i][j] = 'B';
            }
        }
        turn = player1;
    }

    public void changeTurn(){
        if(turn == player1){
            turn = player2;
        }
        else {
            turn = player1;
        }
    }

    public char[][] getTable() {
        return table;
    }

    public Player getTurn() {
        return turn;
    }


    private void printTable(){
        for (int i=0;i<Game.getDim();i++){
            for (int j=0;j<Game.getDim();j++){
                System.out.print(table[i][j] + " ");
            }
            System.out.println();
        }
    }

    public char gameOver(){
        for (int i=0;i<Game.getDim();i++){
            if (table[i][0] == 'B'){
                continue;
            }
            boolean flag = false;
            for(int j=0;j<Game.getDim()-1;j++){
                if (table[i][j] != table[i][j+1]){
                    flag = true;
                    break;
                }
                else continue;
            }
            if (!flag) {
                return turn.getSymbol();
            }
        }

        for (int j=0;j<Game.getDim();j++){
            if (table[0][j] == 'B'){
                continue;
            }
            boolean flag = false;
            for(int i=0;i<Game.getDim()-1;i++){
                if (table[i][j] != table[i+1][j]){
                    flag = true;
                    break;
                }
                else continue;
            }
            if (!flag) {
                return turn.getSymbol();
            }
        }

        boolean flag = false;
        for (int i=0;i<Game.getDim() - 1;i++){
            if (table[i][i] == 'B'){
                flag = true;
                break;
            }
            if (table[i][i] != table[i+1][i+1]){
                flag = true;
                break;
            }
        }
        if (!flag){
            return turn.getSymbol();
        }

        flag = false;
        for (int i=0;i<Game.getDim() - 1;i++){
            if (table[i][Game.getDim() - i - 1] == 'B'){
                flag = true;
                break;
            }
            if (table[i][Game.getDim() - i - 1] != table[i+1][Game.getDim() - i - 2]){
                flag = true;
                break;
            }
        }
        if (!flag){
            return turn.getSymbol();
        }

        if (player1.getTokens() == 0 && player2.getTokens() == 0){
            return 'D';
        }

        return 'B';
    }

}
