from flask_restful import Resource
from flask_restful import request
from flask_restful import reqparse
from flask import abort
from .Utils import *
from .LoadFile import *

class Meal(Resource):
    def get(self, name):
        result = exec_get_one("SELECT userID from users where name = %s", (name,))
        sql = """SELECT name FROM meal
        WHERE userID = %s"""
        return exec_get_all(sql, (result[0],))

    def post(self):
        parser = reqparse.RequestParser()
        parser.add_argument('name', type=str)
        parser.add_argument('mealName', type=str)
        args = parser.parse_args() 
        name = args['name']
        meal = args['mealName']
        result = exec_get_one("SELECT userID from users where name = %s", (name,))
        sql = """INSERT INTO meal(userID, name) VALUES (%s, %s)"""
        exec_commit(sql, (result[0], meal,))
        saveMeal()

    def put(self):
        return

    def delete(self, name, meal):
        meal = meal.replace('_', ' ')
        result = exec_get_one("SELECT userID from users where name = %s", (name,))
        sql = """DELETE FROM meal 
        WHERE userID = %s
        AND name = %s"""
        exec_commit(sql, (result[0], meal,))
        saveMeal()