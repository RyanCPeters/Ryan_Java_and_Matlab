%% 
% problem # 2)
% 
% Recall that the initial value problem y' = -2ty with y(0) = 1 has analytical 
% solution : y = e^-t^2. 
% 
% 
% 
% Implement a Runge-Kutta 4 solution to this IVP over the interavl (-3,3) 
% with step size of 0.05. 
% 
% 
% 
% Plot the analytical and numeric solutions.

clear
% step size
h = .05;

% set up intial condition
t = 0:h:3;
y = [1,zeros(1,length(t)-1)];
% interval set up

% setting up the invitial funciton 
% for which we are solving
diffEQ = @(t,y) -2*(t*y);





% setting up the numeric solution.
numeric = zeros(1,length(t));
position = length(t);
for i = 2:length(t)
    
    k1 =  diffEQ(t(i-1),y(i-1));
    
    k2 =  diffEQ(t(i-1)+h/2,y(i-1)+k1*h/2);
    
    k3 =  diffEQ(t(i-1)+h/2, y(i-1) + k2*h/2);
    
    k4 =  diffEQ(t(i-1)+h,y(i-1) + k3*h/2);
    
    y(i) = y(i-1) + (h/6)*(k1 + 2*k2 + 2*k3 + k4);
    
    
    
end % end of the for loop
t = [-1*t(end:-1:2),t];
y = [y(end:-1:2),y];

% setting up the analytic anon function
analytic_func = @(t) exp(-t.^2);
analytic = analytic_func(t)';

% now  lets plot some shit!
figure('Name', 'problem 1 analytic solution in black and numeric solution in red');
hold on, grid on, grid minor;
plot(t,analytic,'.k')
plot(t,y,'or');
% ylim([-.03 1.01])
%% 
% problem #3)
% 
% 
% 
% the lotke-volterra equations can be used to model the dynamics of  a predator-prey 
% system.
% 
% x = # of prey
% 
% y = # of predators
% 
% the values a, b c and d are real parameters determined by the interaction 
% of thetwo species.
% 
% dx/dt = ax - bxy;
% 
% dy/dt = cxy - dy;

clear
a = 2;
b = 3;
c = 1;
d = 1;

x = 1;
y = 1;

% equation for dx/dt, xp being x-prime
xp = @(xi,yi) a*xi - b*xi*yi;

% equation for dy/dt, yp being y-prime
yp = @(yi,yi) c*xi*yi - d*yi;

%% 
% problem #4)
% 
% Implement a Runge-Kutta 2 solution to the predator-prey model with: 
% 
% a = 2;
% 
% b = 3;
% 
% c = d = 1;

% a,b,c, and d are already declared above in problem 3;

% let the initial conditions for the x and y populations be:

% x(0) = 20;
x = 20;

% y(0) = 200;
y = 200;

% choose a step size of .05;
h = .05

% choosing a time interval of 0 to 300;
t = 0:h:300;

% now we do teh for loop with k1, and k2 as the factors of the rk2
% and let f(xn,yn) = f(xp(i-1),yp(i-1));
% for i = 2:length(t)
%     k1y = yp(x(i-1),y(i-1));
%     k1x = xp(x(i-1),y(i-1));
% %     k2y = yp(
% end % end of for loop