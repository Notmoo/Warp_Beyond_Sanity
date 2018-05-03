package helpers;

import data.Hex;
import data.Tuple;

public interface IHexCoordHelper {
    Tuple<Float, Float> getPixelPos(int q, int r, float size);
    Hex getGridPos(float x, float y, float size);
}
