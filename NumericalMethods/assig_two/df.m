%% Author: R.Peters <R.Peters@RYANS-DESKTOP>
%% Created: 2017-05-15

function  returnable = df(func_of_x, x_pos, order_of_deriv, step_size)
% function df(func_of_x, x_pos, order_of_deriv, step_size) %%note that order_of_deriv
%                                                 is an optional parameter, as is step_size
%   
%   This custom function by default produces the first derivative output  
%       for the user provided anonymous function "func_of_x" at the user 
%       provided x-position "pos_x". The third parameter is optional, and 
%       it provides the user with a means to request up to a 4'th
%       derivative output for their given function and position.
%
%
%   inputs:     
%           func_of_x = the anonymous function for which we are seeking a derivative
%
%               x_pos = the specific x position for which we will calculate a derivative
%
%      order_of_deriv = This is an optional parameter, that can be used to select
%                       what order of derivative to return, however this funciton
%                       can only derive up to the 4'th order. If this parameter
%                       is left blank then the function will default to the first
%                       derivative of func_of_x.
%
%   Outputs:
%               This function will return the y value derived from either the 
%               first derivative of the passed function, or that corresponding to
%               the order of derivative as prescribed by the order_of_deriv parameter.
%

% we can implicitly assume that it's best to start with our step size, h, should
% start with the general case best step size assigned.
    h = 0.31210;    
    if(nargin > 2)
        if(nargin > 3 & step_size>0)
            h = step_size;
        end  
    else
        % because we must have fewer than 3 paramters if we have made it into this
        % portion of the if block, we can go ahead and set our order to 1 for the
        % first derivative.
        order_of_deriv = 1;
    end


    switch(order_of_deriv)
            case 2
            % case 2 is where we set returnable equal to the value for the second
            % derivative of the given func_of_x at x_pos.
                returnable = (...
                - func_of_x(x_pos+(2*h)) ...
                + 16*func_of_x(x_pos+h) ...
                - 30*func_of_x(x_pos) ...
                + 16*func_of_x(x_pos-h) ...
                - func_of_x(x_pos-(2*h)))...
                / (12*h*h);
            case 3
            % case 3 is where we set returnable equal to the value for the third
            % derivative of the given func_of_x at x_pos.
                returnable =  (...
                - func_of_x(x_pos+(3*h)) ...
                + 8*func_of_x(x_pos+(2*h)) ...
                - 13*func_of_x(x_pos+h) ...
                + 13*func_of_x(x_pos-h) ...
                - 8*func_of_x(x_pos-(2*h)) ...
                + func_of_x(x_pos-(3*h)))...
                / (8*h*h*h);
            case 4
            % case 4 is where we set returnable equal to the value for the fourth
            % derivative of the given func_of_x at x_pos.
                returnable = (...
                - func_of_x(x_pos+(3*h)) ...
                + 12*func_of_x(x_pos+(2*h)) ...
                - 39*func_of_x(x_pos+h) ...
                + 56*func_of_x(x_pos) ...
                - 39*func_of_x(x_pos-h) ...
                + 12*func_of_x(x_pos-(2*h)) ...
                - func_of_x(x_pos-(3*h)))...
                / (6*h*h*h*h);          
            otherwise
            % the otherwise case is where we set returnable equal to the value for 
            % the first derivative of the given func_of_x at x_pos.
                returnable =  (...
                - func_of_x(x_pos+(2*h)) ...
                + 8*func_of_x(x_pos+h) ...
                - 8*func_of_x(x_pos-h) ...
                + func_of_x(x_pos-(2*h)))...
                / (12*h);
    end

end