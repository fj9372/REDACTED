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

public class HandleXML {

    private String[] sections = new String[]{"INGREDIENTS", "RECIPES", "MEALS", "PERSONAL-HISTORY"};

    private BufferedWriter writer;

    public void exportXML(User user, String filepath) throws Exception {
        String filename = "/personalXMLDatabase.xml";
        File xmlFile = new File(filepath + filename);
        writer = new BufferedWriter(new FileWriter(xmlFile));
        writer.write("<database>");
        writer.newLine();
        for(String section : sections) {
            sectionWriter(section, user);
        }
        writer.write("</database>");
        writer.close();
    }

    public void importXML(User user, String filepath) {

    }

    private void sectionWriter(String section, User user) throws Exception {
        writer.write("\t<section-" + section.toLowerCase() + ">");
        writer.newLine();
        switch (section) {
            case "INGREDIENTS":
                List<Ingredients> ings = user.getIngredients();
                for(Ingredients tempIng : ings) {
                    writer.write("\t\t<ingredient>");
                    writer.newLine();
                    writer.write("\t\t\t<id>"+ tempIng.getID() + "</id>");
                    writer.newLine();
                    writer.write("\t\t\t<name>" +  tempIng.getName() + "</name>");
                    writer.newLine();
                    writer.write("\t\t\t<stock>" + tempIng.getStock() + "</stock>");
                    writer.newLine();
                    writer.write("\t\t\t<calories>" + tempIng.getCalories() + "</calories>");
                    writer.newLine();
                    writer.write("\t\t\t<fat>" + tempIng.getFat() + "</fat>");
                    writer.newLine();
                    writer.write("\t\t\t<fiber>" + tempIng.getFiber() + "</fiber>");
                    writer.newLine();
                    writer.write("\t\t\t<protein>" + tempIng.getProtein() + "</protein>");
                    writer.newLine();
                    writer.write("\t\t\t<carbs>" + tempIng.getCarbs() + "</carbs>");
                    writer.newLine();
                    writer.write("\t\t\t<units>" + tempIng.getUnits() + "</units>");
                    writer.newLine();
                    writer.write("\t\t</ingredient>");
                    writer.newLine();
                }
                break;
            case "RECIPES":
                List<Recipe> recipes = user.getRecipe();
                for(Recipe tempRec : recipes) {
                    writer.write("\t\t<recipe>");
                    writer.newLine();
                    writer.write("\t\t\t<name>" + tempRec.getName() + "</name>");
                    writer.newLine();
                    writer.write("\t\t\t<instructions>" + tempRec.getPrepInstructions() + "</instructions>");
                    writer.newLine();
                    writer.write("\t\t\t<stock>" + tempRec.getStock() + "</stock>");
                    writer.newLine();
                    writer.write("\t\t\t<calories>" + tempRec.getCalories() + "</calories>");
                    writer.newLine();
                    writer.write("\t\t\t<fat>" + tempRec.getFat() + "</fat>");
                    writer.newLine();
                    writer.write("\t\t\t<fiber>" + tempRec.getFiber() + "</fiber>");
                    writer.newLine();
                    writer.write("\t\t\t<protein>" + tempRec.getProtein() + "</protein>");
                    writer.newLine();
                    writer.write("\t\t\t<carbs>" + tempRec.getCarbs() + "</carbs>");
                    writer.newLine();
                    writer.write("\t\t</recipe>");
                    writer.newLine();
                }
                break;
            case "MEALS":
                List<Meal> meals = user.getMeal();
                for(Meal tempMeal : meals) {
                    writer.write("\t\t<meal>");
                    writer.newLine();
                    writer.write("\t\t\t<name>" + tempMeal.getName() + "</name>");
                    writer.newLine();
                    writer.write("\t\t\t<stock>" + tempMeal.getStock() + "</stock>");
                    writer.newLine();
                    writer.write("\t\t\t<calories>" + tempMeal.getCalories() + "</calories>");
                    writer.newLine();
                    writer.write("\t\t\t<fat>" + tempMeal.getFat() + "</fat>");
                    writer.newLine();
                    writer.write("\t\t\t<fiber>" + tempMeal.getFiber() + "</fiber>");
                    writer.newLine();
                    writer.write("\t\t\t<protein>" + tempMeal.getProtein() + "</protein>");
                    writer.newLine();
                    writer.write("\t\t\t<carbs>" + tempMeal.getCarbs() + "</carbs>");
                    writer.newLine();
                    writer.write("\t\t</meal>");
                    writer.newLine();
                }
                break;
            case "PERSONAL HISTORY":
                List<Workout> workouts = user.getWorkout();
                for(Workout tempWO : workouts) {
                    writer.write("\t\t<workout>");
                    writer.newLine();
                    writer.write("\t\t\t<start-time>" + tempWO.getStartTime() + "</start-time>");
                    writer.newLine();
                    writer.write("\t\t\t<end-time>" + tempWO.getEndTime() + "</end-time>");
                    writer.newLine();
                    writer.write("\t\t\t<calories>" + tempWO.getCalories() + "<calories>");
                    writer.newLine();
                    writer.write("\t\t</workout>");
                    writer.newLine();
                }
                break;
            default:
                writer.newLine();
                break;
        }
        writer.write("\t</section-" + section.toLowerCase() + ">");
        writer.newLine();
    }
}
