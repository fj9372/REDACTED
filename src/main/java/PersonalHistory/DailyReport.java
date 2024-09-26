package PersonalHistory;

import java.util.ArrayList;
import java.util.List;

import Food.Meal;
import Food.Recipe;
import Workout.CreateHigh;
import Workout.CreateLow;
import Workout.CreateMedium;
import Workout.Workout;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DailyReport {
    private int id;
    private int weight;
    private int caloriesConsumed;
    private int caloriesTarget;
    private String date;
    private List<Workout> workouts;
    private List<Meal> meals;
    
    public DailyReport() {
        caloriesConsumed = 0;
        caloriesTarget = 0;
        workouts = new ArrayList<>();
        meals = new ArrayList<>();
    }

    public DailyReport(int id, int weight, int caloriesConsumed, int caloriesTarget, String date) {
        this.id = id;
        this.weight = weight;
        this.caloriesConsumed = caloriesConsumed;
        this.caloriesTarget = caloriesTarget;
        this.date = date;
        workouts = new ArrayList<>();
        meals = new ArrayList<>();
    }

    public int getID(){
        return id;
    }

    public int getWeight(){
        return weight;
    }

    public int getCaloriesConsumed(){
        return caloriesConsumed;
    }

    public int getCaloriesTarget(){
        return caloriesTarget;
    }

    public String getDate(){
        return date;
    }

    public List<Workout> getWorkouts(){
        return workouts;
    }

    public List<Meal> getMeals(){
        return meals;
    }

    public void setWeight(int weight){
        this.weight = weight;
    }

    public void setCaloriesConsumed(int caloriesConsumed){
        this.caloriesConsumed = caloriesConsumed;
    }

    public void setCaloriesTarget(int caloriesTarget){
        this.caloriesTarget = caloriesTarget;
    }

    public void setDate(String date){
        this.date = date;
    }

    public void setWorkouts(List<Workout> workouts){
        this.workouts = workouts;
    }

    public void setMeals(List<Meal> meals){
        this.meals = meals;
    }

    public void addWorkouts(Workout workout){
        workouts.add(workout);
    }

    public void addMeal(Meal meal){
        meals.add(meal);
    }

    public static List<Workout> getHistoryWorkouts(int id) throws Exception{
        String newUrl = "http://localhost:5000/historyworkout/" + id;   
        URL url = new URL(newUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String data;
        List<Workout> workoutData = new ArrayList<>();
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
                workoutData.add(newWorkout);
            }
        }
        in.close();
        return workoutData;
    }

    public static List<Meal> getHistoryMeals(int id, String name, List<Recipe> recipe) throws Exception {
        String newUrl = "http://localhost:5000/historymeal/" + id;
        URL url = new URL(newUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String data;
        List<Meal> mealData = new ArrayList<>();
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
                mealData.add(newmeal);
            }
        }
        in.close();
        return mealData;
    }

    public static void newDay(String name, int weight, int caloriesTarget) throws Exception{
        URL newUrl = new URL("http://localhost:5000/history");
        HttpURLConnection conn = (HttpURLConnection) newUrl.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        OutputStream outputStream = conn.getOutputStream();
        String data = "{\n"+
            "\"name\": \"" + name + "\",\n" +
            "\"weight\": " + weight + ",\n" +
            "\"caloriesTarget\": " + caloriesTarget + "\n" +
            "}";
        outputStream.write(data.getBytes());
        outputStream.flush();
        outputStream.close();
        conn.getResponseCode();
    }

    public static void addMealHistory(String name, String mealName, int historyID, int caloriesConsumed) throws Exception{
        URL newUrl = new URL("http://localhost:5000/historymeal");
        HttpURLConnection conn = (HttpURLConnection) newUrl.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        OutputStream outputStream = conn.getOutputStream();
        String data = "{\n"+
            "\"name\": \"" + name + "\",\n" +
            "\"mealName\": \"" + mealName + "\",\n" +
            "\"historyID\": " + historyID + ",\n" +
            "\"caloriesConsumed\": " + caloriesConsumed + "\n" +
            "}";
        outputStream.write(data.getBytes());
        outputStream.flush();
        outputStream.close();
        conn.getResponseCode();
    }
}
