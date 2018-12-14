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
    protected LinkedList<State> frontierList;
    protected Set<State> exploredList;
    protected LinkedList<MoveDirection> directions;
    protected StateFactory stateFactory;
    protected int maxDepth;

    public BFS(State initialState, SearchOrder searchOrder) {
        stateFactory = new StateFactory(initialState.getDimensionX(), initialState.getDimensionY());
        goalState = stateFactory.getSolvedState();
        currentState = initialState;
        frontierList = new LinkedList<>();
        exploredList = new LinkedHashSet<>();
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
        System.out.println("BFS");
        int visitedStates = 0;
        long startTimestamp = System.nanoTime();

        frontierList.addFirst(currentState);
        visitedStates++;

        while (!frontierList.isEmpty()) {
            currentState = frontierList.pollFirst();
            exploredList.add(currentState);

            if (isSolved()) {
                // Save the information about the solution
                long endTimestamp = System.nanoTime();

                solutionInformation.setSolutionLength(currentState.getDepthLevel());
                solutionInformation.setSolutionMoves(currentState.getPath());

                extraInformation.setVisitedStates(visitedStates);
                extraInformation.setProcessedStates(exploredList.size());
                extraInformation.setMaxRecursionDepth(maxDepth);
                extraInformation.setSolutionLength(currentState.getDepthLevel());

                double computationTime = (endTimestamp - startTimestamp) / 100000.0;
                extraInformation.setComputationTime(computationTime);
                return; // Success
            }

            for (State neighbor : stateFactory.getNeighbors(currentState, searchStrategy)) {
                // TODO check what happens when you remove max depth
                if(currentState.getDepthLevel() > 25)
                    break;
//                if (!(frontierList.contains(neighbor) || exploredList.contains(neighbor))) {
                if ((!(frontierList.contains(neighbor))) && (!(exploredList.contains(neighbor)))) {
                    if(currentState.getDepthLevel() > maxDepth)
                        maxDepth = currentState.getDepthLevel();
                    visitedStates++;
                    frontierList.addLast(neighbor);
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
