from datetime import datetime
import hashlib
from flask_restful import Resource
from flask_restful import request
from flask_restful import reqparse
from flask import abort
import json
from .Utils import *
from .LoadFile import *

class Users(Resource):
    def get(self, name, password):
        hashed_password = hashlib.sha512(password.encode('utf-8')).hexdigest()
        result = exec_get_all("SELECT name, height, currentWeight, targetWeight, to_char(birthday, 'YYYY-MM-DD') as birth FROM users WHERE name = %s AND password = %s", (name, hashed_password))
        return result
    
    def post(self):
        parser = reqparse.RequestParser()
        parser.add_argument('name', type =str)
        parser.add_argument('height', type=float)
        parser.add_argument('weight', type=float)
        parser.add_argument('targetWeight', type=float)
        parser.add_argument('birthday', type=str)
        parser.add_argument('password', type=str)
        args = parser.parse_args()
        result = exec_get_all("SELECT name, currentWeight, targetWeight, to_char(birthday, 'YYYY-MM-DD') as birth FROM users WHERE name = %s", (args['name'],))
        if(result != []):
            abort(409)
        hash_password = hashlib.sha512(args['password'].encode('utf-8')).hexdigest()
        sql = """INSERT INTO users(name, password, height, currentWeight, targetWeight, birthday) VALUES (%s, %s, %s, %s, %s, %s)"""
        exec_commit(sql, (args['name'], hash_password, float(args['height']), float(args['weight']), float(args['targetWeight']), args['birthday'],))
        saveUser()

    def put(self):
        parser = reqparse.RequestParser()
        parser.add_argument('oldName', type=str)
        parser.add_argument('name', type=str)
        parser.add_argument('height', type=float)
        parser.add_argument('weight', type=float)
        parser.add_argument('targetWeight', type=float)
        parser.add_argument('birthday', type=str)
        args = parser.parse_args()
        oldName = args['oldName']
        name = args['name']
        height = args['height']
        weight = args['weight']
        targetWeight = args['targetWeight']
        birthday = args['birthday']
        sql = """UPDATE users SET
        name = COALESCE(%s, users.name),
        height = COALESCE(%s, users.height),
        currentWeight = COALESCE(%s, users.currentWeight),
        targetWeight = COALESCE(%s, users.targetWeight),
        birthday = COALESCE(%s, users.birthday)
        WHERE users.name = %s"""
        exec_commit(sql, (name, height, weight, targetWeight, birthday, oldName))
        saveUser()