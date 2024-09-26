package team;

import History.TeamsSnapshot;
import NUTRiAPP.User;

public interface notiferTeam {
    public void notiftyInivteToTeam(String from, String to, int id) throws Exception;
    public void notifyTeamAccpeted(User user, User inviter);
    public void notifyTeamHistory(User user);
    public void nofifyTeamLogout(User user);
    public void notifyTeamChallenge(User user);
    public TeamsSnapshot createSnapshot(int teamId);
    public void restoreFromSnapshot(TeamsSnapshot snapshot, int teamId);
}
