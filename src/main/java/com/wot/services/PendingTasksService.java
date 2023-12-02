package com.wot.services;

import com.wot.entitites.PendingTasks;
import com.wot.payloads.TaskStatusCode;
import com.wot.repositories.PendingTasksRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PendingTasksService {

	@Autowired
	private PendingTasksRepo pendingTasksRepo;
	@Autowired
	private TaskStatusService taskStatusService;

	public boolean saveIncomingTaskRequest(PendingTasks pendingTasks) {
		if(pendingTasks !=null)
		{
			pendingTasksRepo.save(pendingTasks);
			return  true;
		}
		return  false;
	}

	public List<PendingTasks> getAllPendingTaskOfCurrentClient(int id) {

		return pendingTasksRepo.getTaskOfCurrentUser(id);
	}

	public List<PendingTasks> getAllTasksOfClients() {
		return pendingTasksRepo.findAll();
	}

	public PendingTasks getTaskById(int id) {
		return pendingTasksRepo.findIncomingTaskDetailsById(id);

	}

	public boolean saveTask(PendingTasks pendingTasks) {
		PendingTasks pendingTasks2 = this.getTaskById(pendingTasks.getId());
		pendingTasks2.setAttachment(pendingTasks.getAttachment());
		pendingTasks2.setCount(pendingTasks.getCount());
		pendingTasks2.setDeadline(pendingTasks.getDeadline());
		pendingTasks2.setReferencestyle(pendingTasks.getReferencestyle());
		pendingTasks2.setRequirements(pendingTasks.getRequirements());
		pendingTasks2.setCharges(pendingTasks.getCharges());
		pendingTasks2.setStatus(taskStatusService.getTaskStatusByStatus(TaskStatusCode.IN_REVIEW));
        return pendingTasksRepo.save(pendingTasks2) != null;

    }

	public void deleteTaskById(int id) {
		pendingTasksRepo.deleteById(id);
	}

	public List<PendingTasks> getAllTasksByStatus(String status) {

		return pendingTasksRepo.getAllTasksByStatus(status);
	}

	public boolean setFailedQueryForClient(PendingTasks pendingTasks) {
		PendingTasks pendingTasks1 = this.getTaskById(pendingTasks.getId());
		pendingTasks1.setQuery(pendingTasks.getQuery());
		pendingTasks1.setStatus(taskStatusService.getTaskStatusByStatus(TaskStatusCode.FAILED));
        return pendingTasksRepo.save(pendingTasks1) != null;
    }

    public List<PendingTasks> getPendingTaskOfClientByEmail(String email) {
		return  pendingTasksRepo.getPendingTaskOfClientByEmail(email);
    }
}
