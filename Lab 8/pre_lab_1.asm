
ORG 0
LOADI -1				;put -1 into AC
SHIFT 11				;shift AC 11 bits left -> -1*2^11 is -2024
ADDI -1023				;
ADDI -262				;bring AC to -3333

CALL CountSetBits		; Perform Subroutine
STORE Result1			; store answer into memory

CALL CountSetBits		; count bits (set) on that anser to the previous question
STORE Result2			; store result

Infinite:
	JUMP Infinite		;infinite loop

CountSetBits:
	STORE Original_Val	;save intended value
    LOADI 16			;reinitialize loop counter
    STORE Loop_Counter	;Store the initial value of loop counter
    LOADI 0
    STORE Set_Bits_Count
    
SubroutineLoop:
	LOAD Loop_Counter	;load the counter into AC
    JZERO END			;if we did all 16 iterations, return
    
    LOAD Original_Val	;get original val in AC
    AND Mask			;do AC AND 0x0001 to see if LSB is 1 or zero
    ADD Set_Bits_Count	;add that 0 or 1 to the set_bits_count
    STORE Set_Bits_Count	;Store updated count
    
    LOAD Loop_Counter	;get the loop counter
    ADDI -1				;decrement loop counter
    STORE Loop_Counter	;store updated loop counter
    
    LOAD Original_Val	;get the val back
    SHIFT -1			;shift right by one bit to get next LSB
    STORE Original_Val	;restore the new shifted value
    
    JUMP SubroutineLoop	;loop back to top
    
END:
	LOAD Set_Bits_Count	;load the number of set bits into memory
	RETURN
    
;Subroutine Variables
Set_Bits_Count:	DW	0
Original_Val:	DW	0
Loop_Counter:	DW	0
Mask:			DW	1

;Result Locations
Result1:		DW	0
Result2:		DW	0




