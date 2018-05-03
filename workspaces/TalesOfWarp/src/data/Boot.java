package data;

import data.grid.Grid;
import helpers.Clock;
import static helpers.WindowHelper.*;

public class Boot {

    public Boot() {
        initWindow();

        int tileWidth = 64, tileHeight=64;

        //Must be a array int[maxR-minR][maxQ-minQ]
        Integer[][] mapData = {
                {0,1,1,1,1,1,1,1,1,2},
                {3,4,4,4,4,4,4,4,4,5},
                {3,4,4,4,4,4,4,4,4,5},
                {3,4,4,4,4,4,4,4,4,5},
                {3,4,4,4,4,4,4,4,4,5},
                {3,4,4,4,4,4,4,4,4,5},
                {3,4,4,4,4,4,4,4,4,5},
                {3,4,4,4,4,4,4,4,4,5},
                {3,4,4,4,4,4,4,4,4,5},
                {6,7,7,7,7,7,7,7,7,8}
        };

        Grid grid = new Grid(tileWidth, tileHeight);
        grid.load(10, 10, mapData);/**/
        grid.setActivateTile(1,1,true);
        grid.setPlayerPos(1, 1);

        Player player = new Player(grid);
        player.setPlayerPos(1,1);

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
