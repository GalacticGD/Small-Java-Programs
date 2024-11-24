import java.util.*;
import java.io.*;

public class MineSweeper{
    public static char[][] map;
    public static char[][] progress;

    //public static int[][] mineCooridinates;
    
    public static void main(String[] args){
        Scanner console = new Scanner(System.in);
        Random rand = new Random();
        boolean gameInProgress = true;
        
        
        System.out.println("Welcome to MineSweeper!");
        System.out.print("Choose game difficulty (NOTE: Difficulty: 1 to 10): ");
        int difficulty = console.nextInt();
        
        //Initialize and generate arrays
        map = new char[difficulty][difficulty];
        progress = new char[difficulty][difficulty];
        fillArray(map, '0');
        fillArray(progress, '?');
        generateMines(rand, difficulty);
        generateNumbers();
        System.out.println();
        
        clearStartArea(rand, difficulty);
        
        while(gameInProgress){
            printArray(map);
            System.out.println();
            printArray(progress);
            
            System.out.print("\nPress [1] to Dig or [2] to place a mine: ");
            int next = console.nextInt();
            System.out.print("X-Cooridinate: ");
            int x = console.nextInt();
            System.out.print("Y-Cooridinate: ");
            int y = console.nextInt();
            
            if (next == 1){
                gameInProgress = checkForMine(x, y);
            } else{
                progress[x][y] = 'X';
            }
            System.out.println();
            gameInProgress = !arraysAreEqual(map, progress);
        }
        
        
    }
    
    //Gameplay Functions
    
    public static void clearStartArea(Random rand, int difficulty){
        int startX = rand.nextInt(progress.length);
        int startY = rand.nextInt(progress.length);
        System.out.println(startX + " " + startY + ":" + map[startY][startX]);
        while(map[startY][startX] == 'X'){
            startX = rand.nextInt(progress.length);
            startY = rand.nextInt(progress.length);
            System.out.println("Retry " + startX + " " + startY + ":" + map[startY][startX]);
        }
        
        int maxDist = difficulty/3;
        if(maxDist < 1){
            maxDist = 1;
        }
        System.out.println("maxDist " + maxDist);
        int distX = rand.nextInt();
        System.out.println("distX " + distX);
        int distY = rand.nextInt();
        System.out.println("distY " + distY);
        
        /*if(maxX > (map.length - 1)){
            maxX = map.length - 1;
        } else if(minX < 0){
            minX = 0;
        }
        if(maxY > (map[x].length - 1)){
            maxY = map[x].length - 1;
        } else if(minY < 0){
            minY = 0;
        }*/
    }
    
    public static boolean checkForMine(int x, int y){
        char selectedTile = map[x][y];
        
        if(selectedTile == 'X'){
            System.out.println("\n\nMINE TRIGGERED");
            System.out.println("GAME OVER");
            return false;
        }else{
            progress[x][y] = selectedTile;
            return true;
        }
    }
    
    public static boolean arraysAreEqual(char[][] arrayOne, char[][] arrayTwo){
        //Check if arrays are same length
        if(arrayOne.length != arrayTwo.length || arrayOne[0].length != arrayTwo[0].length){
            System.out.println("ERROR: Array lengths are not equal.");
            return false;
        }
        
        //Run test
        for(int row = 0; row < arrayOne.length; row++){
            for(int col = 0; col < arrayOne[row].length; col++){
                if(arrayOne[row][col] != arrayTwo[row][col]){
                    return false;
                }
            }
        }
        
        System.out.println("\nCONGRATS! YOU WON.");
        return true;
    }
    
    
    //Initial Map Generation Functions
    
    public static void generateMines(Random rand, int difficulty) {
        //mineCooridinates = new int[numMines][2];
        //Decide number of mines to place
        int numMines = (int)(difficulty / 2);
        if((difficulty % 2) == 1){
            numMines++;
        }
        
        //Place mines
        for (int index = 0 ; index < numMines; ++index)
        {
            //Random number between 0 and numMines.length
            int x = rand.nextInt(map.length);
            int y = rand.nextInt(map[0].length);
            //System.out.println("(" + x + "," + y + ")");
            
            map[x][y] = 'X';
        }
    }
    
    public static void generateNumbers(){
        for(int row = 0; row < map.length; row++){
            for(int col = 0; col < map[row].length; col++){
                if(map[row][col] == 'X'){
                    updateNumbers(row, col);
                }
            }
        }
    }
    
    public static void updateNumbers(int x, int y){
        int maxX = x + 1; 
        int maxY = y + 1;
        int minX = x - 1;
        int minY = y - 1;
        //System.out.println("Max X " + maxX + " Max Y " + maxY + " Min X " + minX + " MinY " + minY);
        
        //Check to see if max and min are within boundaries of the map
        if(maxX > (map.length - 1)){
            maxX = map.length - 1;
        } else if(minX < 0){
            minX = 0;
        }
        if(maxY > (map[x].length - 1)){
            maxY = map[x].length - 1;
        } else if(minY < 0){
            minY = 0;
        }
        //System.out.println("Max X " + maxX + " Max Y " + maxY + " Min X " + minX + " MinY " + minY + "\n");
        
        //Update numbers near indicated mine
        for(int i = minX; i <= maxX; i++){
            for(int k = minY; k <= maxY; k++){
                if(map[i][k] != 'X'){
                    int temp = Character.getNumericValue(map[i][k]);
                    temp++;
                    map[i][k] = (char) (temp + 48);
                }
            }
        }
    }
    
    
    //General Array Functions
    
    public static void fillArray(char[][] array, char fill){
        for(int row = 0; row < array.length; row++){
            for(int col = 0; col < array[row].length; col++){
                array[row][col] = fill;
            }
        }
    }
    
    public static void printArray(char[][] array){
        //Print top formatting
        System.out.print(" ");
        System.out.printf("%2s", " ");
        for(int i = 0; i < array[0].length; i++){
            System.out.printf("%2d", i);
        }
        System.out.println();
        //Print position numbers
        System.out.printf("%2s", " ");
        System.out.print("|");
        for(int i = 0; i < array[0].length; i++){
            System.out.printf("%2s", "-");
        }
        System.out.printf("%2s", "|");
        System.out.println();
        
        //Print array
        for(int row = 0; row < array.length; row++){
            for(int col = 0; col < array[row].length; col++){
                if(col == 0){
                    System.out.print(row);
                    System.out.printf("%2s", "|");
                }
                
                System.out.printf("%2c", array[row][col]);
                
                if (col == array[row].length - 1) {
                    System.out.printf("%2s", "|");
                    System.out.println();
                }
            }
        }
        
        //Print bottom formating
        System.out.printf("%2s", " ");
        System.out.print("|");
        for(int i = 0; i < array[0].length; i++){
            System.out.printf("%2s", "-");
        }
        System.out.printf("%2s", "|");
        System.out.println();
    }
}