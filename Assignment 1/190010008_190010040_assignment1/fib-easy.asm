    .data
n:
    1
    .text
main:
    load %x0, $n, %x3
    addi %x0, 65535, %x4
    addi %x0, 0, %x5
    addi %x0, 1, %x6
    addi %x0, 1, %x7
    store %x5, 0, %x4
    beq %x7, %x3, finish
    subi %x4, 1, %x4
    store %x6, 0, %x4
loop:
    addi %x7, 1, %x7
    beq %x7, %x3, finish
    add %x5, %x6, %x8
    subi %x4, 1, %x4
    store %x8, 0, %x4
    addi %x6, 0, %x5
    addi %x8, 0, %x6
    jmp loop
finish:
    end