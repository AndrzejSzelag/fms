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

1. Log into Windows 11 Pro with a user that is a direct member of the Administrators group.
2. Download __Java 17.0.6 LTS__ and install to a local directory.
3. Download __Apache Maven 3.8.6__ and extract zip file to a local directory.
4. Download __Oracle Database 21c Express Edition__, extract zip file to a local directory and run setup.exe. After instalation create schema, user account and password - you will need this data in the DataSource.java class:


package pl.szelag.config;

import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Singleton;
import java.sql.Connection;

`code`@Singleton
@DataSourceDefinition(
        name = "java:app/jdbc/FMSDescriptorDS",
        className = "oracle.jdbc.OracleDriver",
        url = "jdbc:oracle:thin:@localhost:1521:XE",
        user = "<DATABASE_USER>",
        password = "<DATABASE_PASSWORD>",
        isolationLevel = Connection.TRANSACTION_READ_COMMITTED)
public class DataSource {
}`code`

5. Download __Payara Server 5.2021.10__ and extract zip file to a local directory.

6. Copy __ojdbc11.jar__ file from __Oracle Database 21c Express Edition__ (__lib__ folder) to Payara Server (__lib__ folder).

* Verify if __Oracle Database 21c Express Edition__ started using __lsnrctl start__ command (as an Administrator).
* Run __Payara Server 5.2021.10__ using __asadmin start-domain__ command.

### Description

...
