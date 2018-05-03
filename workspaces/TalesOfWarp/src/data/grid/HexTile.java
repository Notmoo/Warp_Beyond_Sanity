package data.grid;

public class HexTile extends Tile {
    public HexTile(float x, float y, float size, TileType type) {
        super(x, y, (float) (Math.sqrt(3)*size), size*2, type);
    }
}
