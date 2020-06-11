package com.rad.server.web.corona.controllers;

import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import com.rad.server.web.corona.services.*;

import javax.validation.Valid;
import java.util.*;

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
	public Object getUsers(@RequestHeader HttpHeaders headers)
	{
		return nmsAccessService.getUsers(headers);
	}

	@GetMapping("/users/getToken")
	@ResponseBody
	public Object getUserToken(@RequestHeader HttpHeaders headers)
	{
		return nmsAccessService.getUserToken(headers);
	}

	@PostMapping("/users")
	@ResponseBody
	public Object addUser(@RequestHeader HttpHeaders headers,@RequestBody Object user)
	{
		return nmsAccessService.addUser(user,headers);
	}

	@DeleteMapping("/users/{id}")
	@ResponseBody
	public Object deleteUser(@RequestHeader HttpHeaders headers,@PathVariable(value="id") Long id){
		return nmsAccessService.deleteUser(id,headers);
//		Map<String, Boolean> response = new HashMap<>();
//		response.put("deleted", Boolean.TRUE);
//		return response;
	}

	@PutMapping("/users/{id}")
	@ResponseBody
	public Object updateUser(@RequestHeader HttpHeaders headers,@PathVariable(value = "id")Long id,@RequestBody Object user){
		return nmsAccessService.updateUser(id,user,headers);
	}


	@GetMapping("/roles")
	@ResponseBody
	public Object getRoles(@RequestHeader HttpHeaders headers)
	{
		return nmsAccessService.getRoles(headers);
	}

	@PostMapping("/roles")
	@ResponseBody
	public Object addRole(@RequestHeader HttpHeaders headers,@RequestBody Object role)
	{
		return nmsAccessService.addRole(role,headers);
	}

	@PutMapping("/roles/{id}")
	@ResponseBody
	public Object updateRole(@RequestHeader HttpHeaders headers,
								@PathVariable(value = "id") Long roleId,
									 @Valid @RequestBody Object roleDetailes){
	return nmsAccessService.updateRole(roleId,roleDetailes,headers);

	}

	@DeleteMapping("/roles/{name}")
	@ResponseBody
	public Object deleteRole(@RequestHeader HttpHeaders headers,
			@PathVariable(value = "name") String roleName){
    	return nmsAccessService.deleteRole(roleName,headers);
//		Map<String, Boolean> response = new HashMap<>();
//		response.put("deleted", Boolean.TRUE);
//		return response;
	}

    @DeleteMapping("/rolesid/{id}")
    @ResponseBody
    public Object deleteRole(@RequestHeader HttpHeaders headers,
    		@PathVariable(value = "id") long roleId){
        return nmsAccessService.deleteRole(roleId,headers);

    }

    @GetMapping("/permissions")
	@ResponseBody
	public Object getPermissions(@RequestHeader HttpHeaders headers){return  nmsAccessService.getPermissions(headers);}

	@GetMapping("/tenants")
	@ResponseBody
	public Object getTenants(@RequestHeader HttpHeaders headers)
	{
		return nmsAccessService.getTenants(headers);
	}

	@PostMapping("/tenants")
	@ResponseBody
	public Object addTenant(@RequestHeader HttpHeaders headers,@RequestBody Object tenant)
	{
		return nmsAccessService.addTenant(tenant,headers);
	}

	@DeleteMapping("/tenants/{id}")
	@ResponseBody
	public Object deleteTenant(@RequestHeader HttpHeaders headers,@PathVariable(value = "id")long id){
		return nmsAccessService.deleteTenant(id,headers);
//		Map<String, Boolean> response = new HashMap<>();
//		response.put("deleted", Boolean.TRUE);
//		return response;
	}

	@PutMapping("/tenants/{id}")
	@ResponseBody
	public Object updateTenant(@RequestHeader HttpHeaders headers,@PathVariable(value = "id")Long id,@RequestBody Object tenant){
		return nmsAccessService.updateTenant(id,tenant,headers);
	}

	
	@GetMapping("/corona")
	@ResponseBody
	public Object getCoronaData(@RequestHeader HttpHeaders headers)
	{
		return coronaVirusService.getCoronaVirusData(headers);
	}

	@GetMapping("/totalsLatest")
	@ResponseBody
	public Object getTotalsLatest(@RequestHeader HttpHeaders headers)
	{
		return coronaVirusService.getTotalsLatest(headers);

	}

	@GetMapping("/tenants/tenantsForCorona")
	@ResponseBody
	public Object getTenantsForCorona(@RequestHeader HttpHeaders headers)
	{
		return nmsAccessService.getTenantsForCorona(headers);
	}

	@GetMapping("/totalsDaily")
	@ResponseBody
	public Object getTotalsDaily(@RequestHeader HttpHeaders headers,@RequestParam("date") String date)
	{
		return coronaVirusService.getTotalsDaily(date,headers);

	}



	@GetMapping("/countriesLatest")
	@ResponseBody
	public Object getCountriesLatest(@RequestHeader HttpHeaders headers)
	{
		return coronaVirusService.getCountriesLatest(headers);

	}


	@GetMapping("/countryDaily")
	@ResponseBody
	public Object getCountryDaily(@RequestHeader HttpHeaders headers,@RequestParam("date") long date, @RequestParam("countryName") String countryName)
	{
		return coronaVirusService.getCountryDaily(date, countryName,headers);

	}

	@PostMapping("/settings")
    @ResponseBody
    public Object postSettings(@RequestHeader HttpHeaders headers,@RequestBody Object settings){
	    return nmsAccessService.postSettings(settings,headers);
    }

    @PostMapping("/login")
	@ResponseBody
	public Object login(@RequestBody Object loginDetails){
		return nmsAccessService.login(loginDetails);
	}

	@PostMapping("/logout")
	@ResponseBody
	public Object logout(@RequestHeader HttpHeaders headers, @RequestBody Object refreshToken) { return  nmsAccessService.logout(refreshToken,headers);}



}