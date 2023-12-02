package com.wot.repositories;

import com.wot.entitites.TaskFiles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskFilesRepo extends JpaRepository<TaskFiles, String> {

}
