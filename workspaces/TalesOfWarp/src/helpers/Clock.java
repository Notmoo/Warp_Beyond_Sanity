package helpers;

import org.lwjgl.Sys;

public class Clock {
    private static boolean paused = false;
    public static long lastFrame, totalTime;
    public static float d = 1, multiplier = 1;

    public static long getTime(){
        return Sys.getTime()*1000/Sys.getTimerResolution();
    }

    public static float getDelta(){
        long currentTime = getTime();
        int delta = (int) (currentTime-lastFrame);
        lastFrame = currentTime;
        return delta*0.01f;
    }

    public static float delta(){
        if(paused)
            return 0;
        else
            return d*multiplier;
    }

    public static float totalTime(){
        return totalTime;
    }

    public static float getMultiplier() {
        return multiplier;
    }

    public static void update(){
        d = delta();
        totalTime += d;
    }

    public static void changeMultiplier(int change){
        if(!(multiplier+change<=0 || multiplier+change>=6))
            multiplier+=change;
    }

    public static void pause(){
        paused = !paused;
    }
}
