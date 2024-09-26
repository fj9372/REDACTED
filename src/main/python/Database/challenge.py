from flask_restful import Resource
from flask_restful import request
from flask_restful import reqparse
from flask import abort
import json
from .Utils import *
from .LoadFile import *

class Challenge(Resource):
    def get(self, id):
        check = exec_get_all("SELECT * FROM teamChallenge WHERE teamID = %s", (id,))
        if (check == []):
            return ['none']
        data = exec_get_all("""WITH user_workout_duration AS (
                            SELECT u.name AS username,
                                SUM(EXTRACT(EPOCH FROM (w.timeEnded - w.timeStarted))) AS total_duration
                            FROM users u
                            JOIN workout w ON u.userID = w.userid
                            JOIN teamUsers tu ON u.userID = tu.userID
                            JOIN teamChallenge tc ON tu.teamID = tc.teamID
                            WHERE tc.teamID = %s
                            GROUP BY u.name
                        )
                        SELECT username
                        FROM user_workout_duration
                        ORDER BY total_duration DESC
                        """, (id,))
        return data
    
    def post(self):
        parser = reqparse.RequestParser()
        parser.add_argument('teamID')
        args = parser.parse_args() 
        teamID = args['teamID']
        exec_commit("INSERT INTO teamChallenge(teamID) VALUES (%s)", (teamID,))
        saveTeamChallenge()