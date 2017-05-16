%% Author: R.Peters || Created: 2017-05-15


function retval = froot (f,x,g)
% function df(f, x, (optional)g)
%   -- note that the third parameter, g, is optional and should only be used as
%      a means to pass the derivative of the function, f, if it is known.
%
%   --This custom function by default will find the nearest root of a given function
%       with regard to the given initial guess value given in the parameter x.
%
%
%   inputs:     
%               f = the anonymous function for which we are seeking the root.
%
%               x = the initial guess for where on the x-axis the function 
%                   equals zero. Also note that x should only be given as a
%                   scalar value.
%
%               g = This is an optional parameter that can be used to provide a
%                   user desired form of the derivative of f.
%
%   Outputs:
%               This function will return the x-value approximation for where
%               the function f has an output of zero.
%

%% thought it's not needed here, I wanted to include a stamp of what the
%  agreed upon step size is from class.
% h = 0.31210;

%% the target delta between retval and the variable last, where last is only 
%  assigned inside of the for loop, is the assigned value from the course spec
target_delta = 5*10^(-10);

%% the following if/else block handles the logic for decideding if we are going
%  to be using the Newton-Raphson root finding method or if we will be using the
%  Secant method. 
%
%  That said, if the number of args passed into our froot function is greater than
%  2, then it's assumed that the third argument should be a user provided derivative
%  of the function that should have been passed into the first parameter.
if(nargin > 2)
    % nargin is greater than 2, so we should have a user provided derivative of 
    % the function f. So lets use it to solve in the Newton-Raphson method.
    worker_func = @(f,x1) x1 - (f(x1)/((g(x1)==0)*target_delta+g(x1)));    
else
    % there doesn't seem to be a given derivative of the function. So we will
    % use the secant method and approximate the derivative with the df function.
    worker_func = @(f,x1) x1 - (f(x1)/((df(f,x1)==0)*target_delta+df(f,x1)));
end
retval = worker_func(f,x);

% the search for the root will be done in this for loop. As such, we can expect
% that if last is close enough to retval, we will see the function self-abort
% and return retval as it is at that time.
for pass = 1:1000
    last = retval;
    retval = worker_func(f,retval);
    if(abs(last-retval)/(((abs(retval) == 0)*target_delta)+abs(retval)))< target_delta;
        break;
    end
end

    
end