package helpers;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;

public class WindowHelper {

    public static final int WIDTH = 1280, HEIGHT = 768;

    public static void initWindow(){
        try {
            Display.setTitle("Warp Beyond Sanity");//TODO Centraliser le titre dans un module de lang
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create();
        } catch (Exception e) {
            //TODO Gère l'exception
            e.printStackTrace();
        }

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    public static void destroyWindow(){
        Display.destroy();
    }

    public static boolean isCloseRequested(){
        return Display.isCloseRequested();
    }

    public static void updateWindow(){
        Display.update();
        Display.sync(60);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);	// Clean the screen and the depth buffer
        glLoadIdentity();
    }
}
