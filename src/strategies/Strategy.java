package strategies;

import writer.ExtraInformation;
import writer.SolutionInformation;

public interface Strategy {
    void solve();

    ExtraInformation getExtraInformation();

    SolutionInformation getSolutionInformation();
}
