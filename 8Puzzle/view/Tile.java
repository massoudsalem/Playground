package view;

public class Tile {
    private double xCoordinate ;
    private double yCoordinate ;
    private int numberOfTile ;

    public Tile(double xCoordinate, double yCoordinate, int numberOfTile) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.numberOfTile = numberOfTile;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "xCoordinate=" + xCoordinate +
                ", yCoordinate=" + yCoordinate +
                ", numberOfTile=" + numberOfTile +
                "}\n";
    }

    public double getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(double xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public double getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(double yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public int getNumberOfTile() {
        return numberOfTile;
    }

}
