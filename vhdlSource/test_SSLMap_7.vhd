--05/03/14
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use work.CNFTConfiguration.all;

use WORK.mytypes_pkg.all;
entity test_SpikingCNFTMap is

end test_SpikingCNFTMap;

architecture Behavioral of test_SpikingCNFTMap is

	Component SpikingCNFTMap
		port(
			--@generatePorts;
			potential0_0: out std_logic_vector(INT+FRAC-1 downto 0);
			input0_0: in std_logic_vector(INT+FRAC downto 0);
			potential0_1: out std_logic_vector(INT+FRAC-1 downto 0);
			input0_1: in std_logic_vector(INT+FRAC downto 0);
			potential0_2: out std_logic_vector(INT+FRAC-1 downto 0);
			input0_2: in std_logic_vector(INT+FRAC downto 0);
			potential0_3: out std_logic_vector(INT+FRAC-1 downto 0);
			input0_3: in std_logic_vector(INT+FRAC downto 0);
			potential0_4: out std_logic_vector(INT+FRAC-1 downto 0);
			input0_4: in std_logic_vector(INT+FRAC downto 0);
			potential0_5: out std_logic_vector(INT+FRAC-1 downto 0);
			input0_5: in std_logic_vector(INT+FRAC downto 0);
			potential0_6: out std_logic_vector(INT+FRAC-1 downto 0);
			input0_6: in std_logic_vector(INT+FRAC downto 0);
			potential1_0: out std_logic_vector(INT+FRAC-1 downto 0);
			input1_0: in std_logic_vector(INT+FRAC downto 0);
			potential1_1: out std_logic_vector(INT+FRAC-1 downto 0);
			input1_1: in std_logic_vector(INT+FRAC downto 0);
			potential1_2: out std_logic_vector(INT+FRAC-1 downto 0);
			input1_2: in std_logic_vector(INT+FRAC downto 0);
			potential1_3: out std_logic_vector(INT+FRAC-1 downto 0);
			input1_3: in std_logic_vector(INT+FRAC downto 0);
			potential1_4: out std_logic_vector(INT+FRAC-1 downto 0);
			input1_4: in std_logic_vector(INT+FRAC downto 0);
			potential1_5: out std_logic_vector(INT+FRAC-1 downto 0);
			input1_5: in std_logic_vector(INT+FRAC downto 0);
			potential1_6: out std_logic_vector(INT+FRAC-1 downto 0);
			input1_6: in std_logic_vector(INT+FRAC downto 0);
			potential2_0: out std_logic_vector(INT+FRAC-1 downto 0);
			input2_0: in std_logic_vector(INT+FRAC downto 0);
			potential2_1: out std_logic_vector(INT+FRAC-1 downto 0);
			input2_1: in std_logic_vector(INT+FRAC downto 0);
			potential2_2: out std_logic_vector(INT+FRAC-1 downto 0);
			input2_2: in std_logic_vector(INT+FRAC downto 0);
			potential2_3: out std_logic_vector(INT+FRAC-1 downto 0);
			input2_3: in std_logic_vector(INT+FRAC downto 0);
			potential2_4: out std_logic_vector(INT+FRAC-1 downto 0);
			input2_4: in std_logic_vector(INT+FRAC downto 0);
			potential2_5: out std_logic_vector(INT+FRAC-1 downto 0);
			input2_5: in std_logic_vector(INT+FRAC downto 0);
			potential2_6: out std_logic_vector(INT+FRAC-1 downto 0);
			input2_6: in std_logic_vector(INT+FRAC downto 0);
			potential3_0: out std_logic_vector(INT+FRAC-1 downto 0);
			input3_0: in std_logic_vector(INT+FRAC downto 0);
			potential3_1: out std_logic_vector(INT+FRAC-1 downto 0);
			input3_1: in std_logic_vector(INT+FRAC downto 0);
			potential3_2: out std_logic_vector(INT+FRAC-1 downto 0);
			input3_2: in std_logic_vector(INT+FRAC downto 0);
			potential3_3: out std_logic_vector(INT+FRAC-1 downto 0);
			input3_3: in std_logic_vector(INT+FRAC downto 0);
			potential3_4: out std_logic_vector(INT+FRAC-1 downto 0);
			input3_4: in std_logic_vector(INT+FRAC downto 0);
			potential3_5: out std_logic_vector(INT+FRAC-1 downto 0);
			input3_5: in std_logic_vector(INT+FRAC downto 0);
			potential3_6: out std_logic_vector(INT+FRAC-1 downto 0);
			input3_6: in std_logic_vector(INT+FRAC downto 0);
			potential4_0: out std_logic_vector(INT+FRAC-1 downto 0);
			input4_0: in std_logic_vector(INT+FRAC downto 0);
			potential4_1: out std_logic_vector(INT+FRAC-1 downto 0);
			input4_1: in std_logic_vector(INT+FRAC downto 0);
			potential4_2: out std_logic_vector(INT+FRAC-1 downto 0);
			input4_2: in std_logic_vector(INT+FRAC downto 0);
			potential4_3: out std_logic_vector(INT+FRAC-1 downto 0);
			input4_3: in std_logic_vector(INT+FRAC downto 0);
			potential4_4: out std_logic_vector(INT+FRAC-1 downto 0);
			input4_4: in std_logic_vector(INT+FRAC downto 0);
			potential4_5: out std_logic_vector(INT+FRAC-1 downto 0);
			input4_5: in std_logic_vector(INT+FRAC downto 0);
			potential4_6: out std_logic_vector(INT+FRAC-1 downto 0);
			input4_6: in std_logic_vector(INT+FRAC downto 0);
			potential5_0: out std_logic_vector(INT+FRAC-1 downto 0);
			input5_0: in std_logic_vector(INT+FRAC downto 0);
			potential5_1: out std_logic_vector(INT+FRAC-1 downto 0);
			input5_1: in std_logic_vector(INT+FRAC downto 0);
			potential5_2: out std_logic_vector(INT+FRAC-1 downto 0);
			input5_2: in std_logic_vector(INT+FRAC downto 0);
			potential5_3: out std_logic_vector(INT+FRAC-1 downto 0);
			input5_3: in std_logic_vector(INT+FRAC downto 0);
			potential5_4: out std_logic_vector(INT+FRAC-1 downto 0);
			input5_4: in std_logic_vector(INT+FRAC downto 0);
			potential5_5: out std_logic_vector(INT+FRAC-1 downto 0);
			input5_5: in std_logic_vector(INT+FRAC downto 0);
			potential5_6: out std_logic_vector(INT+FRAC-1 downto 0);
			input5_6: in std_logic_vector(INT+FRAC downto 0);
			potential6_0: out std_logic_vector(INT+FRAC-1 downto 0);
			input6_0: in std_logic_vector(INT+FRAC downto 0);
			potential6_1: out std_logic_vector(INT+FRAC-1 downto 0);
			input6_1: in std_logic_vector(INT+FRAC downto 0);
			potential6_2: out std_logic_vector(INT+FRAC-1 downto 0);
			input6_2: in std_logic_vector(INT+FRAC downto 0);
			potential6_3: out std_logic_vector(INT+FRAC-1 downto 0);
			input6_3: in std_logic_vector(INT+FRAC downto 0);
			potential6_4: out std_logic_vector(INT+FRAC-1 downto 0);
			input6_4: in std_logic_vector(INT+FRAC downto 0);
			potential6_5: out std_logic_vector(INT+FRAC-1 downto 0);
			input6_5: in std_logic_vector(INT+FRAC downto 0);
			potential6_6: out std_logic_vector(INT+FRAC-1 downto 0);
			input6_6: in std_logic_vector(INT+FRAC downto 0);

			--@END;



			clk,propagate,compute,reset,nextcomp : in std_logic
		);
	end Component;
		--@generateSignals;
		signal potential0_0 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input0_0 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential0_1 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input0_1 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential0_2 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input0_2 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential0_3 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input0_3 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential0_4 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input0_4 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential0_5 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input0_5 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential0_6 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input0_6 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential1_0 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input1_0 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential1_1 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input1_1 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential1_2 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input1_2 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential1_3 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input1_3 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential1_4 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input1_4 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential1_5 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input1_5 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential1_6 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input1_6 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential2_0 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input2_0 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential2_1 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input2_1 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential2_2 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input2_2 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential2_3 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input2_3 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential2_4 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input2_4 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential2_5 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input2_5 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential2_6 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input2_6 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential3_0 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input3_0 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential3_1 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input3_1 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential3_2 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input3_2 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential3_3 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input3_3 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential3_4 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input3_4 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential3_5 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input3_5 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential3_6 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input3_6 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential4_0 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input4_0 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential4_1 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input4_1 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential4_2 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input4_2 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential4_3 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input4_3 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential4_4 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input4_4 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential4_5 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input4_5 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential4_6 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input4_6 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential5_0 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input5_0 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential5_1 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input5_1 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential5_2 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input5_2 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential5_3 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input5_3 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential5_4 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input5_4 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential5_5 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input5_5 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential5_6 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input5_6 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential6_0 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input6_0 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential6_1 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input6_1 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential6_2 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input6_2 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential6_3 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input6_3 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential6_4 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input6_4 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential6_5 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input6_5 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential6_6 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input6_6 : std_logic_vector(INT+FRAC downto 0):= (others => '0');

		--@END;



		signal clk: std_logic;
		signal reset : std_logic := '0';
		signal propagate : std_logic := '0';
		signal compute : std_logic := '0';
		signal nextComp : std_logic := '0';
		signal preComp : std_logic := '0';
		signal ioLoading : std_logic := '0';
		 -- Clock period definitions
		constant clk_period : time := 10 ns;
	
 

begin
	--@connectSpikingCNFTMap;
	 uut: SpikingCNFTMap PORT MAP (
	potential0_0 => potential0_0,
	input0_0 => input0_0,
	potential0_1 => potential0_1,
	input0_1 => input0_1,
	potential0_2 => potential0_2,
	input0_2 => input0_2,
	potential0_3 => potential0_3,
	input0_3 => input0_3,
	potential0_4 => potential0_4,
	input0_4 => input0_4,
	potential0_5 => potential0_5,
	input0_5 => input0_5,
	potential0_6 => potential0_6,
	input0_6 => input0_6,
	potential1_0 => potential1_0,
	input1_0 => input1_0,
	potential1_1 => potential1_1,
	input1_1 => input1_1,
	potential1_2 => potential1_2,
	input1_2 => input1_2,
	potential1_3 => potential1_3,
	input1_3 => input1_3,
	potential1_4 => potential1_4,
	input1_4 => input1_4,
	potential1_5 => potential1_5,
	input1_5 => input1_5,
	potential1_6 => potential1_6,
	input1_6 => input1_6,
	potential2_0 => potential2_0,
	input2_0 => input2_0,
	potential2_1 => potential2_1,
	input2_1 => input2_1,
	potential2_2 => potential2_2,
	input2_2 => input2_2,
	potential2_3 => potential2_3,
	input2_3 => input2_3,
	potential2_4 => potential2_4,
	input2_4 => input2_4,
	potential2_5 => potential2_5,
	input2_5 => input2_5,
	potential2_6 => potential2_6,
	input2_6 => input2_6,
	potential3_0 => potential3_0,
	input3_0 => input3_0,
	potential3_1 => potential3_1,
	input3_1 => input3_1,
	potential3_2 => potential3_2,
	input3_2 => input3_2,
	potential3_3 => potential3_3,
	input3_3 => input3_3,
	potential3_4 => potential3_4,
	input3_4 => input3_4,
	potential3_5 => potential3_5,
	input3_5 => input3_5,
	potential3_6 => potential3_6,
	input3_6 => input3_6,
	potential4_0 => potential4_0,
	input4_0 => input4_0,
	potential4_1 => potential4_1,
	input4_1 => input4_1,
	potential4_2 => potential4_2,
	input4_2 => input4_2,
	potential4_3 => potential4_3,
	input4_3 => input4_3,
	potential4_4 => potential4_4,
	input4_4 => input4_4,
	potential4_5 => potential4_5,
	input4_5 => input4_5,
	potential4_6 => potential4_6,
	input4_6 => input4_6,
	potential5_0 => potential5_0,
	input5_0 => input5_0,
	potential5_1 => potential5_1,
	input5_1 => input5_1,
	potential5_2 => potential5_2,
	input5_2 => input5_2,
	potential5_3 => potential5_3,
	input5_3 => input5_3,
	potential5_4 => potential5_4,
	input5_4 => input5_4,
	potential5_5 => potential5_5,
	input5_5 => input5_5,
	potential5_6 => potential5_6,
	input5_6 => input5_6,
	potential6_0 => potential6_0,
	input6_0 => input6_0,
	potential6_1 => potential6_1,
	input6_1 => input6_1,
	potential6_2 => potential6_2,
	input6_2 => input6_2,
	potential6_3 => potential6_3,
	input6_3 => input6_3,
	potential6_4 => potential6_4,
	input6_4 => input6_4,
	potential6_5 => potential6_5,
	input6_5 => input6_5,
	potential6_6 => potential6_6,
	input6_6 => input6_6,
	propagate => propagate,
	compute => compute,
	nextComp => nextComp,
	clk => clk,
	reset => reset
	 );

	  --@END;



		  
		   -- Clock process definitions
   clk_process :process
   begin
		clk <= '0';
		wait for clk_period/2;
		clk <= '1';
		wait for clk_period/2;
   end process;
	
	 
	
	-- Stimulus process
   stim_proc: process
   begin		
      -- hold reset state for 100 ns.
      wait for 100 ns;	
		
		input0_0 <= "01000000000";
		compute <= '1';
		wait for clk_period;
		input0_0 <= "00000000000";
		compute <= '0';
		
		for I in 0 to 15 loop
			--nextComp : reset lateral feed
			nextcomp <= '1';
			wait for clk_period;
			nextcomp <= '0';
			propagate <= '1';
			--wait for propagation (and IOfeeding later)
			ioLoading <= '1';
			wait for 4*clk_period;
			--precomp (savePot)
			propagate <= '0';
			ioLoading <= '0';
			precomp <= '1';
			wait for clk_period;
			precomp <= '0';
			--compute  
			ioLoading <= '1';
			compute <= '1';
			wait for 1*clk_period;
			compute <= '0';
			
			
			
			
		
		end loop;
		


		
		
      wait;
   end process;


end Behavioral;

