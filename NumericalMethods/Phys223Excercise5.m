%% 
% let the anonymous function 'Emission' be the equation:
%   (n1^2 * n2^2)/(lamb*(n2^2 - n1^2))

% this is that anonymous function to calculate the output value
% for a given intensity index an corresponding lambda value.
Emission = @(n2, lamb) (4*n2.^2)./(lamb .* (n2^2 - 4));

% set up vectors containing the emission line data for each device type
tvSpecLambs   = 10^(-9)*[692.58, 532.49, 484.50, 462.12];
vernierLambs  = 10^(-9)*[657.0, 485.6, 441.9, 407.0];
oceanOptLambs = 10^(-9)*[656.9, 486.2, 433.9, 409.7];

% the uncertainty for each device type's readings.
tvSpecErr   = 10^(-9) * 0.3;
vernierErr  = 10^(-9) * 3.5;
oceanOptErr = 10^(-9) * 1.2;

% set up point values for each device by entering
% the emision line vectors into our Emission function.
% Then save that output value and it's input as a coordinate pair
% in the point matricis named for their corresponding device datas.
tvSpecPoints = zeros(4,2);
vernierPoints = zeros(4,2);
oceanOptPoints = zeros(4,2);

for n = 3:6
    n
    tvSpecPoints(n-2,:)   = [Emission(n,1), Emission(n,tvSpecLambs(n-2))];
    vernierPoints(n-2,:)  = [Emission(n,1), Emission(n,vernierLambs(n-2))];
    oceanOptPoints(n-2,:) = [Emission(n,1), Emission(n,oceanOptLambs(n-2))];
    
end % end of the for n = 3:6 loop

tvBestLine = linreg(tvSpecPoints);
vernBestLine = linreg(vernierPoints);
oceanBestLine = linreg(oceanOptPoints);



figure('Name',...
    ['line plots for the TV Spec, Vernier,',...
    ' and Ocean Optics spectrophotometers'])

plot(tvSpecPoints(:,1),tvSpecPoints(:,2),':k',...
    vernierPoints(:,1),vernierPoints(:,2),':r',...
    oceanOptPoints(:,1),oceanOptPoints(:,2),':b')



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
    disp(['you have passed more than 2 arguments in the linreg.m function call'])
    disp(['you might want to try again'])
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

