package dmit2015.rendoruiz.assignment03.config;

import javax.annotation.sql.DataSourceDefinition;
import javax.annotation.sql.DataSourceDefinitions;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;

@DataSourceDefinitions({
        @DataSourceDefinition(
                name="java:app/datasources/h2databaseDS",
                className="org.h2.jdbcx.JdbcDataSource",
//                url="jdbc:h2:file:~/databases/dmit2015-assignment03-rendoruiz-db",
                url="jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
                user="sa",
                password="sa"),
})

/**
 * This class is a configuration class containing the data source definitions.
 *
 * @author Rendo Ruiz
 * @version 2021.03.12
 *
 */
@FacesConfig
@ApplicationScoped
public class ApplicationConfig {
}
