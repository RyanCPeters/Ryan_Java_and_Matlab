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
            br = [br; zeros(growTo,2)]; %#ok<AGROW>
        end
        position = position +1;
        % Bracket contains a root so append 
        % current bracket to bracket array.
        br(position,:) = [i, i+h];
    elseif (br_sign == 0)
        rootApprox = i;
        % the rootAppros is assumed to be i, unless the following
        % condition proves true, in which case rootApprox = i+h instead.
        if(f(i+h) == 0)
            rootApprox = i+h;
        end
        if(br(end) == rootApprox)
            % continue passes control to the next iteration of a for or 
            % while loop. It skips any remaining statements in the body 
            % of the loop for the current iteration. The program continues 
            % execution from the next iteration.
            continue
        end
        tmpRowCount = size(br,1);
        if(position + 1) > tmpRowCount            
            growTo = tmpRowCount*( ...
                    1*((2^31) - tmpRowCount > ( (2^31)/ 2 )) ...
                    + (1/2)*((2^31) - tmpRowCount > ( (2^31)/ 4 ))...
                    + (1/3)*((2^31) - tmpRowCount > ( (2^31)/ 6 ))...
                    + (1/6)*((2^31) - tmpRowCount > ( (2^31)/ 8 ))) ;
            br = [br; zeros(growTo,2)]; %#ok<AGROW>
        end
        position = position +1;
        % Bracket contains a root so append 
        % current bracket to bracket array.
        br(position,:) = [rootApprox-(h/2), rootApprox+(h/2)];
    end
end
end
