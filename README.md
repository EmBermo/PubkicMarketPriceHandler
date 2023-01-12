# TASKS
## A subscriber to listen to the market prices. 
You can assume the feed is coming from a messaging system where all you have to do is implement an interface, e.g. void onMessage(String message).
### Assumptions:
Instead of implement interface onMessage(String message). Interface onMessage(Message message) was selected due to it fits better with Java EE 7 Technologies. Specifically because to provide the solution Java Message Service API 2.0 was selected. 
```
Find the Subscriber class in the package es.bermo.emilio.subscriber 
#BUILD
Find a good build for the Subscriber in the class FXServlet, package es.bermo.emilio.rest.
```
Only EUR/USD, GBP/USD & EUR/JPY are considered valid pairs.

For similar reasons only consider valid Zone as "Europe/London".
## With an incoming price, process each with a margin 
(add commission) function, assume it is simply  -0.1% on bid, +0.1% on ask (subtract from bid, add to ask).
```
Find this functionality implemented into ClientPriceFromCSVString class in the package es.bermo.emilio.util 
#BUILD
Find a good build for the ClientPriceFromCSVString in the method onMessage of the class Subscriber.
```
## Publish the adjusted price to REST endpoint
The class FXServlet, package es.bermo.emilio.rest, pretends to be the rest endpoint. 
## Write a suitable test that gets the latest price
Search for testSubscriber5 in SubsciberTests. Receives multiple prices for the same ccy pair but only stores the most recent one, after commission is applied. 

The result is printed out on the test console depending on your locale sould be similar to:
```
ene 12, 2023 12:27:21 PM es.bermo.emilio.SubsciberTests testSubscriber5
INFORMACIÓN: EUR/USD
ene 12, 2023 12:27:21 PM es.bermo.emilio.SubsciberTests testSubscriber5
INFORMACIÓN: ClientPriceDto{id=106, currencies='EUR/USD', bid=0.99000, ask=1.32000, zonedDateTime=2020-06-01T12:01:01.001+01:00[Europe/London]}
ene 12, 2023 12:27:21 PM es.bermo.emilio.SubsciberTests testSubscriber5
INFORMACIÓN: GBP/USD
ene 12, 2023 12:27:21 PM es.bermo.emilio.SubsciberTests testSubscriber5
INFORMACIÓN: ClientPriceDto{id=109, currencies='GBP/USD', bid=1.12491, ask=1.38171, zonedDateTime=2020-06-01T12:01:02.100+01:00[Europe/London]}
ene 12, 2023 12:27:21 PM es.bermo.emilio.SubsciberTests testSubscriber5
INFORMACIÓN: EUR/JPY
ene 12, 2023 12:27:21 PM es.bermo.emilio.SubsciberTests testSubscriber5
INFORMACIÓN: ClientPriceDto{id=110, currencies='EUR/JPY', bid=107.649, ask=131.901, zonedDateTime=2020-06-01T12:01:02.110+01:00[Europe/London]}
```
# TROUBLESHOOTING
Mail me: emilio.bermo.vizcaya@gmail.com
