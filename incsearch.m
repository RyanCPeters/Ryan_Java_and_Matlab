function br = incsearch(f, a, b, n)
%rough_br= incsearch(f, a, b, n): Incremental search on the function f
%                             over the interval (a,b) with n subdivisions.
%
% input: f = Anonymous function to find roots of.
%        a = Lower limit of initial interval.
%        b = Upper limit of initial interval.
%        n = Number of subdivisions.
%
% output:rough_br= An n by 2 matrix of brackets. Each row
%              represents a bracket. The left column
%              is the lower limits of the brackets and
%              the right column is the upper limits.

% Set step size.
h = (b - a) / n;

% Initialize bracket array
rough_br = zeros(n,2);
position = 0;
growTo = 0; %#ok<*NASGU>
% Perform incremental search
for i = a:h:b-h
  br_sign = f(i) * f(i+h);

    % Evaluate function at endpoints, if their
    % product is nonpositive, then the bracket
    % contains a root.
    if (br_sign < 0)
        
        position = position +1;
        % Bracket contains a root so append 
        % current bracket to bracket array.
        rough_br(position,:) = [i, i+h];
    elseif (br_sign == 0)
        rootApprox = i;
        
        % the rootAppros is assumed to be i, unless the following
        % condition proves true, in which case rootApprox = i+h instead.
        if(f(i+h) == 0)            
            rootApprox = i+h;
        end
        
        low = i;
        high = rootApprox+(h/2);
        if(position ~= 0)
            % if the following condition is true, we have the same root again
            % so it's ok to skip adding it to the bracket matrix this time.
            if((rough_br(position,2)) == (rootApprox+(h/2)))

                % continue passes control to the next iteration of a for or 
                % while loop. It skips any remaining statements in the body 
                % of the loop for the current iteration. The program continues 
                % execution from the next iteration.
                continue
            end

            
            low = rootApprox-(h/2);
        end
        position = position +1;
        % Bracket contains a root so append 
        % current bracket to bracket array.
        rough_br(position,:) = [low, high];
    end
end
br = rough_br(1:position,:);
end
