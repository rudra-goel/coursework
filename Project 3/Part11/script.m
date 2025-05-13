%% Sweeping T1
t1 = [
    0.5
    0.45
    0.4
    0.35

    0.3
    0.25
    0.2
    0.15
    
    0.1
    0.05
    0.025
    0.0125
    
    0.005
    0.004
    0.003
    0.00295

    0.0027
    0.0026
    0.0025
    0.00225        % Design failed 
    
];

clockTicks = 15 - t1*5

delay_1 = [ 
    71.687
    71.682
    71.68
    71.67231

    71.65217
    71.63902
    71.6221
    71.5988

    71.5733
    71.5092
    71.4197
    71.3378

    74.0291
    75.1269
    81.2919
    82.8091
    
    83.1609
    91.3586
    98.1865

    103.2391

    ];
% delay_1 = delay_1 * 10^-12;

plot(t1, delay_1, "LineWidth", 5); grid on; title("Decreasing Setup Time on CLK-Q Delay for DFF");
xlabel("__T1 (xT)");
ylabel("CLK-Q Delay (ps)");

T1_Delay_Table = table(t1, delay_1, 'VariableNames', {'T1', 'Delay (s)'});
disp('T1 vs CLK-Q Delay Table:');
disp(T1_Delay_Table);

%% Sweeping T2

t2 = [
    0.5
    0.4
    0.3
    0.2

    0.1
    0.05
    0.025
    0.015

    0.014
    0.013
    0.012
    0.011

    0.01
    0.005
    0
    -0.001

    -0.002
    -0.003
    -0.004
    -0.0045

    -0.005
    -0.009
    -0.25

    ];

delay_2 = [
    71.6875
    71.6866
    71.685
    71.6813

    71.6845
    71.6863
    71.685
    71.7998
    
    71.633
    71.625
    71.616
    71.5984
    
    72.3845
    72.3345
    72.344
    72.2438
    
    72.3901
    71.461
    71.5664
    80.2134

    116.325
    0.0
    0.0

    ];

% delay_2 = delay_2 * 10^-12;

plot(t2, delay_2, "LineWidth", 5); grid on; title("Decreasing Hold Time on CLK-Q Delay for DFF");
xlabel("__T2 (xT)");
ylabel("CLK-Q Delay (ps)");

% Creating and displaying table
T2_Delay_Table = table(t2, delay_2, 'VariableNames', {'T2', 'Delay (s)'});
disp('T2 vs CLK-Q Delay Table:');
disp(T2_Delay_Table);