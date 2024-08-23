	.data
a:
	70
	80
	40
	20
	10
	30
	50
	60
n:
	8
	.text
main:
	load %x0, $n, %x3
	addi %x0, 0, %x4
	addi %x0, 0, %x5
loop:
	beq %x4, %x3, finish
	load %x4, $a, %x6
	load %x5, $a, %x7
	bgt %x7, %x6, swap
increase:
	addi %x5, 1, %x5
	beq %x5, %x3, next
	jmp loop
next:
	addi %x4, 1, %x4
	addi %x4, 0, %x5
	jmp loop
swap:
	addi %x7, 0, %x8
	addi %x6, 0, %x7
	addi %x8, 0, %x6
	store %x6, $a, %x4
	store %x7, $a, %x5
	jmp increase
finish:
	end