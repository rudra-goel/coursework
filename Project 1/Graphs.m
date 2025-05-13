xx = [1, 2, 4, 8];
xx2 = [50, 100, 200, 400];
power = [11.88e-15, 23.48e-15, 46.69e-15, 93.11e-15];

vhl = [9.05e-12, 14.16e-12, 20.25e-12, 32.93e-12];
vlh = [10.68e-12, 15.57e-12, 21.88e-12, 34.58e-12];

figure;
plot(xx2, power)
%plot(xx, vhl, 'r-', 'DisplayName','V_hl');
%hold on;
%plot(xx, vlh, 'b-', 'DisplayName','V_lh');

xlabel('Frequency (Hz)');
ylabel('Power (joules)')

hold off;