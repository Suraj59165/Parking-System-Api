package com.wot.controllers;

import com.wot.entitites.Client;
import com.wot.services.ClientService;
import com.wot.services.PendingClientsService;
import com.wot.services.ManagerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

	@Autowired
	private PendingClientsService pendingClientsService;
	@Autowired
	private ClientService clientService;
	@Autowired
	private ManagerService managerService;

	@ModelAttribute
	public void commonData(Principal principal, Model model, HttpSession session) {
		model.addAttribute("name", managerService.getCurrentLoggedInManager(principal.getName()).getName());
	}

	@GetMapping("/dashboard")
	public String dashboard() {
		return "admin/admin_dashboard";
	}

	@GetMapping("/managers-info")
	public String managersInformation() {
		return "admin/managers_info";
	}

	@GetMapping("/clients-requests")
	public String getAllClientsRequest(Model model) {
		model.addAttribute("managers", managerService.getAllManagers());
		model.addAttribute("clients", pendingClientsService.getAllClientsForApproval());

		return "admin/client_requests";
	}

	@PostMapping("/client-data")
	public ResponseEntity<HttpStatus> savingApprovedClientData(@ModelAttribute Client client,
			@RequestParam(value = "managerId") String id) {
		if (clientService.savingApprovedClientData(client, id)) {
			return ResponseEntity.ok(HttpStatus.ACCEPTED);
		}
		return ResponseEntity.ok(HttpStatus.NOT_ACCEPTABLE);
	}

}
