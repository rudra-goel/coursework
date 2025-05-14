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

-- DATE "08/29/2024 17:23:19"

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

-- Design Ports Information
-- RUN	=>  Location: PIN_AA24,	 I/O Standard: 3.3-V LVTTL,	 Current Strength: 16mA
-- ALERT	=>  Location: PIN_AC22,	 I/O Standard: 3.3-V LVTTL,	 Current Strength: 16mA
-- DIVERT	=>  Location: PIN_AD24,	 I/O Standard: 3.3-V LVTTL,	 Current Strength: 16mA
-- EStop	=>  Location: PIN_AA30,	 I/O Standard: 3.3-V LVTTL,	 Current Strength: Default
-- Continue	=>  Location: PIN_AC29,	 I/O Standard: 3.3-V LVTTL,	 Current Strength: Default
-- ItemPresent	=>  Location: PIN_AB30,	 I/O Standard: 3.3-V LVTTL,	 Current Strength: Default
-- Damaged	=>  Location: PIN_Y27,	 I/O Standard: 3.3-V LVTTL,	 Current Strength: Default
-- Oversize	=>  Location: PIN_AC30,	 I/O Standard: 3.3-V LVTTL,	 Current Strength: Default
-- Overweight	=>  Location: PIN_AB28,	 I/O Standard: 3.3-V LVTTL,	 Current Strength: Default


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
SIGNAL \~QUARTUS_CREATED_GND~I_combout\ : std_logic;
SIGNAL \EStop~input_o\ : std_logic;
SIGNAL \ItemPresent~input_o\ : std_logic;
SIGNAL \Continue~input_o\ : std_logic;
SIGNAL \Damaged~input_o\ : std_logic;
SIGNAL \inst7~0_combout\ : std_logic;
SIGNAL \inst~0_combout\ : std_logic;
SIGNAL \Overweight~input_o\ : std_logic;
SIGNAL \Oversize~input_o\ : std_logic;
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

-- Location: IOOBUF_X89_Y11_N45
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
	o => ww_RUN);

-- Location: IOOBUF_X86_Y0_N2
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
	o => ww_ALERT);

-- Location: IOOBUF_X88_Y0_N37
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
	o => ww_DIVERT);

-- Location: IOIBUF_X89_Y21_N21
\EStop~input\ : cyclonev_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_EStop,
	o => \EStop~input_o\);

-- Location: IOIBUF_X89_Y21_N4
\ItemPresent~input\ : cyclonev_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_ItemPresent,
	o => \ItemPresent~input_o\);

-- Location: IOIBUF_X89_Y20_N95
\Continue~input\ : cyclonev_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_Continue,
	o => \Continue~input_o\);

-- Location: IOIBUF_X89_Y25_N21
\Damaged~input\ : cyclonev_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_Damaged,
	o => \Damaged~input_o\);

-- Location: LABCELL_X88_Y21_N0
\inst7~0\ : cyclonev_lcell_comb
-- Equation(s):
-- \inst7~0_combout\ = ( \Continue~input_o\ & ( \Damaged~input_o\ & ( !\EStop~input_o\ ) ) ) # ( !\Continue~input_o\ & ( \Damaged~input_o\ & ( (!\EStop~input_o\ & !\ItemPresent~input_o\) ) ) ) # ( \Continue~input_o\ & ( !\Damaged~input_o\ & ( 
-- !\EStop~input_o\ ) ) ) # ( !\Continue~input_o\ & ( !\Damaged~input_o\ & ( !\EStop~input_o\ ) ) )

-- pragma translate_off
GENERIC MAP (
	extended_lut => "off",
	lut_mask => "1010101010101010101010101010101010100000101000001010101010101010",
	shared_arith => "off")
-- pragma translate_on
PORT MAP (
	dataa => \ALT_INV_EStop~input_o\,
	datac => \ALT_INV_ItemPresent~input_o\,
	datae => \ALT_INV_Continue~input_o\,
	dataf => \ALT_INV_Damaged~input_o\,
	combout => \inst7~0_combout\);

-- Location: LABCELL_X88_Y21_N9
\inst~0\ : cyclonev_lcell_comb
-- Equation(s):
-- \inst~0_combout\ = ( \Damaged~input_o\ & ( \ItemPresent~input_o\ ) )

-- pragma translate_off
GENERIC MAP (
	extended_lut => "off",
	lut_mask => "0000000000000000000000000000000001010101010101010101010101010101",
	shared_arith => "off")
-- pragma translate_on
PORT MAP (
	dataa => \ALT_INV_ItemPresent~input_o\,
	dataf => \ALT_INV_Damaged~input_o\,
	combout => \inst~0_combout\);

-- Location: IOIBUF_X89_Y21_N38
\Overweight~input\ : cyclonev_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_Overweight,
	o => \Overweight~input_o\);

-- Location: IOIBUF_X89_Y25_N55
\Oversize~input\ : cyclonev_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_Oversize,
	o => \Oversize~input_o\);

-- Location: LABCELL_X88_Y21_N12
\inst4~0\ : cyclonev_lcell_comb
-- Equation(s):
-- \inst4~0_combout\ = ( \Oversize~input_o\ & ( (!\EStop~input_o\ & \ItemPresent~input_o\) ) ) # ( !\Oversize~input_o\ & ( (!\EStop~input_o\ & (\Overweight~input_o\ & \ItemPresent~input_o\)) ) )

-- pragma translate_off
GENERIC MAP (
	extended_lut => "off",
	lut_mask => "0000001000000010000010100000101000000010000000100000101000001010",
	shared_arith => "off")
-- pragma translate_on
PORT MAP (
	dataa => \ALT_INV_EStop~input_o\,
	datab => \ALT_INV_Overweight~input_o\,
	datac => \ALT_INV_ItemPresent~input_o\,
	datae => \ALT_INV_Oversize~input_o\,
	combout => \inst4~0_combout\);

-- Location: LABCELL_X48_Y5_N0
\~QUARTUS_CREATED_GND~I\ : cyclonev_lcell_comb
-- Equation(s):

-- pragma translate_off
GENERIC MAP (
	extended_lut => "off",
	lut_mask => "0000000000000000000000000000000000000000000000000000000000000000",
	shared_arith => "off")
-- pragma translate_on
;
END structure;


