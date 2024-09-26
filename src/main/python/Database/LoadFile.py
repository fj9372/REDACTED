from .Utils import connect, exec_commit, exec_sql_file, exec_many, exec_list, exec_get_all, exec_get_one
import psycopg2
import yaml
import os

def loadFile1():
    exec_sql_file('ingredient.sql')
    conn = connect()
    cur = conn.cursor() 
    csv_files = [
        ("csvFiles/ingredients.csv", "ingredients"),
        ("csvFiles/users.csv", "users"),
        ("csvFiles/inventory.csv", "inventory"),
        ("csvFiles/recipe.csv", "recipe"),
        ("csvFiles/recipeIngredients.csv", "recipeIngredients"),
        ("csvFiles/meal.csv", "meal"),
        ("csvFiles/mealRecipes.csv", "mealRecipes"),
        ("csvFiles/workout.csv", "workout"),
        ("csvFiles/personalHistory.csv", "personalHistory"),
        ("csvFiles/historyMeal.csv", "historyMeal"),
        ("csvFiles/historyWorkout.csv", "historyWorkouts"),
        ("csvFiles/teams.csv", "teams"),
        ("csvFiles/teamUsers.csv", "teamUsers"),
        ("csvFiles/teamChallenge.csv", "teamChallenge"),
        ("csvFiles/notification.csv", "notification"),
    ]
    for file_path, table_name in csv_files:
        copy_csv_to_database(file_path, table_name, cur)
    conn.commit() 
    cur.close() 
    conn.close()  
    resetSequence()  

def copy_csv_to_database(file_path, table_name, cur):
    with open(file_path, "r") as f: 
        cur.copy_expert(f"copy {table_name} from stdin with csv header", f)

def resetSequence():
    maxUserID = exec_get_one("SELECT MAX(userID) + 1 FROM users")
    maxUserID = list(maxUserID)
    if maxUserID[0] is None:
        maxUserID[0] = 1
    exec_commit("ALTER SEQUENCE users_userid_seq RESTART WITH %s", (maxUserID[0],))
    maxRecipeID = exec_get_one("SELECT MAX(recipeID) + 1 FROM recipe")
    maxRecipeID = list(maxRecipeID)
    if maxRecipeID[0] is None:
        maxRecipeID[0] = 1
    exec_commit("ALTER SEQUENCE recipe_recipeid_seq RESTART WITH %s", (maxRecipeID[0],))
    maxMealID = exec_get_one("SELECT MAX(mealID) + 1 FROM meal")
    maxMealID = list(maxMealID)
    if maxMealID[0] is None:
        maxMealID[0] = 1
    exec_commit("ALTER SEQUENCE meal_mealid_seq RESTART WITH %s", (maxMealID[0],))
    maxWorkoutID = exec_get_one("SELECT MAX(workoutID) + 1 FROM workout")
    maxWorkoutID = list(maxWorkoutID)
    if maxWorkoutID[0] is None:
        maxWorkoutID[0] = 1
    exec_commit("ALTER SEQUENCE workout_workoutid_seq RESTART WITH %s", (maxWorkoutID[0],))
    maxHistoryID = exec_get_one("SELECT MAX(historyID) + 1 FROM personalHistory")
    maxHistoryID = list(maxHistoryID)
    if maxHistoryID[0] is None:
        maxHistoryID[0] = 1
    exec_commit("ALTER SEQUENCE personalHistory_historyid_seq RESTART WITH %s", (maxHistoryID[0],))
    maxTeamID = exec_get_one("SELECT MAX(teamID) + 1 FROM teams")
    maxTeamID = list(maxTeamID)
    if maxTeamID[0] is None:
        maxTeamID[0] = 1
    exec_commit("ALTER SEQUENCE teams_teamid_seq RESTART WITH %s", (maxTeamID[0],))
    maxNotifID = exec_get_one("SELECT MAX(notifID) + 1 FROM notification")
    maxNotifID = list(maxNotifID)
    if maxNotifID[0] is None:
        maxNotifID[0] = 1
    exec_commit("ALTER SEQUENCE notification_notifid_seq RESTART WITH %s", (maxNotifID[0],))
    
    

def saveUser():
    conn = connect()
    cur = conn.cursor()
    csvPath = "csvFiles/users.csv"
    with open(csvPath, 'w') as f:
        cur.copy_expert("COPY (SELECT * FROM users) TO STDOUT WITH CSV HEADER", f)
    cur.close()
    conn.close()

def saveInventory():
    conn = connect()
    cur = conn.cursor()
    csvPath = "csvFiles/inventory.csv"
    with open(csvPath, 'w') as f:
        cur.copy_expert("COPY (SELECT * FROM inventory) TO STDOUT WITH CSV HEADER", f)
    cur.close()
    conn.close()

def saveMeal():
    conn = connect()
    cur = conn.cursor()
    csvPath = "csvFiles/meal.csv"
    with open(csvPath, 'w') as f:
        cur.copy_expert("COPY (SELECT * FROM meal) TO STDOUT WITH CSV HEADER", f)
    cur.close()
    conn.close()

def saveMealRecipes():
    conn = connect()
    cur = conn.cursor()
    csvPath = "csvFiles/mealRecipes.csv"
    with open(csvPath, 'w') as f:
        cur.copy_expert("COPY (SELECT * FROM mealRecipes) TO STDOUT WITH CSV HEADER", f)
    cur.close()
    conn.close()

def saveRecipe():
    conn = connect()
    cur = conn.cursor()
    csvPath = "csvFiles/recipe.csv"
    with open(csvPath, 'w') as f:
        cur.copy_expert("COPY (SELECT * FROM recipe) TO STDOUT WITH CSV HEADER", f)
    cur.close()
    conn.close()

def saveRecipeIngredients():
    conn = connect()
    cur = conn.cursor()
    csvPath = "csvFiles/recipeIngredients.csv"
    with open(csvPath, 'w') as f:
        cur.copy_expert("COPY (SELECT * FROM recipeIngredients) TO STDOUT WITH CSV HEADER", f)
    cur.close()
    conn.close()

def saveWorkout():
    conn = connect()
    cur = conn.cursor()
    csvPath = "csvFiles/workout.csv"
    with open(csvPath, 'w') as f:
        cur.copy_expert("COPY (SELECT * FROM workout) TO STDOUT WITH CSV HEADER", f)
    cur.close()
    conn.close()

def saveHistory():
    conn = connect()
    cur = conn.cursor()
    csvPath = "csvFiles/personalHistory.csv"
    with open(csvPath, 'w') as f:
        cur.copy_expert("COPY (SELECT * FROM personalHistory) TO STDOUT WITH CSV HEADER", f)
    cur.close()
    conn.close()

def saveHistoryMeal():
    conn = connect()
    cur = conn.cursor()
    csvPath = "csvFiles/historyMeal.csv"
    with open(csvPath, 'w') as f:
        cur.copy_expert("COPY (SELECT * FROM historyMeal) TO STDOUT WITH CSV HEADER", f)
    cur.close()
    conn.close()

def saveHistoryWorkout():
    conn = connect()
    cur = conn.cursor()
    csvPath = "csvFiles/historyWorkout.csv"
    with open(csvPath, 'w') as f:
        cur.copy_expert("COPY (SELECT * FROM historyWorkouts) TO STDOUT WITH CSV HEADER", f)
    cur.close()
    conn.close()

def saveTeamUsers():
    conn = connect()
    cur = conn.cursor()
    csvPath = "csvFiles/teamUsers.csv"
    with open(csvPath, 'w') as f:
        cur.copy_expert("COPY (SELECT * FROM teamUsers) TO STDOUT WITH CSV HEADER", f)
    cur.close()
    conn.close()

def saveTeam():
    conn = connect()
    cur = conn.cursor()
    csvPath = "csvFiles/teams.csv"
    with open(csvPath, 'w') as f:
        cur.copy_expert("COPY (SELECT * FROM teams) TO STDOUT WITH CSV HEADER", f)
    cur.close()
    conn.close()    

def saveNotification():
    conn = connect()
    cur = conn.cursor()
    csvPath = "csvFiles/notification.csv"
    with open(csvPath, 'w') as f:
        cur.copy_expert("COPY (SELECT * FROM notification) TO STDOUT WITH CSV HEADER", f)
    cur.close()
    conn.close()

def saveTeamChallenge():
    conn = connect()
    cur = conn.cursor()
    csvPath = "csvFiles/teamChallenge.csv"
    with open(csvPath, 'w') as f:
        cur.copy_expert("COPY (SELECT * FROM teamChallenge) TO STDOUT WITH CSV HEADER", f)
    cur.close()
    conn.close()
