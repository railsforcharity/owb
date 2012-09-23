Introduction
============
[Orphanage Without Borders (OWB)](http://orphanagewithoutborders.org) is a volunteer non-profit organization aiming to improve the live quality of children in orphanages and abandoned children.

- OWB is an application made in GWT + Java that aims to increase collaboration between NGOs and improve the efficiency of the work to the cause of orphan children.
- It would also be a portal for companies and individuals to participate or contribute in different ways (donations, volunteering, fund-raising).
- In addition, it would allow to share information about the state and needs of orphanages and previous experiences of projects that helped to improve the quality of orphanages.
- We intend to make the interface friendly and amusing by creating a simple game that would allow the users to gain points as they participate and see the status at all time of their contribution.
- The project is very challenging, not only technically but conceptually since behind there is a whole plan to allow orphanages to acquire minimum standards of life (something that every child deserve).
- The application is based on the Model-View-Presenter so we can separate the development work at the back-end from the graphical design. The project includes refining our current rough design and ideas, development and testing. Our philosophy is based on the enthusiasm of helping others. If you believe in the cause of helping orphan children and you love challenging technical projects we would love to work with you.

Installation
============
1. Clone this repository.
2. Install Eclipse
3. Open eclipse and browse to Help > Install New Software > Put the url http://dl.google.com/eclipse/plugin/<version>
   Replace the <version> with whatever eclipse version that you have.
4. Type 'Google' in the search term and select
   a. Developer Tools
   b. Google plugin for eclipse
   c. GWT designer for GPE
   d. SDKs
5. Now type in "GWT" and select
   a. GWT Designer Core
   b. GWT Designer Editor
   c. GWT Designer GPE
6. Install the plugins.
7. In eclipse, create a new webapplication project called "owb" and a package "com". You can remove the packages "com.client", "com.server" and "com.shared" that eclipse produces for you.
8. Now you import the directory workdir/owb into your project at owb/src. A bunch of packages will appear in your list. 
9. There is only one remaining thing to do before starting, linking the external libraries. Go to properties of the project and include files to the external jars of your library list at tmp/owb/libs.
10. You can get rid of the tmp directory since your files will be already in your project.

Demo
====
Currently deployed at http://karmagochi.appspot.com/

Design Description
==================
- The approach of this application is a Model-View-Presenter.
- The global picture is as follow:
 * client packages contain the front end.
 * server packages contain the back end.
 * shared packages are visible to the front and back end.

