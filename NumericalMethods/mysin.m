function sin = mysin(x)
% sin = mysin(x): Computes the sine of x using a Taylor Seried. 
%                 x is assumed to be in the first quadrant.
%
% input: x = the value to compute the sine of.
% output sin = the sine of x.

% Set the center.
center = pi / 4;

% Translate input around center.
x = x - center;

% Compute sin(x) with a Taylor Series centered at pi/4.
