public class Infiltrator {
    private boolean reachedDC;
    private Border border;
    private int currX;
    private int currY;

    //Constructor which takes length , width and p value as arguments
    Infiltrator(int borderLength, int borderWidth, double headsProbability) {

        //Infiltrator starts from Attacking Country, so initially reachedDC is set as false
        this.reachedDC = false;

        //Border object as a data member, used to call the respective border functions like restartCells and isActive
        this.border = new Border(borderLength, borderWidth, headsProbability);

        //Initally let's assume the infiltrator starts at (0,0)
        this.currX = 0;
        this.currY = 0;

    }

    //Used to determine if infiltrator has reached Defender country
    public boolean hasReachedDC() {
        return this.reachedDC;
    }

    
    public void nextMove() {
        // To check if infiltrator has reached Defender country
        if (this.hasReachedDC())
            return;

        // If not then redetermine the status of the sensors
        this.border.restartCells();

        // After restarting, we check the nearby sensor cells and store their status in a 2D array
        boolean nearbyCells[][] = new boolean[3][3];

        for (int i = -1; i < 2; i += 1) {

            for (int j = -1; j < 2; j += 1) {

                nearbyCells[i+1][j+1] = this.border.isActive(currX + i, currY + j);

            }
            
        }

        // We check front 3 sensors and if any one of them is OFF, we change the coordinates of the infiltrator i.e. the infiltrator moves on that sensor
        for (int j = 1, i = 1; j >= -1; j -= 1) {

            if (nearbyCells[i+1][j+1] == false && nearbyCells[1][1] == false) {

                this.currX += i;
                this.currY += j;
                break;

            }

        }

        this.reachedDC |= this.border.isDC(currX, currY);
    }

}
