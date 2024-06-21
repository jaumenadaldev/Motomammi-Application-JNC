package com.dam.tfg.MotoMammiApplicationJNC.Services.Impl;

import com.dam.tfg.MotoMammiApplicationJNC.Models.*;
import com.dam.tfg.MotoMammiApplicationJNC.Repositories.*;
import com.dam.tfg.MotoMammiApplicationJNC.Services.ProcesService;
import com.dam.tfg.MotoMammiApplicationJNC.Utils.Constants;
import com.dam.tfg.MotoMammiApplicationJNC.Utils.MetodosGenericos;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProcesServiceImpl implements ProcesService {

    @Value("${nameFile.customers}")
    private String nameFileCustomers;
    @Value("${nameFile.vehicles}")
    private String nameFileVehicles;
    @Value("${nameFile.parts}")
    private String nameFileParts;
    @Value("${nameFile.in.ext}")
    private String extension;
    @Value("${path.in}")
    private String in;
    @Value("${nameFile.invoices}")
    private String nameInvoiceFile;
    @Value("${path.out}")
    private String out;
    @Value("${nameFile.out.ext}")
    private String outExtension;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private ProviderRepository providerRepository;
    @Autowired
    private InterfaceRepository interfaceRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PartsRepository partsRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private TranslationRepository translationRepository;
    @Autowired
    InvoiceRepository invoiceRepository;
    @Autowired
    InvoicesRepository invoicesRepository;

    private CustomerDTO customerDTO = new CustomerDTO();
    private VehiclesDTO vehicleDTO = new VehiclesDTO();
    private PartsDTO partsDTO = new PartsDTO();

    // Llegeix el contingut del fitxer i processa les dades segons el tipus de font (origen)
    @Override
    public void readFileInfo(String pSource, String codProv, String date) {
        try {
            System.out.println("Hora actual cada 15 segons: " + dateFormat.format(new Date()) + " pSource: " + pSource);
            List<ProviderDTO> proveidors = providerRepository.retrieve("", "");
            for (ProviderDTO prov : proveidors) {
                System.out.println(prov.getId() + " " + prov.getCodProv() + " " + prov.getNameProv() + " " + prov.getDateIni() + " " +
                        prov.getDateEnd() + " " + prov.getSwiAct() + " " + new Date());

                try {
                    String codigoProveedor;

                    if (codProv == null) {
                        codigoProveedor = prov.getCodProv();
                    } else {
                        codigoProveedor = codProv;
                    }
                    String path = getNomFitxer(pSource, codProv, date, prov.getCodProv());

                    File file = new File(path);
                    if (!file.exists()) {
                        System.out.println("Fitxer no trobat: " + path);
                        continue; // Salta al següent proveïdor si el fitxer no existeix
                    }

                    FileReader fr = new FileReader(path);
                    BufferedReader br = new BufferedReader(fr);
                    String linea;
                    br.readLine();
                    List<String> list = new ArrayList<>();
                    while ((linea = br.readLine()) != null) {
                        System.out.println(linea);
                        list.add(linea);
                    }

                    br.close();
                    switch (pSource) {
                        case Constants.SOURCE_CUSTOMERS:
                            processaDadesClient(list, codigoProveedor, pSource);
                            break;
                        case Constants.SOURCE_VEHICLES:
                            processaDadesVehicle(list, codigoProveedor, pSource);
                            break;
                        case Constants.SOURCE_PARTS:
                            processaDadesPart(list, codigoProveedor, pSource);
                            break;
                    }

                } catch (Exception e) {
                    System.out.println("Fitxer no trobat");
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.out.println("Error al llegir el fitxer");
            e.printStackTrace();
        }
    }

    // Processa les dades del client
    private void processaDadesClient(List<String> data, String codProv, String pSource) {

        Gson gson = new Gson();

        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        try {
            for (String d : data) {

                if (d.isEmpty()) {
                    continue;
                }

                InterfaceDTO interfaceDTO = new InterfaceDTO();
                String[] fields = d.split(";");
                Date dateBirth = MetodosGenericos.formatDate(fields[3]);

                customerDTO.setName(fields[0]);
                customerDTO.setFirstName(fields[1]);
                customerDTO.setLastName(fields[2]);
                customerDTO.setBirthDate(dateBirth);
                customerDTO.setPostalCode(fields[4]);
                customerDTO.setStreetType(fields[5]);
                customerDTO.setCity(fields[6]);
                customerDTO.setNumberStreet(Integer.parseInt(fields[7]));
                customerDTO.setPhoneNumber(fields[8]);
                customerDTO.setDni(fields[9]);
                customerDTO.setLicenseType(fields[10]);
                customerDTO.setCarPlate(fields[11]);
                customerDTO.setEmail(fields[12]);
                customerDTO.setGender(fields[13]);

                String json = gson.toJson(customerDTO);

                // Estableix els valors de InterfaceDTO segons sigui necessari
                interfaceDTO.setCodExternal(fields[9]);
                interfaceDTO.setCodProv(codProv);
                interfaceDTO.setContJson(json);
                interfaceDTO.setCreationDate(currentTimestamp);
                interfaceDTO.setLastUpdate(currentTimestamp);
                interfaceDTO.setCreatedBy("Admin");
                interfaceDTO.setUpdatedBy("Admin");
                interfaceDTO.setCodError(null);
                interfaceDTO.setErrorMessage(null);
                interfaceDTO.setStatusProcess("N");
                interfaceDTO.setOperation("NEW");
                interfaceDTO.setResource(pSource);

                int valid = validaInformacio(interfaceDTO, json, Constants.SOURCE_CUSTOMERS);

                System.out.println("Valor de valid: " + valid);

                if (valid == 0) {
                    interfaceRepository.store(interfaceDTO);
                } else if (valid == 1) {
                    interfaceDTO.setLastUpdate(currentTimestamp);
                    interfaceDTO.setUpdatedBy("Sistema de comparacio");
                    interfaceDTO.setOperation("UPD");
                    interfaceRepository.store(interfaceDTO);
                } else if (valid == 2) {
                    System.out.println("Coincidencia en l'entrada de les dades");
                }
            }

        } catch (Exception e) {
            if (!data.isEmpty()) {
                InterfaceDTO interfaceDTO = new InterfaceDTO();
                interfaceDTO.setCodError(220);
                interfaceDTO.setErrorMessage(e.getMessage());
                interfaceDTO.setStatusProcess("E");
                interfaceRepository.store(interfaceDTO);
                e.printStackTrace();
            }
        }
    }

    // Processa les dades del vehicle
    private void processaDadesVehicle(List<String> data, String codProv, String pSource) {

        Gson gson = new Gson();

        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        try {
            for (String d : data) {
                if (d.isEmpty()) {
                    continue;
                }

                InterfaceDTO interfaceDTO = new InterfaceDTO();
                String[] fields = d.split(";");

                vehicleDTO.setCarPlate(fields[0]);
                vehicleDTO.setVehicleType(fields[1]);
                vehicleDTO.setBrand(fields[2]);
                vehicleDTO.setModel(fields[3]);

                String json = gson.toJson(vehicleDTO);

                // Estableix els valors de InterfaceDTO segons sigui necessari
                interfaceDTO.setCodExternal(fields[0]);
                interfaceDTO.setCodProv(codProv);
                interfaceDTO.setContJson(json);
                interfaceDTO.setCreationDate(currentTimestamp);
                interfaceDTO.setLastUpdate(currentTimestamp);
                interfaceDTO.setCreatedBy("Admin");
                interfaceDTO.setUpdatedBy("Admin");
                interfaceDTO.setCodError(null);
                interfaceDTO.setErrorMessage(null);
                interfaceDTO.setStatusProcess("N");
                interfaceDTO.setOperation("NEW");
                interfaceDTO.setResource(pSource);

                int valid = validaInformacio(interfaceDTO, json, Constants.SOURCE_VEHICLES);
                System.out.println("Valor de valid: " + valid);
                if (valid == 0) {
                    interfaceRepository.store(interfaceDTO);
                } else if (valid == 1) {
                    interfaceDTO.setLastUpdate(currentTimestamp);
                    interfaceDTO.setUpdatedBy("Sistema de comparacio");
                    interfaceDTO.setOperation("UPD");
                    interfaceRepository.store(interfaceDTO);
                } else if (valid == 2) {
                    System.out.println("Coincidencia en l'entrada de les dades");
                }
            }

        } catch (Exception e) {
            if (!data.isEmpty()) {
                InterfaceDTO interfaceDTO = new InterfaceDTO();
                interfaceDTO.setCodError(220);
                interfaceDTO.setErrorMessage(e.getMessage());
                interfaceDTO.setStatusProcess("E");
                interfaceRepository.store(interfaceDTO);
                e.printStackTrace();
            }
        }
    }

    // Processa les dades dels parts
    private void processaDadesPart(List<String> data, String codProv, String pSource) {

        Gson gson = new Gson();

        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        try {
            for (String d : data) {
                if (d.isEmpty()) {
                    continue;
                }

                InterfaceDTO interfaceDTO = new InterfaceDTO();
                String[] fields = d.split(";");
                Date dateClaim = MetodosGenericos.formatDate(fields[3]);

                partsDTO.setDni(fields[0]);
                partsDTO.setClaimNumber(fields[1]);
                partsDTO.setPolicyNumber(fields[2]);
                partsDTO.setClaimDate(dateClaim);
                partsDTO.setDescription(fields[4]);
                partsDTO.setStatus((fields[5]));
                partsDTO.setAmount(Double.parseDouble(fields[6]));

                String json = gson.toJson(partsDTO);

                // Estableix els valors de InterfaceDTO segons sigui necessari
                interfaceDTO.setCodExternal(fields[1]);
                interfaceDTO.setCodProv(codProv);
                interfaceDTO.setContJson(json);
                interfaceDTO.setCreationDate(currentTimestamp);
                interfaceDTO.setLastUpdate(currentTimestamp);
                interfaceDTO.setCreatedBy("Admin");
                interfaceDTO.setUpdatedBy("Admin");
                interfaceDTO.setCodError(null);
                interfaceDTO.setErrorMessage(null);
                interfaceDTO.setStatusProcess("N");
                interfaceDTO.setOperation("NEW");
                interfaceDTO.setResource(pSource);

                int valid = validaInformacio(interfaceDTO, json, Constants.SOURCE_PARTS);
                System.out.println("Valor de valid: " + valid);
                if (valid == 0) {
                    interfaceRepository.store(interfaceDTO);
                } else if (valid == 1) {
                    interfaceDTO.setLastUpdate(currentTimestamp);
                    interfaceDTO.setUpdatedBy("Sistema de comparacio");
                    interfaceDTO.setOperation("UPD");
                    interfaceRepository.store(interfaceDTO);
                } else if (valid == 2) {
                    System.out.println("Coincidencia en l'entrada de les dades");
                }
            }

        } catch (Exception e) {
            if (!data.isEmpty()) {
                InterfaceDTO interfaceDTO = new InterfaceDTO();
                interfaceDTO.setCodError(220);
                interfaceDTO.setErrorMessage(e.getMessage());
                interfaceDTO.setStatusProcess("E");
                interfaceRepository.store(interfaceDTO);
                e.printStackTrace();
            }
        }
    }

    // Integra la informació de la interfície segons la font d'origen
    @Override
    public void integrateInfo(String pSource, String codProv) {
        List<InterfaceDTO> interfaceDTOS = interfaceRepository.retrieve(pSource, "");
        switch (pSource) {
            case Constants.SOURCE_CUSTOMERS:
                integraInfoClient(codProv, interfaceDTOS);
                break;
            case Constants.SOURCE_PARTS:
                integraInfoParts(codProv, interfaceDTOS);
                break;
            case Constants.SOURCE_VEHICLES:
                integraInfoVehicles(codProv, interfaceDTOS);
                break;
        }
    }

    // Integra la informació del client
    private void integraInfoClient(String codProv, List<InterfaceDTO> interfaceDTOS) {
        Gson gson = new Gson();
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        for (InterfaceDTO interfaceDTO : interfaceDTOS) {
            try {
                CustomerDTO customers = gson.fromJson(interfaceDTO.getContJson(), CustomerDTO.class);
                int validateCustomerExist = validaExistenciaClient(customers.getDni());
                System.out.println("Valor de client existeix: " + validateCustomerExist);

                if (codProv.isEmpty()) {
                    codProv = interfaceDTO.getCodProv();
                }

                if (validateCustomerExist == 0) {
                    String traduit = traduirTipusCarrer(customers.getStreetType(), codProv);
                    customers.setStreetType(traduit);
                    customerRepository.store(customers);
                    interfaceDTO.setUpdatedBy("Sistema actualitzador nou");
                } else {
                    interfaceDTO.setUpdatedBy("Sistema actualitzador existent");
                }
                interfaceDTO.setLastUpdate(currentTimestamp);
                interfaceDTO.setCodError(null);
                interfaceDTO.setErrorMessage(null);
                interfaceDTO.setStatusProcess("P");
                interfaceRepository.update(interfaceDTO);
            } catch (Exception e) {
                InterfaceDTO errorInterfaceDTO = new InterfaceDTO();
                errorInterfaceDTO.setCodExternal(interfaceDTO.getCodExternal());
                errorInterfaceDTO.setUpdatedBy("Sistema");
                errorInterfaceDTO.setLastUpdate(currentTimestamp);
                errorInterfaceDTO.setCodError(240);
                errorInterfaceDTO.setErrorMessage(e.getMessage());
                errorInterfaceDTO.setStatusProcess("E");
                errorInterfaceDTO.setOperation("UPD");
                interfaceRepository.update(errorInterfaceDTO);
            }
        }
    }

    // Integra la informació de les parts
    private void integraInfoParts(String codProv, List<InterfaceDTO> interfaceDTOS) {
        Gson gson = new Gson();
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        for (InterfaceDTO interfaceDTO : interfaceDTOS) {
            try {
                PartsDTO parts = gson.fromJson(interfaceDTO.getContJson(), PartsDTO.class);
                int validatePartExist = validaExistenciaPart(parts.getDni(), parts.getClaimNumber());
                System.out.println("Valor de part existeix: " + validatePartExist);

                if (validatePartExist == 0) {
                    partsRepository.store(parts);
                    interfaceDTO.setUpdatedBy("Sistema actualitzador nou");
                } else {
                    interfaceDTO.setUpdatedBy("Sistema actualitzador existent");
                }
                interfaceDTO.setLastUpdate(currentTimestamp);
                interfaceDTO.setCodError(null);
                interfaceDTO.setErrorMessage(null);
                interfaceDTO.setStatusProcess("P");
                interfaceRepository.update(interfaceDTO);
            } catch (Exception e) {
                InterfaceDTO errorInterfaceDTO = new InterfaceDTO();
                errorInterfaceDTO.setCodExternal(interfaceDTO.getCodExternal());
                errorInterfaceDTO.setUpdatedBy("Sistema");
                errorInterfaceDTO.setLastUpdate(currentTimestamp);
                errorInterfaceDTO.setCodError(240);
                errorInterfaceDTO.setErrorMessage(e.getMessage());
                errorInterfaceDTO.setStatusProcess("E");
                errorInterfaceDTO.setOperation("UPD");
                interfaceRepository.update(errorInterfaceDTO);
            }
        }
    }

    // Integra la informació del vehicle
    private void integraInfoVehicles(String codProv, List<InterfaceDTO> interfaceDTOS) {
        Gson gson = new Gson();
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        for (InterfaceDTO interfaceDTO : interfaceDTOS) {
            try {
                VehiclesDTO vehicles = gson.fromJson(interfaceDTO.getContJson(), VehiclesDTO.class);
                int validateVehicleExist = validaExistenciaVehicle(vehicles.getCarPlate());
                System.out.println("Valor de vehicle existeix: " + validateVehicleExist);

                if (validateVehicleExist == 0) {
                    vehicleRepository.store(vehicles);
                    interfaceDTO.setUpdatedBy("Sistema actualitzador nou");
                } else {
                    interfaceDTO.setUpdatedBy("Sistema actualitzador existent");
                }
                interfaceDTO.setLastUpdate(currentTimestamp);
                interfaceDTO.setCodError(null);
                interfaceDTO.setErrorMessage(null);
                interfaceDTO.setStatusProcess("P");
                interfaceRepository.update(interfaceDTO);

            } catch (Exception e) {

                InterfaceDTO errorInterfaceDTO = new InterfaceDTO();
                errorInterfaceDTO.setCodExternal(interfaceDTO.getCodExternal());
                errorInterfaceDTO.setUpdatedBy("Sistema");
                errorInterfaceDTO.setLastUpdate(currentTimestamp);
                errorInterfaceDTO.setCodError(240);
                errorInterfaceDTO.setErrorMessage(e.getMessage());
                errorInterfaceDTO.setStatusProcess("E");
                errorInterfaceDTO.setOperation("UPD");
                interfaceRepository.update(errorInterfaceDTO);
            }
        }
    }

    // Genera el fitxer de factures
    @Override
    public void genInvoiceFile(String codProv, String date) {
        // Recuperar la llista de proveïdors
        List<ProviderDTO> proveedores = providerRepository.retrieve("", "");

        for (ProviderDTO provider : proveedores) {

            // Assignar el codi de proveïdor si el paràmetre està buit
            if (codProv.isEmpty()) {
                codProv = provider.getCodProv();
            }

            // Assignar la data actual si el paràmetre està buit
            if (date.isEmpty()) {
                date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
            }

            // Construir el nom del fitxer de sortida
            String fileNameOut = out + nameInvoiceFile + codProv + "_" + date + outExtension;
            File file = new File(fileNameOut);

            // Recuperar les factures per aquest proveïdor i data
            List<InvoiceDTO> invoiceDTOS = invoiceRepository.searchList(codProv, date);

            if (!invoiceDTOS.isEmpty()) {
                try (FileWriter writer = new FileWriter(file, true)) {
                    // Escriure la capçalera del fitxer si hi ha dades per escriure
                    writer.append("Dni;Name;FirstName;LastName;Model;CarPlate;Cost;InvoiceNumber\n");

                    // Iterar sobre cada factura i escriure les dades al fitxer
                    for (InvoiceDTO invoice : invoiceDTOS) {
                        writer.append(invoice.getDniCustomer()).append(";");
                        writer.append(invoice.getNameCustomer()).append(";");
                        writer.append(invoice.getfirstNameCustomer()).append(";");
                        writer.append(invoice.getlastNameCustomer()).append(";");
                        writer.append(invoice.getModelVehicle()).append(";");
                        writer.append(invoice.getcarPlateVehicle()).append(";");
                        writer.append(String.format("%.2f", invoice.getCost())).append(";");
                        writer.append(invoice.getInvoiceNumber()).append("\n");
                    }

                    writer.flush(); // Assegurar que tota la informació s'escriu correctament al fitxer
                    System.out.println("Arxiu " + fileNameOut + " creat amb èxit");

                    // Actualitzar l'estat de les factures a la base de dades després d'escriure-les al fitxer
                    for (InvoiceDTO invoice : invoiceDTOS) {
                        InvoicesDTO invoiceToUpdate = invoicesRepository.search(invoice.getInvoiceNumber(), codProv, date);
                        if (invoiceToUpdate != null) {
                            invoiceToUpdate.setSend(true);
                            invoicesRepository.update(invoiceToUpdate);
                        }
                    }

                } catch (Exception e) {
                    System.err.println("Error escrivint al fitxer: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                System.out.println("No hi ha factures per escriure per al proveïdor: " + codProv + " i data: " + date);
            }
        }
    }

    // Valida si el client existeix
    private int validaExistenciaClient(String dni) {
        CustomerDTO customerDTO = customerRepository.search(dni, "", "");
        if (customerDTO == null) {
            return 0;
        } else {
            return 1;
        }
    }

    // Valida si la part existeix
    private int validaExistenciaPart(String dni, String claimNumber) {
        PartsDTO partsDTO = partsRepository.search(dni, claimNumber, "");
        if (partsDTO == null) {
            return 0;
        } else {
            return 1;
        }
    }

    // Valida si el vehicle existeix
    private int validaExistenciaVehicle(String carPlate) {
        VehiclesDTO vehicleDTO = vehicleRepository.search(carPlate, "", "");
        if (vehicleDTO == null) {
            return 0;
        } else {
            return 1;
        }
    }

    // Valida la informació de la interfície
    private int validaInformacio(InterfaceDTO interfaceDTO, String json, String pSource) {
            InterfaceDTO interfaceData = interfaceRepository.search(interfaceDTO.getCodExternal(), interfaceDTO.getCodProv(), pSource);

        if (interfaceData == null) {
            return 0;
        } else if (!interfaceData.getContJson().equals(json)) {
            return 1;
        } else {
            return 2;
        }
    }

    // Traduïr tipus de carrer
    private String traduirTipusCarrer(String streetType, String codProv) {
        TranslationDTO translationDTO = translationRepository.search(codProv, streetType, "");
        streetType = translationDTO.getInternalCode();

        return streetType;
    }

    // Obtenir el nom del fitxer segons la font d'origen, el codi de proveïdor i la data
    private String getNomFitxer(String pSource, String codProv, String date, String codProvConsulta) {
        String[] fechaActual = LocalDate.now().toString().split("-");
        String typeOfFile;

        if (pSource.equals("CUSTOMERS")) {
            typeOfFile = nameFileCustomers;
        } else if (pSource.equals("PARTS")) {
            typeOfFile = nameFileParts;
        } else {
            typeOfFile = nameFileVehicles;
        }

        if (!date.isEmpty()) {
            if (codProv == null) {
                return in + typeOfFile + codProvConsulta + "_" + date + extension;
            } else {
                return in + typeOfFile + codProv + "_" + date + extension;
            }
        }

        if (codProv == null) {
            return in + typeOfFile + codProvConsulta + "_" + fechaActual[0] + fechaActual[1] + fechaActual[2] + extension;
        } else {
            return in + typeOfFile + codProv + "_" + fechaActual[0] + fechaActual[1] + fechaActual[2] + extension;
        }
    }
}
