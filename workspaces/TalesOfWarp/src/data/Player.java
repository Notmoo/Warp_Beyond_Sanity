package data;

import data.grid.Grid;
import data.io.IButtonInputActiveManager;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.List;

import static helpers.WindowHelper.HEIGHT;

public class Player {
    private Grid grid;

    private List<IButtonInputActiveManager> bics;

    public Player(Grid grid) {
        this.grid = grid;
        bics = new ArrayList<>();
        bics.add(new IButtonInputActiveManager() {
            private boolean value = false;
            private boolean previousValue = false;

            @Override
            public void update() {
                previousValue = value;
                value = Mouse.isButtonDown(0); //Clic gauche
                if(isActivated())
                    tryActivateTile();
            }

            @Override
            public boolean isActivated() {
                return value && !previousValue;
            }
        });
    }

    public void tryActivateTile(){
        float x = Mouse.getX(), y = HEIGHT - Mouse.getY();
        Tuple<Integer, Integer> pos = grid.getGridPos(x, y);
        boolean isMovePossible = grid.isPlayerMovePossible(pos);
        if(isMovePossible)
            grid.setActivateTile(pos, true);/**/
    }

    public void readInput(){
        bics.forEach(IButtonInputActiveManager::update);
    }

    public void updateAndDraw(){}
}
