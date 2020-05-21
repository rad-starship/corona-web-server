package com.rad.server.web.corona.services;

import org.springframework.http.HttpHeaders;

public interface NmsAccessService
{
	Object getUsers(HttpHeaders headers);
	Object getUserToken(HttpHeaders headers);
	Object getRoles(HttpHeaders headers);
	Object getTenants(HttpHeaders headers);
	Object getPermissions(HttpHeaders headers);
	Object getTenantsForCorona(HttpHeaders headers);
	Object addUser(Object user, HttpHeaders headers);
	Object addRole(Object role, HttpHeaders headers);
	Object addTenant(Object tenant, HttpHeaders headers);

	Object deleteRole(String roleName, HttpHeaders headers);
	Object deleteRole(long roleId, HttpHeaders headers);

	Object deleteUser(long id, HttpHeaders headers);
	Object deleteTenant(long id, HttpHeaders headers);


    Object updateRole(Long roleId, Object roleDetailes, HttpHeaders headers);
	Object updateUser(Long id, Object user, HttpHeaders headers);
	Object updateTenant(Long id, Object tenant, HttpHeaders headers);


    Object postSettings(Object settings, HttpHeaders headers);

    Object login(Object loginDetails);
}