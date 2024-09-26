package FileImportAndExport;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

import Food.Ingredients;
import Food.Meal;
import Food.Recipe;
import NUTRiAPP.User;
import Workout.Workout;

public class HandleCSV {

    private String[] sections = new String[]{"INGREDIENTS", "RECIPES", "MEALS", "PERSONAL HISTORY"};

    private String[] ingColumns = new String[]{"ID", "Name", "Stock", "Calories", "Fat", "Fiber", "Protein", "Carbs", "Units"};
    private String[] recipeColumns = new String[]{"Name", "Instructions", "Stock", "Calories", "Fat", "Fiber", "Protein", "Carbs"};
    private String[] mealColumns = new String[]{"Name", "Stock", "Calories", "Fat", "Fiber", "Protein", "Carbs"};
    private String[] historyColumns = new String[]{"Start Time", "End Time", "Calories"};

    private BufferedReader reader;
    private BufferedWriter writer;

    public void exportCSV(User user, String filepath) throws Exception {
        String filename = "/personalCSVDatabase.csv";
        File csvFile = new File(filepath + filename);
        writer = new BufferedWriter(new FileWriter(csvFile));
        for(String section : sections) {
            sectionWriter(writer, section, user);
        }
        writer.close();
    }

    public void importCSV(User user, String filepath) throws Exception {
        reader = new BufferedReader(new FileReader(filepath));
        boolean catWasLastLine = false;
        String curCat = null;
        String curLine = "placeholder";
        while((curLine = reader.readLine()) != null) {
            for(String category : sections) {
                if(curLine.equals(category)) {
                    curCat = category;
                } else {
                    curCat = null;
                }
            }
            if(curCat != null) {
                catWasLastLine = true;
                continue;
            } else if(catWasLastLine == true) {
                catWasLastLine = false;
                continue;
            } else {
                //sectionReader
            }
        }
    }

    private void sectionWriter(BufferedWriter writer, String section, User user) throws Exception {
        writer.write(section);
        writer.newLine();
        String[] curSection;
        switch (section) {
            case "INGREDIENTS":
                curSection = ingColumns;
                headerConstructor(curSection);
                List<Ingredients> ings = user.getIngredients();
                for(Ingredients ing : ings) {
                    writer.write(ing.getID() + "," + ing.getName() + "," + ing.getStock() + "," + ing.getCalories() + "," +
                        ing.getFat() + "," + ing.getFiber() + "," + ing.getProtein() + "," + ing.getCarbs() + "," + ing.getUnits());
                    writer.newLine();
                }
                break;
            case "RECIPES":
                curSection = recipeColumns;
                headerConstructor(curSection);
                List<Recipe> recipes = user.getRecipe();
                for(Recipe recipe : recipes) {
                    writer.write(recipe.getName() + ",\"" + recipe.getPrepInstructions() + "\"," + recipe.getStock() + "," + 
                        recipe.getCalories() + "," + recipe.getFat() + "," + recipe.getFiber() + "," + recipe.getProtein() + 
                        "," + recipe.getCarbs());
                    writer.newLine();
                }
                break;
            case "MEALS":
                curSection = mealColumns;
                headerConstructor(curSection);
                List<Meal> meals = user.getMeal();
                for(Meal meal : meals) {
                    writer.write(meal.getName() + "," + meal.getStock() + "," + meal.getCalories() + "," + meal.getFat() + "," +
                        meal.getFiber() + "," + meal.getProtein() + "," + meal.getCarbs());
                    writer.newLine();
                }
                break;
            case "PERSONAL HISTORY":
                curSection = historyColumns;
                headerConstructor(curSection);
                List<Workout> workouts = user.getWorkout();
                for(Workout workout : workouts) {
                    writer.write(workout.getStartTime() + "," + workout.getEndTime() + "," + workout.getCalories());
                    writer.newLine();
                }
                break;
            default:
                curSection = null;
                break;
        }
    }

    private void headerConstructor(String[] currentSection) throws Exception {
        for(int i = 0; i < currentSection.length; i++) {
            if(i == (currentSection.length - 1)) {
                writer.write(currentSection[i]);
            } else {
                writer.write(currentSection[i] + ",");
            }
        }
        writer.newLine();
    }
}
