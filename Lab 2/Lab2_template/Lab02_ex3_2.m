%% 3.2

mySig.freq = 3; %-- (in hertz)

mySig.complexAmp = 4*exp(j*pi/6); 

dur = 3; 
start = -1; 
dt = 1/(32*mySig.freq); 

mySigWithVals = makeCosVals( mySig, dur, start, dt ); 

dbstop if error

%- Plot the values in sigWithVals
plot( mySigWithVals.times, mySigWithVals.values)   %<--- complete the plot statement
xlabel('t [s]')
ylabel('x(t)') 

function sigOut = makeCosVals(sigIn, dur, tstart, dt ) %
freq = sigIn.freq; 
X = sigIn.complexAmp; 
%
%...(Fill in several lines of code)...      
%
A = abs(sigIn.complexAmp);

tt = tstart: dt : dur + tstart%-- Create the vector of times 

xx = A*cos(2 * pi * sigIn.freq * tt + angle(sigIn.complexAmp));     %-- Vectorize the cosine evaluation

sigOut.times = tt;     %-- Put vector of times into the output structure
sigOut.values = xx;    %-- Put values into the output structure
end