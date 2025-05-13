%% 3.4
clc;clear;close all

wd = 2*pi*1/32; xpix = ones(256,1)*cos(wd*(0:255));

% Your code: Generate xpix4 and xpix12

xpix4 = ones(256, 1)*cos(4*wd*(0:255));
xpix12 = ones(256, 1)*cos(12*wd*(0:255))

% Downsampling images
xpix4_downsample = xpix4(1:2:end,1:2:end);
xpix12_downsample = xpix12(1:2:end,1:2:end);

% Your code: Show the 2 images and the 2 downsampled images
subplot(2, 2, 1);
imagesc(xpix4);

subplot(2, 2, 2);
imagesc(xpix12);

subplot(2, 2, 3);
imagesc(xpix4_downsample);

subplot(2, 2, 4);
imagesc(xpix12_downsample);
