clear victory_cases
clear s
clear i
clear deets
clear scr_BTN
clear use_manual_increment


s = 30

% victory_cases = [ s i myNroot theirNroot 
victory_cases = [s 0 0 0];


use_manual_increment = questdlg('would you like to be able to manually increment the loop?','checking for manual override request','No','Yes','No');
if strcmp ( use_manual_increment, 'Yes' )
	for i = 1:100;
		scr_BTN = questdlg('do you want to manually increment?', 'Increment Manually?','No');
		if strcmp ( scr_BTN, 'Yes' )
			deets = man_roots(s,i);
        elseif strcmp (scr_BTN, 'No')      
			deets = nroot(s,i);
		else
			return;
        end
    end
else
	deets = [s,1, nroot(s,1), nthroot(s,1)];
    myString = 'wictory';
	for i = 2:100;
        myNroot = nroot(s,i);
        theirNroot = nthroot(s,i);
        if(abs( myNroot - theirNroot) < 10^(-4) )
            myString
            victory_cases = [ victory_cases; s, i, myNroot, theirNroot ];
        else
            deets = [deets; nroot(s,i), s^(1/i)];
        end
    end
end
