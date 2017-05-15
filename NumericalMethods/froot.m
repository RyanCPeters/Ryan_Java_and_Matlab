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
## @deftypefn {Function File} {@var{retval} =} froot (@var{input1}, @var{input2})
##
## @seealso{}
## @end deftypefn

## Author: R.Peters <R.Peters@RYANS-DESKTOP>
## Created: 2017-05-15

function retval = froot (f,x,g)


h = 0.31210;

if(nargin > 2)
    nargin(3)
    if(nargin(3) > 0)
        retval = x - f(x)/g(x)
    end
else
    retval = x - (f(x)/df(f,x))
end

for pass = 1:1000
    last = retval;
    retval = retval - (-h*f(retval))/(f(retval-h)-f(retval))
    if(abs(last-retval)/abs(retval))< 10^-10
        break;
    end
end

    
end
