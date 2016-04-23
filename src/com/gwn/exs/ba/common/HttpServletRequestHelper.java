package com.gwn.exs.ba.common;

import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestHelper {

	public static StringBuilder getServerContextPath(HttpServletRequest request) {
		StringBuilder b = new StringBuilder();
		b.append(request.getScheme()).append("://");
		b.append(request.getServerName()).append(":").append(request.getServerPort());
		b.append(request.getContextPath());
		return b;
	}
}
