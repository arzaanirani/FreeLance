cases = [100, 500, 1000, 5000, 10000, 25000, 50000, 100000, 500000, 1000000]  # All case values
openedCases = eval(input(""))  # Get the number of cases opened

totalValue = 1691600  # Total value of cases added up. Subtract value of selected cases from this
for i in range(0, openedCases):
    caseNumber = eval(input(""))  # Get the case number
    # Subtract the case value from the total value (subtract 1 from caseNumber because indexing of arrays starts at 0
    totalValue -= cases[caseNumber - 1]
average = totalValue / (10 - openedCases)  # Get average of value of cases not opened
offer = eval(input())  # Get banker offer

if offer > average:   # If the offer is greater than the average
    print("deal")     # Deal
else:                 # Or
    print("no deal")  # No Deal

