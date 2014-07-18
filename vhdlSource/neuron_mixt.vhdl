
library IEEE;
library ieee_proposed;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.NUMERIC_STD.ALL;
use IEEE.MATH_REAL.ALL;
use ieee_proposed.fixed_pkg.ufixed;
use ieee_proposed.fixed_pkg.sfixed;
use ieee_proposed.fixed_pkg.to_ufixed;
use ieee_proposed.fixed_pkg."srl";
use ieee_proposed.fixed_pkg.to_sfixed;
use ieee_proposed.fixed_pkg.to_slv;
use ieee_proposed.fixed_pkg.">";
use ieee_proposed.fixed_pkg.resize;
use work.txt_util.all;
use STD.textio.all;
use WORK.CNFTConfiguration.all;
use WORK.FixedPointOperations.all;

entity @name is
	port(
		--connection to every other neurons potential in
		@1
		
		--input feed
		input : in std_logic_vector(INT+FRAC downto 0); --signed
		
		--neuron's potential
		potentialOut : out std_logic_vector(INT+FRAC-1 downto 0); --unsigned
		
		clk,compute,nextComp,reset : in std_logic
		
	);
end @name;

architecture Behavioral of @name is


		
	
		
		
		type cnft_map is array(0 to RES-1,0 to RES-1) of ufixed(INT-1 downto -FRAC);
		type cnft_linear_map is array(0 to RES*RES-1) of ufixed(INT-1 downto -FRAC);
		type cnft_array is array(0 to RES-1) of ufixed(INT-1 downto -FRAC);
		
		--Memory
		@2
		signal potential : ufixed(INT-1 downto -FRAC) := (others => '0'); --memory unsigned
		signal save_exc_sum : cnft_array;
		signal save_inh_sum : cnft_array;
		signal input_mem : sfixed(INT downto -FRAC):= (others => '0'); --save the input when nextComp=1
		
			
		--Wires
		signal exc_mult : cnft_map ;
		signal inh_mult : cnft_map ;
		signal potential_map :  cnft_map;
		signal convolution : sfixed(INT downto -FRAC) := (others => '0');
		--Wires for potential computation
		signal multiplication : sfixed(INT downto -FRAC);
		signal addition : sfixed(INT downto -FRAC);
		signal prePot : sfixed(INT downto -FRAC):= (others => '0'); --signed
		signal prePot2 : ufixed(INT-1 downto -FRAC):= (others => '0'); --unsigned

begin

		process(clk,reset,nextComp)
		begin
			if reset='1' then
				input_mem <= (others => '0');
			else
				if rising_edge(clk) and nextComp='1' then
				--Save the current input
					input_mem <= to_sfixed(input,input_mem);
				end if;
			end if;
		
		end process;

		--Compute the potential
		--print("Potential : " & str(std_logic_vector(to_sfixed(potential))));
		--print("Input : " & str(std_logic_vector(to_sfixed(input,multiplication))));
		--print("DT_TAU : " & str(std_logic_vector(to_sfixed(DT_TAU,multiplication))));
		addition <= resize((input_mem + convolution - to_sfixed(potential)),multiplication) ;
		--print("Addition : " & str(std_logic_vector(addition)));
		multiplication <= resize( to_sfixed(DT_TAU,multiplication)*addition,multiplication);
		--print("Mult : " & str(std_logic_vector(multiplication)));
		prepot <= resize(to_sfixed(potential) + multiplication,prepot);
		prepot2 <= to_ufixed(unsigned(prepot(INT-1 downto -FRAC))) when prepot(INT) = '0' else (others => '0');
		
		
		process(clk,reset,nextComp) 
			variable convolution : sfixed(INT downto -FRAC) := (others => '0');
		begin
			if(reset = '1') then
				potential <= (others => '0');
			else
				if rising_edge(clk) and nextComp='1' then
				--PRINT("update pot");
					--Update the potential
					potential <= prepot2;
				end if;
			end if;
		end process;
		
		potentialOut <= to_slv(potential);
		

	--Connect the potential map
	@3

	--Convolution (mult part)
	convolution_column : for i in 0 to RES-1 generate
			
	begin
		convolution_row : for j in 0 to RES-1 generate
			begin
			exc_mult(i,j) <= exc_weights(i,j) * potential_map(i, j);
			inh_mult(i,j) <= inh_weights(i,j) * potential_map(i, j);
		end generate convolution_row;
		
		--Convolution2 (Adding part in sequential for space saving)
		process(clk,compute) is
			variable exc_sum : cnft_array;
			variable inh_sum : cnft_array;
		begin
			if(rising_edge(clk) and compute='1')then
					--print("clk");
				--reset the sum
				exc_sum(i) := (others => '0');
				inh_sum(i) := (others => '0');
				--add the row values
				for j in 0 to RES-1 loop
					exc_sum(i) := exc_sum(i) + exc_mult(i,j);
					inh_sum(i) := inh_sum(i) + inh_mult(i,j);
						--print(str(i) & " for : " & str(std_logic_vector(inh_mult(i,j))));
						--	print("res" & str(std_logic_vector(inh_sum(i))));
				end loop;
				--We save the last value
				save_exc_sum(i) <= exc_sum(i);
				save_inh_sum(i) <= inh_sum(i);
				--print("save_exc_sum(i) = " & str(std_logic_vector(save_inh_sum(i))));
			end if;
			
		end process;
		
		
	end generate convolution_column;
	

	--finally we add the saved sum
	row_addition : process(clk,compute	) is
		variable exc_total : ufixed(INT-1 downto - FRAC);
		variable inh_total : ufixed(INT-1 downto - FRAC);
	begin
		if(rising_edge(clk) and compute='1')then
			exc_total := (others => '0');
			inh_total := (others => '0');
			for i in 0 to RES-1 loop
				exc_total := exc_total + save_exc_sum(i);
				inh_total := inh_total + save_inh_sum(i);
			end loop;
			--print("exc_total : " & str(std_logic_vector(exc_total)) & " inh total " & str(std_logic_vector(inh_total)));
			convolution <= resize(to_sfixed(exc_total) - to_sfixed(inh_total),convolution);
		end if;

	end process row_addition;
	
end Behavioral;

