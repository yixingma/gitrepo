package com.invy.controller;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.ProducesRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.invy.wadl.Application;
import com.invy.wadl.Doc;
import com.invy.wadl.Param;
import com.invy.wadl.ParamStyle;
import com.invy.wadl.Representation;
import com.invy.wadl.Request;
import com.invy.wadl.Resource;
import com.invy.wadl.Resources;
import com.invy.wadl.Response;

@Controller
@RequestMapping
public class WadlGeneratorController {
	// @Autowired
	private RequestMappingHandlerMapping handlerMapping;

	/**
	 * Constructor for initializing the Wadl Controller
	 * 
	 * @param handlerMapping
	 */
	@Autowired
	public WadlGeneratorController(RequestMappingHandlerMapping handlerMapping) {
		this.handlerMapping = handlerMapping;
	}

	/**
	 * This is a function which will be responsible for generating the WADL
	 * file.
	 * 
	 * @param request
	 *            : Represents the Request
	 * @return WadlApplication : This object will be converted to the WADL File.
	 */
	@RequestMapping(value = "/get-wadl",method = RequestMethod.GET, produces = { "application/xml" })
	public @ResponseBody
	Application generateWadl(HttpServletRequest request) {
		Application result = new Application();
		Doc doc = new Doc();
		doc.setTitle("REST Service WADL");
		result.getDoc().add(doc);
		Resources wadlResources = new Resources();
		wadlResources.setBase(getBaseUrl(request));

		Map<RequestMappingInfo, HandlerMethod> handletMethods = handlerMapping
				.getHandlerMethods();
		for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handletMethods
				.entrySet()) {
			Resource wadlResource = new Resource();

			HandlerMethod handlerMethod = entry.getValue();
			RequestMappingInfo mappingInfo = entry.getKey();

			Set<String> pattern = mappingInfo.getPatternsCondition()
					.getPatterns();
			Set<RequestMethod> httpMethods = mappingInfo.getMethodsCondition()
					.getMethods();
			ProducesRequestCondition producesRequestCondition = mappingInfo
					.getProducesCondition();
			Set<MediaType> mediaTypes = producesRequestCondition
					.getProducibleMediaTypes();

			for (RequestMethod httpMethod : httpMethods) {
				com.invy.wadl.Method wadlMethod = new com.invy.wadl.Method();

				for (String uri : pattern) {
					wadlResource.setPath(uri);
				}

				wadlMethod.setName(httpMethod.name());
				Method javaMethod = handlerMethod.getMethod();
				wadlMethod.setId(javaMethod.getName());
				Doc wadlDocMethod = new Doc();
				wadlDocMethod.setTitle(javaMethod.getDeclaringClass().getName()
						+ "." + javaMethod.getName());
				wadlMethod.getDoc().add(wadlDocMethod);

				// Request
				Request wadlRequest = new Request();

				Annotation[][] annotations = javaMethod
						.getParameterAnnotations();
				Class<?>[] paramTypes = javaMethod.getParameterTypes();
				int parameterCounter = 0;

				for (Annotation[] annotation : annotations) {
					for (Annotation annotation2 : annotation) {
						if (annotation2 instanceof RequestParam) {
							RequestParam param2 = (RequestParam) annotation2;

							Param waldParam = new Param();

							waldParam.setName(param2.value());

							waldParam.setStyle(ParamStyle.QUERY);
							waldParam.setRequired(param2.required());

							if (paramTypes != null
									&& paramTypes.length > parameterCounter) {
								if (paramTypes.length > parameterCounter
										&& (paramTypes[parameterCounter] == javax.servlet.http.HttpServletRequest.class || paramTypes[parameterCounter] == javax.servlet.http.HttpServletResponse.class))
									parameterCounter++;
								if (paramTypes.length > parameterCounter
										&& (paramTypes[parameterCounter] == javax.servlet.http.HttpServletRequest.class || paramTypes[parameterCounter] == javax.servlet.http.HttpServletResponse.class))
									parameterCounter++;

								if (paramTypes.length > parameterCounter) {

									waldParam
											.setType(getQNameForType(paramTypes[parameterCounter]));
									parameterCounter++;
								}
							}

							String defaultValue = cleanDefault(param2
									.defaultValue());
							if (!defaultValue.equals("")) {
								waldParam.setDefault(defaultValue);
							}
							wadlRequest.getParam().add(waldParam);
						} else if (annotation2 instanceof PathVariable) {
							PathVariable param2 = (PathVariable) annotation2;

							Param waldParam = new Param();
							waldParam.setName(param2.value());
							waldParam.setStyle(ParamStyle.TEMPLATE);
							waldParam.setRequired(true);
							if (paramTypes != null
									&& paramTypes.length > parameterCounter) {
								if (paramTypes.length > parameterCounter
										&& (paramTypes[parameterCounter] == javax.servlet.http.HttpServletRequest.class || paramTypes[parameterCounter] == javax.servlet.http.HttpServletResponse.class))
									parameterCounter++;
								if (paramTypes.length > parameterCounter
										&& (paramTypes[parameterCounter] == javax.servlet.http.HttpServletRequest.class || paramTypes[parameterCounter] == javax.servlet.http.HttpServletResponse.class))
									parameterCounter++;

								if (paramTypes.length > parameterCounter) {

									waldParam
											.setType(getQNameForType(paramTypes[parameterCounter]));
									parameterCounter++;
								}
							}

							wadlRequest.getParam().add(waldParam);
						} else
							parameterCounter++;
					}
				}
				if (!wadlRequest.getParam().isEmpty()) {
					wadlMethod.setRequest(wadlRequest);
				}

				// Response
				if (!mediaTypes.isEmpty()) {
					Response wadlResponse = new Response();
					wadlResponse.getStatus().add(200l);
					for (MediaType mediaType : mediaTypes) {
						Representation wadlRepresentation = new Representation();
						wadlRepresentation.setMediaType(mediaType.toString());
						wadlResponse.getRepresentation()
								.add(wadlRepresentation);
					}
					wadlMethod.getResponse().add(wadlResponse);
				}

				wadlResource.getMethodOrResource().add(wadlMethod);

			}

			wadlResources.getResource().add(wadlResource);

		}
		result.getResources().add(wadlResources);

		return result;
	}

	private String getBaseUrl(HttpServletRequest request) {

		return request.getScheme() + "://" + request.getServerName() + ":"
				+ request.getServerPort() + "" + request.getContextPath() + "/"
				+ request.getServletPath().substring(1);
	}

	private String cleanDefault(String value) {
		value = value.replaceAll("\t", "");
		value = value.replaceAll("\n", "");
		value = value.replaceAll("?", "");
		value = value.replaceAll("?", "");
		value = value.replaceAll("?", "");
		return value;
	}

	/**
	 * This is an private function, which will return the QName based upon the
	 * Java Type.
	 * 
	 * @param classType
	 *            : Represent the type of class
	 * @return QName
	 */
	private QName getQNameForType(Class<?> classType) {
		QName qName = null;

		/**
		 * Check whether the thing that is coming is an Array of a data type or
		 * not.
		 */
		if (classType.isArray()) {
			classType = classType.getComponentType();
		}

		if (classType == java.lang.Long.class)
			qName = new QName("http://www.w3.org/2001/XMLSchema", "long");
		else if (classType == java.lang.Integer.class)
			qName = new QName("http://www.w3.org/2001/XMLSchema", "integer");
		else if (classType == java.lang.Double.class)
			qName = new QName("http://www.w3.org/2001/XMLSchema", "double");
		else if (classType == java.lang.String.class)
			qName = new QName("http://www.w3.org/2001/XMLSchema", "string");
		else if (classType == java.util.Date.class)
			qName = new QName("http://www.w3.org/2001/XMLSchema", "date");

		return qName;
	}
}
