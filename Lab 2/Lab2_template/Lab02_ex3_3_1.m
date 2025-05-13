%% 3.3.1

ss(1).freq = 21; ss(1).complexAmp = exp(j*pi/4); 
ss(2).freq = 15; ss(2).complexAmp = 2i; 
ss(3).freq = 9; ss(3).complexAmp = -4; 
%
dur = 1; 
tstart = -0.5; 
dt = 1/(21*32); %-- use the highest frequency to define delta_t 
%
ssOut = addCosVals( ss, dur, tstart, dt ); 
%
plot( ssOut.times, ssOut.values) %



function sigOut = addCosVals( cosIn, dur, tstart, dt ) 
%ADDCOSVALS Synthesize a signal from sum of sinusoids 
%(do not assume all the frequencies are the same)
% 
% usage: sigOut = addCosVals( cosIn, dur, tstart, dt ) 
% 
% cosIn = vector of structures; each one has the following fields: 
%   cosIn.freq = frequency (in Hz), usually none should be negative
%   cosIn.complexAmp = COMPLEX amplitude of the cosine
% 
% dur = total time duration of all the cosines 
% start = starting time of all the cosines 
% dt = time increment for the time vector 
% 
% The output structure has only signal values because it is not necessarily a sinusoid 
%   sigOut.values = vector of signal values at t = sigOut.times
%   sigOut.times = vector of times, for the time axis
% 
% The sigOut.times vector should be generated with a small time increment that 
%   creates 32 samples for the shortest period, i.e., use the period
%   corresponding to the highest frequency cosine in the input array of structures.

% <--- Write your code here --->

tt = tstart:dt:dur;
xx = 0*tt;


for k = 1:length(cosIn)

    xNew = abs(cosIn(k).complexAmp) * cos(2*pi*cosIn(k).freq*tt + angle(cosIn(k).complexAmp));

    xx(1:length(tt)) = xx(1:length(tt))+xNew;
end

sigOut.times = tt;
sigOut.values = xx;

end