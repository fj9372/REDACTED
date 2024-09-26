package Goals;

public class ImproveFitness extends GoalOption{
    public ImproveFitness(Goals goal){
        super(goal);
    }

    public double targetCalories(){
        return super.goal.targetCalories()*1.2;
    }

    public double targetCarbs(){
        return super.goal.targetCarbs() + 50;
    }

    public String goalName() {
        return super.goal.goalName() + " with Improve Fitness";
    }

}
