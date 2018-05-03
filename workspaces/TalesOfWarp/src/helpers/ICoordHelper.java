package helpers;

import data.Hex;
import data.Tuple;

public interface ICoordHelper {
    Tuple<Float, Float> getPixelPos(int row, int col, float width, float height);
    Tuple<Integer, Integer> getGridPos(float x, float y, float width, float height);
}
