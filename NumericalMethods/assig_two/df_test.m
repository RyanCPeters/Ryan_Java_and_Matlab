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
maxVal = 15;
inc_steps = .0001;

%% expected form of derives for order 1-4: --Note that these are all very simple test cases.

% for ln(x)
% first  (y'): 1/x;
% second (y''): -1/(x*x);
% third  (y'''): 2/(x*x*x);
% fourth (y''''): -6/x^4;
natLog = @(x) log(x);
natLog_first = @(x)1/x;
natLog_second = @(x)-1/(x*x);
natLog_third = @(x)2/(x*x*x);
natLog_fourth = @(x)-6/x^4;

% for e^(2*x)
% first  (y'): 2*e^(2*x);
% second (y''): 4*e^(2*x);
% third  (y'''): 8*e^(2*x);
% fourth (y''''): 16*e^(2*x);
expo = @(x) exp(2*x);
expo_first = @(x)2*e^(2*x);
expo_second = @(x)4*e^(2*x);
expo_third = @(x)8*e^(2*x);
expo_fourth = @(x)16*e^(2*x);

% for sin(x)
% first  (y'): cos(x);
% second (y''): -sin(x);
% third  (y'''): -cos(x);
% fourth (y''''): sin(x);
sine = @(x) sin(x);
sine_first = @(x)cos(x);
sine_second = @(x)-sin(x);
sine_third = @(x)-cos(x);
sine_fourth = @(x)sin(x);

% for cos(x)
% first  (y'): -sin(x);
% second (y''): -cos(x);
% third  (y'''): sin(x);
% fourth (y''''): cos(x);
cosine = @(x) cos(x);
cosine_first = @(x)-sin(x);
cosine_second = @(x)-cos(x);
cosine_third = @(x)sin(x);
cosine_fourth = @(x)cos(x);

% for x*sin(x)
% first  (y'): sin(x) + x*cos(x);
% second (y''): 2*cos(x) - x*sin(x);
% third  (y'''): -3*sin(x) - x*cos(x);
% fourth (y''''): x*sin(x) - 4*cos(x);
xsine = @(x) x*sin(x);
xsine_first = @(x)sin(x) + x*cos(x);
xsine_second = @(x)2*cos(x) - x*sin(x);
xsine_third = @(x)-3*sin(x) - x*cos(x);
xsine_fourth = @(x)x*sin(x) - 4*cos(x);

% for x*cos(x)
% first  (y'): cos(x) - x*sin(x);
% second (y''): -2*sin(x) - x*cos(x);
% third  (y'''): x*sin(x) - 3*cos(x);
% fourth (y''''): 4*sin(x) + x*cos(x);
xcosine = @(x) x*cos(x);
xcosine_first = @(x)cos(x) - x*sin(x);
xcosine_second = @(x)-2*sin(x) - x*cos(x);
xcosine_third = @(x)x*sin(x) - 3*cos(x);
xcosine_fourth = @(x)4*sin(x) + x*cos(x);

%% preassigning storage variables so we don't have to do a shitload of
%  matrix concatination during the loops:

%% the first through fourth holders will we eventually store 
%  the averaged difference from our approximation to the assumed true value 
%  according to the simple calculated derivatives above on a per-order of
%  derivative basis.
%
%  Each row in a derivative holder is going to be the average of the 
%  function holders at that specific row.
holderFirst  = zeros((1/inc_steps)*maxVal,3);
holderSecond = zeros((1/inc_steps)*maxVal,3);
holderThird  = zeros((1/inc_steps)*maxVal,3);
holderFourth = zeros((1/inc_steps)*maxVal,3);

% log set
firstLogHolder  = zeros((1/inc_steps)*maxVal,3);
secondLogHolder = zeros((1/inc_steps)*maxVal,3);
thirdLogHolder  = zeros((1/inc_steps)*maxVal,3);
fourthLogHolder = zeros((1/inc_steps)*maxVal,3);

% expo set
firstExpHolder  = zeros((1/inc_steps)*maxVal,3);
secondExpHolder = zeros((1/inc_steps)*maxVal,3);
thirdExpHolder  = zeros((1/inc_steps)*maxVal,3);
fourthExpHolder = zeros((1/inc_steps)*maxVal,3);

% sine set
firstSinHolder  = zeros((1/inc_steps)*maxVal,3);
secondSinHolder = zeros((1/inc_steps)*maxVal,3);
thirdSinHolder  = zeros((1/inc_steps)*maxVal,3);
fourthSinHolder = zeros((1/inc_steps)*maxVal,3);

% cosine set
firstCosHolder  = zeros((1/inc_steps)*maxVal,3);
secondCosHolder = zeros((1/inc_steps)*maxVal,3);
thirdCosHolder  = zeros((1/inc_steps)*maxVal,3);
fourthCosHolder = zeros((1/inc_steps)*maxVal,3);

% xsine set
firstXSinHolder  = zeros((1/inc_steps)*maxVal,3);
secondXSinHolder = zeros((1/inc_steps)*maxVal,3);
thirdXSinHolder  = zeros((1/inc_steps)*maxVal,3);
fourthXSinHolder = zeros((1/inc_steps)*maxVal,3);

% xcos set
firstXCosHolder  = zeros((1/inc_steps)*maxVal,3);
secondXCosHolder = zeros((1/inc_steps)*maxVal,3);
thirdXCosHolder  = zeros((1/inc_steps)*maxVal,3);
fourthXCosHolder = zeros((1/inc_steps)*maxVal,3);

% these values will be used inside the inner most loop as a means to track
% and report progress through the total process. Currently, it's set to 
% print status updates every 10% of the way through the total process.
num_steps = (1/inc_steps)*maxVal;
benchmarkNotice = num_steps / 10;

%% these matrices will hold the averaged differences between the approximate
%  derivative from df and the "true" derivative calculated above.
firstDAvgs = zeros((1/inc_steps)*maxVal,2);
secondDAvgs = zeros((1/inc_steps)*maxVal,2);
thirdDAvgs = zeros((1/inc_steps)*maxVal,2);
fourthDAvgs = zeros((1/inc_steps)*maxVal,2);

% counter will represent the actual index position for the matrices as we
% populate them, this is much easier to think about than trying to derive
% some index value from the combination of step, val, and order. counter is
% updated at the start of each pass through order's for loop.
counter = 0;
%% the following for loops are structured in a sequence of:
%   value_of_x [  step_size (  %evaluate and store stuff at this level% ) ]
for val = 1:maxVal
    
    %% because the variable val only changes a few times relative to the step var
    %  it makes sense to only calculate the function values at the time that val
    %  is updated then reuse those values later on inside of the for step loop.
    firstDs  = [natLog_first(val),  expo_first(val),  sine_first(val),  cosine_first(val),  xsine_first(val),  xcosine_first(val)];
    secondDs = [natLog_second(val), expo_second(val), sine_second(val), cosine_second(val), xsine_second(val), xcosine_second(val)];
    thirdDs  = [natLog_third(val),  expo_third(val),  sine_third(val),  cosine_third(val),  xsine_third(val),  xcosine_third(val)];
    fourthDs = [natLog_fourth(val), expo_fourth(val), sine_fourth(val), cosine_fourth(val), xsine_fourth(val), xcosine_fourth(val)];
    
    for step = inc_steps : inc_steps : 1 
        counter = counter +1;
        % this if statement provides a simple command prompt output with status
        % updates every 10% of the way through the total process. However, it
        % should be noted that this output doesn't work in Octave for some reason.
%        if(mod(counter, benchmarkNotice) == 0)
%            disp(counter/num_steps)
%        end
        % the first column in each matrix is the "true" value, the second column
        % is the approximate value from df, with the third column being the 
        % step value for the current row.  
        
        firstLogHolder(counter,:)   = [firstDs(1),  df(natLog,val,1,step), step];
        firstExpHolder(counter,:)   = [firstDs(2),    df(expo,val,1,step), step];
        firstSinHolder(counter,:)   = [firstDs(3),    df(sine,val,1,step), step];
        firstCosHolder(counter,:)   = [firstDs(4),  df(cosine,val,1,step), step];
        firstXSinHolder(counter,:)  = [firstDs(5),   df(xsine,val,1,step), step];
        firstXCosHolder(counter,:)  = [firstDs(6), df(xcosine,val,1,step), step];
        
        firstDAvgs(counter,:) = [((abs(firstLogHolder(counter,2)) - abs(firstLogHolder(counter,1)))/abs(firstLogHolder(counter,1))...
                                + (abs(firstExpHolder(counter,2)) - abs(firstExpHolder(counter,1)))/abs(firstExpHolder(counter,1))...
                                + (abs(firstSinHolder(counter,2)) - abs(firstSinHolder(counter,1)))/abs(firstSinHolder(counter,1))...
                                + (abs(firstCosHolder(counter,2)) - abs(firstCosHolder(counter,1)))/abs(firstCosHolder(counter,1))...
                                + (abs(firstXSinHolder(counter,2)) - abs(firstXSinHolder(counter,1)))/abs(firstXSinHolder(counter,1))...
                                + (abs(firstXCosHolder(counter,2)) - abs(firstXCosHolder(counter,1)))/abs(firstXCosHolder(counter,1))...
                                )/ 6, step];

        secondLogHolder(counter,:)  = [secondDs(1), df(natLog,val,2,step), step];
        secondExpHolder(counter,:)  = [secondDs(2),   df(expo,val,2,step), step];
        secondSinHolder(counter,:)  = [secondDs(3),   df(sine,val,2,step), step];
        secondCosHolder(counter,:)  = [secondDs(4), df(cosine,val,2,step), step];
        secondXSinHolder(counter,:) = [secondDs(5),  df(xsine,val,2,step), step];
        secondXCosHolder(counter,:) = [secondDs(6),df(xcosine,val,2,step), step];
        
        secondDAvgs(counter,:) = [((abs(secondLogHolder(counter,2)) - abs(secondLogHolder(counter,1)))/abs(secondLogHolder(counter,1))...
                                + (abs(secondExpHolder(counter,2)) - abs(secondExpHolder(counter,1)))/abs(secondExpHolder(counter,1))...
                                + (abs(secondSinHolder(counter,2)) - abs(secondSinHolder(counter,1)))/abs(secondSinHolder(counter,1))...
                                + (abs(secondCosHolder(counter,2)) - abs(secondCosHolder(counter,1)))/abs(secondCosHolder(counter,1))...
                                + (abs(secondXSinHolder(counter,2)) - abs(secondXSinHolder(counter,1)))/abs(secondXSinHolder(counter,1))...
                                + (abs(secondXCosHolder(counter,2)) - abs(secondXCosHolder(counter,1)))/abs(secondXCosHolder(counter,1))...
                                )/ 6, step];


        thirdLogHolder(counter,:)   = [thirdDs(1),  df(natLog,val,3,step), step];
        thirdExpHolder(counter,:)   = [thirdDs(2),    df(expo,val,3,step), step];
        thirdSinHolder(counter,:)   = [thirdDs(3),    df(sine,val,3,step), step];
        thirdCosHolder(counter,:)   = [thirdDs(4),  df(cosine,val,3,step), step];
        thirdXSinHolder(counter,:)  = [thirdDs(5),   df(xsine,val,3,step), step];
        thirdXCosHolder(counter,:)  = [thirdDs(6), df(xcosine,val,3,step), step];
        
        thirdDAvgs(counter,:) = [((abs(thirdLogHolder(counter,2)) - abs(thirdLogHolder(counter,1)))/abs(thirdLogHolder(counter,1))...
                                + (abs(thirdExpHolder(counter,2)) - abs(thirdExpHolder(counter,1)))/abs(thirdExpHolder(counter,1))...
                                + (abs(thirdSinHolder(counter,2)) - abs(thirdSinHolder(counter,1)))/abs(thirdSinHolder(counter,1))...
                                + (abs(thirdCosHolder(counter,2)) - abs(thirdCosHolder(counter,1)))/abs(thirdCosHolder(counter,1))...
                                + (abs(thirdXSinHolder(counter,2)) - abs(thirdXSinHolder(counter,1)))/abs(thirdXSinHolder(counter,1))...
                                + (abs(thirdXCosHolder(counter,2)) - abs(thirdXCosHolder(counter,1)))/abs(thirdXCosHolder(counter,1))...
                                )/ 6, step];


        fourthLogHolder(counter,:)  = [fourthDs(1), df(natLog,val,4,step), step];
        fourthExpHolder(counter,:)  = [fourthDs(2),   df(expo,val,4,step), step];
        fourthSinHolder(counter,:)  = [fourthDs(3),   df(sine,val,4,step), step];
        fourthCosHolder(counter,:)  = [fourthDs(4), df(cosine,val,4,step), step];
        fourthXSinHolder(counter,:) = [fourthDs(5),  df(xsine,val,4,step), step];
        fourthXCosHolder(counter,:) = [fourthDs(6),df(xcosine,val,4,step), step];
        
        fourthDAvgs(counter,:) = [((abs(fourthLogHolder(counter,2)) - abs(fourthLogHolder(counter,1)))/abs(fourthLogHolder(counter,1))...
                                + (abs(fourthExpHolder(counter,2)) - abs(fourthExpHolder(counter,1)))/abs(fourthExpHolder(counter,1))...
                                + (abs(fourthSinHolder(counter,2)) - abs(fourthSinHolder(counter,1)))/abs(fourthSinHolder(counter,1))...
                                + (abs(fourthCosHolder(counter,2)) - abs(fourthCosHolder(counter,1)))/abs(fourthCosHolder(counter,1))...
                                + (abs(fourthXSinHolder(counter,2)) - abs(fourthXSinHolder(counter,1)))/abs(fourthXSinHolder(counter,1))...
                                + (abs(fourthXCosHolder(counter,2)) - abs(fourthXCosHolder(counter,1)))/abs(fourthXCosHolder(counter,1))...
                                )/ 6, step];

    
    end %for %step
end %for %val
disp('finished building the holder matrices')



