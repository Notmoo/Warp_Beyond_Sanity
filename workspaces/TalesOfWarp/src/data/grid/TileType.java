package data.grid;

import java.util.EnumSet;

//TODO ça n'a pas besoin d'être un enum, ça peut être un fichier de config
public enum TileType {

    None("", "", -1)
    ,TopLeft("grass_tl", "grass_tl_fog", 0)
    ,Top("grass_t", "grass_t_fog", 1)
    ,TopRight("grass_tr", "grass_tr_fog", 2)
    ,Left("grass_l", "grass_l_fog", 3)
    ,Middle("grass_m", "grass_m_fog", 4)
    ,Right("grass_r", "grass_r_fog", 5)
    ,BottomLeft("grass_bl", "grass_bl_fog", 6)
    ,Bottom("grass_b", "grass_b_fog", 7)
    ,BottomRight("grass_br", "grass_br_fog", 8)
    ,SelectedTile("selected_tile", "selected_tile", 9)
    ,Player("character", "character", 10);
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
