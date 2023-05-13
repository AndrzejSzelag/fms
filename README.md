# FMS (Fault Management System)
### IT System for Managing Control System Faults on the Mining Railways

![url3.java](url3.png)

### Tech Stack
* 🔶 Java 17.0.6 LTS
* 🔶 Java Persistence API 2.2
* 🔶 Java Transaction API 1.2
* 🔶 Context and Dependency Injection 2.0
* 🔶 Enterprise JavaBeans 3.2
* 🔶 JavaServer Faces 2.3
* 🔶 Lombok 1.18.26
* 🔶 BootsFaces 1.5.0
* 🔶 PrimeFaces 10.0.0


### Environment
* 🔶 Java EE 8.0.1
* 🔶 Apache Maven 3.8.6
* 🔶 Payara Server 5.2021.10
* 🔶 Oracle Database 21c Express Edition
* 🔶 Apache NetBeans IDE 17


### Video

🚀 Comming Soon


### Requirements

1. Log into __Windows 11 Pro__ with a user that is a direct member of the Administrators group.
2. Download __Java 17.0.6 LTS__ and install to a local directory (e.g. __C:\Politechnika\jdk-17__).
3. Download __Apache Maven 3.8.6__ and extract zip file to a local directory (e.g. __C:\Politechnika\apache-maven-3.8.6__).
4. Download __Oracle Database 21c Express Edition__, extract zip file to a local directory (e.g. __C:\app\Andrzej\product\21c__) and run __setup.exe__. 

    > #### IMPORTANT
    > 
    > After installation create a new schema, user account and password - you will need this data in the __DataSource.java__ class:
    > 
    > ![DataSource.png](DataSource.png)

6. Download __Payara Server 5.2021.10__ and extract zip file to a local directory  (e.g. __C:\Politechnika\payara__).
7. Copy __ojdbc11.jar__ file from __Oracle Database 21c Express Edition__ (__lib__ folder) to __Payara Server__ (__lib__ folder).

    > Example
    > 
    > ![cmd1.png](cmd1.png)

7. Verify if __Oracle Database 21c Express Edition__ runs.
8. Start default domain on the __Payara Server 5.2021.10__ using __asadmin__ utility with __start-domain__ subcommand.

    > Example
    > 
    > ![cmd4.png](cmd4.png)

10. Clone a Github Repository:
    * From Github Repository, click on __Clone__
    * Copy the clone URL (__https://github.com/AndrzejSzelag/fms.git__)
    * In command line Windows, move to partition __D:__ 
    * Use the __git clone__ command along with the copied URL.
    
11. In command line Windows, move to __D:\fms__ folder, and run __mvn clean install__ command.
12. Deploy __D:\fms\target\FMS-1.0.war__ file to __Payara Server 5.2021.10__:

    > Example
    > 
    > ![cmd3.png](cmd3.png)

14. In __Oracle Database 21c Express Edition__ execute __data.sql__ file from __D:\fms\src\main\resources__ FMS application folder.
15. In your Web browser use URL: __https://localhost:8181/FMS__.
16. Click on button __Advanced__.

![url1.java](url1.png)

17. Click on link __Continue to local (unsafe)__. You should see the main page FMS application (tip: first photo).

![url2.java](url2.png)

### Description

Comming Soon
