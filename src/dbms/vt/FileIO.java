package dbms.vt;

import java.sql.*;
import java.io.*;
import java.util.*;

/**
 * The Class FileIO.
 */
public class FileIO {

	/**
	 * This function readStatementsFromFile reads all the SQL statements from a
	 * text file and save each of these statements into a separate element of an
	 * ArrayList for easy access - input parameter: fileURL - a location of a
	 * SQL file - output parameter: an ArrayList in which each element is a
	 * complete SQL statement.
	 *
	 * @param fileURL
	 *            the file URL
	 * @return the array list
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static ArrayList<String> readStatementsFromFile(String fileURL) throws IOException {

		FileReader fr = new FileReader(fileURL);
		BufferedReader br = new BufferedReader(fr);
		ArrayList<String> SQLstatements = new ArrayList<String>();
		String currentLine = "", stmt = "";

		while ((currentLine = br.readLine()) != null) {

			stmt += currentLine + " ";
			if (currentLine.contains(";")) {
				SQLstatements.add(stmt);
				stmt = "";
			}
		}

		br.close();
		return SQLstatements;
	}

	/**
	 * Writes each individual query to HTML file.
	 *
	 * @param query
	 *            the query
	 * @param rs
	 *            the rs
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws SQLException
	 *             the SQL exception
	 */
	public static void writeToHTML(String query, ResultSet rs) throws IOException, SQLException {

		String queryPrompt = query.substring(0, query.indexOf("SELECT"));

		int index = queryPrompt.indexOf("/*");
		String queryNumber = query.substring(index + 3, index + 5);

		// Location and name of the output HTML file that we want to write to
		String htmlFile = "/Users/pp/Desktop/html/" + queryNumber + ".html";

		// Create an output file if it does not exist
		File file = new File(htmlFile);
		if (!file.exists())
			file.createNewFile();

		// Create a file writer and a buffer writer
		// that establish a writing path
		// between your output file and this Java application
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);

		ResultSetMetaData rsMetaData = rs.getMetaData();
		int rsColumnCount = rsMetaData.getColumnCount();

		// Prepare the content of a HTML file
		String content = "<!DOCTYPE html><html>" + "<head><title>JDBC - PostgreSQL</title></head>"
				+ "<body><h2>Query Execution Output</h2>" + "<h4>" + queryPrompt + "</h4>" + "<table border=\"1\">";

		// Fill in the rest of the content
		// from ResultSet data
		content += "<tr>";
		for (int i = 1; i <= rsColumnCount; i++)
			content += "<th>" + rsMetaData.getColumnName(i) + "</th>";
		content += "</tr>";

		while (rs.next()) {
			content += "<tr>";
			for (int i = 1; i <= rsColumnCount; i++)
				content += "<td>" + rs.getString(i) + "</td>";
			content += "</tr>";
		}

		content += "</table></body></html>";

		// Write to output file
		// and close the writing path, once finish
		bw.write(content);
		bw.close();
	}
}