package com.github.sca.common.request.extend;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 请求体过滤器
 * 
 * @author Ryan
 * @date 2020/01/17
 * @version 1.0.0
 *
 */
public class RequestBodyFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		RequestBodyServletRequestWrapper wrapper = null;

		if (request instanceof HttpServletRequest) {
			wrapper = new RequestBodyServletRequestWrapper((HttpServletRequest) request);
		}

		if (null == wrapper) {
			chain.doFilter(request, response);
		} else {
			chain.doFilter(wrapper, response);
		}

	}

}
