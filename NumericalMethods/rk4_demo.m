% Numerical solution to the following system using RK4.
% This IVP has no known significance and is intended for 
% illustrative purposes.
%
% x' = t - xy
% y' = tx^2
% x(0) = 1
% y(0) = 0

% close any open figures
close

% Initialize stuff.
x = 1;
y = 0;
h = 0.01;
t = 0:h:4;

% Make anonymous functions for DEs
% fx, fy are always functions of t, x, y
% so script can be modified for different DEs.
fx = @(t, x, y) t - x*y;
fy = @(t, x, y) t * sqrt(x); 

% Perform RK4
for i = 2:length(t)
    % Compute 1st order slopes
    kx1 = fx(t(i-1), x(i-1), y(i-1));
    ky1 = fy(t(i-1), x(i-1), y(i-1));
    
    % Compute 2nd order slopes
    kx2 = fx(t(i-1) + h/2, x(i-1) + (h/2)*kx1, y(i-1) + (h/2)*ky1);
    ky2 = fy(t(i-1) + h/2, x(i-1) + (h/2)*kx1, y(i-1) + (h/2)*ky1);
    
    % Compute 3rd order slopes
    kx3 = fx(t(i-1) + h/2, x(i-1) + (h/2)*kx2, y(i-1) + (h/2)*ky2);
    ky3 = fy(t(i-1) + h/2, x(i-1) + (h/2)*kx2, y(i-1) + (h/2)*ky2);
    
    % Compute 4th order slopes
    kx4 = fx(t(i-1) + h, x(i-1) + h*kx3, y(i-1) + h*ky3);
    ky4 = fy(t(i-1) + h, x(i-1) + h*kx3, y(i-1) + h*ky3);
    
    % Update solutions
    x(i) = x(i-1) + (h/6) * (kx1 + 2*kx2 + 2*kx3 + kx4);
    y(i) = y(i-1) + (h/6) * (ky1 + 2*ky2 + 2*ky3 + ky4);
end

% Plot the solutions x and y vs. t
figure('Name', 'x (blue) and y (red) vs. t'),plot( t, x, 'b', t, y, 'r'),grid on, grid minor


% Plot y vs. x
figure('Name', 'x vs. y'), plot(x, y),grid on, grid minor