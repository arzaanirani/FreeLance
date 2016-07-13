def read_initial_info():
    ''' None->(float, 2D-list)
    Reads the file a4-input.txt and returns a tuple. The first element of that tuple is the limit,
    the second is a 2D list called banks of a format as follows (for this particular file a4-input.txt_:
    [[25.0, 1, 100.5, 4, 320.5], [125.0, 2, 40.0, 3, 85.0], [175.0, 0, 125.0, 3, 75.0], [75.0, 0, 125.0], [181.0, 2, 125.0]]

    Before returning the tuple the function should also print the 2D list banks by calling print(banks) 
    '''
    
    # YOUR CODE GOES HERE

    file = open('a4-input.txt', 'r+')
    lines = file.readlines()

    limit = float(lines[0])

    i = 0
    banks_array = []
    while i < len(lines) - 1:
        i += 1
        bank_array = []
        line = lines[i].replace("\n", "")
        if not line == "":
            split = line.split(" ")
            for s in split:
                bank_array.append(eval(s))
            banks_array.append(bank_array)
    print(banks_array,"\n\n")
    return limit, banks_array

def current_Assets(banks):
    ''' (2D-list)->list
    given the 2D list banks, returns a (1D) list with the current_assets of all banks
    Precondition: the format of the 2D list banks is as explained in the assignment
    and as produced in read_initial_info()
    '''

    # YOUR CODE GOES HERE TOO
    #for bank in banks:

def calculate_assets(bank):
    balance = float(bank[0])

    pairs = int((len(bank) - 1) / 2)
    for j in range(0, pairs):
        balance += float(bank[2 + (j * 2)])
    return balance


def poll_unsafe(banks, safe_banks, limit):
    for bank_id in safe_banks:
        bank = banks[bank_id]
        if calculate_assets(bank) < limit:
            return bank


def remove_assets(banks, safe_banks, bank_id):
    new_banks = []
    for safe_id in safe_banks:
        bank = banks[safe_id]
        pairs = int((len(bank) - 1) / 2)
        offset = 0
        for j in range(0, pairs):
            if int(bank[1 + (j * 2) - offset]) == bank_id:
                del bank[1 + (j * 2) - offset]
                del bank[1 + (j * 2) - offset]
                offset += 2
        new_banks.append(safe_id)
    return new_banks


def print_safe_banks(banks, safe_banks):
    print("Current assets of the banks which are not in the unsafe list:")
    for bank_id in safe_banks:
        bank = banks[bank_id]
        print("Bank ",bank_id," Current Assets: ",calculate_assets(bank)," millions")


def get_bank_ids_readable(banks):
    readable = ""
    for bank_id in banks:
        readable += str(bank_id) + " "
    return readable


def find_unsafe():
    '''None->None

    Your solution goes here. This function should start off by calling read_initial_info()
    and then work with the data read_initial_info() returns, i.e. with limit and 2D list banks
    It should call other helper functions
    '''
    tuple = read_initial_info()
    limit = tuple[0]
    banks = tuple[1]
    safe_banks = []
    for i in range(len(banks)):
        safe_banks.append(i)

    unsafe_banks = []

    print("Safe limit is:" , limit , "million dollars\n\n")

    print("Current unsafe banks are: ")
    print_safe_banks(banks, safe_banks)
    print("\n")

    bank = poll_unsafe(banks, safe_banks, limit)
    while not (bank is None):
        bank_id = banks.index(bank)
        unsafe_banks.append(bank_id)
        safe_banks.remove(bank_id)
        safe_banks = remove_assets(banks, safe_banks, bank_id)
        print("Adding bank ",bank_id," to the list of unsafe banks.")
        print("Current unsafe banks are: " + get_bank_ids_readable(unsafe_banks))
        print_safe_banks(banks, safe_banks)
        bank = poll_unsafe(banks, safe_banks, limit)
        print("\n")

    print("Unsafe: " , get_bank_ids_readable(unsafe_banks))
    print("Safe: " , get_bank_ids_readable(safe_banks))

    # YOUR CODE GOES HERE TOO
 

# main
# main can only have this function call and nothing else. Do not modify after this line
find_unsafe()
    
