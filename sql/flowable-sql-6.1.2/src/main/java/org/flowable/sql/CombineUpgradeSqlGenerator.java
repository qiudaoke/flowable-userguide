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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.flowable.common.sql.SqlScriptUtil;
import org.flowable.engine.ProcessEngineConfiguration;

import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.resource.ClassLoaderResourceAccessor;

public class CombineUpgradeSqlGenerator {

    public static void main(String[] args) throws Exception {
    	String oldVersion = args[0];
    	String newVersion = args[1];
    	
    	Database database = getDatabaseInstance();
    	generateUpgradeAll(database, oldVersion, newVersion);
    }
    
    protected static void generateUpgradeAll(Database database, String oldVersion, String newVersion) throws Exception {
    	String databaseName = SqlScriptUtil.getDatabaseName(database);
    	File bpmnEngineFile = new File("../sql/upgrade/bpmn/flowable." + databaseName + ".upgradestep." + oldVersion + ".to." + newVersion + ".engine.sql");
    	File dmnEngineFile = new File("../sql/upgrade/dmn/flowable." + databaseName + ".upgradestep." + oldVersion + ".to." + newVersion + ".dmn.sql");
    	File formEngineFile = new File("../sql/upgrade/form/flowable." + databaseName + ".upgradestep." + oldVersion + ".to." + newVersion + ".form.sql");
    	File contentEngineFile = new File("../sql/upgrade/content/flowable." + databaseName + ".upgradestep." + oldVersion + ".to." + newVersion + ".content.sql");
    	
    	File allDir = new File("../sql/upgrade/all");
    	if (!allDir.exists()) {
    		allDir.mkdir();
    	}
    	File allFile = new File("../sql/upgrade/all/flowable." + databaseName + ".upgradestep." + oldVersion + ".to." + newVersion + ".all.sql");
    	if (allFile.exists()) {
    		allFile.delete();
    	}
		allFile.createNewFile();
    	
    	joinFiles(allFile, bpmnEngineFile, dmnEngineFile, formEngineFile, contentEngineFile);
    }
    
    protected static Database getDatabaseInstance() throws Exception {
    	ProcessEngineConfiguration engineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("flowable.cfg.xml");
    	DatabaseFactory databaseFactory = DatabaseFactory.getInstance();
    	DatabaseConnection connection = databaseFactory.openConnection(engineConfiguration.getJdbcUrl(), engineConfiguration.getJdbcUsername(), 
    			engineConfiguration.getJdbcPassword(), engineConfiguration.getJdbcDriver(), null, null, null, new ClassLoaderResourceAccessor());
        return DatabaseFactory.getInstance().findCorrectDatabaseImplementation(connection);
    }
    
    public static void joinFiles(File destination, File... sources) throws Exception {
        try (OutputStream output = createAppendableStream(destination)) {
            for (File source : sources) {
                appendFile(output, source);
            }
        }
    }

    protected static BufferedOutputStream createAppendableStream(File destination) throws Exception {
        return new BufferedOutputStream(new FileOutputStream(destination, true));
    }

    private static void appendFile(OutputStream output, File source) throws Exception {
    	try (InputStream input = new BufferedInputStream(new FileInputStream(source))) {
            IOUtils.copy(input, output);
        } 
    }

}
