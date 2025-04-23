# FMS (Fault Management System)

[<img alt="Java EE" src="https://img.shields.io/badge/Java EE-8.0.1-007396.svg?logo=javaee">](https://www.oracle.com/java/technologies/java-ee-glance.html)
[<img alt="Maven" src="https://img.shields.io/badge/Maven-3.8.6-C71A36.svg?logo=apachemaven">](https://maven.apache.org/)
[<img alt="Oracle Database" src="https://img.shields.io/badge/Oracle Database-21c Express Edition-F80000.svg?logo=oracle"/>](https://www.oracle.com/database/technologies/xe-downloads.html)
[<img alt="Payara Server" src="https://img.shields.io/badge/Payara Server-5.2021.10-FF7300.svg?logo=payara">](https://www.payara.fish/)
[<img alt="Boot Faces" src="https://img.shields.io/badge/Boot Faces-1.5.0-7952B3.svg?logo=bootstrap">](https://www.bootsfaces.net/index.jsf)
[<img alt="Lombok" src="https://img.shields.io/badge/Lombok-1.18.26-DA525D.svg?logo=java">](https://projectlombok.org/)

**Fault Management System** is an enterprise-grade application designed for managing faults in railway traffic control systems within a mining environment. The system is built using the **MVC (Model View Controller)** architectural pattern, which organises code by separating it into three interconnected components. The system utilises the structural design pattern **Facade** that simplifies access to complex operations and improves maintainability by providing a unified interface to a set of interfaces in a subsystem. Built using **Java Enterprise Edition** technologies, it leverages **JPA (Java Persistence API)** for data persistence, **JTA (Java Transaction API)** for transaction management, and **EJBs (Enterprise JavaBeans)** to handle business logic. The application runs on the **Payara Server** and integrates with an **Oracle Database**. The project is built with **Apache Maven** and makes use of the **Lombok** Java library to reduce boilerplate code.

![url3.java](url3.png "Fault Management System")

### Tech Stack
* 🔶 Java 17.0.6 LTS
* 🔶 Java Persistence API 2.2
* 🔶 Java Transaction API 1.2
* 🔶 Context and Dependency Injection 2.0
* 🔶 Enterprise JavaBeans 3.2
* 🔶 JavaServer Faces 2.3
* 🔶 Lombok 1.18.26
* 🔶 BootsFaces 1.5.0
* 🔶 PrimeFaces 10.0.0 (icons)


### Environment
* 🔶 Java EE 8.0.1
* 🔶 Apache Maven 3.8.6
* 🔶 Payara Server 5.2021.10
* 🔶 Oracle Database 21c Express Edition
* 🔶 IntelliJ IDEA 2023.1.1


### Video

🚀 [Management of user accounts by Administrator](https://youtu.be/eArxW2pi3XQ)

🚀 [Fault management by Supervision and Electrician](https://youtu.be/tnAVQGW4tXk)


### Requirements & How to run

1. Log into __Windows 11 Pro__ with a user that is a direct member of the Administrators group.
2. Download the __Java 17.0.6 LTS__ (_x64 MSI Installer_) and install to a local directory.
3. Download the __Apache Maven 3.8.6__ (_Binary zip archive_) and extract zip file to a local directory.
4. Download the __Oracle Database 21c Express Edition__ (_.zip_), extract zip file to a local directory and run __setup.exe__. After installation:
    * verify if __Oracle Database 21c Express Edition__ runs,
    * create a new __database user__ (e.g. __system__) and __database password__ (e.g. __pa$$w0rd__) - you will need this data in __DataSource.java__ before starting to use the __FMS__ application:

   
            // DataSource.java 
            package pl.szelag.config;
    
            import javax.annotation.sql.DataSourceDefinition;
            import javax.ejb.Singleton;
            import java.sql.Connection;
    
            @Singleton
            @DataSourceDefinition(
                     name = "java:app/jdbc/FMSDescriptorDS",
                     className = "oracle.jdbc.OracleDriver",
                     url = "jdbc:oracle:thin:@localhost:1521:XE",
                     user = "system",
                     password = "pa$$w0rd",
                     isolationLevel = Connection.TRANSACTION_READ_COMMITTED)
            public class DataSource {
            }

6. Download the __Payara Server 5.2021.10__ (_.zip_) and extract zip file to a local directory.
7. In command line Windows, copy file __ojdbc11.jar__ from the __Oracle Database 21c Express Edition__ to the __Payara Server 5.2021.10__:

       copy %ORACLE_HOME%\jdbc\lib\ojdbc11.jar %PAYARA_HOME%\glassfish\domains\domain1\lib

8. In command line Windows, start default domain on the __Payara Server 5.2021.10__:

        %PAYARA_HOME%\bin\asadmin start-domain

10. Clone a Github Repository:
    * From Github Repository, click on __Clone__
    * Copy the clone URL
    * In command line Windows, go to partition __D:__ 
    * Use the __git clone__ command along with the copied URL:


          git clone https://github.com/AndrzejSzelag/fms.git
        
    
11. In command line Windows, go to folder __D:\fms__, and run command: 

        mvn clean install
        
12. In command line Windows, go to folder __D:\fms\target__ and deploy file __FMS-1.0.war__ to the __Payara Server 5.2021.10__.

        %PAYARA_HOME%\bin\asadmin deploy FMS-1.0.war

13. Load test data from the script __D:\fms\src\main\resources\data.sql__ to the __Oracle Database 21c Express Edition__ with usining __Oracle SQL Developer__, __SQL Plus__ etc.

14. In your Web browser,
    * paste a URL:

          https://localhost:8181/FMS
            
    * click on __Advanced__
    * click on __Continue to localhost (unsafe)__


#### You should see the home page of the FMS application (hint: first photo) and log in with using the following logins and password.

> Logins for confirmed and activated test users: 
> 
>   * __AndrzejSzelag__ (administrator)
>   * __TomaszDominiak__ (supervision)
>   * __ArekStolecki__ (electrician)
> 
> Default __password__ for all test users: __12345678__
