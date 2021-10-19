import random
import sys
import json
import names
from faker import Faker

def leadingZero(value):
    value = int(value)
    if(value < 10):
        return_value = "0" + str(value)
        return return_value
    return str(value)

def parseCards(jsonData):
    f = open("../../../cards.txt", "w")
    for pair in jsonData:
        writeString = pair['name'] + "," + pair['number'] + "\n"
        f.write(writeString)
    f.close()

def genMovies(movie_count):
    f = open("../../../movies.txt", "w")
    i = 0
    movie_list = []
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
        movie_list.append("movie"+str(i+1))
    f.close()
    return movie_list

def genCinemas(cinema_count, movie_list):
    f = open("../../../cinemas.txt", "w")
    i = 0
    cinemas_list = []
    for i in range(0, cinema_count):
        names = ["Event", "Hoyts", "Palace", "Reading", "Village"]
        name = names[random.randint(0,4)]
        random_movies = random.sample(movie_list, int(4*round(len(movie_list)/5)))
        writeString = "{},{},{}\n".format(name,Faker().unique.city(), ";".join(str(a) for a in random_movies))
        f.write(writeString)
        cinemas_list.append(writeString.strip())
    f.close()
    return cinemas_list

def genGiftCards(card_count):
    f = open("../../../giftcards.txt", "w")
    i = 0
    for i in range(0, card_count):
        writeString = "{},{}\n".format(random.randint(1000000000000000,9999999999999999), random.randint(0,1))
        f.write(writeString)

#Main Function
try:
    cardListPath = sys.argv[1]
    movie_count = int(sys.argv[2])
    cinema_count = int(sys.argv[3])
    card_count = int(sys.argv[4])
    with open(cardListPath) as f:
        jsonData = json.load(f)
        f.close()
    parseCards(jsonData)
    movie_list = genMovies(movie_count)
    cinemas_list = genCinemas(cinema_count, movie_list)
    #genSchedules(cinemas_list, movie_list)
    genGiftCards(card_count)
except Exception as e:
    print("Usage: python3 auto_generate.py [credit_card.json] [movie_count (int)] [cinema_count (int)], [giftCard_count (int)]")
    print(e)
    sys.exit()