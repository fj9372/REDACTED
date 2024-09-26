package Food;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Meal implements Food {
    private List<Recipe> recipesNeeded;
    private String name;
    
    public Meal(String name) {
        this.name = name;
        recipesNeeded = new ArrayList<>();
    }

    public Meal(String name, List<Recipe> recipes) {
        this.name = name;
        recipesNeeded = recipes;
    }

    public void addRecipe(Recipe recipe){
        recipesNeeded.add(recipe);
    }
    public void removeRecipe(Recipe recipe){
        recipesNeeded.remove(recipe);
    }
    public List<Recipe> getRecipesNeeded() {
        return recipesNeeded;
    }

    @Override
    public int getCalories() {
        int cals = 0;
        for (Recipe rec : recipesNeeded){
            cals+= rec.getCalories();
        }
        return cals;
    }


    @Override
    public double getCarbs() {
        int carb = 0;
        for (Recipe rec : recipesNeeded){
            carb+= rec.getCarbs();
        }
        return carb;
    }


    @Override
    public double getFat() {
        int fat = 0;
        for (Recipe rec : recipesNeeded){
            fat+= rec.getFat();
        }
        return fat;
    }


    @Override
    public double getProtein() {
        int protein = 0;
        for (Recipe rec : recipesNeeded){
            protein+= rec.getProtein();
        }
        return protein;
    }


    @Override
    public double getFiber() {
        int fiber = 0;
        for (Recipe rec : recipesNeeded){
            fiber+= rec.getFiber();
        }
        return fiber;
    }


    @Override
    public String getName() {
        return name;
    }


    @Override
    public int getStock() {
        return 0;
    }


    public static int insertMeal(String name, String mealName) throws Exception {
        URL newUrl = new URL("http://localhost:5000/meal");
        HttpURLConnection conn = (HttpURLConnection) newUrl.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        OutputStream outputStream = conn.getOutputStream();
        String data = "{\n"+
            "\"name\": \"" + name + "\",\n" +
            "\"mealName\": \"" + mealName + "\"\n" +
            "}";
        outputStream.write(data.getBytes());
        outputStream.flush();
        outputStream.close();
        int code = conn.getResponseCode();
        return code;
    }

    public static int insertMealRecipes(String name, String mealName, String recipeName) throws Exception{
        URL newUrl = new URL("http://localhost:5000/mealRecipes");
        HttpURLConnection conn = (HttpURLConnection) newUrl.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        OutputStream outputStream = conn.getOutputStream();
        String data = "{\n"+
            "\"name\": \"" + name + "\",\n" +
            "\"recipeName\": \"" + recipeName + "\",\n" +
            "\"mealName\": \"" + mealName + "\"\n" +
            "}";
        outputStream.write(data.getBytes());
        outputStream.flush();
        outputStream.close();
        int code = conn.getResponseCode();
        return code;
    }

    public static void deleteMeal(String name, String mealName) throws Exception {
        mealName = mealName.replaceAll(" ", "_");
        String newUrl = "http://localhost:5000/meal/" + name + "/" + mealName;
        URL url = new URL(newUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("DELETE");
        conn.getInputStream().close();
    }

    public static List<Meal> getUserMeal(String name) throws Exception {
        List<Meal> meal = new ArrayList<>();
        String newUrl = "http://localhost:5000/meal/" + name;
        URL url = new URL(newUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String data;
        while((data=in.readLine()) != null){
            if(!data.trim().contains("[") && !data.trim().contains("]")){
                String newData = data.replace(",","").replace("\"","").trim();
                Meal newmeal = new Meal(newData);
                // List<String> mealRecipe = Recipe.getMealRecipes(name, newData);
                // for(Recipe r: recipe){
                //     if(mealRecipe.contains(r.getName())){
                //         newmeal.addRecipe(r);
                //     }
                // }
                meal.add(newmeal);
            }
        }
        in.close();
        return meal;
    }
    
    @Override
    public String toString() {
        String str = """
                {
                    "name": "%s",
                    "recipiesNeeded": %s
                }
                """;
        str = String.format(str, name, recipesNeeded);
        return str;
    }
}
