%% Part 1
vdd = [1, .9, .8, .7, .6];

s_delays = [38.03578e-12, 45.14933e-12, 56.82557e-12, 79.13736e-12, 132.8568e-12];
c_out_delays = [29.5564e-12, 34.62276e-12, 42.87585e-12, 58.4766e-12, 96.13775e-12];

subplot(2, 1, 1); 
plot(vdd, s_delays); grid on; 
title('Relationship between Vdd and Propagation Delay for Sum Output');
xlabel('Vdd (V)');
ylabel('Propagation Delay (s)');


subplot(2, 1, 2); 
plot(vdd, c_out_delays); grid on; 
title('Relationship between Vdd and Propagation Delay for Carryout Output');
xlabel('Vdd (V)');
ylabel('Propagation Delay (s)');