from flask_restful import Resource
from flask_restful import request
from flask_restful import reqparse
from flask import abort
from .Utils import *
from .LoadFile import *

class MealRecipes(Resource):
    def get(self, name, meal):
        meal = meal.replace('_', ' ')
        result = exec_get_one("SELECT userID from users where name = %s", (name,))
        mealID = exec_get_one("SELECT mealID from meal WHERE userID = %s AND name = %s", (result[0], meal))
        sql = """SELECT recipe.name 
        FROM recipe, mealRecipes
        WHERE mealRecipes.recipeID = recipe.recipeID
        AND mealRecipes.mealID = %s"""
        return exec_get_all(sql, (mealID[0],))

    def post(self):
        parser = reqparse.RequestParser()
        parser.add_argument('name', type=str)
        parser.add_argument('mealName', type=str)
        parser.add_argument('recipeName', type=str)
        args = parser.parse_args() 
        name = args['name']
        recipe = args['recipeName']
        meal = args['mealName']
        result = exec_get_one("SELECT userID from users where name = %s", (name,))
        recipeID = exec_get_one("SELECT recipeID from recipe WHERE userID = %s AND name = %s", (result[0], recipe))
        mealID = exec_get_one("SELECT mealID from meal WHERE userID = %s AND name = %s", (result[0], meal))
        sql = """INSERT INTO mealRecipes (mealID, recipeID) VALUES (%s, %s)"""
        exec_commit(sql, (mealID[0], recipeID[0],))
        saveMealRecipes()

    def put(self):
        return

    def delete(self, name, recipe):
        return