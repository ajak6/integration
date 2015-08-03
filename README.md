# integration
integration of appdirect apis

This application provides sample integration of appdirect apis to manage applications

Apis handled:
Subscribe
Unsubscribe
Assign
Unassign

This project uses a lightweight ORM called genericDAO whose jar is not published on maven so you may need to add the jar to your local repository before compiling the project.
You can download the jar from here https://drive.google.com/open?id=0B8H2j1plkjhFU2lNMkJIVnN1YnM

This is a spring boot project, once the application is deployed on a platform use its url to add in appdirect integration endpoints
Access the application data of users and subscribers 
 http://ajak6.cfapps.io/

 subscription : http://http://ajak6.cfapps.io/subs?url={event} 
 
 unsubscribe: http://http://ajak6.cfapps.io/cancel?url={eventUrl}
 
 assign: http://ajak6.cfapps.io/assign?url={eventUrl}
 
 unassing:http://ajak6.cfapps.io/unassign?url={eventUrl}
