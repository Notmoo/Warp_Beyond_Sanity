package helpers;

import data.Tuple;

public interface ICoordHelper {
    //Permet de convertir des coordonnées de grille [ligne, colonne] en coordonnées absolues en pixel [x, y]
    Tuple<Float, Float> getPixelPos(int row, int col, float width, float height);
    Tuple<Float,Float> getPixelPos(int row, int col, float width, float height, int firstDiplayedRow, int firstDiplayedCol);

    //Permet de convertir des coordonnées absolues en pixel [x, y] en coordonnées pour la grille [ligne, colonne]
    Tuple<Integer, Integer> getGridPos(float x, float y, float width, float height);
    Tuple<Integer,Integer> getGridPos(float x, float y, float width, float height, int firstDiplayedRow, int firstDiplayedCol);

    /*
        Fait varier l'offset en pixel. Cet offset permet de faire en sorte le coin supérieur gauche de la grille ne soit
        plus considéré comme étant le point [x = 0, y = 0]. Cela permet notamment de faire en sorte que la grille soit
        affichée n'importe où sur l'écran et que le coordHelper fasse quand même des bonnes conversions de coordonnées.
     */
    void setPixelOffset(float x, float y);
}
