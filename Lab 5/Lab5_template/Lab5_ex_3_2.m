%% 3.2
clc;clear;close all
load echart.mat

bdiffh = [1, -1];

imshow(echart)
m = 65; % 147, 221
yy1 = conv( echart(m,:), bdiffh );

%% Plot the input and output in the same figure using subplot
nn = 1:length(echart(m,:));

subplot(2, 1, 1);
plot(nn, echart(m,:)); grid on;
title('Input Signal');

subplot(2, 1, 2);
plot([nn, length(yy1)], yy1); grid on;
title('Convolved Signal');

%% Find the width of "E"

indices = find(yy1);

%ignore the first impulse

width = indices(3) - indices(2);
disp(width)
