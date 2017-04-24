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
br = zeros(1,2);
position = 0;
growTo = 0; %#ok<*NASGU>
% Perform incremental search
for i = a:h:b-h
  br_sign = f(i) * f(i+h);

    % Evaluate function at endpoints, if their
    % product is nonpositive, then the bracket
    % contains a root.
    if (br_sign < 0)
        tmpRowCount = size(br,1);
        if(position + 1) > tmpRowCount            
            growTo = tmpRowCount*( ...
                    1*((2^31) - tmpRowCount > ( (2^31)/ 2 )) ...
                    + (1/2)*((2^31) - tmpRowCount > ( (2^31)/ 4 ))...
                    + (1/3)*((2^31) - tmpRowCount > ( (2^31)/ 6 ))...
                    + (1/6)*((2^31) - tmpRowCount > ( (2^31)/ 8 ))) ;
            br = [br, zeros(1,growTo)]; %#ok<AGROW>
        end
        position = position +1;
        % Bracket contains a root so append 
        % current bracket to bracket array.
        br(position,:) = [i, i+h];
    elseif (br_sign == 0)
        tmpRowCount = size(br,1);
        if(position + 1) > tmpRowCount            
            growTo = tmpRowCount*( ...
                    1*((2^31) - tmpRowCount > ( (2^31)/ 2 )) ...
                    + (1/2)*((2^31) - tmpRowCount > ( (2^31)/ 4 ))...
                    + (1/3)*((2^31) - tmpRowCount > ( (2^31)/ 6 ))...
                    + (1/6)*((2^31) - tmpRowCount > ( (2^31)/ 8 ))) ;
            br = [br, zeros(1,growTo)]; %#ok<AGROW>
        end
        position = position +1;
        % Bracket contains a root so append 
        % current bracket to bracket array.
        br(position,:) = [i-(h/2), i+(h/2)];
    end
end
end
