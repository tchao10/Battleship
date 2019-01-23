/**
 * Created by Tommy on 11/XX/2016.
 */
import java.util.Arrays;
import java.util.Scanner;

public class Fleet{
    //fields--------------------------------------------------------------------------
    private int size;
    private Ship[] allShips;
    //these are default
    private boolean entireFleetSunk = false;
    private String[] allShipNames = new String[]{"Aircraft Carrier", "Battleship", "Destroyer", "Submarine", "Patrol Boat"};
    private int[] allShipLengths = new int[]{5, 4, 3, 3, 2};

    //constructor--------------------------------------------------------------------------
    public Fleet(int size){
        this.size = size;
        this.allShips = new Ship[this.size];
    }

    //public getters and setters--------------------------------------------------------------------------
    public int getSize(){return this.size;}

    public Ship[] getAllShips(){return this.allShips;}
    public void setAllShips(Ship[] newAllShips){this.allShips = newAllShips;}

    public boolean getEntireFleetSunk(){return this.entireFleetSunk;}
    public void setEntireFleetSunk(boolean newEntireFleetSunkCondition){this.entireFleetSunk = newEntireFleetSunkCondition;}

    //public methods--------------------------------------------------------------------------
    /** Processes a shot once it is fired, checking for hit/miss, ship sinking, and victory.
     Input: int[] shot, the shot that is being processed
     Output: none
     */
    public void processShot(int[] shot){
        boolean shotIsAHit = false;

        for (int i=0; i<this.size; i++){
            if (allShips[i].checkIfShotIsAHit(shot)){
                shotIsAHit = true;
                System.out.println("Hit!\n");
                allShips[i].updateShipWhenHit(shot);
                if (allShips[i].checkIfShipIsSunk()){
                    allShips[i].setIsSunk(true);
                    System.out.println("          ***"+allShips[i].getName()+" ("+allShips[i].getLength()+") was sunk!***\n");
                    if (this.checkIfFleetIsSunk()){
                        this.entireFleetSunk = true;
                        System.out.println("          *************************");
                        System.out.println("          **   Fleet was sunk!   **");
                        System.out.println("          *************************\n");
                    }
                }
            }
        }

        if (!shotIsAHit){
            System.out.println("Miss!\n");
        }
    }

    /** Generates a randomized fleet (mainly used for the AI).
     Input: none
     Output: none
     */
    public void generateRandomShips(){
        for (int i=0; i<this.size; i++){
            boolean shipPositionValid = false;
            Ship newRandomShip = new Ship(this.allShipNames[i], this.allShipLengths[i]);
            while (!shipPositionValid){
                newRandomShip.generateRandomStartAndDirection();
                newRandomShip.generateCoordinatesWithStartAndDirection();
                shipPositionValid = this.verifyIfShipPositionIsAvailable(newRandomShip, i);
            }

            allShips[i] = newRandomShip;

            //System.out.println("AI's Ship "+(i+1)+"'s Coordinates (with length "+newRandomShip.getLength()+")");
            //for (int j=0; j<newRandomShip.getLength(); j++){
            //    System.out.println(newRandomShip.getAllCoordinates()[j][0]+", "+newRandomShip.getAllCoordinates()[j][1]);
            //}
            //System.out.println();
        }
    }

    /** Generates a fleet which asks for human input on where to place the ships.
     Input: Display display; the display object of this current game (used to display the board as the human player is placing ships)| Player player; the human player
     Output: none
     */
    public void generatePlayerShips(Display display, Player player){
        for (int i=0; i<this.size; i++) {
            Scanner kboard = new Scanner(System.in);
            boolean shipPositionValid = false;
            while (!shipPositionValid) {
                boolean needValidInput = true;
                String startCoord = "";
                String direction = "";
                while (needValidInput) {
                    display.displayShipsWhilePlacingShips(player);
                    if (i == 0) {
                        System.out.println("Enter the starting coordinate for your "+allShipNames[i]+" (length " + allShipLengths[i] + ") and direction you want to place the ship, separated with a space. (ex. \"b3 right\")");
                    } else {
                        System.out.println("Enter the starting coordinate for your "+allShipNames[i]+" (length " + allShipLengths[i] + ") and direction.");
                    }
                    String shipPlacementInput = kboard.nextLine();
                    if (!shipPlacementInput.contains(" ")){
                        System.out.println("Please follow the correct format. (ex. \"b3 right\") \n");
                    } else {
                        startCoord = shipPlacementInput.substring(0, shipPlacementInput.indexOf(" ")).toLowerCase();
                        direction = shipPlacementInput.substring(shipPlacementInput.indexOf(" ")+1).toLowerCase();

                        if (startCoord.length() == 2 && Character.isLetter(startCoord.charAt(0)) && Character.isDigit(startCoord.charAt(1)) && (direction.equals("up") || direction.equals("down") || direction.equals("left") || direction.equals("right"))) {
                            if (Character.toString(startCoord.charAt(0)).matches("[a-i]") && Character.toString(startCoord.charAt(1)).matches("[1-9]")){
                                needValidInput = false;
                            } else {
                                System.out.println("Your start coordinate is not on the board. Please try again.\n");
                            }
                        } else {
                            System.out.println("Please follow the correct format. (ex. \"b3 right\") \n");
                        }
                    }
                }

                Ship newPlayerShip = new Ship(this.allShipNames[i], this.allShipLengths[i], decodeInputStartCoordinate(startCoord), translateInputDirection(direction));
                newPlayerShip.generateCoordinatesWithStartAndDirection();
                shipPositionValid = this.verifyIfShipPositionIsAvailable(newPlayerShip, i);
                if (shipPositionValid){
                    //for (int j=0; j<newPlayerShip.getLength(); j++){
                    //    System.out.println(newPlayerShip.getAllCoordinates()[j][0]+", "+newPlayerShip.getAllCoordinates()[j][1]);
                    //}
                    allShips[i] = newPlayerShip;
                    System.out.println(this.allShipNames[i]+" was successfully placed.\n");
                } else {
                    System.out.println(this.allShipNames[i]+" was placed in an invalid location.\n");
                }
            }
        }
    }

    /** Decodes the user input of the start coordinate of the ship.
     Input: String startCoord; which is in the format of "b3"
     Output: int[]; which contains two integers, one for the x-coord and one for the y-coord
     */
    public int[] decodeInputStartCoordinate(String startCoord){
        String[] columnLetters = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i"};
        return new int[]{Arrays.asList(columnLetters).indexOf(Character.toString(startCoord.charAt(0))), Character.getNumericValue(startCoord.charAt(1))-1};
    }

    /** Translates the user input of the direction of the ship.
     Input: String direction; which is in plain english
     Output: int; where 0 is up, 1 is right, 2 is down, 3 is left
     */
    public int translateInputDirection(String direction){
        String[] allDirections = new String[]{"up", "right", "down", "left"};
        return Arrays.asList(allDirections).indexOf(direction);
    }

    /** Checks if the placed ship doesn't overlap with another ship and doesn't go off the board.
     Input: Ship verifiedShip; the ship that is being checked for overlap | int numOfShipsGenerated; used to check how many ships are already generated to avoid verifying verifiedShip with an empty Ship object
     Output: boolean; true if the ship space is available, false if it is not available
     */
    public boolean verifyIfShipPositionIsAvailable(Ship verifiedShip, int numOfShipsGenerated){
        boolean shipPositionIsValid = true;

        //checks if ship goes off the board
        for (int i=0; i<verifiedShip.getLength(); i++){
            for (int j=0; j<2; j++){ //j<2 checks for the two x and y coordinates
                if (verifiedShip.getAllCoordinates()[i][j] > 8 || verifiedShip.getAllCoordinates()[i][j] < 0){
                    shipPositionIsValid = false;
                    break;
                }
            }
        }

        //checks ship overlap ONLY after the ship coordinates don't go off the board (saves time)
        if (shipPositionIsValid && numOfShipsGenerated != 0){
            for (int k=0; k<numOfShipsGenerated; k++){ //goes through all the ships in fleet
                for (int l=0; l<allShips[k].getLength(); l++){ //goes through all the coordinates in each ship in the fleet
                    for (int m=0; m<verifiedShip.getLength(); m++){ //goes through all the coordinates of the ship being verified
                        if (Arrays.equals(allShips[k].getAllCoordinates()[l], verifiedShip.getAllCoordinates()[m])){
                            shipPositionIsValid = false;
                            break;
                        }
                    }
                }
            }
        }

        return shipPositionIsValid;
    }

    /** Checks if the entire fleet is sunk by checking the isSunk field of each ship.
     Input: none
     Output: boolean; true if the entire fleet is sunk, false if it is not sunk
     */
    public boolean checkIfFleetIsSunk(){
        for (int i=0; i<this.size; i++){
            if (!allShips[i].getIsSunk()){
                return false;
            }
        }
        return true;
    }

    /** Checks if a shot hits ANY ship in the fleet
     Input: int[] shot; the shot that is being checked for whether or not it hit a ship
     Output: boolean; true if the entire fleet is sunk, false if it is not sunk
     */
    public boolean checkIfShotHitsAnyShip(int[] shot){
        boolean shotIsAHit = false;

        for (int i=0; i<this.size; i++){
            if (allShips[i].checkIfShotIsAHit(shot)){
                shotIsAHit = true;
            }
        }

        return shotIsAHit;
    }

    //main method--------------------------------------------------------------------------
    public static void main(String[] args){
        //Fleet fleet1 = new Fleet(5);
        //fleet1.generateRandomShips();
        //fleet1.generatePlayerShips();
    }
}