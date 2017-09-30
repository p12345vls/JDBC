package dbms.vt;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * The Class SQLRunner Contains a pubic method execute() that returns true 
 * if successfully execute and output data in HTML, and false otherwise.
 */
public class SQLRunner {

	/**
	 * Execute.
	 *
	 * @param connection
	 *            the connection
	 * @param fileURL
	 *            the file URL
	 * @return true, if successful
	 * @throws SQLException
	 *             the SQL exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static boolean execute(Connection connection, String fileURL) throws SQLException, IOException {

		ArrayList<String> queries = FileIO.readStatementsFromFile(fileURL);
		int countQueries = 0;
		Statement stmt = connection.createStatement();

		ResultSet rs = null;
		for (String query : queries) {
			rs = stmt.executeQuery(query);
			countQueries++;
			FileIO.writeToHTML(query, rs);
		}

		connection.commit();
		connection.setAutoCommit(true);

		if (rs != null)
			rs.close();

		stmt.close();
		connection.close();
		if (countQueries == queries.size())
			return true;
		return false;

	}

}
