package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.CellType;
import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.exceptions.CellException;
import com.progmatic.labyrinthproject.exceptions.InvalidMoveException;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author pappgergely
 */
public class LabyrinthImpl implements Labyrinth {
    Labyrinth l;
    private int width;
    private int height;
    private CellType[][] lArr;
    //private Coordinate widthHeight;
    private Coordinate playerCoordinate;
    //private Coordinate end;

    public LabyrinthImpl() {
        lArr = new CellType[1][1];

    }

    @Override
    public int getWidth() {
        if (lArr[0].length == 1) {
            return -1;
        } else {
            return lArr[0].length;
        }
    }

    @Override
    public int getHeight() {
        if (lArr.length == 1) {
            return -1;
        } else {
            return lArr.length;
        }
    }

    @Override
    public void loadLabyrinthFile(String fileName) {
        try {
            Scanner sc = new Scanner(new File(fileName));
            int width = Integer.parseInt(sc.nextLine());
            int height = Integer.parseInt(sc.nextLine());
            lArr = new CellType[height][width];
            for (int hh = 0; hh < height; hh++) {
                String line = sc.nextLine();
                for (int ww = 0; ww < width; ww++) {
                    CellType type;
                    switch (line.charAt(ww)) {
                        case 'W':
                            type = CellType.WALL;

                            break;
                        case 'E':
                            type = CellType.END;
                            //end = new Coordinate(ww, hh);
                            break;
                        case 'S':
                            type = CellType.START;
                            playerCoordinate = new Coordinate(ww, hh);
                            break;
                        default:
                            type = CellType.EMPTY;

                            break;
                    }
                    setCellType(new Coordinate(ww, hh), type);
                }
            }
        } catch (FileNotFoundException | NumberFormatException | CellException ex) {
            System.out.println(ex.toString());
        }
    }

    @Override
    public CellType getCellType(Coordinate c) throws CellException {
        if (c.getCol() < 0 || c.getRow() < 0) {
            throw new CellException(c.getRow(), c.getCol(), "Hibás koordináta");
        }
        if (c.getRow() > lArr.length - 1 || c.getCol() > lArr[0].length - 1) {
            throw new CellException(c, "Túl nagy koordináta");
        }
        return lArr[c.getRow()][c.getCol()];

    }

    @Override
    public void setSize(int width, int height) {
        l = new LabyrinthImpl();
        lArr = new CellType[height][width];

    }

    @Override
    public void setCellType(Coordinate c, CellType type) throws CellException {
        if (c.getCol() < 0 || c.getRow() < 0) {
            throw new CellException(c.getRow(), c.getCol(), "Hibás koordináta érték");
        }
        if (c.getRow() > lArr.length - 1 || c.getCol() > lArr[0].length - 1) {
            throw new CellException(c, "Túl nagy koordináta érték");
        }
        if (type.equals(CellType.START)) {
            playerCoordinate = new Coordinate(c.getCol(), c.getRow());
        }
        lArr[c.getRow()][c.getCol()] = type;
    }

    @Override
    public Coordinate getPlayerPosition() {

        return playerCoordinate;
    }

    @Override
    public boolean hasPlayerFinished() {
        return lArr[playerCoordinate.getRow()][playerCoordinate.getCol()] == CellType.END;
    }

    @Override
    public List<Direction> possibleMoves() {
        List<Direction> possible = new ArrayList<>();

        int row = playerCoordinate.getRow();
        int col = playerCoordinate.getCol();
        if (row > 0 && lArr[row - 1][col] == CellType.END){
            possible.add(Direction.NORTH);
        }
        if (row > 0 && lArr[row - 1][col] == CellType.EMPTY||lArr[row - 1][col] == CellType.END) {
            possible.add(Direction.NORTH);
        }if (row < lArr.length - 1 && lArr[row + 1][col] == CellType.END){
            possible.add(Direction.SOUTH);
        }
        if (row < lArr.length - 1 && lArr[row + 1][col] == CellType.EMPTY) {
            possible.add(Direction.SOUTH);
        }if (col > 0 && lArr[row][col - 1] == CellType.END){
            possible.add(Direction.WEST);
        }
        if (col > 0 && lArr[row][col - 1] == CellType.EMPTY) {
            possible.add(Direction.WEST);
        }if (lArr[row][col + 1] == CellType.END&&col < lArr[row].length - 1){
            possible.add(Direction.EAST);
        }
        if (col < lArr[row].length - 1 && lArr[row][col + 1] == CellType.EMPTY) {
            possible.add(Direction.EAST);
        }

        return possible;
    }

    @Override
    public void movePlayer(Direction direction) throws InvalidMoveException {
        if (possibleMoves().contains(direction)) {
            switch (direction) {
                case NORTH:
                    playerCoordinate = new Coordinate(playerCoordinate.getCol(), playerCoordinate.getRow() - 1);
                    break;
                case SOUTH:
                    playerCoordinate = new Coordinate(playerCoordinate.getCol(), playerCoordinate.getRow() + 1);
                    break;
                case WEST:
                    playerCoordinate = new Coordinate(playerCoordinate.getCol() - 1, playerCoordinate.getRow());
                    break;
                case EAST:
                    playerCoordinate = new Coordinate(playerCoordinate.getCol() + 1, playerCoordinate.getRow());
                    break;
            }
        } else {
            throw new InvalidMoveException();
        }

    }

}
