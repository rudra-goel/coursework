%% 3.5
clc;clear;close all

% Determine filter order
rp = .05;           % Passband ripple 
rs = .01;           % Stopband ripple 
fs = 10000;        % Sampling frequency
f = [3000, 3200];       % Cutoff frequencies
a = [1, 0];        % Desired amplitudes

% use firpmord to compute M
[n, f0, a0, w] = firpmord(f, a, [rp, rs], fs); 

% Find filter coefficients(HINT: IF n doesn't work, try n+1 or n+2)
% use firpm to compute filter coefficients b
b = firpm(n, f0, a0, w);

% Plot impulse response
stem(1:length(b), b);
title("Impulse response of FIR Filter");
xlabel("n");
ylabel("h[n]");

% Plot frequency response
figure
[HH,ww] = freekz(b,1,1024,'whole');
% Plot magnitude and frequency response here
plot(ww, abs(HH));
title("Magnitude response of FIR Filter");
xlabel("w (rad)");
ylabel("|H(e^jw)|");

%% Generate input: 1500Hz and 3500Hz
t = 0:1/fs:2;
x = cos(2*pi*(1500)*t) + cos(2*pi*(3500)*t);

% Filter signal
y = conv(x, b);

figure;
% Plot spectrogram
%plot(1:length(x), x); hold on;
%plot(1:length(y), y); hold on;
spectrogram(x, [], [], [], fs, "yaxis");
figure;
spectrogram(y, [], [], [], fs, "yaxis");