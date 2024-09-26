package History;

public class Snapshot {
    public UserSnapshot userSnapshot;
    public TeamsSnapshot teamsSnapshot;
    
    public Snapshot(UserSnapshot userSnapshot, TeamsSnapshot teamsSnapshot) {
        this.userSnapshot = userSnapshot;
        this.teamsSnapshot = teamsSnapshot;
    }
}