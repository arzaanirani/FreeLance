#p108ex8 - Direction

import sys
numCases = int(raw_input())

if (numCases < 1 or numCases > 1000):
    print "Too many cases"
    sys.exit(0)

cases = []
for i in range(numCases):
    cases.append(int(raw_input()))

for i in range(numCases):
    n = cases[i]
    if (n < 1 or n > 360):
        print "Invalid number reached"
        sys.exit(0)
    if (n <= 45 or n >= 315):
        print "Output: N"
    elif (n > 45 and n <= 135):
        print "Output: E"
    elif (n > 135 and n <= 225):
        print "Output: S"
    elif (n > 225):
        print "Output: W"
