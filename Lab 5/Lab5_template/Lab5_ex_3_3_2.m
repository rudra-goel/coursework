%% 3.3.2
clc;clear;close all

%% Part a
xx = 255*(rem(1:159,30)>19);
bb = [1, -1];
yy = firfilt(bb, xx);

% Plot x and y using subplot
nn1 = 1:length(xx);
subplot(3, 1, 1);
stem(nn1-1, xx(nn1));

title("X Vector");

nn2 = 1:length(yy);
subplot(3, 1, 2);
stem(nn2-1, yy(nn2), 'filled');
title("Y Vector");

%% Part b
% Explain the effect of the first-difference operator on this input signal.
% puts an impulse (positive) when the input signal transitions from 0 to
% HIGH and then gives a negative impulse when the input signal
% transitions from high to low
%% Part c
% Find length of xx and yy
lengthY = length(xx)+length(bb)-1;

%% Part d: find the edges
threshold = 255;
d = abs(yy)>=threshold;

%% Part e: find edges indices
edge_index = find(d)
num_edges = length(edge_index);
rangeIdx = 1:num_edges;

subplot(3, 1, 3);
stem(rangeIdx-1, edge_index(rangeIdx)); grid on;
title("edges in signal");