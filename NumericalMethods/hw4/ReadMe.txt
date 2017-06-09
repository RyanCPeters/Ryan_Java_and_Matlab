Engr 240
Spring 2017
Programming Assignment 4
Polynomial Interpolation and Cubic Splines

For this assignment we will be creating two .m files, lagrange.m and cub spline.m


                          lagrange.m
1.  lagrange() takes a set of n points and returns the lagrange polynomial
    that fits that set of points.

2.  lagrange() accepts two column vectors as arguments. The first argu-
    ment is the set of x-values, and the second argument is the set of
    y-values. As usual, we do not throw any error messages. The argu-
    ments are assumed to be column vectors of the same length where each
    of the values in the x-vector are distinct.
    
3.  lagrange() returns an anonymous function that is the lagrange polyno-
    mial associated with the set of points.
    
                          cub spline.m
1.  Download cub spline.m from Canvas. The code to create the second
    order coefficients has been supplied.
    
2.  cub spline() takes a set of n points and returns a matrix of coefficients
    for the set of piecewise cubics fit to the data.
    
3.  cub spline() accepts two arguments. The first argument is a column
    vector containing the x-values of the points. The second argument is a
    column vector containing the y-values of the points. It is assumed the
    vectors x and y are column vectors of the same length where none of
    the x-values are repeated.
    
4.  Create coefficient vectors for the 1st and 3rd order coefficients of the
    spline functions. Once those have been built, use them to assemble a
    n x n-1 matrix of coefficients for the spline functions.
