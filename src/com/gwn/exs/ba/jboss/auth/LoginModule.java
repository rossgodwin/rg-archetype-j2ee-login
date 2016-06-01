package com.gwn.exs.ba.jboss.auth;

import java.io.IOException;
import java.security.Principal;
import java.security.acl.Group;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.jboss.security.SimpleGroup;
import org.jboss.security.SimplePrincipal;
import org.jboss.security.auth.spi.AbstractServerLoginModule;

import com.gwn.exs.ba.biz.UserPasswordHelper;
import com.gwn.exs.ba.common.UserPrincipal;
import com.gwn.exs.ba.data.hibernate.entity.User;
import com.gwn.exs.ba.data.shared.UserRole;

public class LoginModule extends AbstractServerLoginModule {

	private CallbackHandler handler;
	
	private String dsJndiName;
	
	private UserPrincipal principal;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void initialize(Subject subject, CallbackHandler handler, Map sharedState, Map options) {
		super.initialize(subject, handler, sharedState, options);

		if (log.isDebugEnabled()) {
			log.debug("Invoke initiliaze...");
		}
		this.subject = subject;
		this.handler = handler;
		this.sharedState = sharedState;
		this.options = options;

		dsJndiName = (String) options.get("dsJndiName");
	}
	
	public boolean login() throws LoginException {
		try {
			if (handler == null) {
				throw new LoginException("No callback Handler specified");
			}
			loginOk = isLoginValid();
		} catch (FailedLoginException e) {
			log.warn(e.getMessage());
			loginOk = false;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			loginOk = false;
		}
		return loginOk;
	}
	
	private Context getContext() throws NamingException {
		return new InitialContext();
	}

	private DataSource getDataSource() {
		DataSource ds = null;
		try {
			ds = (DataSource) getContext().lookup(dsJndiName);
		} catch (NamingException ne) {
			ne.printStackTrace();
		}
		return ds;
	}

	private QueryRunner getQueryRunner() {
		return new QueryRunner(getDataSource());
	}
	
	private User findUser(String username) throws SQLException {
		ResultSetHandler<Object[]> h = new ResultSetHandler<Object[]>() {
			public Object[] handle(ResultSet rs) throws SQLException {
				if (!rs.next()) {
					return null;
				}

				ResultSetMetaData meta = rs.getMetaData();
				int cols = meta.getColumnCount();
				Object[] result = new Object[cols];

				for (int i = 0; i < cols; i++) {
					result[i] = rs.getObject(i + 1);
				}

				return result;
			}
		};
		
		User user = null;
		Object[] results = (Object[]) getQueryRunner().query("select id, username, password, role, email from user where username = ?", h, username);
		if (results != null) {
			user = new User();
			user.setId(((Number)results[0]).longValue());
			user.setUsername((String)results[1]);
			user.setPassword((String)results[2]);
			user.setRole(UserRole.valueOf((String)results[3]));
			user.setEmail((String)results[4]);
		}
		return user;
	}
	
	private boolean isLoginValid() throws LoginException {
		String username;

		NameCallback n = new NameCallback("User Name - ");
		PasswordCallback p = new PasswordCallback("Password - ", true);
		Callback[] callbacks = { n, p };
		
		try {
			handler.handle(callbacks);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (UnsupportedCallbackException e) {
			throw new RuntimeException(e);
		}
		
		username = n.getName().trim();
		
		User user = null;
		try {
			user = findUser(username);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		if (user == null) {
			throw new FailedLoginException("Invalid username");
		}
		
		char[] plainPasswordChars = p.getPassword();
		if (plainPasswordChars != null) {
			String plainPassword = new String(plainPasswordChars);
			if (UserPasswordHelper.checkPassword(plainPassword, user.getPassword())) {
				principal = new UserPrincipal(user.getId(), user.getUsername(), user.getRole());
				return true;
			} else {
				throw new FailedLoginException("Invalid password");
			}
		}
		
		return false;
	}
	
	@Override
	protected Principal getIdentity() {
		return principal;
	}

	@Override
	protected Group[] getRoleSets() throws LoginException {
		Group rolesGroup = new SimpleGroup("Roles");
		Group callerPrincipalGroup = new SimpleGroup("CallerPrincipal");
		Group[] groups = { rolesGroup, callerPrincipalGroup };
		rolesGroup.addMember(new SimplePrincipal(principal.getRole().name()));
		callerPrincipalGroup.addMember(principal);
		return groups;
	}
}
