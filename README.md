
Benchmark - Protcol Buffers 3 vs Jackson JSON
------

This is a simple comparison of performance and size between 
Jackson (w/ Afterburner) and Protocol Buffers version 3.


I came across an article comparing the two, showing PB with worse performance, in
some tests nearly 2-times slower. 
 
My initial reaction was: this can't be correct.  When browsing the benchmark's
source code I noticed the PB version was adversely impacted by converting
JodaTime to / from string, while Jackson was using a more efficient
serialization.  The profiler showed JodaTime conversion was taking up most of
the time, making PB look far worse.

Numbers from my development laptop:

    JDK 8
    Macbook Pro Retina (mid-2012)
    2.7 GHz Intel Core i7
    16 GB 1600 MHz DDR3

    loops = 10
    iters = 10,000

    times in milliseconds
    sizes in bytes (protobuf) and characters (jackson)

                              test      min      max      avg
    -----------------------------------------------------------------

    with 0 children
             jackson serialization      7.3     71.3     19.1
           jackson deserialization     13.6     95.6     27.2
         afterburner serialization      8.0     36.6     14.5
       afterburner deserialization     13.5     15.6     13.9
            protobuf serialization      1.2     12.4      3.1
          protobuf deserialization      1.7     12.5      4.0

    encoded sizes:
                           jackson   104
                       afterburner   104
                          protobuf   26


    with 8 children
             jackson serialization     53.3     78.7     62.5
           jackson deserialization    110.1    130.9    114.9
         afterburner serialization     53.5     57.5     54.7
       afterburner deserialization    109.8    115.4    112.7
            protobuf serialization     10.2     82.1     19.0
          protobuf deserialization     19.3     35.2     25.1

    encoded sizes:
                           jackson   949
                       afterburner   949
                          protobuf   258


    with 16 children
             jackson serialization    102.2    111.3    105.8
           jackson deserialization    210.4    218.6    214.7
         afterburner serialization    102.2    108.5    105.6
       afterburner deserialization    210.8    229.2    217.2
            protobuf serialization     19.5     23.2     20.3
          protobuf deserialization     37.6     47.6     40.3

    encoded sizes:
                           jackson   1804
                       afterburner   1804
                          protobuf   497


    with 32 children
             jackson serialization    198.2    211.6    204.0
           jackson deserialization    403.9    427.1    416.1
         afterburner serialization    198.4    244.4    212.8
       afterburner deserialization    407.0    439.5    420.2
            protobuf serialization     38.0     41.4     39.5
          protobuf deserialization     73.8     84.0     76.9

    encoded sizes:
                           jackson   3516
                       afterburner   3516
                          protobuf   977

Example Objects
-----

JSON-encoded object with 1 child (indented for readability): 207 characters

    {
        "num64": 9223372036854775807,
        "flag": true,
        "str": "parent",
        "type": "FOO",
        "children": [
            {
                "num64": 9223372036854775807,
                "flag": true,
                "str": "child 1",
                "type": "FOO",
                "children": null,
                "num32": 2147483647
            }
        ],
        "num32": 2147483647
    }

Protobuf-encoded object with 1 child (byte array): 55 bytes

    { 
      \x08, \x01, \x18, \xff, \xff, \xff, \xff, \x07, \x20, \xff, \xff, \xff, \xff, \xff, \xff, \xff, \xff, \x7f, \x2a,
      \x06, 'p', 'a', 'r', 'e', 'n', 't', \x52, \x1b, \x08, \x01, \x18, \xff, \xff, \xff, \xff, \x07, \x20, \xff, \xff,
      \xff, \xff, \xff, \xff, \xff, \xff, \x7f, \x2a, \x07, 'c', 'h', 'i', 'l', 'd', ' ', '1' 
    }

References:

Article
http://technicalrex.com/2015/02/27/performance-playground-jackson-vs-protocol-buffers-part-2/
       
Source
http://github.com/egillespie/performance-playground 


