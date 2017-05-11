function m = dq(f, x, h)
% m = dq(f, x, h): Computes the different quotient of a function f(x)
%                  at the point x with a step size of h. If only two
%                  arguments are passed, h defaults to 10^-6.
%
% input: f = Anonymous function to be used
%        x = The point at which to compute the difference quotient.
%        h = (optional) The step size.
%
% output: m = The slope of the line between the points (x, f(x)) and
%             (x + h, f(x + h)).

%%%%% Complete the following two steps. Use nargin to 
%%%%% determine how many arguments are passed
 

% If only two arguments are passed, set h = 10^-6
if nargin < 3
    h = 10^-6;
end %end if nargin < 3

% Compute the difference quotient 
m = (f(x + h) - f(x))./h;

end % end of function