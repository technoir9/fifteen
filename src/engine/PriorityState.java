package engine;

public class PriorityState implements Comparable<PriorityState> {
    private State state;
    private int priority;

    public PriorityState(State state, int priority) {
        this.state = state;
        this.priority = priority;
    }

    public State getState() {
        return state;
    }

    @Override
    public int compareTo(PriorityState o) {
        return Integer.compare(priority, o.priority);
    }
}
