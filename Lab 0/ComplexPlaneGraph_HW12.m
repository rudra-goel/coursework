% indices = 0:40;
% 
% vectors = zeros(length(indices), 2);
% 
% z = (1255/1221)*exp(j*-pi/40);
% 
% for index = 1:length(indices)
% 
%     temp = z^(indices(index));
% 
%     vectors(index, 1) = real(temp);
%     vectors(index, 2) = imag(temp);
% 
% 
% end
% 
% disp(vectors);
% plot(vectors(:, 1), vectors(:, 2), 'ro', 'MarkerSize',8);
% 
% % Customize plot (optional) 
% grid on;
% xlabel('Real Axis');
% ylabel('Imaginary Axis');
% title('Complex Number on the Complex Plane');
% axis equal; % Ensure axes have the same scale
% 
% % Display the point's coordinates
% text(real_part + 0.2, imag_part, ['(', num2str(real_part), ', ', num2str(imag_part), ')']); 

% Define the center and radius of the circle
center_x = 6;
center_y = 8;
radius = 10;

% Generate angles for the circle
theta = 0:0.01:2*pi;

% Calculate x and y coordinates of points on the circle
x = center_x + radius * cos(theta);
y = center_y + radius * sin(theta);

% Plot the circle
plot(x, y, 'b-'); 


% Customize the plot
grid on;
xlabel('Real Axis');
ylabel('Imaginary Axis');
title('Circle in the Complex Plane');
axis equal;


% Get current axes handle
ax = gca; 

% Set origin to (0, 0)
ax.XAxisLocation = 'origin';
ax.YAxisLocation = 'origin';

% Customize appearance (optional)
ax.Box = 'off'; % Remove the box around the plot