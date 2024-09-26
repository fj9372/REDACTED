package Commands;
import java.util.Scanner;

import Goals.GoalList;
import NUTRiAPP.User;

public class SetWeight implements CommandCreator {
    private User user;
    private GoalList goalList;

    public SetWeight(User user, GoalList goalList) {
        this.user = user;
        this.goalList = goalList;
    }

    @Override
    public void performAction() {
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter your new current weight in kg");
        System.out.print("=> ");
        int s = in.nextInt();
        try{
            user.setWeight(s);
            goalList.checkGoals();
            System.out.println("Your new current has been set to " + user.getWeight());
        }catch(Exception e){
            System.out.println(e.getStackTrace());
        }
        in.close();
    }
    @Override
    public String commandDescription() {
        return("Change your current weight");
    }
}
