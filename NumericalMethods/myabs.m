function myinput = myabs( input_args )
%MYABS Summary of this function goes here
%   Detailed explanation goes here
%   
%   for some reason, I can't seem to get it to accept a matrix because
%   the resulting matrix from sign( some_matrix ) has new dimensions.

myinput = sign( input_args ) * input_args;


