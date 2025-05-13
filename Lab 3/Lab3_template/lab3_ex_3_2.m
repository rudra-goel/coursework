%% 3.2
clc;clear;close all

%% Fill in the values
fs = 1000;
Amp = 1;
T = 1;
tStop = 5;

tt=0:(1/fs):tStop;
xx=Amp*abs(sin(2*pi*tt/T));

Tsect = 5*T;
Lsect = fs * Tsect;

figure
plot(tt,xx);
title('Full-wave Rectified Sine'); xlabel('t [sec]')

figure
plotspec( xx+j*1e-12, fs, Lsect ), colorbar, grid on %-- with negative frequencies

% *****Hint: Zoom in on the verticle axis to see the harmonics*****

% Fundamental freq = ???cas

%% GUI
% fseriesdemo       %<==Uncomment to use the GUI

% a1=???
% a3=???