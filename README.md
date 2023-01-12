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

The result is printed out on the test console.
