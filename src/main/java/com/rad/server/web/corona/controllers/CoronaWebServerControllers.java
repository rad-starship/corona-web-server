package com.rad.server.web.corona.controllers;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import com.rad.server.web.corona.services.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author raz_o
 */
@Controller
@CrossOrigin(origins = {"http://localhost:4200"})
public class CoronaWebServerControllers
{
	@Autowired
	private CoronaVirusService	coronaVirusService;

	@Autowired
	private NmsAccessService	nmsAccessService;




    @CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/users")
	@ResponseBody
	public Object getUsers()
	{
		return nmsAccessService.getUsers();
	}

    @CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/users")
	@ResponseBody
	public Object addUser(@RequestBody Object user)
	{
		return nmsAccessService.addUser(user);
	}

    @CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/roles")
	@ResponseBody
	public Object getRoles()
	{
		return nmsAccessService.getRoles();
	}

    @CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/roles")
	@ResponseBody
	public Object addRole(@RequestBody Object role)
	{
		return nmsAccessService.addRole(role);
	}

	@DeleteMapping("/roles/{name}")
	@ResponseBody
	public Map<String, Boolean> deleteRole(@PathVariable(value = "name") String roleName){
    	nmsAccessService.deleteRole(roleName);
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
	
	@GetMapping("/corona")
	@ResponseBody
	public Object getCoronaData()
	{
		return coronaVirusService.getCoronaVirusData();
	}
}