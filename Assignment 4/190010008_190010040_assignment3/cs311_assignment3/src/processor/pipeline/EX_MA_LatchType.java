package processor.pipeline;

import generic.Instruction;

public class EX_MA_LatchType {
	
	boolean status;
	Instruction instruction;
	int operand1;
	int operand2;
	int immediate;
	int destination_operand;

	int alu;
	
	public EX_MA_LatchType()
	{
		status = false;
	}
	public EX_MA_LatchType(boolean status)
	{
		this.status=status;
	}
	public EX_MA_LatchType(boolean status, int alu) {
		this.status = status;
		this.alu = alu;
	}
	public EX_MA_LatchType(boolean status, int alu_result, Instruction instruction) {
		this.status = status;
		this.alu = alu_result;
		this.instruction = instruction;
	}

	public boolean isstatus() {
		return status;
	}

	public void setstatus(boolean status) {
		this.status = status;
	}
	public Instruction getInstruction()
	{
		return instruction;

	}
	public void setInstruction(Instruction instruction)
	{
		this.instruction=instruction;
	}
	public int getALU()
	{
		return alu;
	}
	public void setalu(int alu)
	{
		this.alu=alu;
	}
	public void setOperand1(int operand1)
	{
		this.operand1=operand1;
	}
	public int getOperand1()
	{
		return operand1;
	}
	public void setOperand2(int operand2)
	{
		this.operand2=operand2;
	}
	public int getOperand2()
	{
		return operand2;
	}
	public void setImmediate(int immediate)
	{
		this.immediate = immediate;
	}
	public int getImmediate()
	{
		return immediate;
	}
	public void setDestinationOperand(int destination_operand)
	{
		this.destination_operand=destination_operand;
	}
	public int getDestinationOperand()
	{
		return destination_operand;
	}



}
