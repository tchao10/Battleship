import java.util.Arrays;

public class Display{
    //fields----------------------------------------------------------------------------------------------------
    //- - water, x - hit, m - miss, s - ship
    private int[] column = new int[]{1,2,3,4,5,6,7,8,9,10}; //top of each column is a number
    private int[] row = new int[]{1,2,3,4,5,6,7,8,9,10}; //start of each row are letters
    private String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    //if hit X
    //if miss O
    //if Ship S
    //if ocean -

    //constructors
    public Display(){
    }

    //public getters and setters----------------------------------------------------------------------------------------------------

    //public methods----------------------------------------------------------------------------------------------------
    /** Displays the human's current ship board while they are placing their ships.
     * Input: Player activePlayer; the player whose ship board is currently being displayed
     * Output: none
     */
    public void displayShipsWhilePlacingShips(Player activePlayer){
        System.out.println(" Your ships:");
        //write a for loop to write the whole grid
        for(int i = 0; i < column.length; i++){ //loops through each row
            for(int j = 0; j < row.length; j++){ //loops through each column in each row
                if(i == 0 && j == 0){//writes the letters
                    for(int s = 0;  s < column.length-1; s++){
                        if(s == 0)
                            System.out.print("  " + alphabet.charAt(s)+" ");
                        else
                            System.out.print(alphabet.charAt(s)+" ");
                    }
                }
                if(j == 0 && i != 0){
                    //writes the numbers
                    System.out.print(i+" ");
                } else if (i != 0){
                    int[] currentCoordinate = new int[]{j-1, i-1};

                    boolean dontPrintOcean = false;

                    if (activePlayer.getFleet().getSize() != 0 && activePlayer.getFleet().getAllShips()[0] != null){
                        int numOfShipsAlreadyPlaced = 0;
                        for (int m=0; m<activePlayer.getFleet().getSize(); m++){
                            if (activePlayer.getFleet().getAllShips()[m] == null){
                                break;
                            } else {
                                numOfShipsAlreadyPlaced++;
                            }
                        }

                        for (int k = 0; k < numOfShipsAlreadyPlaced; k++) {
                            //prints first letter of ship
                            for (int l = 0; l < activePlayer.getFleet().getAllShips()[k].getLength(); l++) {
                                if (Arrays.equals(currentCoordinate, activePlayer.getFleet().getAllShips()[k].getAllCoordinates()[l])) {
                                    System.out.print(activePlayer.getFleet().getAllShips()[k].getName().charAt(0) + " ");
                                    dontPrintOcean = true;
                                }
                            }
                        }
                    }

                    //prints - (ocean)
                    if (!dontPrintOcean){
                        System.out.print("- ");
                    }
                }
            }
            System.out.println();
        }
        System.out.println("-------------------------------------------------------------\n");
    }

    /** Shows the activePlayer's shots board which shows which shots the player has taken and whether or not they are hit or miss.
     * Input: Player activePlayer; the player of this current turn | Player opponent; the player who is not acting on this turn
     * Output: none
     */
    public void showShotsBoard(Player activePlayer, Player opponent){
        System.out.println(" Your shots:");
        //write a for loop to write the whole grid
        for(int i = 0; i < column.length; i++){
            for(int j = 0; j < row.length; j++){
                if(i == 0 && j == 0){//writes the letters
                    for(int s = 0;  s < column.length-1; s++){
                        if(s == 0){
                            System.out.print("  "+alphabet.charAt(s)+" ");
                        } else {
                            System.out.print(alphabet.charAt(s)+" ");
                        }
                    }
                }
                if(j == 0 && i != 0){
                    //writes the numbers
                    System.out.print(i+" ");
                } else if (i != 0){
                    int[] currentCoordinate = new int[]{j-1, i-1};

                    boolean dontPrintOcean = false;
                    boolean coordinateWasMissed = false;

                    //prints O (misses from active player)
                    for (int n=0; n<activePlayer.getMissShotsCalled().length; n++) {
                        if (Arrays.equals(currentCoordinate, activePlayer.getMissShotsCalled()[n])) {
                            System.out.print("O ");
                            coordinateWasMissed = true;
                            dontPrintOcean = true;
                        }
                    }

                    //prints X (hits from active player)
                    if (!coordinateWasMissed){
                        if (activePlayer.getHitShotsCalled().length != 0) {
                            for (int m = 0; m < activePlayer.getHitShotsCalled().length; m++) {
                                if (Arrays.equals(currentCoordinate, activePlayer.getHitShotsCalled()[m])) {
                                    System.out.print("X ");
                                    dontPrintOcean = true;
                                }
                            }
                        }
                    }


                    //prints - (ocean)
                    if (!dontPrintOcean){
                        System.out.print("- ");
                    }
                }
            }
            System.out.println("");
        }
    }

    /** Shows the activePlayer's ships board which shows the activePlayer's fleet.
     * Input: Player activePlayer; the player of this current turn | Player opponent; the player who is not acting on this turn
     * Output: none
     */
    public void showShipsBoard(Player activePlayer, Player opponent){
        System.out.println(" Your ships:");
        //write a for loop to write the whole grid
        for(int i = 0; i < column.length; i++){ //loops through each row
            for(int j = 0; j < row.length; j++){ //loops through each column in each row
                if(i == 0 && j == 0){//writes the letters
                    for(int s = 0;  s < column.length-1; s++){
                        if(s == 0)
                            System.out.print("  " + alphabet.charAt(s)+" ");
                        else
                            System.out.print(alphabet.charAt(s)+" ");
                    }
                }
                if(j == 0 && i != 0){
                    //writes the numbers
                    System.out.print(i+" ");
                } else if (i != 0){
                    int[] currentCoordinate = new int[]{j-1, i-1};

                    boolean dontPrintOcean = false;
                    boolean coordinateWasMissed = false;
                    boolean coordinateIsDamaged = false;

                    //prints O (misses from opponent)
                    for (int n=0; n<opponent.getMissShotsCalled().length; n++) {
                        if (Arrays.equals(currentCoordinate, opponent.getMissShotsCalled()[n])) {
                            System.out.print("O ");
                            coordinateWasMissed = true;
                            dontPrintOcean = true;
                        }
                    }

                    if (!coordinateWasMissed){
                        for (int k = 0; k < activePlayer.getFleet().getSize(); k++) {
                            //prints X (active player's hits)
                            if (activePlayer.getFleet().getAllShips()[k].getNumOfCoordinatesDamaged() != 0) {
                                for (int m = 0; m < activePlayer.getFleet().getAllShips()[k].getNumOfCoordinatesDamaged(); m++) {
                                    if (Arrays.equals(currentCoordinate, activePlayer.getFleet().getAllShips()[k].getDamagedCoordinates()[m])) {
                                        System.out.print("X ");
                                        coordinateIsDamaged = true;
                                        dontPrintOcean = true;
                                    }
                                }
                            }

                            //prints first letter of ship
                            if (!coordinateIsDamaged) {
                                for (int l = 0; l < activePlayer.getFleet().getAllShips()[k].getLength(); l++) {
                                    if (Arrays.equals(currentCoordinate, activePlayer.getFleet().getAllShips()[k].getAllCoordinates()[l])) {
                                        System.out.print(activePlayer.getFleet().getAllShips()[k].getName().charAt(0) + " ");
                                        dontPrintOcean = true;
                                    }
                                }
                            }
                        }
                    }

                    //prints - (ocean)
                    if (!dontPrintOcean){
                        System.out.print("- ");
                    }
                }
            }
            System.out.println();
        }
        System.out.println("-------------------------------------------------------------\n");
    }

    //main method----------------------------------------------------------------------------------------------------
    public static void main(String[] args){
        //Display d = new Display();
        //d.showBottomBoard();
    }
}