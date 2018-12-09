package strategies;

import writer.ExtraInformation;
import writer.SolutionInformation;

public interface PuzzleSolver {
    void solve();

    ExtraInformation getExtraInformation();

    SolutionInformation getSolutionInformation();
}
