DROP table if EXISTS ingredients CASCADE;
CREATE TABLE ingredients(
    NDB_No SERIAL PRIMARY KEY NOT NULL,
    Shrt_Desc VARCHAR(100),
    Water float,
    Energy int,
    Protein float,
    Lipid float,
    Ash float,
    Carbohydrate float,
    Fiber float,
    Sugar float,
    Calcium float,
    Iron float,
    Magnesium float,
    Phosphorus float,
    Potassium float,
    Sodium float,
    Zinc float,
    Copper float,
    Manganese float,
    Selenium float,
    VitaminC float,
    Thiamin float,
    Riboflavin float,
    Niacin float,
    Panto_Acid float,
    VitaminB3 float,
    Folate int,
    FolidAcid int,
    FoodFolate int,
    FolateDFE int,
    Choline float,
    VitaminB12 float,
    VitaminA_IU int,
    VitaminA_RAE int,
    Retinol int,
    AlphaCarot int,
    BetaCarot int,
    BetaCrypt int,
    Lycopene int,
    Lut_Zea int,
    VitaminE float,
    VitaminD float,
    VitaminD_IU int,
    VitaminK float,
    SaturatedFat float,
    MonoFat float,
    PolyFat float,
    Cholesterol int,
    GmWt float,
    GmWtDesc VARCHAR(100),
    GmWt2 float,
    GmWtDesc2 VARCHAR(100),
    Refuse_Pct int
);

DROP TABLE if EXISTS users CASCADE;
CREATE TABLE users(
    userID SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(30) NOT NULL UNIQUE,
    password text NOT NULL,
    height float NOT NULL,
    currentWeight float NOT NULL,
    targetWeight float NOT NULL,
    birthday timestamp NOT NULL
);

DROP TABLE if EXISTS inventory;
CREATE TABLE inventory(
    userID INT NOT NULL REFERENCES users(userID),
    ingredientID INT NOT NULL REFERENCES ingredients(NDB_No),
    amount INT NOT NULL DEFAULT 0
);

DROP TABLE if EXISTS recipe CASCADE;
CREATE TABLE recipe(
    recipeID SERIAL PRIMARY KEY NOT NULL,
    userID INT NOT NULL REFERENCES users(userID),
    name VARCHAR(30) NOT NULL,
    instruction VARCHAR NOT NULL
);

DROP TABLE if EXISTS recipeIngredients;
CREATE TABLE recipeIngredients(
    recipeID INT NOT NULL REFERENCES recipe(recipeID),
    ingredientID INT NOT NULL REFERENCES ingredients(NDB_No),
    amount INT NOT NULL 
);

DROP TABLE if EXISTS meal CASCADE;
CREATE TABLE meal(
    mealID SERIAL PRIMARY KEY NOT NULL,
    userID INT NOT NULL REFERENCES users(userID),
    name VARCHAR(30) NOT NULL
);

DROP TABLE if EXISTS mealRecipes;
CREATE TABLE mealRecipes(
    mealID INT NOT NULL REFERENCES meal(mealID),
    recipeID INT NOT NULL REFERENCES recipe(recipeID)
);

DROP TABLE if EXISTS workout CASCADE;
CREATE TABLE workout(
    workoutID SERIAL PRIMARY KEY NOT NULL,
    userid INT NOT NULL REFERENCES users(userID),
    intensity text NOT NULL,
    calories INT NOT NULL,
    timeStarted timestamp NOT NULL,
    timeEnded timestamp NOT NULL
);

DROP TABLE if EXISTS personalHistory CASCADE;
CREATE TABLE personalHistory(
    historyID SERIAL PRIMARY KEY NOT NULL,
    userID INT NOT NULL REFERENCES users(userID),
    weight INT NOT NULL,
    caloriesConsumed INT NOT NULL,
    caloriesTarget INT NOT NULL,
    date timestamp NOT NULL
);

DROP TABLE if EXISTS historyMeal;
CREATE TABLE historyMeal(
    historyID INT NOT NULL REFERENCES personalHistory(historyID),
    mealID INT NOT NULL REFERENCES meal(mealID)
);

DROP TABLE if EXISTS historyWorkouts;
CREATE TABLE historyWorkouts(
    historyID INT NOT NULL REFERENCES personalHistory(historyID),
    workoutID INT NOT NULL REFERENCES workout(workoutID)
);

DROP TABLE if EXISTS teams CASCADE;
CREATE TABLE teams(
    teamID SERIAL PRIMARY KEY NOT NULL,
    teamName text NOT NULL
);

DROP TABLE if EXISTS teamUsers;
CREATE TABLE teamUsers(
    teamID INT NOT NULL REFERENCES teams(teamID),
    userID INT NOT NULL REFERENCES users(userID)
);

DROP TABLE if EXISTS teamChallenge;
CREATE TABLE teamChallenge(
    teamID INT NOT NULL REFERENCES teams(teamID),
    challengeStart timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    challengeEnd timestamp
);

DROP TABLE if EXISTS notification;
CREATE TABLE notification(
    notifID SERIAL PRIMARY KEY NOT NULL,
    message text NOT NULL,
    toUserID INT NOT NULL REFERENCES users(userID),
    teamInvite boolean DEFAULT false
);