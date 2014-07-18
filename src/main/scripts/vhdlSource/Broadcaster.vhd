
library IEEE;

use IEEE.STD_LOGIC_1164.ALL;
use ieee.numeric_std.all;

use work.txt_util.all;
use WORK.CNFTConfiguration.all;

--05/03/14
--can receive activation signal at any time
--but it will broadcast neuronID only when activated

entity Broadcaster is
port(
	activeNeurons : in std_logic_vector(RES*RES-1 downto 0);--when an entry is active, the corid_active_neuronponding neuron is active
	brodcastedNeuronId : out std_logic_vector(WIDTH_ID-1 downto 0); -- currently brodcasted ID, (from 1 to RES*RES) 0== nothing
	clk,reset,enable : in std_logic
);
end Broadcaster;



architecture Behavioral of Broadcaster is
	
	--copy the active input until we use it
	signal activated_neuron : std_logic_vector(RES*RES-1 downto 0) := (others => '0');
	--(supose that the neuron is not activated again before the brodcast of its id)
	signal id_active_neuron : std_logic_vector(WIDTH_ID-1 downto 0) := (others => '0'); --the returned id (start from 1)
begin
	process(clk,reset,enable) is
		variable mask : std_logic_vector(RES*RES-1 downto 0) := (others => '1'); --to erase used id
		variable merged : std_logic_vector(RES*RES-1 downto 0) := (others => '0'); --activated_neuron or activeNeurons
		
		--compute the mask with a lut (to generate)
		type rom_table_type is array(0 to RES*RES) of std_logic_vector(RES*RES-1 downto 0);
		constant rom_table: rom_table_type :=
		--@createRomTableAER;
		(
		1 => "000000001",
		2 => "000000010",
		3 => "000000100",
		4 => "000001000",
		5 => "000010000",
		6 => "000100000",
		7 => "001000000",
		8 => "010000000",
		9 => "100000000",
		others => (others => '0'))
		--@END;

		;
		
		
	begin
		if(reset = '1') then 
			activated_neuron <= (others => '0');
			--mask := (others => '1');
		else
			if(rising_edge(clk) and enable ='1') then 
				--print active neuron on activated_neuron array
				--print("activeNeurons "  & str(activeNeurons));
				
				--erase broadcasted id with the mask lookup in the table
				mask := rom_table(to_integer(unsigned(id_active_neuron)));
				merged := (activated_neuron or activeNeurons) and not(mask);
				activated_neuron <= merged ;
				--print("activated_neuron "  & str(activated_neuron));
				--dichotomic search of an active signal in activated neuron
			--(to generate)
			--@generateDichotomicSearch;
			if(merged(RES*RES-1 downto 0) = "000000000") then
				id_active_neuron <= (others => '0');
			else
				if(merged(4 downto 0) /= "00000") then
					if(merged(2 downto 0) /= "000") then
						if(merged(1 downto 0) /= "00") then
							if(merged(0 downto 0) /= "0") then
								id_active_neuron <= "0001";
							else 
								id_active_neuron <= "0010";
							end if; 
						else 
							id_active_neuron <= "0011";
						end if; 
					else 
						if(merged(3 downto 3) /= "0") then
							id_active_neuron <= "0100";
						else 
							id_active_neuron <= "0101";
						end if; 
					end if; 
				else 
					if(merged(7 downto 5) /= "000") then
						if(merged(6 downto 5) /= "00") then
							if(merged(5 downto 5) /= "0") then
								id_active_neuron <= "0110";
							else 
								id_active_neuron <= "0111";
							end if; 
						else 
							id_active_neuron <= "1000";
						end if; 
					else 
						if(merged(8 downto 8) /= "0") then
							id_active_neuron <= "1001";
						else 
							id_active_neuron <= "1010";
						end if; 
					end if; 
				end if; 
			end if;

			--@END;					


			end if;
		end if;
	end process;
	
	
	
		
	
	
		brodcastedNeuronId <= id_active_neuron;
end Behavioral;

