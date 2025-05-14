-- Copyright (C) 2019  Intel Corporation. All rights reserved.
-- Your use of Intel Corporation's design tools, logic functions 
-- and other software and tools, and any partner logic 
-- functions, and any output files from any of the foregoing 
-- (including device programming or simulation files), and any 
-- associated documentation or information are expressly subject 
-- to the terms and conditions of the Intel Program License 
-- Subscription Agreement, the Intel Quartus Prime License Agreement,
-- the Intel FPGA IP License Agreement, or other applicable license
-- agreement, including, without limitation, that your use is for
-- the sole purpose of programming logic devices manufactured by
-- Intel and sold by Intel or its authorized distributors.  Please
-- refer to the applicable agreement for further details, at
-- https://fpgasoftware.intel.com/eula.

-- *****************************************************************************
-- This file contains a Vhdl test bench with test vectors .The test vectors     
-- are exported from a vector file in the Quartus Waveform Editor and apply to  
-- the top level entity of the current Quartus project .The user can use this   
-- testbench to simulate his design using a third-party simulation tool .       
-- *****************************************************************************
-- Generated on "09/30/2024 13:51:08"
                                                             
-- Vhdl Test Bench(with test vectors) for design  :          door_state_machine
-- 
-- Simulation tool : 3rd Party
-- 

LIBRARY ieee;                                               
USE ieee.std_logic_1164.all;                                

ENTITY door_state_machine_vhd_vec_tst IS
END door_state_machine_vhd_vec_tst;
ARCHITECTURE door_state_machine_arch OF door_state_machine_vhd_vec_tst IS
-- constants                                                 
-- signals                                                   
SIGNAL ajar : STD_LOGIC;
SIGNAL clock : STD_LOGIC;
SIGNAL inner : STD_LOGIC;
SIGNAL outer : STD_LOGIC;
SIGNAL resetn : STD_LOGIC;
COMPONENT door_state_machine
	PORT (
	ajar : OUT STD_LOGIC;
	clock : IN STD_LOGIC;
	inner : IN STD_LOGIC;
	outer : IN STD_LOGIC;
	resetn : IN STD_LOGIC
	);
END COMPONENT;
BEGIN
	i1 : door_state_machine
	PORT MAP (
-- list connections between master ports and signals
	ajar => ajar,
	clock => clock,
	inner => inner,
	outer => outer,
	resetn => resetn
	);

-- clock
t_prcs_clock: PROCESS
BEGIN
LOOP
	clock <= '0';
	WAIT FOR 50000 ps;
	clock <= '1';
	WAIT FOR 50000 ps;
	IF (NOW >= 1000000 ps) THEN WAIT; END IF;
END LOOP;
END PROCESS t_prcs_clock;

-- resetn
t_prcs_resetn: PROCESS
BEGIN
	resetn <= '1';
	WAIT FOR 10000 ps;
	resetn <= '0';
	WAIT FOR 50000 ps;
	resetn <= '1';
WAIT;
END PROCESS t_prcs_resetn;

-- inner
t_prcs_inner: PROCESS
BEGIN
	inner <= '0';
	WAIT FOR 920000 ps;
	inner <= '1';
	WAIT FOR 60000 ps;
	inner <= '0';
WAIT;
END PROCESS t_prcs_inner;

-- outer
t_prcs_outer: PROCESS
BEGIN
	outer <= '0';
	WAIT FOR 130000 ps;
	outer <= '1';
	WAIT FOR 40000 ps;
	outer <= '0';
	WAIT FOR 60000 ps;
	outer <= '1';
	WAIT FOR 40000 ps;
	outer <= '0';
	WAIT FOR 170000 ps;
	outer <= '1';
	WAIT FOR 50000 ps;
	outer <= '0';
	WAIT FOR 250000 ps;
	outer <= '1';
	WAIT FOR 50000 ps;
	outer <= '0';
	WAIT FOR 50000 ps;
	outer <= '1';
	WAIT FOR 50000 ps;
	outer <= '0';
WAIT;
END PROCESS t_prcs_outer;
END door_state_machine_arch;
