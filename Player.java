/**
 * Created by Tommy on 11/XX/2016.
 */
import java.util.Arrays;
import java.util.Scanner;

public class Player{
    //fields-----------------------------------------------------------------------------------------------
    private boolean isAI;
    private Fleet fleet;
    //these are default
    private boolean lostGame = false;
    private int numOfShipsSunk = 0;
    private int[][] allShotsCalled = new int[0][2];
    private int[][] hitShotsCalled = new int[0][2];
    private int[][] missShotsCalled = new int[0][2];

    //constructors-----------------------------------------------------------------------------------------------
    public Player(boolean isAI, int fleetSize){
        this.isAI = isAI;
        this.fleet = new Fleet(fleetSize);
    }

    //public getters and setters-----------------------------------------------------------------------------------------------
    public boolean getLostGame(){return this.lostGame;}
    public void setLostGame(boolean newLostGameCondition){this.lostGame = newLostGameCondition;}

    public int getNumOfShipsSunk(){return this.numOfShipsSunk;}
    public void setNumOfShipsSunk(int newNumOfShipsSunk){this.numOfShipsSunk = newNumOfShipsSunk;}

    public int[][] getAllShotsCalled(){return this.allShotsCalled;}
    public void setAllShotsCalled(int[][] newAllShotsCalled){this.allShotsCalled = newAllShotsCalled;}

    public int[][] getHitShotsCalled(){return this.hitShotsCalled;}
    public void setHitShotsCalled(int[][] newHitShotsCalled){this.hitShotsCalled = newHitShotsCalled;}

    public int[][] getMissShotsCalled(){return this.missShotsCalled;}
    public void setMissShotsCalled(int[][] newMissShotsCalled){this.missShotsCalled = newMissShotsCalled;}

    public Fleet getFleet(){return this.fleet;}
    public void setFleet(Fleet newFleet){this.fleet = newFleet;}

    public boolean getIsAI(){return this.isAI;}
    public void setIsAI(boolean newIsAICondition){this.isAI = newIsAICondition;}

    //public methods-----------------------------------------------------------------------------------------------
    /** Places the ships (randomly if this.isAI == true, user input if this.isAI == false)
        Input: Display display; the display object of this current game (used to display the board as the human player is placing ships)| Player player; the human player
        Output: none
     */
    public void buildFleet(Display display, Player player){
        if(this.isAI){
            this.fleet.generateRandomShips();
        } else {
            this.fleet.generatePlayerShips(display, player);
        }
    }

    /** Chooses a random coordinate to shoot at on the board.
     Input: none
     Output: int[]; the shot that was randomly generated
     */
    public int[] chooseARandomCoordinateToShoot(){
        boolean needNewRandomShot = true;
        int[] newRandomShot = new int[2];
        while (needNewRandomShot){
            newRandomShot = new int[]{(int)(Math.random()*9), (int)(Math.random()*9)};
            if (this.allShotsCalled.length != 0) {
                boolean shotIsNew = true;

                for (int i=0; i<this.allShotsCalled.length; i++) {
                    if (this.allShotsCalled[i][0] == newRandomShot[0] && this.allShotsCalled[i][1] == newRandomShot[1]){
                        shotIsNew = false;
                    }
                }

                if (shotIsNew){
                    needNewRandomShot = false;
                }
            } else {
                needNewRandomShot = false;
            }
        }

        return newRandomShot;
    }

    /** Prompts the user to fire at a certain coordinate of the board.
        Input: none
        Output: int[]; the shot that the user wishes to fire at
     */
    public int[] promptUserToShoot(){
        Scanner kboard = new Scanner(System.in);
        boolean needValidInput = true;
        int[] decodedShotCoord = new int[2];
        while (needValidInput) {
            if (this.allShotsCalled.length == 0) {
                System.out.println("Choose a coordinate to fire at. (ex. \"b3\")");
            } else {
                System.out.println("Choose a coordinate to fire at.");
            }
            String shotCoord = kboard.nextLine();

            //Checks if the input was in the correct format.
            if (shotCoord.length() == 2 && Character.isLetter(shotCoord.charAt(0)) && Character.isDigit(shotCoord.charAt(1))){
                //Checks if the input was a coordinate that was on the board.
                if (Character.toString(shotCoord.charAt(0)).matches("[a-i]") && Character.toString(shotCoord.charAt(1)).matches("[1-9]")){
                    decodedShotCoord = decodeFiredShotCoord(shotCoord);
                    //Checks if the inputted shot was not already fired at.
                    if (this.allShotsCalled.length != 0) {
                        boolean shotIsNew = true;

                        for (int i=0; i<this.allShotsCalled.length; i++) {
                            if (this.allShotsCalled[i][0] == decodedShotCoord[0] && this.allShotsCalled[i][1] == decodedShotCoord[1]){
                                shotIsNew = false;
                                System.out.println("You already shot at this coordinate! Try again.\n");
                            }
                        }

                        if (shotIsNew){
                            needValidInput = false;
                        }
                    } else {
                        needValidInput = false;
                    }
                } else {
                    System.out.println("Your shot is not even on the board. Please aim better.\n");
                }
            } else {
                System.out.println("Please follow the correct format. (ex. \"b3\") \n");
            }
        }

        return decodedShotCoord;
    }

    /** Decodes the user input of the coordinates of the fired shot (b3 decodes into (1,2)).
        Input: String shotCoord; which is in the format of "b3"
        Output: int[]; which contains two integers, one for the x-coord and one for the y-coord
     */
    public int[] decodeFiredShotCoord(String shotCoord){
        String[] columnLetters = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i"};
        return new int[]{Arrays.asList(columnLetters).indexOf(Character.toString(shotCoord.charAt(0))), Character.getNumericValue(shotCoord.charAt(1))-1};
    }

    /** Calls this.fleet.processShot() and checks if the player has lost the game.
        Input: none
        Output: none
     */
    public void receiveShot(int[] shot){
        //Processes shot
        this.fleet.processShot(shot);

        //Checks if the player has lost the game.
        if (this.fleet.checkIfFleetIsSunk()){
            this.lostGame = true;
        }
    }

    /** Adds the shot that was just fired into this.allShotsCalled and this.hitShotsCalled if it was a hit and this.missShotsCalled if it was a miss
     Input: int[] shot; the shot that was just fired | Player receivingPlayer; the player that receives the shot
     Output: none
     */
    public void addShotIntoShotsCalled(int[] shot, Player receivingPlayer){
        this.allShotsCalled = Arrays.copyOf(this.allShotsCalled, this.allShotsCalled.length + 1);
        this.allShotsCalled[this.allShotsCalled.length-1] = shot;

        if (receivingPlayer.getFleet().checkIfShotHitsAnyShip(shot)){
            this.hitShotsCalled = Arrays.copyOf(this.hitShotsCalled, this.hitShotsCalled.length + 1);
            this.hitShotsCalled[this.hitShotsCalled.length-1] = shot;
        } else {
            this.missShotsCalled = Arrays.copyOf(this.missShotsCalled, this.missShotsCalled.length + 1);
            this.missShotsCalled[this.missShotsCalled.length-1] = shot;
        }
    }

    //main method------------------------------------------------------------------------------------------------
    public static void main(String[] args){
        //Player human = new Player(false, 5);
        //human.buildFleet();
        //human.promptUserToShoot();
        //human.promptUserToShoot();
    }
}