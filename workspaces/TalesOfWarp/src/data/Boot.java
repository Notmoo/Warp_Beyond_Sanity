package data;

import data.grid.Grid;
import helpers.Clock;
import helpers.DrawUtil;
import org.newdawn.slick.opengl.Texture;

import java.util.Random;

import static helpers.TexUtil.QuickLoadPngTexture;
import static helpers.WindowHelper.*;

public class Boot {

    public Boot() {
        initWindow();

        int tileWidth = 64, tileHeight=64;

        Random rand = new Random(System.nanoTime());

        int startingRow = Math.abs(rand.nextInt()%6);
        int startingCol = Math.abs(rand.nextInt()%6);

        Integer[][][] mapData = {
                {{4,0},{4,1},{4,2},{4,3},{4,4},{4,5}},
                {{4,-1},{4,-1},{4,-1},{4,-1},{4,-1},{4,-1}},
                {{4,-1},{4,-1},{4,-1},{4,-1},{4,-1},{4,-1}},
                {{4,-1},{4,-1},{4,-1},{4,-1},{4,-1},{4,-1}},
                {{4,-1},{4,-1},{4,-1},{4,-1},{4,-1},{4,-1}},
                {{4,-1},{4,-1},{4,-1},{4,-1},{4,-1},{4,-1}}
        };

        IScreen background = new IScreen() {

            private boolean isShown = true;
            private Texture tex = QuickLoadPngTexture("background_1280x768");

            @Override
            public boolean isShown() {
                return isShown;
            }

            @Override
            public void show() {
                isShown = true;
            }

            @Override
            public void hide() {
                isShown = false;
            }

            @Override
            public void updateAndDraw() {
                if (!isShown)
                    return;

                DrawUtil.drawQuadTexUsingTexDimensions(tex, 0,0);
            }
        };

        Grid grid = new Grid(tileWidth, tileHeight, 400, 200);
        grid.load(6, 6, mapData);/**/
        
        grid.setActivateTile(startingRow, startingCol, true);

        grid.show();

        Player player = new Player(grid);

        while(!isCloseRequested()){
            Clock.update();
            background.updateAndDraw();
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
