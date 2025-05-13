t = 0:1:4


% z = ((1255/1221)*exp(-pi*3*j/40)).^t
z = (exp(j*.4*pi)).^(15*t)

answer = sum(z)

% disp(answer)

% 
% x = real(z);
% y = imag(z);
% 
% % Plot the complex exponential on the complex plane
% figure;
% plot(x, y);
% xlabel('Real Part');
% ylabel('Imaginary Part');
% title('Complex Exponential on the Complex Plane');
% grid on;
% axis equal; 
% 
% % Optionally, plot individual points
% hold on;
% plot(x, y, 'ro'); 
% hold off;
