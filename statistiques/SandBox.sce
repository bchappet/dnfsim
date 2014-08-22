//
//A=matrix(1:12,[1,1,2,3,2]);
//// in this case mean(A,'m') is equivalent to mean(A,3), the first non singleton dimension of A
//y=mean(A,'m')
//
//disp(A,'A');
//disp(y,'y');

A = zeros(2,2,3);
A(:,:,1)=eye(2,2);
A(:,:,2)=eye(2,2);
A(:,:,3)=eye(2,2);

disp(mean(A,'m'));
