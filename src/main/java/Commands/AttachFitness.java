package Commands;

import Goals.GoalList;
import Goals.Goals;
import Goals.ImproveFitness;
import NUTRiAPP.User;

public class AttachFitness implements CommandCreator {
    private GoalList goalList;

    public AttachFitness(User user, GoalList goalList) {
        this.goalList = goalList;
    }

    @Override
    public void performAction() {
        Goals currentGoal = goalList.getGoal();
        ImproveFitness newGoal = new ImproveFitness(currentGoal);
        goalList.setGoal(newGoal);
        System.out.println("Improve Fitness successfully attached");
    }

    @Override
    public String commandDescription() {
        return("Attach Improve Fitness to current goal");
    }
}
