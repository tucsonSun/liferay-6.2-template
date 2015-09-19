<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>

<%
 	Map<String, String> jspUserInfo = (Map<String, String>)session.getAttribute("LIFERAY_SHARED_JSP_USER_INFO");
	HashMap<String, String> myListKeys = new HashMap<String, String>();
	myListKeys.put("USER_TYPE", "UNKNOWN");
	myListKeys.put("USER_CLASS", "UNKNOWN");
	myListKeys.put("HAS_USER_MGMT","false");
	
	//If session contains jspInfo then override default value
	if (jspUserInfo != null) {
		for (String key : myListKeys.keySet()) {
			myListKeys.put(key, jspUserInfo.get(key));
		}
	}

	//
	//Build javascript object
	//
	StringBuilder sb = new StringBuilder();
	sb.append("  var _userInfo = {};\n");
	for (String key : myListKeys.keySet()) {
		String value = myListKeys.get(key);
		//if boolean use this format
		if ("false".equals(value.toLowerCase()) || "true".equals(value.toLowerCase())) {
			sb.append(String.format("  _userInfo.%s=%s;\n",key,value));
		}
		//if string use this format
		else {
			sb.append(String.format("  _userInfo.%s=\"%s\";\n",key,value));
		}
	}
	
	
	out.println("\n<script>\n");
    out.println(sb.toString());
    out.println("</script>\n\n");
 %>