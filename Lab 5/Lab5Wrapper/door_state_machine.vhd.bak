library IEEE;
use IEEE.std_logic_1164.all;

entity door_state_machine is	
	Port (
		clock		:	in std_logic;
		resetn	:	in std_logic;
		inner		:	in std_logic;
		outer		:	in std_logic;
		ajar		:	out std_logic
	);
	
end door_state_machine;

architecture behaviour of door_state_machine is 
	-- make new types for state
	type state_type is (nothing_bad, undesireable_once, undesireable_twice);
	
	signal state, next_state : state_type;
	
	--begin definition of architecture
	begin
		--following process assigns next state to current state
		--also resets state if resetn is low
		--state only changes on rising edges of clock and resetn being low
		process(clock, resetn)
		begin
		--how the following logic only changes on rising clock edges
			if resetn = '0' then
				state <= undesireable_once;
			elsif rising_edge(clock) then
				state <= next_state;
			end if;
		end process;
		
		--process to define combinational logic
		process(state, inner, outer)
		begin
			case state is
				when nothing_bad => 
					if inner = '0' and outer = '1' then
						next_state <= undesireable_once;
					else
						next_state <= nothing_bad;
					end if;
				when undesireable_once =>
					if inner = '0' and outer = '1' then
						next_state <= undesireable_twice;
					elsif inner = '0' and outer = '0' then
						next_state <= nothing_bad;
					else
						next_state <= undesireable_once;
					end if;
				when undesireable_twice =>
					if inner = '0' and outer = '1' then
						next_state <= undesireable_twice;
					else
						next_state <= nothing_bad;
					end if;
			end case;
		end process;
		
		process(state) 
		begin
			case state is
				when undesireable_twice =>
					ajar <= '1';
				when others => 
					ajar <= '0';
			end case;
		end process;
end behaviour;

		