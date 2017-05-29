Engr 240 
Spring 2017 
Programming Assignment 3 
Numeric Integration and Linear Regression

-For this assignment we will be creating two .m ﬁles linreg.m and integrate.m 

-linreg.m
--1. The function linreg() constructs an anonymous function that is the line of best ﬁt for a data set.

--2. If one argument is passed, linreg() assumes it to be an n × 2 matrix where the ﬁrst column represents 
     the independent variable and the second column represents the dependent variable.
     
--3. If two arguments are passed, linreg() assumes the ﬁrst parameter is a vector representing the independent variable,
     and the second paramters represents the dependent variable. It also assumes the vectors are of the same length.
     
--4. The function linreg() returns an anonymous function that is the line of best ﬁt for the data.

--5. Use the formulas (14.15) and (14.16) on page 338 in our textbook.

--Sample function calls.
--->> f = l i n r e g ( data ) 
--->> f = l i n r e g (x , y)


-integrate.m
--1. The function integrate() approximates the deﬁnite integral of f(x) from low_bound a to high_bound b.

--2. The function integrate() accepts ﬁve arguments and returns a scalar.

--3. Integrate should not use any loops. The sum() function does the main work in integrate().

--4. The ﬁrst argument is the anonymous function representing f(x).

--5. The second argument is a string telling integrate which method of ap-proximation to use. The following table indicates which string is to be used for each approximation.
---‘l’ Left Hand Endpoints 
---‘r’ Right Hand Enpoints 
---‘t’ Trapezoid Rule 
---‘m’ Midpoint Rule 
---‘s’ Simpson’s Rule

--6. The next two arguments are the limits of integration.
--7. The ﬁfth argument tells integrate() how many approximating curves to use.
--Sample function calls.
--->> integrate ( f , ‘m’ ,1 ,2 ,20) 
--->> area = integrate ( f , ‘ s ’ , 3 , 8 . 4 , 8 ) ;
