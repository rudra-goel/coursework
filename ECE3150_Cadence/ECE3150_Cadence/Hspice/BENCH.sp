
.option post INGOLD=2

.param VDDC = 1.0V
.param LoadCap = 10f

.TEMP 25

.tran 1p 6n 

VD VDD 0 VDDC
VA Vin 0 Pulse (0 VDDC 1n 50p 50p 2.45n 5n)
CL Vout 0 LoadCap

.MEASURE TRAN tf TRIG V(Vin) VAL=0.5 RISE=1 targ V(Vout) VAL=0.5 FALL=1
.MEASURE TRAN tr TRIG V(Vin) VAL=0.5 FALL=1 targ V(Vout) VAL=0.5 RISE=1

.include './INC/NMOS_VTL.inc'
.include './INC/PMOS_VTL.inc'
.include 'INVX1.netlist'

x0 Vin Vout 0 VDD INVX1

.end
