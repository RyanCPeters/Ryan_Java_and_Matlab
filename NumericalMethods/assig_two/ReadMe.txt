Engineering 240 
Spring 2017 
Programming Assignment 2 
Newton-Raphson/Secant Method Numeric Diﬀerentiation

For this assignment we will be writing the following MATLAB functions: froot.m and df.m

-froot()
--The function froot() will implement the Newton-Rahpson Method or Secant Method depending on the arguments passed.

--The ﬁrst argument will be the anonymous function to ﬁnd the root of. 
--The second argument will be the initial guess. 
--If only two arguments are passed, froot() will use a ﬁrst order derivative
  approximation of the anonymous function for f(x). We will use the function df() to estimate the derivative of f(x).
--If three arguments are passed, and the third argument is an anonymous function, then froot() will use the Newton-Raphson method to ﬁnd     the root of f(x). 
--The third argument will be the anonymous function for f (x).
--The function froot() needs to satisfy the following guidlines. 
---The algorithm performs no more than 1000 iterations. 
---The algorithm breaks the loop if the current guess for the root is within 10 signiﬁcant ﬁgures of the previous guess.
---If the derivative of the current guess is 0, the algorithm needs to gen-erate a new guess.
---The function froot() needs to call the function df() when performing a Secant Method.

-df()
--The function df() estimates the derivative of a function f(x) at a point. 
--The function df() will accept two or three arguments.
---If only two arguments are passed, df() defaults to the ﬁrst derivative of f(x). The ﬁrst argument is the anonymous function to  
   diﬀerentiate, the second argument is the point f(x) is diﬀerentiated at.
---If three arguments are passed, the third argument is the order of the derivative of f(x). 
--df() should be able to compute 1st, 2nd, 3rd, and 4th derivatives. Use the formulas from page 527 of our text.
