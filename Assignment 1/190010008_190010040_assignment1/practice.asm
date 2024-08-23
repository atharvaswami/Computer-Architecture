    .data
a:
    1
    2
    3
    4
    5
    6
    7
    8
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
incj:
    addi %x5, 1, %x5
    beq %x5, %x3, inci
    jmp loop
inci:
    addi %x4, 1, %x4
    addi %x4, 0, %x5
    jmp loop
swap:
    addi %x7, 0, %x8
    addi %x6, 0, %x7
    addi %x8, 0, %x6
    store %x6, $a, %x4
    store %x7, $a, %x5
    jmp incj
finish:
    end