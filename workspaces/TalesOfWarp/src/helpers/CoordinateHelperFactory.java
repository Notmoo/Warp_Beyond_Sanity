package helpers;

import data.Tuple;

public class CoordinateHelperFactory {

    public static ICoordHelper makeGridCoordinateHelper() {
        return new ICoordHelper() {
            @Override
            public Tuple<Float, Float> getPixelPos(int row, int col, float width, float height) {
                return new Tuple<>(row*height, col*width);
            }

            @Override
            public Tuple<Integer, Integer> getGridPos(float x, float y, float width, float height) {
                int row = (int)Math.floor(x/height);
                int col = (int)Math.floor(y/width);
                return new Tuple<>(row, col);
            }

            @Override
            //TODO ajouter test pour vérif que le couple [row, col] ne dépasse pas de la zone à afficher
            public Tuple<Float, Float> getPixelPos(int row, int col, float width, float height, int firstDiplayedRow, int firstDiplayedCol) {
                if(row-firstDiplayedRow>=0 && col-firstDiplayedCol>=0)
                    return new Tuple<>((row-firstDiplayedRow)*height, (col-firstDiplayedCol)*width);

                return null;
            }

            @Override
            public Tuple<Integer, Integer> getGridPos(float x, float y, float width, float height, int firstDiplayedRow, int firstDiplayedCol) {
                int row = (int)Math.floor(x/height)+firstDiplayedRow;
                int col = (int)Math.floor(y/width)+firstDiplayedCol;
                return new Tuple<>(row, col);
            }
        };
    }
}
