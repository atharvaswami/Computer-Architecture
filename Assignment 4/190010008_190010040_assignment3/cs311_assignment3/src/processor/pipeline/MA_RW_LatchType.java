package processor.pipeline;

import generic.Instruction;

public class MA_RW_LatchType {
	
	boolean RW_status;
	Instruction instruction;
	int load;
	int alu;
	int operand1;
	int operand2;
	int immediate;
	int destination_operand;
	
	public MA_RW_LatchType() {
	
		this.RW_status = false;
	}

	public MA_RW_LatchType(boolean rw_status) {

		this.RW_status = rw_status;
	}

	public MA_RW_LatchType(boolean rw_status, Instruction instruction) {

		this.RW_status = rw_status;
		this.instruction = instruction;
	}

	public MA_RW_LatchType(boolean rw_status, Instruction instruction, int load) {

		this.RW_status = rw_status;
		this.instruction = instruction;
		this.load = load;
	}

	public MA_RW_LatchType(boolean rw_status, Instruction instruction, int load, int alu) {

		this.RW_status = rw_status;
		this.instruction = instruction;
		this.load = load;
		this.alu = alu;
	}

	public boolean IS_status() {
	
		return RW_status;
	}

	public void setRW_status(boolean rw_status) {
	
		this.RW_status = rw_status;
	}

	public Instruction getInstruction() {
	
		return instruction;
	}

	public void setInstruction(Instruction inst) {
	
		this.instruction = inst;
	}

	public void setLoad(int result) {
	
		this.load = result;
	}

	public int getLoad() {
	
		return load;
	}

	public int getALU() {
	
		return alu;
	}

	public void setALU(int result) {
	
		this.alu = result;
	}

}
