package ShoppingList;

import java.util.ArrayList;
import java.util.List;

import Food.Ingredients;
import NUTRiAPP.User;

public class ByOverallStock implements ListCriteria{
    @Override
    public List<Ingredients> doGetList(User user) throws Exception {
        List<Ingredients> completeList = new ArrayList<>();
        List<Ingredients> userInv = user.getIngredients();
        for(Ingredients ing: userInv) {
            int quan = ing.getStock();
            if(quan < 3) {
                Ingredients newIngredients = ing;
                completeList.add(newIngredients);
            } 
        }
        return completeList;
    }    
    public String criteriaName(){
        return "Overall";
    }
}
