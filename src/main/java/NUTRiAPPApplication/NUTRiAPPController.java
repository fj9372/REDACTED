package NUTRiAPPApplication;


import java.util.ArrayList;
import java.io.File;
import java.util.List;
import java.util.logging.FileHandler;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import FileImportAndExport.CSVHandler;
import FileImportAndExport.JSONHandler;
import FileImportAndExport.XMLHandler;
import Food.Ingredients;
import Food.Meal;
import Food.Recipe;
import Goals.GoalList;
import NUTRiAPP.User;
import PersonalHistory.DailyReport;
import ShoppingList.ByOverallStock;
import ShoppingList.BySpecificRecipeStock;
import ShoppingList.ShoppingList;
import Workout.CreateHigh;
import Workout.CreateLow;
import Workout.CreateMedium;
import Workout.HighIntensity;
import Workout.LowIntensity;
import Workout.MediumIntensity;
import Workout.Workout;
import Workout.WorkoutCreator;
import team.TeamMemberNotifyerBase;


@RestController
public class NUTRiAPPController {
    private User currentUser;
    private ShoppingList shoppingList;
    private GoalList userGoal;
    private DailyReport today;

    public NUTRiAPPController(){
        currentUser = new User();
    }

    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody loginForm user) throws Exception{
        boolean isCorrect = currentUser.userExist(user.getName(), user.getPassword());
        shoppingList = new ShoppingList(currentUser);
        userGoal = new GoalList(currentUser);
        boolean dayExists = currentUser.checkHistory();
        if(!dayExists){
            DailyReport.newDay(currentUser.getName(), (int)currentUser.getWeight(), (int)userGoal.baseCalories());
            currentUser.getPersonalHistoryDB();
        }
        today = currentUser.getPersonalHistory().get(0);
        return new ResponseEntity<>(isCorrect, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Boolean> create(@RequestBody loginForm user) throws Exception{
        boolean added = currentUser.addUser(user.getName(), user.getPassword(), user.getHeight(), user.getWeight(), user.getTargetWeight(), user.getBirthday());
        shoppingList = new ShoppingList(currentUser);
        userGoal = new GoalList(currentUser);
        boolean dayExists = currentUser.checkHistory();
        if(!dayExists){
            DailyReport.newDay(currentUser.getName(), (int)currentUser.getWeight(), (int)userGoal.baseCalories());
            currentUser.getPersonalHistoryDB();
        }
        return new ResponseEntity<>(added, HttpStatus.OK);
    }

    @GetMapping("/ingredients")
    public ResponseEntity<List<Ingredients>> getIngredients() throws Exception{
        List<Ingredients> ingredients = currentUser.getIngredients();
        return new ResponseEntity<>(ingredients, HttpStatus.OK);
    }

    @PutMapping("/ingredients")
    public ResponseEntity<Boolean> updateIngredients(@RequestBody Ingredients ingredient) throws Exception{
        currentUser.getAppHistory().storeSnapshot();
        currentUser.updateInventory(ingredient.getID(), ingredient.getStock());
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/ingredients")
    public ResponseEntity<Boolean> addIngredients(@RequestBody Ingredients ingredient) throws Exception{
        currentUser.getAppHistory().storeSnapshot();
        int code = Ingredients.insertInventory(currentUser.getName(), ingredient.getID(), ingredient.getStock());
        if(code == 200){
            currentUser.getPersonalStock();
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.OK);
    }

    @GetMapping("/goal")
    public ResponseEntity<GoalList> getGoal() throws Exception{
        return new ResponseEntity<>(userGoal, HttpStatus.OK);
    }

    @PutMapping("/goal")
    public ResponseEntity<Boolean> updateGoal(@RequestBody loginForm user) throws Exception{
        currentUser.getAppHistory().storeSnapshot();
        currentUser.setWeight(user.getWeight());
        currentUser.setTargetWeight(user.getTargetWeight());
        userGoal.checkGoals();
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUser() throws Exception{
        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

    @GetMapping("/searchuser/{name}")
    public ResponseEntity<List<String>> searchUser(@PathVariable String name) throws Exception{
        List<String> userList = User.searchUser(name);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/getallusers")
    public ResponseEntity<List<String>> getAllUsers()throws Exception{
        List<String> userList = User.getAllUsers();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
    
    @GetMapping("/workout")
    public ResponseEntity<List<Workout>> getWorkout() throws Exception{
        List<Workout> workouts = currentUser.getWorkout();
        return new ResponseEntity<>(workouts, HttpStatus.OK);
    }

    @GetMapping("/workout/{name}")
    public ResponseEntity<List<Workout>> getWorkoutMember(@PathVariable String name) throws Exception{
        List<Workout> workouts = WorkoutCreator.getWorkout(name);
        return new ResponseEntity<>(workouts, HttpStatus.OK);
    }

    @PostMapping("/high")
    public ResponseEntity<Boolean> postHigh(@RequestBody HighIntensity high) throws Exception{
        currentUser.getAppHistory().storeSnapshot();
        CreateHigh createHigh = new CreateHigh();
        createHigh.createWorkout(high.getStartTime(), high.getEndTime(), currentUser.getName(), today.getID());
        currentUser.getPersonaWorkout();
        currentUser.getPersonalHistoryDB();
        currentUser.notifyWorkout();
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/medium")
    public ResponseEntity<Boolean> postMedium(@RequestBody MediumIntensity medium) throws Exception{
        currentUser.getAppHistory().storeSnapshot();
        CreateMedium createMedium = new CreateMedium();
        createMedium.createWorkout(medium.getStartTime(), medium.getEndTime(), currentUser.getName(), today.getID());
        currentUser.getPersonaWorkout();
        currentUser.getPersonalHistoryDB();
        currentUser.notifyWorkout();
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/low")
    public ResponseEntity<Boolean> postLow(@RequestBody LowIntensity low) throws Exception{
        currentUser.getAppHistory().storeSnapshot();
        CreateLow createLow = new CreateLow();
        createLow.createWorkout(low.getStartTime(), low.getEndTime(), currentUser.getName(), today.getID());
        currentUser.getPersonaWorkout();
        currentUser.getPersonalHistoryDB();
        currentUser.notifyWorkout();
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/ingredient/{ingredient}")
    public ResponseEntity<List<List<String>>> searchIngredient(@PathVariable String ingredient) throws Exception{
        List<List<String>> ingredients = Ingredients.getIngredientsInfo(ingredient);
        return new ResponseEntity<>(ingredients, HttpStatus.OK);
    }

    @GetMapping("/recipe")
    public ResponseEntity<List<Recipe>> getRecipe() throws Exception{
        List<Recipe> recipes = currentUser.getRecipe();
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @PostMapping("/recipe")
    public ResponseEntity<Boolean> addRecipe(@RequestBody Recipe recipe) throws Exception{
        currentUser.getAppHistory().storeSnapshot();
        Recipe.insertRecipe(currentUser.getName(), recipe.getName(), recipe.getPrepInstructions());
        for(Ingredients ing: recipe.getIngredientsNeeded()){
            Recipe.insertRecipeIngredients(currentUser.getName(), recipe.getName(), ing.getID(), ing.getStock());
        }
        currentUser.getPersonalRecipe();
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/recipe/{name}")
    public ResponseEntity<List<Recipe>> getUserRecipe(@PathVariable String name) throws Exception{
        List<Recipe> recipes = Recipe.getUserRecipe(name);
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }


    @GetMapping("/inventory/{name}")
    public ResponseEntity<List<Ingredients>> getUserInventory(@PathVariable String name) throws Exception{
        List<Ingredients> ingredients = Ingredients.getUserIngredients(name);
        return new ResponseEntity<>(ingredients, HttpStatus.OK);
    }

    @GetMapping("/meal/{name}")
    public ResponseEntity<List<Meal>> getUserMeal(@PathVariable String name) throws Exception{
        List<Meal> meals = Meal.getUserMeal(name);
        return new ResponseEntity<>(meals, HttpStatus.OK);
    }

    @GetMapping("/meal")
    public ResponseEntity<List<Meal>> getMeal() throws Exception{
        List<Meal> meals = currentUser.getMeal();
        return new ResponseEntity<>(meals, HttpStatus.OK);
    }

    @PostMapping("/meal")
    public ResponseEntity<Boolean> addMeal(@RequestBody Meal meal) throws Exception{
        currentUser.getAppHistory().storeSnapshot();
        Meal.insertMeal(currentUser.getName(), meal.getName());
        for(Recipe recipe: meal.getRecipesNeeded()){
            Meal.insertMealRecipes(currentUser.getName(), meal.getName(), recipe.getName());
        }
        currentUser.getPersonalMeal();
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<List<DailyReport>> getHistory() throws Exception{
        List<DailyReport> history = currentUser.getPersonalHistory();
        return new ResponseEntity<>(history, HttpStatus.OK);
    }

    @GetMapping("/shopping")
    public ResponseEntity<List<Ingredients>> getShoppingList() throws Exception{
        return new ResponseEntity<>(shoppingList.getShoppingList(), HttpStatus.OK);
    }

    @PutMapping("/shopping")
    public ResponseEntity<Boolean> changeShoppingList(@RequestBody String criteria) throws Exception{
        if(criteria.equals("\"Overall Stock\"")){
            BySpecificRecipeStock newCriteria = new BySpecificRecipeStock();
            shoppingList.setListCriteria(newCriteria);
        }
        else {
            ByOverallStock newCriteria = new ByOverallStock();
            shoppingList.setListCriteria(newCriteria);
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/historymeal")
    public ResponseEntity<Boolean> addMealHistory(@RequestBody Meal meal) throws Exception{
        DailyReport.addMealHistory(currentUser.getName(), meal.getName(), today.getID(), meal.getCalories());
        for(Recipe recipes: meal.getRecipesNeeded()){
            for(Ingredients ingredient: recipes.getIngredientsNeeded()){
                for(Ingredients userIngredients: currentUser.getIngredients()){
                    if(ingredient.getID() == userIngredients.getID()){
                        int newAmount = userIngredients.getStock() - ingredient.getStock();
                        if(newAmount < 0){
                            newAmount = 0;
                        }
                        currentUser.updateInventory(ingredient.getID(), newAmount);
                    }
                }
            }
        }
        currentUser.getPersonalStock();
        currentUser.getPersonalHistoryDB();
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/team")
    public ResponseEntity<List<String>> getTeam() throws Exception{
        List<String> members = TeamMemberNotifyerBase.getTeamMembers(currentUser.getTeam());
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @DeleteMapping("/team")
    public ResponseEntity<Boolean> leaveTeam() throws Exception{
        TeamMemberNotifyerBase.leaveTeam(currentUser.getName());
        currentUser.getTeamNumber();
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/team")
    public ResponseEntity<Boolean> createTeam(@RequestBody String name) throws Exception{
        TeamMemberNotifyerBase.createTeam(currentUser.getName(), name.substring(1, name.length()-1));
        currentUser.getTeamNumber();
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/undo")
    public ResponseEntity<Boolean> undo() throws Exception {
        currentUser.getAppHistory().restoreSnapshot(0);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/teamuser")
    public ResponseEntity<Boolean> inviteMember(@RequestBody String name) throws Exception {
        currentUser.getAppHistory().storeSnapshot();
        currentUser.inviteUser(name.substring(1, name.length()-1));
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
  
    @GetMapping("/notification")
    public ResponseEntity<List<String>> notifi() throws Exception {
        List<String> result = currentUser.getNotif();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/export/{type}")
    public ResponseEntity<Boolean> exportFiles(@PathVariable String type) throws Exception{
        String userHome = System.getProperty("user.home");
        String downloadsDirectory = userHome + File.separator + "Downloads";
        if(type.equals("csv")){
            CSVHandler fileHandler = new CSVHandler();
            fileHandler.exportFile(currentUser, downloadsDirectory);
        }
        else if(type.equals("xml")){
            XMLHandler fileHandler = new XMLHandler();
            fileHandler.exportFile(currentUser, downloadsDirectory);
        }
        else if(type.equals("json")){
            JSONHandler fileHandler = new JSONHandler();
            fileHandler.exportFile(currentUser, downloadsDirectory);
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/challenge")
    public ResponseEntity<List<String>> getChallenge() throws Exception {
        List<String> result = TeamMemberNotifyerBase.getChallengeRanking(currentUser.getTeam());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/challenge")
    public ResponseEntity<Boolean> createChallenge() throws Exception {
        User.createChallenge(currentUser.getTeam());
        currentUser.issueChallenefe();
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}


