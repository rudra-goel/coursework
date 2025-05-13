%% 3.2
clc;clear;close all

%% Part(a)   Fill in the values
fs = 1000;
Amp = 1;
T = 1;
tStop = 5;

tt=0:(1/fs):tStop;
xx=Amp*abs(sin(2*pi*tt/T));

Tsect = 5*T;
Lsect = fs*Tsect;
DBrange = 80;

figure
plotspecDB( xx+j*1e-12, fs, Lsect, DBrange), colorbar, grid on %-- with negative frequencies


%% Part(b)   Fill in the values
fs = 1000;
Amp = 1;
T = 2;
tStop = 5;

tt=0:(1/fs):tStop;
xx2=Amp*abs(sin(2*pi*tt/T));

Tsect = T*2.5;
Lsect = fs * Tsect;
DBrange = 80;

figure
plotspecDB( xx+j*1e-12, fs, Lsect, DBrange), colorbar, grid on %-- with negative frequencies

