-- ExternalMemory.VHD
-- 2024.10.22
--
-- This SCOMP peripheral provides one 16-bit word of external memory for SCOMP.
-- Any value written to this peripheral can be read back.
-- Edits By Rudra Goel 10/31/2024, 11/2/2024


LIBRARY IEEE;
LIBRARY LPM;
--specifiy library to use for altsyncram
LIBRARY altera_mf;

USE IEEE.STD_LOGIC_1164.ALL;
USE IEEE.STD_LOGIC_ARITH.ALL;
USE IEEE.STD_LOGIC_UNSIGNED.ALL;
USE LPM.LPM_COMPONENTS.ALL;

--how to actually import the altsyncram megafunction
USE altera_mf.altera_mf_components.all;

ENTITY BetterMemory IS
    PORT(
        -- Active Low Reset Signal
        RESETN      : IN    STD_LOGIC,

        -- Clock signal
        CLK         : IN    STD_LOGIC,

        --Control signals to know when internal signals should latch onto the IO_DATA bus from SCOMP
        VALUE_EN    : IN    STD_LOGIC,
	    ADDRESS_EN  : IN	STD_LOGIC,
        --signal from SCOMP to know which
        IO_WRITE    : IN    STD_LOGIC;
        --16bit Data input from scomp
        IO_DATA     : INOUT STD_LOGIC_VECTOR(15 DOWNTO 0);

        --control pins for altsyncram
        WRITE_EN    : OUT   STD_LOGIC;
        READ_EN     : OUT   STD_LOGIC;

        --connected to altsyncram's data_a and q_a pins
        DATA_TO_MEMORY      : OUT   STD_LOGIC_VECTOR(15 DOWNTO 0);  
        DATA_FROM_MEMORY    : IN    STD_LOGIC_VECTOR(15 DOWNTO 0);
        --address to drive to store/reterive from altsyncram
        ADDRESS             : INOUT STD_LOGIC_VECTOR(15 DOWNTO 0);
    );

END BetterMemory;

ARCHITECTURE a OF BetterMemory IS

    --define the state type enum
    type state_type is (
        IDLE, STORE_VAL, STORE_ADDRESS, WRITE_TO_MEMORY,
        READ_FROM_MEMORY
    );
    
    -- DEFINE INTERNAL SIGNALS THAT WILL LATCH ONTO THE VALUE FROM SCOMP
    -- this signal will only latch onto
    SIGNAL INTERNAL_DATA    : STD_LOGIC_VECTOR(15 DOWNTO 0);
    SIGNAL INTERNAL_ADDRESS : STD_LOGIC_VECTOR(15 DOWNTO 0);
    
    --define state of system
    SIGNAL STATE            : state_type;

BEGIN

    --use altsyncram for memory
    altsyncram_component : altsyncram
    --define the generic params
    --literally copy pasted from lab 8 scomp
    GENERIC MAP (
        --number of words we want this memory module to interact with
        --since the AC is 16 bits wide, we have access to 2^16 diff addresses
        --therefore number of words in memory can be 2^16
        numwords_a => 65536,
        
        --width of the address. since address comes from AC in SCOMP, address size can be 16
        widthad_a => 16,

        --width of the words in memory. since AC can hold 16bits, words in mem should also be 16 bits long
        width_a => 16,

        -- since we are not loading in something to memory when SCOMP starts up
        init_file => "UNUSED",

        clock_enable_input_a => "BYPASS",
        clock_enable_output_a => "BYPASS",
        intended_device_family => "CYCLONE V",
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
        -- control signal to know when to write data_a to memory 
        wren_a      => WRITE_EN,

        --control signal to know when to read in data from altsyncram memory
        rden_a      => READ_EN,
        
        --altsyncram's memory's data input --> where the data is driven into memory
        data_a      => DATA_TO_MEMORY,
        
        --this is the address to write memory to
        address_a   => ADDRESS,

        --we need to generate a clock signal that is faster than SCOMP's clock
        clock0      => CLK,

        --memory's data output --> where the memory should actually spit out the data
        --when SCOMP requests IN data from a specified address
        q_a         => DATA_FROM_MEMORY
    );

    -- Use Intel LPM IP to create tristate drivers
    -- the IO_DATA I/O port in our peripheral must be tristated with the internal signals and data coming from altsyncram
    -- documentation: https://www.intel.com/content/www/us/en/programmable/quartushelp/17.0/hdl/mega/mega_file_lpm_bustri.htm

    --single tristate buffer that will latch IO_DATA bus of SCOMP to the data coming from altsyncram memory
    io_bus_data_from_memory: lpm_bustri
        GENERIC MAP (
        --width of the tristate bus
        lpm_width => 16
    )
    PORT MAP (
        -- only enable the buffer when SCOMP wants to read from memory
        enabledr => READ_EN,

        -- signal that will be driven by input when buffer is enabled 
        result     => IO_DATA,

        -- input to the tristate buffer. this is the input of ou rperipheral that is driven by q_a from altsyncram
        tridata  => DATA_FROM_MEMORY
    );

    --set the outputs of the system based on the FSM
    WITH STATE SELECT READ_EN <=
        '1' WHEN READ_FROM_MEMORY,
        '0' WHEN OTHERS;
    
    WITH STATE SELECT WRITE_EN <=
        '1' WHEN WRITE_TO_MEMORY,
        '0' WHEN OTHERS;

    --latch data to altsyncram memory onto the internal data signal
    DATA_TO_MEMORY <= INTERNAL_DATA;


    --our memory peripheral's FSM will only depend on the CLK and RESETN signals
    PROCESS(CLK, RESETN)
    BEGIN
        IF (RESETN = '0') THEN 
            STATE <= IDLE
        ELSIF (RISING_EDGE(CLK)) THEN
            CASE STATE IS 
                WHEN IDLE =>
                    --logic for idle state
                    IF (VAL_EN = '1' AND IO_WRITE = '1') THEN
                        STATE <= STORE_VAL;
                    ELSIF (ADDRESS_EN = '1' AND IO_WRITE = '0') THEN
                        STATE <= STORE_ADDRESS;
                    END IF;
                WHEN STORE_VAL =>
                    -- logic for store_val state
                    --first latch the internal_data to the IO_DATA bus from SCOMP
                    INTERNAL_DATA <= IO_DATA;

                    --next state logic
                    IF (ADDRESS_EN = '1' AND IO_WRITE = '1') THEN
                        STATE <= STORE_ADDRESS;
                    END IF;


                WHEN STORE_ADDRESS =>
                    -- logic for store_address state
                    --first latch the internal_address to the IO_DATA bus from SCOMP
                    INTERNAL_ADDRESS <= IO_DATA;

                    --next state logic
                    IF (ADDRESS_EN = '1' AND IO_WRITE = '1') THEN
                        STATE <= WRITE_TO_MEMORY;
                    ELSIF (ADDRESS_EN = '1' AND IO_WRITE = '0') THEN
                        STATE <= READ_FROM_MEMORY;
                    END IF;
                WHEN WRITE_TO_MEMORY =>
                    -- logic for write_to_memory state

                    --latch the outputs of the peripheral to the interanl DFFs that store the data and the address
                    ADDRESS <= INTERNAL_ADDRESS;
                    DATA_TO_MEMORY <= INTENRAL_DATA;

                    --next state logic
                    IF (ADDRESS_EN = '0') THEN
                        STATE <= IDLE;
                    END IF;
                WHEN READ_FROM_MEMORY =>
                    -- logic for read_to_memory state

                    IF (ADDRESS_EN = '0') THEN
                        STATE <= IDLE;
                    END IF;
                WHEN OTHERS =>
                        STATE <= IDLE;
            END CASE

        
    END PROCESS;

END a;
