from flask_restful import Resource
from flask_restful import request
from flask_restful import reqparse
from flask import abort
from .Utils import *
from .LoadFile import *

class RecipeIngredients(Resource):
    def get(self, name, recipe):
        recipe = recipe.replace('_', ' ')
        result = exec_get_one("SELECT userID from users where name = %s", (name,))
        recipeID = exec_get_one("SELECT recipeID from recipe WHERE userID = %s AND name = %s", (result[0], recipe))
        sql = """SELECT ingredients.Shrt_Desc, recipeIngredients.amount 
        FROM ingredients, recipeIngredients
        WHERE recipeIngredients.ingredientID = ingredients.NDB_No
        AND recipeIngredients.recipeID = %s"""
        return exec_get_all(sql, (recipeID[0],))

    def post(self):
        parser = reqparse.RequestParser()
        parser.add_argument('name', type=str)
        parser.add_argument('recipeName', type=str)
        parser.add_argument('ingredientID', type=int)
        parser.add_argument('amount', type=int)
        args = parser.parse_args() 
        name = args['name']
        recipe = args['recipeName']
        ingredient = args['ingredientID']
        amount = args['amount']
        result = exec_get_one("SELECT userID from users where name = %s", (name,))
        recipeID = exec_get_one("SELECT recipeID from recipe WHERE userID = %s AND name = %s", (result[0], recipe))
        sql = """INSERT INTO recipeIngredients (recipeID, ingredientID, amount) VALUES (%s, %s, %s)"""
        exec_commit(sql, (recipeID[0],ingredient, amount,))
        saveRecipeIngredients()

    def put(self):
        return

    def delete(self, name, recipe):
        return