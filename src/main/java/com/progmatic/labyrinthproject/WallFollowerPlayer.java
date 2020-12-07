package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;
import com.progmatic.labyrinthproject.interfaces.Player;

public class WallFollowerPlayer implements Player {
    @Override
    public Direction nextMove(Labyrinth l) {
        if (l.possibleMoves().contains(Direction.SOUTH)) {
            return Direction.SOUTH;
        } else if (l.possibleMoves().contains(Direction.EAST)) {
            return Direction.EAST;
        } else if (l.possibleMoves().contains(Direction.NORTH)) {
            return Direction.NORTH;
        }
        return Direction.WEST;
    }
}

