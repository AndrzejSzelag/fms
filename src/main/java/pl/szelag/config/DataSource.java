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
