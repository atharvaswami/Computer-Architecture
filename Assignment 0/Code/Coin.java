import java.lang.Math;

// p = probability of heads after a coin toss
public class Coin {

    // Store the p value
    public double headsProbability;

    
    Coin(final double headsProbability) {

        // Checks if the p value lies between 0 and 1
        assert headsProbability > 0 && headsProbability < 1: "Probability should be between 0 & 1";

        this.headsProbability = headsProbability;

    }

    // If the random function generates a value less than 'p', we assume its head(i.e. Sensor will be ON), else its tail(i.e. Sensor will be OFF)
    public boolean toss() {

        return (boolean)(Math.random() < this.headsProbability);

    }
    
}
