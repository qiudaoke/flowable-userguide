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

import java.io.File;
import java.io.StringWriter;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;

import liquibase.Liquibase;
import liquibase.database.Database;

public class SqlScriptUtil {
	
	
	public static void generateUpgradeSqlFile(Liquibase liquibase, Database database, String version, String engine) throws Exception {
    	StringWriter stringWriter = new StringWriter();
        liquibase.update(engine, stringWriter);
        stringWriter.close();
        String sqlScript = filterOutComments(stringWriter.toString());
        sqlScript = filterOutDatabaseNamePrefix(sqlScript);
        FileUtils.write(new File("../sql/upgrade/" + engine, "flowable." + getDatabaseName(database) + ".upgradestep." + version + "." + engine + ".sql"), sqlScript, "utf-8");
        
        liquibase.update(engine);
    }
	
	public static void generateCreateSqlFile(Liquibase liquibase, Database database, String engine) throws Exception {
    	StringWriter stringWriter = new StringWriter();
        liquibase.update(engine, stringWriter);
        stringWriter.close();
        String sqlScript = SqlScriptUtil.filterOutComments(stringWriter.toString());
        sqlScript = SqlScriptUtil.filterOutDatabaseNamePrefix(sqlScript);
        FileUtils.write(new File("../sql/create/" + engine, "flowable." + getDatabaseName(database) + "." + engine + "-engine.create.sql"), sqlScript, "utf-8");
        
        liquibase.update(engine);
    }

	public static String filterOutComments(String sql) {
        StringBuilder result = new StringBuilder();
        Scanner scanner = new Scanner(sql);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (!line.startsWith("--") && !line.startsWith("GO") && !line.startsWith("USE") &&
            		!line.startsWith("SET DEFINE OFF")) {
            	
                result.append(line);
                result.append(System.lineSeparator());
            }
        }
        scanner.close();
        String s = result.toString();
        s = s.trim();
        if (s.equals("SET DEFINE OFF;")) { // Oracle
            return "";
        }
        return result.toString();
    }
    
	public static String filterOutDatabaseNamePrefix(String sql) {
        String tempSql = sql.replaceAll("flowableliquibase.", "");
        tempSql = tempSql.replaceAll("FLOWABLELIQUIBASE.", "");
        tempSql = tempSql.replaceAll("public.", "");
        tempSql = tempSql.replaceAll("PUBLIC.", "");
        tempSql = tempSql.replaceAll("DB2INST1.", "");
        tempSql = tempSql.replaceAll("\\[dbo\\].", "");
        return tempSql;
    }
	
	public static String getDatabaseName(Database database) {
		String dbName = database.getDatabaseProductName().toLowerCase();
		if ("postgresql".equals(dbName)) {
			dbName = "postgres";
		} else if (dbName.contains("db2")) {
			dbName = "db2";
		} else if ("microsoft sql server".equals(dbName)) {
			dbName = "mssql";
		}
		
		return dbName;
	}
}
