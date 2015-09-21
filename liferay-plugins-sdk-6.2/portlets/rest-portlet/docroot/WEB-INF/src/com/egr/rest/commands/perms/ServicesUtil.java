package com.egr.rest.commands.perms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;

public class ServicesUtil {


	public enum UserType {
		EMPLOYEE, CUSTOMER, BROKER, UNKNOWN
	};

	private static final Logger _logger = LoggerFactory.getLogger(ServicesUtil.class);


	public static UserType getUserType(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String userType = (String) session.getAttribute(IPermission.USERTYPE_SESSION_KEY);
		if (userType == null)
			return UserType.UNKNOWN;
		return UserType.valueOf(userType.toUpperCase());
	}



	/**
	 * Initializes the permission checker. If the user is null , gets the default admin user.
	 * 
	 * @param companyId
	 * @param user
	 * @throws Exception
	 */
	public static void initializePermissionChecker(long companyId, User user) throws Exception {
		if (null == user) {
			user = UserLocalServiceUtil.getUserByScreenName(companyId, PropsUtil.get(PropsKeys.DEFAULT_ADMIN_SCREEN_NAME));
		}
		PrincipalThreadLocal.setName(user.getUserId());
		PermissionChecker permissionChecker = PermissionCheckerFactoryUtil.create(user);
		PermissionThreadLocal.setPermissionChecker(permissionChecker);
	}

	/**
	 * Removes the permission checker from the thread local.
	 */
	public static void disablePermissionChecker() {
		if (null != PermissionThreadLocal.getPermissionChecker()) {
			PermissionThreadLocal.setPermissionChecker(null);
		}
	}


}
