from flask_restful import Resource
from flask_restful import request
from flask_restful import reqparse
from flask import abort
from .Utils import *
from .LoadFile import *


class Inventory(Resource):
    def get(self, name, ingredientID=None):
        result = exec_get_one("SELECT userID from users where name = %s", (name,))
        sql = """SELECT ingredients.Shrt_Desc, inventory.amount from ingredients, inventory
        WHERE inventory.ingredientID = ingredients.NDB_No
        AND inventory.userID = %s
        ORDER BY Shrt_Desc"""
        parameters = (result[0],)
        if(ingredientID != None):
            sql += " AND inventory.ingredientID = %s"
            parameters += (ingredientID,)
        return exec_get_all(sql, parameters)

    def post(self):
        parser = reqparse.RequestParser()
        parser.add_argument('name', type =str)
        parser.add_argument('ingredientID', type=int)
        parser.add_argument('amount', type=int)
        args = parser.parse_args()
        name = args['name']
        ingredientID = args['ingredientID']
        amount = args['amount']
        result = exec_get_one("SELECT userID from users where name = %s", (name,))
        checkInventory = exec_get_all("SELECT * FROM inventory WHERE inventory.userID = %s AND ingredientID = %s", (result[0], ingredientID))
        if(checkInventory != []):
            sql = """UPDATE inventory SET
                amount = amount + %s
                WHERE ingredientID = %s 
                AND userID = %s"""
        else: 
            sql = """INSERT INTO inventory(amount, ingredientID, userID) VALUES (%s, %s, %s)"""
        exec_commit(sql, (amount, ingredientID, result[0]))
        saveInventory()

    def put(self):
        parser = reqparse.RequestParser()
        parser.add_argument('name', type = str)
        parser.add_argument('ingredientID', type=str)
        parser.add_argument('amount', type=str)
        args = parser.parse_args()
        name = args['name']
        ingredientID = args['ingredientID']
        amount = args['amount']
        result = exec_get_one("SELECT userID from users where name = %s", (name,))
        sql = """UPDATE inventory SET
        amount = %s
        WHERE userID = %s 
        AND ingredientID = %s"""
        exec_commit(sql, (amount, result[0], ingredientID,))
        saveInventory()

    def delete(self, name, ingredientID):
        result = exec_get_one("SELECT userID from users where name = %s", (name,))
        sql = """DELETE FROM inventory 
        WHERE userID = %s
        AND ingredientID = %s"""
        exec_commit(sql, (result[0], ingredientID))
        saveInventory()