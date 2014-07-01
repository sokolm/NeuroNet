package org.amse.marinaSokol.model.impl.utils;


public class NonExpandingQuery  {
    private final int mySizeX;
    private final int mySizeY;
    private double[][] myStorage;

    public NonExpandingQuery(int sizeY, int sizeX) {
        mySizeY = sizeY;
        mySizeX = sizeX;
        myStorage = new double[sizeY][sizeX];
    }

    public NonExpandingQuery(double[][] src) {
        mySizeY = src.length;
        mySizeX = src[0].length;

        myStorage = new double[src.length][];
        for(int i = 0; i < this.myStorage.length; i++) {
            myStorage[i] = src[i].clone();
        }
    }

    public void addFront(double[] src) {
        for (int y = mySizeY - 1; y > 0; y--) {
            myStorage[y] = myStorage[y - 1];
        }
        System.arraycopy(src, 0, myStorage[0], 0, mySizeX);
    }

    public void addBack(double []src) {
        /*for (int y = 0; y < mySizeY - 1; y++) {
            myStorage[y] = myStorage[y + 1];
        }*/
        System.arraycopy(myStorage, 1, myStorage, 0, mySizeY - 1);
        System.arraycopy(src, 0, myStorage[mySizeY - 1], 0, mySizeX);
    }

    public int getY() {
        return mySizeY;
    }

    public void clear() {
        for (int x = 0; x < mySizeX; x++) {
            for (int y = 0; y < mySizeY; y++) {
                myStorage[y][x] = 0;
            }
        }
    }

    public double[] get(int k) {
        if ((k < 0) && (k >= mySizeY)) {
            throw new RuntimeException("NonExpandingQuery: выход за границы myStorage");
        }
        return myStorage[k];
    }

    public void set(int k, double []src) {
        if ((k < 0) && (k >= mySizeY)) {
            throw new RuntimeException("NonExpandingQuery: выход за границы myStorage");
        }
        System.arraycopy(src, 0, myStorage[k], 0, mySizeX);
    }

    public double[] getBack() {
        return myStorage[mySizeY - 1];
    }

    public double[] getFront() {
        return myStorage[0];
    }

    public int getSizeX() {
        return mySizeX;
    }

    public int getSizeY() {
        return mySizeY;
    }

    public double getXY(int y, int x) {
        return myStorage[y][x];
    }
}
