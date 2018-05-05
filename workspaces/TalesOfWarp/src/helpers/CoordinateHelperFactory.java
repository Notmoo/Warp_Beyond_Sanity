package helpers;

import data.Tuple;

public class CoordinateHelperFactory {

    public static ICoordHelper makeGridCoordinateHelper() {
        return new ICoordHelper() {

            private float offsetX = 0, offsetY = 0;

            @Override
            public Tuple<Float, Float> getPixelPos(int row, int col, float width, float height) {
                return getPixelPos(row, col, width, height, 0, 0);
            }

            @Override
            public Tuple<Integer, Integer> getGridPos(float x, float y, float width, float height) {
                return getGridPos(x, y, width, height, 0, 0);
            }

            @Override
            //TODO ajouter test pour vérif que le couple [row, col] ne dépasse pas de la zone à afficher
            public Tuple<Float, Float> getPixelPos(int row, int col, float width, float height, int firstDiplayedRow, int firstDiplayedCol) {
                if(row-firstDiplayedRow>=0 && col-firstDiplayedCol>=0)
                    return new Tuple<>((col-firstDiplayedCol)*width+offsetX, (row-firstDiplayedRow)*height+offsetY);

                return null;
            }

            @Override
            public Tuple<Integer, Integer> getGridPos(float x, float y, float width, float height, int firstDiplayedRow, int firstDiplayedCol) {
                int row = (int)Math.floor((y-offsetY)/height)+firstDiplayedRow;
                int col = (int)Math.floor((x-offsetX)/width)+firstDiplayedCol;
                return new Tuple<>(row, col);
            }

            @Override
            public void setPixelOffset(float x, float y) {
                this.offsetX = x;
                this.offsetY = y;
            }
        };
    }
}
