package data.grid;

import data.IDrawable;
import org.newdawn.slick.opengl.Texture;

import static helpers.DrawUtil.drawDrawable;
import static helpers.TexUtil.*;

public class Tile implements IDrawable{

    protected float x, y, width, height;
    protected boolean isActivated;
    protected Texture normalTexture, foggyTexture, contentTexture;
    protected TileType type;
    protected TileContent content;

    public Tile(float x, float y, float width, float height, TileType type, TileContent content) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.normalTexture = QuickLoadPngTexture(type.getTextureName());
        this.foggyTexture = QuickLoadPngTexture(type.getFoggyTextureName());
        this.type = type;
        this.content = content;
        this.isActivated = false;
        reloadContentTexture();
    }

    private void reloadContentTexture(){
        this.contentTexture = content==TileContent.NONE ? null : QuickLoadPngTexture(content.getTextureName());
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public Texture[] getTextures() {
        if(isActivated){
            return new Texture[]{normalTexture, contentTexture};
        }else{
            return new Texture[]{foggyTexture};
        }
    }

    public void setTexture(Texture texture) {
        this.normalTexture = texture;
    }

    public void setFoggyTexture(Texture foggyTexture) {
        this.foggyTexture = foggyTexture;
    }

    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    public void update(){

    }

    public void draw(){
        drawDrawable(this);
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public TileContent getContent() {
        return content;
    }

    public void setContent(TileContent content) {
        if(content!=this.content){
            this.content = content;
            reloadContentTexture();
        }
    }
}
