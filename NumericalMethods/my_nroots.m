% calling clear at the start of the script to ensure that there are no
% reasons to doubt the consistency of the data when we arriave at the end of
% the script.
clear





compSize = 300;
% the next line serves to preassign a matrix of all zero values so we don't have 
% to worry about the time costs of growing the matrix should we want to test 
% significantly large intervals of n or num. 
%
%Labeling the columns 1 through 5 going left to right you can expect:
% column 1: "mine" ->the solution returned by our nroot() function.
% column 2: "theirs" ->the solution from matlab's nthroot() function.
% column 3: "num" ->the base number value beeing passed into nroot and nthroot.
% column 4: "n" -> the root to which the functions will try to calculate for the given num
% column 5: "abs(mine-theirs) < delta" -> will return either a 1 or 0 based upon
%           if nroot's solution is within "delta" tolerance of nthroot.
sideByside = zeros(compSize, 5);% 
delta = 10^-12; % the error tolerance suggested in the assignment.
num = 1; % the starting value for num, will be updated inside of the while loop

% index_position allows for a repeating block of behavior in the for loops with 
% unique indexing when it comes time for saving the data into sideByside.
index_position = 1;


% use this initial loop block to identify points at which the nroot() function
% fails to find solutions that are less than 'delta' different from matlab's 
% nthroot().
while(index_position < compSize)

    % if you are looking for a good place to monkey with values to try and cause
    % strange behavior from nroot and nthroot, this is probably a good place to
    % start. multiplying by fractions or trying to cause num to land on multiples
    % of 3 or powers of 2 is a good starting point.
    num = num*2;
    %% with each pass of the while loop, 200 row block of data will be created
    %% in the sideByside matrix.
    %  
    for batch = 1:10^5:10^6
        for row = 1:20
            n = batch + row;
            mine = (nroot ( num, n ));
            theirs = (nthroot ( num, n ));           
            sideByside(index_position,:) = [mine, theirs, num, n, abs(mine-theirs) < delta];
            index_position = index_position +1;
            
        endfor
    endfor
    % just finished creating a data block, 
    num = num^2;
endwhile

% myRootFailedAt will be a matrix with dimensions of k by 6, where k is the number
% rows that myRootFailedAt has copied from sideByside based upon the boolean result
% that was stored there when sideByside was being populated.
myRootFailedAt = [];
for row = 1:size(sideByside)(1)
    if(sideByside(row,5))<1
        myRootFailedAt = [myRootFailedAt; sideByside(row,:), row];
    endif
endfor


%~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~%
% everything above this point is only necessary if you need to identify what 
% num values represent significant numbers of values that
% misbehave. But once you've done that the bellow logic structure is helpful in refinin


test_base_num = 128;
didItFail = zeros(1,10^2);
Isize = size(didItFail)(2);
fails = 0;


% this next line is only here to serve as a reminder that if we want to see what
% 'n' finished on in the above loops, we can call it here.
% previous_n = n;   
gap_check_n = 10^6;


user_input = NaN;

base_num_input = questdlg('Would you like to input a value for the base number?', 'Do you wish to pick a base number?', 'Yes','No','No');

if strcmp(base_num_input, 'Yes')
    base_num_input = inputdlg('Please enter your desired base value, invalid input will default into a base value of 128.');    
    if size(base_num_input)(1) > 0
        user_input = str2double(base_num_input);
    endif    
endif

if (user_input == NaN) == 0 % yeah, I'm having problem with the "not" operator
    test_base_num = user_input;
endif  

base_n_input = questdlg('Would you like to input a value for how high of an order n you would like to loop to?', 'Do you wish to pick a value for n?', 'Yes','No','No');  

if strcmp(base_n_input, 'Yes')
    base_n_input = inputdlg('Please enter your desired base value, invalid input will default into a base value of 128.');
    if size(base_n_input)(1) > 0
        user_input = str2double(base_n_input);
    endif
endif

if (user_input == NaN) == 0 % yeah, I'm having problem with the "not" operator
    gap_check_n = user_input;
endif  


disp('scirpt is set to test the base number of:')
disp(test_base_num)
disp('for roots ranging from 1 to:')
disp(gap_check_n)
% The gaps variable bellow will clearly illustrate where nroot() fails to match 
% the findings of matlabs's nthroot().
%
% it will record where streaks of consecutive failures/successes longer than a 
% single data point occur. 
%
% It will illustrate this information by showing consecutive streaks as non-zero 
% value pairs on their own row. This means each row in the gap variable represents
% a new series of failures or successes. 
%
% So, if you were to explore the nth roots of 128 for n from 1 to 10^3, you should
% see that gap holds a single row vector of [1, 1000], but if you go from 1 to
% 10^6
gaps = [1, 0];
makeNewRow = 0;
for n = 1:gap_check_n
    
    if (abs(nroot(test_base_num,n) - nthroot(test_base_num,n)) > delta)
    
        fails = fails + 1;
        
        if(gaps(end) == (n-1) || gaps(end) == 0)
            gaps(end) = n;
            makeNewRow = 1;
        else
            % yes, we want it to be [gaps; n, n] in order for the above conditional
            % check to be able to properly recognize if we are still being
            % continuous in the current series of failures.
            gaps = [gaps; n, n];
        endif
        
        if(fails + 1 > Isize)
            % a vain attempt at emulating the ternary assignment from Java.
            % by taking advantage of the fact that boolean operations return zero
            % for false conditions, you can conditionally strip components of a 
            % summation away based upon basic arithmatic comparisons.
            growTo = Isize *(((2^31 - Isize) > Isize) * 1 + ...
                       1/2 * ((2^31 - Isize/2) > Isize) +...
                       1/4 * ((2^31 - Isize/4) > Isize));            
                % The reason growTo is set up so conditionally is to try and avoid having
                % the didItFail variable colide with the "error: out of memory or dimension too 
                % large for Octave's index type"
            
            didItFail = [didItFail, zeros(1,growTo)];
            Isize = size(didItFail)(2); 
        endif
        % now that we know that we've made room for future growth of didItFail
        % we can go ahead and update the variable without boatloads of single value
        % concatination.
        didItFail(fails) = n;
        
        
    else
        if makeNewRow >0
            makeNewRow = 0;
            if (gaps(end) == 0)
                gaps(end) = n;
            endif
            
            gaps = [gaps; n, n];
            
        elseif(gaps(end) == (n-1) || gaps(end) == 0)
        
            gaps(end) = n;
            
        else
            % yes, we want it to be [gaps; n, n] in order for the above conditional
            % check to be able to properly recognize if we are still being
            % continuous in the current series of failures.
            gaps = [gaps; n, n];
        endif
    endif
endfor
msgbox("please note that the 'didItFail' variable having many values does not\n\
automatically mean there were that many points where nroot did not match nthroot.\n\n\
Only values greater than 0 indicate a failure, hence the variable name being\n\
in the form of a question, 'didItFail? == 0 = no, > 0 = yes' ")
