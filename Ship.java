/**
 * Created by Tommy on 11/XX/2016.
 */

public class Ship{
    //fields--------------------------------------------------------------------------
    private String name;
    private int length; //endCoordinate - startCoordinate + 1
    private int[] startCoordinate;
    private int directionFromStart; //0 is up, 1 is right, 2 is down, 3 is left
    private int[] endCoordinate;
    //these are default
    private int[][] allCoordinates; //array of ordered pairs
    private int numOfCoordinatesDamaged = 0;
    private int[][] damagedCoordinates; //array of ordered pairs
    private boolean isSunk = false;

    //constructors--------------------------------------------------------------------------
    //This constructor is for the AI's ships.
    public Ship(String name, int length){
        this.name = name;
        this.length = length;
        this.allCoordinates = new int[length][2];
        this.damagedCoordinates = new int[length][2];
    }

    //This constructor is for the human's ships.
    public Ship(String name, int length, int[] start, int direction){
        this.name = name;
        this.length = length;
        this.startCoordinate = start;
        this.directionFromStart = direction;
        this.allCoordinates = new int[length][2];
        this.damagedCoordinates = new int[length][2];
    }

    //public getters and setters--------------------------------------------------------------------------
    public String getName(){return this.name;}
    public void setName(String newName){this.name = newName;}

    public int getLength(){return this.length;}
    public void setLength(int newLength){this.length = newLength;}

    public int[] getStartCoordinate(){return this.startCoordinate;}

    public int getDirectionFromStart(){return this.directionFromStart;}

    public int[] getEndCoordinate(){return this.endCoordinate;}

    public int[][] getAllCoordinates(){return this.allCoordinates;}
    public void setAllCoordinates(int[][] newShipCoordinates){this.allCoordinates = newShipCoordinates;}

    public int getNumOfCoordinatesDamaged(){return this.numOfCoordinatesDamaged;}
    public void setNumOfCoordinatesDamaged(int newNumDamaged){this.numOfCoordinatesDamaged = newNumDamaged;}

    public int[][] getDamagedCoordinates(){return this.damagedCoordinates;}
    public void setDamagedCoordinates(int[][] newDamagedCoordinates){this.damagedCoordinates = newDamagedCoordinates;}

    public void setIsSunk(boolean newIsSunkCondition){this.isSunk = newIsSunkCondition;}
    public boolean getIsSunk(){return this.isSunk;}

    //public methods--------------------------------------------------------------------------
    /** Generates a random start coordinate between (0,0) and (8,8) as an int[2] and generates a random start direction between 0 and 3 (0 is up, 1 is right, 2 is down, 3 is left).
        Input: none
        Output: none
     */
    public void generateRandomStartAndDirection(){
        this.startCoordinate = new int[]{(int)(Math.random()*9), (int)(Math.random()*9)};
        this.directionFromStart = (int)(Math.random()*4);
    }

    /** Generates the coordinates of the ship using the starting coordinate and direction that it is placed in.
        Input: none
        Output: none
     */
    public void generateCoordinatesWithStartAndDirection(){
        this.allCoordinates[0] = this.startCoordinate;

        if (this.directionFromStart == 0){ //if direction is UP
            for (int i=0; i<this.length; i++){
                this.allCoordinates[i] = new int[]{this.startCoordinate[0], this.startCoordinate[1]-i};
            }
        } else if (this.directionFromStart == 1){ //if direction is RIGHT
            for (int i=0; i<this.length; i++){
                this.allCoordinates[i] = new int[]{this.startCoordinate[0]+i, this.startCoordinate[1]};
            }
        } else if (this.directionFromStart == 2){ //if direction is DOWN
            for (int i=0; i<this.length; i++){
                this.allCoordinates[i] = new int[]{this.startCoordinate[0], this.startCoordinate[1]+i};
            }
        } else if (this.directionFromStart == 3){ //if direction is LEFT
            for (int i=0; i<this.length; i++){
                this.allCoordinates[i] = new int[]{this.startCoordinate[0]-i, this.startCoordinate[1]};
            }
        }

        //for (int j=0; j<this.length; j++){
        //    System.out.println(this.allCoordinates[j][0]+", "+this.allCoordinates[j][1]);
        //}
    }

    /** Checks if the fired shot is a hit on this ship.
        Input: int[] shot; the coordinates of the fired shot that is now being checked
        Output: boolean; true if it hit this ship, false if it missed this ship
     */
    public boolean checkIfShotIsAHit(int[] shot){
        for (int i=0; i<this.length; i++){
            if (this.allCoordinates[i][0] == shot[0] && this.allCoordinates[i][1] == shot[1]){
                return true;
            }
        }
        return false; //else block is unnecessary
    }

    /** Updates the ship's damagedCoordinates and numOfCoordinatesDamaged fields.
        Input: int[] shot; the coordinates of the hit shot
        Output: none
     */
    public void updateShipWhenHit(int[] shot){
        damagedCoordinates[numOfCoordinatesDamaged] = shot;
        numOfCoordinatesDamaged++;
    }

    /** Checks if the ship is sunk by comparing the numOfCoordinatesDamaged to the length of the ship.
        Input: none
        Output: boolean; true if the ship is sunk, false if the ship isn't sunk
     */
    public boolean checkIfShipIsSunk(){
        if (this.numOfCoordinatesDamaged == this.allCoordinates.length){
            return true;
        }
        return false; //else unnecessary
    }

    //main method--------------------------------------------------------------------------
    public static void main(String[] args){
        //Ship ship1 = new Ship("Randomized Ship", 5);
        //ship1.generateRandomStartAndDirection();
        //ship1.generateCoordinatesWithStartAndDirection();
    }
}