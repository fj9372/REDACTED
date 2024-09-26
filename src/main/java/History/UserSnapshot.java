package History;

import java.util.List;

import Food.Ingredients;
import Food.Meal;
import Food.Recipe;

public class UserSnapshot {
    private List<Meal> meals;
    private List<Recipe> recipies;
    private List<Ingredients> ingredients;
    private double weight;
    private double targetWeight;
    
    public UserSnapshot(List<Meal> meals, List<Recipe> recipies, List<Ingredients> ingredients, double weight, double targetWeight) {
        this.meals = meals;
        this.recipies = recipies;
        this.ingredients = ingredients;
        this.weight = weight;
        this.targetWeight = targetWeight;
    }
    
    public List<Meal> getMeals() {
        return meals;
    }
    
    public List<Recipe> getRecipes() {
        return recipies;
    }
    
    public List<Ingredients> getIngredients() {
        return ingredients;
    }
    
    public double getWeight() {
        return weight;
    }
    
    public double getTargetWeight() {
        return targetWeight;
    }
    
    @Override
    public String toString() {
        String str = """
                {
                    "meals": %s,
                    "recipies": %s,
                    "ingredients": %s,
                    "weight": %s,
                    "targetWeight": %s
                }
                """;
        str = String.format(str, meals, recipies, ingredients, weight, targetWeight);
        return str;
    }
}
