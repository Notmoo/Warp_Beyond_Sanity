package helpers;

import data.Tuple;

public interface ICoordHelper {
    Tuple<Float, Float> getPixelPos(int row, int col, float width, float height);
    Tuple<Integer, Integer> getGridPos(float x, float y, float width, float height);

    Tuple<Float,Float> getPixelPos(int row, int col, float width, float height, int firstDiplayedRow, int firstDiplayedCol);
    Tuple<Integer,Integer> getGridPos(float x, float y, float width, float height, int firstDiplayedRow, int firstDiplayedCol);
}
