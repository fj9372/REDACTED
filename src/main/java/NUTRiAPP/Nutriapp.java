package NUTRiAPP;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import Commands.*;
import Goals.GoalList;
import ShoppingList.ShoppingList;

public class Nutriapp {
    private Map<String, CommandCreator> commandList = new HashMap<String, CommandCreator>();
    private User user = new User();
    private GoalList userGoal;
    private ShoppingList shoppingList;
    private static boolean isDayExpired = false;

    public void StartFile() {
        userGoal = new GoalList(user);     
        shoppingList = new ShoppingList(user);
        commandList.put("create-meal", new CreateMeal(user));
        commandList.put("prepare-meal", new PrepareMeal(user));
        commandList.put("create-recipe", new CreateRecipe(user));
        commandList.put("get-history", new RetrieveHistory());
        commandList.put("get-goal", new GetGoal(userGoal));
        commandList.put("set-weight", new SetWeight(user, userGoal));
        commandList.put("attach-fitness", new AttachFitness(user, userGoal));
        commandList.put("add-to-inventory", new AddToInventory(user));
        commandList.put("get-inventory", new GetInventory(user));
        commandList.put("get-recipe", new GetRecipe(user));
        commandList.put("get-meal", new GetMeal(user));
        commandList.put("get-info", new GetUserInfo(user));

    }

    public void startUp() {
        System.out.println("""

                        .:'
                    __ :'__
                 .'`__`-'__``.
                :__________.-'
                :_________:
                 :_________`-;
                  `.__.-.__.'
                        """);
        String welcomeMessage = """
                Welcome to the NUTRiAPP(aka NUTAPP) Command Line Interface!

                Are you a new or old user? (Type \"new\" or \"old\" respectively)
                    """;
        Scanner in = new Scanner(System.in);
        System.out.println(welcomeMessage);
        boolean var = true;
        while (var){
        
        System.out.print("=> ");
        String s = "";
        
        s = in.nextLine();
        switch (s){
            case "new":{
                createNewUser(in);
                var = false;
                break;
            }
            case "old":{
                login(in);
                var = false;
                break;
            }
            default :{
                System.out.println("Invalid command! (Please type \"new\" or \"old\") ");
            }
        }}
        userCommands(in);
    }

    private void userCommands(Scanner in){
        System.out.println("How long would you like a day to be? (in seconds)");
        System.out.print("=> ");
        int dayLength = in.nextInt();
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run(){
                isDayExpired = true;
            }
        }, dayLength * 1000, dayLength * 1000);
        boolean var = true;
        in.nextLine();
        while(var){
            System.out.println();
            System.out.println("Type /help for list of commands or type \"Quit\" to quit.");
            System.out.print("=> ");
            String s = in.nextLine();
            switch (s){ // change to function calls when it gets more complicated
                case "Quit":{
                    System.out.println("Thank you for using NUTRiAPP! Now quitting........");
                    var = false;
                    break;
                }
                case "/help":{
                    System.out.println("Here is a list of available commands and their respective arguments:");
                    System.out.println();
                    for (Map.Entry<String, CommandCreator> allCommands : commandList.entrySet()) {
                        String key = allCommands.getKey();
                        String value = allCommands.getValue().commandDescription();
                        System.out.println(key + ": " + value);
                        // Do something with key and value
                    }
                    break;
                }
                default:{
                    if(commandList.get(s) != null) {
                        commandList.get(s).performAction();
                    } else{
                        System.out.println("Command does not exist! Please try again!");
                    }
                }

            }
            if(isDayExpired){
                endOfDay(in);
                isDayExpired = false;
            }
        }
        in.close();
    }
    private void endOfDay(Scanner in){
        System.out.println("It looks like you have reached the end of a day!");
        commandList.get("set-weight").performAction();
    }
    private void createNewUser(Scanner in){
        System.out.println("Let's get you setup!\n");
        System.out.print("What is your name? => ");
        String s = in.nextLine();
        String name = s;

        System.out.print("\nWhat is your height in inches? => ");
        s = in.nextLine();
        String heightString = s;
        double height = Double.parseDouble(heightString);

        System.out.print("\nWhat is your weight? => ");
        s = in.nextLine();
        String weightString = s;
        double weight = Double.parseDouble(weightString);

        System.out.print("\nWhat is your target weight? => ");
        s = in.nextLine();
        String targetWeightString = s;
        double targetWeight = Double.parseDouble(targetWeightString);

        System.out.print("\nWhat is your birthday? Please enter in YYYY/MM/DD) => " );
        s = in.nextLine();
        String birthday = s.replace("/", "-");
      
            try {
                if(user.addUser(name, "derp", height, weight, targetWeight, birthday)){
                    System.out.println("Successfully created!");
                    StartFile();
                }
                else{
                    System.out.println("User's name has been taken...restarting");
                    System.out.println();
                    startUp();
   }
            } catch (Exception e) {
                e.printStackTrace();
            }}
        private void login(Scanner in){
            System.out.println("Let's look you up!\n");
                System.out.print("What is your name? => ");
                String s = in.nextLine();
                String name = s;
                
                try{
                    if(user.userExist(name, "DERP")){
                        System.out.println("Welcome Back!");
                        StartFile();
                        return;
                    }
                    else{
                        System.out.println("User does not exist...restarting");
                        System.out.println();
                        startUp();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        
    public static void main(String args[]) throws Exception{
       new Nutriapp().startUp();

    }


}
