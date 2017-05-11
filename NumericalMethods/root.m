function r = root( num, n )
%ROOT Summary of this function goes here
%   Detailed explanation goes here
x = num/n;
r = x/2 + num/(2*x);
format long
for i = 1:100;
    if(abs(r- num^(1/n)) < .0000000001);
        r = [r,i];
        return;
    end
    x = r;
    r = x/2 + num/(2*x);
end
r = [r,i];
end

