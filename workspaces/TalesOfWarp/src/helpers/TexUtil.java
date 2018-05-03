package helpers;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;

public class TexUtil {

    public static Texture LoadTexture(String path, String fileType){
        Texture tex = null;
        InputStream in = ResourceLoader.getResourceAsStream(path);

        try {
            tex = TextureLoader.getTexture(fileType, in);
        } catch (IOException e) {
            //TODO Gère l'exception
            e.printStackTrace();
        }

        return tex;
    }

    public static Texture QuickLoadPngTexture(String imageName){
        return LoadTexture("res/"+imageName+".png", "PNG");
    }
}
