from flask_restful import Resource
from flask_restful import request
from flask_restful import reqparse
from flask import abort
import json
from .Utils import *
from .LoadFile import *

class SearchUser(Resource):
    def get(self, name):
        data = exec_get_all("SELECT name FROM users WHERE LOWER(name) LIKE %s AND userID NOT IN (SELECT userID FROM teamUsers)", (f'%{name.lower()}%',))
        return data