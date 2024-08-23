package generic;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import processor.Clock;
import processor.Processor;

public class Simulator {

	static Processor processor;
	static boolean simulationComplete;

	public static void setupSimulation(String assemblyProgramFile, Processor p) throws IOException {
		Simulator.processor = p;
		loadProgram(assemblyProgramFile);

		simulationComplete = false;
	}

	static void loadProgram(String assemblyProgramFile) throws IOException {

		InputStream input_file = null;
		try {
			input_file = new FileInputStream(assemblyProgramFile);

		} catch (FileNotFoundException error) {
			error.printStackTrace();

		}
		DataInputStream assembly_instructions = new DataInputStream(input_file);

		int current_address = -1;
		int move_offset = 1;
		while (assembly_instructions.available() > 0) {
			int current_inst = assembly_instructions.readInt();
			if (current_address == -1) {
				processor.getRegisterFile().setProgramCounter(current_inst);

			} else {
				processor.getMainMemory().setWord(current_address, current_inst);

			}
			current_address += move_offset;
		}
		processor.getRegisterFile().setValue(0, 0);
		processor.getRegisterFile().setValue(1, 65535);
		processor.getRegisterFile().setValue(2, 65535);

	}

	public static void simulate() {
		while (simulationComplete == false) {
			processor.getIFUnit().performIF();
			Clock.incrementClock();
			processor.getOFUnit().performOF();
			Clock.incrementClock();
			processor.getEXUnit().performEX();
			Clock.incrementClock();
			processor.getMAUnit().performMA();
			Clock.incrementClock();
			processor.getRWUnit().performRW();
			Clock.incrementClock();

			Statistics.setNumberOfInstructions(Statistics.getNumberOfInstructions() + 1);
			Statistics.setNumberOfCycles(Statistics.getNumberOfCycles() + 1);

		}

		
	}

	public static void setSimulationComplete(boolean value) {
		simulationComplete = value;
	}
}
