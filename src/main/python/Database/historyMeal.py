from flask_restful import Resource
from flask_restful import request
from flask_restful import reqparse
from flask import abort
import json
from .Utils import *
from .LoadFile import *

class HistoryMeals(Resource):
    def get(self, id):
        sql = """SELECT meal.name
        FROM meal, historyMeal
        WHERE historyMeal.mealID = meal.mealID
        AND historyMeal.historyID = %s
        """
        data = exec_get_all(sql, (id,))
        return data
    
    def post(self):
        parser = reqparse.RequestParser()
        parser.add_argument('name', type=str)
        parser.add_argument('mealName', type=str)
        parser.add_argument('historyID')
        parser.add_argument('caloriesConsumed')
        args = parser.parse_args() 
        name = args['name']
        meal = args['mealName']
        historyID = args['historyID']
        caloriesConsumed = args['caloriesConsumed']
        result = exec_get_one("SELECT userID from users where name = %s", (name,))
        mealID = exec_get_one("SELECT mealID from meal where name = %s AND userID = %s", (meal, result[0],))
        sql = """INSERT INTO historyMeal(historyID, mealID) VALUES (%s, %s)"""
        exec_commit(sql, (historyID, mealID[0],))
        sql = """UPDATE personalHistory SET caloriesConsumed = caloriesConsumed + %s
                WHERE historyID = %s"""
        exec_commit(sql, (caloriesConsumed, historyID,))
        saveHistoryMeal()