package com.gwn.exs.ba.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.gwn.exs.ba.common.UserPrincipal;
import com.gwn.exs.ba.data.hibernate.dao.DAOFactory;
import com.gwn.exs.ba.data.hibernate.entity.User;
import com.gwn.exs.ba.data.shared.ResponseDTO;
import com.gwn.exs.ba.data.shared.UserDTO;
import com.gwn.exs.ba.data.transformer.SafeUserDtoTransformer;

public class HttpServletRequestHelper {

	public static UserPrincipal getUserPrincipal(HttpServletRequest request) {
		UserPrincipal principal = (UserPrincipal) request.getUserPrincipal();
		return principal;
	}
	
	public static User getUser(HttpServletRequest request) {
		String username = getUserPrincipal(request).getUsername();
		User user = DAOFactory.getInstance().getUserDAO().findByUsername(username);
		return user;
	}
	
	public static StringBuilder getServerContextPath(HttpServletRequest request) {
		StringBuilder b = new StringBuilder();
		b.append(request.getScheme()).append("://");
		b.append(request.getServerName()).append(":").append(request.getServerPort());
		b.append(request.getContextPath());
		return b;
	}
	
	public static String getCookie(HttpServletRequest request, String name, String path, String domain) {
		Cookie[] cookies = request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (StringUtils.equals(cookie.getName(), name) && StringUtils.equals(cookie.getPath(), path) && StringUtils.equals(cookie.getDomain(), domain)) {
				return cookie.getValue();
			}
		}
		return null;
	}
	
	/**
	 * Make sure request is authenticated.
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static void writeUserJsonResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
		User obj = getUser(request);
		UserDTO dto = new SafeUserDtoTransformer().transform(obj);
		String json = new Gson().toJson(new ResponseDTO<UserDTO>(dto));
		writeJsonResponse(response, json);
	}
	
	public static void writeJsonResponse(HttpServletResponse response, String json) throws IOException {
		response.setContentType(MediaType.APPLICATION_JSON);
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
	}
}
