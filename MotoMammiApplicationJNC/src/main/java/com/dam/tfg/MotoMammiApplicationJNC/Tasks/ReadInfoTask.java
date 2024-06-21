package com.dam.tfg.MotoMammiApplicationJNC.Tasks;

import com.dam.tfg.MotoMammiApplicationJNC.Services.Impl.ProcesServiceImpl;
import com.dam.tfg.MotoMammiApplicationJNC.Services.ProcesService;
import com.dam.tfg.MotoMammiApplicationJNC.Utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReadInfoTask {
    @Autowired
    private ProcesService procesService;
    @Scheduled(cron = "${cron.task.getcustomers}")
    public void task(){
        try{
            //procesService.readFileInfo(Constants.SOURCE_CUSTOMERS,null,null);
        } catch (Exception e){
            e.getMessage();
        }
    }
}
