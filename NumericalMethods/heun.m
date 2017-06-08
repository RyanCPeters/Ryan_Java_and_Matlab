% Script to demonstrate Heun's Method

% Use y' = 0.2y, y(0) = 2.

% initialize stuff
h = 0.1;
t = 0:h:20;

% Euler's Method
ye = 2;
for i = 2:length(t)
    ye(i) = ye(i-1) * (1 + 0.2*h);
end


%%% Incorrect Implementation. %%%
%%% Either the slopes shouldn't be of the form h * f(t,y) 
%%% or the update shouldn't depend on h.
% Heun's Method
% yh = 2;
% for i = 2:length(t)
%     % Computing slopes
%     k1 = 0.2 * h * yh(i-1);
%     k2 = 0.2 * h * (yh(i-1) + h*k1);
%     % update yh
%     yh(i) = yh(i-1) + (h/2) * (k1 + k2);
% end

% %%% Correct implementation where the slopes have the form k = h*f
% yh = 2;
% for i = 2:length(t)
%     % Computing slopes
%     k1 = 0.2 * h * yh(i-1);
%     k2 = 0.2 * h * (yh(i-1) + k1);
%     % update yh
%     % Since the slopes depend on h, we don't need
%     % h in the update.
%     yh(i) = yh(i-1) + (1/2) * (k1 + k2);
% end

%%% Correct implementation where the slopes aren't multiplied by h.
yh = 2;
for i = 2:length(t)
   % Computing slope
   k1 = 0.2 * yh(i-1);
   k2 = 0.2 *(yh(i-1) + h*k1);
   % update yh
   % Now we need to include h in the update.
   yh(i) = yh(i-1) + (h/2) * (k1 + k2);
end

% Create analytical solution
f = @(x) 2 * exp(0.2 * x);

% Plot the analytical and numeric solutions.
figure('Name', 'Eulers method in blue, Heuns in red, Analytic in black') 
plot(t, ye, 'ob', t, yh, 'sr', t, f(t), '.k'),grid on, grid minor



