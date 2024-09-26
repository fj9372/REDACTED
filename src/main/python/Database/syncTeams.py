from flask_restful import Resource
from flask_restful import request
from flask import request, json
from .Utils import *
from .LoadFile import *


class SyncTeams(Resource):
    def post(self, teamId):
        data = json.loads(request.data.replace(b'[',b'["').replace(b']',b'"]').replace(b', ',b'", "'))
        teamName = data[1]
        data = [x for x in data if x != teamName]
        
        exec_commit(f"DELETE FROM public.teamUsers WHERE teamid = {teamId};")
        teamMembers = [(
            int(teamId),
            exec_get_one(f"SELECT userid FROM public.users WHERE name = '{member}'")[0],
        ) for member in data]
        exec_commit(f"INSERT INTO public.teamusers(teamid, userid) VALUES {str(teamMembers)[1:-1]}")