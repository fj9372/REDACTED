package NUTRiAPP;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import Food.Ingredients;
import Food.Meal;
import Food.Recipe;
import PersonalHistory.DailyReport;
import Workout.CreateHigh;
import Workout.CreateLow;
import Workout.CreateMedium;
import Workout.Workout;
import team.TeamMemberNotifyerBase;
import team.notiferTeam;
import team.userTeamFunctionailty;
import History.AppHistory;
import History.Snapshot;
import History.UserSnapshot;

import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class User implements userTeamFunctionailty{
    private String name;
    private String password;
    private double height; 
    private double weight;
    private double targetWeight;
    private String birthday;
    private List<Ingredients> ingredients;
    private List<Meal> meal;
    private List<Recipe> recipe;
    private List<Workout> workout;
    private int teamNumber;


    private List<DailyReport> personalHistory; 

    private notiferTeam team;
    private AppHistory appHistory;
    
    public User (){
        this.team = new TeamMemberNotifyerBase();
        appHistory = new AppHistory(this, this.team);
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public double getTargetWeight() {
        return targetWeight;
    }

    public String getBirthday() {
        return birthday;
    }

    public int getTeam() {
        return teamNumber;
    }

    public int getAge() {
        DateTimeFormatter newFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthdateConverted = LocalDate.parse(birthday, newFormat);
        LocalDate currentDate = LocalDate.now();
        int age = Period.between(birthdateConverted,currentDate).getYears();
        return age;
    }
    
    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public List<Recipe> getRecipe(){
        return recipe;
    }

    public List<Meal> getMeal(){
        return meal;
    }

    public List<Workout> getWorkout(){
        return workout;
    }

    public List<DailyReport> getPersonalHistory(){
        return personalHistory;
    }

    public void setName(String name) throws Exception{
        String data = "{\n"+
        "\"oldName\": \"" + this.name + "\",\n" +
        "\"name\": \"" + name + "\"\n" +
        "}";
        UpdateUser(data);
        this.name = name;
    }

    public void setPassword(String password) throws Exception{
        this.password = password;
    }

    public void setHeight(double height) throws Exception{
        String data = "{\n"+
        "\"oldName\": \"" + this.name + "\",\n" +
        "\"height\": " + height + "\n" +
        "}";
        UpdateUser(data);
        this.height = height;
    }

    public void setWeight(double weight) throws Exception{
        String data = "{\n"+
        "\"oldName\": \"" + this.name + "\",\n" +
        "\"weight\": " + weight + "\n" +
        "}";
        UpdateUser(data);
        this.weight = weight;
    }

    public void setTargetWeight(double weight) throws Exception{
        String data = "{\n"+
        "\"oldName\": \"" + this.name + "\",\n" +
        "\"targetWeight\": " + weight + "\n" +
        "}";
        UpdateUser(data);
        this.targetWeight = weight;
    }

    public void setBirthday(String birthday) throws Exception{
        String data = "{\n"+
        "\"oldName\": \"" + this.name + "\",\n" +
        "\"birthday\": \"" + birthday + "\"\n" +
        "}";
        UpdateUser(data);
        this.birthday = birthday;
    }

    public boolean userExist(String name, String password) throws Exception{
        String newUrl = "http://localhost:5000/users/" + name + "/" + password;
        URL url = new URL(newUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("name", name);
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String data;
        List<String> userData = new ArrayList<>();
        while((data=in.readLine()) != null){
            if(!data.trim().equals("[") && !data.trim().equals("]")){
                String newData = data.replace(",","").replace("\"","").trim();
                userData.add(newData.trim());
            }
        }
        in.close();
        if(userData.get(0).equals("[]")){
            return false;
        }
        this.name = userData.get(0);
        this.height = Double.valueOf(userData.get(1));
        this.weight = Double.valueOf(userData.get(2));
        this.targetWeight = Double.valueOf(userData.get(3));
        this.birthday = userData.get(4);
        this.appHistory = new AppHistory(this, this.team);
        getPersonalStock();
        getPersonalRecipe();
        getPersonalMeal();
        getPersonaWorkout();
        getPersonalHistory();
        getTeamNumber();
        getPersonalHistoryDB();
        return true;
    }

    public boolean addUser(String name, String password, double height, double weight, double targetWeight, String birthday) throws Exception{
        URL newUrl = new URL("http://localhost:5000/users");
        HttpURLConnection conn = (HttpURLConnection) newUrl.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        OutputStream outputStream = conn.getOutputStream();
        String postData = "{\n"+
                "\"name\": \"" + name + "\",\n" +
                "\"password\": \"" + password + "\",\n" +
                "\"height\": " + height + ",\n" +
                "\"weight\": " + weight + ",\n" +
                "\"targetWeight\": " + targetWeight + ",\n" +
                "\"birthday\": \"" + birthday + "\"\n" +
                "}";
        outputStream.write(postData.getBytes());
        outputStream.flush();
        outputStream.close();
        int code = conn.getResponseCode();
        if(code != 200){
            return false;
        }
        conn.disconnect();
        userExist(name, password);
        return true;
    }

    public void UpdateUser(String data) throws Exception {
        URL newUrl = new URL("http://localhost:5000/users");
        HttpURLConnection conn = (HttpURLConnection) newUrl.openConnection();
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        OutputStream outputStream = conn.getOutputStream();
        outputStream.write(data.getBytes());
        outputStream.flush();
        outputStream.close();
        int code = conn.getResponseCode();
        if(code != 200){
            return;
        }
        conn.disconnect();
    }

    //mess around later for shopping cart
    public HashMap<String, Integer> getPersonalStock(String name) throws Exception{
        URL newUrl = new URL("http://localhost:5000/inventory/" + name);
        HttpURLConnection conn = (HttpURLConnection) newUrl.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String data;
        HashMap<String, Integer> invData = new HashMap<>();
		while((data=in.readLine()) != null){
            if(!data.trim().contains("[") && !data.trim().contains("]")){
                String ingredient = data.replace("\"","").trim();
                if(ingredient.endsWith(",")){
                    ingredient = ingredient.substring(0, ingredient.length()-1);
                }                
                Integer amount = Integer.parseInt(in.readLine().trim());
                invData.put(ingredient, amount);
                List<List<String>> listIngredients = Ingredients.getIngredientsInfo(ingredient);
                List<String> info = listIngredients.get(0);
                Ingredients temp = new Ingredients(Integer.parseInt(info.get(0)),amount, info.get(1), Integer.parseInt(info.get(2)), Double.parseDouble(info.get(3)), Double.parseDouble(info.get(4)),Double.parseDouble(info.get(5)), Double.parseDouble(info.get(6)), info.get(7));
                ingredients.add(temp);
            }
        }
        return invData;
    }

    //mess around later for shopping cart
    public void getPersonalStock() throws Exception{
        ingredients = new ArrayList<>();
        URL newUrl = new URL("http://localhost:5000/inventory/" + name);
        HttpURLConnection conn = (HttpURLConnection) newUrl.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String data;
		while((data=in.readLine()) != null){
            if(!data.trim().contains("[") && !data.trim().contains("]")){
                String ingredient = data.replace("\"","").trim();
                if(ingredient.endsWith(",")){
                    ingredient = ingredient.substring(0, ingredient.length()-1);
                }                
                Integer amount = Integer.parseInt(in.readLine().trim());
                List<List<String>> listIngredients = Ingredients.getIngredientsInfo(ingredient);
                List<String> info = listIngredients.get(0);
                Ingredients temp = new Ingredients(Integer.parseInt(info.get(0)),amount, info.get(1), Integer.parseInt(info.get(2)), Double.parseDouble(info.get(3)), Double.parseDouble(info.get(4)),Double.parseDouble(info.get(5)), Double.parseDouble(info.get(6)), info.get(7));
                ingredients.add(temp);
            }
        }
    }

    public void getPersonalRecipe() throws Exception {
        recipe = new ArrayList<>();
        String newUrl = "http://localhost:5000/recipe/" + name;
        URL url = new URL(newUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String data;
        while((data=in.readLine()) != null){
            if(!data.trim().contains("[") && !data.trim().contains("]")){
                String recipeName = data.replace("\"","").trim();
                if(recipeName.endsWith(",")){
                    recipeName = recipeName.substring(0, recipeName.length()-1);
                }
                String instruction = in.readLine().trim();
                Recipe temp = new Recipe(instruction, recipeName);
                HashMap<String, Integer> tempMap = Ingredients.getRecipeIngredients(name, recipeName);
                for (Map.Entry<String, Integer> recipeIng : tempMap.entrySet()) {
                    List<List<String>> listIngredients = Ingredients.getIngredientsInfo(recipeIng.getKey());
                    if (listIngredients.size() != 0) {
                        List<String> info = listIngredients.get(0);
                        Ingredients tempIng = new Ingredients(Integer.parseInt(info.get(0)),recipeIng.getValue(), info.get(1), Integer.parseInt(info.get(2)), Double.parseDouble(info.get(3)), Double.parseDouble(info.get(4)),Double.parseDouble(info.get(5)), Double.parseDouble(info.get(6)), info.get(7));
                        temp.addingredient(tempIng);
                    }
                }
                recipe.add(temp);
            }
        }
        in.close();
    }

    public void getPersonalHistoryDB() throws Exception {
        this.personalHistory = new ArrayList<>();
        URL newUrl = new URL("http://localhost:5000/history/" + name);
        HttpURLConnection conn = (HttpURLConnection) newUrl.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String data;
        while((data=in.readLine()) != null){
            if(!data.trim().contains("[") && !data.trim().contains("]")){
                int id = Integer.parseInt(data.substring(0, data.length()-1).trim());
                String weightLine = in.readLine().trim();
                int weight = Integer.parseInt(weightLine.substring(0, weightLine.length()-1));
                String caloriesConsumedLine = in.readLine().trim();
                int caloriesConsumed = Integer.parseInt(caloriesConsumedLine.substring(0, caloriesConsumedLine.length()-1));
                String caloriesTargetLine = in.readLine().trim();
                int caloriesTarget = Integer.parseInt(caloriesTargetLine.substring(0, caloriesTargetLine.length()-1));
                String date = in.readLine().trim();
                date = date.substring(1, date.length()-1);
                DailyReport report = new DailyReport(id, weight, caloriesConsumed, caloriesTarget, date);
                List<Workout> workouts = DailyReport.getHistoryWorkouts(id);
                List<Meal> meals = DailyReport.getHistoryMeals(id, name, recipe);
                report.setWorkouts(workouts);
                report.setMeals(meals);
                this.personalHistory.add(report);
            }
        }
    }

    public void getPersonalMeal() throws Exception {
        meal = new ArrayList<>();
        String newUrl = "http://localhost:5000/meal/" + name; //user's name 
        URL url = new URL(newUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String data;
        while((data=in.readLine()) != null){
            if(!data.trim().contains("[") && !data.trim().contains("]")){
                String newData = data.replace(",","").replace("\"","").trim();
                Meal newmeal = new Meal(newData);
                List<String> mealRecipe = Recipe.getMealRecipes(name, newData);
                for(Recipe r: recipe){
                    if(mealRecipe.contains(r.getName())){
                        newmeal.addRecipe(r);
                    }
                }
                meal.add(newmeal);
            }
        }
        in.close();
    }

    public void getTeamNumber() throws Exception {
        String newUrl = "http://localhost:5000/team/" + name; //user's name 
        URL url = new URL(newUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String data;
        Boolean hasTeam = false;
        while((data=in.readLine()) != null){
            if(!data.trim().contains("[") && !data.trim().contains("]")){
                String newData = data.replace(",","").replace("\"","").trim();
                teamNumber = Integer.parseInt(newData);
                hasTeam = true;
            }
        }
        if(!hasTeam){
            teamNumber = 0;
        }
        in.close();
    }

    public void updateInventory(int id, int amount) throws Exception{
        URL newUrl = new URL("http://localhost:5000/inventory");
        HttpURLConnection conn = (HttpURLConnection) newUrl.openConnection();
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        OutputStream outputStream = conn.getOutputStream();
        String data = "{\n"+
            "\"name\": \"" + name + "\",\n" +
            "\"ingredientID\": " + id + ",\n" +
            "\"amount\": \"" + amount + "\"\n" +
            "}";
        outputStream.write(data.getBytes());
        outputStream.flush();
        outputStream.close();
        conn.getResponseCode();
        getPersonalStock();
    }

  
    public void getPersonaWorkout() throws Exception {
        workout = new ArrayList<>();
        String newUrl = "http://localhost:5000/workout/" + name; //user's name 
        URL url = new URL(newUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String data;
        while((data=in.readLine()) != null){
            if(!data.trim().contains("[") && !data.trim().contains("]")){
                String newData = data.replace(",","").replace("\"","").trim();
                String startTime = in.readLine().replace(",","").replace("\"","").trim();
                String endTime = in.readLine().replace(",","").replace("\"","").trim();
                Workout newWorkout;
                if(newData.equals("high")){
                    CreateHigh createHigh = new CreateHigh();
                    newWorkout = createHigh.createWorkout(startTime, endTime);
                }
                else if(newData.equals("medium")){
                    CreateMedium createMedium = new CreateMedium();
                    newWorkout = createMedium.createWorkout(startTime, endTime);
                }
                else{
                    CreateLow createLow = new CreateLow();
                    newWorkout = createLow.createWorkout(startTime, endTime);
                }
                workout.add(newWorkout);
            }
        }
        in.close();
    }

    public boolean checkHistory() throws Exception{
        if(personalHistory == null || personalHistory.size() == 0){
            return false;
        }
        DailyReport check = this.personalHistory.get(0);
        String date = check.getDate().split(" ")[0];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate checkDate = LocalDate.parse(date, formatter);
        LocalDate today = LocalDate.now();
        return checkDate.equals(today);
    }
      
    @Override
    public void inviteUser(String user) throws Exception {
        String message = name + " has invited you to their team";
        team.notiftyInivteToTeam(message, user, teamNumber);
    }

    @Override
    public void issueChallenefe() {
        team.notifyTeamChallenge(this);
    }

    @Override
    public void notifyWorkout() {
        team.notifyTeamHistory(this);
    }

    @Override
    public void logout() {
        team.nofifyTeamLogout(this);
    }
 
    public UserSnapshot createSnapshot() {
        return new UserSnapshot(this.meal, this.recipe, this.ingredients, this.weight, this.targetWeight);
    }
    
    public void restoreFromSnapshot(UserSnapshot snapshot) {
        this.meal = snapshot.getMeals();
        this.recipe = snapshot.getRecipes();
        this.ingredients = snapshot.getIngredients();
        this.weight = snapshot.getWeight();
        this.targetWeight = snapshot.getTargetWeight();
        try {
            syncInventory();
        } catch(Exception e) {
            System.err.println(e);
        }
    }
    
    public AppHistory getAppHistory() {
        return appHistory;
    }
    
    public void syncInventory() throws Exception {
        UserSnapshot snapshot = new UserSnapshot(this.meal, this.recipe, this.ingredients, this.weight, this.targetWeight);
        URL newUrl = new URL("http://localhost:5000/sync/inventory/" + this.name);
        HttpURLConnection conn = (HttpURLConnection) newUrl.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        OutputStream outputStream = conn.getOutputStream();
        String data = snapshot.toString();
        outputStream.write(data.getBytes());
        outputStream.flush();
        outputStream.close();
        conn.getResponseCode();
    }

    public static List<String> searchUser(String name) throws Exception{
        String newUrl = "http://localhost:5000/searchuser/" + name;
        URL url = new URL(newUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("name", name);
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String data;
        List<String> userData = new ArrayList<>();
        while((data=in.readLine()) != null){
            if(!data.trim().contains("[") && !data.trim().contains("]")){
                String newData = data.replace(",","").replace("\"","").trim();
                userData.add(newData.trim());
            }
        }
        in.close();
        return userData;
    }

    public List<String> getNotif() throws Exception{
        List<String> notifs = new ArrayList<>();
        URL newUrl = new URL("http://localhost:5000/notification/" + name);
        HttpURLConnection conn = (HttpURLConnection) newUrl.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String data;
        while((data=in.readLine()) != null){
            if(!data.trim().contains("[") && !data.trim().contains("]")){
                String notif = data.replace("\"","").trim();
                notifs.add(notif);
            }
        }
        return notifs;
    }

    public static List<String> getAllUsers() throws Exception{
        String newUrl = "http://localhost:5000/getallusers";
        URL url = new URL(newUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String data;
        List<String> userData = new ArrayList<>();
        while((data=in.readLine()) != null){
            if(!data.trim().contains("[") && !data.trim().contains("]")){
                String newData = data.replace(",","").replace("\"","").trim();
                userData.add(newData.trim());
            }
        }
        in.close();
        return userData;
    }

    public static void createChallenge(int teamNumber) throws Exception{
        URL newUrl = new URL("http://localhost:5000/challenge");
        HttpURLConnection conn = (HttpURLConnection) newUrl.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        OutputStream outputStream = conn.getOutputStream();
        String postData = "{\n"+
                "\"teamID\": " + teamNumber + "\n" +
                "}";
        outputStream.write(postData.getBytes());
        outputStream.flush();
        outputStream.close();
        int code = conn.getResponseCode();
    }
}
