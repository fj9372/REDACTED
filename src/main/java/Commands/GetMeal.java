package Commands;

import java.util.List;

import Food.Meal;
import NUTRiAPP.User;

public class GetMeal implements CommandCreator {
    private User user;

    public GetMeal(User user){
        this.user = user;
    }

    @Override
    public void performAction() {
        List<Meal> meals = user.getMeal();
        int mealNum = 1;
        for(Meal meal: meals){
            System.out.println(mealNum + ": "+ meal.getName());
            mealNum += 1;
        }
    }

    @Override
    public String commandDescription() {
        return("See all the Meals you have created");
    }
}
    
