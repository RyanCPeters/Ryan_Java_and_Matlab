function area = integrate( funct, method_type, low_bound, hi_bound,sub_intervals )
%% function integrate(funct, method_type, low_bound, hi_bound, sub_intervals)
%   The integrate function accepts five arguments and returns a scalar. If
%   for some reason your inputs do not work right, this function will return
%   a zero value in the returnable variable.
%
%% inputs: There are 5 inputs that need to be provided, the anonymous 
%          function(funct), a String representing what method of
%          approximation is desired (method_type), your bounds of
%          integration(low_bound, hi_bound), and the number of
%          approximating rectangles, trapezoids or curves you need.
%
%     funct = the anonymouse function representing math function f(x).
%           
%     method_type = a String representation of the method solving type
%                   methods of:
%                       'l' = Left Hand Endpoints Rule
%                       'r' = Right Hand Endpoints Rule
%                       't' = Trapezoid Rule
%                       'm' = Midpoint Rule
%                       's' = Simpson's Rule
%         
%     low_bound = the lower boundary value or limit of integration for
%                 the interval to be calculated for.
%
%     hi_bound  = the upper boundary value or limit of integration.
%
%     sub_intervals = the number of sub intervals for which we should
%                     calculate.
%
%% Outputs:
%         
%     returns the scalar value for the sum of the area over the interval
%     from low_bound to hi_bound calculated on the value for
%     sub_intervals.
%   

%%
% setting up initial condition variables

% h will be the size of a single step.
h = (hi_bound - low_bound)/sub_intervals;

% subs will be a vector containing the bounds for each sub-interval
subs = low_bound : h : hi_bound ;

%%
% the following switch block will set the return variable's value 
% inside of each case statement of the switch block.
switch method_type
    case 'l' % the Left Hand Endpoints Rule
        
        area = sum(h*funct(subs(1:end-1)));
        
    case 'r' % the Right Hand Endpoints Rule
        
        area = sum(h*funct(subs(2:end)));
        
    case 't' % the Trapezoid Rule
        
        area = h/2 * (funct(subs(1)) + funct(subs(end)) + 2*sum(funct(subs(2:end-1))));      
      
    case 'm' % the Midpoint Rule
        
        area = sum(h*(funct(subs(1:2:end-1))+funct(subs(2:2:end))));     
        
    case 's' % Simpson's 
        
        % Simpson's method needs there to be an even number of
        % sub-intervals, which means we need an odd number of delimiting
        % points. ie, sub_intervals needs to be an odd number.
        if(bitget(sub_intervals,1) == 0)
            % quick note: bitget(integer,n) returns the bit state of the
            % n'th bit from 1 to however many bits this number uses. Where
            % n = 1 is the lowest value bit in the series of bits.            
            % the given value was even, so lets bump it up to an odd.
            sub_intervals = sub_intervals + 1;
            
            % since sub_intervals has changed, we need to upadate h. 
            h = (hi_bound - low_bound)/sub_intervals;
        
            % because h has changed, we need to also update the subs vector.
            subs = low_bound : h : hi_bound ;
            
        end % end of if block
        
        
         
        area = ...
            h/3 * ( funct(subs(1)) + funct(subs(end)) ...
            + 2 * sum(funct(subs(2:2:end-2))) ...
            + 4 * sum(funct(subs(1:2:end-1))));
        
    otherwise
        % this is a stub value for the output. It should serve to prevent 
        % null pointer errors and give a value to check for if you
        % anticipate a possible fail condition.
        area = 0;
        
end % end of switch block
    


end % end of function.

