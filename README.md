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
* 🔶 PrimeFaces 10.0.0 (icons)


### Environment
* 🔶 Java EE 8.0.1
* 🔶 Apache Maven 3.8.6
* 🔶 Payara Server 5.2021.10
* 🔶 Oracle Database 21c Express Edition
* 🔶 IntelliJ IDEA 2023.1.1


### Video

🚀 Coming Soon


### Requirements

1. Log into __Windows 11 Pro__ with a user that is a direct member of the Administrators group.
2. Download __Java 17.0.6 LTS__ and install to a local directory.
3. Download __Apache Maven 3.8.6__ and extract zip file to a local directory.
4. Download __Oracle Database 21c Express Edition__, extract zip file to a local directory and run __setup.exe__. 

    > #### IMPORTANT
    > 
    > After installation create a new schema, user account and user password - you will need this data in __DataSource.java__:
     
        package pl.szelag.config;
    
        import javax.annotation.sql.DataSourceDefinition;
        import javax.ejb.Singleton;
        import java.sql.Connection;
    
        @Singleton
        @DataSourceDefinition(
                name = "java:app/jdbc/FMSDescriptorDS",
                className = "oracle.jdbc.OracleDriver",
                url = "jdbc:oracle:thin:@localhost:1521:XE",
                user = "<USER>",
                password = "<PASSWORD>",
                isolationLevel = Connection.TRANSACTION_READ_COMMITTED)
        public class DataSource {}

6. Download __Payara Server 5.2021.10__ and extract zip file to a local directory.
7. In command line Windows, copy __ojdbc11.jar__ file from __Oracle Database 21c Express Edition__ to __Payara Server 5.2021.10__:

       copy %ORACLE_HOME%\jdbc\lib\ojdbc11.jar %PAYARA_HOME%\glassfish\domains\domain1\lib

7. Verify if __Oracle Database 21c Express Edition__ runs.
8. In command line Windows, start default domain __domain1__ on the __Payara Server 5.2021.10__ using __asadmin__ utility and __start-domain__ subcommand:

        %PAYARA_HOME%\bin\asadmin start-domain

10. Clone a Github Repository:
    * From Github Repository, click on __Clone__
    * Copy the clone URL
    * In command line Windows, move to partition __D:__ 
    * Use the __git clone__ command along with the copied URL:


          git clone https://github.com/AndrzejSzelag/fms.git
        
    
11. In command line Windows, move to __D:\fms__ folder, and run command: 

        mvn clean install

13. In command line Windows, move to __D:\fms\target__ folder and deploy __FMS-1.0.war__ file on __Payara Server 5.2021.10__.

        %PAYARA_HOME%\bin\asadmin deploy FMS-1.0.war

14. In __Oracle Database 21c Express Edition__ execute __data.sql__ file from __D:\fms\src\main\resources__ folder.
15. In your Web browser,
    * copy and paste URL:

          https://localhost:8181/FMS
            
    * click on __Advanced__
    * click on __Continue to localhost (unsafe)__

__You should see the main page application (tip: first photo)__!


### Description

👉 FMS is my first business application in the Java programming language, summarizing the knowledge I gained in the postgraduate degree __Modern business applications Java/Jakarta EE__ at __Lodz University of Technology__.

👉 This application was created with using [__Apache Maven__](https://maven.apache.org/) and [__Java EE__](https://www.oracle.com/java/technologies/java-ee-glance.html) technology with many different standards, such as __JPA__, __JTA__, __EJB__, __CDI__, __JSF__ etc. It also uses the advanced tools, such as [__Oracle Database XE__](https://www.oracle.com/pl/database/technologies/appdev/xe.html) and [__Payara Server__](https://www.payara.fish/downloads/payara-platform-community-edition/).
