#p307ex7: Grades

import sys
numGrades = int(raw_input())

if (numGrades < 1 or numGrades > 100):
    print "Too many grades"
    sys.exit(0)

grades = []
for i in range(numGrades):
    n = int(raw_input())
    if (n < -1000 or n > 1000):
        print "Incorrect number!"
        sys.exit(0)
    grades.append(n)

for i in range (numGrades):
    n = grades[i]
    if (n >= 80 and n <= 100):
        print "Letter Grade: A"
    elif (n >= 70 and n <= 79):
        print "Letter Grade: B"
    elif (n >= 60 and n <= 69):
        print "Letter Grade: C"
    elif (n >= 50 and n <= 59):
        print "Letter Grade: D"
    elif (n >= 0 and n <= 49):
        print "Letter Grade: F"
    else:
        print "Letter Grade: X"
