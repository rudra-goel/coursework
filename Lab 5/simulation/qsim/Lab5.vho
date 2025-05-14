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

-- DATE "09/30/2024 13:51:10"

-- 
-- Device: Altera 5CSXFC6D6F31C6 Package FBGA896
-- 

-- 
-- This VHDL file should be used for ModelSim-Altera (VHDL) only
-- 

LIBRARY ALTERA;
LIBRARY ALTERA_LNSIM;
LIBRARY CYCLONEV;
LIBRARY IEEE;
USE ALTERA.ALTERA_PRIMITIVES_COMPONENTS.ALL;
USE ALTERA_LNSIM.ALTERA_LNSIM_COMPONENTS.ALL;
USE CYCLONEV.CYCLONEV_COMPONENTS.ALL;
USE IEEE.STD_LOGIC_1164.ALL;

ENTITY 	door_state_machine IS
    PORT (
	clock : IN std_logic;
	resetn : IN std_logic;
	inner : IN std_logic;
	outer : IN std_logic;
	ajar : OUT std_logic
	);
END door_state_machine;

ARCHITECTURE structure OF door_state_machine IS
SIGNAL gnd : std_logic := '0';
SIGNAL vcc : std_logic := '1';
SIGNAL unknown : std_logic := 'X';
SIGNAL devoe : std_logic := '1';
SIGNAL devclrn : std_logic := '1';
SIGNAL devpor : std_logic := '1';
SIGNAL ww_devoe : std_logic;
SIGNAL ww_devclrn : std_logic;
SIGNAL ww_devpor : std_logic;
SIGNAL ww_clock : std_logic;
SIGNAL ww_resetn : std_logic;
SIGNAL ww_inner : std_logic;
SIGNAL ww_outer : std_logic;
SIGNAL ww_ajar : std_logic;
SIGNAL \ajar~output_o\ : std_logic;
SIGNAL \clock~input_o\ : std_logic;
SIGNAL \inner~input_o\ : std_logic;
SIGNAL \outer~input_o\ : std_logic;
SIGNAL \state.undesireable_once~0_combout\ : std_logic;
SIGNAL \resetn~input_o\ : std_logic;
SIGNAL \state.undesireable_once~q\ : std_logic;
SIGNAL \Selector0~0_combout\ : std_logic;
SIGNAL \state.nothing_bad~q\ : std_logic;
SIGNAL \Selector2~0_combout\ : std_logic;
SIGNAL \state.undesireable_twice~q\ : std_logic;
SIGNAL \ALT_INV_outer~input_o\ : std_logic;
SIGNAL \ALT_INV_inner~input_o\ : std_logic;
SIGNAL \ALT_INV_state.undesireable_once~q\ : std_logic;
SIGNAL \ALT_INV_state.nothing_bad~q\ : std_logic;

BEGIN

ww_clock <= clock;
ww_resetn <= resetn;
ww_inner <= inner;
ww_outer <= outer;
ajar <= ww_ajar;
ww_devoe <= devoe;
ww_devclrn <= devclrn;
ww_devpor <= devpor;
\ALT_INV_outer~input_o\ <= NOT \outer~input_o\;
\ALT_INV_inner~input_o\ <= NOT \inner~input_o\;
\ALT_INV_state.undesireable_once~q\ <= NOT \state.undesireable_once~q\;
\ALT_INV_state.nothing_bad~q\ <= NOT \state.nothing_bad~q\;

\ajar~output\ : cyclonev_io_obuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	open_drain_output => "false",
	shift_series_termination_control => "false")
-- pragma translate_on
PORT MAP (
	i => \state.undesireable_twice~q\,
	devoe => ww_devoe,
	o => \ajar~output_o\);

\clock~input\ : cyclonev_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_clock,
	o => \clock~input_o\);

\inner~input\ : cyclonev_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_inner,
	o => \inner~input_o\);

\outer~input\ : cyclonev_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_outer,
	o => \outer~input_o\);

\state.undesireable_once~0\ : cyclonev_lcell_comb
-- Equation(s):
-- \state.undesireable_once~0_combout\ = (!\inner~input_o\ & ((!\outer~input_o\) # ((!\state.nothing_bad~q\)))) # (\inner~input_o\ & (((\state.undesireable_once~q\))))

-- pragma translate_off
GENERIC MAP (
	extended_lut => "off",
	lut_mask => "1010100011111101101010001111110110101000111111011010100011111101",
	shared_arith => "off")
-- pragma translate_on
PORT MAP (
	dataa => \ALT_INV_inner~input_o\,
	datab => \ALT_INV_outer~input_o\,
	datac => \ALT_INV_state.nothing_bad~q\,
	datad => \ALT_INV_state.undesireable_once~q\,
	combout => \state.undesireable_once~0_combout\);

\resetn~input\ : cyclonev_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_resetn,
	o => \resetn~input_o\);

\state.undesireable_once\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \clock~input_o\,
	d => \state.undesireable_once~0_combout\,
	clrn => \resetn~input_o\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \state.undesireable_once~q\);

\Selector0~0\ : cyclonev_lcell_comb
-- Equation(s):
-- \Selector0~0_combout\ = (!\inner~input_o\ & (!\outer~input_o\)) # (\inner~input_o\ & ((\state.undesireable_once~q\)))

-- pragma translate_off
GENERIC MAP (
	extended_lut => "off",
	lut_mask => "1000110110001101100011011000110110001101100011011000110110001101",
	shared_arith => "off")
-- pragma translate_on
PORT MAP (
	dataa => \ALT_INV_inner~input_o\,
	datab => \ALT_INV_outer~input_o\,
	datac => \ALT_INV_state.undesireable_once~q\,
	combout => \Selector0~0_combout\);

\state.nothing_bad\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \clock~input_o\,
	d => \Selector0~0_combout\,
	clrn => \resetn~input_o\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \state.nothing_bad~q\);

\Selector2~0\ : cyclonev_lcell_comb
-- Equation(s):
-- \Selector2~0_combout\ = (!\inner~input_o\ & (\outer~input_o\ & !\state.nothing_bad~q\))

-- pragma translate_off
GENERIC MAP (
	extended_lut => "off",
	lut_mask => "0010000000100000001000000010000000100000001000000010000000100000",
	shared_arith => "off")
-- pragma translate_on
PORT MAP (
	dataa => \ALT_INV_inner~input_o\,
	datab => \ALT_INV_outer~input_o\,
	datac => \ALT_INV_state.nothing_bad~q\,
	combout => \Selector2~0_combout\);

\state.undesireable_twice\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \clock~input_o\,
	d => \Selector2~0_combout\,
	clrn => \resetn~input_o\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \state.undesireable_twice~q\);

ww_ajar <= \ajar~output_o\;
END structure;


