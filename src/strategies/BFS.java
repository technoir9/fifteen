package strategies;

import engine.MoveDirection;
import engine.SearchOrder;
import engine.State;
import engine.StateFactory;
import writer.ExtraInformation;
import writer.SolutionInformation;

import java.util.*;

public class BFS implements Strategy {
    private ExtraInformation extraInformation;
    private SolutionInformation solutionInformation;

    protected State goalState;
    protected State currentState;
    protected SearchOrder searchStrategy;
    protected LinkedList<State> listOfOpenStates;
    protected Set<State> listOfClosedStates;
    protected LinkedList<MoveDirection> directions;
    protected StateFactory stateFactory;
    protected int maxDepth;

    public BFS(State initialState, SearchOrder searchOrder) {
        stateFactory = new StateFactory(initialState.getDimensionX(), initialState.getDimensionY());
        goalState = stateFactory.getSolvedState();
        currentState = initialState;
        listOfOpenStates = new LinkedList<>();
        listOfClosedStates = new LinkedHashSet<>();
        directions = new LinkedList<>();
        searchStrategy = searchOrder;
        maxDepth = 1;

        extraInformation = new ExtraInformation();
        solutionInformation = new SolutionInformation();
    }

    private boolean isSolved() {
        return (Arrays.equals(currentState.getStateArray(), goalState.getStateArray()));
    }

    @Override
    public void solve() {
        int visitedStates = 0;
        long startTimestamp = System.nanoTime();

        listOfOpenStates.addFirst(currentState);
        visitedStates++;

        while (!listOfOpenStates.isEmpty()) {
            currentState = listOfOpenStates.pollFirst();
            listOfClosedStates.add(currentState);

            if (isSolved()) {
                long endTimestamp = System.nanoTime();

                solutionInformation.setSolutionLength(currentState.getDepthLevel());
                solutionInformation.setSolutionMoves(currentState.getPath());

                extraInformation.setVisitedStates(visitedStates);
                extraInformation.setProcessedStates(listOfClosedStates.size());
                extraInformation.setMaxRecursionDepth(maxDepth);
                extraInformation.setSolutionLength(currentState.getDepthLevel());

                double computationTime = (endTimestamp - startTimestamp) / 100000.0;
                extraInformation.setComputationTime(computationTime);
                return; // Success
            }

            for (State neighbor : stateFactory.getNeighbors(currentState, searchStrategy)) {
                if(currentState.getDepthLevel() > 20)
                    break;
                if (!(listOfOpenStates.contains(neighbor) || listOfClosedStates.contains(neighbor))) {
                    if(currentState.getDepthLevel() > maxDepth)
                        maxDepth = currentState.getDepthLevel();
                    visitedStates++;
                    listOfOpenStates.addLast(neighbor);
                }
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
