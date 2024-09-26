from flask_restful import Resource
from flask_restful import request
from flask_restful import reqparse
from flask import abort
from .Utils import *
from .LoadFile import *


class Ingredient(Resource):
    def get(self, name):
        words = '%' + name.replace('_', '%') + '%'
        result = exec_get_all("SELECT NDB_No, Shrt_Desc, energy, lipid, protein, fiber, carbohydrate, GmWtDesc from ingredients WHERE Shrt_Desc ILIKE %s LIMIT 10", (words,))
        return result