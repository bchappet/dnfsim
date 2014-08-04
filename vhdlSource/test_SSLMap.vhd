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
			potential1_0: out std_logic_vector(INT+FRAC-1 downto 0);
			input1_0: in std_logic_vector(INT+FRAC downto 0);
			potential1_1: out std_logic_vector(INT+FRAC-1 downto 0);
			input1_1: in std_logic_vector(INT+FRAC downto 0);
			potential1_2: out std_logic_vector(INT+FRAC-1 downto 0);
			input1_2: in std_logic_vector(INT+FRAC downto 0);
			potential2_0: out std_logic_vector(INT+FRAC-1 downto 0);
			input2_0: in std_logic_vector(INT+FRAC downto 0);
			potential2_1: out std_logic_vector(INT+FRAC-1 downto 0);
			input2_1: in std_logic_vector(INT+FRAC downto 0);
			potential2_2: out std_logic_vector(INT+FRAC-1 downto 0);
			input2_2: in std_logic_vector(INT+FRAC downto 0);

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
		signal potential1_0 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input1_0 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential1_1 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input1_1 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential1_2 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input1_2 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential2_0 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input2_0 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential2_1 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input2_1 : std_logic_vector(INT+FRAC downto 0):= (others => '0');
		signal potential2_2 : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');
		signal input2_2 : std_logic_vector(INT+FRAC downto 0):= (others => '0');

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
	potential1_0 => potential1_0,
	input1_0 => input1_0,
	potential1_1 => potential1_1,
	input1_1 => input1_1,
	potential1_2 => potential1_2,
	input1_2 => input1_2,
	potential2_0 => potential2_0,
	input2_0 => input2_0,
	potential2_1 => potential2_1,
	input2_1 => input2_1,
	potential2_2 => potential2_2,
	input2_2 => input2_2,
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

