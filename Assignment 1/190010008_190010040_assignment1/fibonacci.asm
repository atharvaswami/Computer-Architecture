	.data
n:
	15
	.text
main:
	load %x0, $n, %x3
	addi %x0, 65535, %x4
	addi %x0, 0, %x5
	addi %x0, 1, %x6
	addi %x0, 1, %x7
	store %x5, 0, %x4
	subi %x4, 1, %x4
	store %x6, 0, %x4
	jmp increase
loop:
	beq %x7, %x3, finish
	add %x5, %x6, %x8
	subi %x4, 1, %x4
	store %x8, 0, %x4
change:
	addi %x6, 0, %x5
	addi %x8, 0, %x6
increase:
	addi %x7, 1, %x7
	jmp loop
finish:
	 end