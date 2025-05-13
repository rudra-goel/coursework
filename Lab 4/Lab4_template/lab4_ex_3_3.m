%% 3.3
clc;clear;close all

xpix = ones(256,1)*cos(2*pi*(0:255)/32);

% Your code: show the image
imagesc(xpix)
disp(xpix(1, :))
colormap gray;
