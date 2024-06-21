package com.dam.tfg.MotoMammiApplicationJNC.ControllersAPI;

import com.dam.tfg.MotoMammiApplicationJNC.Services.ProcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.dam.tfg.MotoMammiApplicationJNC.Utils.Constants;

@RestController
public class Controller {
    @Autowired
    ProcesService procesService;

    @RequestMapping(value =("/appInsurance/v1/readInfoFileJNC/{resource}/{codprov}/{date}"),
            method = RequestMethod.GET,
            produces = "application/json")
    String callProcessReadInfo(@PathVariable String resource,
                               @PathVariable String codprov,
                               @PathVariable String date//"20240423"
    ){
        if (resource.isEmpty()){
            return "ERROR: El resource es obligatori";
        }

        try{
            System.out.println("\nEsta tarea se lanza cada 15 segundos");
            procesService.readFileInfo(resource,codprov,date);
        } catch (Exception e){
            System.err.println("heey pero me estoy poniendo peluche yo 😏😏");
        }
        System.out.println("El valor de resource es: "+resource);
        return "Buenos dias";
    }

    @RequestMapping(value =("/appInsurance/v1/processInfoFileJNC/{resource}/{codprov}/{date}"),
            method = RequestMethod.GET,
            produces = "application/json")
    String callIntegrateInfo(@PathVariable String resource,
                             @PathVariable String codprov,
                             @PathVariable String date //"20240423"
    ){
        if (resource.isEmpty()){
            return "ERROR: El resource es obligatori";
        }

        try{
            System.out.println("\nEsta tarea se lanza cada 15 segundos");
            procesService.integrateInfo(resource,codprov);
        } catch (Exception e){
            System.err.println("heey pero me estoy poniendo peluche yo 😏😏");
        }
        System.out.println("El valor de resource es: "+resource);
        return "Buenos dias";
    }

    @RequestMapping(value =("/appInsurance/v1/genInvoiceFileJNC/{codprov}/{date}"),
            method = RequestMethod.GET,
            produces = "application/json")
    String genInvoiceFile(   @PathVariable String codprov,
                             @PathVariable String date //"20240423"
    ){
        try{
            System.out.println("\nEsta tarea se lanza cada 15 segundos");
            procesService.genInvoiceFile(codprov, date);
        } catch (Exception e){
            System.err.println("heey pero me estoy poniendo peluche yo 😏😏");
            e.printStackTrace();
        }
        return "Buenos dias";
    }
}