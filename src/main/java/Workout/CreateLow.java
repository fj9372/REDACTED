package Workout;

public class CreateLow extends WorkoutCreator{

    @Override
    public Workout createWorkout(String startTime, String endTime, String username, int historyID) throws Exception {
        LowIntensity lowIntensity = new LowIntensity(startTime, endTime);
        logToDatabase(username, startTime, endTime, lowIntensity.getCalories(), "low", historyID);
        return lowIntensity;
    }

    @Override
    public Workout createWorkout(String startTime, String endTime) throws Exception {
        LowIntensity lowIntensity = new LowIntensity(startTime, endTime);
        return lowIntensity;
    }
    
}
