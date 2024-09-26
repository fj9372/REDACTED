package Commands;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Map;

import Food.Ingredients;
import Food.Meal;
import Food.Recipe;
import NUTRiAPP.User;

public class PrepareMeal implements CommandCreator {
    User user;

    public PrepareMeal(User user){
        this.user = user;
    }
    @Override
    public void performAction() {
        Scanner in = new Scanner(System.in);
        List<Meal> meals = user.getMeal();
        if(meals.isEmpty()){
            in.close();
            System.out.println("Oops! You haven't created any meals");
            return;
        }
        System.out.println("Here's a list of meals you've created");
        int num = 1;
        for(Meal meal: meals){
            System.out.println(num + ": " + meal.getName());
            num += 1;
        }
        System.out.println("Please enter the number for the meal you want to prepare");
        System.out.print("=> ");
        int mealNum = in.nextInt();
        try{
            List<String> recipes = Recipe.getMealRecipes(user.getName(), meals.get(mealNum-1).getName());
            List<Recipe> userRecipe = user.getRecipe();
            HashMap<String, Integer> userIngredients = user.getPersonalStock(user.getName());
            for(String recipe: recipes) {
                HashMap<String, Integer> ingredientsNeeded = Ingredients.getRecipeIngredients(user.getName(), recipe);
                for (Map.Entry<String, Integer> ingredient : ingredientsNeeded.entrySet()) {
                    String ingName = ingredient.getKey();
                    int ingAmount = ingredient.getValue();
                    if(!userIngredients.containsKey(ingName)){
                        System.out.println("Warning: You do not have " + ingName + " in your stock");
                    }
                    else if(userIngredients.get(ingName) < ingAmount){
                        System.out.println("Warning: You do not have enough of " + ingName + " in your stock");
                    }
                }
            }
            for(String recipe: recipes) {
                for(Recipe r: userRecipe){
                    if(r.getName().equals(recipe)){
                        System.out.println("To prepare the recipe " + recipe + " in the meal, do the following: ");
                        System.out.println("\t" + r.getPrepInstructions());
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        in.close();
    }

    @Override
    public String commandDescription() {
        return("Get the instructions for preparing a meal");
    }
}
