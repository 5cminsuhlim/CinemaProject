import random
import sys
import json
import names
import datetime
import math
import copy
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
        releaseDate = leadingZero(str(random.randint(1,31))) + "/" + leadingZero(str(random.randint(1,12))) + "/" + str(random.randint(2021,2022)) ###ADD DAY AS WELL######
        j = random.randint(3,8)
        k = 0
        cast = ""
        while k < j:
            cast += names.get_full_name()+";"
            k += 1
        cast += names.get_full_name()
        screenSizes = ["Bronze", "Silver", "Gold"]
        screenSize = screenSizes[random.randint(0,2)]
        front = random.randint(0,100)
        m = random.randint(0,100)
        r = random.randint(0,100)
        price = '{0:.2f}'.format(random.randint(10,20) + 0.05*random.randint(0,19))
        #writeString = "{},synopsis,{},{},{},schedule,{},{},{},{},{},{},{},{}\n".format(
        writeString = "{},{},synopsis,{},{},{},schedule,{},{},{},{},{},{},{},{}\n".format(
        i+1, "movie"+str(i+1),rating,releaseDate,cast,screenSize,front,100-front,m,100-m,r,100-r,price)
        f.write(writeString)
        movie_list.append(i+1)
    f.close()
    return movie_list

def genCinemas(cinema_count, movie_list):
    f = open("../../../cinemas.txt", "w")
    i = 0
    cinemas_list = []
    for i in range(0, cinema_count):
        names = ["Event", "Hoyts", "Palace", "Reading", "Village"]
        name = names[random.randint(0,4)]
        if (len(movie_list) > 1):
            random_movies = random.sample(movie_list, int(4*round(len(movie_list)/5)))
        else:
            random_movies = movie_list
        current_city = Faker().unique.city()
        writeString = "{},{},{},{}\n".format(i+1, name,current_city, ";".join(str(a) for a in random_movies))
        # writeString = "{},{},{}\n".format(name,current_city, ";".join(str(a) for a in random_movies))
        f.write(writeString)
        cinemas_list.append([i+1, name,current_city, random_movies])
    f.close()
    return cinemas_list

def genGiftCards(card_count):
    f = open("../../../giftcards.txt", "w")
    i = 0
    for i in range(0, card_count):
        writeString = "{}GC,{}\n".format(random.randint(1000000000000000,9999999999999999), random.randint(0,1))
        f.write(writeString)

def genSchedule(cinemas_list):
    open_time = 9
    close_time = 21
    total_hours = close_time-open_time
    days = ["Monday" , "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"]
    len_days = len(days)
    total_hours_max = len_days*total_hours
    movie_runtime = 2
    min_break = 0.5
    full_schedule = []
    for cinema in cinemas_list:
        current_hours_left = total_hours_max
        current_days = copy.copy(days)
        final_schedule = []
        current_hours = open_time
        #ARRAY POS
        #0: ID, 1: NAME, 2: LOCATION, 3: MOIES SHOWING (ARRAY)
        while len(current_days) > 0:
            while current_hours_left > 0:
                current_movie = random.sample(cinema[3], 1)
                minutes, hour  = math.modf(current_hours)
                set_time = datetime.time(int(hour), int((minutes*60)))
                final_schedule.append([current_movie[0], days[0], str(set_time)])
                current_hours += movie_runtime+min_break
                current_hours_left -= (current_hours)
            current_days.pop(0)
            current_hours_left = total_hours_max
            current_hours = 0
        full_schedule.append([cinema[0], final_schedule])
    f = open("../../../schedules.txt", "w")
    i = 0
    for i in range(0, len(full_schedule)):
        f.write(str(full_schedule[i]))
    f.close()
    return

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
    genSchedule(cinemas_list)
    genGiftCards(card_count)
except Exception as e:
    print("Usage: python3 auto_generate.py [credit_card.json] [movie_count (int)] [cinema_count (int)], [giftCard_count (int)]")
    sys.exit()