package processor.pipeline;
import configuration.Configuration;
import generic.*;
import processor.Clock;
import processor.Processor;

public class InstructionFetch implements Element {
	
	Processor containingProcessor;
	IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	EX_IF_LatchType EX_IF_Latch;
	
	public InstructionFetch(Processor containingProcessor, IF_EnableLatchType iF_EnableLatch, IF_OF_LatchType iF_OF_Latch, EX_IF_LatchType eX_IF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_EnableLatch = iF_EnableLatch;
		this.IF_OF_Latch = iF_OF_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
	}
	
	public void performIF()
	{

		if(IF_EnableLatch.isIF_enable()) {
			if (EX_IF_Latch.getIS_status()) {
				int currentPC = EX_IF_Latch.getprogram_counter();
				int newInstruction = containingProcessor.getMainMemory().getWord(currentPC);
				//IF_OF_Latch.setInstruction(newInstruction);
				System.out.println(" Branching Instruction executing with opcode  " + Integer.toBinaryString(newInstruction));
				containingProcessor.getRegisterFile().setProgramCounter(currentPC);
				EX_IF_Latch.setIS_status(false);
			}
			IF_OF_Latch.setOF_enable(true);
			int currentPC = containingProcessor.getRegisterFile().getProgramCounter();
			int newInstruction = containingProcessor.getMainMemory().getWord(currentPC);
			System.out.println(" Normal Instruction executing with opcode  " + Integer.toBinaryString(newInstruction));
			IF_OF_Latch.setInstruction(newInstruction);
			containingProcessor.getRegisterFile().setProgramCounter(currentPC + 1);
			Simulator.getEventQueue().addEvent(
					new MemoryReadEvent(
							Clock.getCurrentTime() + Configuration.mainMemoryLatency,
							(Element) this,
							containingProcessor.getMainMemory(),
							currentPC)
			);
			Statistics.setNumberOfInstructions(Statistics.getNumberOfInstructions() + 1);
			if(Configuration.mainMemoryLatency > containingProcessor.getMax_latency())
			{
				containingProcessor.setMax_latency(Configuration.mainMemoryLatency);
			}
		}


	}
	@Override

	public void handleEvent(Event e) {

	}

}
