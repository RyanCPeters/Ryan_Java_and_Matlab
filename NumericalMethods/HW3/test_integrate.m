%% 
% the clear statement is just a safety practice used to ensure any possible 
% buggy behavior isn't the result of any confounding artifacts left from previous 
% code.

clear
%% 
% I'm setting up some basic control variables so if I want to change a value 
% somewhere I can do it in a single location instead of having to hunt down a 
% dozen different places where that value should be used.
%%
% n will be the number of sub-intervals in our func calls
n = 100; 
plotlen = n;
range = 1:plotlen;
approximations = ones(length(n),3);

% here are some bound values 
low = 1;
high = 2*pi;
f2_low_nonzero = (low == 0)*.1;
f4_nonborring = ((high - low)<30)*30;
% implicit in this list of bounds is that constants like pi and eps 
% are already declared in matlab, and are thus a part of this list of bounds.
breakShit = 2 * 10^6;

    
% function set 1
f1a = @(x) x.^2;
f1b = @(x) x.^2 + 1;
f1c = @(x) x.^2 -1;

% function set 2s
f2a = @(x) log(x);
f2b = @(x) log(x) + 1;
f2c = @(x) log(x) - 1;

% function set 3
f3a = @(x) sin(x);
f3b = @(x) sin(x) + 1;
f3c = @(x) sin(x) - 1;

% function set 4
f4a = @(x) exp(x);
f4b = @(x) exp(x) + 1;
f4c = @(x) expm1(x);% expm1(x) is a matlab function that handles exp(x)-1 without roundoff error.

% f1 func set sols
f1a_sol = integral(f1a,low,high);
f1b_sol = integral(f1b,low,high);
f1c_sol = integral(f1c,low,high);
% f2 func set sols
f2a_sol = integral(f2a,(low + f2_low_nonzero),high);
f2b_sol = integral(f2b,(low + f2_low_nonzero),high);
f2c_sol = integral(f2c,(low + f2_low_nonzero),high);
% f3 func set sols
f3a_sol = integral(f3a,low,high);
f3b_sol = integral(f3b,low,high);
f3c_sol = integral(f3c,low,high);
% f4 func set sols
f4a_sol = integral(f4a,low,high+f4_nonborring);
f4b_sol = integral(f4b,low,high+f4_nonborring);
f4c_sol = integral(f4c,low,high+f4_nonborring);

%% 
% First up, the function approximations by left, right, mid, trap, and simpson's 
% methods plotted on interval of:
% 
% low to high;(see variable assignment for low and high above)
%%
% these first few lines are set up for our interval and preassigning the vectors to be used later.
maxy = 0;
% this matrix can be used to plot a flat line representing the actual area
% of the function for interval from low to high that serves as a visual reference for how the custom function
% integrate is performing.
sols1 = [ones(length(n),1)*f1a_sol,ones(length(n),1)*f1b_sol,ones(length(n),1)*f1c_sol];


fig1 = figure('Position',[10000,10000,925,440],...
    'Name', 'function set 1 (x.^2); all methods; low to high; y-axis is approximation, x-axis is number of iteratons');
grid on, grid minor, hold on
% % set 1 actual functions
% plot(range,sols1(:,1),'*k')
% plot(range,sols1(:,2),'*r')
% plot(range,sols1(:,3),'*b') 

% set 1 integral approximations
% now we set up for and plot with left hand endpoints method
for i = 1:n
     approximations(i,1) = abs(integrate(f1a,'l',low,high,i) - f1a_sol)/abs(f1a_sol);
     approximations(i,2) = abs(integrate(f1b,'l',low,high,i) - f1b_sol)/abs(f1b_sol);
     approximations(i,3) = abs(integrate(f1c,'l',low,high,i) - f1c_sol)/abs(f1c_sol);
     
end

maxy = max(max(max(approximations)),maxy);

plot(range,approximations(range(1):range(end),1),'.k')
plot(range,approximations(range(1):range(end),2),'.r')
plot(range,approximations(range(1):range(end),3),'.b')

% now we set up for and plot with right hand endpoint method
for i = 1:n
     approximations(i,1) = abs(integrate(f1a,'r',low,high,i) - f1a_sol)/abs(f1a_sol);
     approximations(i,2) = abs(integrate(f1b,'r',low,high,i) - f1b_sol)/abs(f1b_sol);
     approximations(i,3) = abs(integrate(f1c,'r',low,high,i) - f1c_sol)/abs(f1c_sol);
end

maxy = max(max(max(approximations)),maxy);

plot(range,approximations(range(1):range(end),1),'+k')
plot(range,approximations(range(1):range(end),2),'+r')
plot(range,approximations(range(1):range(end),3),'+b')

% now we set up for and plot with midpoint method
for i = 1:n
     approximations(i,1) = abs(integrate(f1a,'m',low,high,i) - f1a_sol)/abs(f1a_sol);
     approximations(i,2) = abs(integrate(f1b,'m',low,high,i) - f1b_sol)/abs(f1b_sol);
     approximations(i,3) = abs(integrate(f1c,'m',low,high,i) - f1c_sol)/abs(f1c_sol);
     
end

maxy = max(max(max(approximations)),maxy);

plot(range,approximations(range(1):range(end),1),'--k')
plot(range,approximations(range(1):range(end),2),'--r')
plot(range,approximations(range(1):range(end),3),'--b')

% now we set up for and plot with trapezoid method
for i = 1:n
     approximations(i,1) = abs(integrate(f1a,'t',low,high,i) - f1a_sol)/abs(f1a_sol);
     approximations(i,2) = abs(integrate(f1b,'t',low,high,i) - f1b_sol)/abs(f1b_sol);
     approximations(i,3) = abs(integrate(f1c,'t',low,high,i) - f1c_sol)/abs(f1c_sol);
     
     end

maxy = max(max(max(approximations)),maxy);

plot(range,approximations(range(1):range(end),1),':k')
plot(range,approximations(range(1):range(end),2),':r')
plot(range,approximations(range(1):range(end),3),':b')

% now we set up for and plot with simpson's method
for i = 1:n
     approximations(i,1) = abs(integrate(f1a,'s',low,high,i) - f1a_sol)/abs(f1a_sol);
     approximations(i,2) = abs(integrate(f1b,'s',low,high,i) - f1b_sol)/abs(f1b_sol);
     approximations(i,3) = abs(integrate(f1c,'s',low,high,i) - f1c_sol)/abs(f1c_sol);
     
end

maxy = max(max(max(approximations)),maxy);

plot(range,approximations(range(1):range(end),1),'ok')
plot(range,approximations(range(1):range(end),2),'or')
plot(range,approximations(range(1):range(end),3),'ob')

xlim([-1.3 38.5])
ylim([-0.03 maxy + maxy * .05])


%%

% Begin function set 2

% as was the case with the above set, this matrix can be used to plot a flat line representing the actual area
% of the function for interval from low to high that serves as a visual reference for how the custom function
% integrate is performing.
sols2 = [ones(length(n),1)*f2a_sol,ones(length(n),1)*f2b_sol,ones(length(n),1)*f2c_sol];
approximations = ones(length(n),3);

fig2 = figure('Position',[10000,10000,925,440],...
    'Name', 'function set 2 (log(x)); all methods; low to high; y-axis is approximation, x-axis is number of iteratons');
grid on, grid minor, hold on
% % set 1 actual functions
% plot(range,sols2(:,1),'k')
% plot(range,sols2(:,2),'r')
% plot(range,sols2(:,3),'b') 

% now we set up for and plot with left hand endpoints method
for i = 1:n
     approximations(i,1) = abs(integrate(f2a,'l',(low + f2_low_nonzero),high,i) - f2a_sol)/abs(f2a_sol);
     approximations(i,2) = abs(integrate(f2b,'l',(low + f2_low_nonzero),high,i) - f2b_sol)/abs(f2b_sol);
     approximations(i,3) = abs(integrate(f2c,'l',(low + f2_low_nonzero),high,i) - f2c_sol)/abs(f2c_sol);
     
end

maxy = max(max(max(approximations)),maxy);

plot(range,approximations(range(1):range(end),1),'.k')
plot(range,approximations(range(1):range(end),2),'.r')
plot(range,approximations(range(1):range(end),3),'.b')

% now we set up for and plot with right hand endpoint method
for i = 1:n
     approximations(i,1) = abs(integrate(f2a,'r',(low + f2_low_nonzero),high,i) - f2a_sol)/abs(f2a_sol);
     approximations(i,2) = abs(integrate(f2b,'r',(low + f2_low_nonzero),high,i) - f2b_sol)/abs(f2b_sol);
     approximations(i,3) = abs(integrate(f2c,'r',(low + f2_low_nonzero),high,i) - f2c_sol)/abs(f2c_sol);
     
end

maxy = max(max(max(approximations)),maxy);

plot(range,approximations(range(1):range(end),1),'+k')
plot(range,approximations(range(1):range(end),2),'+r')
plot(range,approximations(range(1):range(end),3),'+b')

% now we set up for and plot with midpoint method
for i = 1:n
     approximations(i,1) = abs(integrate(f2a,'m',(low + f2_low_nonzero),high,i) - f2a_sol)/abs(f2a_sol);
     approximations(i,2) = abs(integrate(f2b,'m',(low + f2_low_nonzero),high,i) - f2b_sol)/abs(f2b_sol);
     approximations(i,3) = abs(integrate(f2c,'m',(low + f2_low_nonzero),high,i) - f2c_sol)/abs(f2c_sol);
     
end

maxy = max(max(max(approximations)),maxy);

plot(range,approximations(range(1):range(end),1),'--k')
plot(range,approximations(range(1):range(end),2),'--r')
plot(range,approximations(range(1):range(end),3),'--b')

% now we set up for and plot with trapezoid method
for i = 1:n
     approximations(i,1) = abs(integrate(f2a,'t',(low + f2_low_nonzero),high,i) - f2a_sol)/abs(f2a_sol);
     approximations(i,2) = abs(integrate(f2b,'t',(low + f2_low_nonzero),high,i) - f2b_sol)/abs(f2b_sol);
     approximations(i,3) = abs(integrate(f2c,'t',(low + f2_low_nonzero),high,i) - f2c_sol)/abs(f2c_sol);
     
end

maxy = max(max(max(approximations)),maxy);

plot(range,approximations(range(1):range(end),1),':k')
plot(range,approximations(range(1):range(end),2),':r')
plot(range,approximations(range(1):range(end),3),':b')

% now we set up for and plot with simpson's method
for i = 1:n
     approximations(i,1) = abs(integrate(f2a,'s',(low + f2_low_nonzero),high,i) - f2a_sol)/abs(f2a_sol);
     approximations(i,2) = abs(integrate(f2b,'s',(low + f2_low_nonzero),high,i) - f2b_sol)/abs(f2b_sol);
     approximations(i,3) = abs(integrate(f2c,'s',(low + f2_low_nonzero),high,i) - f2c_sol)/abs(f2c_sol);
     
end

maxy = max(max(max(approximations)),maxy);

plot(range,approximations(range(1):range(end),1),'ok')
plot(range,approximations(range(1):range(end),2),'or')
plot(range,approximations(range(1):range(end),3),'ob')

maxy = max(max(approximations))+.05*max(max(approximations));

xlim([-1.3 38.5])
ylim([-0.03 maxy + maxy * .05])



%%
% Begin function set 3

% as was the case with the above sets, this matrix can be used to plot a flat line representing the actual area
% of the function for interval from low to high that serves as a visual reference for how the custom function
% integrate is performing.
sols3 = [ones(length(n),1)*f3a_sol,ones(length(n),1)*f3b_sol,ones(length(n),1)*f3c_sol];
approximations = ones(length(n),3);

fig3 = figure('Position',[10000,10000,925,440],...
    'Name', 'function set 3 (sin(x)); all methods; low to high; y-axis is approximation, x-axis is number of iteratons');
grid on, grid minor, hold on
% % set 1 actual functions
% plot(range,sols3(:,1),'k')
% plot(range,sols3(:,2),'r')
% plot(range,sols3(:,3),'b') 

% now we set up for and plot with left hand endpoints method
for i = 1:n
     approximations(i,1) = abs(integrate(f3a,'l',low,high,i) - f3a_sol)/abs(f3a_sol);
     approximations(i,2) = abs(integrate(f3b,'l',low,high,i) - f3b_sol)/abs(f3b_sol);
     approximations(i,3) = abs(integrate(f3c,'l',low,high,i) - f3c_sol)/abs(f3c_sol);
     
end

maxy = max(max(max(approximations)),maxy);

plot(range,approximations(range(1):range(end),1),'.k')
plot(range,approximations(range(1):range(end),2),'.r')
plot(range,approximations(range(1):range(end),3),'.b')

% now we set up for and plot with right hand endpoint method
for i = 1:n
     approximations(i,1) = abs(integrate(f3a,'r',low,high,i) - f3a_sol)/abs(f3a_sol);
     approximations(i,2) = abs(integrate(f3b,'r',low,high,i) - f3b_sol)/abs(f3b_sol);
     approximations(i,3) = abs(integrate(f3c,'r',low,high,i) - f3c_sol)/abs(f3c_sol);
     
end

maxy = max(max(max(approximations)),maxy);

plot(range,approximations(range(1):range(end),1),'+k')
plot(range,approximations(range(1):range(end),2),'+r')
plot(range,approximations(range(1):range(end),3),'+b')

% now we set up for and plot with midpoint method
for i = 1:n
     approximations(i,1) = abs(integrate(f3a,'m',low,high,i) - f3a_sol)/abs(f3a_sol);
     approximations(i,2) = abs(integrate(f3b,'m',low,high,i) - f3b_sol)/abs(f3b_sol);
     approximations(i,3) = abs(integrate(f3c,'m',low,high,i) - f3c_sol)/abs(f3c_sol);
     
end

maxy = max(max(max(approximations)),maxy);

plot(range,approximations(range(1):range(end),1),'--k')
plot(range,approximations(range(1):range(end),2),'--r')
plot(range,approximations(range(1):range(end),3),'--b')

% now we set up for and plot with trapezoid method
for i = 1:n
     approximations(i,1) = abs(integrate(f3a,'t',low,high,i) - f3a_sol)/abs(f3a_sol);
     approximations(i,2) = abs(integrate(f3b,'t',low,high,i) - f3b_sol)/abs(f3b_sol);
     approximations(i,3) = abs(integrate(f3c,'t',low,high,i) - f3c_sol)/abs(f3c_sol);
     
end

maxy = max(max(max(approximations)),maxy);

plot(range,approximations(range(1):range(end),1),':k')
plot(range,approximations(range(1):range(end),2),':r')
plot(range,approximations(range(1):range(end),3),':b')

% now we set up for and plot with simpson's method
for i = 1:n
     approximations(i,1) = abs(integrate(f3a,'s',low,high,i) - f3a_sol)/abs(f3a_sol);
     approximations(i,2) = abs(integrate(f3b,'s',low,high,i) - f3b_sol)/abs(f3b_sol);
     approximations(i,3) = abs(integrate(f3c,'s',low,high,i) - f3c_sol)/abs(f3c_sol);
     
end

maxy = max(max(max(approximations)),maxy);

plot(range,approximations(range(1):range(end),1),'ok')
plot(range,approximations(range(1):range(end),2),'or')
plot(range,approximations(range(1):range(end),3),'ob')

maxy = max(max(approximations))+.05*max(max(approximations));

xlim([-1.3 38.5])
ylim([-0.03 maxy + maxy * .05])



%%

% Begin function set 4

% as was the case with the above sets, this matrix can be used to plot a flat line representing the actual area
% of the function for interval from low to high that serves as a visual reference for how the custom function
% integrate is performing.
sols4 = [ones(length(n),1)*f4a_sol,ones(length(n),1)*f4b_sol,ones(length(n),1)*f4c_sol];
approximations = ones(length(n),3);

fig4 = figure('Position',[10000,10000,925,440],...
    'Name', 'function set 4 (exp(x)); all methods; low to high; y-axis is approximation, x-axis is number of iteratons');
grid on, grid minor, hold on
% % set 4 actual functions
% plot(range,sols4(:,1),'k')
% plot(range,sols4(:,2),'r')
% plot(range,sols4(:,3),'b') 
% now we set up for and plot with left hand endpoints method
for i = 1:n
     approximations(i,1) = abs(integrate(f4a,'l',low,high + f4_nonborring,i) - f4a_sol)/abs(f4a_sol);
     approximations(i,2) = abs(integrate(f4b,'l',low,high + f4_nonborring,i) - f4b_sol)/abs(f4b_sol);
     approximations(i,3) = abs(integrate(f4c,'l',low,high + f4_nonborring,i) - f4c_sol)/abs(f4c_sol);
     
end

maxy = max(max(max(approximations)),maxy);

plot(range,approximations(range(1):range(end),1),'.k')
plot(range,approximations(range(1):range(end),2),'.r')
plot(range,approximations(range(1):range(end),3),'.b')

% now we set up for and plot with right hand endpoint method
for i = 1:n
     approximations(i,1) = abs(integrate(f4a,'r',low,high + f4_nonborring,i) - f4a_sol)/abs(f4a_sol);
     approximations(i,2) = abs(integrate(f4b,'r',low,high + f4_nonborring,i) - f4b_sol)/abs(f4b_sol);
     approximations(i,3) = abs(integrate(f4c,'r',low,high + f4_nonborring,i) - f4c_sol)/abs(f4c_sol);
     
end

maxy = max(max(max(approximations)),maxy);

plot(range,approximations(range(1):range(end),1),'+k')
plot(range,approximations(range(1):range(end),2),'+r')
plot(range,approximations(range(1):range(end),3),'+b')

% now we set up for and plot with midpoint method
for i = 1:n
     approximations(i,1) = abs(integrate(f4a,'m',low,high + f4_nonborring,i) - f4a_sol)/abs(f4a_sol);
     approximations(i,2) = abs(integrate(f4b,'m',low,high + f4_nonborring,i) - f4b_sol)/abs(f4b_sol);
     approximations(i,3) = abs(integrate(f4c,'m',low,high + f4_nonborring,i) - f4c_sol)/abs(f4c_sol);
     
end

maxy = max(max(max(approximations)),maxy);

plot(range,approximations(range(1):range(end),1),'--k')
plot(range,approximations(range(1):range(end),2),'--r')
plot(range,approximations(range(1):range(end),3),'--b')

% now we set up for and plot with trapezoid method
for i = 1:n
     approximations(i,1) = abs(integrate(f4a,'t',low,high + f4_nonborring,i) - f4a_sol)/abs(f4a_sol);
     approximations(i,2) = abs(integrate(f4b,'t',low,high + f4_nonborring,i) - f4b_sol)/abs(f4b_sol);
     approximations(i,3) = abs(integrate(f4c,'t',low,high + f4_nonborring,i) - f4c_sol)/abs(f4c_sol);
     
end

maxy = max(max(max(approximations)),maxy);

plot(range,approximations(range(1):range(end),1),':k')
plot(range,approximations(range(1):range(end),2),':r')
plot(range,approximations(range(1):range(end),3),':b')

% now we set up for and plot with simpson's method
for i = 1:n
     approximations(i,1) = abs(integrate(f4a,'s',low,high + f4_nonborring,i) - f4a_sol)/abs(f4a_sol);
     approximations(i,2) = abs(integrate(f4b,'s',low,high + f4_nonborring,i) - f4b_sol)/abs(f4b_sol);
     approximations(i,3) = abs(integrate(f4c,'s',low,high + f4_nonborring,i) - f4c_sol)/abs(f4c_sol);
     
end

maxy = max(max(max(approximations)),maxy);

plot(range,approximations(range(1):range(end),1),'ok')
plot(range,approximations(range(1):range(end),2),'or')
plot(range,approximations(range(1):range(end),3),'ob')

maxy = max(max(max(approximations)),maxy);

xlim([-1.3 38.5])
ylim([-0.03 maxy + maxy * .05])


%% 
% 
%%
box = msgbox(...
    ['for chosen interval from low = ',num2str(low),' to high = ',num2str(high),newline,...
    'left hand endpoints = "." ', newline,...
    'right hand endpoints = "+"',newline,...
    'midpoint = "--"',newline,...
    'trapezoid = ":"',newline,...
    'simpons rule = "o"'],'This is the legend for figures 1 through 4');

%% 
% Now to position each figure so it's easily visible

movegui(fig1,'northwest');
movegui(fig2,'northeast');
movegui(fig3,'southwest');
movegui(fig4,'southeast');
movegui(box,'center');