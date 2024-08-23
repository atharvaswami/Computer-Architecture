public class Sensor {

    // Initialize the variables
    private boolean isActive;
    private Coin coin;

    // Constructor which accepts p value
    Sensor(final double headsProbability) {

        this.coin = new Coin(headsProbability);

        //Initially the sensor is assumed to be OFF
        this.isActive = false;

    }

    // Returns True if the sensor is ON else False if the sensor is OFF
    public boolean checkStatus() {

        return this.isActive;

    }

    // For each cell, the coin is tossed and the status of the sensor is redetermined.
    public void restart() {

        this.isActive = this.coin.toss();

    }
}
