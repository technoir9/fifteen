package engine;

public enum MoveDirection {
    DOWN("D"),
    LEFT("L"),
    NONE("NO"),
    RIGHT("R"),
    UP("U");

    private final String directionShort;

    MoveDirection(String directionShort) {
        this.directionShort = directionShort;
    }

    @Override
    public String toString() {
        return directionShort;
    }
}
