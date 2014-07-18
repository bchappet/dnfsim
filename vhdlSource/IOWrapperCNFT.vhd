library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use WORK.CNFTConfiguration.all;
use work.txt_util.all;

entity IOWrapperCNFT is
    Port ( CLK : in  STD_LOGIC;
           Reset : in  STD_LOGIC;
			  Start : in STD_LOGIC;
			  Enable: in STD_LOGIC;
           Pixel : in  STD_LOGIC_VECTOR (INT + FRAC downto 0);
			  Done: out STD_LOGIC;
           AddrMemPixel : out  STD_LOGIC_VECTOR (20 downto 0);
			  CELMemPixel: out STD_LOGIC;
        	  OELMemPixel: out STD_LOGIC;
        	  WELMemPixel: out STD_LOGIC;
           Potential : out  STD_LOGIC_VECTOR (INT + FRAC-1 downto 0);
           AddrMemPot : out  STD_LOGIC_VECTOR (20 downto 0);
			  CELMemPot: out STD_LOGIC;
        	  OELMemPot: out STD_LOGIC;
        	  WELMemPot: out STD_LOGIC;
			  LoadImage: out std_logic; --signal to load image
			  SaveImage : out std_logic --signal to save image
			  );
end IOWrapperCNFT;

architecture Behavioral of IOWrapperCNFT is

component IOAddressGeneratorUnit is
    port ( CLK : in  STD_LOGIC;
           Reset : in  STD_LOGIC;
           Enable : in  STD_LOGIC;
           Address : out  STD_LOGIC_VECTOR (AddressWidth-1 downto 0));
end component;

component IOTappedDelayLine is

    Port ( CLK : in  STD_LOGIC;
           Reset : in  STD_LOGIC;
			  enable : in  STD_LOGIC;
           Input : in  STD_LOGIC_VECTOR (INT + FRAC  downto 0);
           Output : out  STD_LOGIC_VECTOR (RES*RES*(INT + FRAC+1)-1 downto 0)
           );
end component;

component IOMirrorState is

    Port ( CLK : in  STD_LOGIC;
           Reset : in  STD_LOGIC;
			  Load : in  STD_LOGIC;
			  EnableShift : in  STD_LOGIC;
           Input : in STD_LOGIC_VECTOR (RES*RES*(INT + FRAC)-1 downto 0) ;
           Output : out STD_LOGIC_VECTOR ((INT + FRAC)-1 downto 0)  
           );
end component;

component IOFSMControl is
		generic( GRID_SIZE: Natural:= RES*RES;
         		NB_IT_COMP: Natural:= 1;
			NB_IT_PROPAGATION: Natural:= 5;
			NB_CYCLE: Natural:= 3
			);
		Port ( CLK : in  STD_LOGIC;
           Reset : in  STD_LOGIC;
           Start : in  STD_LOGIC;
           EnableLoad : out  STD_LOGIC; --signal to read Input
			  EnableLoadSave : out STD_LOGIC; --signal to load input and save potentials
			 -- EnableSave : out STD_LOGIC; --signal to save potentials
			  EnableCompute: out std_logic; --signal to compute state of cnft
			  EnablePropagation: out std_logic; --signal to propagate spikes
			  PreComp: out std_logic; --signal to prepare next_comp (save pot)
			  PostComp: out std_logic; --signal to prepare propagation
           DoneCycle: out  STD_LOGIC --signal that all computation ended
			  ); 
	end component; 


-- Component definition
-- The current model under test
-- It should be generated automatically
-- 00 is for x=0 y=0 origin left/top

component SpikingCNFTMap is
	PORT(
		--@generatePorts;
		potential0_0 : out std_logic_vector(INT+FRAC-1 downto 0);
		input0_0 : in std_logic_vector(INT+FRAC downto 0);
		potential0_1 : out std_logic_vector(INT+FRAC-1 downto 0);
		input0_1 : in std_logic_vector(INT+FRAC downto 0);
		potential0_2 : out std_logic_vector(INT+FRAC-1 downto 0);
		input0_2 : in std_logic_vector(INT+FRAC downto 0);
		potential1_0 : out std_logic_vector(INT+FRAC-1 downto 0);
		input1_0 : in std_logic_vector(INT+FRAC downto 0);
		potential1_1 : out std_logic_vector(INT+FRAC-1 downto 0);
		input1_1 : in std_logic_vector(INT+FRAC downto 0);
		potential1_2 : out std_logic_vector(INT+FRAC-1 downto 0);
		input1_2 : in std_logic_vector(INT+FRAC downto 0);
		potential2_0 : out std_logic_vector(INT+FRAC-1 downto 0);
		input2_0 : in std_logic_vector(INT+FRAC downto 0);
		potential2_1 : out std_logic_vector(INT+FRAC-1 downto 0);
		input2_1 : in std_logic_vector(INT+FRAC downto 0);
		potential2_2 : out std_logic_vector(INT+FRAC-1 downto 0);
		input2_2 : in std_logic_vector(INT+FRAC downto 0);
		--@END;
		clk,compute,propagate,reset,nextcomp : in std_logic
	);
end component;

-- Internal signals required for connecting the 
-- unit under test to the I/O wraper
-- It should be generated automatically

signal ParallelPixelToCNFT: STD_LOGIC_VECTOR (RES*RES*((INT + FRAC)+1)-1 downto 0);
signal ParallelPotentialCNFT: STD_LOGIC_VECTOR (RES*RES*(INT + FRAC)-1 downto 0);


-- Internal signals required to wire the FSM controller to
-- the different components
--signal EnableLoadInt: std_logic;
--signal EnableComputeInt: std_logic;
--signal EnableLoadSaveInt: std_logic;
--signal NextCompInt : std_logic;
--signal ComputeInt : std_logic;

signal ReadInputInt,LoadInputInt,SaveInputNInt : std_logic;
signal WritePotentialInt,SavePotentialInt,SavePotentialNInt : std_logic;
signal ComputeInt,doneInt : std_logic;
signal nextCompInt : std_logic;

signal	EnableLoadInt: std_logic;
signal	EnableLoadSaveInt : std_logic;
signal	EnableComputeInt : std_logic;
signal	EnablePropagationInt : std_logic;
signal	PreCompInt : std_logic := '0';
signal	PostCompInt : std_logic := '0';
signal	DoneCycleInt : std_logic := '0';	

--tempory variable to merge signals
signal EnableLoad : std_logic := '0';
signal ResetIOAdressGenerator : std_logic := '0';

begin

EnableLoad <= enableLoadInt or enableLoadSaveInt;
ResetIOAdressGenerator <=  Reset or PreCompInt;

u0: IOAddressGeneratorUnit port map(
			CLK => CLK,
			Reset => ResetIOAdressGenerator,
			Enable => EnableLoad,
			Address => AddrMemPixel
			);
--print("NextCompInt : " & str(NextCompInt));
--print("Enable : " & str(EnableLoadInt or EnableLoadSaveInt));
--print("Input : " & str(Pixel));

u1: IOTappedDelayLine  port map(
			CLK => CLK,
			Reset => Reset,
			Enable => EnableLoad,
			Input => Pixel,
			OutPut => ParallelPixelToCNFT
			);
--print("output tapped delay " & str(ParallelPixelToCNFT));

-- Instatiate the component under test
-- The IOWrapper handles all the I/O details to load/store
-- data in memory. It should be generated automatically
-- 0_0 is for x=0 y=0 origin left/top
-- reverse the order to have the same input as in the file							
UUT: SpikingCNFTMap  port map(
	--@connectPorts;
	potential0_0 => ParallelPotentialCNFT ( 89 downto 80),
	input0_0 => ParallelPixelToCNFT ( 98 downto 88), 
	potential1_0 => ParallelPotentialCNFT ( 79 downto 70), 
	input1_0 => ParallelPixelToCNFT ( 87 downto 77 ), 
	potential2_0 => ParallelPotentialCNFT ( 69 downto 60), 
	input2_0 => ParallelPixelToCNFT ( 76 downto 66), 
	potential0_1 => ParallelPotentialCNFT ( 59 downto 50),
	input0_1 => ParallelPixelToCNFT ( 65 downto 55), 
	potential1_1 => ParallelPotentialCNFT ( 49 downto 40),
	input1_1 => ParallelPixelToCNFT ( 54 downto 44 ), 
	potential2_1 => ParallelPotentialCNFT ( 39 downto 30),
	input2_1 => ParallelPixelToCNFT ( 43 downto 33), 
	potential0_2  => ParallelPotentialCNFT ( 29 downto 20),
	input0_2 => ParallelPixelToCNFT ( 32 downto 22), 
	potential1_2 => ParallelPotentialCNFT ( 19 downto 10),
	input1_2 => ParallelPixelToCNFT ( 21 downto 11), 
	potential2_2  => ParallelPotentialCNFT ( 9 downto 0),
	input2_2 => ParallelPixelToCNFT ( 10 downto 0), 
	--@END;
	clk => CLK,
	compute => EnableComputeInt,
	NextComp =>  PostcompInt,
	propagate => '1',
	reset => reset
);
--print("potential : " & str(ParallelPotentialCNFT));
u2: IOMirrorState  port map(
		CLK => CLK,
		Reset => Reset,
		Load => PreCompInt,
		EnableShift => EnableLoadSaveInt,
		Input => ParallelPotentialCNFT,
		Output => Potential
		);

							
u3: IOAddressGeneratorUnit port map(
		CLK => CLK,
		Reset =>ResetIOAdressGenerator,
		Enable => EnableLoadSaveInt,
		Address => AddrMemPot
		);	
										
u4: IOFSMControl PORT MAP(
		CLK => CLK,
		Reset => Reset,
		Start => Start,
		
		EnableLoad => EnableLoadInt,
		EnableLoadSave => EnableLoadSaveInt,
		EnableCompute => EnableComputeInt,
		EnablePropagation  => EnablePropagationInt,
		PreComp => PreCompInt,
		PostComp => PostCompInt,
		DoneCycle => DoneCycleInt	
	);
	


CELMemPixel <= '0';
OELMemPixel <= not (EnableLoadSaveInt or EnableLoadInt);
WELMemPixel <= '1';

CELMemPot <= '0';
OELMemPot <= '1';
WELMemPot <= not EnableLoadSaveInt;

LoadImage <= PreCompInt;
SaveImage <= PreCompInt;
Done <= DoneCycleInt	;
										
end Behavioral;
