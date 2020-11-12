package com.example.application.data.service;


import com.example.application.data.entity.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RepositoryOrder extends MongoRepository<Order,String> {
    List<Order> getByGarden(String garden);
    List<Order> getByCurrentDate(LocalDate date);
    List<Order> getByDepartmentNumber(String departmentNumber);
    List<Order> getByType(String type);
    List<Order> getByConfirmsName(String confirmsName);
    List <Order> getByPaymentMethods(String paymentMethods);
    List<Order> findByCheckNumberStartingWith(String checkNumber);
    List<Order> findByProviderStartingWith(String provider);
    List<Order> findByAmountStartingWith(String amount);
    List<Order> getByDurationNumber(LocalDate date);
    List<Order> findByDetailStartingWith(String detail);
    List<Order> findByPaymentQuantityStartingWith(String quantity);
    List<Order> getByFirstPaymentDate(LocalDate date);
    List<Order> findByPaymentDatesDetailsStartingWith(String detail);
    List<Order> findByDeliveredToStartingWith(String delivered);
    List<Order> findByComplaintPay(String complaint);
    List<Order> getByPayDate(LocalDate date);
    List<Order> getByDateInCheck(LocalDate date);
    List<Order> getBySendComplaintDate(LocalDate date);
    List<Order> findBySendWorkerNameStartingWith(String name);
    List<Order> findByAddressStartingWith(String name);
    List<Order> findByDocumentTypeStartingWith(String type);
    List<Order> findByInternalReferenceStartingWith(String ref);
    List<Order> findBySocialWorkStartingWith(String socialWork);
    List<Order> findByClientNameStartingWith(String name);
    List<Order> findByRrILStartingWith(String rril);
    List<Order> findByRemarksStartingWith(String text);
}
