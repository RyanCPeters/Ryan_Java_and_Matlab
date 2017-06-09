%% 
% clear out old data here if you are concerned with possible errors comming 
% up.

clear
%% 
% set up the conditions you want for your x and y variables, then assign 
% values to those variables.
%%
% defining some anonymous functions that should give a fairly
% oscillatory graph that remains continuous across negative and positive
% regions of an interval.
f = @(x) sin(2*x)./cos(x);
g = @(x) sin(x)./x;
p = @(x) f(x) .* g(x);


% defining the bounds for x then the number of points to pick between
% those bounds;
xBounds = [-100 100];
numPoints = 13; % change this number to however many points you want.
grangePts = (abs(xBounds(1)-xBounds(2))/numPoints);


% these next couple lines of code aren't used in generating the 
% lagrange polynomial for the approximating curve. However, they
% are used when we go to plot that curve.
step = .001;
interval = xBounds(1):step:xBounds(2);

myX = xBounds(1):grangePts:xBounds(2);
myYf = f(myX);
myYg = g(myX);
myYp = p(myX);


%% 
%