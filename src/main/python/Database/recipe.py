from flask_restful import Resource
from flask_restful import request
from flask_restful import reqparse
from flask import abort
from .Utils import *
from .LoadFile import *

class Recipe(Resource):
    def get(self, name):
        result = exec_get_one("SELECT userID from users where name = %s", (name,))
        sql = """SELECT name, instruction FROM recipe
        WHERE userID = %s"""
        return exec_get_all(sql, (result[0],))

    def post(self):
        parser = reqparse.RequestParser()
        parser.add_argument('name', type=str)
        parser.add_argument('recipeName', type=str)
        parser.add_argument('instruction', type=str)
        args = parser.parse_args() 
        name = args['name']
        recipeName = args['recipeName']
        instruction = args['instruction']
        result = exec_get_one("SELECT userID from users where name = %s", (name,))
        sql = """INSERT INTO recipe(userID, name, instruction) VALUES (%s, %s, %s)"""
        exec_commit(sql, (result[0], recipeName, instruction,))
        saveRecipe()

    def put(self):
        parser = reqparse.RequestParser()
        parser.add_argument('name', type=str)
        parser.add_argument('oldRecipeName', type=str)
        parser.add_argument('recipeName', type=str)
        parser.add_argument('instruction', type=str)
        args = parser.parse_args() 
        name = args['name']
        oldRecipeName = args['oldRecipeName']
        recipeName = args['recipeName']
        instruction = args['instruction']
        result = exec_get_one("SELECT userID from users where name = %s", (name,))
        sql = """UPDATE recipe SET
        name = COALESCE(%s, recipe.name),
        intruction = COALESCE(%s, recipe.instruction)
        WHERE userID = %s
        AND name = %s"""
        exec_commit(sql, (recipeName, instruction, result[0], oldRecipeName,))
        saveRecipe()

    def delete(self, name, recipe):
        recipe = recipe.replace('_', ' ')
        result = exec_get_one("SELECT userID from users where name = %s", (name,))
        sql = """DELETE FROM recipe 
        WHERE userID = %s
        AND name = %s"""
        exec_commit(sql, (result[0], recipe,))
        saveRecipe()