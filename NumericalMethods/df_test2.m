clear y*, clear x*


clear under, clear over, clear both
under = holderFirst <= 5*10^(-10);
over  = holderFirst > 0;
both  = find( holderFirst > 0 & holderFirst <= 5*10^(-10));
y1    = holderFirst(under & over);
x1    = 1:size(y1);

% msgbox(num2str([size(x1),size(y1)]),'completed (x1,y1)');

%clear under, clear over, %clear both
under = holderSecond(:,1) <= 5*10^(-10);
over  = holderSecond(:,1) > 0;
both  = find( holderSecond > 0 & holderSecond <= 5*10^(-10));
y2    = holderSecond(under & over,1);
x2    = 1:size(y2);

% msgbox(num2str([size(x2),size(y2)]),'completed (x2,y2)');

% clear under, clear over,% clear both
under = holderThird(:,1) <= 5*10^(-10);
over  = holderThird(:,1) > 0;
y3    = holderThird(under & over,1);
x3    = 1:size(y3);

% msgbox(num2str([size(x3),size(y3)]),'completed (x3,y3)');

% clear under, clear over, %clear both
under = holderFourth(:,1) <= 5*10^(-10);
over  = holderFourth(:,1) > 0;
y4    = holderFourth(under & over,1);
x4    = 1:size(y4);

% msgbox(num2str([size(x4),size(y4)]),'completed (x4,y4)');

figure('Name','find best step','NumberTitle','off')
hold on
grid on
grid minor

plot(holderFirst,'r')
% plot(holderSecond.*.5,'b')
plot(holderThird,'g')
plot(holderFourth,'k')
% plot(x1,y1,'r')
% plot(x2,y2,'g')
% plot(x3,y3,'b')
% plot(x4,y4,'k')