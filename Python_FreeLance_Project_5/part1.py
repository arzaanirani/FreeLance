import os
import re

p = re.compile('\w*@\w*.com')
fileName = str(raw_input())

matchList = []

m = p.findall(open(fileName).read().lower())
for i in m:
    matchList.append(i)

matchList.sort()

for match in matchList:
    print match
