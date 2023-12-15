package fr.valentinthuillier.prim;

import fr.valentinthuillier.prim.enums.Direction;
import fr.valentinthuillier.prim.enums.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Prim {

    private static final double PERCENT_WALL_DEFAULT = 0.35;
    private static final Random RDM = new Random();
    private final int row;
    private final int col;
    private final double percentWall;
    private boolean[][] visited;
    private Case[][] map;

    public Prim(int row, int col, double percentWall) {
        this.row = row;
        this.col = col;
        this.percentWall = percentWall;
        this.visited = new boolean[row][col];
    }

    public Prim(int row, int col) {
         this(row, col, PERCENT_WALL_DEFAULT);
    }

    private void initializeMap() {
        this.visited = new boolean[this.row][this.col];
        this.map = new Case[this.row][this.col];
        for(int i = 0; i < this.row; i++) {
            for(int j = 0; j < this.col; j++) {
                this.map[i][j] = new Case(Type.WALL, new Coordinate(i, j));
            }
        }
    }

    public void generePrim() {
        initializeMap();
        List<Coordinate> frontier = new ArrayList<>();
        Coordinate coord = new Coordinate(RDM.nextInt(row - 1), RDM.nextInt(col - 1));
        this.map[coord.getRow()][coord.getCol()].setType(Type.START);
        setVisited(coord);
        addToFrontier(frontier, getFrontierCoords(coord));
        Coordinate next = null;
        while(!frontier.isEmpty()) {
            next = frontier.get(RDM.nextInt(frontier.size()));
            coord = getOriginCord(next);
            setVisited(next);
            frontier.remove(next);
            setVisited(getGateway(coord, next));
            addToFrontier(frontier, getFrontierCoords(next));
        }
        this.map[next.getRow()][next.getCol()].setType(Type.END);
        genereNotSet();
    }

    void genereNotSet() {
        for(int i = 0; i < this.row; i++) {
            for(int j = 0; j < this.col; j++) {
                if(!isVisited(new Coordinate(i, j)) && RDM.nextDouble(1) <= this.percentWall && isValidWall(i, j)) {
                    this.setVisited(i, j);
                }
            }
        }

    }

    private void setVisited(Coordinate coordinate) {
        this.setVisited(coordinate.getRow(), coordinate.getCol());
    }

    private void setVisited(int row, int col) {
        this.map[row][col].setType(Type.EMPTY);
        this.visited[row][col] = true;
    }

    List<Coordinate> getFrontierCoords(Coordinate coordinate) {
        List<Coordinate> coordinateList = new ArrayList<>();
        for(Direction d : Direction.values()) {
            coordinateList.add(coordinate.getSpecificCoordinate(d).getSpecificCoordinate(d));
        }
        return coordinateList;
    }

    void addToFrontier(List<Coordinate> frontier, List<Coordinate> coordinatesFrontier) {
        for(Coordinate coord : coordinatesFrontier) {
            if(isPossibility(coord) && !frontier.contains(coord)) {
                frontier.add(coord);
            }
        }
    }

    boolean isPossibility(Coordinate coordinate) {
        return coordinate.isValid(row, col) && isWall(coordinate);
    }

    private boolean isWall(Coordinate coordinate) {
        return this.map[coordinate.getRow()][coordinate.getCol()].getType() == Type.WALL;
    }

    private boolean isVisited(Coordinate coordinate) {
        return this.visited[coordinate.getRow()][coordinate.getCol()];
    }

    Coordinate getOriginCord(Coordinate coordinate) {
        List<Coordinate> coordinatesFrontier = getFrontierCoords(coordinate);
        for(Coordinate coord : coordinatesFrontier) {
            if(coord.isValid(row, col) && isVisited(coord)) {
                return coord;
            }
        }
        return null;
    }

    Coordinate getGateway(Coordinate start, Coordinate end) {
        Coordinate diff = new Coordinate(start.getRow() - end.getRow(), start.getCol() - end.getCol());
        if(diff.getCol() < 0) diff.incrementCol();
        else if(diff.getCol() > 0) diff.decrementCol();
        else if(diff.getRow() < 0) diff.incrementRow();
        else if(diff.getRow() > 0) diff.decrementRow();
        else throw new GatewayNotFoundException("Gateway not found to Prims Algorithm");
        return new Coordinate(start.getRow() - diff.getRow(), start.getCol() - diff.getCol());

    }

    private boolean isValidWall(int row, int col) {
        if(this.map[row][col].getType() != Type.EMPTY) return false;
        Coordinate[] neighbors = new Coordinate(row, col).around().values().toArray(new Coordinate[0]);
        for (Coordinate neighbor : neighbors) {
            if (neighbor.isValid(this.row, this.col) && this.map[neighbor.getRow()][neighbor.getCol()].getType() == Type.EMPTY) {
                return true;
            }
        }
        return false;
    }



    class GatewayNotFoundException extends RuntimeException {
        GatewayNotFoundException(String message) { super(message); }
    }
}
