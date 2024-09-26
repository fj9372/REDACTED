package Workout;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public abstract class WorkoutCreator {
    
    public void logToDatabase(String username, String startTime, String endTime, int calories, String intensity, int historyID) throws Exception{
        URL newUrl = new URL("http://localhost:5000/workout");
        HttpURLConnection conn = (HttpURLConnection) newUrl.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        OutputStream outputStream = conn.getOutputStream();
        String postData = "{\n"+
                "\"name\": \"" + username + "\",\n" +
                "\"intensity\": \"" + intensity + "\",\n" +
                "\"startTime\": \"" + startTime + "\",\n" +
                "\"endTime\": \"" + endTime + "\",\n" +
                "\"calories\": " + calories + ",\n" +
                "\"historyID\": " + historyID + "\n" +
                "}";
        outputStream.write(postData.getBytes());
        outputStream.flush();
        outputStream.close();
        conn.getResponseCode();
    }

    public static List<Workout> getWorkout(String username) throws Exception{
        String newUrl = "http://localhost:5000/workout/" + username; 
        URL url = new URL(newUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String data;
        List<Workout> workout = new ArrayList<>();
        while((data=in.readLine()) != null){
            if(!data.trim().contains("[") && !data.trim().contains("]")){
                String newData = data.replace(",","").replace("\"","").trim();
                String startTime = in.readLine().replace(",","").replace("\"","").trim();
                String endTime = in.readLine().replace(",","").replace("\"","").trim();
                Workout newWorkout;
                if(newData.equals("high")){
                    CreateHigh createHigh = new CreateHigh();
                    newWorkout = createHigh.createWorkout(startTime, endTime);
                }
                else if(newData.equals("medium")){
                    CreateMedium createMedium = new CreateMedium();
                    newWorkout = createMedium.createWorkout(startTime, endTime);
                }
                else{
                    CreateLow createLow = new CreateLow();
                    newWorkout = createLow.createWorkout(startTime, endTime);
                }
                workout.add(newWorkout);
            }
        }
        in.close();
        return workout;
    }

    public abstract Workout createWorkout(String startTime, String endTime, String username, int historyID) throws Exception;

    public abstract Workout createWorkout(String startTime, String endTime) throws Exception;
}
