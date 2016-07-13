values = input().split(" ")  # Get the line, split it by spaces to get the values separate
rate = eval(values[0]) / 100   # convert rate from percent to decimal
population = eval(values[1])   # initial population
startYear = eval(values[2])    # start year
endPopulation = eval(values[3])  # end population

totalPopulation = population   # Population counter
finalYear = startYear  # Year counter
while totalPopulation < endPopulation:  # While we haven't found the year where the population is what we want
    finalYear += 1  # Add a year
    totalPopulation *= 1 + rate  # Get the new population

print(finalYear)  # Print the year
