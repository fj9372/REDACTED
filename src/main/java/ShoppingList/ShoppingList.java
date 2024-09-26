package ShoppingList;

import java.util.List;

import Food.Ingredients;
import NUTRiAPP.User;

public class ShoppingList {

    private ListCriteria criteria;
    private User user;

    public ShoppingList(User user){
        this.user = user;
        this.criteria = new ByOverallStock();
    }

    public void setListCriteria(ListCriteria newCriteria) {
        this.criteria = newCriteria;
    }

    public String getCriteria(){
        return this.criteria.criteriaName();
    }

    public List<Ingredients> getShoppingList() throws Exception {
        return criteria.doGetList(user);
    }
}
