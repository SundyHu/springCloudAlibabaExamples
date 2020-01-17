package com.github.sca.common.request.extend;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 请求体请求包装
 * 
 * @author Ryan
 * @date 2020/01/17
 * @version 1.0.0
 *
 */
public class RequestBodyServletRequestWrapper extends HttpServletRequestWrapper {

	private final String body;

	public RequestBodyServletRequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;

		try {
			InputStream inputStream = request.getInputStream();
			if (null != inputStream) {
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			} else {
				stringBuilder.append("");
			}
		} catch (IOException ex) {
			throw ex;
		} finally {
			try {
				if (null != bufferedReader) {
					bufferedReader.close();
				}
			} catch (IOException ex) {
				throw ex;
			}
		}

		body = stringBuilder.toString();
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {

		ByteArrayInputStream bais = new ByteArrayInputStream(this.body.getBytes());

		ServletInputStream servletInputStream = new ServletInputStream() {

			@Override
			public int read() throws IOException {
				return bais.read();
			}

			@Override
			public void setReadListener(ReadListener readListener) {

			}

			@Override
			public boolean isReady() {
				return true;
			}

			@Override
			public boolean isFinished() {
				return 0 == bais.available();
			}
		};

		return servletInputStream;
	}

	@Override
	public BufferedReader getReader() throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getInputStream()));
		return bufferedReader;
	}

	public String getBody() {
		return this.body;
	}

}
