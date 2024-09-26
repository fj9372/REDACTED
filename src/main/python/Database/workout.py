from datetime import datetime
import hashlib
from flask_restful import Resource
from flask_restful import request
from flask_restful import reqparse
from flask import abort
import json
from .Utils import *
from .LoadFile import *

class Workout(Resource):
    def get(self, name):
        result = exec_get_one("SELECT userID from users where name = %s", (name,))
        sql = """SELECT intensity, to_char(timeStarted, 'YYYY-MM-DD HH24:MI'), to_char(timeEnded, 'YYYY-MM-DD HH24:MI') FROM workout
        WHERE userID = %s
        ORDER BY timeStarted"""
        return exec_get_all(sql, (result[0],))
    
    def post(self):
        parser = reqparse.RequestParser()
        parser.add_argument('name', type=str)
        parser.add_argument('calories')
        parser.add_argument('startTime')
        parser.add_argument('endTime')
        parser.add_argument('intensity')
        parser.add_argument('historyID')
        args = parser.parse_args()
        result = exec_get_all("SELECT userID from users where name = %s", (args['name'],))
        sql = "INSERT INTO workout(userid, intensity, calories, timeStarted, timeEnded) VALUES (%s, %s, %s, %s, %s) RETURNING workoutID"
        result = exec_insert_returning(sql, (result[0], args['intensity'], args['calories'], args['startTime'], args['endTime'], ))
        sql = "INSERT INTO historyWorkouts(historyID, workoutID) VALUES (%s, %s)"
        exec_commit(sql, (args['historyID'], result,))
        saveHistoryWorkout()
        saveWorkout()
