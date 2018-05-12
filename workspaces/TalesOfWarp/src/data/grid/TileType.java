package data.grid;

import java.util.EnumSet;

//TODO ça n'a pas besoin d'être un enum, ça peut être un fichier de config
public enum TileType {

    None("", "", -1)
    ,TopLeft("grass_tl", "old_scroll", 0)
    ,Top("grass_t", "old_scroll", 1)
    ,TopRight("grass_tr", "old_scroll", 2)
    ,Left("grass_l", "old_scroll", 3)
    ,Middle("grass_m", "old_scroll", 4)
    ,Right("grass_r", "old_scroll", 5)
    ,BottomLeft("grass_bl", "old_scroll", 6)
    ,Bottom("grass_b", "old_scroll", 7)
    ,BottomRight("grass_br", "old_scroll", 8)
    ,SelectedTile("selected_tile", "selected_tile", 9)
    ,Player("character", "character", 10)
    , GRID_BORDER("grid_border_6x6_64x64", "grid_border_6x6_64x64", 11);
    ;

    private final String textureName, foggyTextureName;
    private final int tileTypeId;

    TileType(String textureName, String foggyTextureName, int tileTypeId){
        this.textureName = textureName;
        this.foggyTextureName = foggyTextureName;
        this.tileTypeId = tileTypeId;
    }

    public String getTextureName() {
        return textureName;
    }

    public int getId() {
        return tileTypeId;
    }

    public static TileType getTypeById(int id){
        for(TileType type : EnumSet.allOf(TileType.class)){
            if(type.tileTypeId == id)
                return type;
        }

        return None;
    }

    public String getFoggyTextureName() {
        return foggyTextureName;
    }
}
