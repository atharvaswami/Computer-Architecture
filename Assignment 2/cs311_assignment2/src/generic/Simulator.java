package generic;

import java.io.FileInputStream;

import generic.Instruction.OperationType;

import java.nio.ByteBuffer;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

public class Simulator {

	static FileInputStream inputcodeStream = null;

	public static void setupSimulation(String assemblyProgramFile) {
		int firstCodeAddress = ParsedProgram.parseDataSection(assemblyProgramFile);
		ParsedProgram.parseCodeSection(assemblyProgramFile, firstCodeAddress);
		ParsedProgram.printState();
	}
	public static String opcodeMatch2(Instruction inst)
	{
		String return_value="";
		OperationType temp=inst.getOperationType();
		switch(temp){
			case add: 
			return_value="00000";
			break;
			case addi: 
			return_value="00001";
			break;
			case sub: 
			return_value="00010";
			break;
			case subi: 
			return_value="00011";
			break;
			case mul: 
			return_value="00100";
			break;
			case muli: 
			return_value="00101";
			break;
			case div: 
			return_value="00110";
			break;
			case divi: 
			return_value="00111";
			break;
			case and: 
			return_value="01000";
			break;
			case andi: 
			return_value="01001";
			break;
			case or: 
			return_value="01010";
			break;
			case ori: 
			return_value="01011";
			break;
			case xor: 
			return_value="01100";
			break;
			case xori: 
			return_value="01101";
			break;
			case slt: 
			return_value="01110";
			break;
			case slti: 
			return_value="01111";
			break;
			case sll: 
			return_value="10000";
			break;
			case slli: 
			return_value="10001";
			break;
			case srl: 
			return_value="10010";
			break;
			case srli: 
			return_value="10011";
			break;
			case sra: 
			return_value="10100";
			break;
			case srai: 
			return_value="10101";
			break;
			case load: 
			return_value="10110";
			break;
			case end: 
			return_value="11101";
			break;
			case beq: 
			return_value="11001";
			break;
			case jmp: 
			return_value="11000";
			break;
			case bne: 
			return_value="11010";
			break;
			case blt: 
			return_value="11011";
			break;
			case bgt: 
			return_value="11100";
			break;
			default:
				break;
		}
		return return_value;
	}

	private static String toFixedBinary(int arg1, int arg2) {
		String binary = String.format("%" + arg2 + "s", Integer.toBinaryString(arg1)).replace(' ', '0');
		return binary;
	}

	private static String changeRepr(Operand inst, int precision) {
		if (inst == null)
			return toFixedBinary(0, precision);

		if (inst.getOperandType() == Operand.OperandType.Label)
			return toFixedBinary(ParsedProgram.symtab.get(inst.getLabelValue()), precision);

		return toFixedBinary(inst.getValue(), precision);

	}

	public static void assemble(String objectProgramFile) {
		// TODO your assembler code
		// 1. open the objectProgramFile in binary mode
		// 2. write the firstCodeAddress to the file
		// 3. write the data to the file
		// 4. assemble one instruction at a time, and write to the file
		// 5. close the file
		FileOutputStream outputFile;
		try {
			outputFile = new FileOutputStream(objectProgramFile);
			BufferedOutputStream binFile = new BufferedOutputStream(outputFile);

			byte[] firstAddressCode = ByteBuffer.allocate(4).putInt(ParsedProgram.firstCodeAddress).array();
			binFile.write(firstAddressCode);

			for (int value : ParsedProgram.data) {
				byte[] fileData = ByteBuffer.allocate(4).putInt(value).array();
				binFile.write(fileData);
			}

			for (Instruction inst : ParsedProgram.code) {
				String binInstRepr = "";
				binInstRepr += opcodeMatch2(inst);
				int instOpCode = Integer.parseInt(binInstRepr, 2);
				int programControl = inst.getProgramCounter();

				if (instOpCode <= 20 && instOpCode % 2 == 0) {
					binInstRepr += changeRepr(inst.getSourceOperand1(), 5);
					binInstRepr += changeRepr(inst.getSourceOperand2(), 5);
					binInstRepr += changeRepr(inst.getDestinationOperand(), 5);
					binInstRepr += toFixedBinary(0, 12);
				} else if (instOpCode == 29) {
					binInstRepr += toFixedBinary(0, 27);
				} else if (instOpCode == 24) {
					binInstRepr += toFixedBinary(0, 5);
					int destOperand = Integer.parseInt(changeRepr(inst.getDestinationOperand(), 5), 2) - programControl;
					String bin = toFixedBinary(destOperand, 22);
					binInstRepr += bin.substring(bin.length() - 22);
				} else {
					if (instOpCode >= 25 && instOpCode <= 28) {
						int destOperand = Integer.parseInt(changeRepr(inst.getDestinationOperand(), 5), 2)
								- programControl;
						binInstRepr += changeRepr(inst.getSourceOperand1(), 5);
						binInstRepr += changeRepr(inst.getSourceOperand2(), 5);
						String bin = toFixedBinary(destOperand, 17);
						binInstRepr += bin.substring(bin.length() - 17);
					} else {
						binInstRepr += changeRepr(inst.getSourceOperand1(), 5);
						binInstRepr += changeRepr(inst.getDestinationOperand(), 5);
						binInstRepr += changeRepr(inst.getSourceOperand2(), 17);
					}
				}
				int intRepr = (int) Long.parseLong(binInstRepr, 2);
				byte[] binRepr = ByteBuffer.allocate(4).putInt(intRepr).array();
				binFile.write(binRepr);

			}

			binFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
