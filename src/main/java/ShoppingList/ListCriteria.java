package ShoppingList;

import java.util.List;

import Food.Ingredients;
import NUTRiAPP.User;

/**
 * Defines the behavior that allows a user to create a shopping list
 * 
 * @author Max Collins
 * 
 * @param user A user who's current ingredient stock will be compared against either itself using
 * it's own quantity, or a recipe
 * @param ingList A list of the ingredients to be analyzed. From this list, a shopping list
 * will be created according to the specified algorithm
 * 
 * @return HashMap of the ingredients and the quantity the user needs to purchase
 */
public interface ListCriteria {
    public List<Ingredients> doGetList(User user) throws Exception;

    public String criteriaName();
}
