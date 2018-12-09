package engine;

import java.util.ArrayList;
import java.util.List;

public class State {
    public static final byte ZERO_PUZZLE = 0;

    private byte dimensionX;
    private byte dimensionY;
    private byte[] stateArray;
    private State parentState;
    private MoveDirection previousMove;
    private int depthLevel;

    public State(byte dimensionX, byte dimensionY, byte[] stateArray, State parentState, MoveDirection previousMove, int depthLevel) {
        this.dimensionX = dimensionX;
        this.dimensionY = dimensionY;
        this.stateArray = stateArray;
        this.parentState = parentState;
        this.previousMove = previousMove;
        this.depthLevel = depthLevel;
    }

    public byte getDimensionX() { return dimensionX; }

    public byte getDimensionY() {
        return dimensionY;
    }

    public byte[] getStateArray() {
        return stateArray;
    }

    public byte getPuzzleIndex(byte puzzleValue) {
        for (byte i = 0; i < dimensionX * dimensionY; i++) {
            if (stateArray[i] == puzzleValue) {
                return i;
            }
        }

        return -1;
    }

    public int getDepthLevel() {
        return depthLevel;
    }

    public String getPath() {
        String path = "";

        if (parentState == null) {
            return path;
        }

        path = path.concat(parentState.getPath() + previousMove);

        return path;
    }

    public byte getPuzzleX(byte puzzleValue) {
        return (byte) (getPuzzleIndex(puzzleValue) % dimensionX);
    }

    public byte getPuzzleY(byte puzzleValue) {
        return (byte) ((getPuzzleIndex(puzzleValue) - getPuzzleX(puzzleValue)) / dimensionX);
    }

    public List<MoveDirection> getAvailableMoves() {
        List<MoveDirection> availableMoves = new ArrayList<>();

        if (getPuzzleY(ZERO_PUZZLE) > 0) {
            availableMoves.add(MoveDirection.UP);
        }
        if (getPuzzleY(ZERO_PUZZLE) < dimensionY - 1) {
            availableMoves.add(MoveDirection.DOWN);
        }
        if (getPuzzleX(ZERO_PUZZLE) > 0) {
            availableMoves.add(MoveDirection.LEFT);
        }
        if (getPuzzleX(ZERO_PUZZLE) < dimensionX - 1) {
            availableMoves.add(MoveDirection.RIGHT);
        }

        return availableMoves;
    }


    @Override
    public String toString() {
        String str = "";

        for (int row = 0; row < dimensionY; row++) {
            for (int column = 0; column < dimensionX; column++) {
                str = str.concat(String.valueOf(stateArray[row * dimensionX + column]) + " ");
            }
            str = str.concat("\n");
        }

        return str;
    }
}
