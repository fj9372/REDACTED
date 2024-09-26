from flask_restful import Resource
from flask_restful import request
from flask_restful import reqparse
from flask import abort
import json
from .Utils import *
from .LoadFile import *

class HistoryWorkouts(Resource):
    def get(self, id):
        sql = """SELECT intensity, to_char(timeStarted, 'YYYY-MM-DD HH24:MI'), to_char(timeEnded, 'YYYY-MM-DD HH24:MI') 
        FROM workout, historyWorkouts
        WHERE historyWorkouts.workoutID = workout.workoutID
        AND historyWorkouts.historyID = %s
        """
        data = exec_get_all(sql, (id,))
        return data
    
    def post(self):
        return