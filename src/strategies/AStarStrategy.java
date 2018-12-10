package strategies;

import engine.MoveDirection;
import engine.State;
import engine.StateFactory;
import engine.PriorityState;
import strategies.heuristics.Heuristic;
import writer.ExtraInformation;
import writer.SolutionInformation;

import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class AStarStrategy implements Strategy {
    private ExtraInformation extraInformation;
    private SolutionInformation solutionInformation;

    private State currentState;
    private State solvedState;
    private StateFactory stateFactory;
    private Heuristic heuristic;

    public AStarStrategy(State initialState, Heuristic heuristic) {
        currentState = initialState;
        this.heuristic = heuristic;

        stateFactory = new StateFactory(initialState.getDimensionX(), initialState.getDimensionY());
        solvedState = stateFactory.getSolvedState();

        extraInformation = new ExtraInformation();
        solutionInformation = new SolutionInformation();
    }

    private boolean isSolved() {
        return Arrays.equals(currentState.getStateArray(), solvedState.getStateArray());
    }

    @Override
    public void solve() {
        int visitedStates = 0;
        long startTimestamp = System.nanoTime();

        PriorityQueue<PriorityState> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(new PriorityState(currentState, 0));

        while (!priorityQueue.isEmpty()) {
            currentState = priorityQueue.poll().getState();

            visitedStates++;

            if (isSolved()) {
                long endTimestamp = System.nanoTime();

                solutionInformation.setSolutionLength(currentState.getDepthLevel());
                solutionInformation.setSolutionMoves(currentState.getPath());

                extraInformation.setVisitedStates(visitedStates);
                extraInformation.setProcessedStates(visitedStates + priorityQueue.size());
                extraInformation.setMaxRecursionDepth(currentState.getDepthLevel());
                extraInformation.setSolutionLength(currentState.getDepthLevel());

                double computationTime = (endTimestamp - startTimestamp) / 100000.0;
                extraInformation.setComputationTime(computationTime);

                return;
            }

            List<MoveDirection> availableMoves = currentState.getAvailableMoves();

            for (MoveDirection moveDirection : availableMoves) {
                State stateAfterMove = stateFactory.getStateAfterMove(currentState, moveDirection);
                priorityQueue.add(new PriorityState(stateAfterMove, heuristic.getValue(stateAfterMove, solvedState)));
            }
        }
    }

    @Override
    public ExtraInformation getExtraInformation() {
        return extraInformation;
    }

    @Override
    public SolutionInformation getSolutionInformation() {
        return solutionInformation;
    }
}
