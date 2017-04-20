function br = incsearch(f, a, b, n)
% br = incsearch(f, a, b, n): Incremental search on the function f
%                             over the interval (a,b) with n subdivisions.
%
% input: f = Anonymous function to find roots of.
%        a = Lower limit of initial interval.
%        b = Upper limit of initial interval.
%        n = Number of subdivisions.
%
% output: br = An n by 2 matrix of brackets. Each row
%              represents a bracket. The left column
%              is the lower limits of the brackets and
%              the right column is the upper limits.

% Set step size.
h = (b - a) / n;

% Initialize bracket array
br = zeros(1,2,n);
position = 0;
% Perform incremental search
for i = a:h:b-h
  

    % Evaluate function at endpoints, if their
    % product is nonpositive, then the bracket
    % contains a root.
    if (f(i) * f(i+h) <= 0)
        position = position +1;
        % Bracket contains a root so append 
        % current bracket to bracket array.
        br(:,:,position) = [i i+h];
    end

end
