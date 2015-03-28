# Playar  Geo-MMORPG Game Server Features #

Wherigo cartridge compatible.
Urwigo project compatible.
Team Playing.
Layar Vision compatible.
Upload your cartridge scenario and play at it.

# Key Concept of Playar server #

In front end, Playar server use Joomla 1.7 with community builder.

The server is made out of 3 parts:

## Web Server ##
Community, Access control, mission preparation on joomla server. Front end part of Playar is on an Apache Tomcat7 server. Render user interface game behavior result from Player Server to Layar. Render the mission control interface in layar.

(You can access this via the layer action menu on layar)

## The Main Server ##
Handles the request done via the front end, for everything not related to a specific player’s game. It will return available scenarios, related to the player position or play anywhere scenario. It handles also the request to start a new game, quitting a game, restoring a game state. The front end communicate request via an internal socket port. At startup, the server create and array of available game slot and assign a private unique internal socket port to each.
(You can set the maximum number of player in the property file of the server)

When the main server receive a start playing  scenario request, it check for the next empty game slot, start a virtual player server dedicated to the specific player and bind the slot socket port to the player server. Each players have is own private virtual server. We achieve this way high availlability of game behaviors for each players, all the time
The main server also handles communications between the game slot for teaming interaction between each game slot of a same team.

## The Player Server ##
Each time a new game is started, a virtual copy of it is launch in the java environment. At the start request, it receive the socket port to use privately with the player and start listening for players’ request on the dedicated socket port. At startup it load the requested cartridge, load the lua game table related to the cartridge, verify if the player as already started this game, restore player state if necessary, and compile by itself all the functions specific to that scenario and to this player. As long as the timeout preset is not reach, It remain idle in the memory, Always ready to answer to it’s unique players’ requests.


# What can we do with this server? #
The playar server can be used to drive role playing a game based on gps location. A lot of possible usage can be achieve with this server. Creating scenario for team building events. Viral Marketing campaing. Geo Enhanced TV shows or movies. Interactive touristical guide. Simulation for Emergency  disaster. Just build the scenario you want on Urwigo, upload and it’s ready to play.

> Easy …as 1 2 3 …!

# How to deploy playar server? #
As of writing those lines, playar server is still in pre-alpha release. But we’re close to have the first alpha release unveil. The original plan was to be at this state of development at the end of the year. But Layar Vision Challenge push us in an tremendous amount of work over the last 6 weeks. And having the server in this state of development put us closer than ever to achieve the goals we have at imajie with this server.

## Prerequisties: ##
Tomcat7
Apache server with jomla 1.7
Community builder joomla component installed

## Configuration: ##
There is 2 files involve in the configuration Both same name Constant.java located in org.imajie.server.web of imajiematch project and MatchsServer project.
Adjust the settings of those files according to your system.
On MainServerProject we have 2 files to build. MatchsServers.java and PlayerMatchServer.java.
(TODO Configuration need to be moved to a property file)

Place the build made out of those 2 files ( with librairies and classes) in the directory according to the setting in the Constant.java files.

Place the demo cartriges with the media content folder in the directory you chose in the Constants files.
Build and deploy on tomcat7 imajiematch project.

Run your MatchsServers build.

Start imajiematch application on tomcat

Put your layar api endpoint to http://yourhost:8080/IMAJIEMATCH/layar?developerID=YOUR_ID_SET_IN_CONSTANTS.JAVA

And enjoy.

## Left to be done on dev: ##
Callback return on player input request for clues.
Team Gaming behavior
Work on stability
Finish the integration on community builder.

(SOON How to build a cartridge compatible with Playar and Layar Vision)