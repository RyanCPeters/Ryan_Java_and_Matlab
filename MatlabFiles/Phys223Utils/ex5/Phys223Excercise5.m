clear

%% 
% let the anonymous function 'R' be the equation: 
% 
% (n1^2 * n2^2)/(lamb*(n2^2 - n1^2)) where n1 = 2 for all calculations here.

% this is that anonymous function to calculate the output value
% for a given intensity index an corresponding lambda value.
R = @(n2, lamb) (4*n2^2)/(lamb * (n2^2 - 4));
X = @(n2) (n2^2 - 4)/(4 * n2^2);
% set up vectors containing the R line data for each device type
tvSpecLambs   = 10^(-9)*[692.58, 532.49, 484.50, 462.12];
vernierLambs  = 10^(-9)*[657.00, 485.60, 441.90, 407.00];
oceanOptLambs = 10^(-9)*[656.90, 486.20, 433.90, 409.70];

% the uncertainty for each device type's readings.
tvSpecErr   = 10^(-9) * 0.3;
vernierErr  = 10^(-9) * 3.5;
oceanOptErr = 10^(-9) * 1.2;

% set up point values for each device by entering
% the emision line vectors into our R function.
% Then save that output value and it's input as a coordinate pair
% in the point matricis named for their corresponding device datas.
tvSpecPoints = zeros(4,2);
vernierPoints = zeros(4,2);
oceanOptPoints = zeros(4,2);

for n = 3:6
 tvSpecPoints(n-2,:)   = [X(n), R(n, tvSpecLambs(n-2))*X(n)];
 vernierPoints(n-2,:)  = [X(n), R(n, vernierLambs(n-2))*X(n)];
 oceanOptPoints(n-2,:) = [X(n), R(n, oceanOptLambs(n-2))*X(n)];
 
end % end of the for n = 3:6 loop

%%
% taking the inverse matrix transform of tool and the output of R(n, lambda) at Xn
tool = [tvSpecPoints(:,1), ones(length(tvSpecPoints(:,1)),1)];
tvRSlope = tool\tvSpecPoints(:,2);

tool = [vernierPoints(:,1),ones(length(vernierPoints(:,1)),1)];
vernierRSlope = tool\vernierPoints(:,2);

tool = [oceanOptPoints(:,1),ones(length(oceanOptPoints(:,1)),1)];
oceanRSlope = tool\oceanOptPoints(:,2);


%% 
% creating the anonymous functions that we'll use as line equations for 
% the graph of the R slopes for each meter.
%%
tvLine = slope_int(tvSpecPoints);
minx = min(tvSpecPoints(:,1));
maxx = max(tvSpecPoints(:,1));
tvShort= minx:(maxx-minx)/100:maxx + .1*maxx;
tvinterval = -.01:(maxx-minx)/100:maxx + .1*maxx;
% tvBar = bar(tvSpecPoints(:,1),tvSpecPoints(:,2),'BaseValue',1141704.56187473,...
%     'DisplayName','TvSpec Spectrophotometer',...
%     'FaceAlpha',0.4,...
%     'FaceColor',[0 0 0],...
%     'BarWidth',0.3,...
%     'BarLayout','stacked');

 

vernLine = slope_int(vernierPoints);
minx = min(vernierPoints(:,1));
maxx = max(vernierPoints(:,1));
vernShort= minx:(maxx-minx)/100:maxx + .1*maxx;
verninterval = -.01:(maxx-minx)/100:maxx + .1*maxx;

% vernBar = bar(vernierPoints(:,1),vernierPoints(:,2),'BaseValue',1141704.56187473,...
%     'DisplayName','Vernier Spectrophotometer',...
%     'FaceAlpha',0.4,...
%     'FaceColor',[1 0 0],...
%     'EdgeColor',[1 0 0],...
%     'BarWidth',0.2,...
%     'BarLayout','stacked');

oceanLine = slope_int(oceanOptPoints);
minx = min(oceanOptPoints(:,1));
maxx = max(oceanOptPoints(:,1));
oceanShort = minx:(maxx-minx)/100:maxx + .1*maxx;
oceaninterval = -.01:(maxx-minx)/100:maxx + .1*maxx;
% oceanBar = bar(oceanOptPoints(:,1),oceanOptPoints(:,2),'BaseValue',1141704.56187473,...
%     'DisplayName','Ocean Optics Spectrophotometer',...
%     'FaceAlpha',0.4,...
%     'FaceColor',[0 0 1],...
%     'EdgeColor',[0 0 1],...
%     'BarWidth',0.1,...
%     'BarLayout','stacked');


%% 
% now we graph!!
%%
figure('Name',...
 'line plots for the TV Spec(black), Vernier(red), and Ocean Optics(blue) spectrophotometers');
hold on, grid, grid minor

plot(tvSpecPoints(:,1),tvSpecPoints(:,2),'*k',...
     vernierPoints(:,1),vernierPoints(:,2),'sr',...
     oceanOptPoints(:,1),oceanOptPoints(:,2),'db');

plot(   tvShort,tvLine(tvShort),'k',...
        vernShort, vernLine(vernShort),'r',...
        oceanShort,oceanLine(oceanShort),'b');


    
        
figure('Name', 'this is just a reference graph to show the scale of the values relative to zero.')
hold on, grid, grid minor
tvBar.BaseValue = 0;
vernBar.BaseValue = 0;
oceanBar.BaseValue = 0;
plot(tvSpecPoints(:,1),tvSpecPoints(:,2),'*k',...
     vernierPoints(:,1),vernierPoints(:,2),'sr',...
     oceanOptPoints(:,1),oceanOptPoints(:,2),'db');

plot(   tvinterval,tvLine(tvinterval),'k',...
        verninterval, vernLine(verninterval),'r',...
        oceaninterval,oceanLine(oceaninterval),'b');