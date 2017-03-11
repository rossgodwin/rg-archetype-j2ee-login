package com.gwn.exs.ba.data.hibernate.dao;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Query;

public class DAOUtils {

	public static void applyParameters(Query query, Map<String, Object> params) {
		Iterator<Entry<String, Object>> it = params.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> param = it.next();
			query.setParameter(param.getKey(), param.getValue());
		}
	}
	
	public static void applyPaging(Query query, Integer offset, Integer limit) {
		if (offset != null && limit != null) {
			query.setFirstResult(offset);
			query.setMaxResults(limit);
		}
	}
}
