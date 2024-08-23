	.data
a:
	10
	.text
main:
	load %x0, $a, %x3
	addi %x0, 0, %x10
	divi %x3, 2, %x3
	addi %x0, 1, %x4
	beq %x4, %x31, odd
	subi %x0, 1, %x10
	end
odd:
	addi %x10, 1, %x10
	end