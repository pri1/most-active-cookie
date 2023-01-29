

## **Most Active Cookie**

Given a cookie log file in the following format:

    cookie,timestamp
    AtY0laUfhglK3lC7,2018-12-09T14:19:00+00:00
    SAZuXPGUrfbcn5UA,2018-12-09T10:13:00+00:00
    5UAVanZf6UtGyKVS,2018-12-09T07:25:00+00:00
    AtY0laUfhglK3lC7,2018-12-09T06:19:00+00:00
    SAZuXPGUrfbcn5UA,2018-12-08T22:03:00+00:00
    4sMM2LxV07bPJzwf,2018-12-08T21:30:00+00:00
    fbcn5UAVanZf6UtG,2018-12-08T09:30:00+00:00
    4sMM2LxV07bPJzwf,2018-12-07T23:30:00+00:00

Process the log file and return the most active cookie for a specific day. Please include a -f parameter for the filename to process and a -d parameter to
specify the date.
Execute program like this to obtain the most active cookie for 9th Dec 2018.

    $ ./java -jar cookie-finder-1.0-standalone.jar -f cookie_log.csv -d 2018-12-09

Help

    $ ./java -jar cookie-finder-1.0-standalone.jar --help

And it would write to stdout:

    AtY0laUfhglK3lC7

If multiple cookies meet that criteria, returns all of them on separate lines.

## **How to build**

You can build the project using:

    ./gradlew build

Afterwards, you will find executable file at location

    ./build/libs/cookie-finder-1.0-standalone.jar

You can execute the file using



    java -jar cookie-finder-1.0-standalone.jar -f /path/to/cookie.csv -d 2018-12-09

