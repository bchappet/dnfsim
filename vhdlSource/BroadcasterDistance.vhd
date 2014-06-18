
library IEEE;

use IEEE.STD_LOGIC_1164.ALL;
use ieee.numeric_std.all;

use work.txt_util.all;
use WORK.CNFTConfiguration.all;

--05/03/14
--can receive activation signal at any time
--but it will broadcast neuronID only when activated

--06/03/14
--now we send the X_Y position of the neuron instead of its id (starting from 1_1 to res_res
--the WIDTH_ID is just one bit larger

--TODO we can get rid of the num_active_neuron signal by generating a good rom-table
--which gives the index using the coords


entity BroadcasterDistance is
port(
	activeNeurons : in std_logic_vector(RES*RES-1 downto 0);--when an entry is active, the corid_active_neuronponding neuron is active
	brodcastedNeuronId : out std_logic_vector(WIDTH_ID-1 downto 0); -- currently brodcasted ID, (from 1 to RES*RES) 0== nothing
	clk,reset,enable : in std_logic
);
end BroadcasterDistance;



architecture Behavioral of BroadcasterDistance is
	
	--copy the active input until we use it
	signal activated_neuron : std_logic_vector(RES*RES-1 downto 0) := (others => '0');
	--(supose that the neuron is not activated again before the brodcast of its id)
	signal id_active_neuron : std_logic_vector(WIDTH_ID-1 downto 0) := (others => '0'); --the returned id (start from 1)
	signal num_active_neuron : std_logic_vector(WIDTH_ID-1 downto 0) := (others => '0'); --the num of activr neuron from 1)
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
				--print("Erasing : "  & str(to_integer(unsigned(num_active_neuron))));
				--erase broadcasted id with the mask lookup in the table
				mask := rom_table(to_integer(unsigned(num_active_neuron)));
				
				merged := (activated_neuron or activeNeurons) and not(mask);
				
				activated_neuron <= merged ;
				--print("activated_neuron "  & str(activated_neuron));
				--dichotomic search of an active signal in activated neuron
			--(to generate)
			--@generateDichotomicSearch;
			if(merged(RES*RES-1 downto 0) = "000000000") then
				id_active_neuron <= (others => '0');
				num_active_neuron <= (others => '0');
			else
				if(merged(4 downto 0) /= "00000") then
					if(merged(2 downto 0) /= "000") then
						if(merged(1 downto 0) /= "00") then
							if(merged(0 downto 0) /= "0") then
								num_active_neuron <= "0001";
								id_active_neuron <= "01"&"01";
							else 
								num_active_neuron <= "0010";
								id_active_neuron <= "10"&"01";
							end if; 
						else 
							num_active_neuron <= "0011";
							id_active_neuron <= "11"&"01";
						end if; 
					else 
						if(merged(3 downto 3) /= "0") then
							num_active_neuron <= "0100";
							id_active_neuron <= "01"&"10";
						else 
							num_active_neuron <= "0101";
							id_active_neuron <= "10"&"10";
						end if; 
					end if; 
				else 
					if(merged(6 downto 5) /= "00") then
						if(merged(5 downto 5) /= "0") then
							num_active_neuron <= "0110";
							id_active_neuron <= "11"&"10";
						else 
							num_active_neuron <= "0111";
							id_active_neuron <= "01"&"11";
						end if; 
					else 
						if(merged(7 downto 7) /= "0") then
							num_active_neuron <= "1000";
							id_active_neuron <= "10"&"11";
						else 
							num_active_neuron <= "1001";
							id_active_neuron <= "11"&"11";
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

