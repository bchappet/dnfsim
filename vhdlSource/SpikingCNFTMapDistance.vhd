library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use WORK.CNFTConfiguration.all;
use work.txt_util.all;
use work.mytypes_pkg.all;

entity SpikingCNFTMap is
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

	clk,compute,propagate,reset,nextcomp : in std_logic
	
);
end SpikingCNFTMap;

architecture Behavioral of SpikingCNFTMap is

	component SpikingNeuron
		port(
			activate : out std_logic;
			input : in std_logic_vector(INT + FRAC downto 0); --signed
			potential : out std_logic_vector(INT + FRAC - 1 downto 0); --unsigned
			exc,inh : in std_logic_vector(INT + FRAC -1 downto 0); --unsigned
			ovf : out std_logic;
			enable,clk,reset : in std_logic
		);
	end component;
	
	 COMPONENT SynapseDistance
	  generic(
			rom_table : rom_table_distance_type ;
			COORD : std_logic_vector(WIDTH_ID-1 downto 0)
			);
    PORT(
				brodcastedNeuronId : in std_logic_vector(WIDTH_ID-1 downto 0); -- currently brodcasted ID, (from 1 to RES+RES) 0== nothing
				lateralFeedingOut : out std_logic_vector(INT+FRAC-1 downto 0);--lateral feeding given to the neuron (unsigned)
				ovf : out std_logic; -- true if the addition overflows
				clk,reset,enable : in std_logic   
			);
    END COMPONENT;
	 
	 COMPONENT BroadcasterDistance
    PORT(
			activeNeurons : in std_logic_vector(RES*RES-1 downto 0);--when an entry is active, the corid_active_neuronponding neuron is active
			brodcastedNeuronId : out std_logic_vector(WIDTH_ID-1 downto 0); -- currently brodcasted ID, (from 1 to RES*RES) 0== nothing
			clk,reset,enable : in std_logic
        );
    END COMPONENT;
	
	--@generateSignals;
	signal potentialS0_0 : std_logic_vector(INT+FRAC-1 downto 0);
	signal activate0_0 : std_logic;
	signal exc0_0 : std_logic_vector(INT+FRAC-1 downto 0);
	signal inh0_0 : std_logic_vector(INT+FRAC-1 downto 0);
	signal ovf0_0,ovfExc0_0,ovfInh0_0 : std_logic;
	signal potentialS0_1 : std_logic_vector(INT+FRAC-1 downto 0);
	signal activate0_1 : std_logic;
	signal exc0_1 : std_logic_vector(INT+FRAC-1 downto 0);
	signal inh0_1 : std_logic_vector(INT+FRAC-1 downto 0);
	signal ovf0_1,ovfExc0_1,ovfInh0_1 : std_logic;
	signal potentialS0_2 : std_logic_vector(INT+FRAC-1 downto 0);
	signal activate0_2 : std_logic;
	signal exc0_2 : std_logic_vector(INT+FRAC-1 downto 0);
	signal inh0_2 : std_logic_vector(INT+FRAC-1 downto 0);
	signal ovf0_2,ovfExc0_2,ovfInh0_2 : std_logic;
	signal potentialS1_0 : std_logic_vector(INT+FRAC-1 downto 0);
	signal activate1_0 : std_logic;
	signal exc1_0 : std_logic_vector(INT+FRAC-1 downto 0);
	signal inh1_0 : std_logic_vector(INT+FRAC-1 downto 0);
	signal ovf1_0,ovfExc1_0,ovfInh1_0 : std_logic;
	signal potentialS1_1 : std_logic_vector(INT+FRAC-1 downto 0);
	signal activate1_1 : std_logic;
	signal exc1_1 : std_logic_vector(INT+FRAC-1 downto 0);
	signal inh1_1 : std_logic_vector(INT+FRAC-1 downto 0);
	signal ovf1_1,ovfExc1_1,ovfInh1_1 : std_logic;
	signal potentialS1_2 : std_logic_vector(INT+FRAC-1 downto 0);
	signal activate1_2 : std_logic;
	signal exc1_2 : std_logic_vector(INT+FRAC-1 downto 0);
	signal inh1_2 : std_logic_vector(INT+FRAC-1 downto 0);
	signal ovf1_2,ovfExc1_2,ovfInh1_2 : std_logic;
	signal potentialS2_0 : std_logic_vector(INT+FRAC-1 downto 0);
	signal activate2_0 : std_logic;
	signal exc2_0 : std_logic_vector(INT+FRAC-1 downto 0);
	signal inh2_0 : std_logic_vector(INT+FRAC-1 downto 0);
	signal ovf2_0,ovfExc2_0,ovfInh2_0 : std_logic;
	signal potentialS2_1 : std_logic_vector(INT+FRAC-1 downto 0);
	signal activate2_1 : std_logic;
	signal exc2_1 : std_logic_vector(INT+FRAC-1 downto 0);
	signal inh2_1 : std_logic_vector(INT+FRAC-1 downto 0);
	signal ovf2_1,ovfExc2_1,ovfInh2_1 : std_logic;
	signal potentialS2_2 : std_logic_vector(INT+FRAC-1 downto 0);
	signal activate2_2 : std_logic;
	signal exc2_2 : std_logic_vector(INT+FRAC-1 downto 0);
	signal inh2_2 : std_logic_vector(INT+FRAC-1 downto 0);
	signal ovf2_2,ovfExc2_2,ovfInh2_2 : std_logic;

	--@END;

	
		
	signal brodcastedNeuronIdInt : std_logic_vector(WIDTH_ID-1 downto 0);-- := (others => '0');
	signal activeNeuronsInt : std_logic_vector(RES*RES-1 downto 0) := (others => '0');

	constant rom_table_exc : rom_table_distance_type := 
	--@generateExcTable;
	(
	0 => "0000000000",
	1 => "0000000011",
	2 => "0000000101",
	3 => "0000001000",
	4 => "0000001010",
	)
	--@END;
	;

	constant rom_table_inh : rom_table_distance_type := 
	--@generateInhTable;
	(
	0 => "0000000000",
	1 => "0000000011",
	2 => "0000000101",
	3 => "0000001000",
	4 => "0000001010",
	)
	--@END;
	;

	
	
begin


	--@connectPotential;
	potential0_0 <= potentialS0_0;
	potential0_1 <= potentialS0_1;
	potential0_2 <= potentialS0_2;
	potential1_0 <= potentialS1_0;
	potential1_1 <= potentialS1_1;
	potential1_2 <= potentialS1_2;
	potential2_0 <= potentialS2_0;
	potential2_1 <= potentialS2_1;
	potential2_2 <= potentialS2_2;

	--@END;

	--@connectActiveNeuron;
	activeNeuronsInt <=activate0_0 & activate0_1 & activate0_2 & activate1_0 & activate1_1 & activate1_2 & activate2_0 & activate2_1 & activate2_2 ;

	--@END;

								
	--a bit dirty... but it works (reset assynchrone)
	--nextComp <= '1' when compute='1' and compute'last_value='0' and rising_edge(clk) else '0';
								
	--print("Active Neurons Int : " & str(activeNeuronsInt)& "  " & time'image(now));
	--print("Broadcasted Neuron ID : " & str(brodcastedNeuronIdInt)& "  " & time'image(now));
	--print("nextComp " & str(nextComp)  & "  " & time'image(now));
	
	
	AER: BroadcasterDistance Port Map (
		activeNeurons => activeNeuronsInt,
		brodcastedNeuronId  => brodcastedNeuronIdInt,
		clk => clk,
		enable => propagate,
		reset => reset
	);
	 
	
	--@connectSpikingNeuron;
	n0_0: SpikingNeuron PORT MAP ( 
		activate => activate0_0, 
		input => input0_0, 
		potential => potentialS0_0, 
		exc => exc0_0, 
		inh => inh0_0, 
		ovf => ovf0_0, 
		enable => compute, 
		clk => clk, 
		reset => reset 
	);
	excF0_0 : Synapse 
	generic map(
		rom_table =>
	(
	0 => "0000000000",
	1 => "0000000011",
	2 => "0000000101",
	3 => "0000001000",
	4 => "0000001010",
	),
	COOR => "01" & "01"
	)PORT MAP (
		brodcastedNeuronId => brodcastedNeuronIdInt,
		lateralFeedingOut => exc0_0,
		ovf => ovfexc0_0,
		enable => propagate,
		clk => clk,
		reset => nextComp
	);
	inhF0_0 : Synapse 
	generic map(
		rom_table =>
	(
	1 => "0000000001",
	2 => "0000000011",
	3 => "0000000100",
	4 => "0000000101",
	5 => "0000000110",
	6 => "0000001000",
	7 => "0000001001",
	8 => "0000001010",
	9 => "0000001100",
	others => (others => '0'))
	)PORT MAP (
		brodcastedNeuronId => brodcastedNeuronIdInt,
		lateralFeedingOut => inh0_0,
		ovf => ovfinh0_0,
		enable => propagate,
		clk => clk,
		reset => nextComp
	);
	n0_1: SpikingNeuron PORT MAP ( 
		activate => activate0_1, 
		input => input0_1, 
		potential => potentialS0_1, 
		exc => exc0_1, 
		inh => inh0_1, 
		ovf => ovf0_1, 
		enable => compute, 
		clk => clk, 
		reset => reset 
	);
	excF0_1 : Synapse 
	generic map(
		rom_table =>
	(
	1 => "0000000011",
	2 => "0000000101",
	3 => "0000001000",
	4 => "0000001010",
	5 => "0000001101",
	6 => "0000001111",
	7 => "0000010010",
	8 => "0000010100",
	9 => "0000010111",
	others => (others => '0'))
	)PORT MAP (
		brodcastedNeuronId => brodcastedNeuronIdInt,
		lateralFeedingOut => exc0_1,
		ovf => ovfexc0_1,
		enable => propagate,
		clk => clk,
		reset => nextComp
	);
	inhF0_1 : Synapse 
	generic map(
		rom_table =>
	(
	1 => "0000000001",
	2 => "0000000011",
	3 => "0000000100",
	4 => "0000000101",
	5 => "0000000110",
	6 => "0000001000",
	7 => "0000001001",
	8 => "0000001010",
	9 => "0000001100",
	others => (others => '0'))
	)PORT MAP (
		brodcastedNeuronId => brodcastedNeuronIdInt,
		lateralFeedingOut => inh0_1,
		ovf => ovfinh0_1,
		enable => propagate,
		clk => clk,
		reset => nextComp
	);
	n0_2: SpikingNeuron PORT MAP ( 
		activate => activate0_2, 
		input => input0_2, 
		potential => potentialS0_2, 
		exc => exc0_2, 
		inh => inh0_2, 
		ovf => ovf0_2, 
		enable => compute, 
		clk => clk, 
		reset => reset 
	);
	excF0_2 : Synapse 
	generic map(
		rom_table =>
	(
	1 => "0000000011",
	2 => "0000000101",
	3 => "0000001000",
	4 => "0000001010",
	5 => "0000001101",
	6 => "0000001111",
	7 => "0000010010",
	8 => "0000010100",
	9 => "0000010111",
	others => (others => '0'))
	)PORT MAP (
		brodcastedNeuronId => brodcastedNeuronIdInt,
		lateralFeedingOut => exc0_2,
		ovf => ovfexc0_2,
		enable => propagate,
		clk => clk,
		reset => nextComp
	);
	inhF0_2 : Synapse 
	generic map(
		rom_table =>
	(
	1 => "0000000001",
	2 => "0000000011",
	3 => "0000000100",
	4 => "0000000101",
	5 => "0000000110",
	6 => "0000001000",
	7 => "0000001001",
	8 => "0000001010",
	9 => "0000001100",
	others => (others => '0'))
	)PORT MAP (
		brodcastedNeuronId => brodcastedNeuronIdInt,
		lateralFeedingOut => inh0_2,
		ovf => ovfinh0_2,
		enable => propagate,
		clk => clk,
		reset => nextComp
	);
	n1_0: SpikingNeuron PORT MAP ( 
		activate => activate1_0, 
		input => input1_0, 
		potential => potentialS1_0, 
		exc => exc1_0, 
		inh => inh1_0, 
		ovf => ovf1_0, 
		enable => compute, 
		clk => clk, 
		reset => reset 
	);
	excF1_0 : Synapse 
	generic map(
		rom_table =>
	(
	1 => "0000000011",
	2 => "0000000101",
	3 => "0000001000",
	4 => "0000001010",
	5 => "0000001101",
	6 => "0000001111",
	7 => "0000010010",
	8 => "0000010100",
	9 => "0000010111",
	others => (others => '0'))
	)PORT MAP (
		brodcastedNeuronId => brodcastedNeuronIdInt,
		lateralFeedingOut => exc1_0,
		ovf => ovfexc1_0,
		enable => propagate,
		clk => clk,
		reset => nextComp
	);
	inhF1_0 : Synapse 
	generic map(
		rom_table =>
	(
	1 => "0000000001",
	2 => "0000000011",
	3 => "0000000100",
	4 => "0000000101",
	5 => "0000000110",
	6 => "0000001000",
	7 => "0000001001",
	8 => "0000001010",
	9 => "0000001100",
	others => (others => '0'))
	)PORT MAP (
		brodcastedNeuronId => brodcastedNeuronIdInt,
		lateralFeedingOut => inh1_0,
		ovf => ovfinh1_0,
		enable => propagate,
		clk => clk,
		reset => nextComp
	);
	n1_1: SpikingNeuron PORT MAP ( 
		activate => activate1_1, 
		input => input1_1, 
		potential => potentialS1_1, 
		exc => exc1_1, 
		inh => inh1_1, 
		ovf => ovf1_1, 
		enable => compute, 
		clk => clk, 
		reset => reset 
	);
	excF1_1 : Synapse 
	generic map(
		rom_table =>
	(
	1 => "0000000011",
	2 => "0000000101",
	3 => "0000001000",
	4 => "0000001010",
	5 => "0000001101",
	6 => "0000001111",
	7 => "0000010010",
	8 => "0000010100",
	9 => "0000010111",
	others => (others => '0'))
	)PORT MAP (
		brodcastedNeuronId => brodcastedNeuronIdInt,
		lateralFeedingOut => exc1_1,
		ovf => ovfexc1_1,
		enable => propagate,
		clk => clk,
		reset => nextComp
	);
	inhF1_1 : Synapse 
	generic map(
		rom_table =>
	(
	1 => "0000000001",
	2 => "0000000011",
	3 => "0000000100",
	4 => "0000000101",
	5 => "0000000110",
	6 => "0000001000",
	7 => "0000001001",
	8 => "0000001010",
	9 => "0000001100",
	others => (others => '0'))
	)PORT MAP (
		brodcastedNeuronId => brodcastedNeuronIdInt,
		lateralFeedingOut => inh1_1,
		ovf => ovfinh1_1,
		enable => propagate,
		clk => clk,
		reset => nextComp
	);
	n1_2: SpikingNeuron PORT MAP ( 
		activate => activate1_2, 
		input => input1_2, 
		potential => potentialS1_2, 
		exc => exc1_2, 
		inh => inh1_2, 
		ovf => ovf1_2, 
		enable => compute, 
		clk => clk, 
		reset => reset 
	);
	excF1_2 : Synapse 
	generic map(
		rom_table =>
	(
	1 => "0000000011",
	2 => "0000000101",
	3 => "0000001000",
	4 => "0000001010",
	5 => "0000001101",
	6 => "0000001111",
	7 => "0000010010",
	8 => "0000010100",
	9 => "0000010111",
	others => (others => '0'))
	)PORT MAP (
		brodcastedNeuronId => brodcastedNeuronIdInt,
		lateralFeedingOut => exc1_2,
		ovf => ovfexc1_2,
		enable => propagate,
		clk => clk,
		reset => nextComp
	);
	inhF1_2 : Synapse 
	generic map(
		rom_table =>
	(
	1 => "0000000001",
	2 => "0000000011",
	3 => "0000000100",
	4 => "0000000101",
	5 => "0000000110",
	6 => "0000001000",
	7 => "0000001001",
	8 => "0000001010",
	9 => "0000001100",
	others => (others => '0'))
	)PORT MAP (
		brodcastedNeuronId => brodcastedNeuronIdInt,
		lateralFeedingOut => inh1_2,
		ovf => ovfinh1_2,
		enable => propagate,
		clk => clk,
		reset => nextComp
	);
	n2_0: SpikingNeuron PORT MAP ( 
		activate => activate2_0, 
		input => input2_0, 
		potential => potentialS2_0, 
		exc => exc2_0, 
		inh => inh2_0, 
		ovf => ovf2_0, 
		enable => compute, 
		clk => clk, 
		reset => reset 
	);
	excF2_0 : Synapse 
	generic map(
		rom_table =>
	(
	1 => "0000000011",
	2 => "0000000101",
	3 => "0000001000",
	4 => "0000001010",
	5 => "0000001101",
	6 => "0000001111",
	7 => "0000010010",
	8 => "0000010100",
	9 => "0000010111",
	others => (others => '0'))
	)PORT MAP (
		brodcastedNeuronId => brodcastedNeuronIdInt,
		lateralFeedingOut => exc2_0,
		ovf => ovfexc2_0,
		enable => propagate,
		clk => clk,
		reset => nextComp
	);
	inhF2_0 : Synapse 
	generic map(
		rom_table =>
	(
	1 => "0000000001",
	2 => "0000000011",
	3 => "0000000100",
	4 => "0000000101",
	5 => "0000000110",
	6 => "0000001000",
	7 => "0000001001",
	8 => "0000001010",
	9 => "0000001100",
	others => (others => '0'))
	)PORT MAP (
		brodcastedNeuronId => brodcastedNeuronIdInt,
		lateralFeedingOut => inh2_0,
		ovf => ovfinh2_0,
		enable => propagate,
		clk => clk,
		reset => nextComp
	);
	n2_1: SpikingNeuron PORT MAP ( 
		activate => activate2_1, 
		input => input2_1, 
		potential => potentialS2_1, 
		exc => exc2_1, 
		inh => inh2_1, 
		ovf => ovf2_1, 
		enable => compute, 
		clk => clk, 
		reset => reset 
	);
	excF2_1 : Synapse 
	generic map(
		rom_table =>
	(
	1 => "0000000011",
	2 => "0000000101",
	3 => "0000001000",
	4 => "0000001010",
	5 => "0000001101",
	6 => "0000001111",
	7 => "0000010010",
	8 => "0000010100",
	9 => "0000010111",
	others => (others => '0'))
	)PORT MAP (
		brodcastedNeuronId => brodcastedNeuronIdInt,
		lateralFeedingOut => exc2_1,
		ovf => ovfexc2_1,
		enable => propagate,
		clk => clk,
		reset => nextComp
	);
	inhF2_1 : Synapse 
	generic map(
		rom_table =>
	(
	1 => "0000000001",
	2 => "0000000011",
	3 => "0000000100",
	4 => "0000000101",
	5 => "0000000110",
	6 => "0000001000",
	7 => "0000001001",
	8 => "0000001010",
	9 => "0000001100",
	others => (others => '0'))
	)PORT MAP (
		brodcastedNeuronId => brodcastedNeuronIdInt,
		lateralFeedingOut => inh2_1,
		ovf => ovfinh2_1,
		enable => propagate,
		clk => clk,
		reset => nextComp
	);
	n2_2: SpikingNeuron PORT MAP ( 
		activate => activate2_2, 
		input => input2_2, 
		potential => potentialS2_2, 
		exc => exc2_2, 
		inh => inh2_2, 
		ovf => ovf2_2, 
		enable => compute, 
		clk => clk, 
		reset => reset 
	);
	excF2_2 : Synapse 
	generic map(
		rom_table =>
	(
	1 => "0000000011",
	2 => "0000000101",
	3 => "0000001000",
	4 => "0000001010",
	5 => "0000001101",
	6 => "0000001111",
	7 => "0000010010",
	8 => "0000010100",
	9 => "0000010111",
	others => (others => '0'))
	)PORT MAP (
		brodcastedNeuronId => brodcastedNeuronIdInt,
		lateralFeedingOut => exc2_2,
		ovf => ovfexc2_2,
		enable => propagate,
		clk => clk,
		reset => nextComp
	);
	inhF2_2 : Synapse 
	generic map(
		rom_table =>
	(
	1 => "0000000001",
	2 => "0000000011",
	3 => "0000000100",
	4 => "0000000101",
	5 => "0000000110",
	6 => "0000001000",
	7 => "0000001001",
	8 => "0000001010",
	9 => "0000001100",
	others => (others => '0'))
	)PORT MAP (
		brodcastedNeuronId => brodcastedNeuronIdInt,
		lateralFeedingOut => inh2_2,
		ovf => ovfinh2_2,
		enable => propagate,
		clk => clk,
		reset => nextComp
	);

	--@END;

	


end Behavioral;

