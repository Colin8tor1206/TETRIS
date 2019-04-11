
/**
 * Tetrad class to be completed for Tetris project
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.awt.*;

public class Tetrad{
    private Block[] blocks;

    public Tetrad(BoundedGrid<Block> grid){

        blocks = new Block[4];
        for(int i = 0; i < blocks.length; i++){
            blocks[i] = new Block();
        }
        Color color = Color.BLACK;
        Location[] locs = new Location[4];
        int shape = (int) (Math.random() * 7); 

        switch (shape) {
            case 0: // I SHAPE
                color = color.RED;
                locs[1] = new Location(0, 3);
                locs[0] = new Location(0, 4);
                locs[2] = new Location(0, 5);
                locs[3] = new Location(0, 6);
                break;
            case 1: // T SHAPE
                color = color.ORANGE;
                locs[1] = new Location(0, 3);
                locs[0] = new Location(0, 4);
                locs[2] = new Location(1, 4);
                locs[3] = new Location(0, 5);
                break;
            case 2: // O SHAPE
                color = color.YELLOW;
                locs[0] = new Location(0, 4);
                locs[1] = new Location(1, 4);
                locs[2] = new Location(0, 5);
                locs[3] = new Location(1, 5);
                break;
            case 3: // L SHAPE
                color = color.GREEN;
                locs[2] = new Location(0, 4);
                locs[1] = new Location(1, 4);
                locs[0] = new Location(0, 5);
                locs[3] = new Location(0, 6);
                break;
            case 4: // J SHAPE
                color = color.BLUE;
                locs[2] = new Location(0, 3);
                locs[0] = new Location(0, 4);
                locs[1] = new Location(0, 5);
                locs[3] = new Location(1, 5);
                break;
            case 5: // S SHAPE
                color = color.MAGENTA;
                locs[2] = new Location(1, 4);
                locs[1] = new Location(0, 5);
                locs[0] = new Location(1, 5);
                locs[3] = new Location(0, 6);
                break;
            case 6: // Z SHAPE
                color = color.PINK;
                locs[1] = new Location(0, 3);
                locs[2] = new Location(0, 4);
                locs[0] = new Location(1, 4);
                locs[3] = new Location(1, 5);
                break;
            default:
                System.out.println("COLOR ERROR");
                break; 
        }
        for(int i = 0; i < blocks.length; i++){
            blocks[i].setColor(color);
        }
        addToLocations(grid, locs);
    }

    private void addToLocations(BoundedGrid<Block> grid, Location[] locs){
        for(int i = 0 ; i < locs.length; i++){
            blocks[i].putSelfInGrid(grid, locs[i]);
        }
    }

    private Location[] removeBlocks(){
        Location[] locs = new Location[4];
        for(int i = 0; i < blocks.length; i++){
            locs[i] = blocks[i].getLocation();
            blocks[i].removeSelfFromGrid();
        }
        return locs;
    }

    private boolean areEmpty(BoundedGrid<Block> grid, Location[] locs){
        boolean output = true;
        for(int i = 0; i < locs.length; i++){
            if(!grid.isValid(locs[i]) || grid.get(locs[i]) != null){
                output = false; 
            }
        }
        return output;
    }

    public boolean translate(int deltaRow, int deltaCol){

        BoundedGrid<Block> grid = blocks[0].getGrid();
        Location[] locs = removeBlocks();
        Location[] newLocs = new Location[locs.length];
        for(int i = 0; i < locs.length; i++){
           newLocs[i] = new Location(locs[i].getRow() + deltaRow, locs[i].getCol() + deltaCol);
        }
        if(areEmpty(grid, newLocs)){
            addToLocations(grid, newLocs);
            return true;
        } else {
            addToLocations(grid, locs);
            return false;
        }
        
    }

    public boolean rotate(){
        BoundedGrid<Block> grid = blocks[0].getGrid();
        Location[] locs = removeBlocks();
        Location[] newLocs = new Location[locs.length];
        newLocs[0] = locs[0];
        for(int i = 1; i < locs.length; i++){
           newLocs[i] = new Location(locs[0].getRow() - locs[0].getCol() + locs[i].getCol(), locs[0].getRow() + locs[0].getCol() - locs[i].getRow());
        }
        if(areEmpty(grid, newLocs)){
            addToLocations(grid, newLocs);
            return true;
        } else {
            addToLocations(grid, locs);
            return false;
        }
        
    }
}