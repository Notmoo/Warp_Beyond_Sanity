package data;

import data.grid.Grid;
import helpers.Clock;
import static helpers.WindowHelper.*;

public class Boot {

    public Boot() {
        initWindow();

        int tileWidth = 128, tileHeight=128;

        //Must be a array int[maxR-minR][maxQ-minQ]
        Integer[][] mapData = {
                {4,4,4,4,4,4},
                {4,4,4,4,4,4},
                {4,4,4,4,4,4},
                {4,4,4,4,4,4},
                {4,4,4,4,4,4},
                {4,4,4,4,4,4}
        };

        Grid grid = new Grid(tileWidth, tileHeight, 0, 0);
        grid.load(6, 6, mapData);/**/
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
