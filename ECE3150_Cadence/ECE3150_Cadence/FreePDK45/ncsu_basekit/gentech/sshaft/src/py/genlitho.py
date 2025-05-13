###################################################
#
# gentech.py
#
# Created 1/3/2007 by Rhett Davis (rhett_davis@ncsu.edu)
#
#####################################################

import sshaft, os, sys, testproc

sshaft.beginstep('genlitho.py')

# Parse arguments
args=sys.argv[1:]
while (len(args)>0):
    arg=args[0]
    args=args[1:]
    sshaft.logf('LOG_WARN',"WARNING: Unrecognized argument "+arg)

opticdir=os.environ['PDK_DIR']+'/techfile/calibre/models/'
cmd='calibrewb -a opticsgen -opticalfile '+opticdir+'Opticald0 -o '+opticdir+'opticalD0'
sshaft.substep(mode='unix_prog', cmd=cmd, env='calibre')

cmd='calibrewb -a opticsgen -opticalfile '+opticdir+'OpticalD10 -o '+opticdir+'opticalD10'
sshaft.substep(mode='unix_prog', cmd=cmd, env='calibre')

cmd='calibrewb -a opticsgen -opticalfile '+opticdir+'OpticalDM10 -o '+opticdir+'opticalDM10'
sshaft.substep(mode='unix_prog', cmd=cmd, env='calibre')

sshaft.endstep()
