close all;
clear;

%%
%obtain impulse response of FIR filter (b_k)
wp = 0.6*pi;
ws = 0.64*pi;

passband_ripple = 0.05;
stopband_ripple = 0.01;

fs = 10000;

[n, f0, a0, w] = firpmord([wp/(2*pi)*fs, ws/(2*pi)*fs], [1, 0], [passband_ripple, stopband_ripple], fs); 

b = firpm(n, f0, a0, w);

%%graph the impulse response and magnitude response of the filter
stem(1:length(b), b);
title("impulse response of filter");
figure;


Xk = freqz(b);

plot((pi/length(Xk))*(1:length(Xk)/2), abs(Xk(1:length(Xk)/2)));
title("magnitude response of FIR filter");

%define an input signal;


n = 1:1/fs:2*pi;

x = cos(2*pi*(2500/fs)*n) + cos(2*pi*(4000/fs)*n);
