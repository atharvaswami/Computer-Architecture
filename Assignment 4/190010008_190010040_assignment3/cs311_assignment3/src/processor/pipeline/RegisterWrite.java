package processor.pipeline;

import generic.Instruction;
import generic.Simulator;
import generic.Instruction.OperationType;
import processor.Processor;

public class RegisterWrite {
	Processor containingProcessor;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;
	
	public RegisterWrite(Processor containingProcessor, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch)
	{
		this.containingProcessor = containingProcessor;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
	}
	
	public void performRW()
	{
		if(MA_RW_Latch.IS_status())
		{
			//System.out.println("RW Stage");
			MA_RW_Latch.setRW_status(false);
			IF_EnableLatch.setIF_enable(true);

			Instruction instruction = MA_RW_Latch.getInstruction();
			int alu = MA_RW_Latch.getALU();
			//System.out.println("ALUVALUE");
			//System.out.println(alu);
			OperationType operation = instruction.getOperationType();

			switch(operation)
			{
				
				case bne:
				case blt:
				case bgt:
				case store:
				case jmp:
				case beq:
					break;
				case load:
					int load= MA_RW_Latch.getLoad();
					int destination_register= instruction.getDestinationOperand().getValue();
					containingProcessor.getRegisterFile().setValue(destination_register, load);
					System.out.println("Load Dest loadvalue " + destination_register + " "+load);
					break;
				case end:
					Simulator.setSimulationComplete(true);
					break;
				default:
					destination_register = instruction.getDestinationOperand().getValue();
					containingProcessor.getRegisterFile().setValue(destination_register, alu);
					System.out.println("ALU Load Dest ALUvalue " + destination_register+ " " + alu);

					break;

			}

			MA_RW_Latch.setRW_status(false);
			IF_EnableLatch.setIF_enable(true);
			
		}
	}

}
