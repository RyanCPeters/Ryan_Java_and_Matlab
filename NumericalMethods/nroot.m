function r = nroot(num, n) 
% r = nroot(number, n): 
% 
% input: num is the value who's n'th root is desired
% input: n is the order of the root to which we will take num
% 
% output: the n'th root of num as a single double value
% methodology: Asserting that the given inputs can be expressed
%              as :: num^(1/n) :: to find the n'th root of num.
%
%              We can thus see that the solution will always be
%              less than the value given by num/n. This assertion
%              will be the basis for the vaue used in our initial guess.               
    x = num/n;
    r = [  (n-1)*x/(2*n) - num/(2*x^(n-1)*n), 1];
    for i = 2:1000
        if abs(x-r(1)) < .00000000001
            return 
        endif
        x = r(1);
        r = [r; (n-1)*x/(2*n) - num/(2*x^(n-1)*n) , i];
    endfor
endfunction
