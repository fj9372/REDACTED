from flask_restful import Resource
from flask_restful import request
from flask_restful import reqparse
from flask import abort
import json
from .Utils import *
from .LoadFile import *

class Teams(Resource):
    def get(self, name):
        userID = exec_get_one("SELECT userID FROM users WHERE name = %s", (name,))
        data = exec_get_all("SELECT teamID FROM teamUsers WHERE userID = %s", (userID[0],))
        return data
    
    def post(self):
        parser = reqparse.RequestParser()
        parser.add_argument('name', type=str)
        parser.add_argument('teamName')
        args = parser.parse_args() 
        name = args['name']
        teamName = args['teamName']
        userID = exec_get_one("SELECT userID FROM users WHERE name = %s", (name,))
        sql = """INSERT INTO teams (teamName) VALUES (%s) RETURNING teamID"""
        teamID = exec_insert_returning(sql, (teamName,))
        sql = """INSERT INTO teamUsers (teamID, userID) VALUES (%s, %s)"""
        exec_commit(sql, (teamID, userID[0],))
        saveTeamUsers()
        saveTeam()

    def delete(self, name):
        userID = exec_get_one("SELECT userID FROM users WHERE name = %s", (name,))
        teamid = exec_insert_returning("DELETE FROM teamUsers WHERE userID = %s RETURNING teamid", (userID[0],))
        check = exec_get_all("SELECT * FROM teamUsers WHERE teamid = %s", (teamid,))
        print(teamid)
        if check == []:
            exec_commit("DELETE FROM teams WHERE teamID = %s", (teamid,))
            saveTeam()
        saveTeamUsers()
 