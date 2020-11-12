package com.example.application.data.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "TestingAppL")
@Builder
@AllArgsConstructor
@Data
@ToString
public class Order {
    @Id
    @EqualsAndHashCode.Exclude
    private String id;

    private LocalDate currentDate;
    private String garden;
    private String departmentNumber;
    private LocalDate durationNumber;
    private String provider;
    private String checkNumber;
    private String amount;
    private String type;
    private String detail;
    private String paymentMethods;
    private String paymentQuantity;
    private LocalDate firstPaymentDate;
    private String paymentDatesDetails;
    private String confirmsName;
    private String deliveredTo;

    private Set<String> urlFiles;

    private String complaintPay;
    private LocalDate payDate;
    private LocalDate dateInCheck;
    private LocalDate sendComplaintDate;
    private String sendWorkerName;
    private String address;
    private String documentType;
    private String internalReference;
    private String socialWork;
    private String clientName;
    private String rrIL;
    private String remarks;

    public Order() {
    }
    public void setUrlFiles(Set<String> urlFiles) {
        if (this.urlFiles == null){
            this.urlFiles = new HashSet<>();
        }
        this.urlFiles.addAll(urlFiles);
    }


}
