## Copyright (C) 2017 R.Peters
## 
## This program is free software; you can redistribute it and/or modify it
## under the terms of the GNU General Public License as published by
## the Free Software Foundation; either version 3 of the License, or
## (at your option) any later version.
## 
## This program is distributed in the hope that it will be useful,
## but WITHOUT ANY WARRANTY; without even the implied warranty of
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
## GNU General Public License for more details.
## 
## You should have received a copy of the GNU General Public License
## along with this program.  If not, see <http://www.gnu.org/licenses/>.

## -*- texinfo -*- 
## @deftypefn {Function File} {@var{retval} =} man_roots (@var{input1}, @var{input2})
##
## @seealso{}
## @end deftypefn

## Author: R.Peters <R.Peters@RYANS-DESKTOP>
## Created: 2017-04-14

function r = man_roots (man_num, man_n)
    % r = nroot(number, n): 
    % 
    % input: num is the value who's n'th root is desired
    % input: n is the order of the root to which we will take num
    % 
    % output: the n'th root of num as a single double value
    num = man_num;
    n = man_n;

    if num > n;
        x = num/n;
    else ;
        x = n/num;
    endif;
    r = [( x/2 + num/(2*x^(n-1))), 1];
    r_dupe = r;
    x_dupe = x;
    my_msg = strcat("proceed with iteration at i = ", num2str(i),"?");
    my_title = strcat("i = ",  num2str(i));
    my_yes = "proceed";
    my_no = "repeat prev";
    my_cancel = "give up";
    
    for i = 1:100;   
        
        BTN = questdlg(my_msg, my_title , my_cancel, my_no, my_yes, my_yes);
        
        if( strcmp ( BTN, "give up" ));
            return;
        elseif ( strcmp ( BTN, "proceed" ));
            continue;
        else
            i = i - 1;
            x = x_dupe;
            r = r_dupe;
        endif
        
        if abs(x-r(1)) < .00000000001;
            return ;
        endif;
        
        x = r(1);
        r = [x - (x^(n)-num) / ((n)*x^(n-1)) , i];
        x_dupe = [x_dupe, x];
        r_dupe = [r_dupe, r];
    endfor
endfunction
