package Goals;
import NUTRiAPP.User;

public class GoalList implements Goals{
    private Goals goal;
    private User user;

    public GoalList(User user){
        this.user = user;
        checkGoals();
    }

    public double targetCalories(){
        return goal.targetCalories();
    }

    public double targetFat(){
        return goal.targetFat();
    }

    public double targetProtein(){
        return goal.targetProtein();
    }

    public double targetFiber(){
        return goal.targetFiber();
    }

    public double targetCarbs(){
        return goal.targetCarbs();
    }

    public String goalName(){
        return goal.goalName();
    }

    public Goals getGoal() {
        return this.goal;
    }

    public void checkGoals(){
        double baseCalories = baseCalories();
        if(user.getWeight() > user.getTargetWeight() + 5){
            Goals newGoal = new LoseWeight(baseCalories);
            this.setGoal(newGoal);
        }
        else if(user.getWeight() < user.getTargetWeight() - 5){
            Goals newGoal = new GainWeight(baseCalories);
            this.setGoal(newGoal);
        }
        else {
            Goals newGoal = new MaintainWeight(baseCalories);
            this.setGoal(newGoal);
        }
    }
    public void setGoal(Goals goal){
        this.goal = goal;
    }

    public double baseCalories(){
        double weight = user.getWeight();
        double height = user.getHeight();
        int age = user.getAge();
        return (88.362 + 13.397*weight + 4.799*height - 5.677*age);
    }
}
