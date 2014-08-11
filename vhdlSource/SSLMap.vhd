library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use WORK.CNFTConfiguration.all;
use WORK.mytypes_pkg.all;
entity SpikingCNFTMap is
PORT(
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

compute,propagate,clk,reset,nextComp : in std_logic
);
end SpikingCNFTMap;

architecture Behavioral of SpikingCNFTMap is

component SSLCell is
generic(
	EXC_TABLE : rom_table_distance_type;
	INH_TABLE : rom_table_distance_type
	);port(
	input : in std_logic_vector(INT + FRAC downto 0); --signed
	potential : out std_logic_vector(INT + FRAC - 1 downto 0); --unsigned
	inSpikes : in std_logic_vector(TRANS_OUT_WIDTH*4-1 downto 0);
	outSpikes : out std_logic_vector(TRANS_OUT_WIDTH*4-1 downto 0);
	compute : in std_logic; --last only one clock => compute the new potential of the neuron
	propagate: in std_logic; --Spike propagation should be (X-1)*2
	nextComp: in std_logic; --Prepare the next computation: reset the synapse accumulation
	clk,reset : in std_logic
);
end component;

--Signal definition
constant outSpikesNULL: STD_LOGIC_VECTOR(TRANS_OUT_WIDTH*4-1 downto 0) := (others => '0');
constant outSpikesInhNULL: STD_LOGIC_VECTOR(TRANS_OUT_WIDTH*4-1 downto 0) := (others => '0');
--@generateSignals;
signal outSpikes0_0 : std_logic_vector(TRANS_OUT_WIDTH*4-1 downto 0);
signal outSpikes0_1 : std_logic_vector(TRANS_OUT_WIDTH*4-1 downto 0);
signal outSpikes0_2 : std_logic_vector(TRANS_OUT_WIDTH*4-1 downto 0);
signal outSpikes1_0 : std_logic_vector(TRANS_OUT_WIDTH*4-1 downto 0);
signal outSpikes1_1 : std_logic_vector(TRANS_OUT_WIDTH*4-1 downto 0);
signal outSpikes1_2 : std_logic_vector(TRANS_OUT_WIDTH*4-1 downto 0);
signal outSpikes2_0 : std_logic_vector(TRANS_OUT_WIDTH*4-1 downto 0);
signal outSpikes2_1 : std_logic_vector(TRANS_OUT_WIDTH*4-1 downto 0);
signal outSpikes2_2 : std_logic_vector(TRANS_OUT_WIDTH*4-1 downto 0);


--@END;



constant exc_table: rom_table_distance_type :=
(
	--@generateExcTable;
	0 => "0000011010",
	1 => "0000110011",
	2 => "0001001101",
	3 => "0001100110"

	--@END;

);


constant inh_table: rom_table_distance_type  :=
(
	--@generateInhTable;
	0 => "0000001101",
	1 => "0000011010",
	2 => "0000100110",
	3 => "0000110011"

	--@END;

);


begin

--@connectSSLNeuron;		
Neuron0_0: SSLCell
GENERIC MAP ( 
	EXC_TABLE => exc_table,
	INH_TABLE => inh_table
)PORT MAP ( 
	CLK => CLK,
	nextComp => nextComp,
	propagate => propagate,
	compute => compute,
	Reset => Reset,
	input => input0_0, 
inSpikes((N+1)*TRANS_OUT_WIDTH-1 downto N*TRANS_OUT_WIDTH) => outSpikesNULL((S+1)*TRANS_OUT_WIDTH-1 downto S*TRANS_OUT_WIDTH),
inSpikes((S+1)*TRANS_OUT_WIDTH-1 downto S*TRANS_OUT_WIDTH) => outSpikes0_1((N+1)*TRANS_OUT_WIDTH-1 downto N*TRANS_OUT_WIDTH),
inSpikes((E+1)*TRANS_OUT_WIDTH-1 downto E*TRANS_OUT_WIDTH) => outSpikes1_0((W+1)*TRANS_OUT_WIDTH-1 downto W*TRANS_OUT_WIDTH),
inSpikes((W+1)*TRANS_OUT_WIDTH-1 downto W*TRANS_OUT_WIDTH) => outSpikesNULL((E+1)*TRANS_OUT_WIDTH-1 downto E*TRANS_OUT_WIDTH),

	potential => potential0_0, 
	outSpikes => outSpikes0_0
);
Neuron0_1: SSLCell
GENERIC MAP ( 
	EXC_TABLE => exc_table,
	INH_TABLE => inh_table
)PORT MAP ( 
	CLK => CLK,
	nextComp => nextComp,
	propagate => propagate,
	compute => compute,
	Reset => Reset,
	input => input0_1, 
inSpikes((N+1)*TRANS_OUT_WIDTH-1 downto N*TRANS_OUT_WIDTH) => outSpikes0_0((S+1)*TRANS_OUT_WIDTH-1 downto S*TRANS_OUT_WIDTH),
inSpikes((S+1)*TRANS_OUT_WIDTH-1 downto S*TRANS_OUT_WIDTH) => outSpikes0_2((N+1)*TRANS_OUT_WIDTH-1 downto N*TRANS_OUT_WIDTH),
inSpikes((E+1)*TRANS_OUT_WIDTH-1 downto E*TRANS_OUT_WIDTH) => outSpikes1_1((W+1)*TRANS_OUT_WIDTH-1 downto W*TRANS_OUT_WIDTH),
inSpikes((W+1)*TRANS_OUT_WIDTH-1 downto W*TRANS_OUT_WIDTH) => outSpikesNULL((E+1)*TRANS_OUT_WIDTH-1 downto E*TRANS_OUT_WIDTH),

	potential => potential0_1, 
	outSpikes => outSpikes0_1
);
Neuron0_2: SSLCell
GENERIC MAP ( 
	EXC_TABLE => exc_table,
	INH_TABLE => inh_table
)PORT MAP ( 
	CLK => CLK,
	nextComp => nextComp,
	propagate => propagate,
	compute => compute,
	Reset => Reset,
	input => input0_2, 
inSpikes((N+1)*TRANS_OUT_WIDTH-1 downto N*TRANS_OUT_WIDTH) => outSpikes0_1((S+1)*TRANS_OUT_WIDTH-1 downto S*TRANS_OUT_WIDTH),
inSpikes((S+1)*TRANS_OUT_WIDTH-1 downto S*TRANS_OUT_WIDTH) => outSpikesNULL((N+1)*TRANS_OUT_WIDTH-1 downto N*TRANS_OUT_WIDTH),
inSpikes((E+1)*TRANS_OUT_WIDTH-1 downto E*TRANS_OUT_WIDTH) => outSpikes1_2((W+1)*TRANS_OUT_WIDTH-1 downto W*TRANS_OUT_WIDTH),
inSpikes((W+1)*TRANS_OUT_WIDTH-1 downto W*TRANS_OUT_WIDTH) => outSpikesNULL((E+1)*TRANS_OUT_WIDTH-1 downto E*TRANS_OUT_WIDTH),

	potential => potential0_2, 
	outSpikes => outSpikes0_2
);
Neuron1_0: SSLCell
GENERIC MAP ( 
	EXC_TABLE => exc_table,
	INH_TABLE => inh_table
)PORT MAP ( 
	CLK => CLK,
	nextComp => nextComp,
	propagate => propagate,
	compute => compute,
	Reset => Reset,
	input => input1_0, 
inSpikes((N+1)*TRANS_OUT_WIDTH-1 downto N*TRANS_OUT_WIDTH) => outSpikesNULL((S+1)*TRANS_OUT_WIDTH-1 downto S*TRANS_OUT_WIDTH),
inSpikes((S+1)*TRANS_OUT_WIDTH-1 downto S*TRANS_OUT_WIDTH) => outSpikes1_1((N+1)*TRANS_OUT_WIDTH-1 downto N*TRANS_OUT_WIDTH),
inSpikes((E+1)*TRANS_OUT_WIDTH-1 downto E*TRANS_OUT_WIDTH) => outSpikes2_0((W+1)*TRANS_OUT_WIDTH-1 downto W*TRANS_OUT_WIDTH),
inSpikes((W+1)*TRANS_OUT_WIDTH-1 downto W*TRANS_OUT_WIDTH) => outSpikes0_0((E+1)*TRANS_OUT_WIDTH-1 downto E*TRANS_OUT_WIDTH),

	potential => potential1_0, 
	outSpikes => outSpikes1_0
);
Neuron1_1: SSLCell
GENERIC MAP ( 
	EXC_TABLE => exc_table,
	INH_TABLE => inh_table
)PORT MAP ( 
	CLK => CLK,
	nextComp => nextComp,
	propagate => propagate,
	compute => compute,
	Reset => Reset,
	input => input1_1, 
inSpikes((N+1)*TRANS_OUT_WIDTH-1 downto N*TRANS_OUT_WIDTH) => outSpikes1_0((S+1)*TRANS_OUT_WIDTH-1 downto S*TRANS_OUT_WIDTH),
inSpikes((S+1)*TRANS_OUT_WIDTH-1 downto S*TRANS_OUT_WIDTH) => outSpikes1_2((N+1)*TRANS_OUT_WIDTH-1 downto N*TRANS_OUT_WIDTH),
inSpikes((E+1)*TRANS_OUT_WIDTH-1 downto E*TRANS_OUT_WIDTH) => outSpikes2_1((W+1)*TRANS_OUT_WIDTH-1 downto W*TRANS_OUT_WIDTH),
inSpikes((W+1)*TRANS_OUT_WIDTH-1 downto W*TRANS_OUT_WIDTH) => outSpikes0_1((E+1)*TRANS_OUT_WIDTH-1 downto E*TRANS_OUT_WIDTH),

	potential => potential1_1, 
	outSpikes => outSpikes1_1
);
Neuron1_2: SSLCell
GENERIC MAP ( 
	EXC_TABLE => exc_table,
	INH_TABLE => inh_table
)PORT MAP ( 
	CLK => CLK,
	nextComp => nextComp,
	propagate => propagate,
	compute => compute,
	Reset => Reset,
	input => input1_2, 
inSpikes((N+1)*TRANS_OUT_WIDTH-1 downto N*TRANS_OUT_WIDTH) => outSpikes1_1((S+1)*TRANS_OUT_WIDTH-1 downto S*TRANS_OUT_WIDTH),
inSpikes((S+1)*TRANS_OUT_WIDTH-1 downto S*TRANS_OUT_WIDTH) => outSpikesNULL((N+1)*TRANS_OUT_WIDTH-1 downto N*TRANS_OUT_WIDTH),
inSpikes((E+1)*TRANS_OUT_WIDTH-1 downto E*TRANS_OUT_WIDTH) => outSpikes2_2((W+1)*TRANS_OUT_WIDTH-1 downto W*TRANS_OUT_WIDTH),
inSpikes((W+1)*TRANS_OUT_WIDTH-1 downto W*TRANS_OUT_WIDTH) => outSpikes0_2((E+1)*TRANS_OUT_WIDTH-1 downto E*TRANS_OUT_WIDTH),
	potential => potential1_2, 
	outSpikes => outSpikes1_2
);
Neuron2_0: SSLCell
GENERIC MAP ( 
	EXC_TABLE => exc_table,
	INH_TABLE => inh_table
)PORT MAP ( 
	CLK => CLK,
	nextComp => nextComp,
	propagate => propagate,
	compute => compute,
	Reset => Reset,
	input => input2_0, 
inSpikes((N+1)*TRANS_OUT_WIDTH-1 downto N*TRANS_OUT_WIDTH) => outSpikesNULL((S+1)*TRANS_OUT_WIDTH-1 downto S*TRANS_OUT_WIDTH),
inSpikes((S+1)*TRANS_OUT_WIDTH-1 downto S*TRANS_OUT_WIDTH) => outSpikes2_1((N+1)*TRANS_OUT_WIDTH-1 downto N*TRANS_OUT_WIDTH),
inSpikes((E+1)*TRANS_OUT_WIDTH-1 downto E*TRANS_OUT_WIDTH) => outSpikesNULL((W+1)*TRANS_OUT_WIDTH-1 downto W*TRANS_OUT_WIDTH),
inSpikes((W+1)*TRANS_OUT_WIDTH-1 downto W*TRANS_OUT_WIDTH) => outSpikes1_0((E+1)*TRANS_OUT_WIDTH-1 downto E*TRANS_OUT_WIDTH),

	potential => potential2_0, 
	outSpikes => outSpikes2_0
);
Neuron2_1: SSLCell
GENERIC MAP ( 
	EXC_TABLE => exc_table,
	INH_TABLE => inh_table
)PORT MAP ( 
	CLK => CLK,
	nextComp => nextComp,
	propagate => propagate,
	compute => compute,
	Reset => Reset,
	input => input2_1, 
inSpikes((N+1)*TRANS_OUT_WIDTH-1 downto N*TRANS_OUT_WIDTH) => outSpikes2_0((S+1)*TRANS_OUT_WIDTH-1 downto S*TRANS_OUT_WIDTH),
inSpikes((S+1)*TRANS_OUT_WIDTH-1 downto S*TRANS_OUT_WIDTH) => outSpikes2_2((N+1)*TRANS_OUT_WIDTH-1 downto N*TRANS_OUT_WIDTH),
inSpikes((E+1)*TRANS_OUT_WIDTH-1 downto E*TRANS_OUT_WIDTH) => outSpikesNULL((W+1)*TRANS_OUT_WIDTH-1 downto W*TRANS_OUT_WIDTH),
inSpikes((W+1)*TRANS_OUT_WIDTH-1 downto W*TRANS_OUT_WIDTH) => outSpikes1_1((E+1)*TRANS_OUT_WIDTH-1 downto E*TRANS_OUT_WIDTH),

	potential => potential2_1, 
	outSpikes => outSpikes2_1
);
Neuron2_2: SSLCell
GENERIC MAP ( 
	EXC_TABLE => exc_table,
	INH_TABLE => inh_table
)PORT MAP ( 
	CLK => CLK,
	nextComp => nextComp,
	propagate => propagate,
	compute => compute,
	Reset => Reset,
	input => input2_2, 
inSpikes((N+1)*TRANS_OUT_WIDTH-1 downto N*TRANS_OUT_WIDTH) => outSpikes2_1((S+1)*TRANS_OUT_WIDTH-1 downto S*TRANS_OUT_WIDTH),
inSpikes((S+1)*TRANS_OUT_WIDTH-1 downto S*TRANS_OUT_WIDTH) => outSpikesNULL((N+1)*TRANS_OUT_WIDTH-1 downto N*TRANS_OUT_WIDTH),
inSpikes((E+1)*TRANS_OUT_WIDTH-1 downto E*TRANS_OUT_WIDTH) => outSpikesNULL((W+1)*TRANS_OUT_WIDTH-1 downto W*TRANS_OUT_WIDTH),
inSpikes((W+1)*TRANS_OUT_WIDTH-1 downto W*TRANS_OUT_WIDTH) => outSpikes1_2((E+1)*TRANS_OUT_WIDTH-1 downto E*TRANS_OUT_WIDTH),

	potential => potential2_2, 
	outSpikes => outSpikes2_2
);

--@END;

END Behavioral;
