#Problem J1: What is n, Daddy?

n = int(raw_input("Please enter a number between 1 and 10:"))

if (n < 1 or n > 10):
    print "Error: Please enter a number between 1 and 10 (inclusive)"

if (n == 2 or n == 3):
    print "Output:",2
elif (n == 4 or n == 5):
    print "Output:",3
else:
    print "Output:",1
