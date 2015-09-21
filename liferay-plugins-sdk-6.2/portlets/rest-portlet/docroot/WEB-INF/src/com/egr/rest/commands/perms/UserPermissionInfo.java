package com.egr.rest.commands.perms;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.model.ExpandoTableConstants;
import com.liferay.portlet.expando.service.ExpandoTableLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoValueLocalServiceUtil;

public class UserPermissionInfo {

	private User user;
	private long companyId;
	private String userType;
	private String userClass;
	private long assignedOrgId;
	private long assignedOrgGroupId;
	private String taxId;
	private String accountNumber;

	// Making the constructor private so no one else create the instance from
	// outside.
	private UserPermissionInfo() {

	}

	private PermissionChecker permissionChecker;
	private Map<String, Boolean> portletPermissions = new HashMap<String, Boolean>();

	private static Logger _logger = LoggerFactory.getLogger(UserPermissionInfo.class);

	/**
	 * Add detailed comments on why we are caching the permissionChecker and Map
	 * of permissions.
	 * 
	 * @param portletName
	 * @param permissionName
	 * @return
	 */
	public boolean hasPermission(String portletName, String permissionName) {
		boolean hasPermission = false;
		try {
			String permissionKey = portletName + permissionName;
			if (portletPermissions.containsKey(permissionKey)) {
				return portletPermissions.get(permissionKey);
			} else {
				if (null == permissionChecker) {
					permissionChecker = PermissionCheckerFactoryUtil.create(user);
				}
				hasPermission = permissionChecker.hasPermission(assignedOrgGroupId, portletName, 0, permissionName);
				_logger.debug("-------------->>> " + permissionKey + "=" + hasPermission);
				portletPermissions.put(permissionKey, hasPermission);
			}
		} catch (Exception e) {
			_logger.error("Exception while checking the permission", e);
		}
		return hasPermission;
	}

	public HashMap<String, Boolean> getUserPermissions(Properties permissionProperties) {
		HashMap<String, Boolean> userPermissions = new HashMap<String, Boolean>();
		Boolean hasPerm = false;

		Set<Object> permissionKeySet = permissionProperties.keySet();
		for (Object keyObject : permissionKeySet) {
			String key = (String) keyObject;
			String permissionValue = permissionProperties.getProperty(key);
			String[] permParts = StringUtils.commaDelimitedListToStringArray(permissionValue);
			if (permParts.length != 2) {
				continue;
			}
			String permName = permParts[0];
			String portletName = permParts[1];
			hasPerm = hasPermission(portletName, permName);
			userPermissions.put(key, hasPerm);
		}
		return userPermissions;
	}

	public static UserPermissionInfo getUserPermissionInfo(HttpServletRequest request) {
		UserPermissionInfo userPermissionInfo = (UserPermissionInfo) request.getAttribute(ICommandKeys.USER_PERMISSION_PARAM_NAME);
		try {
			if (null == userPermissionInfo) {
				userPermissionInfo = new UserPermissionInfo();
				User user = PortalUtil.getUser(request);
				if (null != user) {
					userPermissionInfo.setUser(user);
					userPermissionInfo.setCompanyId(user.getCompanyId());
				}
				HttpSession session = request.getSession();
				String userType = (String) session.getAttribute(ICommandKeys.USERTYPE_SESSION_KEY);
				if (userType == null) {
					userType = ICommandKeys.UNKNOWN_USER_TYPE;
				}
				userPermissionInfo.setUserType(userType);
				request.setAttribute(ICommandKeys.USER_PERMISSION_PARAM_NAME, userPermissionInfo);
			}
		} catch (Exception e) {
			_logger.error("Exception while getting the user permission", e);
		}
		return userPermissionInfo;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserClass() {
		return userClass;
	}

	public void setUserClass(String userClass) {
		this.userClass = userClass;
	}

	public long getAssignedOrgId() {
		return assignedOrgId;
	}

	public void setAssignedOrgId(long assignedOrgId) {
		this.assignedOrgId = assignedOrgId;
	}

	public long getAssignedOrgGroupId() {
		return assignedOrgGroupId;
	}

	public void setAssignedOrgGroupId(long assignedOrgGroupId) {
		this.assignedOrgGroupId = assignedOrgGroupId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	private static Object getExpandoValue(Organization org, String columnName) {
		Object value = null;
		try {
			ExpandoTable expandoTable = ExpandoTableLocalServiceUtil.getTable(org.getCompanyId(), Organization.class.getName(), ExpandoTableConstants.DEFAULT_TABLE_NAME);

//			if (columnName.equals(IConstants.GUIDEWIRE_ORG_CUSTOM_FIELD_NAME) || columnName.equals(IConstants.AGENCY_ORG_CUSTOM_FIELD_NAME)) {
//				value = ExpandoValueLocalServiceUtil.getData(org.getCompanyId(), Organization.class.getName(), expandoTable.getName(), columnName, org.getPrimaryKey(), false);
//			} else {
//				value = ExpandoValueLocalServiceUtil.getData(org.getCompanyId(), Organization.class.getName(), expandoTable.getName(), columnName, org.getPrimaryKey(), "");
//			}
			value = ExpandoValueLocalServiceUtil.getData(org.getCompanyId(), Organization.class.getName(), expandoTable.getName(), columnName, org.getPrimaryKey(), "");
		} catch (Exception e) {
			_logger.error("Exception while getting the expando value", e);
		}
		return value;
	}
}
