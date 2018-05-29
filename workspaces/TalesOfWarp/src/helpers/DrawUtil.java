package helpers;

import data.IDrawable;
import org.newdawn.slick.opengl.Texture;

import static org.lwjgl.opengl.GL11.*;

public class DrawUtil {

    public static void drawQuadTexUsingTexDimensions(Texture tex, float x, float y){
        tex.bind();
        glTranslatef(x, y, 0);

        glBegin(GL_QUADS);

        glTexCoord2f(0, 0);
        glVertex2f(0, 0);

        glTexCoord2f(tex.getWidth(), 0);
        glVertex2f(tex.getImageWidth(), 0);

        glTexCoord2f(tex.getWidth(), tex.getHeight());
        glVertex2f(tex.getImageWidth(),tex.getImageHeight());

        glTexCoord2f(0, tex.getHeight());
        glVertex2f(0,tex.getImageHeight());

        glEnd();
        glLoadIdentity();
    }

    public static void drawQuadTex(Texture tex, float x, float y, float width, float height){
        tex.bind();
        glTranslatef(x, y, 0);

        glBegin(GL_QUADS);

        glTexCoord2f(0, 0);
        glVertex2f(0, 0);

        glTexCoord2f(1, 0);
        glVertex2f(width, 0);

        glTexCoord2f(1, 1);
        glVertex2f(width, height);

        glTexCoord2f(0, 1);
        glVertex2f(0, height);

        glEnd();
        glLoadIdentity();
    }

    public static void drawHexTexAsQuadTex(Texture tex, float x, float y, float width, float height, float xOffset, float yOffset){
        tex.bind();
        glTranslatef(x+xOffset, y+yOffset, 0);

        glBegin(GL_QUADS);

        glTexCoord2f(0, 0);
        glVertex2f(0, 0);

        glTexCoord2f(1, 0);
        glVertex2f(width, 0);

        glTexCoord2f(1, 1);
        glVertex2f(width, height);

        glTexCoord2f(0, 1);
        glVertex2f(0, height);

        glEnd();
        glLoadIdentity();
    }

    public static void drawDrawable(IDrawable drawable){
        for(Texture tex : drawable.getTextures()){
            if(tex!=null)
                drawQuadTex(tex, drawable.getX(), drawable.getY(), drawable.getWidth(), drawable.getHeight());
        }
    }
}
