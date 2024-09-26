package Workout;

public class CreateHigh extends WorkoutCreator{
    @Override
    public Workout createWorkout(String startTime, String endTime, String username, int historyID) throws Exception {
        HighIntensity highIntensity = new HighIntensity(startTime, endTime);
        logToDatabase(username, startTime, endTime, highIntensity.getCalories(), "high", historyID);
        return highIntensity;
    }

    @Override
    public Workout createWorkout(String startTime, String endTime) throws Exception {
        HighIntensity highIntensity = new HighIntensity(startTime, endTime);
        return highIntensity;
    }
}
