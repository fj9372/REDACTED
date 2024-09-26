package Commands;

import java.util.List;
import java.util.Scanner;

import Food.Meal;
import Food.Recipe;
import NUTRiAPP.User;

public class CreateMeal implements CommandCreator {
    private User user;

    public CreateMeal(User user){
        this.user = user;
    }

    @Override
    public void performAction() {
        Scanner in = new Scanner(System.in);
        System.out.println("What would you like to name this meal?");
        System.out.print("=> ");
        String mealName = in.nextLine();
        int recipeNum = 0;
        try{
            int mealCode = Meal.insertMeal(user.getName(), mealName);
            if(mealCode == 200){
                System.out.println("Meal added! Now let's add the recipes");
            }
            else{
                in.close();
                return;
            }
            while(true){
                System.out.println("Would like to add a recipe to the meal? ('Yes', 'No')");
                System.out.print("=> ");
                String response = in.nextLine();
                if(response.equals("Yes")){
                    System.out.println("Here are all the recipes you have");
                    List<Recipe> userRecipe = user.getRecipe();
                    int number = 1;
                    for(Recipe r: userRecipe){
                        System.out.println(number + ": "+ r.getName());
                        number += 1;
                    }
                    System.out.println("Please type number of the recipe you want to add (Enter 0 to go back)");
                    System.out.print("=> ");
                    int recipeID = in.nextInt();
                    if(recipeID == 0){
                        in.nextLine();
                        continue;
                    }
                    int code = Meal.insertMealRecipes(user.getName(), mealName, userRecipe.get(recipeID-1).getName());
                    if(code == 200) {
                        System.out.println("Recipe successfully added to the meal!");
                        in.nextLine();
                        recipeNum += 1;
                    }
                }
                else if(response.equals("No")) {
                    if(recipeNum == 0){
                        Meal.deleteMeal(user.getName(), mealName);
                    }
                    user.getPersonalMeal();
                    break;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        in.close();
    }

    @Override
    public String commandDescription() {
        return("Create a Meal with Recipes you've made");
    }
}
    
