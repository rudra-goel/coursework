ORG 0
Start:
    CALL LoadScoresIntoMemory

WaitForAllSwitchesLow:
    IN SWITCHES
    JPOS WaitForAllSwitchesLow

RegularMode:
    IN  SWITCH
    CALL Delay_point_1sec

    AND AUTO_INCREMENT_MASK
    JPOS AutoIncrementMode

    IN SWITCH   ;dont need to delay for switch bounce since did that above

    AND SHOW_GAME_MASK
    JPOS GetAndDisplayScore

    JUMP RegularMode    ; if neither control switches high, loop back



GetAndDisplayScore:
    ;get the address from the first four switches
    IN SWITCH
    AND ADDRESS_MASK

    ;get the value (address of gt game score) from the desired_game (address)
    OUT ADDRESS_EN
    IN VALUE_EN

    ;ac should now have a pointer (address) to the gt score
    OUT ADDRESS_EN  ;set the peripherals internal address signal
    OUT INCR_EN     ; set auto increment to be enabled to retrieve next three scores

    IN VALUE_EN     ;get the actual gt game score
    OUT HEX0        ;diplay the gt score on HEX0

    IN  VALUE_EN    ;get the opponent score from peripheral (bc autoincremented enabled already)
    OUT HEX1        ;display on hex1

    IN  VALUE_EN    ;get the outcome
    JZERO CheckForDisplayGameSwitchDown

    LOADI   &B3FF   
    OUT     LEDs    ;set all LEDs HIGH

CheckForDisplayGameSwitchDown:
    IN SWITCH
    CALL Delay_point_1sec
    AND SHOW_GAME_MASK
    JZERO RegularMode
    JUMP CheckForDisplayGameSwitchDown



AutoIncrementMode:
    IN      SWITCH          ; get the switch value
    AND     ADDRESS_MASK    ; get the starting address to increment from
    STORE   AUTOINCREMENT_ADDRESS

AutoIncrementLoop:
    
    OUT     ADDRESS_EN      ; set the peripherals address
    IN      VALUE_EN        ;read the pointer to the gt game score


    OUT     ADDRESS_EN      ; set that to be address of peripheral internal
    OUT     INCR_EN         ; enable the auto increment mode

    IN      VALUE_EN        ; get the gt game score
    OUT     HEX0            ; display on hex 0

    IN      VALUE_EN        ; get opposing teams score
    OUT     HEX1            ; display on hex1

    IN      VALUE_EN        ; get the outcome
    OUT     INCR_EN         ; turn off auto increment mode

    JZERO   AutoIncrementCheck

    LOADI   &B3FF   
    OUT     LEDs            ;set all LEDs HIGH

AutoIncrementCheck:
    IN  SWITCH
    AND AUTO_INCREMENT_MASK
    JZERO EndAutoIncrementMode

    CALL Delay_1sec

    CALL LoadNextGameAddress

    STORE AUTOINCREMENT_ADDRESS ; store it back for future use

    JUMP  AutoIncrementLoop

EndAutoIncrementMode:
    OUT INCR_EN ;turn off auto increment
    JUMP RegularMode


LoadNextGameAddress:
    LOAD    AUTOINCREMENT_ADDRESS

    ADDI    1   ;add 1
    ADDI    -10 ;check to see if address is >= 11

    JPOS   ResetToOne

EndSubroutine:
    LOAD    AUTOINCREMENT_ADDRESS
    ADDI    1
    RETURN

ResetToOne:
    LOADI   0                       ;reset the autoincrement address to be 1
    STORE   AUTOINCREMENT_ADDRESS   ; store the new reset address
    JUMP    EndSubroutine





Delay_1sec: 					; Function for 1 sec delay
    OUT    TIMER
WaitingLoop:
    IN     TIMER
    ADDI   -10
    JNEG   WaitingLoop
    RETURN

Delay_point_1sec: 					; Function for 0.1 sec delay
    OUT    TIMER
WaitingLoop:
    IN     TIMER
    ADDI   -1
    JNEG   WaitingLoop
    RETURN

LoadScoresIntoMemory:
    LOADI   0           ;set the initial address
    OUT     ADDRESS_EN  ;tell peripheral of initial address
    OUT     INCR_EN     ;set the auto increment feature high
    
    LOADI   11          ;games 1-10 store the address of the GT score
    OUT     VALUE_EN    ;store address of game 1 at 0
    OUT     VALUE_EN    ;store address of game 1 at 1


    ADDI    3
    OUT     VALUE_EN    ;store address of game 2 at 2

    ADDI    3
    OUT     VALUE_EN    ;store address of game 3 at 3

    ADDI    3
    OUT     VALUE_EN    ;store address of game 4 at 4

    ADDI    3
    OUT     VALUE_EN    ;store address of game 5 at 5

    ADDI    3
    OUT     VALUE_EN    ;store address of game 6 at 6

    ADDI    3
    OUT     VALUE_EN    ;store address of game 7 at 7

    ADDI    3
    OUT     VALUE_EN    ;store address of game 8 at 8

    ADDI    3
    OUT     VALUE_EN    ;store address of game 9 at 9

    ADDI    3
    OUT     VALUE_EN    ;store address of game 10 at 10


    ;load the game scores and outcomes for 10 gt games
    LOAD    GAME1_GT
    OUT     VALUE_EN    ;load game and send to memory
    LOAD    GAME1_OTHER
    OUT     VALUE_EN    ;load game and send to memory
    LOAD    OUTCOME_1    ;get the outcome of the game
    OUT     VALUE_EN

    LOAD    GAME2_GT
    OUT     VALUE_EN    ;load game and send to memory
    LOAD    GAME2_OTHER
    OUT     VALUE_EN    ;load game and send to memory
    LOAD    OUTCOME_2    ;get the outcome of the game
    OUT     VALUE_EN

    LOAD    GAME3_GT
    OUT     VALUE_EN    ;load game and send to memory
    LOAD    GAME3_OTHER
    OUT     VALUE_EN    ;load game and send to memory
    LOAD    OUTCOME_3    ;get the outcome of the game
    OUT     VALUE_EN

    LOAD    GAME4_GT
    OUT     VALUE_EN    ;load game and send to memory
    LOAD    GAME4_OTHER
    OUT     VALUE_EN    ;load game and send to memory
    LOAD    OUTCOME_4    ;get the outcome of the game
    OUT     VALUE_EN

    LOAD    GAME5_GT
    OUT     VALUE_EN    ;load game and send to memory
    LOAD    GAME5_OTHER
    OUT     VALUE_EN    ;load game and send to memory
    LOAD    OUTCOME_5    ;get the outcome of the game
    OUT     VALUE_EN

    LOAD    GAME6_GT
    OUT     VALUE_EN    ;load game and send to memory
    LOAD    GAME6_OTHER
    OUT     VALUE_EN    ;load game and send to memory
    LOAD    OUTCOME_6    ;get the outcome of the game
    OUT     VALUE_EN

    LOAD    GAME7_GT
    OUT     VALUE_EN    ;load game and send to memory
    LOAD    GAME7_OTHER
    OUT     VALUE_EN    ;load game and send to memory
    LOAD    OUTCOME_7    ;get the outcome of the game
    OUT     VALUE_EN

    LOAD    GAME8_GT
    OUT     VALUE_EN    ;load game and send to memory
    LOAD    GAME8_OTHER
    OUT     VALUE_EN    ;load game and send to memory
    LOAD    OUTCOME_8    ;get the outcome of the game
    OUT     VALUE_EN

    LOAD    GAME9_GT
    OUT     VALUE_EN    ;load game and send to memory
    LOAD    GAME9_OTHER
    OUT     VALUE_EN    ;load game and send to memory
    LOAD    OUTCOME_9    ;get the outcome of the game
    OUT     VALUE_EN

    LOAD    GAME10_GT
    OUT     VALUE_EN    ;load game and send to memory
    LOAD    GAME10_OTHER
    OUT     VALUE_EN    ;load game and send to memory
    LOAD    OUTCOME_10    ;get the outcome of the game
    OUT     VALUE_EN

    OUT INCR_EN         ;turn auto increment off

    RETURN


;GT football 2024 reccord
GAME1_GT:       DW  24
GAME1_OTHER:    DW  21
OUTCOME_1:       EQU 1

GAME2_GT:       DW  35
GAME2_OTHER:    DW  12
OUTCOME_2:       EQU 1

GAME3_GT:       DW  28
GAME3_OTHER:    DW  31
OUTCOME_3:       EQU 0

GAME4_GT:       DW  59
GAME4_OTHER:    DW  7
OUTCOME_4:       EQU 1

GAME5_GT:       DW  19
GAME5_OTHER:    DW  31
OUTCOME_5:       EQU 0

GAME6_GT:       DW  24
GAME6_OTHER:    DW  14
OUTCOME_6:       EQU 1

GAME7_GT:       DW  41
GAME7_OTHER:    DW  34
OUTCOME_7:       EQU 1

GAME8_GT:       DW  13
GAME8_OTHER:    DW  31
OUTCOME_8:       EQU 0

GAME9_GT:       DW  6
GAME9_OTHER:    DW  21
OUTCOME_9:       EQU 0

GAME10_GT:      DW  28
GAME10_OTHER:   DW  23
OUTCOME_10:      EQU 1


;different masks for reading in the switches
AUTO_INCREMENT_MASK:    EQU &H0020
SHOW_GAME_MASK:         EQU &H0010
ADDRESS_MASK:           EQU &H000F

;different vars for program general
AUTOINCREMENT_ADDRESS:  DW  0

;addresses for BetterMemory peripheral
ADDRESS_EN:  	EQU 	&H0070
VALUE_EN:   	EQU 	&H0071
INCR_EN:		EQU	    &H0072

;address for other peripherals on de-10
SWITCH:     EQU 000
LEDs:       EQU 001
TIMER:      EQU 002
HEX0:       EQU 004
HEX1:       EQU 005