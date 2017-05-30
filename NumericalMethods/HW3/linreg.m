function line_func = linreg( firstArg, secondArg )
% function linreg(nBy2Data)
%   This custom function generates an anonymous function handle that
%   generates the line of best fit for the data that is being passed as
%   parameters in this function's call.
%
%   single input configuration:
%     linreg(firstArg)
%       firstarg = The data matrix for which you want a line of best fit.
%                  
%                  This matrix should be in the dimensions of n-rows by
%                  2-columns. 
%                  
%                  The first column is assumed to be your x-axis
%                  data values, and the second column is the y-axis data
%                  values.
%
%   double input configuration:
%     linreg(firstArg, secondArg)
%       inputs: -- Note: for the following parameters, the length (n) of
%                        both firstArg and secondArg should be the same, or 
%                        bad joojoo will happen... ;)
%
%         firstArg = The x-axis data values in a single column that is 
%                    n-rows long.
%           
%         secondArg = The y-axis data values in a single column that is 
%                     n-rows long.
%
%   
%
%   Outputs:
%         
%       line_func: An anonymouse function handle that can be used to
%       generate a line of best fit for the given data that was passed in
%       the initial function call.
%

% it is assumed that if nargin != 1, then 2 parameters have been passed and
% both of them are single column vectors

% if the user only passes one parameter, then we need to assign a value to
% the secondArg variable based upon the data in the 'y' column of the lone
% parameter that was passed.
if(nargin == 1)
    secondArg = firstArg(:,2);
elseif nargin > 2
    disp("you've passed more than 2 arguments in the linreg.m function call")
    disp("you might want to try again")
end

% now that we know secondArg has the required data, we are safe to update
% firstArg to be a 2 column matrix where the first column is the x-value
% data and the second column is a vector of all ones.
firstArg = [firstArg(:,1),ones(max(size(firstArg)),1)];

% using matlab's \ operator to do matrix inverse multiplication in order to
% derive the slope and y-intercept for our line of best fit.
slope_intercept = firstArg \ secondArg;

 
%  And here we are finally putting it all together as the function handle
%  for our return value.
line_func = @(x) slope_intercept(1) .* x + slope_intercept(2);
end

