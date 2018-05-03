package data.grid;


import data.Hex;
import data.Tuple;
import helpers.CoordinateHelperFactory;
import helpers.IHexCoordHelper;

//TODO ajoute un boolean 'isLoaded' pour éviter les problèmes d'accès avant que load(...) soit appelée
public class HexGrid {

    private Tile[][] map;
    private int maxQ, maxR, minQ, minR;
    private float tileSize;
    private IHexCoordHelper coordHelper;

    public HexGrid(float tileSize) {
        this.tileSize = tileSize;
        this.coordHelper = CoordinateHelperFactory.makeHexGridAxialCoordinateHelper();
    }

    public void load(int maxR, int maxQ, int minR, int minQ, Integer[][] gridData){
        this.maxQ = maxQ;
        this.maxR = maxR;
        this.minQ = minQ;
        this.minR = minR;

        this.map = new HexTile[this.maxQ-this.minQ][this.maxR-this.minR];

        for(int i = this.minQ; i< this.maxQ; i++){
            for(int j = this.minR; j< this.maxR; j++){
                Integer data = gridData[j-this.minR][i-this.minQ];
                if(data != null)
                    setTile(i, j, TileType.getTypeById(data));
            }
        }
    }

    public void draw(){
        for(Tile[] column : map){
            for(Tile tile : column){
                if(tile!=null) {
                    tile.update();
                    tile.draw();
                }
            }
        }
    }

    public void setTile(int q, int r, TileType newType){
        if(q>=minQ && q< maxQ && r>=minR && r< maxR && newType!=null){
            Tuple<Float, Float> pos = getPixelPos(q, r);
            map[r-minR][q-minQ] = new HexTile(pos.x, pos.y, tileSize, newType);
        }
    }

    public Tile getTile(int r, int q){
        if(r>= minR && r<maxR && q>=minQ && q<maxQ)
            return map[r][(int) (q-(-Math.floor(r/2)))];
        else
            return null;
    }

    public void setActivateTile(float r, float q, boolean activate) {
        Tile tile = getTile((int)r, (int)q);
        if(tile!=null)
            tile.setActivated(activate);
    }

    public void setActivateTile(Hex hex, boolean activate){
        setActivateTile(hex.r, hex.q, activate);
    }

    public Hex getGridPos(float x, float y) {
        return coordHelper.getGridPos(x, y, tileSize);
    }

    public Tuple<Float, Float> getPixelPos(int row, int column) {
        return coordHelper.getPixelPos(row, column, tileSize);
    }
}
