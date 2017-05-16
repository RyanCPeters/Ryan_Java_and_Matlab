%% this script is to put froot through a wide range of test conditions, 
%  while recording those conditions. It should then graph the trends of the
%  functions outputs against the relative accuracy of the initial guess.

myLog   = @(x) log(x);
myXLog  = @(x) x.*log(x);
myExpo  = @(x) exp(x);
myXExpo = @(x) x.*exp(x);
mySin   = @(x) sin(x);
myCos   = @(x) cos(x);
myXSin  = @(x) x.*sin(x);
myXCos  = @(x) x.*cos(x);

log_store = [];
xlog_store = [];
exp_store = [];
xexp_store = [];
sin_store = [];
cos_store = [];
xsin_store = [];
xcos_store = [];

for i = 1:100
    log_store = [log_store; froot(myLog,i),i];
    xlog_store = [xlog_store; froot(myXLog,i),i];
    exp_store = [exp_store; froot(myExpo,i),i];
    xexp_store = [xexp_store; froot(myXExpo,i),i];
    sin_store = [sin_store; froot(mySin,i),i];
    cos_store = [cos_store; froot(myCos,i),i];
    xsin_store = [xsin_store; froot(myXSin,i),i];
    xcos_store = [xcos_store; froot(myXCos,i),i];
end


