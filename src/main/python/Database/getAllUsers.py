from flask_restful import Resource
from flask_restful import request
from flask_restful import reqparse
from flask import abort
import json
from .Utils import *
from .LoadFile import *

class GetAllUsers(Resource):
    def get(self):
        data = exec_get_all("SELECT name FROM users ") 
        return data