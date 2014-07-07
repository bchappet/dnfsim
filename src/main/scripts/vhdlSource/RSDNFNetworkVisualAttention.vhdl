library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use WORK.RSDNFConfiguration.all;
entity RSDNFNetworkVisualAttention is
PORT(
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

clk,compute_clk,reset : in std_logic
);
end RSDNFNetworkVisualAttention;

architecture Behavioral of RSDNFNetworkVisualAttention is
component RSDNFSpikingNeuron is
port(
	 CLK: in STD_LOGIC;
	 COMPUTE_CLK : in STD_LOGIC;
	 Reset: in STD_LOGIC;
	 FeedingPixel: in std_logic_vector(INT+FRAC downto 0);
	 ExcitationSpikesFromNeighbors: in STD_LOGIC_VECTOR(NEIGHBORHOOD-1 downto 0);--(N,S,E,W)
	 InhibitionSpikesFromNeighbors: in STD_LOGIC_VECTOR(NEIGHBORHOOD-1 downto 0);
	 ExcitationSpikesToNeighbors: out STD_LOGIC_VECTOR(NEIGHBORHOOD-1 downto 0);
	 InhibitionSpikesToNeighbors: out STD_LOGIC_VECTOR(NEIGHBORHOOD-1 downto 0);
	 Potential:  out std_logic_vector(INT+FRAC-1 downto 0)
   );
end component;

--Signal definition
constant ExcitationSpikesToNeighborsNULL: STD_LOGIC_VECTOR(NEIGHBORHOOD-1 downto 0) := (others => '0');
constant InhibitionSpikesToNeighborsNULL: STD_LOGIC_VECTOR(NEIGHBORHOOD-1 downto 0) := (others => '0');
signal ExcitationSpikesToNeighbors0_0 : std_logic_vector(NEIGHBORHOOD-1 downto 0);
signal InhibitionSpikesToNeighbors0_0 : std_logic_vector(NEIGHBORHOOD-1 downto 0);
signal ExcitationSpikesToNeighbors0_1 : std_logic_vector(NEIGHBORHOOD-1 downto 0);
signal InhibitionSpikesToNeighbors0_1 : std_logic_vector(NEIGHBORHOOD-1 downto 0);
signal ExcitationSpikesToNeighbors0_2 : std_logic_vector(NEIGHBORHOOD-1 downto 0);
signal InhibitionSpikesToNeighbors0_2 : std_logic_vector(NEIGHBORHOOD-1 downto 0);
signal ExcitationSpikesToNeighbors1_0 : std_logic_vector(NEIGHBORHOOD-1 downto 0);
signal InhibitionSpikesToNeighbors1_0 : std_logic_vector(NEIGHBORHOOD-1 downto 0);
signal ExcitationSpikesToNeighbors1_1 : std_logic_vector(NEIGHBORHOOD-1 downto 0);
signal InhibitionSpikesToNeighbors1_1 : std_logic_vector(NEIGHBORHOOD-1 downto 0);
signal ExcitationSpikesToNeighbors1_2 : std_logic_vector(NEIGHBORHOOD-1 downto 0);
signal InhibitionSpikesToNeighbors1_2 : std_logic_vector(NEIGHBORHOOD-1 downto 0);
signal ExcitationSpikesToNeighbors2_0 : std_logic_vector(NEIGHBORHOOD-1 downto 0);
signal InhibitionSpikesToNeighbors2_0 : std_logic_vector(NEIGHBORHOOD-1 downto 0);
signal ExcitationSpikesToNeighbors2_1 : std_logic_vector(NEIGHBORHOOD-1 downto 0);
signal InhibitionSpikesToNeighbors2_1 : std_logic_vector(NEIGHBORHOOD-1 downto 0);
signal ExcitationSpikesToNeighbors2_2 : std_logic_vector(NEIGHBORHOOD-1 downto 0);
signal InhibitionSpikesToNeighbors2_2 : std_logic_vector(NEIGHBORHOOD-1 downto 0);

begin
Neuron0_0: RSDNFSpikingNeuron
port map(
CLK => CLK,
COMPUTE_CLK => compute_clk,
Reset => Reset,
FeedingPixel=> input0_0,
ExcitationSpikesFromNeighbors(N) => ExcitationSpikesToNeighborsNULL(S),
ExcitationSpikesFromNeighbors(S) => ExcitationSpikesToNeighbors0_1(N),
ExcitationSpikesFromNeighbors(E) => ExcitationSpikesToNeighbors1_0(W),
ExcitationSpikesFromNeighbors(W) => ExcitationSpikesToNeighborsNULL(E),
InhibitionSpikesFromNeighbors(N) => InhibitionSpikesToNeighborsNULL(S),
InhibitionSpikesFromNeighbors(S) => InhibitionSpikesToNeighbors0_1(N),
InhibitionSpikesFromNeighbors(E) => InhibitionSpikesToNeighbors1_0(W),
InhibitionSpikesFromNeighbors(W) => InhibitionSpikesToNeighborsNULL(E),
ExcitationSpikesToNeighbors => ExcitationSpikesToNeighbors0_0,
InhibitionSpikesToNeighbors => InhibitionSpikesToNeighbors0_0,
Potential => potential0_0
);
Neuron0_1: RSDNFSpikingNeuron
port map(
CLK => CLK,
COMPUTE_CLK => compute_clk,
Reset => Reset,
FeedingPixel=> input0_1,
ExcitationSpikesFromNeighbors(N) => ExcitationSpikesToNeighbors0_0(S),
ExcitationSpikesFromNeighbors(S) => ExcitationSpikesToNeighbors0_2(N),
ExcitationSpikesFromNeighbors(E) => ExcitationSpikesToNeighbors1_1(W),
ExcitationSpikesFromNeighbors(W) => ExcitationSpikesToNeighborsNULL(E),
InhibitionSpikesFromNeighbors(N) => InhibitionSpikesToNeighbors0_0(S),
InhibitionSpikesFromNeighbors(S) => InhibitionSpikesToNeighbors0_2(N),
InhibitionSpikesFromNeighbors(E) => InhibitionSpikesToNeighbors1_1(W),
InhibitionSpikesFromNeighbors(W) => InhibitionSpikesToNeighborsNULL(E),
ExcitationSpikesToNeighbors => ExcitationSpikesToNeighbors0_1,
InhibitionSpikesToNeighbors => InhibitionSpikesToNeighbors0_1,
Potential => potential0_1
);
Neuron0_2: RSDNFSpikingNeuron
port map(
CLK => CLK,
COMPUTE_CLK => compute_clk,
Reset => Reset,
FeedingPixel=> input0_2,
ExcitationSpikesFromNeighbors(N) => ExcitationSpikesToNeighbors0_1(S),
ExcitationSpikesFromNeighbors(S) => ExcitationSpikesToNeighborsNULL(N),
ExcitationSpikesFromNeighbors(E) => ExcitationSpikesToNeighbors1_2(W),
ExcitationSpikesFromNeighbors(W) => ExcitationSpikesToNeighborsNULL(E),
InhibitionSpikesFromNeighbors(N) => InhibitionSpikesToNeighbors0_1(S),
InhibitionSpikesFromNeighbors(S) => InhibitionSpikesToNeighborsNULL(N),
InhibitionSpikesFromNeighbors(E) => InhibitionSpikesToNeighbors1_2(W),
InhibitionSpikesFromNeighbors(W) => InhibitionSpikesToNeighborsNULL(E),
ExcitationSpikesToNeighbors => ExcitationSpikesToNeighbors0_2,
InhibitionSpikesToNeighbors => InhibitionSpikesToNeighbors0_2,
Potential => potential0_2
);
Neuron1_0: RSDNFSpikingNeuron
port map(
CLK => CLK,
COMPUTE_CLK => compute_clk,
Reset => Reset,
FeedingPixel=> input1_0,
ExcitationSpikesFromNeighbors(N) => ExcitationSpikesToNeighborsNULL(S),
ExcitationSpikesFromNeighbors(S) => ExcitationSpikesToNeighbors1_1(N),
ExcitationSpikesFromNeighbors(E) => ExcitationSpikesToNeighbors2_0(W),
ExcitationSpikesFromNeighbors(W) => ExcitationSpikesToNeighbors0_0(E),
InhibitionSpikesFromNeighbors(N) => InhibitionSpikesToNeighborsNULL(S),
InhibitionSpikesFromNeighbors(S) => InhibitionSpikesToNeighbors1_1(N),
InhibitionSpikesFromNeighbors(E) => InhibitionSpikesToNeighbors2_0(W),
InhibitionSpikesFromNeighbors(W) => InhibitionSpikesToNeighbors0_0(E),
ExcitationSpikesToNeighbors => ExcitationSpikesToNeighbors1_0,
InhibitionSpikesToNeighbors => InhibitionSpikesToNeighbors1_0,
Potential => potential1_0
);
Neuron1_1: RSDNFSpikingNeuron
port map(
CLK => CLK,
COMPUTE_CLK => compute_clk,
Reset => Reset,
FeedingPixel=> input1_1,
ExcitationSpikesFromNeighbors(N) => ExcitationSpikesToNeighbors1_0(S),
ExcitationSpikesFromNeighbors(S) => ExcitationSpikesToNeighbors1_2(N),
ExcitationSpikesFromNeighbors(E) => ExcitationSpikesToNeighbors2_1(W),
ExcitationSpikesFromNeighbors(W) => ExcitationSpikesToNeighbors0_1(E),
InhibitionSpikesFromNeighbors(N) => InhibitionSpikesToNeighbors1_0(S),
InhibitionSpikesFromNeighbors(S) => InhibitionSpikesToNeighbors1_2(N),
InhibitionSpikesFromNeighbors(E) => InhibitionSpikesToNeighbors2_1(W),
InhibitionSpikesFromNeighbors(W) => InhibitionSpikesToNeighbors0_1(E),
ExcitationSpikesToNeighbors => ExcitationSpikesToNeighbors1_1,
InhibitionSpikesToNeighbors => InhibitionSpikesToNeighbors1_1,
Potential => potential1_1
);
Neuron1_2: RSDNFSpikingNeuron
port map(
CLK => CLK,
COMPUTE_CLK => compute_clk,
Reset => Reset,
FeedingPixel=> input1_2,
ExcitationSpikesFromNeighbors(N) => ExcitationSpikesToNeighbors1_1(S),
ExcitationSpikesFromNeighbors(S) => ExcitationSpikesToNeighborsNULL(N),
ExcitationSpikesFromNeighbors(E) => ExcitationSpikesToNeighbors2_2(W),
ExcitationSpikesFromNeighbors(W) => ExcitationSpikesToNeighbors0_2(E),
InhibitionSpikesFromNeighbors(N) => InhibitionSpikesToNeighbors1_1(S),
InhibitionSpikesFromNeighbors(S) => InhibitionSpikesToNeighborsNULL(N),
InhibitionSpikesFromNeighbors(E) => InhibitionSpikesToNeighbors2_2(W),
InhibitionSpikesFromNeighbors(W) => InhibitionSpikesToNeighbors0_2(E),
ExcitationSpikesToNeighbors => ExcitationSpikesToNeighbors1_2,
InhibitionSpikesToNeighbors => InhibitionSpikesToNeighbors1_2,
Potential => potential1_2
);
Neuron2_0: RSDNFSpikingNeuron
port map(
CLK => CLK,
COMPUTE_CLK => compute_clk,
Reset => Reset,
FeedingPixel=> input2_0,
ExcitationSpikesFromNeighbors(N) => ExcitationSpikesToNeighborsNULL(S),
ExcitationSpikesFromNeighbors(S) => ExcitationSpikesToNeighbors2_1(N),
ExcitationSpikesFromNeighbors(E) => ExcitationSpikesToNeighborsNULL(W),
ExcitationSpikesFromNeighbors(W) => ExcitationSpikesToNeighbors1_0(E),
InhibitionSpikesFromNeighbors(N) => InhibitionSpikesToNeighborsNULL(S),
InhibitionSpikesFromNeighbors(S) => InhibitionSpikesToNeighbors2_1(N),
InhibitionSpikesFromNeighbors(E) => InhibitionSpikesToNeighborsNULL(W),
InhibitionSpikesFromNeighbors(W) => InhibitionSpikesToNeighbors1_0(E),
ExcitationSpikesToNeighbors => ExcitationSpikesToNeighbors2_0,
InhibitionSpikesToNeighbors => InhibitionSpikesToNeighbors2_0,
Potential => potential2_0
);
Neuron2_1: RSDNFSpikingNeuron
port map(
CLK => CLK,
COMPUTE_CLK => compute_clk,
Reset => Reset,
FeedingPixel=> input2_1,
ExcitationSpikesFromNeighbors(N) => ExcitationSpikesToNeighbors2_0(S),
ExcitationSpikesFromNeighbors(S) => ExcitationSpikesToNeighbors2_2(N),
ExcitationSpikesFromNeighbors(E) => ExcitationSpikesToNeighborsNULL(W),
ExcitationSpikesFromNeighbors(W) => ExcitationSpikesToNeighbors1_1(E),
InhibitionSpikesFromNeighbors(N) => InhibitionSpikesToNeighbors2_0(S),
InhibitionSpikesFromNeighbors(S) => InhibitionSpikesToNeighbors2_2(N),
InhibitionSpikesFromNeighbors(E) => InhibitionSpikesToNeighborsNULL(W),
InhibitionSpikesFromNeighbors(W) => InhibitionSpikesToNeighbors1_1(E),
ExcitationSpikesToNeighbors => ExcitationSpikesToNeighbors2_1,
InhibitionSpikesToNeighbors => InhibitionSpikesToNeighbors2_1,
Potential => potential2_1
);
Neuron2_2: RSDNFSpikingNeuron
port map(
CLK => CLK,
COMPUTE_CLK => compute_clk,
Reset => Reset,
FeedingPixel=> input2_2,
ExcitationSpikesFromNeighbors(N) => ExcitationSpikesToNeighbors2_1(S),
ExcitationSpikesFromNeighbors(S) => ExcitationSpikesToNeighborsNULL(N),
ExcitationSpikesFromNeighbors(E) => ExcitationSpikesToNeighborsNULL(W),
ExcitationSpikesFromNeighbors(W) => ExcitationSpikesToNeighbors1_2(E),
InhibitionSpikesFromNeighbors(N) => InhibitionSpikesToNeighbors2_1(S),
InhibitionSpikesFromNeighbors(S) => InhibitionSpikesToNeighborsNULL(N),
InhibitionSpikesFromNeighbors(E) => InhibitionSpikesToNeighborsNULL(W),
InhibitionSpikesFromNeighbors(W) => InhibitionSpikesToNeighbors1_2(E),
ExcitationSpikesToNeighbors => ExcitationSpikesToNeighbors2_2,
InhibitionSpikesToNeighbors => InhibitionSpikesToNeighbors2_2,
Potential => potential2_2
);
END Behavioral;
