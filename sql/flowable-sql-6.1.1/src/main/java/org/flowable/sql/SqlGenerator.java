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
package org.flowable.sql;

import org.flowable.common.sql.SqlScriptUtil;
import org.flowable.content.engine.ContentEngineConfiguration;
import org.flowable.dmn.engine.DmnEngineConfiguration;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.form.engine.FormEngineConfiguration;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.resource.ClassLoaderResourceAccessor;

public class SqlGenerator {

	public static void main(String[] args) throws Exception {
    	String oldVersion = args[0];
    	String newVersion = args[1];
    	
    	generateDmnEngineUpgradeSql(oldVersion, newVersion);
    	generateFormEngineUpgradeSql(oldVersion, newVersion);
    	generateContentEngineUpgradeSql(oldVersion, newVersion);
    }
    
    protected static void generateDmnEngineUpgradeSql(String oldVersion, String newVersion) throws Exception {
    	Database database = getDatabaseInstance();
    	database.setDatabaseChangeLogTableName(DmnEngineConfiguration.LIQUIBASE_CHANGELOG_PREFIX + database.getDatabaseChangeLogTableName());
        database.setDatabaseChangeLogLockTableName(DmnEngineConfiguration.LIQUIBASE_CHANGELOG_PREFIX + database.getDatabaseChangeLogLockTableName());
    	Liquibase liquibase = new Liquibase("org/flowable/dmn/db/liquibase/flowable-dmn-db-changelog.xml", new ClassLoaderResourceAccessor(), database);
    	SqlScriptUtil.generateUpgradeSqlFile(liquibase, database, oldVersion + ".to." + newVersion, "dmn");
    }
    
    protected static void generateFormEngineUpgradeSql(String oldVersion, String newVersion) throws Exception {
    	Database database = getDatabaseInstance();
    	database.setDatabaseChangeLogTableName(FormEngineConfiguration.LIQUIBASE_CHANGELOG_PREFIX + database.getDatabaseChangeLogTableName());
        database.setDatabaseChangeLogLockTableName(FormEngineConfiguration.LIQUIBASE_CHANGELOG_PREFIX + database.getDatabaseChangeLogLockTableName());
    	Liquibase liquibase = new Liquibase("org/flowable/form/db/liquibase/flowable-form-db-changelog.xml", new ClassLoaderResourceAccessor(), database);
    	SqlScriptUtil.generateUpgradeSqlFile(liquibase, database, oldVersion + ".to." + newVersion, "form");
    }
    
    protected static void generateContentEngineUpgradeSql(String oldVersion, String newVersion) throws Exception {
    	Database database = getDatabaseInstance();
    	database.setDatabaseChangeLogTableName(ContentEngineConfiguration.LIQUIBASE_CHANGELOG_PREFIX + database.getDatabaseChangeLogTableName());
        database.setDatabaseChangeLogLockTableName(ContentEngineConfiguration.LIQUIBASE_CHANGELOG_PREFIX + database.getDatabaseChangeLogLockTableName());
    	Liquibase liquibase = new Liquibase("org/flowable/content/db/liquibase/flowable-content-db-changelog.xml", new ClassLoaderResourceAccessor(), database);
    	SqlScriptUtil.generateUpgradeSqlFile(liquibase, database, oldVersion + ".to." + newVersion, "content");
    }
    
    protected static Database getDatabaseInstance() throws Exception {
    	ProcessEngineConfiguration engineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("flowable.cfg.xml");
    	DatabaseFactory databaseFactory = DatabaseFactory.getInstance();
    	DatabaseConnection connection = databaseFactory.openConnection(engineConfiguration.getJdbcUrl(), engineConfiguration.getJdbcUsername(), 
    			engineConfiguration.getJdbcPassword(), engineConfiguration.getJdbcDriver(), null, null, null, new ClassLoaderResourceAccessor());
        return DatabaseFactory.getInstance().findCorrectDatabaseImplementation(connection);
    }

}
