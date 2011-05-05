package dataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 * @author Anthony Arena, Jonathan Anastasiou, John Frederiksen, Daniel Spitzer
 */
/**
 * Mapper that retrieves all the conversion related objects from the database.
 */
public class ConversionMapper {

	public float getConversionRate(String conversion, Connection con) {
		float rate = 0;
		String SQLString = "select Rate from Conversions where Conversion = ?";
		// get the Rate by conversion

		PreparedStatement statement = null;

		try {
			// === get rate
			statement = con.prepareStatement(SQLString);
			statement.setString(1, conversion);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				rate = rs.getFloat(1);
			}
		} catch (Exception e) {
			System.out
					.println("Something went wrong in ConversionMapper - getConversionRate");
			System.out.println(e.getMessage());
		}
		return rate;
	}
	
	public boolean saveNewConversion(String conversion, float rate, Connection con) {
		int rowsInserted = 0;
		String SQLString = "insert into Conversions values(?, ?)";
		// insert new tuple
		
		PreparedStatement statement;
		
		try {
			// == save new tuple
			statement = con.prepareStatement(SQLString);
			statement.setString(1, conversion);
			statement.setFloat(2, rate);
			rowsInserted = statement.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error in ConversionMapper - saveNewConversion");
			System.out.println(e.getMessage());
		}
		return rowsInserted == 1;
	}
	
	public boolean editConversion(String conversion, float rate, Connection con) {
		int rowsUpdated = 0;
		String SQLString = "update Conversions set Rate = ? where Conversion = ?";
		// update existing tuple
		
		PreparedStatement statement;
		
		try {
			// == update the tuple
			statement = con.prepareStatement(SQLString);
			statement.setFloat(1, rate);
			statement.setString(2, conversion);
			rowsUpdated = statement.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error in ConversionMapper - editConversion");
			System.out.println(e.getMessage());
		}
		return rowsUpdated == 1;
	}
}
