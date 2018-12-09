package engine;

import java.util.Comparator;

public class MoveDirectionComparator implements Comparator<MoveDirection> {
    private final SearchOrder searchOrder;

    MoveDirectionComparator(SearchOrder searchOrder) {
        this.searchOrder = searchOrder;
    }

    @Override
    public int compare(MoveDirection moveDirection1, MoveDirection moveDirection2) {
        String searchOrderString = searchOrder.toString();
        for(int i = 0; i < searchOrderString.length(); i++) {
            if(moveDirection1.toString().charAt(0) == searchOrderString.charAt(i))
                return -1;
            if(moveDirection2.toString().charAt(0) == searchOrderString.charAt(i))
                return 1;
        }
        return 0;
    }
}
