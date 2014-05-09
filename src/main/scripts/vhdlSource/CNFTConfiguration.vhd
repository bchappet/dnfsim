library IEEE;
use IEEE.STD_LOGIC_1164.ALL;


package CNFTConfiguration is
	
	constant WIDTH_ID :  natural  := 
	--@getWidth_id;
	5
	--@END;
	; --width of the bus identifying each neuron
	
	constant RES :  natural := 
	--@getRes;
	3
	--@END;
	; --resolution of the map

	constant DT_TAU :  real := 0.156; --dt/tau 
	constant TAU_1 : real := 1.3; --1/tau
	constant ALPHA :  real := 10.0;
	--Fixed point format
	constant INT: NATURAL := 3;
	constant FRAC: NATURAL := 7;
	constant PROB_WIDTH: NATURAL := 8;

	--IO
	constant AddressWidth: integer := 21;
	
	
	constant NEIGHBORHOOD: NATURAL:= 4;
--Direction definition for Spikes Vectors
	constant W : integer := 0;
	constant E : integer := 1;
	constant S : integer := 2;
	constant N : integer := 3;
	
	constant THRESHOLD: REAL:= 0.75;
	constant BUFFER_SIZE: NATURAL:= 8;
	constant NB_SPIKES: NATURAL:= 1;
	
	
	constant WA: REAL:=0.2;
	constant WB: REAL:=0.1;

	constant PA: REAL:=0.8;
	constant PB: REAL:=0.9;
	
	--
								 
end CNFTConfiguration;
