package processor.pipeline;

import generic.Instruction;
import generic.Instruction.OperationType;
import processor.Processor;

public class MemoryAccess {
	Processor containingProcessor;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	
	public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
	}
	
	public void performMA()
	{
		if(EX_MA_Latch.isstatus())
		{
			//System.out.println("MA Stage");
			Instruction instruction = EX_MA_Latch.getInstruction();
			OperationType operation = instruction.getOperationType();
			int alu=EX_MA_Latch.getALU();
			MA_RW_Latch.setALU(alu);
			

			if(operation==OperationType.store)
			{
				int store= EX_MA_Latch.getOperand1();
				containingProcessor.getMainMemory().setWord(alu, store);
				
			}
			else if (operation==OperationType.load)
			{
				int load = containingProcessor.getMainMemory().getWord(alu);
				MA_RW_Latch.setLoad(load);
			}

			MA_RW_Latch.setInstruction(instruction);
			EX_MA_Latch.setstatus(false);
			MA_RW_Latch.setRW_status(true);
		}
	}

}
