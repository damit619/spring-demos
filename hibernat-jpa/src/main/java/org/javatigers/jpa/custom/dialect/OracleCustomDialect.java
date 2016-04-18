package org.javatigers.jpa.custom.dialect;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.dialect.Oracle10gDialect;

public class OracleCustomDialect extends Oracle10gDialect {
	
	/**
	 * Constructs a OracleCustomDialect
	 */
	public OracleCustomDialect() {
		super();
	}
	
	@Override
	public ResultSet getResultSet(CallableStatement statement, int position) throws SQLException {
		if ( position != 1 ) {
			throw new UnsupportedOperationException( "Oracle11g2 only supports REF_CURSOR parameters as the first parameter" );
		}
		return (ResultSet) statement.getObject( 1 );
	}
	
}
