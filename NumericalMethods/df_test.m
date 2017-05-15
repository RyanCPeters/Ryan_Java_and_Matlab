clear holder;
holder = zeros( 1/.0001,1);
clear counter;
counter = 0;
for i = .00000000000000000000001:.0001:1
    counter = counter +1;
    guess = (-log(100+(2*i)) + 16*log(100+i) - 30*log(100) + 16*log(100-i) - log(100-(2*i)))/(12*i*i);
    holder(counter) = guess;
end

x = [];
y = [];

for i = 1:10001
    if((abs(holder(i)) - abs(1/100^2)) > abs((1/100^2)-.00001))
        disp(i)
    end
    y = [y,abs(abs(holder(i)) - abs((1/100^2)))];
    x = [x,i];
end