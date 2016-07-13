import java.util.ArrayList;
import java.util.List;

public class ActionTurtle extends BasicTurtle {
    private List<Action> actionList = new ArrayList<>();

    public void addAction(Action action) {
        actionList.add(action);
    }

    @Override
    public void move (double dist) {
        super.move(dist);
        for (Action a : actionList)
            a.action(this);
    }
}
