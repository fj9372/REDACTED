package Commands;

import java.util.List;
import java.util.Scanner;

import Food.Ingredients;
import NUTRiAPP.User;

public class AddToInventory implements CommandCreator {
    private User user;

    public AddToInventory(User user){
        this.user = user;
    }

    @Override
    public void performAction() {
        Scanner in = new Scanner(System.in);
        System.out.println("What ingredient would you like to add to the inventory? (Enter 'Quit' to go back)");
        System.out.print("=> ");
        String ingredient = in.nextLine();
        if(ingredient.equals("Quit")) {
            in.close();
            return;
        }
        try{
            List<List<String>> temp = Ingredients.getIngredientsInfo(ingredient);
            System.out.println("Here are all the ingredients we found!");
            for(List<String> i: temp) {
                System.out.println(i.get(0) + ": " + i.get(1));
            }
            System.out.println();
            System.out.println("Please type the ID of the ingredient you would like to add (Enter 0 if not on the list)");
            System.out.print("=> ");
            int ingredientID = in.nextInt();
            if(ingredientID == 0)
                performAction();
            System.out.println("How much of this ingredient do you have?");
            System.out.print("=> ");
            int amount = in.nextInt();
            int code = Ingredients.insertInventory(user.getName(), ingredientID, amount);
            if(code == 200){
                user.getPersonalStock();
                System.out.println("Ingredient added succesfully");
            }
            in.nextLine();
        } catch (Exception e){
            e.printStackTrace();
        }
        in.close();
    }

    @Override
    public String commandDescription() {
        return("Add a new ingredient to your inventory");
    }
}
