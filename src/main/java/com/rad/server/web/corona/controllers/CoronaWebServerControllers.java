package com.rad.server.web.corona.controllers;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import com.rad.server.web.corona.services.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import java.util.Map;

/**
 * @author raz_o
 */
@Controller
//@CrossOrigin(origins = {"http://localhost:4200"})
public class CoronaWebServerControllers
{
	@Autowired
	private CoronaVirusService	coronaVirusService;

	@Autowired
	private NmsAccessService	nmsAccessService;

	@GetMapping("/users")
	@ResponseBody
	public Object getUsers()
	{
		return nmsAccessService.getUsers();
	}

	@PostMapping("/users")
	@ResponseBody
	public Object addUser(@RequestBody Object user)
	{
		return nmsAccessService.addUser(user);
	}

	@DeleteMapping("/users/{id}")
	@ResponseBody
	public Object deleteUser(@PathVariable(value="id") Long id){
		nmsAccessService.deleteUser(id);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	@PutMapping("/users/{id}")
	@ResponseBody
	public Object updateUser(@PathVariable(value = "id")Long id,@RequestBody Object user){
		return nmsAccessService.updateUser(id,user);
	}


	@GetMapping("/roles")
	@ResponseBody
	public Object getRoles()
	{
		return nmsAccessService.getRoles();
	}

	@PostMapping("/roles")
	@ResponseBody
	public Object addRole(@RequestBody Object role)
	{
		return nmsAccessService.addRole(role);
	}

	@PutMapping("/roles/{id}")
	@ResponseBody
	public Object updateRole(		@PathVariable(value = "id") Long roleId,
									 @Valid @RequestBody Object roleDetailes){
	return nmsAccessService.updateRole(roleId,roleDetailes);

	}

	@DeleteMapping("/roles/{name}")
	@ResponseBody
	public Map<String, Boolean> deleteRole(@PathVariable(value = "name") String roleName){
    	nmsAccessService.deleteRole(roleName);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

    @DeleteMapping("/rolesid/{id}")
    @ResponseBody
    public Map<String, Boolean> deleteRole(@PathVariable(value = "id") long roleId){
        nmsAccessService.deleteRole(roleId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

	@GetMapping("/tenants")
	@ResponseBody
	public Object getTenants()
	{
		return nmsAccessService.getTenants();
	}

	@PostMapping("/tenants")
	@ResponseBody
	public Object addTenant(@RequestBody Object tenant)
	{
		return nmsAccessService.addTenant(tenant);
	}

	@DeleteMapping("/tenants/{id}")
	@ResponseBody
	public Object deleteTenant(@PathVariable(value = "id")long id){
		nmsAccessService.deleteTenant(id);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	@PutMapping("/tenants/{id}")
	@ResponseBody
	public Object updateTenant(@PathVariable(value = "id")Long id,@RequestBody Object tenant){
		return nmsAccessService.updateTenant(id,tenant);
	}

	
	@GetMapping("/corona/{tenant}")
	@ResponseBody
	public Object getCoronaData(@PathVariable String tenant)
	{
		return coronaVirusService.getCoronaVirusData(tenant);
	}
}