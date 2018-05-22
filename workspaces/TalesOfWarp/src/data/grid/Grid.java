package data.grid;

import data.IScreen;
import data.Tuple;
import helpers.CoordinateHelperFactory;
import helpers.DrawUtil;
import helpers.ICoordHelper;
import org.newdawn.slick.opengl.Texture;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import static helpers.TexUtil.QuickLoadPngTexture;

//TODO ajoute un boolean 'isLoaded' pour éviter les problèmes d'accès avant que load(...) soit appelée
public class Grid implements IScreen{

    private boolean isShown;
    private float gridPixelPosX, gridPixelPosY;

    private Texture gridBorderTex;

    private Tile[][] map;
    private Collection<Tuple<Tile, Tuple<Integer, Integer>>> elementsOnTile;

    private int nbDisplayedRows, nbDisplayedCols;
    private int firstDiplayedRow, firstDiplayedCol;

    private int nbRows, nbCols;
    private float tileWidth, tileHeight;
    private ICoordHelper coordHelper;

    public Grid(float tileWidth, float tileHeight, float gridPixelPosX, float gridPixelPosY) {
        this.isShown = false;

        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.elementsOnTile = new ArrayList<>();

        float gridBorderThickness = 2;
        this.gridBorderTex = QuickLoadPngTexture("grid_border_6x6_64x64");

        this.gridPixelPosX = gridPixelPosX;
        this.gridPixelPosY = gridPixelPosY;
        this.coordHelper = CoordinateHelperFactory.makeGridCoordinateHelper();
        this.coordHelper.setPixelOffset(gridPixelPosX+ gridBorderThickness, gridPixelPosY+ gridBorderThickness);
    }

    public void load(int nbRows, int nbCols, Integer[][] gridData){
        load(nbRows, nbCols, nbRows, nbCols, gridData);
    }

    public void load(int nbRows, int nbCols, int nbDisplayedRows, int nbDisplayedCols, Integer[][] gridData){
        this.map = new Tile[nbRows][nbCols];
        this.nbRows = nbRows;
        this.nbCols = nbCols;
        this.nbDisplayedRows = nbDisplayedRows;
        this.nbDisplayedCols = nbDisplayedCols;
        this.firstDiplayedCol = 0;
        this.firstDiplayedRow = 0;

        for(int i = 0; i< nbCols; i++){
            for(int j = 0; j< nbRows; j++){
                Integer data = gridData[i][j];
                if(data != null)
                    setTile(i, j, TileType.getTypeById(data));
            }
        }
    }

    public void clear(){
        elementsOnTile.clear();
    }

    @Override
    public boolean isShown() {
        return isShown;
    }

    @Override
    public void show() {
        this.isShown = true;
    }

    @Override
    public void hide() {
        this.isShown = false;
    }

    public void updateAndDraw(){
        if(!isShown)
            return;

        int currentDisplayedRow = 0, currentDisplayedCol = 0;

        int minCol = Math.max(firstDiplayedCol, 0);
        int minRow = Math.max(firstDiplayedRow, 0);
        int maxCol = Math.min(firstDiplayedCol+nbDisplayedCols, nbCols);
        int maxRow = Math.min(firstDiplayedRow+nbDisplayedRows, nbRows);

        for(int i = minCol; i< maxCol; i++){
            for(int j = minRow; j< maxRow; j++){
                Tile tile = getTile(j, i);
                if(tile!=null) {
                    tile.update();
                    if(tile.isActivated){
                        selectIfNotActivated(j, i-1);
                        selectIfNotActivated(j, i+1);
                        selectIfNotActivated(j-1, i);
                        selectIfNotActivated(j+1, i);
                    }

                    //On utilise getPixelPos() sans les coords de la zone à afficher car on l'a déjà prise en compte
                    Tuple<Float, Float> pixelPos = coordHelper.getPixelPos(currentDisplayedRow, currentDisplayedCol, tileWidth, tileHeight);
                    tile.setX(pixelPos.x);
                    tile.setY(pixelPos.y);
                    tile.draw();
                }
                currentDisplayedRow++;
            }
            currentDisplayedRow = 0;
            currentDisplayedCol++;
        }
        elementsOnTile.stream().filter(element->isInDisplayedArea(element.y.x, element.y.y)).forEach(element->{
            int row = element.y.x-firstDiplayedRow;
            int col = element.y.y-firstDiplayedCol;

            //On utilise getPixelPos() sans les coords de la zone à afficher car on l'a déjà prise en compte
            Tuple<Float, Float> pixelPos = coordHelper.getPixelPos(row, col, tileWidth, tileHeight);
            if(pixelPos!=null) {
                element.x.setX(pixelPos.x);
                element.x.setY(pixelPos.y);
                element.x.draw();
            }
        });

        DrawUtil.drawQuadTexUsingTexDimensions(gridBorderTex, gridPixelPosX, gridPixelPosY);
    }

    private boolean isInDisplayedArea(Integer row, Integer col) {
        return col>=firstDiplayedCol && col<firstDiplayedCol+nbDisplayedCols
                && row>=firstDiplayedRow && row<firstDiplayedRow+nbDisplayedRows;
    }

    private void selectIfNotActivated(int row, int col){
         Tile tile = getTile(row, col);//Renvoie null si les coords sont en dehors de la map, ou si la case vaut null de base
         if(tile != null && !tile.isActivated){
             elementsOnTile.add(new Tuple<>(generateTile(row, col, TileType.SelectedTile), new Tuple<>(row, col)));
         }
    }

    //TODO Limiter les mvt du joueur à la zone affichée?
    public boolean isPlayerMovePossible(Tuple<Integer, Integer> pos){
        if(!areCoordInGrid(pos.x, pos.y))
            return false;
        if(isTileActivated(pos))
            return true;
        if(isTileActivated(pos.x-1, pos.y))
            return true;
        if(isTileActivated(pos.x+1, pos.y))
            return true;
        if(isTileActivated(pos.x, pos.y-1))
            return true;
        return isTileActivated(pos.x, pos.y + 1);
    }

    public void setTile(int row, int col, TileType newType){
        setTileInMatrix(map, row, col, newType);
    }

    private void setTileInMatrix(Tile[][] matrix, int row, int col, TileType type){
        if(areCoordInGrid(row,col)) {
            matrix[col][row] = generateTile(row, col, type);
        }
    }

    private Tile generateTile(int row, int col, TileType type){
        if (type != null) {
            Tuple<Float, Float> pos = getPixelPos(row, col);
            if(pos!=null)
                return new Tile(pos.x, pos.y, tileWidth, tileHeight, type);
        }
        return null;
    }

    public Tile getTile(int row, int col){
        return getTileInMatrix(map, row, col);
    }

    private Tile getTileInMatrix(Tile[][] m, int row, int col){
        if(areCoordInGrid(row,col))
            return m[col][row];
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
        return this.elementsOnTile.stream().anyMatch(element->element.y.x == col && element.y.y == row);
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

    public Tuple<Integer, Integer> getGridPos(float x, float y) {
        return coordHelper.getGridPos(x, y, tileWidth, tileHeight, firstDiplayedRow, firstDiplayedCol);
    }

    public Tuple<Float, Float> getPixelPos(int row, int column) {
        return coordHelper.getPixelPos(row, column, tileWidth, tileHeight, firstDiplayedRow, firstDiplayedCol);
    }
}
