package Commands;

import java.util.List;
import java.util.Scanner;

import Food.Ingredients;
import Food.Recipe;
import NUTRiAPP.User;

public class CreateRecipe implements CommandCreator {
    private User user;

    public CreateRecipe(User user){
        this.user = user;
    }
    @Override
    public void performAction() {
        Scanner in = new Scanner(System.in);
        System.out.println("What would you like to name this recipe?");
        System.out.print("=> ");
        String recipeName = in.nextLine();
        System.out.println("What are the instructions to create this recipe?");
        System.out.print("=> ");
        int numIngredients = 0;
        String instruction = in.nextLine();
        try{
            int recipeCode = Recipe.insertRecipe(user.getName(), recipeName, instruction);
            if(recipeCode == 200){
                System.out.println("Recipe added! Now let's add the ingredients");
            }
            else{
                in.close();
                return;
            }
            while(true){
                System.out.println("Would like to add an ingredient to the recipe? ('Yes', 'No')");
                System.out.print("=> ");
                String response = in.nextLine();
                if(response.equals("Yes")){
                    System.out.println("What ingredient would you like to add to the inventory?");
                    System.out.print("=> ");
                    String ingredient = in.nextLine();
                    List<List<String>> temp = Ingredients.getIngredientsInfo(ingredient);
                    System.out.println("Here are all the ingredients we found!");
                    for(List<String> i: temp) {
                        System.out.println(i.get(0) + ": " + i.get(1));
                    }
                    System.out.println();
                    System.out.println("Please type the ID of the ingredient you would like to add (Enter 0 if not on the list)");
                    System.out.print("=> ");
                    int ingredientID = in.nextInt();
                    if(ingredientID == 0){
                        in.nextLine();
                        continue;
                    }
                    System.out.println("How much of this ingredient is needed?");
                    System.out.print("=> ");
                    int amount = in.nextInt();
                    in.nextLine();
                    int code = Recipe.insertRecipeIngredients(user.getName(), recipeName, ingredientID, amount);
                    if(code == 200) {
                        System.out.println("Ingredient successfully added to the recipe!");
                        numIngredients += 1;
                    }
                }
                else if(response.equals("No")) {
                    if(numIngredients == 0){
                        Recipe.deleteRecipe(user.getName(), recipeName);
                    }
                    user.getPersonalRecipe();
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
        return("Create a new Recipe");
    }
}
