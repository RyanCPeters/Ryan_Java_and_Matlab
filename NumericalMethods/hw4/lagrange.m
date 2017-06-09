function daFunkt = lagrange( x, y )
%% function lagrange(x, y)
%   the custom function lagrange(x,y) takes a set of n points and returns 
%   the lagrange polynomial that fits that set of points
%
%% inputs:  There are 2 inputs that need to be provided, a vector of x 
%           values, and a vector of corresponding y values. The data from 
%           these two vectors shoule represent a series of point
%           coordinates in the form of (x1,y1) through (xn, yn).
%
%     x :
%           The x values for the point coordinates through which we are
%           our derived curve should pass.... Dat's some good language ;)
%     y : 
%           The corresponding y values to the points of interest.
%
%% Outputs: 
%           There is a single out put from this function, the anonymous 
%           function handle named daFunkt.
%
%       daFunkt : 
%           This is the handle for the anonymous function generated that
%           can produce a curve which passes through all of the points
%           given in the (x,y) vectors.
%     
%   

%% variable initialization
daFunkt = @(x) 0; 
n = (length(x) == length(y))*length(x);

if n > 0
    % this loop will use jdx as the index position marker as it iterates over
    % the given x vector, this will let us set the curve's behavior around each
    % point of interest.
    for jdx = 1:n
        % yo is the current y-naught for this pass through the loop.   
        yo = y(jdx);
        % xo is the current x-naught for this pass through the loop.
        xo = x(jdx);
        
        % we are initializing multiDiffs to the first quotient factor of 
        % the ugly ugly mess of differences and quotients to come.
        if jdx == 1, firstPos = 2; else, firstPos = 1; end
        multiDiffs = @(pos) ((pos - x(firstPos))./(xo - x(firstPos)));

        for idx = 1:n
            % we want the sum of differences of the x value between all points
            % other than the one corresponding to the current position of jdx.
            if idx == jdx || idx == firstPos, continue; end
            multiDiffs = @(pos) multiDiffs(pos) .* ((pos - x(idx))./(xo - x(idx)));

        end % end of the for idx loop
        multiDiffs = @(pos) multiDiffs(pos) * yo;
        % here we are updating daFunkt as a sum of terms that form the
        % lagrange polynomial for any given position x.
        daFunkt = @(pos) daFunkt(pos) + multiDiffs(pos);
    end % end of the for jdx loop
else
    disp(['You Fail!!! ;) actually, it turns out the vectors x and y that',newline,...
        ' you provided are not of equal length'])
end % end of the if n > 0 block

end