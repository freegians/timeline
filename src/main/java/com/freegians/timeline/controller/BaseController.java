package com.freegians.timeline.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


abstract public class BaseController {
	protected static final Logger logger = LoggerFactory.getLogger(BaseController.class);

	@Autowired
	private WebApplicationContext ctx;
	protected MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();

	protected WebApplicationContext getCurrentContext() {
		return ctx;
	}

	protected void setJsonResponse(Object bean, HttpServletResponse response) throws HttpMessageNotWritableException, IOException {
		MediaType jsonMimeType = MediaType.APPLICATION_JSON;
		if (jsonConverter.canWrite(String.class, jsonMimeType)) {
			jsonConverter.write(bean, jsonMimeType, new ServletServerHttpResponse(response));
		}
	}

	protected Map<String, Object> createSuccessResponse(Object data) {
		return createSuccessResponse("", data);
	}

	protected Map<String, Object> createSuccessResponse(String msg, Object data) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", Boolean.TRUE);
		result.put("data", data);
		result.put("msg", msg);
		return result;
	}

	protected Map<String, Object> createFailureResponse(String msg, Throwable e) {
		logger.error(msg, e);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", Boolean.FALSE);
		result.put("msg", msg);
		return result;
	}

	protected Map<String, Object> createFailureResponse(String msg) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", Boolean.FALSE);
		result.put("msg", msg);
		return result;
	}


	protected Map<String, Object> createSuccessResponse(String msg, Object data, Object metaData) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", Boolean.TRUE);
		result.put("data", data);
		result.put("metaData", metaData);
		result.put("msg", msg);
		return result;
	}

	protected Map<String, Object> createRetryResponse(String msg, Object data, Object metaData) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", Boolean.TRUE);
		result.put("data", data);
		result.put("retry", true);
		result.put("metaData", metaData);
		result.put("msg", msg);
		return result;
	}
	
	@ExceptionHandler(Throwable.class)
	@ResponseBody
	public Object handleInternalServerException(Exception ex, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		boolean ajaxRedirect = request.getHeader("x-requested-with") != null
			&& request.getHeader("x-requested-with").toLowerCase().indexOf("xmlhttprequest") > -1;
		logger.error(ex.getMessage(), ex);
//		if (ajaxRedirect) {
			return createFailureResponse(ex.getMessage(), ex);
//		} else {
//			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//			return ex;
//		}
	
	}

}
