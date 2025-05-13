%% 3.2
clc;clear;close all

A=2;
fc = 800;
alpha = 1000;
beta = 1.5;
gamma = 0;

fs = 4000;
tstart = 0;
dur = 2;

% Your code: Generate the signal
tt = 0:1/fs:dur;
psi = 2 * pi * fc * tt + alpha*cos(2*pi*beta*tt+gamma);

xx = A*cos(psi);

% Your code: plot spectrogram
plotspec((real(xx) + j*10^-12), fs, 128);
xlabel('time (s)')
ylabel('Frequency (Hz)')