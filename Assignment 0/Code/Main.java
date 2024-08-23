import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.Scanner;
import java.util.Arrays;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.ArrayList;

public class Main {

    // This function calulates the average time taken for each input pair of width and probability
    public static double averageTime(int[] infiltrationTime) {

        // Check whether the input is empty or not
        assert infiltrationTime.length > 0 : "Input atleast 1 test case";
        
        // Calculate total time
        int sum = 0;

        for (double currentTime : infiltrationTime)
            sum += currentTime;

        // Calculate and return the average time taken
        return sum / infiltrationTime.length;
    }

    public static double startSimulation(double headsProbability, int borderLength, int borderWidth) {

        // Iterate over 70 times for each input pair to get better results
        int totalInfiltrators = 70;

        int[] infiltrationTime = new int[totalInfiltrators];

        for (int i = 0; i < totalInfiltrators; i += 1) {

            // Create an object for each infiltrator corresponding to a particular input pair
            Infiltrator soldier = new Infiltrator(borderLength, borderWidth, headsProbability);

            // Create a clock object for a duration of 10 secs (1 sec to decide the next move and 9 secs to travel from one cell to another)
            Clock watch = new Clock(10);

            // Loop untill the infiltrator reaches the defending country
            while (!soldier.hasReachedDC()) {

                // Decide the next cell for the soldier to travel
                soldier.nextMove();

                // Update the timer after each move
                watch.changeTime();
            }

            infiltrationTime[i] = watch.changeTime();
        }

        // Return the average time taken by 70 infiltrations for a particular width and probability
        return averageTime(infiltrationTime);
    }

    public static void main(String[] args) {

        // Take the input and output files from the CLI arguments and store them
        String inputFile = args[0];
        String outputFile = args[1];

        System.out.println("\nInput File: " + inputFile);
        System.out.println("\nOutput File: " + outputFile);

        System.out.println("\nSimulating... ");

        // Create a scanner object
        Scanner input;

        try {

            // Give the input file as an input to the scanner object
            input = new Scanner(new File(inputFile));

            // Create a printwriter object to write the outputs into the output file
            PrintWriter writer = new PrintWriter(outputFile);

            // Read the list of widths and probabilities from the input file
            ArrayList<Integer> widths = Stream.of(input.nextLine().split(" ")).map(i -> Integer.valueOf(i)).collect(Collectors.toCollection(ArrayList:: new));
            ArrayList<Double> probabilities = Stream.of(input.nextLine().split(" ")).map(i -> Double.valueOf(i)).collect(Collectors.toCollection(ArrayList:: new));

            // Create an array to store infiltration times
            double[] infiltrationTime = new double[widths.size()*probabilities.size()];

            // Assign a large number to the border length compared to the border width to act like infinity 
            int borderLength = 1000;

            for (int i = 0, k = 0; i < probabilities.size(); i += 1) {

                for (int j = 0; j < widths.size(); j += 1) {

                    // Calculate the infiltration times by simulating every infiltrator
                    infiltrationTime[k] = startSimulation(probabilities.get(i), borderLength, widths.get(j));

                    // Write the output into the output file 
                    // Output Format: Width, Probability, Infiltration Time
                    String data = String.valueOf(widths.get(j)) + ", " + String.valueOf(probabilities.get(i)) + ", " + String.valueOf(infiltrationTime[k]);
                    writer.println(data);

                    k += 1;

                }

            }

            // Close the output file writer
            writer.close();

            System.out.println(Arrays.toString(infiltrationTime));

        } catch (FileNotFoundException e) {

            // Output the error messages
            e.printStackTrace();

        }

    }

}