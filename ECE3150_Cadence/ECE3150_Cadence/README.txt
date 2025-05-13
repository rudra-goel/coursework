Please run the following commands to get Cadence to run:

	1. source source.me.first

	2. cd Cadence
	   virtuoso &

The Hspice directory contains a simple example of an inverter. Please run in
the following manner:

	hspice BENCH.sp > out.log

NOTE: The transistor model files are located in the /Hspice/INC/ directory.

The outputs will be dumped into the file out.log and can be examined using
your favorite editor.
Examining the BENCH.sp file will offer some brief insight for HSPICE control
statements.
	
