(procedure (runDFFCharacterization)
  (let* (
         (libName "myLib")
         (cellName "DFF_Testbench")
         (viewName "schematic")
         (freq 200e6)
         (Tclk (1.0 / freq))
         (tStart 0)
         (tStop 20e-9)
         (stepSize 1e-12)
         (t1_max 5e-9)
         (t1_min 0)
         (results '())
         (simDir "./ocean_sim")
         (numSteps (truncate ((t1_max - t1_min) / stepSize)))
        )

    (ocnWaveformTool 'wavescan)
    (ocnSetDir simDir)
    (ocnOpenWaveformWindow)
    (ocnLoadDesign libName cellName viewName "spectre")
    (ocnDesignOption 'analysis 'tran)
    (ocnAnalysis 'tran ?stop tStop)

    ;; Loop over T1 values
    (for i 0 numSteps
      (let* (
             (t1 (t1_max - (i * stepSize)))
             (t2 2e-9)
             (clkRiseTime 10e-9)
             (d_high_time (clkRiseTime - t1))
             (d_low_time (clkRiseTime + t2))
             (d_pwl (list (list tStart 0)
                          (list d_high_time 1.2)
                          (list d_low_time 0)))
            )

        ;; Set stimulus
        (ocnSetSource "/Clk" 'vpulse
                      'v1 0 'v2 1.2
                      'period Tclk
                      'rise 1e-12 'fall 1e-12
                      'width (Tclk / 2)
                      'delay 0)

        (ocnSetSource "/D" 'pwl d_pwl)

        ;; Run simulation
        (ocnRun)
        (ocnLoadWaveform)

        ;; Measure Clk→Q delay
        (let* (
               (clkEdgeTime clkRiseTime)
               (qCrossTime (ocnCross "Q" clkEdgeTime 'rising 0.6))
               (delay (qCrossTime - clkEdgeTime))
              )
          (printf "T1 = %.3e s, Delay = %.3e s\n" t1 delay)
          (setq results (cons (list t1 delay) results))
        )
      )

    ;; Save results to CSV
    (let ((f (outfile "clk_to_q_delay.csv")))
      (fprintf f "T1,Delay\n")
      (foreach point results
        (fprintf f "%.12e,%.12e\n" (car point) (cadr point)))
      (close f)
    )

    (printf "Characterization complete. Results saved to clk_to_q_delay.csv\n")
  )
)
)