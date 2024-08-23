	.data
a:
	10
	.text
main:
	load %x0, $a, %x3
	addi %x3, 0, %x4
	addi %x0, 0, %x5
	addi %x0, 10, %x6
	blt %x3, %x0, isNotPalindrome
	blt %x3, %x6, isPalindrome
loop:
	div %x4, %x6, %x4
	beq %x4, %x0, break
	add %x31, %x5, %x5
	mul %x5, %x6, %x5
	jmp loop
break:
	add %x31, %x5, %x5
	beq %x3, %x5, isPalindrome
isNotPalindrome:
	subi %x0, 1, %x10
	end
isPalindrome:
	addi %x0, 1, %x10
	end