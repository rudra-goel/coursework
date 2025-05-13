tt = -5 : 0.01 : 10;
xx = cos( 0.5*pi*tt );
zz = 0.6*exp(-j*pi/4)*exp(j*0.5*pi*tt);
%
%<-- plot the real part, which is a sinusoid
plot( tt, xx, 'b-', tt, real(zz), 'r--' ), grid on
title('Test Plot of a TWO sinusoids')
xlabel('Time (sec)')