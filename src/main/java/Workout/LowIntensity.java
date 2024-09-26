package Workout;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LowIntensity implements Workout {
    
    private int calories;
    private String startTime;
    private String endTime;
    private final int INTENSITY = 5;

    public LowIntensity(String startTime, String endTime){
        this.startTime = startTime;
        this.endTime = endTime;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startDateTime = LocalDateTime.parse(this.startTime, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(this.endTime, formatter);
        Duration duration = Duration.between(startDateTime, endDateTime);
        long minutes = duration.toMinutes();
        this.calories = (int)minutes*INTENSITY;
    }

    @Override
    public int getCalories() {
        return calories;   
    }

    @Override
    public String getStartTime() {
        return startTime;
    }

    @Override
    public String getEndTime() {
        return endTime;
    }
    
}
