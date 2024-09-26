package ShoppingList;

import java.util.ArrayList;
import java.util.List;
import Food.Ingredients;
import Food.Recipe;
import NUTRiAPP.User;

public class BySpecificRecipeStock implements ListCriteria {

    @Override
    public List<Ingredients> doGetList(User user) throws Exception {
        List<Ingredients> completeList = new ArrayList<>();

        List<Recipe> userRecipe = user.getRecipe();
        List<Ingredients> userIngredients = user.getIngredients();

        for(Recipe r: userRecipe) {
            for (Ingredients ingredient: r.getIngredientsNeeded()) {
                boolean exists = false;
                for (Ingredients checkIngredients: userIngredients){
                    if(checkIngredients.getName().equals(ingredient.getName())){
                        if(checkIngredients.getStock() < ingredient.getStock()){
                            Ingredients newIngredients = checkIngredients;
                            newIngredients.setStock(ingredient.getStock()-checkIngredients.getStock());
                            completeList.add(newIngredients);
                        }
                        exists = true;
                    }
                }
                if(!exists){
                    completeList.add(ingredient);
                }
            }
        }
        return completeList;
    }
    
    public String criteriaName(){
        return "Specific";
    }
}
