package com.dam.tfg.MotoMammiApplicationJNC.Repositories;

import com.dam.tfg.MotoMammiApplicationJNC.Models.CustomerDTO;
import com.dam.tfg.MotoMammiApplicationJNC.Models.InvoiceDTO;
import com.dam.tfg.MotoMammiApplicationJNC.Models.InvoicesDTO;
import com.dam.tfg.MotoMammiApplicationJNC.Models.VehiclesDTO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InvoiceRepository implements ObjectRepository<InvoiceDTO> {

    @Override
    public void storeList(List<InvoiceDTO> t) {

    }

    @Override
    public void store(InvoiceDTO invoiceDTO) {

    }

    @Override
    public List<InvoiceDTO> retrieve(String pSource, String date) {
        return null;
    }

    @Override
    public InvoiceDTO search(String codProv, String date, String pSource) {
        InvoicesRepository invoicesRepository = new InvoicesRepository();
        CustomerRepository customerRepository = new CustomerRepository();
        VehicleRepository vehicleRepository = new VehicleRepository();

        List<InvoicesDTO> invoicesDTO = invoicesRepository.retrieve(codProv, date);

        for (InvoicesDTO invoices : invoicesDTO) {
            CustomerDTO customerDTO = customerRepository.search(invoices.getDni(), codProv, "");
            VehiclesDTO vehicleDTO = vehicleRepository.search(invoices.getCarPlate(), codProv, "");

            InvoiceDTO invoiceDTO = new InvoiceDTO();

            invoiceDTO.setDniCustomer(customerDTO.getDni());
            invoiceDTO.setNameCustomer(customerDTO.getName());
            invoiceDTO.setfirstNameCustomer(customerDTO.getFirstName());
            invoiceDTO.setlastNameCustomer(customerDTO.getLastName());
            invoiceDTO.setModelVehicle(vehicleDTO.getModel());
            invoiceDTO.setcarPlateVehicle(vehicleDTO.getCarPlate());
            invoiceDTO.setIssueDate(invoices.getIssueDate());
            invoiceDTO.setCost(invoices.getCost());
            invoiceDTO.setInvoiceNumber(invoices.getInvoiceNumber());

            return invoiceDTO;
        }
        return null;

    }

    @Override
    public List<InvoiceDTO> searchList(String codProv, String date) {
        InvoicesRepository invoicesRepository = new InvoicesRepository();
        CustomerRepository customerRepository = new CustomerRepository();
        VehicleRepository vehicleRepository = new VehicleRepository();

        List<InvoicesDTO> invoicesDTOList = invoicesRepository.retrieve(codProv, date);
        List<InvoiceDTO> invoiceDTOList = new ArrayList<>();

        for (InvoicesDTO invoices : invoicesDTOList) {
            CustomerDTO customerDTO = customerRepository.search(invoices.getDni(), codProv, "");
            VehiclesDTO vehicleDTO = vehicleRepository.search(invoices.getCarPlate(), codProv, "");

            InvoiceDTO invoiceDTO = new InvoiceDTO();

            invoiceDTO.setDniCustomer(customerDTO.getDni());
            invoiceDTO.setNameCustomer(customerDTO.getName());
            invoiceDTO.setfirstNameCustomer(customerDTO.getFirstName());
            invoiceDTO.setlastNameCustomer(customerDTO.getLastName());
            invoiceDTO.setModelVehicle(vehicleDTO.getModel());
            invoiceDTO.setcarPlateVehicle(vehicleDTO.getCarPlate());
            invoiceDTO.setIssueDate(invoices.getIssueDate());
            invoiceDTO.setCost(invoices.getCost());
            invoiceDTO.setInvoiceNumber(invoices.getInvoiceNumber());

            invoiceDTOList.add(invoiceDTO);
        }

        return invoiceDTOList;
    }

    @Override
    public InvoiceDTO delete(int id) {
        return null;
    }

    @Override
    public InvoiceDTO update(InvoiceDTO invoiceDTO) {
        return null;
    }
}
