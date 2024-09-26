package Commands;

import NUTRiAPP.User;

public class GetUserInfo implements CommandCreator{
    private User user;

    public GetUserInfo(User user){
        this.user = user;
    }
    @Override
    public void performAction() {
        System.out.println("Name: " + user.getName());
        System.out.println("Height: " + user.getHeight());
        System.out.println("Current weight: " + user.getWeight());
        System.out.println("Target weight: " + user.getTargetWeight());
        System.out.println("Birthday: " + user.getBirthday());
        System.out.println("Age: " + user.getAge());
    }

    @Override
    public String commandDescription() {
        return("Get information about yourself");
    }
    
}
