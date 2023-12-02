package com.wot.controllers;

import com.wot.entitites.Client;
import com.wot.entitites.PendingTasks;
import com.wot.entitites.Task;
import com.wot.payloads.TaskStatusCode;
import com.wot.services.PendingTasksService;
import com.wot.services.ManagerService;
import com.wot.services.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/manager")
@PreAuthorize("hasRole('ROLE_MANAGER')")
public class ManagerController {

	@Autowired
	private ManagerService managerService;
	@Autowired
	private PendingTasksService pendingTasksService;
	@Autowired
	private TaskService taskService;

	@ModelAttribute
	public void commonData(Principal principal, Model model, HttpSession session) {
		model.addAttribute("name", managerService.getCurrentLoggedInManager(principal.getName()).getName());
	}

	@GetMapping("/dashboard")
	public String home() {
		return "manager/manager_dashboard";
	}

	@GetMapping("/clients")
	public String getAllocatedClient(Principal principal, Model model) {
		model.addAttribute("clients", managerService.getCurrentLoggedInManager(principal.getName()).getClient());
		return "manager/my_clients";

	}

	@GetMapping("/task")
	public String getMyClientTask(Model model, Principal principal) {
		List<PendingTasks> tempList = new ArrayList<>();
		for (Client client : managerService.getCurrentLoggedInManager(principal.getName()).getClient()) {
			for (PendingTasks tasks : client.getPendingTasks()) {
				tempList.add(tasks);
			}
		}
		model.addAttribute("tasks", tempList);
		return "manager/all-task";
	}

	@PostMapping("/query")
	public ResponseEntity<HttpStatus> QueryForClient(@ModelAttribute PendingTasks pendingTasks) {
		if(pendingTasksService.setFailedQueryForClient(pendingTasks))
			{
			return ResponseEntity.ok(HttpStatus.ACCEPTED);
			}
		return ResponseEntity.ok(HttpStatus.NOT_ACCEPTABLE);
	}

	@PostMapping("/approve-task")
	public ResponseEntity<HttpStatus> approveTask(@ModelAttribute Task task, @RequestParam("clientId") String id) {
		if (taskService.getApprovedTaskForSaving(task, Integer.parseInt(id))) {
			return ResponseEntity.ok(HttpStatus.ACCEPTED);
		}
		return ResponseEntity.ok(HttpStatus.NOT_ACCEPTABLE);
	}

	@GetMapping("/review-task")
	public String getAllReviewedTask(Model model) {

		model.addAttribute("review_task", pendingTasksService.getAllTasksByStatus(TaskStatusCode.IN_REVIEW));
		System.out.println();
		return "manager/review-task";
	}
	
	@GetMapping("/approved-task")
	public String getAllApprovedTask(Model model,Principal principal)
	{
		model.addAttribute("approved_task",managerService.getAllApprovedTaskOfManagerClient(principal.getName(),TaskStatusCode.APPROVED));
		return "manager/approved-task";
	}

	@GetMapping("/failed-task")
	public String getAllFailedTask(Model model) {
		model.addAttribute("failed_task", pendingTasksService.getAllTasksByStatus(TaskStatusCode.FAILED));

		return "manager/failed-task";
	}

	@PostMapping("/client-amount")
	@ResponseBody
	public ResponseEntity<Boolean> setClientAmountForTask(@RequestBody HashMap<String,String> hashMap)
	{
		int amount=Integer.parseInt(hashMap.get("amount"));
		String notes=hashMap.get("notes");

		int taskId= Integer.parseInt(hashMap.get("taskId"));

		System.out.println(hashMap);
		return  ResponseEntity.status(HttpStatus.OK).body(taskService.setClientAmountForTask(taskId,amount,notes));
	}


}