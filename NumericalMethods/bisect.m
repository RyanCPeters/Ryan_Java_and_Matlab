function br = bisect(f,a,b)
    % returnable = bisect(f,a,b): blah blah
    %
    % input: 
    %		 f = The anonymous function for which we are seeking the root to.
    %			 This function should contain only 1 root on the interval [a b].
    %
    %		 a = The initial low bound for the interval.
    %
    %		 b = The initial high bound for the interval.
    %
    % output:
    %		A vector(array) of 2 elements that represent the lower then upper
    %		bounds of the derived interval containing the function root, and 
    %		is at most 10^-12 wide.

    br = [a b 0];
    format long
    for i = 1:100
        if(br(2) - br(1) < 10^-12),break,end;
        br = incsearch(f,br(1),br(2),2);
    end
    
end