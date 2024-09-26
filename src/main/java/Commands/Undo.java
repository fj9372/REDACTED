package Commands;

import History.Snapshot;
import NUTRiAPP.User;

public class Undo implements CommandCreator {
    private User user;

    public Undo(User user) {
        this.user = user;
    }
    
    @Override
    public void performAction() {
        user.getAppHistory().restoreSnapshot(0);
    }

    @Override
    public String commandDescription() {
        return "Undo the last command on user or team data";
    }
    
}
