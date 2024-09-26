package FileImportAndExport;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

import Food.Ingredients;
import Food.Meal;
import Food.Recipe;
import NUTRiAPP.User;
import Workout.Workout;

public class HandleJSON {

    private String[] sections = new String[]{"INGREDIENTS", "RECIPES", "MEALS", "PERSONAL HISTORY"};

    private String[] ingColumns = new String[]{"ID", "Name", "Stock", "Calories", "Fat", "Fiber", "Protein", "Carbs", "Units"};
    private String[] recipeColumns = new String[]{"Name", "Instructions", "Stock", "Calories", "Fat", "Fiber", "Protein", "Carbs"};
    private String[] mealColumns = new String[]{"Name", "Stock", "Calories", "Fat", "Fiber", "Protein", "Carbs"};
    private String[] historyColumns = new String[]{"Start Time", "End Time", "Calories"};

    private BufferedWriter writer;

    public void exportJSON(User user, String filepath) throws Exception {
        String filename = "/personalJSONDatabase.json";
        File jsonFile = new File(filepath + filename);
        int secCount = 1;
        writer = new BufferedWriter(new FileWriter(jsonFile));
        writer.write("[");
        writer.newLine();
        for(String section : sections) {
            sectionWriter(section, user, secCount);
            secCount++;
        }
        writer.write("]");
        writer.close();
    }

    public void importJSON(User user, String filepath) throws Exception {
 
    }

    public void sectionWriter(String section, User user, int sectionCount) throws Exception {
        writer.write("\t[");
        writer.newLine();
        switch (section) {
            case "INGREDIENTS":
                int ingCount = 1;
                List<Ingredients> ings = user.getIngredients();
                for(Ingredients tempIng : ings) {
                    writer.write("\t\t{");
                    writer.newLine();
                    writer.write("\t\t\t\"" + ingColumns[0] + "\":\"" + tempIng.getID() + "\",");
                    writer.newLine();
                    writer.write("\t\t\t\"" + ingColumns[1] + "\":\"" + tempIng.getName() + "\",");
                    writer.newLine();
                    writer.write("\t\t\t\"" + ingColumns[2] + "\":\"" + tempIng.getStock() + "\",");
                    writer.newLine();
                    writer.write("\t\t\t\"" + ingColumns[3] + "\":\"" + tempIng.getCalories() + "\",");
                    writer.newLine();
                    writer.write("\t\t\t\"" + ingColumns[4] + "\":\"" + tempIng.getFat() + "\",");
                    writer.newLine();
                    writer.write("\t\t\t\"" + ingColumns[5] + "\":\"" + tempIng.getFiber() + "\",");
                    writer.newLine();
                    writer.write("\t\t\t\"" + ingColumns[6] + "\":\"" + tempIng.getProtein() + "\",");
                    writer.newLine();
                    writer.write("\t\t\t\"" + ingColumns[7] + "\":\"" + tempIng.getCarbs() + "\",");
                    writer.newLine();
                    writer.write("\t\t\t\"" + ingColumns[8] + "\":\"" + tempIng.getUnits() + "\"");
                    writer.newLine();
                    if(ingCount == ings.size()) {
                        writer.write("\t\t}");
                        writer.newLine();
                    } else {
                        writer.write("\t\t},");
                        writer.newLine();
                        ingCount++;
                    }
                }
                break;
            case "RECIPES":
                int recCount = 1;
                List<Recipe> recipes = user.getRecipe();
                for(Recipe tempRec : recipes) {
                    writer.write("\t\t{");
                    writer.newLine();
                    writer.write("\t\t\t\"" + recipeColumns[0] + "\":\"" + tempRec.getName() + "\",");
                    writer.newLine();
                    writer.write("\t\t\t\"" + recipeColumns[1] + "\":" + tempRec.getPrepInstructions() + ",");
                    writer.newLine();
                    writer.write("\t\t\t\"" + recipeColumns[2] + "\":\"" + tempRec.getStock() + "\",");
                    writer.newLine();
                    writer.write("\t\t\t\"" + recipeColumns[3] + "\":\"" + tempRec.getCalories() + "\",");
                    writer.newLine();
                    writer.write("\t\t\t\"" + recipeColumns[4] + "\":\"" + tempRec.getFat() + "\",");
                    writer.newLine();
                    writer.write("\t\t\t\"" + recipeColumns[5] + "\":\"" + tempRec.getFiber() + "\",");
                    writer.newLine();
                    writer.write("\t\t\t\"" + recipeColumns[6] + "\":\"" + tempRec.getProtein() + "\",");
                    writer.newLine();
                    writer.write("\t\t\t\"" + recipeColumns[7] + "\":\"" + tempRec.getCarbs() + "\"");
                    writer.newLine();
                    if(recCount == recipes.size()) {
                        writer.write("\t\t}");
                        writer.newLine();
                    } else {
                        writer.write("\t\t},");
                        writer.newLine();
                        recCount++;
                    }
                }
                break;
            case "MEALS":
                int mealCount = 1;
                List<Meal> meals = user.getMeal();
                for(Meal tempMeal : meals) {
                    writer.write("\t\t{");
                    writer.newLine();
                    writer.write("\t\t\t\"" + mealColumns[0] + "\":\"" + tempMeal.getName() + "\",");
                    writer.newLine();
                    writer.write("\t\t\t\"" + mealColumns[1] + "\":\"" + tempMeal.getStock() + "\",");
                    writer.newLine();
                    writer.write("\t\t\t\"" + mealColumns[2] + "\":\"" + tempMeal.getCalories() + "\",");
                    writer.newLine();
                    writer.write("\t\t\t\"" + mealColumns[3] + "\":\"" + tempMeal.getFat() + "\",");
                    writer.newLine();
                    writer.write("\t\t\t\"" + mealColumns[4] + "\":\"" + tempMeal.getFiber() + "\",");
                    writer.newLine();
                    writer.write("\t\t\t\"" + mealColumns[5] + "\":\"" + tempMeal.getProtein() + "\",");
                    writer.newLine();
                    writer.write("\t\t\t\"" + mealColumns[6] + "\":\"" + tempMeal.getCarbs() + "\"");
                    writer.newLine();
                    if(mealCount == meals.size()) {
                        writer.write("\t\t}");
                        writer.newLine();
                    } else {
                        writer.write("\t\t},");
                        writer.newLine();
                        mealCount++;
                    }
                }
                break;
            case "PERSONAL HISTORY":
                int histCount = 1;
                List<Workout> workouts = user.getWorkout();
                for(Workout tempWO : workouts) {
                    writer.write("\t\t{");
                    writer.newLine();
                    writer.write("\t\t\t\"" + historyColumns[0] + "\":\"" + tempWO.getStartTime() + "\",");
                    writer.newLine();
                    writer.write("\t\t\t\"" + historyColumns[1] + "\":\"" + tempWO.getEndTime() + "\",");
                    writer.newLine();
                    writer.write("\t\t\t\"" + historyColumns[2] + "\":\"" + tempWO.getCalories() + "\"");
                    writer.newLine();
                    if(histCount == workouts.size()) {
                        writer.write("\t\t}");
                        writer.newLine();
                    } else {
                        writer.write("\t\t},");
                        writer.newLine();
                        histCount++;
                    }
                }
                break;
            default:
                writer.newLine();
                break;
        }
        if(sectionCount == sections.length) {
            writer.write("\t]");
            writer.newLine();
        } else {
            writer.write("\t],");
            writer.newLine();
        }
    }
}
