package Workout;

public class CreateMedium extends WorkoutCreator{

    @Override
    public Workout createWorkout(String startTime, String endTime, String username, int historyID) throws Exception {
        MediumIntensity mediumIntensity = new MediumIntensity(startTime, endTime);
        logToDatabase(username, startTime, endTime, mediumIntensity.getCalories(), "medium", historyID);
        return mediumIntensity;
    }

    @Override
    public Workout createWorkout(String startTime, String endTime) throws Exception {
        MediumIntensity mediumIntensity = new MediumIntensity(startTime, endTime);
        return mediumIntensity;
    }
    
}
