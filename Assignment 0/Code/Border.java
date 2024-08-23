public class Border {
    private Sensor[][] cells;
    private double headsProbability;
    private int borderLength;
    private int borderWidth;

    // Constructor
    Border(int borderLength, int borderWidth, double headsProbability) {

        // Initialize the variables
        this.borderLength = borderLength;
        this.borderWidth = borderWidth;
        this.headsProbability = headsProbability;
        this.cells = new Sensor[this.borderWidth][this.borderLength];

        // Construct a 2D array of Sensor objects
        for (int i = 0; i < this.borderWidth; i += 1) {

            for (int j = 0; j < this.borderLength; j += 1) {

                this.cells[i][j] = new Sensor(this.headsProbability);

            }
                
        }

    }

    // Checks whether the given co-ordinate is inside the defending country
    public boolean isDC(int x, int y) {

        return x >= this.borderWidth;

    }

    // Checks the sensor status
    public boolean isActive(int x, int y) {

        // Co-ordinates inside the attacking country, here infiltrator has to move forward.
        // So we return true value, which basically assumes the sensor to be ON
        if (x < 0 || y < 0)
            return true;

        // Here the corrdinates are inside Defending Country and the infiltrator does not need to move any further
        // So we return false, which basically assumes the sensor to be OFF
        else if (x >= this.borderWidth || y >= this.borderLength)
            return false;

        // Else the coordinates are definitely inside the border,so we return its status
        return cells[x][y].checkStatus();

    }

    // Restart function from Sensor class is called, where coin is tossed and the status of the cell is redetermined
    public void restartCells() {

        for (int i = 0; i < this.borderWidth; i += 1) {

            for (int j = 0; j < this.borderLength; j += 1) {

                this.cells[i][j].restart();

            }

        }
        
    }

}
