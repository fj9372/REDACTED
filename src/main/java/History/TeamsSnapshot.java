package History;

import java.util.List;

public class TeamsSnapshot {
    private List<String> teamMembers;
    
    public TeamsSnapshot(List<String> teamMembers) {
        this.teamMembers = teamMembers;
    }
    
    public List<String> getLookup() {
        return teamMembers;
    }
    
    @Override
    public String toString() {
        System.out.println("toString:");
        System.out.println(teamMembers.toString());
        return teamMembers.toString();
    }
}
