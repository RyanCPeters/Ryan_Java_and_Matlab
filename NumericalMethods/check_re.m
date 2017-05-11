function givens = check_re( approx, true )
%CHECK_RE Summary of this function goes here
%   Detailed explanation goes here

givens = myabs( ( approx - true ) / true );
end

