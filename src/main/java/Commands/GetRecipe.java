package Commands;

import java.util.List;

import Food.Recipe;
import NUTRiAPP.User;

public class GetRecipe implements CommandCreator {
    private User user;

    public GetRecipe(User user){
        this.user = user;
    }

    @Override
    public void performAction() {
        List<Recipe> recipes = user.getRecipe();
        int recipeNum = 1;
        for(Recipe recipe: recipes){
            System.out.println(recipeNum + ": "+ recipe.getName());
            recipeNum += 1;
        }
    }

    @Override
    public String commandDescription() {
        return("See all the Recipes you have created");
    }
}
    
