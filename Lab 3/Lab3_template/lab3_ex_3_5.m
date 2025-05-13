%% 3.5
clc; clear; close all

[xx,fs] = audioread('ece2026lab1.wav');

Tsect = .1;
Lsect = fs * Tsect;


DBrange = 80;

% Linear spectrogram
figure
plotspec( xx+j*1e-12, fs, Lsect), colorbar, grid on %-- with negative frequencies

% DB spectrogram
figure
plotspecDB( xx+j*1e-12, fs, Lsect, DBrange), colorbar, grid on %-- with negative frequencies

