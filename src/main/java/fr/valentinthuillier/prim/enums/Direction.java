package fr.valentinthuillier.prim.enums;

import fr.valentinthuillier.prim.Coordinate;

public enum Direction {

    NORTH(-1, 0), SOUTH(1, 0), EST(0, 1), WEST(0, -1);

    private final int row;
    private final int col;

    Direction(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() { return row; }

    public int getCol() { return col; }

    public Coordinate getCoordinate(Coordinate startPoint) {
        return new Coordinate(startPoint.getRow() + row, startPoint.getCol() + col);
    }

}
