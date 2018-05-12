package data;

import data.grid.Grid;
import helpers.Clock;

import java.util.Random;

import static helpers.WindowHelper.*;

public class Boot {

    public Boot() {
        initWindow();

        int tileWidth = 64, tileHeight=64;

        Random rand = new Random(System.nanoTime());

        int startingRow = Math.abs(rand.nextInt()%6);
        int startingCol = Math.abs(rand.nextInt()%6);

        //Must be a array int[maxR-minR][maxQ-minQ]
        Integer[][] mapData = {
                {4,4,4,4,4,4},
                {4,4,4,4,4,4},
                {4,4,4,4,4,4},
                {4,4,4,4,4,4},
                {4,4,4,4,4,4},
                {4,4,4,4,4,4}
        };

        Grid grid = new Grid(tileWidth, tileHeight, 400, 200);
        grid.load(6, 6, mapData);/**/
        
        grid.setActivateTile(startingRow, startingCol, true);

        Player player = new Player(grid);

        while(!isCloseRequested()){
            Clock.update();
            grid.clear();
            player.readInput();
            player.updateAndDraw();
            grid.updateAndDraw();
            updateWindow();
        }

        destroyWindow();
    }

    public static void main (String[] args){
        new Boot();
    }
}
