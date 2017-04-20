function r = nroot(num, n) 
% r = nroot(number, n): 
% 
% input: num is the value who's n'th root is desired
% input: n is the order of the root to which we will take num
% 
% output: the n'th root of num as a single double value
    if num > n;
        x = num/n;
    else ;
        x = n * num/(n * 2);
    endif;
%    delta = num^(1/n) - x;
    r = [x - (x^(n)-num) / ((n)*x^(n-1)), 1];
%	past_r = r;
    for i = 2:1000;
        if abs(x-r(1)) < .00000000001
%            r = [delta,past_r];
            return 
        endif;	
        x = r(1);
        r = [x - (x^(n)-num) / ((n)*x^(n-1)) , i];
%        past_r = [past_r, r]
%        delta = [delta, num^(1/n) - r];
	endfor
%	r = [ delta; past_r ]
endfunction