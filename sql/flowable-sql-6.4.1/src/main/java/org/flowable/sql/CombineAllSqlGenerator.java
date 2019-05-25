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

public class CombineAllSqlGenerator {

    public static void main(String[] args) throws Exception {
    	Database database = getDatabaseInstance();
    	generateCreateAll(database);
    }
    
    protected static void generateCreateAll(Database database) throws Exception {
    	String databaseName = SqlScriptUtil.getDatabaseName(database);
    	File commonEngineFile = new File("../sql/create/common/flowable." + databaseName + ".create.common.sql");
    	File bpmnEngineFile = new File("../sql/create/bpmn/flowable." + databaseName + ".create.engine.sql");
    	File appEngineFile = new File("../sql/create/app/flowable." + databaseName + ".app-engine.create.sql");
    	File dmnEngineFile = new File("../sql/create/dmn/flowable." + databaseName + ".dmn-engine.create.sql");
    	File cmmnEngineFile = new File("../sql/create/cmmn/flowable." + databaseName + ".cmmn-engine.create.sql");
    	File formEngineFile = new File("../sql/create/form/flowable." + databaseName + ".form-engine.create.sql");
    	File contentEngineFile = new File("../sql/create/content/flowable." + databaseName + ".content-engine.create.sql");
    	
    	File allDir = new File("../sql/create/all");
    	if (!allDir.exists()) {
    		allDir.mkdir();
    	}
    	File allFile = new File("../sql/create/all/flowable." + databaseName + ".all.create.sql");
    	if (allFile.exists()) {
    		allFile.delete();
    	}
		allFile.createNewFile();
    	
    	joinFiles(allFile, commonEngineFile, bpmnEngineFile, appEngineFile, cmmnEngineFile, 
    			dmnEngineFile, formEngineFile, contentEngineFile);
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
