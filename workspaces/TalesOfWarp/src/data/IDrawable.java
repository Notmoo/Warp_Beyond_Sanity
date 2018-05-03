package data;

import org.newdawn.slick.opengl.Texture;

public interface IDrawable {
    float getWidth();
    float getHeight();
    float getX();
    float getY();
    Texture getTexture();
    void update();
    void draw();
}
