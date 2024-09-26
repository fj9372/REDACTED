package Food;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Recipe implements Food {
    private String prepinstructions;
    private List<Ingredients> ingredientsNeeded;
    private String name;

    public Recipe(String prepinstructions, String name) {
        this.prepinstructions = prepinstructions;
        this.name = name;
        ingredientsNeeded = new ArrayList<>();
    }

    @Override
    public
    int getStock() {
        return 0;
    }

    public void addingredient(Ingredients ingredient){
        ingredientsNeeded.add(ingredient);
    }

    public void removeingredident(Ingredients ingredient){
        ingredientsNeeded.remove(ingredient);
    }

    public List<Ingredients> getIngredientsNeeded() {
        return ingredientsNeeded;
    }

    public String getPrepInstructions() {
        return prepinstructions;
    }

    public void setPrepInstructions(String newInstructions) {
        this.prepinstructions = newInstructions;
    }

    @Override
    public int getCalories() {
        int cals = 0;
        for (Ingredients indr : ingredientsNeeded){
            cals += indr.getCalories()*indr.getStock();
        }
        return cals;
    }

    @Override
    public double getCarbs() {
        int carbs = 0;
        for (Ingredients indr : ingredientsNeeded){
            carbs += indr.getCarbs()*indr.getStock();
        }
        return carbs;
    }

    @Override
    public double getFat() {
        int fat = 0;
        for (Ingredients indr : ingredientsNeeded){
            fat += indr.getFat()*indr.getStock();
        }
        return fat;
    }

    @Override
    public double getProtein() {
        int protien = 0;
        for (Ingredients indr : ingredientsNeeded){
            protien += indr.getProtein()*indr.getStock();
        }
        return protien;
    }

    @Override
    public double getFiber() {
        int fiber = 0;
        for (Ingredients indr : ingredientsNeeded){
            fiber += indr.getFiber()*indr.getStock();
        }
        return fiber;
    }

    @Override
    public String getName() {
        return name;
    }

    public static List<String> getMealRecipes(String name, String meal) throws Exception {
        meal = meal.replaceAll(" ", "_");
        String newUrl = "http://localhost:5000/mealRecipes/" + name + "/" + meal; //user's name and meal name  
        URL url = new URL(newUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String data;
        List<String> recipeData = new ArrayList<>();
        while((data=in.readLine()) != null){
            if(!data.trim().contains("[") && !data.trim().contains("]")){
                String newData = data.replace(","," ").replace("\"","").trim();
                recipeData.add(newData.trim());
            }
        }
        in.close();
        return recipeData;
    }

    public static int insertRecipe(String name, String recipeName, String instruction) throws Exception {
        URL newUrl = new URL("http://localhost:5000/recipe");
        HttpURLConnection conn = (HttpURLConnection) newUrl.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        OutputStream outputStream = conn.getOutputStream();
        String data = "{\n"+
            "\"name\": \"" + name + "\",\n" +
            "\"recipeName\": \"" + recipeName + "\",\n" +
            "\"instruction\": \"" + instruction + "\"\n" +
            "}";
        outputStream.write(data.getBytes());
        outputStream.flush();
        outputStream.close();
        int code = conn.getResponseCode();
        //update the personal recipe array
        return code;
    }

    public static int insertRecipeIngredients(String name, String recipeName, int ingredientID, int amount) throws Exception{
        URL newUrl = new URL("http://localhost:5000/recipeIngredients");
        HttpURLConnection conn = (HttpURLConnection) newUrl.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        OutputStream outputStream = conn.getOutputStream();
        String data = "{\n"+
            "\"name\": \"" + name + "\",\n" +
            "\"recipeName\": \"" + recipeName + "\",\n" +
            "\"ingredientID\": " + ingredientID + ",\n" +
            "\"amount\": " + amount + "\n" +
            "}";
        outputStream.write(data.getBytes());
        outputStream.flush();
        outputStream.close();
        int code = conn.getResponseCode();
        return code;
    }

    public static void deleteRecipe(String name, String recipeName) throws Exception {
        recipeName = recipeName.replaceAll(" ", "_");
        String newUrl = "http://localhost:5000/recipe/" + name + "/" + recipeName;
        URL url = new URL(newUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("DELETE");
        conn.getInputStream().close();
    }

   public static List<Recipe> getUserRecipe(String name) throws Exception {
        List<Recipe> recipe = new ArrayList<>();
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
                    List<String> info = listIngredients.get(0);
                    Ingredients tempIng = new Ingredients(Integer.parseInt(info.get(0)),recipeIng.getValue(), info.get(1), Integer.parseInt(info.get(2)), Double.parseDouble(info.get(3)), Double.parseDouble(info.get(4)),Double.parseDouble(info.get(5)), Double.parseDouble(info.get(6)), info.get(7));
                    temp.addingredient(tempIng);
                }
                recipe.add(temp);
            }
        }
        in.close();
        return recipe;
    }
    
    @Override
    public String toString() {
        String str = """
                {
                    "name": "%s",
                    "prepinstructions": %s,
                    "ingredientsNeeded": %s
                }
                """;
        str = String.format(str, name, prepinstructions, ingredientsNeeded);
        return str;
    }
}
