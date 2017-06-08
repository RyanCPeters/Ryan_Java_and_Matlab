% Numeric solution to x'' = -x

% initialize stuff
h = 0.1;
x = 0;
v = pi;
t = 0:h:(2*pi);

% perform rk2
for i = 2:length(t)
    % compute first order slopes
    kx1 = v(i-1);
    kv1 = -x(i-1);
    
    % compute 2nd order slopes
    kx2 = v(i-1) + h*kv1;
    kv2 = -(x(i-1) + h*kx1);   
    
    % update solutions.
    x(i) = x(i-1) + (h/2)*(kx1 + kx2);
    v(i) = v(i-1) + (h/2)*(kv1 + kv2);
end

