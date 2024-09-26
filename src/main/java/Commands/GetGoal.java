package Commands;

import Goals.GoalList;

public class GetGoal implements CommandCreator {
    private GoalList goalList;

    public GetGoal(GoalList goalList){
        this.goalList = goalList;
    }

    @Override
    public void performAction() {
        System.out.println("Your current goal is " + goalList.goalName());
    }
    @Override
    public String commandDescription() {
        return("Get your current goal");
    }
}
