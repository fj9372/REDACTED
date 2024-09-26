package Goals;

public abstract class GoalOption implements Goals{
    protected Goals goal;

    public GoalOption(Goals goal){
        this.goal = goal;
    }

    public double targetCalories() {
        return goal.targetCalories();
    }

    public double targetFat() {
        return goal.targetFat();
    }

    public double targetProtein() {
        return goal.targetProtein();
    }

    public double targetFiber() {
        return goal.targetFiber();
    }

    public double targetCarbs() {
        return goal.targetCarbs();
    }

    public String goalName() {
        return goal.goalName();
    }
}
