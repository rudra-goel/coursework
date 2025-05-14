ORG 0

	LOADI	20		; load 20 into AC
	SUB		LOC1	; AC - Mem[&H1F]
	STORE	LOC2	; Store AC to Mem[&H20]


Finish: 
	JUMP Finish
    
ORG &H1F    
LOC1:	DW	26

ORG &H20
LOC2: 