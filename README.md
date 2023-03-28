# Lags Legacy Java Kata

A kata to explore approval tests & characterization tests

## The LAGS program

ABEAS Corp is a little company with only one plane. ABEAS Corp’s customers ask for this plane to help them sometimes. They send rent request with start time, travel duration, and a price they will paid.

You could help ABEAS Corp by finding the best request combination to maximize gain.

Here's an example of a request file:
```
Id,    Start, Duration, Price
AF515, 0,     5,        100
CO5,   3,     7,        140
AF516, 5,     9,        70
BA01,  6,     9,        80
```
Here are the different combinations given the file above :
```
🟠 AF515                  = 100
🔴 AF515 + CO5
🟠 AF515 + AF516          = 170
🔴 AF515 + AF516 + BA01
🟢 AF515 + BA01           = 180
🟠 CO5                    = 140
🔴 CO5 + AF516
🔴 CO5 + BA01
🟠 AF516                  = 70
🟠 BA01                   = 80
```
The best possible revenue here is 180, selecting the 1st and 4th orders.

## Command Line Interface

First set the variable `LAGS_ORDER_FILE` to one of the files in the `data` folder.

* ` revenue` : outputs the best possible revenue with the given csv file.

* ` list_orders` : outputs a list of the orders in the csv file.

* ` add_order <id> <start> <duration> <price>` adds an order to the csv file.

* ` add_order <id>` remove the specified order from the csv file.


## Bug

With `test/data/orders.csv` as the orders file, the revenue should be 15801153.
With the current version, the revenue output is 158010148.

Fix the program so that the correct value is output.

## The Program is Slow

With `orders.csv` as the orders file, computing the revenue takes more than 1s.

Fix the program so that the value is output faster.

## The Program should show selected orders in the result

Currently the program only outputs the total revenue.
Change the program so that the selected orders are output as well as the revenue.

With `test/data/sample.csv` as the orders file, the output should be
```
Id         Revenue
AF515      100
C05         80
TOTAL      180
```



