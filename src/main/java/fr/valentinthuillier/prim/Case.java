package fr.valentinthuillier.prim;

import fr.valentinthuillier.prim.enums.Type;

public class Case {

    private Type type;
    private final Coordinate coordinate;

    public Case(Type type, Coordinate coordinate) {
        this.type = type;
        this.coordinate = coordinate;
    }

    public Case(Coordinate coordinate) {
        this(Type.EMPTY, coordinate);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

}
