package data.grid;

import java.util.EnumSet;

public enum TileContent {
    BOSS(0, "boss"),
    MONSTER(1, "monster"),
    CHEST(2, "chest"),
    SHRINE(3, "shrine"),
    TOWN(4, "town"),
    SIDE_ZONE(5, "side_zone"),
    NONE(-1, "");

    private final int id;
    private final String textureName;

    TileContent(int id, String textureName){
        this.textureName = textureName;
        this.id = id;
    }

    public String getTextureName() {
        return textureName;
    }

    public int getId() {
        return id;
    }

    public static TileContent getContentById(int id){
        for(TileContent type : EnumSet.allOf(TileContent.class)){
            if(type.id == id)
                return type;
        }

        return NONE;
    }
}
