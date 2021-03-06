import random
import os
import sys
import json
import names
import datetime
import math
import copy
import traceback
from faker import Faker


def leadingZero(value):
    value = int(value)
    if (value < 10):
        return_value = "0" + str(value)
        return return_value
    return str(value)


def parseCards(jsonData):
    f = open("../../../cards.txt", "w")
    return_string = []
    i = 0
    for pair in jsonData:
        writeString = pair['name'] + "," + pair['number'] + "\n"
        return_string.append(pair['number'])
        f.write(writeString)
    f.close()
    random.shuffle(return_string)
    return return_string


def genMovies(movie_count):
    f = open("../../../movies.txt", "w")
    i = 0
    movie_list = []
    for i in range(0, movie_count):
        ratings = ["G", "PG", "M", "MA15+", "R18+"]
        rating = ratings[random.randint(0, 4)]
        releaseDate = leadingZero(str(random.randint(1, 31))) + "/" + leadingZero(
            str(random.randint(1, 12))) + "/" + str(random.randint(2021, 2022))
        j = random.randint(3, 8)
        k = 0
        cast = ""
        while k < j:
            cast += names.get_full_name() + ";"
            k += 1
        cast += names.get_full_name()

        # writeString = "{},synopsis,{},{},{},schedule,{},{},{},{},{},{},{},{}\n".format(
        writeString = "{},{},synopsis,{},{},{}\n".format(
            i + 1, "movie" + str(i + 1), rating, releaseDate, cast)
        f.write(writeString)
        movie_list.append(i + 1)
    f.close()
    return movie_list


def genCinemas(cinema_count, movie_list):
    f = open("../../../cinemas.txt", "w")
    i = 0
    cinemas_list = []
    for i in range(0, cinema_count):
        names = ["Event", "Hoyts", "Palace", "Reading", "Village"]
        name = names[random.randint(0, 4)]

        # private final int m_id;
        # private final int c_id;
        # private int f_seatsCapacity;  //need to gen this
        # private int m_seatsCapacity;  //need to gen this
        # private int r_seatsCapacity;  //need to gen this
        # string day
        # localtime time
        # string screensize
        # string price

        seats = ["30", "40", "50"]
        days = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"]
        times = [x for x in range(10, 23) if x % 2 == 0]

        random_movies = random.sample(movie_list, int(round(1 * len(movie_list) / 5)))

        screenSizes = ["Bronze", "Silver", "Gold"]
        instances = []
        daytime = []
        for movie in random_movies:
            j = 0
            day = random.sample(days, 3)
            while j < 3:
                valid = False
                while not valid:
                    time = (day[j], random.choice(times))
                    if time in daytime:
                        continue
                    else:
                        daytime.append(time)
                        valid = True
                seats1 = random.choice(seats)
                seats2 = random.choice(seats)
                seats3 = random.choice(seats)
                instance = "{}:{}:{}:{}:{}:{}:{}:{}:{}:{}:{}:{}".format(movie, i+1,
                                                   seats1,
                                                   seats1,
                                                   seats2,
												   seats2,
                                                   seats3,
                                                   seats3,
                                                   time[0], time[1], random.choice(screenSizes),
                                                   '{0:.2f}'.format(random.randint(5, 15)))
                instances.append(instance)
                j += 1
        current_city = Faker().unique.city()
        writeString = "{},{},{},{}\n".format(i + 1, name, current_city, ";".join(instances))
        # writeString = "{},{},{}\n".format(name,current_city, ";".join(str(a) for a in random_movies))
        f.write(writeString)
        cinemas_list.append([i + 1, name, current_city, random_movies])
    f.close()
    return cinemas_list


def genGiftCards(card_count):
    f = open("../../../giftcards.txt", "w")
    i = 0
    for i in range(0, card_count):
        writeString = "{}GC,{}\n".format(random.randint(1000000000000000, 9999999999999999), random.randint(0, 1))
        f.write(writeString)


def genSchedule(cinemas_list):
    open_time = 9
    close_time = 21
    total_hours = close_time - open_time
    days = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"]
    len_days = len(days)
    total_hours_max = len_days * total_hours
    movie_runtime = 2
    min_break = 0.5
    full_schedule = []
    for cinema in cinemas_list:
        current_hours_left = total_hours_max
        current_days = copy.copy(days)
        final_schedule = []
        current_hours = open_time
        # ARRAY POS
        # 0: ID, 1: NAME, 2: LOCATION, 3: MOIES SHOWING (ARRAY)
        i = 0
        while len(current_days) > 0:
            while current_hours_left > 0:
                current_movie = random.sample(cinema[3], 1)
                minutes, hour = math.modf(current_hours)
                set_time = datetime.time(int(hour), int((minutes * 60)))
                final_schedule.append([current_movie[0], days[i], str(set_time)])
                current_hours += movie_runtime + min_break
                current_hours_left -= (current_hours)
            current_days.pop(0)
            current_hours_left = total_hours_max
            current_hours = open_time
            i += 1
        full_schedule.append([cinema[0], final_schedule])
    f = open("../../../schedules.txt", "w")
    i = 0
    for i in range(0, len(full_schedule)):
        f.write(str(full_schedule[i]))
    f.close()
    return


def genCustomers(cards):
    output_string = "admin,password,{};{},1;2;3;4".format(cards[0], cards[1])
    f = open("../../../customers.txt", "w")
    f.write(str(output_string))
    f.close()


# Main Function
try:
    cardListPath = sys.argv[1]
    movie_count = int(sys.argv[2])
    cinema_count = int(sys.argv[3])
    card_count = int(sys.argv[4])
    with open(cardListPath) as f:
        jsonData = json.load(f)
        f.close()
    cards_list = parseCards(jsonData)
    movie_list = genMovies(movie_count)
    cinemas_list = genCinemas(cinema_count, movie_list)
    genSchedule(cinemas_list)
    genGiftCards(card_count)
    genCustomers(cards_list)
    f = open("../../../managerreport.txt", "w")
    f.close()

except Exception as e:
    print(traceback.format_exc())
    print(
        "Usage: python3 auto_generate.py [credit_card.json] [movie_count (int)] [cinema_count (int)], [giftCard_count (int)]")
    print(e)
    sys.exit()