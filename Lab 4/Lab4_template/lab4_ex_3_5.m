%% 3.5
clc;clear;close all

img = imread('lighthouse.png');
figure; imshow(img)

% Downsample by 2
img_downsampled = img(1:2:end, 1:2:end)
% Your code: What's the size of the downsampled image?

figure; imshow(img_downsampled)

% show the images using imshow()
figure; imshow(img); title('Original')
figure; imshow(img_downsampled); title('Downsampled')

%% Your example
clc;clear;close all

image = imread('fence.png')
figure; imshow(image)

factor = 3;
image_down = image(1:factor:end, 1:factor:end, 1:end);
figure; imshow(image_down);