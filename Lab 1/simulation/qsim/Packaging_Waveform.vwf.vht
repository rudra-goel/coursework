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
-- Generated on "08/29/2024 17:11:45"
                                                             
-- Vhdl Test Bench(with test vectors) for design  :          Packaging
-- 
-- Simulation tool : 3rd Party
-- 

LIBRARY ieee;                                               
USE ieee.std_logic_1164.all;                                

ENTITY Packaging_vhd_vec_tst IS
END Packaging_vhd_vec_tst;
ARCHITECTURE Packaging_arch OF Packaging_vhd_vec_tst IS
-- constants                                                 
-- signals                                                   
SIGNAL ALERT : STD_LOGIC;
SIGNAL Continue : STD_LOGIC;
SIGNAL Damaged : STD_LOGIC;
SIGNAL DIVERT : STD_LOGIC;
SIGNAL EStop : STD_LOGIC;
SIGNAL ItemPresent : STD_LOGIC;
SIGNAL Oversize : STD_LOGIC;
SIGNAL Overweight : STD_LOGIC;
SIGNAL RUN : STD_LOGIC;
COMPONENT Packaging
	PORT (
	ALERT : OUT STD_LOGIC;
	Continue : IN STD_LOGIC;
	Damaged : IN STD_LOGIC;
	DIVERT : OUT STD_LOGIC;
	EStop : IN STD_LOGIC;
	ItemPresent : IN STD_LOGIC;
	Oversize : IN STD_LOGIC;
	Overweight : IN STD_LOGIC;
	RUN : OUT STD_LOGIC
	);
END COMPONENT;
BEGIN
	i1 : Packaging
	PORT MAP (
-- list connections between master ports and signals
	ALERT => ALERT,
	Continue => Continue,
	Damaged => Damaged,
	DIVERT => DIVERT,
	EStop => EStop,
	ItemPresent => ItemPresent,
	Oversize => Oversize,
	Overweight => Overweight,
	RUN => RUN
	);

-- ItemPresent
t_prcs_ItemPresent: PROCESS
BEGIN
	ItemPresent <= '1';
WAIT;
END PROCESS t_prcs_ItemPresent;

-- Continue
t_prcs_Continue: PROCESS
BEGIN
	Continue <= '0';
WAIT;
END PROCESS t_prcs_Continue;

-- Damaged
t_prcs_Damaged: PROCESS
BEGIN
	Damaged <= '0';
WAIT;
END PROCESS t_prcs_Damaged;

-- EStop
t_prcs_EStop: PROCESS
BEGIN
	EStop <= '0';
WAIT;
END PROCESS t_prcs_EStop;

-- Oversize
t_prcs_Oversize: PROCESS
BEGIN
	Oversize <= '1';
	WAIT FOR 440000 ps;
	Oversize <= '0';
WAIT;
END PROCESS t_prcs_Oversize;

-- Overweight
t_prcs_Overweight: PROCESS
BEGIN
	Overweight <= '0';
	WAIT FOR 680000 ps;
	Overweight <= '1';
	WAIT FOR 440000 ps;
	Overweight <= '0';
WAIT;
END PROCESS t_prcs_Overweight;
END Packaging_arch;
