%% df_test is intended to allow a user to brute force their way through
%  finding a "universally" satisfactory step size when methodically seeking 
%  the derivative of a given function
%
% Also, it may be a good idea to set your format to long when you run this
% script, as the desired level of accuracy on the derivative output is
% pretty small.


%% using the clear here isn't mandatory, but if you have any doubts about
%  what is going on in the following logic structures it's a good idea to
%  clear any possible erroneus data.
clear 

%% creating the control variables to be used in the loop logic, this way
%  we only have to change a couple of lines to achieve consistent logic
%  updates to the whole structure.
maxVal = 10;
maxOrder = 4;
inc_steps = .001;

%% expected form of derives for order 1-4: --Note that these are all very simple test cases.

% for ln(x)
% first  (y'): 1/x
% second (y''): -1/(x*x)
% third  (y'''): 2/(x*x*x)
% fourth (y''''): -6/x^4
natLog = @(x) log(x);

% for e^(2*x)
% first  (y'): 2*e^(2*x)
% second (y''): 4*e^(2*x)
% third  (y'''): 8*e^(2*x)
% fourth (y''''): 16*e^(2*x)
expo = @(x) exp(2*x);

% for sin(x)
% first  (y'): cos(x)
% second (y''): -sin(x)
% third  (y'''): -cos(x)
% fourth (y''''): sin(x)
sine = @(x) sin(x);

% for cos(x)
% first  (y'): -sin(x)
% second (y''): -cos(x)
% third  (y'''): sin(x)
% fourth (y''''): cos(x)
cosine = @(x) cos(x);

% for x*sin(x)
% first  (y'): sin(x) + x*cos(x)
% second (y''): 2*cos(x) - x*sin(x)
% third  (y'''): -3*sin(x) - x*cos(x)
% fourth (y''''): x*sin(x) - 4*cos(x)
xsine = @(x) x*sin(x);

% for x*cos(x)
% first  (y'): cos(x) - x*sin(x)
% second (y''): -2*sin(x) - x*cos(x)
% third  (y'''): x*sin(x) - 3*cos(x)
% fourth (y''''): 4*sin(x) + x*cos(x)
xcosine = @(x) x*cos(x);


%% preassigning storage variables so we don't have to do a shitload of
%  matrix concatination during the loops:

%% the first through fourth holders will we eventually store 
%  the averaged difference from our approximation to the assumed true value 
%  according to the simple calculated derivatives above on a per-order of
%  derivative basis.
%
%  Each row in a derivative holder is going to be the average of the 
%  function holders at that specific row.
holderFirst = zeros((1/inc_steps)*maxVal,2);
holderSecond = zeros((1/inc_steps)*maxVal,2);
holderThird = zeros((1/inc_steps)*maxVal,2);
holderFourth = zeros((1/inc_steps)*maxVal,2);
deriv_pos = 0;

% these values will be used inside the inner most loop as a means to track
% and report progress through the total process. Currently, it's set to 
% print status updates every 10% of the way through the total process.
num_steps = (1/inc_steps)*maxVal*maxOrder;
benchmarkNotice = num_steps / 10;

% the following holder vars will each contain the approximate, then true,
% then relative difference values between true and approx for their
% correspondingly named functions.(as such they will be of the same size as
% holder.
logHolder   = zeros((1/inc_steps)*maxVal*maxOrder,2);
expoHolder  = zeros((1/inc_steps)*maxVal*maxOrder,2);
sineHolder  = zeros((1/inc_steps)*maxVal*maxOrder,2);
cosHolder   = zeros((1/inc_steps)*maxVal*maxOrder,2);
xsineHolder = zeros((1/inc_steps)*maxVal*maxOrder,2);
xcosHolder  = zeros((1/inc_steps)*maxVal*maxOrder,2);

% counter will represent the actual index position for the matrices as we
% populate them, this is much easier to think about than trying to derive
% some index value from the combination of step, val, and order. counter is
% updated at the start of each pass through order's for loop.
counter = 0;

%% the following for loops are structured in a sequence of:
%   step_size [ value_of_x ( order_of_deriv{ %evaluate and store stuff at this level% } ) ]
for step = inc_steps : inc_steps : 1    
    for val = 1:maxVal
        deriv_pos = deriv_pos + 1;
        for order = 1:maxOrder
            
            counter = counter +1;
            
            % this if-statement provides a periodic output to the console 
            % confirming that the script is still making progress.
            if(mod(counter,benchmarkNotice) == 0),disp(counter/num_steps),end
            
            % the first column in each matrix is the derived approximation.
            logHolder(counter,1)   = df(natLog,val,order,step);
            expoHolder(counter,1)  = df(expo,val,order,step);
            sineHolder(counter,1)  = df(sine,val,order,step);
            cosHolder(counter,1)   = df(cosine,val,order,step);
            xsineHolder(counter,1) = df(xsine,val,order,step);
            xcosHolder(counter,1)  = df(xcosine,val,order,step);
            
            switch order
                % inside this switch block we assign the "true" value for
                % according to order of derivative.
                case 1
                    
                    logHolder(counter,2)   = (1/val);
                    expoHolder(counter,2)  = 2*exp(2*val);
                    sineHolder(counter,2)  = cos(val);
                    cosHolder(counter,2)   = -sin(val);
                    xsineHolder(counter,2) = sin(val) + val*cos(val);
                    xcosHolder(counter,2)  = cos(val) - val*sin(val);
                    
                    % with all of our values assigned for the current config of
                    % step->x_position->deriv_order, we can now take an average of
                    % the relative errors for later comparison.
                    holderFirst(deriv_pos,:) = ...
                          [abs((logHolder(counter,1) - logHolder(counter,2)) / logHolder(counter,2))...
                        + abs((expoHolder(counter,1) - expoHolder(counter,2)) / expoHolder(counter,2))...
                        + abs((sineHolder(counter,1) - sineHolder(counter,2)) / sineHolder(counter,2))...
                        + abs((cosHolder(counter,1) - cosHolder(counter,2)) / cosHolder(counter,2))...
                        + abs((xsineHolder(counter,1) - xsineHolder(counter,2)) / xsineHolder(counter,2))...
                        + abs((xcosHolder(counter,1) - xcosHolder(counter,2)) / xcosHolder(counter,2))...
                        / 6,step];   
                    
                case 2
                    
                    logHolder(counter,2)   = (-1/(val*val));
                    expoHolder(counter,2)  = 4*exp(2*val);
                    sineHolder(counter,2)  = -sin(val);
                    cosHolder(counter,2)   = -cos(val);
                    xsineHolder(counter,2) = -2*sin(val) - val*cos(val);
                    xcosHolder(counter,2)  = -2*sin(val) - val*cos(val);
                    
                    % with all of our values assigned for the current config of
                    % step->x_position->deriv_order, we can now take an average of
                    % the relative errors for later comparison.
                    holderSecond(deriv_pos,:) = ...
                          [abs((logHolder(counter,1) - logHolder(counter,2)) / logHolder(counter,2))...
                        + abs((expoHolder(counter,1) - expoHolder(counter,2)) / expoHolder(counter,2))...
                        + abs((sineHolder(counter,1) - sineHolder(counter,2)) / sineHolder(counter,2))...
                        + abs((cosHolder(counter,1) - cosHolder(counter,2)) / cosHolder(counter,2))...
                        + abs((xsineHolder(counter,1) - xsineHolder(counter,2)) / xsineHolder(counter,2))...
                        + abs((xcosHolder(counter,1) - xcosHolder(counter,2)) / xcosHolder(counter,2))...
                        / 6,step];  
                    
                case 3
                    
                    logHolder(counter,2)   = (2/(val*val*val));
                    expoHolder(counter,2)  = 8*exp(2*val);
                    sineHolder(counter,2)  = -cos(val);
                    cosHolder(counter,2)   = sin(val);
                    xsineHolder(counter,2) = -3*sin(val) - val*cos(val);
                    xcosHolder(counter,2)  = val*sin(val) - 3*cos(val);
                    
                    % with all of our values assigned for the current config of
                    % step->x_position->deriv_order, we can now take an average of
                    % the relative errors for later comparison.
                    holderThird(deriv_pos,:) = ...
                          [abs((logHolder(counter,1) - logHolder(counter,2)) / logHolder(counter,2))...
                        + abs((expoHolder(counter,1) - expoHolder(counter,2)) / expoHolder(counter,2))...
                        + abs((sineHolder(counter,1) - sineHolder(counter,2)) / sineHolder(counter,2))...
                        + abs((cosHolder(counter,1) - cosHolder(counter,2)) / cosHolder(counter,2))...
                        + abs((xsineHolder(counter,1) - xsineHolder(counter,2)) / xsineHolder(counter,2))...
                        + abs((xcosHolder(counter,1) - xcosHolder(counter,2)) / xcosHolder(counter,2))...
                        / 6,step];    
                    
                case 4
                    
                    logHolder(counter,2)   = (-6/(val*val*val*val));
                    expoHolder(counter,2)  = 16*exp(2*val);
                    sineHolder(counter,2)  = sin(val);
                    cosHolder(counter,2)   = cos(val);
                    xsineHolder(counter,2) = val*sin(val) - 4*cos(val);
                    xcosHolder(counter,2)  = 4*sin(val) + val*cos(val);
                    
                    % with all of our values assigned for the current config of
                    % step->x_position->deriv_order, we can now take an average of
                    % the relative errors for later comparison.
                    holderFourth(deriv_pos,:) = ...
                          [abs((logHolder(counter,1) - logHolder(counter,2)) / logHolder(counter,2))...
                        + abs((expoHolder(counter,1) - expoHolder(counter,2)) / expoHolder(counter,2))...
                        + abs((sineHolder(counter,1) - sineHolder(counter,2)) / sineHolder(counter,2))...
                        + abs((cosHolder(counter,1) - cosHolder(counter,2)) / cosHolder(counter,2))...
                        + abs((xsineHolder(counter,1) - xsineHolder(counter,2)) / xsineHolder(counter,2))...
                        + abs((xcosHolder(counter,1) - xcosHolder(counter,2)) / xcosHolder(counter,2))...
                        / 6,step];                     
            end%switch            
        end%for %order
    end%for %val
end%for %step
disp('finished building the holder matrices')
