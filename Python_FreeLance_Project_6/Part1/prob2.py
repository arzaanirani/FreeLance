#Problem J1: Which Alien?

a = int(raw_input("Enter the number of antenna's that the witness claimed to have seen on the alien : "))    #Number of antenna
b = int(raw_input("Enter the number of eyes seen on the alien : "))    #Number of eyes

if (a >= 3 and b <= 4):
        print "Output: TroyMartian"
if (a <= 6 and b >= 2):
        print "Output: VladSaturnian"
if (a <= 2 and b <= 3):
    print "Output: GraemeMercian"
