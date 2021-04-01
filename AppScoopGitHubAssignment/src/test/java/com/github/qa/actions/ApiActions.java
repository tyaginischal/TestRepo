package com.github.qa.actions;

import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

/**
 * The Class ApiActions.
 * 
 * @author Nischal Tyagi
 */
public class ApiActions {

	/**
	 * Raw to XML.
	 *
	 * @param r the r
	 * @return the xml path
	 */
	public XmlPath rawToXML(Response r) {

		String respon = r.asString();
		XmlPath x = new XmlPath(respon);
		return x;

	}

	/**
	 * Raw to json.
	 *
	 * @param r the r
	 * @return the json path
	 */
	public JsonPath rawToJson(Response r) {
		String respon = r.asString();
		JsonPath x = new JsonPath(respon);
		return x;
	}

	/**
	 * Gets the request json.
	 *
	 * @param request the request
	 * @return the request json
	 */
	public String getRequestJson(Response request) {
		return request.getBody().asString();
	}

	/**
	 * Gets the data from jsonpath.
	 *
	 * @param         <T> the generic type
	 * @param request the request
	 * @param jpath   the jpath
	 * @return the data from jsonpath
	 */
	public <T> T getDataFromJsonpath(Response request, String jpath) {
		JsonPath jsonpath = request.jsonPath();

		return jsonpath.get(jpath);
	}

}
