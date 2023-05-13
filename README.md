# FMS (Fault Management System)
### IT System for Managing Control System Faults on the Mining Railways

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

🚀 ... 


### Requirements

1. Log into __Windows 11 Pro__ with a user that is a direct member of the Administrators group.
2. Download __Java 17.0.6 LTS__ and install to a local directory.
3. Download __Apache Maven 3.8.6__ and extract zip file to a local directory.
4. Download __Oracle Database 21c Express Edition__, extract zip file to a local directory and run setup.exe. After instalation create a new schema, user account and password - you will need this data in the __DataSource.java__ class:

    ![DataSource.java](DataSource.png)

6. Download __Payara Server 5.2021.10__ and extract zip file to a local directory.

    > #### IMPORTANT
    > Copy __ojdbc11.jar__ file from __Oracle Database 21c Express Edition__ (__lib__ folder) to __Payara Server__ (__lib__ folder)!
    > 
    > #### EXAMPLE
    > ![cmd1.png](cmd1.png)

7. Verify if __Oracle Database 21c Express Edition__ runs.
8. Verify if __Payara Server 5.2021.10__ runs.
9. Clone a Github Repository:
   * From Github Repository, click on Clone
   * Copy the clone URL
   * In command line Windows, move to partition __D:__ 
   * Use the git clone command along with the copied URL:

    ![cmd2.java](cmd2.png)

11. From __fms__ folder use __mvn clean install__ command.
12. From __fms\target__ folder deploy __FMS-1.0.war__ file to __Payara Server 5.2021.10__ (you can use __asadmin deploy__ command).

    > #### IMPORTANT
    > In __Oracle Database 21c Express Edition__ run __data.sql__ file from __\fms\src\main\resources__ folder!

13. In your Web browser use URL: __https://localhost:8181/FMS__.

### Description

...
