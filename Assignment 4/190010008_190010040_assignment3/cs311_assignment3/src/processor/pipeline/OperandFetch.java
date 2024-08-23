package processor.pipeline;

import javax.lang.model.util.ElementScanner6;

import generic.Instruction;
import generic.Operand;
import generic.Instruction.OperationType;
import generic.Operand.OperandType;
import generic.Simulator;
import processor.Processor;

public class OperandFetch {
	Processor containingProcessor;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;

	public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch) {
		this.containingProcessor = containingProcessor;
		this.IF_OF_Latch = iF_OF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
	}
	
	static String findTwoscomplement(StringBuffer str) {
		int n = str.length();

		int i;
		for (i = n - 1; i >= 0; i--)
			if (str.charAt(i) == '1')
				break;

		if (i == -1)
			return "1" + str;

		for (int k = i - 1; k >= 0; k--) {

			if (str.charAt(k) == '1')
				str.replace(k, k + 1, "0");
			else
				str.replace(k, k + 1, "1");
		}

		return str.toString();
	}
	static int convert_to_int(String binary_num)
	{
		if (binary_num.charAt(0)=='0')
		{
			return Integer.parseInt(binary_num,2);
		}
		else
		{
			StringBuffer updated = new StringBuffer();
			updated.append(binary_num);
			binary_num = findTwoscomplement(updated);
			int a = Integer.parseInt(binary_num,2);
			return -a;




		}

		
	}

	public void performOF() {
		if (IF_OF_Latch.isOF_enable()) {
			//System.out.println("OF Stage");

			IF_OF_Latch.setOF_enable(false);
			OF_EX_Latch.setEX_enable(true);

			int int_instruction = IF_OF_Latch.getInstruction();
			int total_bits = 32;
			String instruction = Integer.toBinaryString(int_instruction);

			while (instruction.length() != 32) {
				instruction = "0" + instruction;
			}

			String opcode = instruction.substring(0, 5);
			int operation_index = Integer.parseInt(opcode, 2);
			OperationType[] operations = OperationType.values();
			OperationType operation = operations[operation_index];

			Instruction OF_EX_Inst = new Instruction();
			int operand1=0,operand2=0,immediate=0,destination_operand=0;
			switch (operation) {
				case end:
					OF_EX_Inst.setOperationType(operation);
					operand1=0;
					operand2=0;
					Simulator.setSimulationComplete(true);
					break;
				case jmp:
					Operand opr = new Operand();
					String immString = instruction.substring(10, 32);
					immediate = Integer.parseInt(immString, 2);
					if (immString.charAt(0) == '1') {

						StringBuffer temp = new StringBuffer();
						temp.append(immString);
						immString = findTwoscomplement(temp);
						immediate = Integer.parseInt(immString, 2)* -1;

					}

					if (immediate != 0) {
						opr.setOperandType(OperandType.Immediate);
						opr.setValue(immediate);
					} else {
						int register = Integer.parseInt(instruction.substring(5, 10), 2);
						opr.setOperandType(OperandType.Register);
						opr.setValue(register);

					}
					OF_EX_Inst.setOperationType(operation);
					OF_EX_Inst.setDestinationOperand(opr);
					operand1=0;
					operand2=0;


					break;
				case add:
				case sub:
				case mul:
				case div:
				case and:
				case or:
				case xor:
				case slt:
				case sll:
				case srl:
				case sra:
					Operand rs1 = new Operand();
					rs1.setOperandType(OperandType.Register);
					rs1.setValue(Integer.parseInt(instruction.substring(5, 10), 2));
					//System.out.println(rs1.getValue());

					Operand rs2 = new Operand();
					rs2.setOperandType(OperandType.Register);
					rs2.setValue(Integer.parseInt(instruction.substring(10, 15), 2));
					//System.out.println(rs2.getValue());

					Operand rd = new Operand();
					rd.setOperandType(OperandType.Register);
					rd.setValue(Integer.parseInt(instruction.substring(15, 20), 2));
					//System.out.println(rd.getValue());

					OF_EX_Inst.setOperationType(operation);
					OF_EX_Inst.setDestinationOperand(rd);
					OF_EX_Inst.setSourceOperand1(rs1);
					OF_EX_Inst.setSourceOperand2(rs2);
					operand1 = containingProcessor.getRegisterFile().getValue(OF_EX_Inst.getSourceOperand1().getValue());
					operand2 = containingProcessor.getRegisterFile().getValue(OF_EX_Inst.getSourceOperand2().getValue());
					destination_operand = containingProcessor.getRegisterFile().getValue(OF_EX_Inst.getSourceOperand2().getValue());
					System.out.println("add sub cases rs1 "+operand1);
					System.out.println("add sub cases rs2 "+operand2);
					System.out.println("add sub cases destr "+ destination_operand);
					break;
				case beq:
				case bne:
				case blt:
				case bgt:
					rs1 = new Operand();
					rs1.setOperandType(OperandType.Register);
					rs1.setValue(Integer.parseInt(instruction.substring(5, 10), 2));
					//System.out.println(rs1.getValue());

					rs2 = new Operand();
					rs2.setOperandType(OperandType.Register);
					rs2.setValue(Integer.parseInt(instruction.substring(10, 15), 2));
					//System.out.println(rs2.getValue());

					opr = new Operand();
					opr.setOperandType(OperandType.Immediate);
					immString = instruction.substring(15, 32);
					immediate = Integer.parseInt(immString,2);
					if (immString.charAt(0) == '1') {

						StringBuffer temp = new StringBuffer();
						temp.append(immString);
						immString = findTwoscomplement(temp);
						immediate = Integer.parseInt(immString, 2)* -1;

					}
					opr.setValue(immediate);

					OF_EX_Inst.setOperationType(operation);
					OF_EX_Inst.setDestinationOperand(opr);
					OF_EX_Inst.setSourceOperand1(rs1);
					OF_EX_Inst.setSourceOperand2(rs2);
					operand1 = containingProcessor.getRegisterFile().getValue(OF_EX_Inst.getSourceOperand1().getValue());
					operand2 = containingProcessor.getRegisterFile().getValue(OF_EX_Inst.getSourceOperand2().getValue());
					destination_operand = containingProcessor.getRegisterFile().getValue(OF_EX_Inst.getSourceOperand2().getValue());
					System.out.println("bne beq cases imm_val "+ immediate);
					System.out.println("bne beq cases opr1 "+operand1);
					System.out.println("bne beq cases opr2 "+operand2);
					System.out.println("bne beq cases destr "+ destination_operand);
					break;
				case addi:
				case subi:
				case muli:
				case divi:
				case andi:
				case ori:
				case xori:
				case slti:
				case slli:
				case srli:
				case srai:
				case load:
				case store:
				rs1 = new Operand();
				rs1.setOperandType(OperandType.Register);
				rs1.setValue(Integer.parseInt(instruction.substring(5, 10), 2));
				//System.out.println(rs1.getValue());

				rd = new Operand();
				rd.setOperandType(OperandType.Register);
				rd.setValue(Integer.parseInt(instruction.substring(10, 15), 2));
				//System.out.println(rd.getValue());

				opr = new Operand();
				opr.setOperandType(OperandType.Immediate);
				immString = instruction.substring(15, 32);
				immediate = Integer.parseInt(immString,2);
					System.out.println("Immediate String " + immString);
				if (immString.charAt(0) == '1') {

					StringBuffer temp = new StringBuffer();
					temp.append(immString);
					immString = findTwoscomplement(temp);
					immediate = Integer.parseInt(immString, 2) * -1;

				}
				opr.setValue(immediate);
				//System.out.println(immediate);

				OF_EX_Inst.setOperationType(operation);
				OF_EX_Inst.setDestinationOperand(rd);
				OF_EX_Inst.setSourceOperand1(rs1);
				OF_EX_Inst.setSourceOperand2(opr);
				//System.out.println(OF_EX_Inst.getSourceOperand2().getValue());
				operand1 = containingProcessor.getRegisterFile().getValue(OF_EX_Inst.getSourceOperand1().getValue());
					System.out.println("i cases opr1 "+operand1);
					System.out.println("i cases imm "+ immediate);
				destination_operand= containingProcessor.getRegisterFile().getValue(OF_EX_Inst.getDestinationOperand().getValue());
						break;

			}
			OF_EX_Latch.setInstruction(OF_EX_Inst);
			OF_EX_Latch.setOperand1(operand1);
			OF_EX_Latch.setOperand2(operand2);
			OF_EX_Latch.setImmediate(immediate);
			OF_EX_Latch.setDestinationOperand(destination_operand);

		}
	}

}
