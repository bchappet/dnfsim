library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use WORK.CNFTConfiguration.all;
entity cnft_map is
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

clk,compute,nextComp,reset : in std_logic
);
end cnft_map;
architecture Behavioral of cnft_map is
COMPONENT neuron_mixt0_0
PORT(
potential0_0 : in std_logic_vector(INT+FRAC-1 downto 0);
potential0_1 : in std_logic_vector(INT+FRAC-1 downto 0);
potential0_2 : in std_logic_vector(INT+FRAC-1 downto 0);
potential1_0 : in std_logic_vector(INT+FRAC-1 downto 0);
potential1_1 : in std_logic_vector(INT+FRAC-1 downto 0);
potential1_2 : in std_logic_vector(INT+FRAC-1 downto 0);
potential2_0 : in std_logic_vector(INT+FRAC-1 downto 0);
potential2_1 : in std_logic_vector(INT+FRAC-1 downto 0);
potential2_2 : in std_logic_vector(INT+FRAC-1 downto 0);
input : IN  std_logic_vector(INT+FRAC downto 0);
potentialOut : OUT  std_logic_vector(INT+FRAC-1 downto 0);
clk : IN  std_logic;
compute : IN std_logic;
nextComp : IN std_logic;
reset : IN  std_logic
);
END COMPONENT;
COMPONENT neuron_mixt0_1
PORT(
potential0_0 : in std_logic_vector(INT+FRAC-1 downto 0);
potential0_1 : in std_logic_vector(INT+FRAC-1 downto 0);
potential0_2 : in std_logic_vector(INT+FRAC-1 downto 0);
potential1_0 : in std_logic_vector(INT+FRAC-1 downto 0);
potential1_1 : in std_logic_vector(INT+FRAC-1 downto 0);
potential1_2 : in std_logic_vector(INT+FRAC-1 downto 0);
potential2_0 : in std_logic_vector(INT+FRAC-1 downto 0);
potential2_1 : in std_logic_vector(INT+FRAC-1 downto 0);
potential2_2 : in std_logic_vector(INT+FRAC-1 downto 0);
input : IN  std_logic_vector(INT+FRAC downto 0);
potentialOut : OUT  std_logic_vector(INT+FRAC-1 downto 0);
clk : IN  std_logic;
compute : IN std_logic;
nextComp : IN std_logic;
reset : IN  std_logic
);
END COMPONENT;
COMPONENT neuron_mixt0_2
PORT(
potential0_0 : in std_logic_vector(INT+FRAC-1 downto 0);
potential0_1 : in std_logic_vector(INT+FRAC-1 downto 0);
potential0_2 : in std_logic_vector(INT+FRAC-1 downto 0);
potential1_0 : in std_logic_vector(INT+FRAC-1 downto 0);
potential1_1 : in std_logic_vector(INT+FRAC-1 downto 0);
potential1_2 : in std_logic_vector(INT+FRAC-1 downto 0);
potential2_0 : in std_logic_vector(INT+FRAC-1 downto 0);
potential2_1 : in std_logic_vector(INT+FRAC-1 downto 0);
potential2_2 : in std_logic_vector(INT+FRAC-1 downto 0);
input : IN  std_logic_vector(INT+FRAC downto 0);
potentialOut : OUT  std_logic_vector(INT+FRAC-1 downto 0);
clk : IN  std_logic;
compute : IN std_logic;
nextComp : IN std_logic;
reset : IN  std_logic
);
END COMPONENT;
COMPONENT neuron_mixt1_0
PORT(
potential0_0 : in std_logic_vector(INT+FRAC-1 downto 0);
potential0_1 : in std_logic_vector(INT+FRAC-1 downto 0);
potential0_2 : in std_logic_vector(INT+FRAC-1 downto 0);
potential1_0 : in std_logic_vector(INT+FRAC-1 downto 0);
potential1_1 : in std_logic_vector(INT+FRAC-1 downto 0);
potential1_2 : in std_logic_vector(INT+FRAC-1 downto 0);
potential2_0 : in std_logic_vector(INT+FRAC-1 downto 0);
potential2_1 : in std_logic_vector(INT+FRAC-1 downto 0);
potential2_2 : in std_logic_vector(INT+FRAC-1 downto 0);
input : IN  std_logic_vector(INT+FRAC downto 0);
potentialOut : OUT  std_logic_vector(INT+FRAC-1 downto 0);
clk : IN  std_logic;
compute : IN std_logic;
nextComp : IN std_logic;
reset : IN  std_logic
);
END COMPONENT;
COMPONENT neuron_mixt1_1
PORT(
potential0_0 : in std_logic_vector(INT+FRAC-1 downto 0);
potential0_1 : in std_logic_vector(INT+FRAC-1 downto 0);
potential0_2 : in std_logic_vector(INT+FRAC-1 downto 0);
potential1_0 : in std_logic_vector(INT+FRAC-1 downto 0);
potential1_1 : in std_logic_vector(INT+FRAC-1 downto 0);
potential1_2 : in std_logic_vector(INT+FRAC-1 downto 0);
potential2_0 : in std_logic_vector(INT+FRAC-1 downto 0);
potential2_1 : in std_logic_vector(INT+FRAC-1 downto 0);
potential2_2 : in std_logic_vector(INT+FRAC-1 downto 0);
input : IN  std_logic_vector(INT+FRAC downto 0);
potentialOut : OUT  std_logic_vector(INT+FRAC-1 downto 0);
clk : IN  std_logic;
compute : IN std_logic;
nextComp : IN std_logic;
reset : IN  std_logic
);
END COMPONENT;
COMPONENT neuron_mixt1_2
PORT(
potential0_0 : in std_logic_vector(INT+FRAC-1 downto 0);
potential0_1 : in std_logic_vector(INT+FRAC-1 downto 0);
potential0_2 : in std_logic_vector(INT+FRAC-1 downto 0);
potential1_0 : in std_logic_vector(INT+FRAC-1 downto 0);
potential1_1 : in std_logic_vector(INT+FRAC-1 downto 0);
potential1_2 : in std_logic_vector(INT+FRAC-1 downto 0);
potential2_0 : in std_logic_vector(INT+FRAC-1 downto 0);
potential2_1 : in std_logic_vector(INT+FRAC-1 downto 0);
potential2_2 : in std_logic_vector(INT+FRAC-1 downto 0);
input : IN  std_logic_vector(INT+FRAC downto 0);
potentialOut : OUT  std_logic_vector(INT+FRAC-1 downto 0);
clk : IN  std_logic;
compute : IN std_logic;
nextComp : IN std_logic;
reset : IN  std_logic
);
END COMPONENT;
COMPONENT neuron_mixt2_0
PORT(
potential0_0 : in std_logic_vector(INT+FRAC-1 downto 0);
potential0_1 : in std_logic_vector(INT+FRAC-1 downto 0);
potential0_2 : in std_logic_vector(INT+FRAC-1 downto 0);
potential1_0 : in std_logic_vector(INT+FRAC-1 downto 0);
potential1_1 : in std_logic_vector(INT+FRAC-1 downto 0);
potential1_2 : in std_logic_vector(INT+FRAC-1 downto 0);
potential2_0 : in std_logic_vector(INT+FRAC-1 downto 0);
potential2_1 : in std_logic_vector(INT+FRAC-1 downto 0);
potential2_2 : in std_logic_vector(INT+FRAC-1 downto 0);
input : IN  std_logic_vector(INT+FRAC downto 0);
potentialOut : OUT  std_logic_vector(INT+FRAC-1 downto 0);
clk : IN  std_logic;
compute : IN std_logic;
nextComp : IN std_logic;
reset : IN  std_logic
);
END COMPONENT;
COMPONENT neuron_mixt2_1
PORT(
potential0_0 : in std_logic_vector(INT+FRAC-1 downto 0);
potential0_1 : in std_logic_vector(INT+FRAC-1 downto 0);
potential0_2 : in std_logic_vector(INT+FRAC-1 downto 0);
potential1_0 : in std_logic_vector(INT+FRAC-1 downto 0);
potential1_1 : in std_logic_vector(INT+FRAC-1 downto 0);
potential1_2 : in std_logic_vector(INT+FRAC-1 downto 0);
potential2_0 : in std_logic_vector(INT+FRAC-1 downto 0);
potential2_1 : in std_logic_vector(INT+FRAC-1 downto 0);
potential2_2 : in std_logic_vector(INT+FRAC-1 downto 0);
input : IN  std_logic_vector(INT+FRAC downto 0);
potentialOut : OUT  std_logic_vector(INT+FRAC-1 downto 0);
clk : IN  std_logic;
compute : IN std_logic;
nextComp : IN std_logic;
reset : IN  std_logic
);
END COMPONENT;
COMPONENT neuron_mixt2_2
PORT(
potential0_0 : in std_logic_vector(INT+FRAC-1 downto 0);
potential0_1 : in std_logic_vector(INT+FRAC-1 downto 0);
potential0_2 : in std_logic_vector(INT+FRAC-1 downto 0);
potential1_0 : in std_logic_vector(INT+FRAC-1 downto 0);
potential1_1 : in std_logic_vector(INT+FRAC-1 downto 0);
potential1_2 : in std_logic_vector(INT+FRAC-1 downto 0);
potential2_0 : in std_logic_vector(INT+FRAC-1 downto 0);
potential2_1 : in std_logic_vector(INT+FRAC-1 downto 0);
potential2_2 : in std_logic_vector(INT+FRAC-1 downto 0);
input : IN  std_logic_vector(INT+FRAC downto 0);
potentialOut : OUT  std_logic_vector(INT+FRAC-1 downto 0);
clk : IN  std_logic;
compute : IN std_logic;
nextComp : IN std_logic;
reset : IN  std_logic
);
END COMPONENT;
type umemory_map is array(0 to RES-1,0 to RES-1) of std_logic_vector(INT+FRAC-1 downto 0);
 type smemory_map is array(0 to RES-1,0 to RES-1) of std_logic_vector(INT+FRAC downto 0);
type umemory_array is array(0 to INT+FRAC-1) of std_logic_vector(RES*RES-1 downto 0);
 type smemory_array is array(0 to INT+FRAC) of std_logic_vector(RES*RES-1 downto 0);
signal potential_map : umemory_array;
signal input_map : smemory_array;
signal potentialS0_0 : std_logic_vector(INT+FRAC-1 downto 0);
signal potentialS0_1 : std_logic_vector(INT+FRAC-1 downto 0);
signal potentialS0_2 : std_logic_vector(INT+FRAC-1 downto 0);
signal potentialS1_0 : std_logic_vector(INT+FRAC-1 downto 0);
signal potentialS1_1 : std_logic_vector(INT+FRAC-1 downto 0);
signal potentialS1_2 : std_logic_vector(INT+FRAC-1 downto 0);
signal potentialS2_0 : std_logic_vector(INT+FRAC-1 downto 0);
signal potentialS2_1 : std_logic_vector(INT+FRAC-1 downto 0);
signal potentialS2_2 : std_logic_vector(INT+FRAC-1 downto 0);

begin
potential0_0 <= potentialS0_0;
potential0_1 <= potentialS0_1;
potential0_2 <= potentialS0_2;
potential1_0 <= potentialS1_0;
potential1_1 <= potentialS1_1;
potential1_2 <= potentialS1_2;
potential2_0 <= potentialS2_0;
potential2_1 <= potentialS2_1;
potential2_2 <= potentialS2_2;

n0_0: neuron_mixt0_0 PORT MAP (
potential0_0 => (others => '0'),
potential0_1 => potentialS0_1,
potential0_2 => potentialS0_2,
potential1_0 => potentialS1_0,
potential1_1 => potentialS1_1,
potential1_2 => potentialS1_2,
potential2_0 => potentialS2_0,
potential2_1 => potentialS2_1,
potential2_2 => potentialS2_2,
input =>input0_0,
potentialOut => potentialS0_0,
clk => clk,
compute => compute,
nextComp => nextComp,
 reset => reset
);
n0_1: neuron_mixt0_1 PORT MAP (
potential0_0 => potentialS0_0,
potential0_1 => (others => '0'),
potential0_2 => potentialS0_2,
potential1_0 => potentialS1_0,
potential1_1 => potentialS1_1,
potential1_2 => potentialS1_2,
potential2_0 => potentialS2_0,
potential2_1 => potentialS2_1,
potential2_2 => potentialS2_2,
input =>input0_1,
potentialOut => potentialS0_1,
clk => clk,
compute => compute,
nextComp => nextComp,
 reset => reset
);
n0_2: neuron_mixt0_2 PORT MAP (
potential0_0 => potentialS0_0,
potential0_1 => potentialS0_1,
potential0_2 => (others => '0'),
potential1_0 => potentialS1_0,
potential1_1 => potentialS1_1,
potential1_2 => potentialS1_2,
potential2_0 => potentialS2_0,
potential2_1 => potentialS2_1,
potential2_2 => potentialS2_2,
input =>input0_2,
potentialOut => potentialS0_2,
clk => clk,
compute => compute,
nextComp => nextComp,
 reset => reset
);
n1_0: neuron_mixt1_0 PORT MAP (
potential0_0 => potentialS0_0,
potential0_1 => potentialS0_1,
potential0_2 => potentialS0_2,
potential1_0 => (others => '0'),
potential1_1 => potentialS1_1,
potential1_2 => potentialS1_2,
potential2_0 => potentialS2_0,
potential2_1 => potentialS2_1,
potential2_2 => potentialS2_2,
input =>input1_0,
potentialOut => potentialS1_0,
clk => clk,
compute => compute,
nextComp => nextComp,
 reset => reset
);
n1_1: neuron_mixt1_1 PORT MAP (
potential0_0 => potentialS0_0,
potential0_1 => potentialS0_1,
potential0_2 => potentialS0_2,
potential1_0 => potentialS1_0,
potential1_1 => (others => '0'),
potential1_2 => potentialS1_2,
potential2_0 => potentialS2_0,
potential2_1 => potentialS2_1,
potential2_2 => potentialS2_2,
input =>input1_1,
potentialOut => potentialS1_1,
clk => clk,
compute => compute,
nextComp => nextComp,
 reset => reset
);
n1_2: neuron_mixt1_2 PORT MAP (
potential0_0 => potentialS0_0,
potential0_1 => potentialS0_1,
potential0_2 => potentialS0_2,
potential1_0 => potentialS1_0,
potential1_1 => potentialS1_1,
potential1_2 => (others => '0'),
potential2_0 => potentialS2_0,
potential2_1 => potentialS2_1,
potential2_2 => potentialS2_2,
input =>input1_2,
potentialOut => potentialS1_2,
clk => clk,
compute => compute,
nextComp => nextComp,
 reset => reset
);
n2_0: neuron_mixt2_0 PORT MAP (
potential0_0 => potentialS0_0,
potential0_1 => potentialS0_1,
potential0_2 => potentialS0_2,
potential1_0 => potentialS1_0,
potential1_1 => potentialS1_1,
potential1_2 => potentialS1_2,
potential2_0 => (others => '0'),
potential2_1 => potentialS2_1,
potential2_2 => potentialS2_2,
input =>input2_0,
potentialOut => potentialS2_0,
clk => clk,
compute => compute,
nextComp => nextComp,
 reset => reset
);
n2_1: neuron_mixt2_1 PORT MAP (
potential0_0 => potentialS0_0,
potential0_1 => potentialS0_1,
potential0_2 => potentialS0_2,
potential1_0 => potentialS1_0,
potential1_1 => potentialS1_1,
potential1_2 => potentialS1_2,
potential2_0 => potentialS2_0,
potential2_1 => (others => '0'),
potential2_2 => potentialS2_2,
input =>input2_1,
potentialOut => potentialS2_1,
clk => clk,
compute => compute,
nextComp => nextComp,
 reset => reset
);
n2_2: neuron_mixt2_2 PORT MAP (
potential0_0 => potentialS0_0,
potential0_1 => potentialS0_1,
potential0_2 => potentialS0_2,
potential1_0 => potentialS1_0,
potential1_1 => potentialS1_1,
potential1_2 => potentialS1_2,
potential2_0 => potentialS2_0,
potential2_1 => potentialS2_1,
potential2_2 => (others => '0'),
input =>input2_2,
potentialOut => potentialS2_2,
clk => clk,
compute => compute,
nextComp => nextComp,
 reset => reset
);
END Behavioral;
