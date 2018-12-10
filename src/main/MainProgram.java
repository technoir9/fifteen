package main;

import engine.State;
import engine.SearchOrder;
import strategies.heuristics.HammingHeuristic;
import strategies.heuristics.ManhattanHeuristic;
import strategies.AStarStrategy;
import strategies.BFS;
import strategies.DFS;
import strategies.Strategy;
import reader.DataReader;
import writer.ExtraInformation;
import writer.ExtraInformationSaver;
import writer.SolutionInformation;
import writer.SolutionInformationSaver;

import java.security.InvalidParameterException;

public class MainProgram {
    // Strategies
    private static final String BFS_STRATEGY = "bfs";
    private static final String DFS_STRATEGY = "dfs";
    private static final String A_STAR_STRATEGY = "astr";

    // Heuristics
    private static final String HAMMING_HEURISTIC = "hamm";
    private static final String MANHATTAN_HEURISTIC = "manh";

    public static void main(String[] args) {
        // Check if 5 arguments have been passed to the program
        if (args.length == 5) {
            String selectedStrategy = args[0];
            String selectedStrategyExtra = args[1];
            String inputFile = args[2];
            String outputSolutionFile = args[3];
            String outputExtraFile = args[4];

            System.out.print(inputFile + ": ");

            State inputState = readInputStateFromFile(inputFile);

            Strategy strategy = null;

            switch (selectedStrategy) {
                case BFS_STRATEGY:
                case DFS_STRATEGY:
                    SearchOrder searchStrategy;
                    try {
                        searchStrategy = SearchOrder.Create(selectedStrategyExtra);
                    }
                    catch(InvalidParameterException e) {
                        System.out.println("Wrong parameter selectedStrategyExtra. Falling back to default move strategy." + e.getMessage());
                        searchStrategy = SearchOrder.Create("RDUL");
                    }

                    // TODO refactor using equals()
                    strategy = (selectedStrategy == BFS_STRATEGY ?  new BFS(inputState, searchStrategy) : new DFS(inputState, searchStrategy));
                    break;
                case A_STAR_STRATEGY:
                    if (selectedStrategyExtra.equals(HAMMING_HEURISTIC)) {
                        strategy = new AStarStrategy(inputState, new HammingHeuristic());
                    }
                    if (selectedStrategyExtra.equals(MANHATTAN_HEURISTIC)) {
                        strategy = new AStarStrategy(inputState, new ManhattanHeuristic());
                    }
                    break;
                default:
                    System.out.println("Selected invalid strategy");
            }

            if (strategy == null) {
                System.out.println("strategy is null");
                return;
            }

            strategy.solve();

            saveSolutionInformation(outputSolutionFile, strategy.getSolutionInformation());
            saveExtraInformation(outputExtraFile, strategy.getExtraInformation());

            System.out.println("solved");
        } else {
            System.out.println("Passed incorrect number of arguments");
        }
    }

    private static State readInputStateFromFile(String fileName) {
        DataReader dataReader = new DataReader(fileName);
        dataReader.read();
        return dataReader.getInputState();
    }

    private static void saveSolutionInformation(String fileName, SolutionInformation solutionInformation) {
        SolutionInformationSaver saver = new SolutionInformationSaver(fileName, solutionInformation);
        saver.save();
    }

    private static void saveExtraInformation(String fileName, ExtraInformation extraInformation) {
        ExtraInformationSaver saver = new ExtraInformationSaver(fileName, extraInformation);
        saver.save();
    }
}
