import re
import os
import binascii

fileName = str(raw_input())
toCheck = str(raw_input())
#p = re.compile(ur'^\t0x\S{4}:\s+.{41}(\S+)\n?|\S.*', re.MULTILINE)
p = re.compile(ur'^\t0x\S{4}:\s+.{41}(\S+)\n?|(\d\d:\d\d:\d\d\.\d+) IP ([\d.]+) > ([\d.]+).*length (\d+)', re.MULTILINE)

content = open(fileName).read()
res = p.finditer(content)
s = ""
prevLine = ""
for m in res:
    if m.group(5) == 0:
        continue
    if m.group(3) != None:
        print s
        print "\\\\\\"
        print m.group(3)
        s = ""

    if m.group(1) != None:
        if toCheck in m.group(1):
            print "Found " + toCheck
            print prevLine
        s += m.group(1).replace(".","")
        prevLine = m.group(1).replace(".","")
