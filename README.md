# < BATMAN AML >
> *Anti Money Laundering and fraud detection in the world of Batman universe*
 
**Disclaimer:** I do not work with AML regulations, my place of employment is 
irrelevant for this project as I only use the publicly-available information 
that I found on [investopedia](www.investopedia.com). Relevant articles are listed
in the GLOSSARY.md file. 
This project is meant to stay in the proof-of-concept or case-study stage, and 
should not be used in a production environment.

---

Let's talk about company shares and owners. Here's a [video](https://www.youtube.com/watch?v=_ktXH0FpDSw)
on why this topic is important. One moment you're a CEO of a worlds largest company, 
another - you're unemployed and humiliated. Poor guy. Should have gathered more info 
on trusts and funds that were buying his shares. Luckily, **< BATMAN AML >** is here
so that those situations could be avoided in the future. 

In business world there are a lot of complications and nuances. 
Luckily, in the world of Batman it's a lot easier. We have `Person`. A regular, 
physical person, that may control shares in some businesses. And we have businesses - hotels, 
companies, factories, trusts, funds, and whatnot. We do not care, to us, software 
developers those all are just a `LegalEntity`, that may still control some shares 
in other businesses, but must have a `Person` as owner.

Sometimes finding the real owner is easy. Like [here](https://www.youtube.com/watch?v=igTi_UM-umY) 
we see Bruce Wayne spotting an owner of the hotel (sneakingly disgusted as a waiter), 
and purchasing city largest hotel, by giving him a check. Amazing, master detective at work.
Sometimes, real owner may be hidden behind a series of shell companies, and to determine
his share we need to analyze shares of all the proxy companies. 

Let's consider the following scenario: 

![Example 1](https://github.com/abar193/batman-aml/blob/master/images/1.png?raw=true)

Who controls company "C3"? On a first glance we see that a person named P3 controls
10% of shares, while 50% and 40% of shares are owned by companies C2 and C4 respectively. 
We know that P3 is a "green listed" person that is safe to work with. But let's go further!
Company C2 is owned by a "green listed" P2 by 80% and just 20% by P1. P1 is a "red-listed"
person, but it only controls 10% of C3. (C2 share is 50%, and P1 share in C2 is 20%, so he owns `(0.5 * 0.2 * 100%)` of 
shares in C3). 

Just 10% is not that much, huh? Can we start working C3 already? But wait, who controls C4? 
We dig two more steps further before discovering that C4 is owned by C1, while C1 belongs to P1!
Which means, that our dangerous and red-listed P1 owns `1 * 1 * 0.4 + 0.5 * 0.2` = 50% of shares
in C3! He is the beneficial owner of this company! And now that you know who you are 
dealing with, you can decide on how to proceed.  

---

I hope that the problem is more or less clear now. We need to build a database, 
where we could upload all the info on the companies and their owners that we have 
(it is assumed that the information was obtained in a legal way and that you have
permission to work with it), and have it calculate: which companies does this `Person` 
controls? Who are the real owners for this `LegalEntity`? 

Normally, this kind of task would require graph databases, along with the certified 
people that know how to work with them. Luckily, MicroStream seems to be just the 
tool for the job. We define our graph structure in a package `me.mrabar.aml.data.graph`. 
It is as straightforward as it gets from now. Notice that `Person` and `LegalEntity` 
share same superclass, but are different - no one may own a Person (that's illegal), 
and we do not have any listing status on a company. They form nodes of a graph. 
For edges, we have a `OwnershipEdge`, describing: who owns how much of what. `OwnershipEdge` is 
stored both on owner and on a business owned, making this a bidirectional acyclic graph, 
I suppose? Bo-o-oring! But yes, in Batman universe Cross Holding may not happen, 
so we don't worry about cycles in a graph. 


We don't bother much with Root as well - just a custom class that holds `Map<String, Lazy<>>`
for all the `Persons` and `LegalEntities` we registered. From root we can check 
whenever any graph Node is registered, and access the node to start performing analysis. 

Since our software is intended to be run in banks that operate only during daily business 
hours (or by an eccentric billionaire, who punches criminals during the nights and sleeps 
during the day) we don't really bother with how fast it starts and how exactly 
will we load the data. It might be stored in a relational database, and we just 
pull it into our MicroStream graph. Our engine should keep operating during the 
working hours, then we might as well drop the storage completely, and reload 
updated information from scratch. 

As long as our engine operates fast enough during the day - we do not care. Speaking of,
check out `BatEngine` to learn what kind of reports we can produce. By running DFS 
algorithm we calculate ownership of each person among every single path from them to 
a company, and... Well, that's it! We create, analyze and store complicated graph 
of relationship between companies, and we're doing it with Java classes and structures. 
It that's not a miracle, I don't know what is. 

A simple addition of Helidon - and our BatEngine is serving its responses over 
the REST-ful interface. I suppose, that porting the code to GraalVM won't be 
a challenge either.  

---

Unfortunately, I'm running out of time. I probably should have started earlier than 
2 days before final deadline, but personally I'm satisfied with my results. 

Final submission will be in a commit with message "Final submission for hackaton". 
I still want to add test data generation, and measure performane of a project, and 
I'll try to upload the results to the repo, but if you are a member of jury, then 
I'll ask you to ignore those updates.   