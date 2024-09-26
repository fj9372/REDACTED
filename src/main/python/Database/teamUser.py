from flask_restful import Resource
from flask_restful import request
from flask_restful import reqparse
from flask import abort
import json
from .Utils import *
from .LoadFile import *

class TeamUsers(Resource):
    def get(self, id):
        data = exec_get_all("SELECT u.name, (SELECT teamName FROM teams WHERE teamID = %s) AS teamName FROM users u JOIN teamUsers tu ON u.userID = tu.userID WHERE tu.teamID = %s", (id, id,))
        return data
    
    def post(self):
        parser = reqparse.RequestParser()
        parser.add_argument('receiver', type=str)
        parser.add_argument('message', type=str)
        parser.add_argument('teamid')
        args = parser.parse_args() 
        message = args['message']
        receiver = args['receiver']
        teamID = args['teamid']
        userID = exec_get_one("SELECT userID FROM users WHERE name = %s", (receiver,))
        sql = """INSERT INTO teamUsers (teamID, userID) VALUES (%s, %s)"""
        exec_commit(sql, (teamID, userID[0],))
        sql = """INSERT INTO notification (message, toUserID, teamInvite) VALUES (%s, %s, true)"""
        exec_commit(sql, (message, userID[0],))
        saveNotification()
        saveTeamUsers()
