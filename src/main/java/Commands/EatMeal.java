/** 
package Commands;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import Food.Ingredients;
import Food.Meal;
import NUTRiAPP.User;

public class EatMeal implements CommandCreator {
    private User user;
    private boolean hasEnough;

    public EatMeal(User user) {
        this.user = user;
        hasEnough = true;
    }

    @SuppressWarnings("unlikely-arg-type")
    @Override
    public void performAction() {
        Scanner in = new Scanner(System.in);
        System.out.println("What meal would you like to eat? (choose name)");
        int count = 1;
        for (Meal meal : user.getMeal()){
            System.err.println(count + ": " + meal.getName());
            count++;
        }
        int mealNum = in.nextInt();
        Meal object = user.getMeal().get(mealNum-1);
        
        in.close();
      
        Map<String, Integer> personal;
        try {
            //loops through add sees if there is enough stock
            personal = user.getPersonalStock(user.getName());  
            object.getRecipesNeeded().forEach(recipe -> {
                    recipe.getIngredientsNeeded().entrySet().forEach(ingredent -> {
                        if (ingredent.getValue()> personal.get(ingredent.getKey().getName())){
                            hasEnough = false;
                        };
                    });
                });
            if (hasEnough){
                object.getRecipesNeeded().forEach(recipe -> {
                    recipe.getIngredientsNeeded().entrySet().forEach(ingredent ->{
                        try {
                            user.updateInventory(Integer.parseInt(Ingredients.getIngredientsInfo(ingredent.getKey().getName()).get(0).get(0)),user.getPersonalStock(user.getName()).get(ingredent.getKey())-ingredent.getValue());
                            user.getPersonalStock();
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                          e.printStackTrace();
                        }
                    });
                });
            }
            else {
                System.out.println("You do not have enough ingridents to eat this meal");
            }
    }
    catch (Exception e) {
        e.printStackTrace();
    }}

    @Override
    public String commandDescription() {
        return ("DO THIS");
    }
}
*/
