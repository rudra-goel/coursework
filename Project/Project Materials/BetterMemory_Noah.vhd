-- BetterMemory.VHD
-- 2024.10.31
--
-- This SCOMP peripheral provides one 16-bit word of external memory for SCOMP.
-- Any value written to this peripheral can be read back.

library ieee;
library altera_mf;
library lpm;

use ieee.std_logic_1164.all;
use ieee.std_logic_arith.all;
use ieee.std_logic_unsigned.all;
use altera_mf.altera_mf_components.all;
use lpm.lpm_components.all;


ENTITY BetterMemory IS
    PORT(
		  CLOCK,
        RESETN,
		  MEM_VAL_EN,  --Using the new outputs from the decoder as opposed to CS
		  MEM_ADDR_EN,
        IO_WRITE : IN    STD_LOGIC;
        IO_DATA  : INOUT STD_LOGIC_VECTOR(15 DOWNTO 0)
    );
END BetterMemory;

ARCHITECTURE a OF BetterMemory IS
	 type state_type is (idle, reading_val, writing_val,
	 reading_addr, writing_addr, init
	 );
	 
	 SIGNAL state   : state_type; 
	 SIGNAL CUR_ADDR: STD_LOGIC_VECTOR(15 DOWNTO 0);
	 SIGNAL CUR_VAL : STD_LOGIC_VECTOR(15 DOWNTO 0);
	 SIGNAL DATA_OUT: STD_LOGIC_VECTOR(15 DOWNTO 0);
	 SIGNAL TEMP_DAT: STD_LOGIC_VECTOR(15 DOWNTO 0);
	 SIGNAL RW_EN   : STD_LOGIC;


	 
BEGIN
	
	-- Altsyncram copy and pasted from SCOMP
	altsyncram_component : altsyncram
	GENERIC MAP (
		numwords_a => 65536,
		widthad_a => 16,
		width_a => 16,
		clock_enable_input_a => "BYPASS",
		clock_enable_output_a => "BYPASS",
		intended_device_family => "MAX 10",
		lpm_hint => "ENABLE_RUNTIME_MOD=NO",
		lpm_type => "altsyncram",
		operation_mode => "SINGLE_PORT",
		outdata_aclr_a => "NONE",
		outdata_reg_a => "UNREGISTERED",
		power_up_uninitialized => "FALSE",
		read_during_write_mode_port_a => "NEW_DATA_NO_NBE_READ",
		width_byteena_a => 1
	)
	PORT MAP (
		wren_a    => RW_EN,
		clock0    => CLOCK,
		address_a => CUR_ADDR,
		data_a    => CUR_VAL,
		q_a       => TEMP_DAT
	);
	
	
    -- Use Intel LPM IP to create tristate drivers
    IO_BUS: lpm_bustri
        GENERIC MAP (
        lpm_width => 16
    )
    PORT MAP (
        enabledt => (MEM_VAL_EN OR MEM_ADDR_EN) AND NOT (IO_WRITE), -- when SCOMP reads
        data     => DATA_OUT,  -- provide this value
        tridata  => IO_DATA    -- driving the IO_DATA bus
    );
	 
	
	PROCESS (resetn, clock)
   BEGIN
		if (resetn = '0') THEN
			state <= init;
			
		elsif (rising_edge(clock)) THEN
		case state is
			when init =>
				CUR_ADDR <= x"0001";
				CUR_VAL <= x"0000";
				RW_EN <= '1';
				
			when idle =>
				IF IO_WRITE = '1' AND MEM_VAL_EN = '1' THEN -- If SCOMP is writing a value to memory
					state <= reading_val;
					
				elsif IO_WRITE = '0' AND MEM_VAL_EN = '1' THEN-- if SCOMP is reading a value from memory
					state <= writing_val;

				elsif IO_WRITE = '1' AND MEM_ADDR_EN = '1' THEN -- IF SCOMP is picking an address for memory
					state <= reading_addr;
					
				elsif IO_WRITE = '0' AND MEM_ADDR_EN = '1' THEN -- if SCOMP is reading the current address for memory, very niche applications
					state <= writing_addr;
				end if;
				
			when reading_val => -- If SCOMP is writing a value to memory
				CUR_VAL <= IO_DATA;  -- store the IO_data into the current value
				RW_EN <= '1'; -- Write value into RAM
				state <= idle;
				
			when writing_val => -- if SCOMP is reading a value from memory
				DATA_OUT <= TEMP_DAT;
				RW_EN <= '0';
				state <= idle;
				
			when reading_addr => -- IF SCOMP is picking an address for memory
				CUR_ADDR <= IO_DATA;
				RW_EN <= '0';
				state <= idle;
				
			when writing_addr => -- if SCOMP is reading the current address for memory, very niche applications
				DATA_OUT <= CUR_ADDR;
				RW_EN <= '0';
				state <= writing_addr;
				
			end case;
		end if;	
	END PROCESS;
	
	END a;
	
	
	
	--PROCESS
   --BEGIN
   --     WAIT UNTIL RISING_EDGE(ENABLE);
   --         IF IO_WRITE = '1' AND MEM_VAL_EN = '1' THEN -- If SCOMP is writing a value to memory
   --            CUR_VAL <= IO_DATA;  -- sample the input on the rising edge of CS
	--				TEMP_ST <= "00";
	--			elsif IO_WRITE = '0' AND MEM_VAL_EN = '1' THEN-- if SCOMP is reading a value from memory
	--				DATA_OUT <= TEMP_DAT;
	--				TEMP_ST <= "01";
	--			elsif IO_WRITE = '1' AND MEM_ADDR_EN = '1' THEN -- IF SCOMP is writing an address to memory
	--				CUR_ADDR <= IO_DATA;
	--				TEMP_ST <= "10";
	--			elsif IO_WRITE = '0' AND MEM_ADDR_EN = '1' THEN -- if SCOMP is reading an address from memory
	--				DATA_OUT <= CUR_ADDR;
	--				TEMP_ST <= "11";
   --        END IF;
   --END PROCESS;
	 
	 
  
	 
	--PROCESS (clock, resetn)
	--BEGIN
	--	if (resetn = '0') then          -- Active-low asynchronous reset
	--		state <= idle;
	--	elsif (rising_edge(clock)) then
	--		case state is
	--			when idle =>
	--				if TEMP_ST = "00" then
	--					state <= reading_val;
	--					--TEMP_ST <= "100";
	--				elsif TEMP_ST = "01" then
	--					state <= writing_val;
	--					--TEMP_ST <= "100";
	--				elsif TEMP_ST = "10" then
	--					state <= reading_addr;
	--					--TEMP_ST <= "100";
	--				elsif TEMP_ST = "11" then
	--					state <= writing_addr;
	--					--TEMP_ST <= "100";
	--				else
	--					state <= idle;	
	--				end if;
	--				
	--			when reading_val =>
	--				RW_EN <= '1';
	--				state <= reading_val2;
	--				
	--			when reading_val2 =>
	--				RW_EN <= '0';
	--				state <= idle;
	--				
	--			when writing_val =>
	--				RW_EN <= '0';
	--				state <= idle;
	--				
	--			when reading_addr =>
	--				state <= idle;
	--				
	--			when writing_addr =>
	--				state <= idle;
	--				
	--		END CASE;
	--	END IF;
--	END PROCESS;



--PROCESS
   --BEGIN
   --     WAIT UNTIL RISING_EDGE(MEM_VAL_EN);
   --         IF IO_WRITE = '1' THEN -- If SCOMP is writing,
   --            CUR_VAL <= IO_DATA;  -- sample the input on the rising edge of CS
	--				TEMP_ST <= "000";
	--			elsif IO_WRITE = '0' THEN -- if SCOMP is reading,
	--				TEMP_ST <= "001";
   --         END IF;
   --END PROCESS;
	 
	--PROCESS
	--BEGIN
	--	 WAIT UNTIL RISING_EDGE(MEM_ADDR_EN);
	--				IF IO_WRITE = '1' THEN -- IF SCOMP is writing,
	--					CUR_ADDR <= IO_DATA;
	--					TEMP_ST <= "010";
	--				elsif IO_WRITE = '0' THEN -- if SCOMP is reading,
	--					TEMP_ST <= "011";
	--				END IF;
	--END PROCESS;