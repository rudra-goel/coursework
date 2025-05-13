* SPICE NETLIST
***************************************

.SUBCKT inverter input_pin vdd! gnd! output_pin
** N=4 EP=4 IP=0 FDC=2
M0 output_pin input_pin gnd! gnd! NMOS_VTL L=5e-08 W=9e-08 AD=5.175e-14 AS=5.175e-14 PD=1.33e-06 PS=1.33e-06 $X=2050 $Y=760 $D=1
M1 output_pin input_pin vdd! vdd! PMOS_VTL L=5e-08 W=3.375e-07 AD=1.94062e-13 AS=1.94062e-13 PD=1.825e-06 PS=1.825e-06 $X=2050 $Y=2600 $D=0
.ENDS
***************************************
