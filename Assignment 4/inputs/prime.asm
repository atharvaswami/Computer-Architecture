	.data
a:
	10
	.text
main:
	load %x0, $a, %x3
	addi %x0, 0, %x31
	addi %x0, 2, %x5
	divi %x3, 2, %x4
	addi %x4, 1, %x4
	bgt %x3, 1, loop
	jmp isNotPrime
loop:
	beq %x4, %x5, isPrime
	div %x3, %x5, %x6
	beq %x0, %x31, isNotPrime
	addi %x5, 1, %x5
	jmp loop
isNotPrime:
	subi %x0, 1, %x10
	end
isPrime:
	addi %x0, 1, %x10
	end