package Commands;

import java.util.List;

import Food.Ingredients;
import NUTRiAPP.User;

public class GetInventory implements CommandCreator {
    private User user;

    public GetInventory(User user){
        this.user = user;
    }

    @Override
    public void performAction() {
        List<Ingredients> ingrendients = user.getIngredients();
        int ingredientNum = 1;
        for(Ingredients ingredient: ingrendients){
            System.out.println(ingredientNum + ": "+ ingredient.getName());
            System.out.println("\tAmount: " + ingredient.getStock());
            ingredientNum += 1;
        }
    }

    @Override
    public String commandDescription() {
        return("See all the ingredients you have in your stock");
    }
}
    
