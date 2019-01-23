/**
 * Created by Tommy on 11/XX/2016.
 */
public class Game{
    //fields----------------------------------------------------------------------------------------------------
    public Player[] players;
    private int turnCounter = 0;
    private int whoseTurn = 1; //1 is human, -1 is AI
    private boolean gameIsRunning = false;

    //constructor----------------------------------------------------------------------------------------------------
    //players[0] is human, players[1] is AI
    public Game(){
        this.players = new Player[2];
    }

    //public getters and setters------------------------------------------------------------------------------
    public Player[] getPlayers(){return this.players;}
    public void setPlayers(Player[] newPlayers){this.players = newPlayers;}

    public int getTurnCounter(){return this.turnCounter;}
    public void setTurnCounter(int newTurnCounter){this.turnCounter = newTurnCounter;}

    public int getWhoseTurn(){return this.whoseTurn;}
    public void getWhoseTurn(int newWhoseTurn){this.whoseTurn = newWhoseTurn;}

    public boolean getGameIsRunning(){return this.gameIsRunning;}
    public void setGameIsRunning(boolean newGameIsRunningCondition){this.gameIsRunning = newGameIsRunningCondition;}

    //public methods----------------------------------------------------------------------------------------------------
    /** Plays the entire game by creating the two players, building their fleets, and running a while loop while the game is still running which alternates between turns.
     Input: none
     Output: none
     */
    public void play(){
        this.gameIsRunning = true;
        Display display = new Display();
        Player human = new Player(false, 5);
        this.players[0] = human;
        Player AI = new Player(true, 5);
        this.players[1] = AI;
        System.out.println("Building human's fleet...\n");
        human.buildFleet(display, human);
        System.out.println("Done building human's fleet.\n");
        System.out.println("Building AI fleet...");
        AI.buildFleet(display, AI);
        System.out.println("Done building AI fleet.\n");

        //game logic
        while(this.gameIsRunning){
            //human's turn------------------------------
            if (whoseTurn == 1){
                display.showShotsBoard(human, AI);
                System.out.println();
                display.showShipsBoard(human, AI);
                int[] humanShot = human.promptUserToShoot();
                human.addShotIntoShotsCalled(humanShot, AI);
                AI.receiveShot(humanShot);
                if (AI.getLostGame()){
                    display.showShotsBoard(human, AI);
                    System.out.println();
                    display.showShipsBoard(human, AI);
                    this.gameIsRunning = false;
                    System.out.println("You win!\n-------------------------------------------------------------");
                }
            }

            //AI's turn---------------------------------
            else {
                System.out.println("AI's turn: ");
                int[] AIShot = AI.chooseARandomCoordinateToShoot();
                AI.addShotIntoShotsCalled(AIShot, human);
                human.receiveShot(AIShot);
                if (!human.getLostGame()){
                    AIShot = AI.chooseARandomCoordinateToShoot();
                    AI.addShotIntoShotsCalled(AIShot, human);
                    human.receiveShot(AIShot);
                }
                if (human.getLostGame()){
                    display.showShotsBoard(human, AI);
                    System.out.println();
                    display.showShipsBoard(human, AI);
                    this.gameIsRunning = false;
                    System.out.println("You lost...");
                }
                System.out.println("-------------------------------------------------------------");
            }
            turnCounter++;
            whoseTurn *= -1;
        }
    }

    //main method----------------------------------------------------------------------------------------------------
    public static void main(String[] args){
        //Game game1 = new Game();
        //game1.play();
    }
}