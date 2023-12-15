package fr.valentinthuillier.prim;

import fr.valentinthuillier.prim.enums.Direction;

import java.util.Map;
import java.util.Objects;

public class Coordinate {

    int row;
    int col;

    public Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Coordinate that)) return false;
        return getRow() == that.getRow() && getCol() == that.getCol();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRow(), getCol());
    }

    public Coordinate north() {
        return Direction.NORTH.getCoordinate(this);
    }

    public Coordinate south() {
        return Direction.SOUTH.getCoordinate(this);
    }

    public Coordinate est() {
        return Direction.EST.getCoordinate(this);
    }

    public Coordinate west() {
        return Direction.WEST.getCoordinate(this);
    }

    public Map<Direction, Coordinate> around() {
        return Map.of(Direction.NORTH, north(), Direction.SOUTH, south(), Direction.EST, est(), Direction.WEST, west());
    }

    public Coordinate getSpecificCoordinate(Direction direction) {
        return switch(direction) {
            case NORTH -> north();
            case EST -> est();
            case SOUTH -> south();
            case WEST -> west();
        };
    }

    public Map<Direction, Coordinate> around(int maxRow, int maxCol) {
        Map<Direction, Coordinate> around = around();
        for(Map.Entry<Direction, Coordinate> entry : around.entrySet()) {
            Coordinate coordinate = entry.getValue();
            if(coordinate.isValid(maxRow, maxCol)) {
                around.remove(entry.getKey());
            }
        }
        return around;
    }

    public boolean isValid(int maxRow, int maxCol) {
        return this.getRow() < 0 || this.getRow() >= maxRow || this.getCol() < 0 || this.getCol() >= maxCol;
    }

    public void incrementCol() { col++; }
    public void decrementCol() { col--; }

    public void incrementRow() { row++; }
    public void decrementRow() { row--; }

}
