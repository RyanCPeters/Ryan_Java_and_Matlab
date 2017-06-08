function coeff = cub_spline(x, y)
%
% Help Comments
%

% Build the vector containing the 2nd degree coefficients
% The vector will be the solution to the equation mx = b
% where m is built so that the ends of the splines have
% a zero 2nd derivative.

% Initialize m.
m = zeros(length(x));

% Build the lower diagonal portion.
m(2:end-1,1:end-2) = m(2:end-1,1:end-2) + diag(x(2:end-1)-x(1:end-2));

% Build the upper diagonal portion.
m(2:end-1,3:end) = m(2:end-1,3:end) + diag(x(3:end) - x(2:end-1));

% Build the main diagonal portion.
m(2:end-1,2:end-1) = m(2:end-1,2:end-1) + 2 * diag(x(3:end) - x(1:end-2));

% Set the corner positions. Since we are building a
%  natural cubic spline we set corners to 1.
m(1,1) = 1; m(end,end) = 1;

% Build the vector b. Note that we will use b for the linear
% coefficients of the spline later.
b = (y(3:end) - y(2:end-1)) ./ (x(3:end) - x(2:end-1));
b = b - ((y(2:end-1) - y(1:end-2)) ./ (x(2:end-1) - x(1:end-2)));
b = 3 * b;

% Add zeros to the end of the vector to accomodate natural spline condidtions.
b = [0; b; 0];

% Solve mx = b to get the coefficient vector c.
c = m \ b;

%%% Build the remaining coefficient vectors and use them to build coeff.
