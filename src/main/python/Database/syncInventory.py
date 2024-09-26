from flask_restful import Resource
from flask_restful import request
from flask import request, json
from .Utils import *
from .LoadFile import *


class SyncInventory(Resource):
    def post(self, name):
        data = json.loads(request.data.replace(b'\\', b'\\\\'))
        userid = exec_get_one(f"""
            UPDATE public.users
            SET currentweight = {data["weight"]},
                targetweight  = {data["targetWeight"]}
            WHERE name = '{name}'
            RETURNING userid;
        """)[0]
        
        # ingredients
        exec_commit(f"""
            DELETE FROM public.inventory
            WHERE userid = {userid};
        """)
        newsets = [(
            userid,
            ingredientItem["id"],
            ingredientItem["stock"]
            ) for ingredientItem in data["ingredients"]]
        if (len(newsets) != 0):
            exec_commit(f"""
                INSERT INTO public.inventory (userid, ingredientid, amount)
                VALUES {str(newsets)[1:-1]};
            """)
        
        #remove meals
        exec_commit(f"""
            DELETE FROM public.mealrecipes
            WHERE mealid in (
                SELECT meal.mealid FROM meal WHERE userid = {userid}
            );
            DELETE FROM public.meal
            WHERE userid = {userid};
        """)
        
        # recipies
        exec_commit(f"""
            DELETE FROM public.recipeingredients
            WHERE recipeid in (
                SELECT recipe.recipeid FROM recipe WHERE userid = {userid}
            );
            DELETE FROM public.recipe
            WHERE userid = {userid};
        """)
        
        newRecipies = [(
            userid,
            recipe["name"],
            recipe["prepinstructions"]
        ) for recipe in data["recipies"]]
        if (len(newRecipies) != 0):
            exec_commit(f"""
                INSERT INTO public.recipe (userid, name, instruction)
                VALUES {str(newRecipies)[1:-1]};
            """)
        
        newRecipies = [
            [(
                exec_get_one(f"SELECT recipeid FROM public.recipe WHERE name = '{recipe['name']}' LIMIT 1;")[0],
                item["id"],
                item["stock"]
            ) for item in recipe["ingredientsNeeded"]]
        for recipe in data["recipies"]]
        if (len(newRecipies) != 0):
            exec_commit(f"""
                INSERT INTO public.recipeingredients (recipeid, ingredientid, amount)
                VALUES {str(newRecipies).replace("[],","").replace("[","").replace("]","")};
            """)
        
        # add meals
        newMeals = [(
            userid,
            meal["name"]
        ) for meal in data["meals"]]
        if (len(newMeals) != 0):
            exec_commit(f"""
                INSERT INTO public.meal (userid, name)
                VALUES {str(newMeals)[1:-1]};
            """)
        
        newMeals = [
            [(
                exec_get_one(f"SELECT mealid FROM public.meal WHERE name = '{meal['name']}' LIMIT 1;")[0],
                exec_get_one(f"SELECT recipeid FROM public.recipe WHERE name = '{item['name']}' LIMIT 1;")[0]
            ) for item in meal["recipiesNeeded"]]
        for meal in data["meals"]]
        if (len(newMeals) != 0):
            exec_commit(f"""
                INSERT INTO public.mealrecipes (mealid, recipeid)
                VALUES {str(newMeals).replace("[],","").replace("[","").replace("]","")};
            """)
        saveInventory()
        saveRecipe()
        saveRecipeIngredients()
        saveMeal()
        saveMealRecipes()
        
        
        