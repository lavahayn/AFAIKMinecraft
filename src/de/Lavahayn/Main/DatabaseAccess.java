package de.Lavahayn.Main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

public class DatabaseAccess {

	baseClass m_Server;

	MySQL m_MySQL = new MySQL("DESKTOP-V2NGU7V\\LAVAHAYN", "1433", "AFAIK", "root", "jk(bnj9Ob9N0");
	Connection c = null;

	public void MaintainceDatabase() {
		String strCreateTBL_Credentials = "CREATE TABLE [dbo].[TBL_Credentials](" 
	+ "[ID] [int] IDENTITY(1,1) NOT NULL,"
	+ "	[Name] [nvarchar](50) NOT NULL UNIQUE," 
	+ "	[Pwd] [nvarchar](50) NOT NULL,"
	+ " CONSTRAINT [PK_TBL_Credentials] PRIMARY KEY CLUSTERED " 
	+ "(" 
	+ "	[ID] ASC"
	+ ")WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, "
	+ "ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]" 
	+ ") ON [PRIMARY]";
		try {

			try {
				c = m_MySQL.openConnection();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				m_Server.Log(Level.WARNING, e.getMessage());
				m_Server.Log(Level.FINEST, e.toString());
			}
			Statement statement = c.createStatement();
			statement.executeQuery(strCreateTBL_Credentials);
		} catch (SQLException e) {
			m_Server.Log(Level.WARNING, e.getMessage());
			m_Server.Log(Level.FINEST, e.toString());
		}
	}

	public DatabaseAccess(baseClass plugin) {
		m_Server = plugin;
	}

	public boolean ValidateCredentials(String user, String pass) {
		try {

			try {
				c = m_MySQL.openConnection();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				m_Server.Log(Level.WARNING, e.getMessage());
				m_Server.Log(Level.FINEST, e.toString());
			}
			Statement statement = c.createStatement();
			ResultSet res = statement.executeQuery(
					"SELECT TOP 1 ID FROM TBL_Credentials WHERE Name = '" + user + "' AND Pwd = '" + pass + "';");
			if (res.next()) {
				return true;
			}
		} catch (SQLException e) {
			m_Server.Log(Level.WARNING, e.getMessage());
			m_Server.Log(Level.FINEST, e.toString());
		}

		return false;
	}

	public boolean RegisterNewUser(String user, String pass) {
		return true;
	}
}
