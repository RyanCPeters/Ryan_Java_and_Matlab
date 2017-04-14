clear victory_cases
clear s
clear i
clear deets
clear scr_BTN

victory_cases = [0 0 0 0];
s = 30;
for i = 1:100;
    scr_BTN = questdlg("do you want to manually increment?")
    if strcmp ( scr_BTN, "Yes" )
        deets = man_roots(s,i)
    elseif strcmp (scr_BTN, "No")      
        deets = nroot(s,i)
    else
        return;
    endif
%    if abs(deets(1) - s^(1/i)) > .0000000001;
%%        disp(i);
%    else;
%%        victory_cases = [victory_cases; s^(1/i) i deets(1) deets(2) ]
%    endif;
endfor;