onerror {exit -code 1}
vlib work
vcom -work work Packaging.vho
vcom -work work Packaging_Waveform.vwf.vht
vsim -novopt -c -t 1ps -L cyclonev -L altera -L altera_mf -L 220model -L sgate -L altera_lnsim work.Packaging_vhd_vec_tst
vcd file -direction Packaging.msim.vcd
vcd add -internal Packaging_vhd_vec_tst/*
vcd add -internal Packaging_vhd_vec_tst/i1/*
proc simTimestamp {} {
    echo "Simulation time: $::now ps"
    if { [string equal running [runStatus]] } {
        after 2500 simTimestamp
    }
}
after 2500 simTimestamp
run -all
quit -f
