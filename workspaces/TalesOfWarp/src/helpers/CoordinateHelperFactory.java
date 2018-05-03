package helpers;

import data.Hex;
import data.Tuple;

public class CoordinateHelperFactory {

    public static IHexCoordHelper makeHexGridAxialCoordinateHelper(){
        return new IHexCoordHelper() {

            class Cube{
                float x, y, z;

                public Cube(float x, float y, float z) {
                    this.x = x;
                    this.y = y;
                    this.z = z;
                }
            }

            @Override
            public Tuple<Float, Float> getPixelPos(int q, int r, float size) {
                float x = (float) (size * (Math.sqrt(3) * q + Math.sqrt(3)/2*r));
                float y = (size * 3/2*r);

                return new Tuple<>(x, y);
            }

            @Override
            public Hex getGridPos(float x, float y, float size) {
                float q = (float)((Math.sqrt(3)/3 * x - 1/3*y)/size);
                float r = (float)((2./3)*y/size);

                return roundHex(new Hex(q, r));
            }

            Hex roundHex(Hex hex){
                return cubeToHex(roundCube(hexToCube(hex)));
            }

            Cube roundCube(Cube cube){

                float xNew = Math.round(cube.x);
                float yNew = Math.round(cube.y);
                float zNew = Math.round(cube.z);

                float xDiff = Math.abs(xNew-cube.x);
                float yDiff = Math.abs(yNew-cube.y);
                float zDiff = Math.abs(zNew-cube.z);

                if(xDiff>yDiff && xDiff>zDiff)
                    xNew = -yNew-zNew;
                else if(yDiff>zDiff)
                    yNew = -xNew-zNew;
                else
                    zNew = -xNew-yNew;

                return new Cube(xNew, yNew, zNew);
            }

            Hex cubeToHex(Cube cube){
                return new Hex(cube.x, cube.z);
            }

            Cube hexToCube(Hex hex){
                return new Cube(hex.q, -hex.q-hex.r, hex.r);
            }
        };
    }

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
        };
    }
}
