import random
import sys
import json
import names
import barnum

def leadingZero(value):
    value = int(value)
    if(value < 10):
        return_value = "0" + str(value)
        return return_value
    return str(value)

def parseCards(jsonData):
    f = open("cards.txt", "w")
    for pair in jsonData:
        writeString = pair['name'] + "," + pair['number'] + "\n"
        f.write(writeString)
    f.close()

def genMovies(movie_count):
    f = open("movies.txt", "w")
    i = 0
    for i in range(0, movie_count):
        ratings = ["G", "PG", "M", "MA15+", "R18+"]
        rating = ratings[random.randint(0,4)]
        releaseDate = leadingZero(str(random.randint(1,12))) + "/" + str(random.randint(2021,2022))
        j = random.randint(3,8)
        k = 0
        cast = ""
        while k < j:
            cast += names.get_full_name()+";"
            k += 1
        cast += names.get_full_name()
        screenSizes = ["bronze", "silver", "gold"]
        screenSize = screenSizes[random.randint(0,2)]
        front = random.randint(0,100)
        m = random.randint(0,100)
        r = random.randint(0,100)
        price = '{0:.2f}'.format(random.randint(10,20) + 0.05*random.randint(0,19))
        writeString = "{},synopsis,{},{},{},schedule,{},{},{},{},{},{},{},{}\n".format(
        "movie"+str(i+1),rating,releaseDate,cast,screenSize,front,100-front,m,100-m,r,100-r,price)
        f.write(writeString)
    f.close()

def genCinemas(cinema_count):
    f = open("cinemas.txt", "w")
    for i in range(0, cinema_count):
        names = ["Event", "Hoyts", "Palace", "Reading", "Village"]
        name = names[random.randint(0,4)]
        writeString = "{},{}\n".format(name,barnum.create_city_state_zip()[1])
        f.write(writeString)
    f.close()

def genGiftCards(card_count):
    f = open("giftcards.txt", "w")
    for i in range(0, card_count):
        writeString = "{},{}\n".format(random.randint(1000000000000000,9999999999999999), random.randint(0,1))
        f.write(writeString)

#Main Function
try:
    path = sys.argv[1]
    movie_count = int(sys.argv[2])
    cinema_count = int(sys.argv[3])
    card_count = int(sys.argv[4])
    with open(path) as f:
        jsonData = json.load(f)
        f.close()
        parseCards(jsonData)
        genMovies(movie_count)
        genCinemas(cinema_count)
        genGiftCards(card_count)
except Exception:
    print("Usage: python3 auto_generate.py [credit_card.json] [movie_count (int)] [cinema_count (int)], [giftCard_count (int)]")
    sys.exit()