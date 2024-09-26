package Goals;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = GainWeightSerializer.class)
public class GainWeight implements Goals{
    private double baseCalories;

    public GainWeight(double baseCalories){
        this.baseCalories = baseCalories;
    }
    public double targetCalories() {
        return (baseCalories+300);
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

    public String goalName(){
        return "Gain Weight";
    }
}
