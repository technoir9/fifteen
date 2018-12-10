package engine;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class StateFactory {
    private byte dimensionX;
    private byte dimensionY;

    public StateFactory(byte dimensionX, byte dimensionY) {
        this.dimensionX = dimensionX;
        this.dimensionY = dimensionY;
    }

    public State getSolvedState() {
        byte[] solvedStateArray = new byte[dimensionX * dimensionY];
        for (byte i = 0; i < solvedStateArray.length - 1; i++) {
            solvedStateArray[i] = (byte) (i + 1);
        }

        return new State(dimensionX, dimensionY, solvedStateArray, null, MoveDirection.NONE, 0);
    }

    public State getStateAfterMove(State currentState, MoveDirection moveDirection) {
        byte zeroIndex = currentState.getPuzzleIndex(State.ZERO_PUZZLE);
        byte indexToSwap = -1;

        switch (moveDirection) {
            case DOWN:
                indexToSwap = (byte) (zeroIndex + currentState.getDimensionX());
                break;
            case UP:
                indexToSwap = (byte) (zeroIndex - currentState.getDimensionX());
                break;
            case LEFT:
                indexToSwap = (byte) (zeroIndex - 1);
                break;
            case RIGHT:
                indexToSwap = (byte) (zeroIndex + 1);
                break;
        }

        byte[] swappedStateArray = swap(currentState.getStateArray(), zeroIndex, indexToSwap);

        return new State(dimensionX, dimensionY, swappedStateArray, currentState, moveDirection, currentState.getDepthLevel() + 1);
    }

    public Queue<State> getNeighbors(State currentState, SearchOrder searchOrder) {
        LinkedList<State> neighbors = new LinkedList<>();
        List<MoveDirection> possibleDirections = currentState.getAvailableMoves();
        possibleDirections.sort(new MoveDirectionComparator(searchOrder));
        for (MoveDirection direction : possibleDirections) {
            State neighbor = getStateAfterMove(currentState, direction);
            neighbors.add(neighbor);
        }
        return neighbors;
    }

    private byte[] swap(byte[] array, byte index1, byte index2) {
        byte[] swappedArray = Arrays.copyOf(array, array.length);

        byte temp = swappedArray[index1];
        swappedArray[index1] = swappedArray[index2];
        swappedArray[index2] = temp;

        return swappedArray;
    }
}
