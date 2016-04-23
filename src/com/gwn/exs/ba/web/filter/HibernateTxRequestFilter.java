package com.gwn.exs.ba.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import com.gwn.exs.ba.data.hibernate.HibernateUtil;

public class HibernateTxRequestFilter implements Filter {

	private static Log log = LogFactory.getLog(HibernateTxRequestFilter.class);
	
	private SessionFactory sf;
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		sf = HibernateUtil.getSessionFactory();
	}
	
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain) throws IOException, ServletException {
		try {
			sf.getCurrentSession().beginTransaction();
			
			chain.doFilter(arg0, arg1);
			
			sf.getCurrentSession().getTransaction().commit();
		} catch (Throwable e) {
			e.printStackTrace();
			
			try {
				sf.getCurrentSession().getTransaction().rollback();
			} catch (Throwable er) {
				log.error("Could not rollback transaction after exception.");
			}
			
			throw new ServletException(e);
		}
	}
	
	@Override
	public void destroy() {
	}
}
