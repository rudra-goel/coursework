%% 3.3.2
% Modify the following code from Prelab 2.6

amps = [1, 1] ;
freqs = [1200, 750] ;
phases = [0, 0] ;
fs = 4000; 
tStart = [.6, .2]; 
durs = [.5, 1.6]; 

maxTime = max(tStart+durs) + 0.1; %-- Add time to show signal ending 

durLengthEstimate = ceil(maxTime*fs); 

tt = (0:durLengthEstimate)*(1/fs); %-- be conservative (add one) 
xx = 0*tt; %--make a vector of zeros to hold the total signal 

for kk = 1:length(amps)
    nStart = round(fs * tStart(kk))+1; %-- add one to avoid zero index 

    xNew = shortSinus(amps(kk), freqs(kk), phases(kk), fs, durs(kk)); 
    
    Lnew = length(xNew); 
    
    nStop = nStart+Lnew-1; %<========= Add code 
    
    xx(nStart:nStop) = xx(nStart:nStop) + xNew;
end

tt = (1/fs)*(0:length(xx)-1); 
figure
spectrogram(xx,256,[ ],[ ],fs,'yaxis'); colorbar


function xs = shortSinus(amp, freq, pha, fs, dur) 
% amp = amplitude 
% freq = frequency in cycle per second 
% pha = phase, time offset for the first peak 
% fs = number of sample values per second 
% dur = duration in sec 
%
tt = 0 : 1/fs : dur; % time indices for all the values 
xs = amp * cos( freq*2*pi*tt + pha ); 
end