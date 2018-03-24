




def main():
    try:
        nameTJ = input("Please enter the name of TJ:")
        FileTJ = open(nameTJ,"r")
        MysumTJ = 0
        count = 0
        for time in FileTJ:
            count += 1
            MysumTJ += int(time)
        print("The Average time of TJ is",MysumTJ/count)
        nameTS = input("Please enter the name of TS:")
        FileTS = open(nameTS,"r")
        MysumTS = 0
        count1 = 0
        for time in FileTS:
            count1 += 1
            MysumTS += int(time)
        print("The Average time of TS is",MysumTS/count1)
    except:
        print("Error")



main()



        
