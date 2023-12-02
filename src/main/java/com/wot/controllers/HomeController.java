package com.wot.controllers;

import com.wot.entitites.PendingClients;
import com.wot.entitites.PendingTasks;
import com.wot.entitites.Manager;
import com.wot.entitites.Roles;
import com.wot.entitites.TaskStatus;
import com.wot.payloads.RoleStatusCode;
import com.wot.payloads.TaskStatusCode;
import com.wot.security.SecurityConfiguration;
import com.wot.services.ClientService;
import com.wot.services.ManagerService;
import com.wot.services.RoleService;
import com.wot.services.TaskService;
import com.wot.services.TaskStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class HomeController {

	@Autowired
	private ClientService clientService;
	@Autowired
	private SecurityConfiguration config;
	@Autowired
	private RoleService roleService;
	@Autowired
	private ManagerService managerService;
	@Autowired
	private SecurityConfiguration configuration;
	@Autowired
	private TaskStatusService taskStatusService;
	@Autowired
	private TaskService taskService;

	@GetMapping("/")
	public String welcome(Model model) {
		model.addAttribute("title", "Welcome ");

		if (roleService.getRoleForUser("ROLE_CLIENT") == null) {
			Roles roles = new Roles();
			roles.setId(new Random().nextInt(100));
			roles.setName(RoleStatusCode.ADMIN);
			Roles roles2 = new Roles();
			roles2.setId(new Random().nextInt(100));
			roles2.setName(RoleStatusCode.MANAGER);
			Roles roles3 = new Roles();
			roles3.setId(new Random().nextInt(100));
			roles3.setName(RoleStatusCode.CLIENT);
			Roles roles4 = new Roles();
			roles4.setId(new Random().nextInt(100));
			roles4.setName(RoleStatusCode.ACCOUNT_MANAGER);

			List<Roles> adminList = new ArrayList<>();
			adminList.add(roles);

			List<Roles> managerList = new ArrayList<>();
			managerList.add(roles2);

			Manager admin = new Manager();
			admin.setId(new Random().nextInt(1000));
			admin.setName("Adarsh");
			admin.setEmail("adarsh@gmail.com");
			admin.setPassword(configuration.passwordEncoder().encode("adarsh"));
			admin.setRoles(adminList);

			Manager manager1 = new Manager();
			manager1.setId(new Random().nextInt(1000));
			manager1.setName("Nancy");
			manager1.setEmail("nancy@gmail.com");
			manager1.setPassword(configuration.passwordEncoder().encode("nancy"));
			manager1.setRoles(managerList);

			Manager manager2 = new Manager();
			manager2.setId(new Random().nextInt(1000));
			manager2.setName("Ravi");
			manager2.setEmail("ravi@gmail.com");
			manager2.setPassword(configuration.passwordEncoder().encode("ravi"));
			manager2.setRoles(managerList);

			Manager manager3 = new Manager();
			manager3.setId(new Random().nextInt(1000));
			manager3.setName("nishant");
			manager3.setEmail("nishant@gmail.com");
			manager3.setPassword(configuration.passwordEncoder().encode("nishant"));
			manager3.setRoles(managerList);

			Manager manager4 = new Manager();
			manager4.setId(new Random().nextInt(1000));
			manager4.setName("Suraj");
			manager4.setEmail("suraj@gmail.com");
			manager4.setPassword(configuration.passwordEncoder().encode("suraj"));
			manager4.setRoles(managerList);

			List<Manager> managers = new ArrayList<>();
			managers.add(admin);
			managers.add(manager1);
			managers.add(manager2);
			managers.add(manager3);
			managers.add(manager4);

			TaskStatus review = new TaskStatus();
			review.setId(new Random().nextInt(100));
			review.setStatus(TaskStatusCode.IN_REVIEW);

			TaskStatus approved = new TaskStatus();
			approved.setId(new Random().nextInt(100));
			approved.setStatus(TaskStatusCode.APPROVED);

			TaskStatus failed = new TaskStatus();
			failed.setId(new Random().nextInt(100));
			failed.setStatus(TaskStatusCode.FAILED);

			TaskStatus completed = new TaskStatus();
			completed.setId(new Random().nextInt(100));
			completed.setStatus(TaskStatusCode.COMPLETED);

			List<Roles> allRoles = new ArrayList<>();
			allRoles.add(roles);
			allRoles.add(roles2);
			allRoles.add(roles3);
			allRoles.add(roles4);

			roleService.saveAllRoles(allRoles);
			List<TaskStatus> status = new ArrayList<TaskStatus>();
			status.add(review);
			status.add(approved);
			status.add(failed);
			status.add(completed);
			taskStatusService.saveAllTaskStatus(status);

			taskStatusService.saveAllTaskStatus(status);
			roleService.saveAllRoles(adminList);
			roleService.saveAllRoles(managerList);
			managerService.saveManagers(managers);

		} else {

		}

		return "home";
	}

	@GetMapping("/signin")
	public String login(Model model) {
		model.addAttribute("title", "Login ");
		return "login";
	}

	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("title", "Signup here");
		return "signup";
	}

	@PostMapping("/user-request")
	public String SignUpUserRequest(@ModelAttribute PendingClients pendingClients, BindingResult result) {
		pendingClients.setPassword(config.passwordEncoder().encode(pendingClients.getPassword()));
		return (clientService.sendClientForApproval(pendingClients)) ? "signup" : "false";

	}
	
	@GetMapping("/task-status")
	@ResponseBody
	public List<PendingTasks> getStatusOfTask(Principal principal, Model model) {
		
		return clientService.getAllPendingTasksOfClient("jyotiengg.tools@gmail.com");
	}

}
