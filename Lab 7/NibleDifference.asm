
ORG 0
	LOAD Value
    AND	LOW_ONES		; AC has 0 for 15-4 and nible for rest
    STORE LOW_NIBLE		; LOW_NIBLE now has bits 3-0 of value
    LOAD Value
    SHIFT -12			; shift 12 bits right to get highest nible
    AND LOW_ONES		; AC has bits 0-3 have highest nible
    STORE HIGH_NIBLE	
    SUB LOW_NIBLE		; high nible - low nible
    JPOS HighBigger
    LOAD LOW_NIBLE		; low nible is bigger since AC is negative since it didn't jump
    STORE RESULT
    JUMP End
    
HighBigger:
	LOAD HIGH_NIBLE
    STORE RESULT
    JUMP End
    
End:
	JUMP End
    
    
Value:		DW	&HFF11
LOW_ONES:	DW	&H000F
LOW_NIBLE:	DW	&H0000
HIGH_NIBLE:	DW	&H0000
RESULT:		DW	&H0000