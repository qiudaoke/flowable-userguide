/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.flowable.common.sql;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

/**
 * @author Joram Barrez
 */
public class DbDropUtil {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(DbDropUtil.class);
    
    public static boolean dropDatabaseTable(String jdbcDriver, String jdbcUrl, String jdbcUser, String jdbcPassword) {
        
        LOGGER.info("JdbcDriver = " + jdbcDriver);
        LOGGER.info("JdbcURL = " + jdbcUrl);
        LOGGER.info("JdbcUser = " + jdbcPassword);
        
        PooledDataSource dataSource = new PooledDataSource(jdbcDriver, jdbcUrl, jdbcUser, jdbcPassword);
        DatabaseConnection connection = null;
        Database database = null;
        try {
            connection = new JdbcConnection(dataSource.getConnection());
            database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(connection);
            Liquibase liquibase = new Liquibase((String) null, new ClassLoaderResourceAccessor(), database);
            liquibase.dropAll();
        } catch (Exception exception) {
            exception.printStackTrace();

            if (connection != null) {
                try {
                    connection.close();
                } catch (DatabaseException e) {
                    e.printStackTrace();
                }
            }

            if (database != null) {
                try {
                    database.close();
                } catch (DatabaseException e) {
                    e.printStackTrace();
                }
            }
        }

        return true;
    }

}
