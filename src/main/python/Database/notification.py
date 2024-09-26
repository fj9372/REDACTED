from flask_restful import Resource
from flask_restful import request
from flask_restful import reqparse
from flask import abort
import json
from .Utils import *
from .LoadFile import *

class Notification(Resource):
    
    def post(self):
        parser = reqparse.RequestParser()
        parser.add_argument('receiver', type=str)
        parser.add_argument('message', type=str)
        args = parser.parse_args() 
        message = args['message']
        receiver = args['receiver']
        userID = exec_get_one("SELECT userID FROM users WHERE name = %s", (receiver,))
        if(userID != None):
            sql = """INSERT INTO notification (message, toUserID, teamInvite) VALUES (%s, %s, true)"""
            exec_commit(sql, (message, userID[0],))
            saveNotification()

    def get(self,name):
        userID = exec_get_one("SELECT userID FROM users WHERE name = %s", (name,))
        return exec_get_all("select message from notification where touserid='%s'",userID)