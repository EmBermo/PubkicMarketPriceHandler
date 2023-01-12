# TASKS
## A subscriber to listen to the market prices. 
You can assume the feed is coming from a messaging system where all you have to do is implement an interface, e.g. void onMessage(String message).
### Assumption:
Instead of implement interface onMessage(String message). Interface onMessage(Message message) was selected due to it fits better with Java EE 7 Technologies. Specifically, to provide the solution, Java Message Service API 2.0 was used. 
```
Find the Subscriber class in the package es.bermo.emilio.subscriber 
#BUILD
Find a good build for the Subscriber in the class FXServlet, package es.bermo.emilio.rest.
```
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
Look for the testSubscriber5 into the SubsciberTests. It receives several prices for the same ccy pair but only stores the most recent one, after applying the commission. The result is printed out to the test console.
