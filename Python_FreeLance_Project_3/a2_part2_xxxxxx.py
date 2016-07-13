import itertools
def largest_two(a):
    a.sort()
    return (a.pop() + a.pop())

def smallest_half(a):
    a.sort()
    length = len(a)
    sum = 0
    for i in range(int(length/2)):
        sum = sum + a[i]
    return sum

def median(a):
    a.sort()
    length = len(a)
    half = int(length/2)
    if not length % 2:
        return ((a[half] + a[half - 1])/ 2.0)
    return a[half]

def at_least_third(a):
    a.sort()
    count = 0
    current = None
    third = int(len(a)/3)
    for num in a:
        if (current == num):
            count = count + 1
            if (count == third):
                return current
        else:
            current = num
            count = 0
    return None

def triple_sum(a,x):
    a.sort(reverse = True)
    for triple in itertools.combinations_with_replacement(a, 3):
        if sum(triple) == x:
            return True
    return False
