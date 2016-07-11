package de.Lavahayn.Main;

public class LoginManager {

	baseClass m_Server;
	DatabaseAccess m_DatabaseAccess;

	public LoginManager(baseClass plugin) {
		m_Server = plugin;
		m_DatabaseAccess = m_Server.getM_DatabaseAccess();
	}

	public boolean Login(String user, String pass) {
		return m_DatabaseAccess.ValidateCredentials(user, pass);
	}

	public boolean Register(String user, String pass) {
		return m_DatabaseAccess.RegisterNewUser(user, pass);
	}
}
