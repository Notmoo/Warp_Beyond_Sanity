package data.grid;

import data.Tuple;
import helpers.CoordinateHelperFactory;
import helpers.ICoordHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

//TODO ajoute un boolean 'isLoaded' pour éviter les problèmes d'accès avant que load(...) soit appelée
public class Grid {

    private Tile[][] map;
    private Collection<Tuple<Tile, Tuple<Integer, Integer>>> elementsOnTile;

    private int nbRows, nbCols;
    private float tileWidth, tileHeight;
    private ICoordHelper coordHelper;

    public Grid(float tileWidth, float tileHeight) {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.coordHelper = CoordinateHelperFactory.makeGridCoordinateHelper();
        this.elementsOnTile = new ArrayList<>();
    }

    public void load(int nbRows, int nbCols, Integer[][] gridData){
        this.map = new Tile[nbRows][nbCols];
        this.nbRows = nbRows;
        this.nbCols = nbCols;

        for(int i = 0; i< nbCols; i++){
            for(int j = 0; j< nbRows; j++){
                Integer data = gridData[j][i];
                if(data != null)
                    setTile(i, j, TileType.getTypeById(data));
            }
        }
    }

    public void clear(){
        elementsOnTile.clear();
    }

    public void updateAndDraw(){
        for(int i = 0; i< nbCols; i++){
            for(int j = 0; j< nbRows; j++){
                Tile tile = getTile(j, i);
                if(tile!=null) {
                    tile.update();
                    if(tile.isActivated){
                        selectIfNotActivated(j, i-1);
                        selectIfNotActivated(j, i+1);
                        selectIfNotActivated(j-1, i);
                        selectIfNotActivated(j+1, i);
                    }
                    tile.draw();
                }
            }
        }
        elementsOnTile.forEach(element->element.x.draw());
    }

    private void selectIfNotActivated(int row, int col){
         Tile tile = getTile(row, col);//Renvoie null si les coords sont en dehors de la map, ou si la case vaut null de base
         if(tile != null && !tile.isActivated){
             elementsOnTile.add(new Tuple<>(generateTile(row, col, TileType.SelectedTile), new Tuple<>(row, col)));
         }
    }

    public boolean isPlayerMovePossible(Tuple<Integer, Integer> pos){
        if(isTileActivated(pos))
            return true;
        if(isTileActivated(pos.x-1, pos.y))
            return true;
        if(isTileActivated(pos.x+1, pos.y))
            return true;
        if(isTileActivated(pos.x, pos.y-1))
            return true;
        if(isTileActivated(pos.x, pos.y+1))
            return true;
        return false;
    }

    public void setTile(int row, int col, TileType newType){
        setTileInMatrix(map, row, col, newType);
    }

    private void setTileInMatrix(Tile[][] matrix, int row, int col, TileType type){
        if(areCoordInGrid(row,col)) {
            matrix[row][col] = generateTile(row, col, type);
        }
    }

    private Tile generateTile(int row, int col, TileType type){
        if (type != null) {
            Tuple<Float, Float> pos = getPixelPos(row, col);
            return new Tile(pos.x, pos.y, tileWidth, tileHeight, type);
        }
        return null;
    }

    public Tile getTile(int row, int col){
        return getTileInMatrix(map, row, col);
    }

    private Tile getTileInMatrix(Tile[][] m, int row, int col){
        if(areCoordInGrid(row,col))
            return m[row][col];
        else
            return null;
    }

    public boolean isTileActivated(int row, int col){
        WeakReference<Tile> tile = new WeakReference<>(getTile(row, col));
        boolean result = false;
        try{
            result = Objects.requireNonNull(tile.get()).isActivated();
        }catch(NullPointerException e){
            //Do Nothing
        }
        return result;
    }

    public boolean isTileActivated(Tuple<Integer, Integer> pos){
        return isTileActivated(pos.x, pos.y);
    }

    public boolean isTileSelected(int row, int col){
        return this.elementsOnTile.stream().anyMatch(element->element.y.x == row && element.y.y == col);
    }

    public boolean isTileSelected(Tuple<Integer, Integer> pos){
        return isTileSelected(pos.x, pos.y);
    }

    public boolean areCoordInGrid(int row, int col){
        return row>=0 && col>=0 && row<nbRows && col<nbCols;
    }

    public void setActivateTile(int row, int col, boolean activate) {
        Tile tile = getTile(row, col);
        if(tile!=null)
            tile.setActivated(activate);
    }

    public void setActivateTile(Tuple<Integer, Integer> pos, boolean activate) {
        setActivateTile(pos.x, pos.y, activate);
    }

    public void setPlayerPos(int row, int col){
        setPlayerPos(new Tuple<>(row, col));
    }

    public void setPlayerPos(Tuple<Integer, Integer> pos){
        elementsOnTile.add(new Tuple<>(generateTile(pos.x, pos.y, TileType.Player), pos));
    }

    public Tuple<Integer, Integer> getGridPos(float x, float y) {
        return coordHelper.getGridPos(x, y, tileWidth, tileHeight);
    }

    public Tuple<Float, Float> getPixelPos(int row, int column) {
        return coordHelper.getPixelPos(row, column, tileWidth, tileHeight);
    }
}
