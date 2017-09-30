package dbms.vt;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


/**
 * The Class SQLLoader Contains a pubic method named insertValues
 *  that return true if successfully inserting data in DBMS, and false otherwise.
 */
public class SQLLoader {

	/**
	 * Insert values.
	 *
	 * @param connection
	 *            the connection
	 * @param fileURL
	 *            the file URL
	 * @return true, if successful
	 * @throws SQLException
	 *             the SQL exception
	 */
	public static boolean insertValues(Connection connection, String fileURL) throws SQLException {
		ArrayList<String> ddlInputs = new ArrayList<>();

		int countInputs = 0;
		try {
			ddlInputs = FileIO.readStatementsFromFile(fileURL);
		} catch (IOException e) {

			e.printStackTrace();
		}

		Statement stmt = connection.createStatement();

		for (String input : ddlInputs) {

			int rowsAffected = stmt.executeUpdate(input);
			countInputs++;
			if (rowsAffected != 1) {
				System.err.println("Error on: " + input);
				return false;
			}
		}

		connection.commit();

		stmt.close();
		if (countInputs == ddlInputs.size())
			return true;
		return false;

	}

}
