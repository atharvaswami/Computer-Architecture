public class Clock {

    // Initialize the variables
    private int currentTime;
    private final int duration;

    // Constructor to start the time and initialize the duration
    Clock(final int duration) {

        this.duration = duration;
        this.currentTime = 0;
        
    }

    // Each time the infiltrator moves, the current time is updated
    public int changeTime() {

        this.currentTime += this.duration;
        return this.currentTime;

    }
    
}
