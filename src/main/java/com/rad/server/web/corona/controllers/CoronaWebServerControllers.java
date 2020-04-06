package com.rad.server.web.corona.controllers;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import com.rad.server.web.corona.services.*;

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