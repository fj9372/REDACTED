package Food;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class Ingredients implements Food {
    private int id;
    private int stock;
    private String name;
    private int calories;
    private double protein;
    private double fiber;
    private double carbs;
    private double fat;
    private String units;

    public Ingredients(int id, int stock, String name, int calories, double fat, double protein, double fiber, double carbs, String units) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.calories = calories;
        this.fat = fat;
        this.fiber = fiber;
        this.protein = protein;
        this.carbs = carbs;
        this.units = units;
    }

    @Override
    public int getStock() {
        return stock;
    }

    @Override
    public int getCalories() {
        return calories;
    }

    @Override
    public double getCarbs() {
        return carbs;
    }

    @Override
    public double getFat() {
        return fat;
    }

    @Override
    public double getProtein() {
       return protein;
    }

    @Override
    public double getFiber() {
        return fiber;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getUnits() {
        return units;
    }

    public int getID(){
        return id;
    }

    public void setStock(int stock){
        this.stock = stock;
    }

    public static List<List<String>> getIngredientsInfo(String name) throws Exception{
        name = name.replaceAll("\\s+", "_");
        String newUrl = "http://localhost:5000/ingredient/" + name;
        URL url = new URL(newUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String data;
        List<List<String>> ingredientData = new ArrayList<>();
        List<String> inner = new ArrayList<>();
        while((data=in.readLine()) != null){
            if(!data.trim().contains("[") && !data.trim().contains("]")){
                String newData = data.replace(","," ").replace("\"","").trim();
                inner.add(newData.trim());
            }
            else{
                if(!inner.isEmpty()){
                    ingredientData.add(inner);
                    inner = new ArrayList<>();
                }
            }
        }
        in.close();
        return ingredientData;
    }

    public static HashMap<String, Integer> getRecipeIngredients(String name, String recipeName) throws Exception {
        recipeName = recipeName.replaceAll(" ", "_");
        String newUrl = "http://localhost:5000/recipeIngredients/" + name + "/" + recipeName; //user's name and recipe name
        URL url = new URL(newUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String data;
        HashMap<String, Integer> recipeData = new HashMap<>();
        while((data=in.readLine()) != null){
            if(!data.trim().contains("[") && !data.trim().contains("]")){
                String ingredientName = data.replace("\"","").trim();
                if(ingredientName.endsWith(",")){
                    ingredientName = ingredientName.substring(0, ingredientName.length()-1);
                }
                Integer amount = Integer.parseInt(in.readLine().trim());
                recipeData.put(ingredientName, amount);
            }
        }
        in.close();
        return recipeData;
    }

    public static int insertInventory(String name, int ingredientID, int amount) throws Exception {
        URL newUrl = new URL("http://localhost:5000/inventory");
        HttpURLConnection conn = (HttpURLConnection) newUrl.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        OutputStream outputStream = conn.getOutputStream();
        String data = "{\n"+
            "\"name\": \"" + name + "\",\n" +
            "\"ingredientID\": " + ingredientID + ",\n" +
            "\"amount\": \"" + amount + "\"\n" +
            "}";
        outputStream.write(data.getBytes());
        outputStream.flush();
        outputStream.close();
        int code = conn.getResponseCode();
        //update the inventory hehe
        return code;
    }

    public static List<Ingredients> getUserIngredients(String name) throws Exception{
        List<Ingredients> ingredients = new ArrayList<>();
        URL newUrl = new URL("http://localhost:5000/inventory/" + name);
        HttpURLConnection conn = (HttpURLConnection) newUrl.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String data;
        while((data=in.readLine()) != null){
            if(!data.trim().contains("[") && !data.trim().contains("]")){
                String ingredient = data.replace("\"", "");
                if(ingredient.endsWith(",")){
                    ingredient = ingredient.substring(0, ingredient.length() - 1);
                }
                Integer amount = Integer.parseInt(in.readLine().trim());
                List<List<String>> listIngredients = Ingredients.getIngredientsInfo(ingredient);
                List<String> info = listIngredients.get(0);
                Ingredients temp = new Ingredients(Integer.parseInt(info.get(0)),amount, info.get(1), Integer.parseInt(info.get(2)), Double.parseDouble(info.get(3)), Double.parseDouble(info.get(4)),Double.parseDouble(info.get(5)), Double.parseDouble(info.get(6)), info.get(7));
                ingredients.add(temp);
            }
        }
        return ingredients;

    }
    
    @Override
    public String toString() {
        String fUnits = units;
        if (!fUnits.equals("null")) {
            fUnits = "\"" + fUnits + "\"";
        }
        String str = """
                {
                    "id": %s,
                    "stock": %s,
                    "name": "%s",
                    "calories": %s,
                    "protein": %s,
                    "fiber": %s,
                    "carbs": %s,
                    "fat": %s,
                    "units": %s
                }
                """;
        str = String.format(str, id, stock, name, calories, protein, fiber, carbs, fat, fUnits);
        return str;
    }
}
