package com.wot.controllers;

import com.wot.entitites.Client;
import com.wot.entitites.ClientAccountBalance;
import com.wot.entitites.PendingTasks;
import com.wot.entitites.TaskFiles;
import com.wot.payloads.TaskStatusCode;
import com.wot.services.ClientService;
import com.wot.services.PendingTasksService;
import com.wot.services.TaskService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/client")
@PreAuthorize("hasRole('ROLE_CLIENT')")
public class ClientController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private PendingTasksService pendingTasksService;

    @ModelAttribute
    public void commonData(Principal principal, Model model, HttpSession session) {
        int balance=0;
       Client client= clientService.getCurrentUser(principal.getName());
       for(ClientAccountBalance clientAccountBalance:client.getClientAccountBalances())
       {
           balance+=Integer.parseInt(clientAccountBalance.getBalance());
       }
        model.addAttribute("client", client);
        model.addAttribute("balance", balance);

    }

    @GetMapping("/dashboard")
    public String dashboard(Principal principal) {
        return "normal/client_dashboard";
    }

    @GetMapping("/add-task")
    public String task() {
        return "normal/add_task";
    }

    @PostMapping("/save-task")
    public String addTask(@ModelAttribute PendingTasks pendingTasks, @RequestParam("clientfile") MultipartFile[] files,
                          Principal principal, HttpSession session) throws IOException {
        if (clientService.saveClientTask(pendingTasks, principal.getName(), files)) {
            session.setAttribute("message", "Successfully Saved");
        } else {
            session.setAttribute("message", "Error Saving Task");
        }
        return "normal/add_task";
    }

    @GetMapping("/task-status")
    public String getStatusOfTask(Principal principal, Model model) {
        model.addAttribute("clientTask", clientService.getAllPendingTasksOfClient(principal.getName()));
        model.addAttribute("approvedTask",
                taskService.getTaskOfUser(clientService.getCurrentUser(principal.getName()), TaskStatusCode.APPROVED));
        model.addAttribute("completed_task");
        return "normal/task-status";
    }

    @PostMapping("sending-again")
    public ResponseEntity<HttpStatus> sendAgainForReviewTask(@ModelAttribute PendingTasks pendingTasks) {
        if (pendingTasksService.saveTask(pendingTasks)) {
            return ResponseEntity.ok(HttpStatus.ACCEPTED);
        } else {
            return ResponseEntity.ok(HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @GetMapping("show-bills")
    public String showBillOfCurrentClientTask(Model model,Principal principal)
    {
        model.addAttribute("task_payment",taskService.getTaskOfUser(clientService.getCurrentUser(principal.getName()),TaskStatusCode.COMPLETED));

        return  "normal/show_bills";
    }

    @PostMapping("/create_order")
    @ResponseBody
    public String createOrder(@RequestBody Map<String, Object> data)
    {
        System.out.println(data);
        return  "done";
    }

}
