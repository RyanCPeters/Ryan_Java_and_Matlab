clear victory_cases
clear s
clear i
clear deets
clear scr_BTN

victory_cases = [0 0 0 0];
s = 30
use_manual_increment = questdlg("would you like to be able to manually increment the loop?",...
"checking for manual override request","No","Yes","No")
if strcmp ( use_manual_increment, "Yes" )
	for i = 1:100;
		scr_BTN = questdlg("do you want to manually increment?", "Increment Manually?","No")
		if strcmp ( scr_BTN, "Yes" )
			deets = man_roots(s,i)
		elseif strcmp (scr_BTN, "No")      
			deets = nroot(s,i)
		else
			return;
		endif
	endfor;
else
	deets = [nroot(s,1), s^(1/1)];
	for i = 2:100;
		deets = [deets;nroot(s,i), s^(1/i)];
	endfor
endif
