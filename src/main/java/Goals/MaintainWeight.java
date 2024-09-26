package Goals;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = MaintainWeightSerializer.class)
public class MaintainWeight implements Goals{
    private double baseCalories;

    public MaintainWeight(double baseCalories){
        this.baseCalories = baseCalories;
    }
    public double targetCalories() {
        return (baseCalories);
    }

    public double targetFat() {
        return ((targetCalories()*0.30)/9);
    }

    public double targetProtein() {
        return ((targetCalories()*0.20*0.129598));
    }

    public double targetFiber() {
        return ((targetCalories()/1000)*14);
    }

    public double targetCarbs() {
        return ((targetCalories()*0.55)/4);
    }

    public String goalName() {
        return "Maintain Weight";
    }
}
