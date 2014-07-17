a = strtod(read_csv("data/a_send/size9/time1/weigth0.0/ReceiveMap_4999_0.9.csv"));
b = strtod(read_csv("data/b_send/size9/time1/weigth0.0/ReceiveMap_4999_0.9.csv"));
ab = strtod(read_csv("data/ab_send/size9/time1/weigth0.0/ReceiveMap_4999_0.9.csv"));
res = ab-a-b
disp(res);

epsilon = 0; 0.000000001;

function[fitness] = computeFitness(A,B,ABe)
    ABt = A + B;
    M = abs(ABt - ABe)./(ABt + ABe + epsilon);
    fitness = sum(M) / length(A);
    return fitness;
endfunction

disp('fitness = '+string(computeFitness(a,b,ab)));
