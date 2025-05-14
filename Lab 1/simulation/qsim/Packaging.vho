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

-- VENDOR "Altera"
-- PROGRAM "Quartus Prime"
-- VERSION "Version 19.1.0 Build 670 09/22/2019 SJ Lite Edition"

-- DATE "08/29/2024 17:11:48"

-- 
-- Device: Altera 5CSXFC6D6F31C6 Package FBGA896
-- 

-- 
-- This VHDL file should be used for ModelSim-Altera (VHDL) only
-- 

LIBRARY ALTERA_LNSIM;
LIBRARY CYCLONEV;
LIBRARY IEEE;
USE ALTERA_LNSIM.ALTERA_LNSIM_COMPONENTS.ALL;
USE CYCLONEV.CYCLONEV_COMPONENTS.ALL;
USE IEEE.STD_LOGIC_1164.ALL;

ENTITY 	Packaging IS
    PORT (
	RUN : OUT std_logic;
	Continue : IN std_logic;
	ItemPresent : IN std_logic;
	Damaged : IN std_logic;
	EStop : IN std_logic;
	ALERT : OUT std_logic;
	DIVERT : OUT std_logic;
	Oversize : IN std_logic;
	Overweight : IN std_logic
	);
END Packaging;

ARCHITECTURE structure OF Packaging IS
SIGNAL gnd : std_logic := '0';
SIGNAL vcc : std_logic := '1';
SIGNAL unknown : std_logic := 'X';
SIGNAL devoe : std_logic := '1';
SIGNAL devclrn : std_logic := '1';
SIGNAL devpor : std_logic := '1';
SIGNAL ww_devoe : std_logic;
SIGNAL ww_devclrn : std_logic;
SIGNAL ww_devpor : std_logic;
SIGNAL ww_RUN : std_logic;
SIGNAL ww_Continue : std_logic;
SIGNAL ww_ItemPresent : std_logic;
SIGNAL ww_Damaged : std_logic;
SIGNAL ww_EStop : std_logic;
SIGNAL ww_ALERT : std_logic;
SIGNAL ww_DIVERT : std_logic;
SIGNAL ww_Oversize : std_logic;
SIGNAL ww_Overweight : std_logic;
SIGNAL \RUN~output_o\ : std_logic;
SIGNAL \ALERT~output_o\ : std_logic;
SIGNAL \DIVERT~output_o\ : std_logic;
SIGNAL \EStop~input_o\ : std_logic;
SIGNAL \Continue~input_o\ : std_logic;
SIGNAL \ItemPresent~input_o\ : std_logic;
SIGNAL \Damaged~input_o\ : std_logic;
SIGNAL \inst7~0_combout\ : std_logic;
SIGNAL \inst~0_combout\ : std_logic;
SIGNAL \Oversize~input_o\ : std_logic;
SIGNAL \Overweight~input_o\ : std_logic;
SIGNAL \inst4~0_combout\ : std_logic;
SIGNAL \ALT_INV_Overweight~input_o\ : std_logic;
SIGNAL \ALT_INV_Oversize~input_o\ : std_logic;
SIGNAL \ALT_INV_Damaged~input_o\ : std_logic;
SIGNAL \ALT_INV_ItemPresent~input_o\ : std_logic;
SIGNAL \ALT_INV_Continue~input_o\ : std_logic;
SIGNAL \ALT_INV_EStop~input_o\ : std_logic;

BEGIN

RUN <= ww_RUN;
ww_Continue <= Continue;
ww_ItemPresent <= ItemPresent;
ww_Damaged <= Damaged;
ww_EStop <= EStop;
ALERT <= ww_ALERT;
DIVERT <= ww_DIVERT;
ww_Oversize <= Oversize;
ww_Overweight <= Overweight;
ww_devoe <= devoe;
ww_devclrn <= devclrn;
ww_devpor <= devpor;
\ALT_INV_Overweight~input_o\ <= NOT \Overweight~input_o\;
\ALT_INV_Oversize~input_o\ <= NOT \Oversize~input_o\;
\ALT_INV_Damaged~input_o\ <= NOT \Damaged~input_o\;
\ALT_INV_ItemPresent~input_o\ <= NOT \ItemPresent~input_o\;
\ALT_INV_Continue~input_o\ <= NOT \Continue~input_o\;
\ALT_INV_EStop~input_o\ <= NOT \EStop~input_o\;

\RUN~output\ : cyclonev_io_obuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	open_drain_output => "false",
	shift_series_termination_control => "false")
-- pragma translate_on
PORT MAP (
	i => \inst7~0_combout\,
	devoe => ww_devoe,
	o => \RUN~output_o\);

\ALERT~output\ : cyclonev_io_obuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	open_drain_output => "false",
	shift_series_termination_control => "false")
-- pragma translate_on
PORT MAP (
	i => \inst~0_combout\,
	devoe => ww_devoe,
	o => \ALERT~output_o\);

\DIVERT~output\ : cyclonev_io_obuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	open_drain_output => "false",
	shift_series_termination_control => "false")
-- pragma translate_on
PORT MAP (
	i => \inst4~0_combout\,
	devoe => ww_devoe,
	o => \DIVERT~output_o\);

\EStop~input\ : cyclonev_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_EStop,
	o => \EStop~input_o\);

\Continue~input\ : cyclonev_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_Continue,
	o => \Continue~input_o\);

\ItemPresent~input\ : cyclonev_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_ItemPresent,
	o => \ItemPresent~input_o\);

\Damaged~input\ : cyclonev_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_Damaged,
	o => \Damaged~input_o\);

\inst7~0\ : cyclonev_lcell_comb
-- Equation(s):
-- \inst7~0_combout\ = (!\EStop~input_o\ & (((!\ItemPresent~input_o\) # (!\Damaged~input_o\)) # (\Continue~input_o\)))

-- pragma translate_off
GENERIC MAP (
	extended_lut => "off",
	lut_mask => "1010101010100010101010101010001010101010101000101010101010100010",
	shared_arith => "off")
-- pragma translate_on
PORT MAP (
	dataa => \ALT_INV_EStop~input_o\,
	datab => \ALT_INV_Continue~input_o\,
	datac => \ALT_INV_ItemPresent~input_o\,
	datad => \ALT_INV_Damaged~input_o\,
	combout => \inst7~0_combout\);

\inst~0\ : cyclonev_lcell_comb
-- Equation(s):
-- \inst~0_combout\ = (\ItemPresent~input_o\ & \Damaged~input_o\)

-- pragma translate_off
GENERIC MAP (
	extended_lut => "off",
	lut_mask => "0001000100010001000100010001000100010001000100010001000100010001",
	shared_arith => "off")
-- pragma translate_on
PORT MAP (
	dataa => \ALT_INV_ItemPresent~input_o\,
	datab => \ALT_INV_Damaged~input_o\,
	combout => \inst~0_combout\);

\Oversize~input\ : cyclonev_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_Oversize,
	o => \Oversize~input_o\);

\Overweight~input\ : cyclonev_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_Overweight,
	o => \Overweight~input_o\);

\inst4~0\ : cyclonev_lcell_comb
-- Equation(s):
-- \inst4~0_combout\ = (!\EStop~input_o\ & (\ItemPresent~input_o\ & ((\Overweight~input_o\) # (\Oversize~input_o\))))

-- pragma translate_off
GENERIC MAP (
	extended_lut => "off",
	lut_mask => "0000001000100010000000100010001000000010001000100000001000100010",
	shared_arith => "off")
-- pragma translate_on
PORT MAP (
	dataa => \ALT_INV_EStop~input_o\,
	datab => \ALT_INV_ItemPresent~input_o\,
	datac => \ALT_INV_Oversize~input_o\,
	datad => \ALT_INV_Overweight~input_o\,
	combout => \inst4~0_combout\);

ww_RUN <= \RUN~output_o\;

ww_ALERT <= \ALERT~output_o\;

ww_DIVERT <= \DIVERT~output_o\;
END structure;


