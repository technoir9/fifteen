package engine;

import java.security.InvalidParameterException;

public enum SearchOrder {
    RIGHT_DOWN_UP_LEFT("RDUL"),
    RIGHT_DOWN_LEFT_UP("RDLU"),
    DOWN_RIGHT_UP_LEFT("DRUL"),
    DOWN_RIGHT_LEFT_UP("DRLU"),
    LEFT_UP_DOWN_RIGHT("LUDR"),
    LEFT_UP_RIGHT_DOWN("LURD"),
    UP_LEFT_DOWN_RIGHT("ULDR"),
    UP_LEFT_RIGHT_DOWN("ULRD");

    private final String searchOrderShort;

    SearchOrder(String searchOrderShort) {
        this.searchOrderShort = searchOrderShort;
    }

    public static SearchOrder Create(String _searchOrderShort) throws InvalidParameterException {
        if(_searchOrderShort.equals(RIGHT_DOWN_UP_LEFT.toString()))
            return RIGHT_DOWN_UP_LEFT;
        if(_searchOrderShort.equals(RIGHT_DOWN_LEFT_UP.toString()))
            return RIGHT_DOWN_LEFT_UP;
        if(_searchOrderShort.equals(DOWN_RIGHT_UP_LEFT.toString()))
            return DOWN_RIGHT_UP_LEFT;
        if(_searchOrderShort.equals(DOWN_RIGHT_LEFT_UP.toString()))
            return DOWN_RIGHT_LEFT_UP;
        if(_searchOrderShort.equals(LEFT_UP_DOWN_RIGHT.toString()))
            return LEFT_UP_DOWN_RIGHT;
        if(_searchOrderShort.equals(LEFT_UP_RIGHT_DOWN.toString()))
            return LEFT_UP_RIGHT_DOWN;
        if(_searchOrderShort.equals(UP_LEFT_DOWN_RIGHT.toString()))
            return UP_LEFT_DOWN_RIGHT;
        if(_searchOrderShort.equals(UP_LEFT_RIGHT_DOWN.toString()))
            return UP_LEFT_RIGHT_DOWN;
        throw new InvalidParameterException("Move order must be one of RDUL, RDLU, DRUL, DRLU, LUDR, LURD, ULDR, ULRD");
    }
    @Override
    public String toString() {
        return searchOrderShort;
    }
}
