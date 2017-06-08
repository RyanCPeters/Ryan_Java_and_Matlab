% Create an anonymous function based on a Taylor Series for sin(x)

% initialize anonymous function
f = @(x) 0;

for i = 1:8

    % Update anonymous function
    f = @(x) f(x) + (-1)^(i-1) * x .^ (2 * i - 1) / factorial(2 * i - 1);

end

% Plot Taylor polynomial and sin(x).
x = linspace(-2*pi, 2*pi, 101);
plot(x, f(x), x, sin(x))
axis([-7 7 -2 2])
