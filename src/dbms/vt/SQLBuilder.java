package dbms.vt;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


/**
 * The Class SQLBuilder contains a pubic method named createTables that return true
 *  if successfully creating tables in DBMS, and false otherwise
 */
public class SQLBuilder {

	/**
	 * Creates the tables.
	 *
	 * @param connection
	 *            the connection
	 * @param fileURL
	 *            the file URL
	 * @return true, if successful
	 * @throws SQLException
	 *             the SQL exception
	 */
	public static boolean createTables(Connection connection, String fileURL) throws SQLException {

		int countTables = 0;
		ArrayList<String> ddlSchema = new ArrayList<>();

		try {
			ddlSchema = FileIO.readStatementsFromFile(fileURL);
		} catch (IOException e) {

			e.printStackTrace();
		}

		connection.setAutoCommit(false);
		Statement stmt = connection.createStatement();

		for (String table : ddlSchema) {

			int rowsAffected = stmt.executeUpdate(table);
			countTables++;
			if (rowsAffected != 0) {
				System.err.println("Error on: " + table);
				return false;
			}

		}

		stmt.close();
		if (countTables == ddlSchema.size())
			return true;
		return false;

	}

}
