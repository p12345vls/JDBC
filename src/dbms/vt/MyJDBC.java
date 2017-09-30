package dbms.vt;

import java.io.IOException;
import java.sql.*;

/**
 * The Class MyJDBC contains main() with DBMS access information 
 */
public class MyJDBC {

	/** The Constant PORT. */
	public final static String PORT = "5432";
	
	/** The Constant DATABASE_NAME. */
	public final static String DATABASE_NAME = "postgres";
	
	
	/** The Constant USERNAME. */
	public final static String USERNAME = "postgres";
	
	/** The Constant PASSWORD. */
	public final static String PSSWD = "pass";
	
	/** The Constant URL. */
	public final static String URL = "jdbc:postgresql://localhost:" + PORT + "/" + DATABASE_NAME;

	/** The Constant CREATE_TABLES_DDL_URL. */
	public final static String CREATE_TABLES_DDL_URL = "SQLFiles/mondial_schema.txt";
	
	/** The Constant INSERT_VALUES_DDL_URL. */
	public final static String INSERT_VALUES_DDL_URL = "SQLFiles/mondial_inputs.txt";
	
	/** The Constant QUERY_DML_URL. */
	public final static String QUERY_DML_URL = "SQLFiles/mondial_queries.txt";

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws SQLException the SQL exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws SQLException, IOException {
		Connection connection = DriverManager.getConnection(URL, USERNAME, PSSWD);

		if (SQLBuilder.createTables(connection, CREATE_TABLES_DDL_URL))
			System.out.println("Successfully created all tables.");

		if (SQLLoader.insertValues(connection, INSERT_VALUES_DDL_URL))
			System.out.println("Successfully inserted all values.");

		if (SQLRunner.execute(connection, QUERY_DML_URL))
			System.out.println("Successfully executed and outputed all results.");

		connection.close();
	}

}
