package processor.pipeline;

import processor.Processor;

import java.util.Arrays;

import generic.*;
import generic.Instruction.OperationType;
import generic.Operand.OperandType;

public class Execute {
	Processor containingProcessor;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	EX_IF_LatchType EX_IF_Latch;

	public Execute(Processor containingProcessor, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch,
			EX_IF_LatchType eX_IF_Latch) {
		this.containingProcessor = containingProcessor;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
	}

	public void performEX() {
		if (OF_EX_Latch.isEX_enable()) {
			//System.out.println("EX Stage");
			OF_EX_Latch.setEX_enable(false);
			EX_MA_Latch.setstatus(true);
			Instruction instruction = OF_EX_Latch.getInstruction();
			EX_MA_Latch.setInstruction(instruction);
			EX_MA_Latch.setOperand1(OF_EX_Latch.getOperand1());
			EX_MA_Latch.setOperand2(OF_EX_Latch.getOperand2());
			EX_MA_Latch.setImmediate(OF_EX_Latch.getImmediate());
			EX_MA_Latch.setDestinationOperand(OF_EX_Latch.getDestinationOperand());
			OperationType operation = instruction.getOperationType();
			int opcode = Arrays.asList(OperationType.values()).indexOf(operation);
			int currentPC = containingProcessor.getRegisterFile().getProgramCounter() - 1;

			int alu = 0;
			//System.out.println("Opcode " + opcode);
			//System.out.println(OperationType.values()[opcode]);
			if(opcode % 2 == 0 && opcode < 21) {

				int operand1 = OF_EX_Latch.getOperand1();
				int operand2 = OF_EX_Latch.getOperand2();
				//System.out.println("Operand 1 " + operand1);
				//System.out.println("Operand 2 " + operand2);
				//System.out.println("Opcode " + opcode);
				System.out.println(operation);
				System.out.println("operand 1 for alu calc " + operand1);
				System.out.println("operand 2 i:e immediate for calc " + operand2);
				switch(operation) {
					case add:
						alu = (operand1 + operand2);
						break;
					case mul:
						alu = (operand1 * operand2);
						break;
					case sub:
						alu = (operand1 - operand2);
						break;
					case load:
						break;
					case and:
						alu = (operand1 & operand2);
						break;
					case div:
						alu = (operand1 / operand2);
						int remainder = (operand1 % operand2);
						containingProcessor.getRegisterFile().setValue(31, remainder);
						break;
					case xor:
						alu = (operand1 ^ operand2);
						break;
					case or:
						alu = (operand1 | operand2);
						break;
					case store:
						break;						
					case slt:
						if(operand1 < operand2)
							alu = 1;
						else
							alu = 0;
						break;
					case srli:
						break;
					case srl:
						alu = (operand1 >>> operand2);
						break;
					case sll:
						alu = (operand1 << operand2);
						break;
					case sra:
						alu = (operand1 >> operand2);
						break;
					case end:
						break;
					default:
						break;
				}
				EX_MA_Latch.setalu(alu);
			}
			else if(opcode < 23) {
				int operand1 = OF_EX_Latch.getOperand1();
				int operand2 = OF_EX_Latch.getImmediate();
				System.out.println(operation);
				System.out.println("operand 1 for alu calc " + operand1);
				System.out.println("operand 2 i:e immediate for calc " + operand2);


				switch(operation) {

					case addi:
						alu = (operand1 + operand2);
						break;
					case muli:
						alu = (operand1 * operand2);
						break;
					case beq:
						break;
					case subi:
						alu = (operand1 - operand2);
						break;
					case andi:
						alu = (operand1 & operand2);
						break;
					case end:
						break;
					case xori:
						alu = (operand1 ^ operand2);
						break;
					case ori:
						alu = (operand1 | operand2);
						break;
					case divi:
						alu = (operand1 / operand2);

						int remainder = (operand1 % operand2);
						containingProcessor.getRegisterFile().setValue(31, remainder);
						break;
					case jmp:
						break;
					case srli:
						alu = (operand1 >>> operand2);
						break;
					case slti:
						if(operand1 < operand2)
							alu = 1;
						else
							alu = 0;
						break;
					case slli:
						alu = (operand1 << operand2);
						break;
					case load:
						alu = (operand1 + operand2);
						break;
					case srai:
						alu = (operand1 >> operand2);
						break;
					default:
						break;
				}
				EX_MA_Latch.setalu(alu);

			}
			else if(opcode == 23) {

				int operand1 = OF_EX_Latch.getDestinationOperand();
				int operand2 = OF_EX_Latch.getImmediate();
				alu = operand1 + operand2;
				EX_MA_Latch.setalu(alu);
			}

			else if(opcode == 24) {

				OperandType optype = instruction.getDestinationOperand().getOperandType();
				int immediate = 0;
				if (optype == OperandType.Register) 
					immediate = OF_EX_Latch.getDestinationOperand();
				else 
					immediate = instruction.getDestinationOperand().getValue();

				alu = immediate + currentPC;
				EX_IF_Latch.setIS_status(true, alu);
				EX_MA_Latch.setstatus(false);
				EX_MA_Latch.setalu(alu);

			}

			else if(opcode < 29) {

				int immediate = instruction.getDestinationOperand().getValue();
				int operand1 = OF_EX_Latch.getOperand1();
				int operand2 = OF_EX_Latch.getOperand2();
				switch(operation) {

					case beq:
						if(operand1 == operand2) {

							alu = immediate + currentPC;
							EX_IF_Latch.setIS_status(true, alu);
							EX_MA_Latch.setstatus(false);
						}
						break;
					case bne:
						if(operand1 != operand2) {

							alu = immediate + currentPC;
							EX_IF_Latch.setIS_status(true, alu);
							EX_MA_Latch.setstatus(false);
						}

						break;
					case blt:
						if(operand1 < operand2) {

							alu = immediate + currentPC;
							EX_IF_Latch.setIS_status(true, alu);
							EX_MA_Latch.setstatus(false);
						}
						break;
					case bgt:
						if(operand1 > operand2) {

							alu = immediate + currentPC;
							EX_IF_Latch.setIS_status(true, alu);
							EX_MA_Latch.setstatus(false);
						}
						break;
					default:
						break;
				}
				EX_MA_Latch.setalu(alu);



			

			


		}
	}

}
}
