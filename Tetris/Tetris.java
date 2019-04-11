/**
 * Tetris class to be completed for Tetris project
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
public class Tetris implements ArrowListener{
    public static void main(String[] args){
        Tetris tetris = new Tetris();
        tetris.play();
    }
    private BoundedGrid<Block> grid;
    private BlockDisplay display;
    private Tetrad activeTetrad;
    private int score = 1;
    private int record;
    private Scanner scan;
    private BufferedWriter bw = null;
    public int ReadRecord(){
        try{
            scan = new Scanner(new File("Record.txt"));
        } catch(Exception e){

        }
        record = scan.nextInt();
        return record;
    }

    public void WriteRecord(){
        try {
            bw = new BufferedWriter(new FileWriter(new File("Record.txt")));
            bw.write(String.valueOf(record));
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Tetris(){
        grid = new BoundedGrid<Block>(20, 10);
        display = new BlockDisplay(grid);
        display.setTitle("Tetris");
        activeTetrad = new Tetrad(grid);
        display.setArrowListener(this);
    }

    public void upPressed(){
        activeTetrad.rotate();
        display.showBlocks();
    }

    public void downPressed(){
        activeTetrad.translate(1, 0);
        display.showBlocks();
    }

    public void leftPressed(){
        activeTetrad.translate(0, -1);
        display.showBlocks();
    }

    public void rightPressed(){
        activeTetrad.translate(0, 1);
        display.showBlocks();
    }

    public void spacePressed(){
        while(activeTetrad.translate(1, 0));
        display.showBlocks();
    }

    public void play(){
        while (true)
        {
            double speed = (double) (1000 - (Math.pow(score, 1.7)));
            try { Thread.sleep((long)speed); } catch(Exception e) {}
            if(!activeTetrad.translate(1, 0)){
                clearCompletedRows();
                if(!topRowsEmpty()){
                    activeTetrad = null;
                    System.out.println("Your score was: " + score);
                    if(score > record){
                        record = score;
                        WriteRecord();
                    }
                    System.out.println("The record is: " + record);
                    break;
                }
                activeTetrad = new Tetrad(grid);
                display.setTitle("Score: " + score + "\tRecord: " + ReadRecord());
            }
            //Insert Exercise 3.3 code here

            display.showBlocks();
        }
    }

    private boolean isCompletedRow(int row){
        boolean output = true;
        for(int i = 0; i < grid.getNumCols(); i++){
            Location loc = new Location(row, i);
            if(grid.get(loc) == null)
                output = false;
        }

        return output;
    }

    private boolean clearRow(int row){
        ArrayList<Location> occupiedLocs = grid.getOccupiedLocations();
        if(isCompletedRow(row)){
            score++;
            
            for(int i = 0; i < grid.getNumCols(); i++){
                Location loc = new Location(row, i);
                grid.remove(loc);
            }
            for(int i = row - 1; i >= 0; i--){
                for(int j = 0; j < grid.getNumCols(); j++){
                    Block block = grid.get(new Location(i, j));
                    if(block != null)
                        block.moveTo(new Location(i + 1, j));
                }
            }
            return true;
            /*
            for(int i = 0; i < occupiedLocs.size(); i++){
            Block block = grid.get(occupiedLocs.get(i));
            block.moveTo(new Location(block.getLocation().getRow() + 1, block.getLocation().getCol()));
            } */
        } else {
            return false;
        }
    }

    //postcondition: All completed rows have been cleared.
    private void clearCompletedRows(){
        for(int i = grid.getNumRows() - 1; i >= 0; i--){
            if(clearRow(i))
                i++;
        }
    }

    //returns true if top two rows of the grid are empty (no blocks), false otherwise
    private boolean topRowsEmpty(){
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < grid.getNumCols(); j++){
                Block block = grid.get(new Location(i, j));
                if(block != null){
                    return false;
                }
            }
        }
        return true;
    }

}