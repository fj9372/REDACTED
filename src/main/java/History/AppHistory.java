package History;

import java.util.LinkedList;
import java.util.ListIterator;

import NUTRiAPP.User;
import team.notiferTeam;

public class AppHistory {
    private LinkedList<Snapshot> history;
    private User user;
    private team.notiferTeam teams;
    
    public AppHistory(User user, notiferTeam teams) {
        this.history = new LinkedList<>();
        this.user = user;
        this.teams = teams;
    }
    
    public void storeSnapshot() {
        UserSnapshot userSnapshot = user.createSnapshot();
        TeamsSnapshot teamsSnapshot = teams.createSnapshot(user.getTeam());
        history.addFirst(new Snapshot(userSnapshot, teamsSnapshot));
    }
    
    public void restoreSnapshot(int index) {
        if (history.size() == 0) {return;}
        Snapshot snapshot = history.get(index);
        user.restoreFromSnapshot(snapshot.userSnapshot);
        teams.restoreFromSnapshot(snapshot.teamsSnapshot, user.getTeam());
        ListIterator<Snapshot> listIter = history.listIterator(index + 1);
        while (listIter.hasPrevious()) {
            listIter.previous();
            listIter.remove();
        }
    }
    
    public LinkedList<Snapshot> getSnapshots() {
        return history;
    }
    
    public void purge(int number) {
        ListIterator<Snapshot> listIter = history.listIterator(number);
        while (listIter.hasNext()) {
            history.remove(listIter.nextIndex());
        }
    }
}
