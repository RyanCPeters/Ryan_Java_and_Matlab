clear y*, clear x*, clear idx*


figure('Name','first derivatives of various functions for values from 1 to 10 and step sizes .0001 to 1','NumberTitle','off')
plot(holderFirst(:,2),holderFirst(:,1),'.r')
grid on
grid minor

figure('Name','second derivatives of various functions for values from 1 to 10 and step sizes .0001 to 1','NumberTitle','off')
plot(holderSecond(:,2),holderSecond(:,1),'.b')
grid on
grid minor

figure('Name','third derivatives of various functions for values from 1 to 10 and step sizes .0001 to 1','NumberTitle','off')
plot(holderThird(:,2),holderThird(:,1),'.g')
grid on
grid minor

figure('Name','fourth derivatives of various functions for values from 1 to 10 and step sizes .0001 to 1','NumberTitle','off')
plot(holderFourth(:,2),holderFourth(:,1),'.k')
grid on
grid minor

figure('Name','Combined graph','NumberTitle','off')
plot(holderFirst(:,2),holderFirst(:,1),'.r',holderSecond(:,2),holderSecond(:,1),'.b',holderThird(:,2),holderThird(:,1),'.g',holderFourth(:,2),holderFourth(:,1),'.k')
grid on
grid minor

figure('Name','just 1, 3, and 4','NumberTitle','off')
plot(holderFirst(:,2),holderFirst(:,1),'.r',holderThird(:,2),holderThird(:,1),'.g',holderFourth(:,2),holderFourth(:,1),'.k')
grid on
grid minor

figure('Name','third derivatives of various functions for values from 1 to 10 and step sizes .0001 to 1 ZOOMED IN','NumberTitle','off')
plot(holderThird(60001:119980,2),holderThird(60001:119980,1),'.g')
grid on
grid minor