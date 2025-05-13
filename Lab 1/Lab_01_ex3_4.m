%%
%3.4 Reading WAV File into MATLAB and Playing an Array
% access the 'sample.wav' and plot it from 0.25 to 0.5
[xx, fs] = audioread('sample.wav'); %fill in your file name
dur = length(xx)/fs; %duration of sound file
len = length(xx); %length of sound array
tt1 = .25:1/fs:.5; %time array for plot
figure;
plot(tt1, xx(2000:4000)); 
title('Sound wave from 0.25 to 0.5 seconds'); xlabel('Time (sec)');

