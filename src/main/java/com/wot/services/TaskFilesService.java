package com.wot.services;

import com.wot.entitites.TaskFiles;
import com.wot.repositories.TaskFilesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TaskFilesService {

    @Autowired
    private TaskFilesRepo taskFilesRepo;

    public boolean saveFileDetails(TaskFiles taskFiles)
    {
        if(taskFiles !=null)
        {
            taskFilesRepo.save(taskFiles);
            return  true;
        }
        else return  false;

    }





}
