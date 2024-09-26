from flask_restful import Resource
from flask_restful import request
from flask_restful import reqparse
from flask import abort
import json
from .Utils import *
from .LoadFile import *

class History(Resource):
    def get(self, name):
        userID = exec_get_one("SELECT userID FROM users WHERE name = %s", (name,))
        data = exec_get_all("SELECT historyID, weight, caloriesConsumed, caloriesTarget, cast(date as text) FROM personalhistory WHERE userID = %s ORDER BY date DESC", (userID[0],))
        return data
    
    def post(self):
        parser = reqparse.RequestParser()
        parser.add_argument('name', type=str)
        parser.add_argument('weight')
        parser.add_argument('caloriesTarget')
        args = parser.parse_args() 
        name = args['name']
        weight = args['weight']
        caloriesTarget = args['caloriesTarget']
        userID = exec_get_one("SELECT userID FROM users WHERE name = %s", (name,))
        sql = """INSERT INTO personalHistory (userID, weight, caloriesConsumed, caloriesTarget, date) VALUES (%s, %s, 0, %s, CURRENT_DATE)"""
        exec_commit(sql, (userID[0], weight, caloriesTarget,))
        saveHistory()
