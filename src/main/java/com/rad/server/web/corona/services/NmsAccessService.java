package com.rad.server.web.corona.services;

public interface NmsAccessService
{
	Object getUsers();
	Object getRoles();
	Object getTenants();
	Object getPermissions();

	Object addUser(Object user);
	Object addRole(Object role);
	Object addTenant(Object tenant);

	Object deleteRole(String roleName);
	Object deleteRole(long roleId);

	Object deleteUser(long id);
	Object deleteTenant(long id);


    Object updateRole(Long roleId, Object roleDetailes);
	Object updateUser(Long id,Object user);
	Object updateTenant(Long id,Object tenant);


    Object postSettings(Object settings);

    Object login(Object loginDetails);
}