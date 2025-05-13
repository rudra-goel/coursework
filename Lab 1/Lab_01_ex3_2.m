%% 3.2 Vectorization
% Change the following code without using the inner for loop
%% With For Loops
%--- make a plot of sum of cosines
dt = 1/800;
XX = rand(1,3).*exp(2i*pi*rand(1,3)); %--Random amplitude and phases
freq = 20;
ccsum = zeros(1,500);
for kx = 1:length(XX)
    for kt = 1:500
        t = kt*dt;
        Ak = abs(XX(kx));
        phik = angle(XX(kx));
        ccsum(kt) = ccsum(kt) + Ak*cos(2*pi*freq*t  + phik);
        tt(kt) = t;
    end
end
plot(tt,ccsum) %-- Plot the sum sinusoid
grid on, zoom on, shg


%% Your code here
ccsum2 = zeros(1,500);
tt2 = dt*[1:1:500]; % generate the timestamps

for kx = 1:length(XX)

    Ak = abs(XX(kx));
    phik = angle(XX(kx));

    ccsum2 = Ak*cos(2*pi*freq*tt2 + phik);

end
figure
plot(tt2,ccsum2) %-- Plot the sum sinusoid
grid on, zoom on, shg