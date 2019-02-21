import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
/*
 Do not forget to set up your classpath.  EVERY TIME 
you can use the mysql setup document to do this permanently, 


you must create a classpath  (so that the java import jar file can be found, and the imports at the top work)
Sorun the following command in onyx:
Keep it here for your convenience:
--TODO  Run this in onyx
export CLASSPATH=/opt/mysql/mysql-connector-java-5.1.45/mysql-connector-java-5.1.45-bin.jar:$CLASSPATH


NOTE:  you must be on ONYX, and not a node for the 
Compiler to find the classpath jar file




-- instructors can use this for preparing the DB if you want to have this example  work 
-- Check out this drop if exists!
DROP PROCEDURE IF EXISTS GetClassStudentsByCode;
DELIMITER $$
CREATE Procedure GetClassStudentsByCode(pCode varchar(10))
BEGIN
-- careful not to name your parameters the same as your table columns! unexpected results arise 
    
    SELECT s.ID, s.FirstName, s.LastName 
    FROM Class c
    join ClassStudent cs
                on cs.ClassID = c.ID
        join Student s
                on s.ID = cs.StudentID
        where c.Code = pCode; 
END;
$$


*/
/*
 The command line tests for this to be used in class are here::
javac jt.java
java project /?
java project runGetClassStudentsByCode cs-hu310


*/

class project {
	public static Connection makeConnection(String port, String database, String password) {
		try {
			Connection conn = null;
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:" + port + "/" + database + "?verifyServerCertificate=false&useSSL=true",
					"msandbox", password);
			// Do something with the Connection
			System.out.println("Database " + database + " connection succeeded!");
			System.out.println();
			return conn;
		} catch (SQLException ex) {
			// handle any errors
			System.err.println("SQLException: " + ex.getMessage());
			System.err.println("SQLState: " + ex.getSQLState());
			System.err.println("VendorError: " + ex.getErrorCode());
		}
		return null;
	}

	// To Do: MAKE your own function to call our procedure getData, here
	// dont forget to consider the parameters for each SQL call and use the
	// appropriate inputs

	public static void runGetClassStudentsByCode(Connection conn, String code) {

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			// be sure to call the appropriate procedure based on the function you are
			// running
			stmt = conn.prepareStatement("call GetClassStudentsByCode(?)");
			stmt.setString(1, code); // input parameter

			boolean isResultSet = stmt.execute();
			if (isResultSet) {
				rs = stmt.getResultSet();
			}

			// We have DATA!
			rs.beforeFirst();
			while (rs.next()) {
				// output must match result set columns
				System.out.println(rs.getInt(1) + ":" + rs.getString(2) + ":" + rs.getString(3));
			}

			// Personal challenge: have more result sets in GetData?
			// Here is some code that will give you a head start to read next sets:
			/*
			 * rs = stmt.getResultSet(); // I am told this is not exactly right, can you
			 * google the options and try them out? while (rs.next()) { // columns must
			 * match second result set System.out.println(rs.getInt(1) + ":" +
			 * rs.getString(2) + ":" + rs.getString(3) );
			 * 
			 * rs = stmt. // some code here to get the next set. Try google to find some
			 * options while (rs.next()) { // columns must match third result set
			 * System.out.println(rs.getInt(1) + ":" + rs.getString(2) + ":" +
			 * rs.getString(3) ); }
			 */

		} catch (SQLException ex) {
			// handle any errors
			System.err.println("SQLException: " + ex.getMessage());
			System.err.println("SQLState: " + ex.getSQLState());
			System.err.println("VendorError: " + ex.getErrorCode());
		} finally {
			// it is a good idea to release resources in a finally{} block
			// in reverse-order of their creation if they are no-longer needed
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
		}
	}

	public static void runQuery(Connection conn) {

		Statement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Student;");
			// Now do something with the ResultSet ....

			rs.beforeFirst();
			while (rs.next()) {
				System.out
						.println(rs.getInt(1) + ":" + rs.getString(2) + ":" + rs.getString(3) + ":" + rs.getString(4));
			}

		} catch (SQLException ex) {
			// handle any errors
			System.err.println("SQLException: " + ex.getMessage());
			System.err.println("SQLState: " + ex.getSQLState());
			System.err.println("VendorError: " + ex.getErrorCode());
		} finally {
			// it is a good idea to release resources in a finally{} block
			// in reverse-order of their creation if they are no-longer needed
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
		}
	}

	public static void main(String[] args) {
		try {
			// The newInstance() call is a work around for some broken Java implementations

			if (args[0].equals("/?")) {
				System.out.println("Usage :   GetClassStudentsByCode     code");
				System.out.println("Usage :   test ");
				// TODO: update the usage to show the new GetData option
				return;
			} else {
				System.out.println(args[0]);
				System.out.println("**");

				if (args.length == 2) {
					System.out.println(args[1]); // show extra args
				}
			}
			// TODO: student needs to replace port,db and pwd here:
			 Class.forName("com.mysql.jdbc.Driver").newInstance();
			//Class.forName("com.mysql.jdbc.Driver");
			// -----
			String portnumber = "5897", dbname = "newDatabase", password = "password";
			
			System.out.println();
			System.out.println("JDBC driver loaded");
			 Connection conn = makeConnection(portnumber, dbname, password);
			// Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:"+ portnumber, "msandbox", password);
			
			// TO DO: add to the code here to run the appropriate function
			// when the getdata request is made
			// at the command line

			if (args.length == 2) {
				System.out.println("Running GetClassStudentsByCode");
				runGetClassStudentsByCode(conn, args[1]);
			} else if (args[0].equals("/?")) {
				System.out.println("Running usagetesttesttesttest");
			} else if (args[0].equals("test")) {
				System.out.println("Running test");
				runQuery(conn);
			} else if (args[0].equals("GetData")) {
				getData(conn);
			}else {
				System.out.println("No process requested");
			}
			conn.close();
			System.out.println();
			System.out.println("Database connection closed");
			System.out.println();
		} catch (Exception ex) {
			// handle the error
			System.out.println("<<<<< FAILED TO LOAD CONNECTION");
			System.err.println(ex);
		}
	}
	
	// -----	
	public static void getData(Connection conn) {
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Student");
			
			//System.out.println(rs);
			
			ResultSetMetaData rsmd = rs.getMetaData();

			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					if (i > 1) System.out.print(",  ");
					String columnValue = rs.getString(i);
					System.out.print(columnValue + " " + rsmd.getColumnName(i));
				}
				
				System.out.println(" ");
			}
		} catch (SQLException e) {
			System.out.println("<<<<< FAILED TO GET DATA");
			System.err.println(e);
		}
	}
}
