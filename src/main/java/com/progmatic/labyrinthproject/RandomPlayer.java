package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;
import com.progmatic.labyrinthproject.interfaces.Player;

public class RandomPlayer implements Player {
    @Override
    public Direction nextMove(Labyrinth l) {

        int random = (int) (Math.random() * l.possibleMoves().size());
        switch (random) {
            case 0:
                return l.possibleMoves().get(0);
            case 1:
                return l.possibleMoves().get(1);
            case 2:
                return l.possibleMoves().get(2);
        }
        return l.possibleMoves().get(3);

    }
}

