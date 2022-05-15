package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

public class PaymentManagement {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/payment?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertProject(String pid, String acc, String unit, String price) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into payment(`id`,`pid`,`acc`,`unit`,`price`)"
					+ " values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, pid);
			preparedStmt.setString(3, acc);
			preparedStmt.setString(4, unit);
			preparedStmt.setString(5, price);

			// execute the statement
			preparedStmt.execute();
			con.close();

			String newProject = readProject();
			output = "{\"status\":\"success\", \"data\": \"" + newProject + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the project.\"}";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String readProject() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\'1\'><tr><th>Payment ID</th><th>Account</th><th>Unit</th><th>Price</th><th>Update</th><th>Delete</th></tr>";
			String query = "select * from payment";

			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String id = rs.getString("id");
				String pid = rs.getString("pid");
				String acc = rs.getString("acc");
				String unit = rs.getString("unit");
				String price = rs.getString("price");
				

				// Add into the html table
				output += "<tr><td><input id=\'hidProjectIDUpdate\' name=\'hidProjectIDUpdate\' type=\'hidden\' value=\'"
						+ id + "'>" + pid + "</td>";
				output += "<td>" + acc + "</td>";
				output += "<td>" + unit + "</td>";
				output += "<td>" + price + "</td>";
				

				
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-payid='"
						+ id + "'>" + "</td></tr>";
			}

			con.close();

			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the projects.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String updateProject(String id,String pid, String acc,String unit, String price) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE payment SET pid=?,acc=?,unit=?,price=? WHERE id=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values

			preparedStmt.setString(1, pid);
			preparedStmt.setString(2, acc);
			preparedStmt.setString(3, unit);
			preparedStmt.setString(4, price);
			preparedStmt.setInt(5, Integer.parseInt(id));

			// execute the statement
			preparedStmt.execute();
			con.close();

			String newProject = readProject();
			output = "{\"status\":\"success\", \"data\": \"" + newProject + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while updating the project.\"}";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String deleteProject(String id) {

		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from payment where id=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(id));

			// execute the statement
			preparedStmt.execute();
			con.close();

			String newProject = readProject();
			output = "{\"status\":\"success\", \"data\": \"" + newProject + "\"}";
		} catch (Exception e) {
			output = "Error while deleting the project.";
			System.err.println(e.getMessage());
		}

		return output;
	}

}
