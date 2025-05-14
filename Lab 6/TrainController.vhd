--
--
-- State machine to control trains
-- This template implements the same general path as the example
-- covered in lecture, but does not "release" trains from their
-- stopped state until the other train is completely clear of the
-- relevant sensor.  This is to account for long trains, but that
-- does not affect your assignment this semester. 
--

LIBRARY IEEE;
USE  IEEE.STD_LOGIC_1164.all;
USE  IEEE.STD_LOGIC_ARITH.all;
USE  IEEE.STD_LOGIC_UNSIGNED.all;


ENTITY TrainController IS
	PORT(
		reset, clock, sensor1, sensor2      : IN std_logic;
		sensor3, sensor4, sensor5, sensor6  : IN std_logic;
		switch1, switch2, switch3, switch4  : OUT std_logic;
		dirA, dirB                          : OUT std_logic_vector(1 DOWNTO 0)
	);
END TrainController;


ARCHITECTURE a OF TrainController IS
	-- Create a new TYPE called STATE_TYPE that is only allowed
	-- to have the values specified here. This
	-- 1) enables using helpful names for values instead of
	--    arbitrary values
	-- 2) ensures that signals of this type can only have valid values, and 
	-- 3) helps the synthesis software create efficient hardware for the design.
	TYPE STATE_TYPE IS (
		A1CCW_B3CCW,
		A1CCW_B2CCW,
		A1CCW_B2CW,
		Aarrived_B3CCW, 
		Aarrived_B2CCW, 
		Aarrived_B2CW
	);
	-- Create a signal of the new type.  Note that there is
	-- nothing special about the names "state" or "state_type", but it makes
	-- sense to use these names because those names fit their purpose.
	SIGNAL current_state	:	STATE_TYPE;
	
	-- This creates some new internal signals which will be concatenations
	-- of some of the sensor signals.  This will make CASE statements easier.
	-- Note that the names are *not* what makes them concatenations of the relevant
	-- signals; all of these signals need to be assigned values in the architecture.
	SIGNAL sensor12, sensor14         : std_logic_vector(1 DOWNTO 0);

BEGIN
	-- This is the sequential logic portion that is clocked
	PROCESS (clock, reset)
	BEGIN
		IF reset = '1' THEN
			current_state <= A1CCW_B3CCW;
		ELSIF clock'EVENT and clock='1' THEN
		
			CASE current_state IS
				WHEN A1CCW_B3CCW =>
					CASE sensor12 IS
						WHEN "11" => current_state <= Aarrived_B2CCW;
						WHEN "10" => current_state <= Aarrived_B3CCW;
						WHEN "01" => current_state <= A1CCW_B2CCW;
						WHEN others => current_state <= A1CCW_B3CCW;
					END CASE;
				WHEN Aarrived_B3CCW =>	
					CASE sensor12 IS
						WHEN "11" => current_state <= Aarrived_B2CCW;
						--WHEN "11" => current_state <= Aarrived
						WHEN others => current_state <= Aarrived_B3CCW;
					END CASE;
				WHEN A1CCW_B2CCW => 
					CASE sensor14 IS
						WHEN "11" => current_state <= Aarrived_B2CW;
						WHEN "10" => current_state <= Aarrived_B2CCW;
						WHEN "01" => current_state <= A1CCW_B2CW;
						WHEN others => current_state <= A1CCW_B2CCW;
					END CASE;
				WHEN Aarrived_B2CCW =>
					CASE sensor4 is
						WHEN '1' => current_state <= Aarrived_B2CW;
						WHEN others => current_state <= Aarrived_B2CCW;
					END CASE;
				WHEN A1CCW_B2CW => 
					CASE sensor12 is
						WHEN "10" => current_state <= Aarrived_B2CW;
						WHEN "01" => current_state <= A1CCW_B2CCW;
						WHEN others => current_state <= A1CCW_B2CW;
					END CASE;
				WHEN Aarrived_B2CW => 
					current_state <= Aarrived_B2CW;
			END CASE;
			
		END IF;
	END PROCESS;

	-- Notice that all of the following logic is NOT in a process block,
	-- and thus does not depend on any clock.  Everything here is pure combinational
	-- logic, and exists in parallel with everything else.
	
	
	
	-- These outputs happen to be constant values for this solution;
	-- they do not depend on the state.
	Switch3 <= '0';
	Switch4 <= '0';

	-- Combine bits for the internal signals declared above.
	-- ("&" operator is concatenation)
	sensor12 <= sensor1 & sensor2;
	sensor14 <= sensor1 & sensor4;

	
	
	--since this is moore FSM, the outputs only depend on the current state
	
	WITH current_state SELECT switch1 <=
		'1' WHEN A1CCW_B2CCW,
		'1' WHEN Aarrived_B2CCW,
		'0' WHEN Aarrived_B2CW,
		'1' WHEN A1CCW_B2CW,
		'0' WHEN others;
	WITH current_state SELECT switch2 <= 
		'0' WHEN A1CCW_B2CCW,
		'0' WHEN Aarrived_B2CCW,
		'0' WHEN Aarrived_B2CW,
		'0' WHEN A1CCW_B2CW,
		'0' WHEN others;
	WITH current_state SELECT dirA <=
		"01" WHEN A1CCW_B3CCW,
		"00" WHEN Aarrived_B3CCW,
		"01" WHEN A1CCW_B2CCW,
		"00" WHEN Aarrived_B2CCW,
		"00" WHEN Aarrived_B2CW,
		"01" WHEN A1CCW_B2CW;
	WITH current_state SELECT dirB <=
		"01" WHEN A1CCW_B3CCW,
		"01" WHEN Aarrived_B3CCW,
		"01" WHEN A1CCW_B2CCW,
		"01" WHEN Aarrived_B2CCW,
		"10" WHEN Aarrived_B2CW,
		"10" WHEN A1CCW_B2CW;
		
		
END a;


