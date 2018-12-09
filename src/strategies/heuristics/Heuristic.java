package strategies.heuristics;

import engine.State;

public interface Heuristic {
    int getValue(State processedState, State solvedState);
}
