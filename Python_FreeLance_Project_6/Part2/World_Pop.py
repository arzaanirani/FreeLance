import math

rate = eval(input("")) / 100   # convert rate from percent to decimal
startYear = eval(input(""))    # start year
population = eval(input(""))   # initial population
endYear = eval(input(""))      # end year

finalPopulation = population;  # Initialize final population to initial population
for x in range(startYear, endYear):  # Go through each year and calculate the new population
    finalPopulation *= 1 + rate  # Set the population to the currentPopulation in the year * 1.(rate)

# given the sample it seems that you round up the final population. Rounding down yields one less person
print(math.ceil(finalPopulation))  # print final population
