from flask import Flask
from flask_restful import Resource, Api
from flask_cors import CORS

from Database.Utils import *
from Database.LoadFile import *
from Database.users import *
from Database.inventory import *
from Database.ingredient import *
from Database.recipe import *
from Database.recipeIngredients import *
from Database.meal import *
from Database.mealRecipes import *
from Database.personalHistory import *
from Database.workout import *
from Database.historyWorkout import *
from Database.historyMeal import *
from Database.teams import *
from Database.teamUser import *
from Database.searchUser import *
from Database.notification import *
from Database.getAllUsers import *
from Database.syncInventory import *
from Database.syncTeams import *
from Database.challenge import *

app = Flask(__name__) #create Flask instance
CORS(app) #Enable CORS on Flask server to work with Nodejs pages
api = Api(app) #api router

api.add_resource(Users, '/users', '/users/<string:name>/<string:password>')
api.add_resource(Inventory, '/inventory',  '/inventory/<string:name>', '/inventory/<string:name>/<int:ingredientID>',)
api.add_resource(Ingredient, '/ingredient/<string:name>')
api.add_resource(Recipe, '/recipe', '/recipe/<string:name>', '/recipe/<string:name>/<string:recipe>')
api.add_resource(RecipeIngredients, '/recipeIngredients', '/recipeIngredients/<string:name>/<string:recipe>')
api.add_resource(Meal, '/meal', '/meal/<string:name>', '/meal/<string:name>/<string:meal>')
api.add_resource(MealRecipes, '/mealRecipes', '/mealRecipes/<string:name>/<string:meal>')
api.add_resource(History, '/history', '/history/<string:name>')
api.add_resource(Workout, '/workout', '/workout/<string:name>')
api.add_resource(HistoryWorkouts, '/historyworkout', '/historyworkout/<int:id>')
api.add_resource(HistoryMeals, '/historymeal', '/historymeal/<int:id>')
api.add_resource(Teams, '/team', '/team/<string:name>')
api.add_resource(TeamUsers, '/teamusers', '/teamusers/<int:id>')
api.add_resource(SearchUser, '/searchuser', '/searchuser/<string:name>')
api.add_resource(Notification, '/notification','/notification/<string:name>')
api.add_resource(GetAllUsers, '/getallusers')
api.add_resource(SyncInventory, '/sync/inventory/<string:name>')
api.add_resource(Challenge, '/challenge', '/challenge/<int:id>')
api.add_resource(SyncTeams, '/sync/teams/<string:teamId>')


if __name__ == '__main__':
    print("Loading db")
    loadFile1()
    print("Starting flask")
    app.run(debug=True), #starts Flask
