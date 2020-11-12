package com.example.application.views.full_table;

import com.example.application.data.entity.Order;
import com.example.application.data.service.RepositoryOrder;
import com.example.application.views.main.MainView;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;


@Route(value = "table", layout = MainView.class)
@PageTitle("Full Table")
@CssImport(value = "./styles/views/edit/edit-view.css" , include = "lumo-badge")
@JsModule("@vaadin/vaadin-lumo-styles/badge.js")
@RouteAlias(value = "table", layout = MainView.class)
public class Table extends Div {




    private DatePicker currentDate = new DatePicker();
    private ComboBox<String> garden = new ComboBox<>();// select garden
    private ComboBox<String> departmentNumber = new ComboBox<>();
    private ComboBox<String> type = new ComboBox<>();
    private ComboBox<String> confirmsName = new ComboBox<>();
    private ComboBox<String> paymentMethods = new ComboBox<>();
    private ComboBox<String> checkNumber = new ComboBox<>();
    private ComboBox<String> provider = new ComboBox<>();
    private ComboBox<String> amount = new ComboBox<>();
    private DatePicker durationNumber = new DatePicker();
    private ComboBox<String> detail = new ComboBox<>();
    private ComboBox<String> paymentQuantity = new ComboBox<>();
    private DatePicker firstPaymentDate = new DatePicker();
    private ComboBox<String> paymentDatesDetails = new ComboBox<>();
    private ComboBox<String> deliveredTo = new ComboBox<>();
    private ComboBox<String> complaintPay = new ComboBox<>();
    private DatePicker payDate = new DatePicker();
    private DatePicker dateInCheck = new DatePicker();
    private DatePicker sendComplaintDate = new DatePicker();
    private ComboBox<String> sendWorkerName = new ComboBox<>();
    private ComboBox<String> address = new ComboBox<>();
    private ComboBox<String> documentType = new ComboBox<>();
    private ComboBox<String> internalReference = new ComboBox<>();
    private ComboBox<String> socialWork = new ComboBox<>();
    private ComboBox<String> clientName = new ComboBox<>();
    private ComboBox<String> rrIL = new ComboBox<>();
    private ComboBox<String> remarks = new ComboBox<>();



    private Grid<Order> grid;

    private RepositoryOrder repositoryOrder;

    private ListDataProvider<Order> dataProvider;


    public Table(@Autowired RepositoryOrder repositoryOrder) {
        setId("edit-view");
        this.repositoryOrder = repositoryOrder;
        grid = new Grid <>(Order.class);
        grid.setColumns("currentDate","garden","departmentNumber","type","confirmsName","paymentMethods","provider","checkNumber","amount"
                ,"durationNumber", "detail","paymentQuantity", "firstPaymentDate",
                "paymentDatesDetails", "deliveredTo", "complaintPay","payDate", "dateInCheck", "sendComplaintDate",
                "sendWorkerName", "address", "documentType", "internalReference", "socialWork", "clientName", "rrIL","remarks");
        grid.getColumnByKey("currentDate").setHeader("תאריך").setWidth("150px");
        grid.getColumnByKey("garden").setHeader("שם גן/מרכז").setWidth("200px");
        grid.getColumnByKey("type").setHeader("סוג").setWidth("200px");
        grid.getColumnByKey("departmentNumber").setHeader("מס מחלקה").setWidth("150px");
        grid.getColumnByKey("confirmsName").setHeader("שם מאשר").setWidth("200px");
        grid.getColumnByKey("paymentMethods").setHeader("עמצעי תשלום").setWidth("200px");




        grid.getColumnByKey("provider").setHeader("ספק").setWidth("250px");
        grid.getColumnByKey("checkNumber").setHeader("מס חשבונית").setWidth("200px");
        grid.getColumnByKey("amount").setHeader("סכום").setWidth("200px");
        grid.getColumnByKey("durationNumber").setHeader("תאריך ארך").setWidth("200px");
        grid.getColumnByKey("detail").setHeader("פרוט").setWidth("200px");
        grid.getColumnByKey("paymentQuantity").setHeader("מס תשלומים").setWidth("200px");
        grid.getColumnByKey("firstPaymentDate").setHeader("תאריך תשלום ראשון").setWidth("200px");
        //grid.getColumnByKey("firstPaymentDate").setHeader("תאריך תשלום ראשון").setWidth("200px");
        grid.getColumnByKey("paymentDatesDetails").setHeader("תאריכים").setWidth("200px");
        grid.getColumnByKey("deliveredTo").setHeader("נמסר לה\\\"ח").setWidth("200px");
        grid.getColumnByKey("complaintPay").setHeader("שולם").setWidth("200px");
        grid.getColumnByKey("payDate").setHeader("תאריך תשלום").setWidth("200px");
        grid.getColumnByKey("dateInCheck").setHeader("תאריך קבלה").setWidth("200px");
        grid.getColumnByKey("sendComplaintDate").setHeader("תאריך שליחה").setWidth("200px");
        grid.getColumnByKey("sendWorkerName").setHeader("עבור").setWidth("200px");
        grid.getColumnByKey("address").setHeader("כתובת").setWidth("200px");
        grid.getColumnByKey("documentType").setHeader("סוג מסמך").setWidth("200px");
        grid.getColumnByKey("internalReference").setHeader("אסמכתא פנימית").setWidth("200px");
        grid.getColumnByKey("socialWork").setHeader("ע\"ס").setWidth("200px");
        grid.getColumnByKey("clientName").setHeader("שם המקבל").setWidth("200px");
        grid.getColumnByKey("rrIL").setHeader("RR..IL").setWidth("200px");
        grid.getColumnByKey("remarks").setHeader("הערות").setWidth("200px");

        dataProvider = new ListDataProvider<Order>(repositoryOrder.findAll());
        grid.setDataProvider(dataProvider);

        addFiltersToGrid();

        grid.setItems(repositoryOrder.findAll());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();



        setGarden();
        setType();
        setDepartmentNumber();
        setPaymentMethods();
        setConfirmsName();
        add(grid);


    }

    private void addFiltersToGrid() {
        HeaderRow filterRow = grid.appendHeaderRow();

        garden.setPlaceholder("שם גן");
        garden.setClearButtonVisible(true);
        garden.setWidth("100%");
        garden.setMinWidth("180px");
        setGarden();
        garden.addValueChangeListener(event -> {
            if (garden.getValue() == null){
                grid.setItems(repositoryOrder.findAll());
            }else
                grid.setItems(repositoryOrder.getByGarden(garden.getValue()));
        });
       filterRow.getCell(grid.getColumnByKey("garden")).setComponent(garden);


        currentDate.setPlaceholder("תאריך");
        currentDate.setClearButtonVisible(true);
        currentDate.setWidth("100%");
        currentDate.setMinWidth("140px");
        currentDate.addValueChangeListener(event -> {
            if (currentDate.getValue() == null){
                grid.setItems(repositoryOrder.findAll());
            }else
                grid.setItems(repositoryOrder.getByCurrentDate(currentDate.getValue()));
                });
        filterRow.getCell(grid.getColumnByKey("currentDate")).setComponent(currentDate);


        departmentNumber.setPlaceholder("מחלקה");
        departmentNumber.setClearButtonVisible(true);
        departmentNumber.setWidth("100%");
        departmentNumber.setMinWidth("140px");
        setDepartmentNumber();
        departmentNumber.addValueChangeListener(event -> {
            if (departmentNumber.getValue() == null){
                grid.setItems(repositoryOrder.findAll());
            }else
                grid.setItems(repositoryOrder.getByDepartmentNumber(departmentNumber.getValue()));
                });
        filterRow.getCell(grid.getColumnByKey("departmentNumber")).setComponent(departmentNumber);


        type.setPlaceholder("סוג");
        type.setClearButtonVisible(true);
        type.setWidth("100%");
        type.setMinWidth("180px");
        setType();
        type.addValueChangeListener(event -> {
            if (type.getValue() == null){
                grid.setItems(repositoryOrder.findAll());
            }else
                grid.setItems(repositoryOrder.getByType(type.getValue()));
                });
        filterRow.getCell(grid.getColumnByKey("type")).setComponent(type);


        confirmsName.setPlaceholder("שם מאשר");
        confirmsName.setClearButtonVisible(true);
        confirmsName.setWidth("100%");
        confirmsName.setMinWidth("180px");
        setConfirmsName();
        confirmsName.addValueChangeListener(event -> {
            if (confirmsName.getValue() == null){
                grid.setItems(repositoryOrder.findAll());
            }else
                grid.setItems(repositoryOrder.getByConfirmsName(confirmsName.getValue()));
                });
        filterRow.getCell(grid.getColumnByKey("confirmsName")).setComponent(confirmsName);


        paymentMethods.setPlaceholder("עמצעי תשלום");
        paymentMethods.setClearButtonVisible(true);
        paymentMethods.setWidth("100%");
        paymentMethods.setMinWidth("180px");
        setPaymentMethods();
        paymentMethods.addValueChangeListener(event -> {
            if (paymentMethods.getValue() == null){
                grid.setItems(repositoryOrder.findAll());
            }else
                grid.setItems(repositoryOrder.getByPaymentMethods(paymentMethods.getValue()));
                });
        filterRow.getCell(grid.getColumnByKey("paymentMethods")).setComponent(paymentMethods);
        
        /////////////////////////////////////////
        List<Order> staticOrderData = repositoryOrder.findAll();

        checkNumber.setPlaceholder("מס חשבונית");
        checkNumber.setClearButtonVisible(true);
        checkNumber.setWidth("100%");
        checkNumber.setMinWidth("150px");
        checkNumber.setItems(staticOrderData.stream().map(o->o.getCheckNumber()));
        checkNumber.addValueChangeListener(event -> {
            if (checkNumber.getValue() == null){
                List<Order> allOrders = repositoryOrder.findAll();
                grid.setItems(allOrders);
            }else {
                List<Order> filteredOrders = repositoryOrder.findByCheckNumberStartingWith(checkNumber.getValue());
                grid.setItems(filteredOrders);
            }
        });
        filterRow.getCell(grid.getColumnByKey("checkNumber")).setComponent(checkNumber);
        
        ////////////////////////////
        provider.setPlaceholder( "ספק");
        provider.setClearButtonVisible(true);
        provider.setWidth("100%");
        provider.setMinWidth("150px");
        provider.setItems(staticOrderData.stream().map(o->o.getProvider()));
        provider.addValueChangeListener(event -> {
            if (provider.getValue() == null){
                List<Order> allOrders = repositoryOrder.findAll();
                staticOrderData.clear();
                staticOrderData.addAll(allOrders);
                grid.setItems(allOrders);
            }else {
                List<Order> filteredOrders = repositoryOrder.findByProviderStartingWith(provider.getValue());
                grid.setItems(filteredOrders);
            }
        });
        filterRow.getCell(grid.getColumnByKey("provider")).setComponent(provider);
        ////////////////////////////
        amount.setPlaceholder( "סכום");
        amount.setClearButtonVisible(true);
        amount.setWidth("100%");
        amount.setMinWidth("150px");
        amount.setItems(staticOrderData.stream().map(o->o.getAmount()));
        amount.addValueChangeListener(event -> {
            if (amount.getValue() == null){
                List<Order> allOrders = repositoryOrder.findAll();
                staticOrderData.clear();
                staticOrderData.addAll(allOrders);
                grid.setItems(allOrders);
            }else {
                List<Order> filteredOrders = repositoryOrder.findByAmountStartingWith(amount.getValue());
                grid.setItems(filteredOrders);
            }
        });
        filterRow.getCell(grid.getColumnByKey("amount")).setComponent(amount);
        ////////////////////////////
        durationNumber.setPlaceholder("תאריך ארך");
        durationNumber.setClearButtonVisible(true);
        durationNumber.setWidth("100%");
        durationNumber.setMinWidth("140px");
        durationNumber.addValueChangeListener(event -> {
            if (durationNumber.getValue() == null){
                grid.setItems(repositoryOrder.findAll());
            }else
                grid.setItems(repositoryOrder.getByDurationNumber(durationNumber.getValue()));
        });
        filterRow.getCell(grid.getColumnByKey("durationNumber")).setComponent(durationNumber);
        ////////////////////////////
        detail.setPlaceholder( "פרוט");
        detail.setClearButtonVisible(true);
        detail.setWidth("100%");
        detail.setMinWidth("150px");
        detail.setItems(staticOrderData.stream().map(o->o.getDetail()));
        detail.addValueChangeListener(event -> {
            if (detail.getValue() == null){
                List<Order> allOrders = repositoryOrder.findAll();
                staticOrderData.clear();
                staticOrderData.addAll(allOrders);
                grid.setItems(allOrders);
            }else {
                List<Order> filteredOrders = repositoryOrder.findByDetailStartingWith(detail.getValue());
                grid.setItems(filteredOrders);
            }
        });
        filterRow.getCell(grid.getColumnByKey("detail")).setComponent(detail);
        ////////////////////////////
        paymentQuantity.setPlaceholder( "מס תשלומים");
        paymentQuantity.setClearButtonVisible(true);
        paymentQuantity.setWidth("100%");
        paymentQuantity.setMinWidth("150px");
        paymentQuantity.setItems(staticOrderData.stream().map(o->o.getPaymentQuantity()));
        paymentQuantity.addValueChangeListener(event -> {
            if (paymentQuantity.getValue() == null){
                List<Order> allOrders = repositoryOrder.findAll();
                staticOrderData.clear();
                staticOrderData.addAll(allOrders);
                grid.setItems(allOrders);
            }else {
                List<Order> filteredOrders = repositoryOrder.findByPaymentQuantityStartingWith(paymentQuantity.getValue());
                grid.setItems(filteredOrders);
            }
        });
        filterRow.getCell(grid.getColumnByKey("paymentQuantity")).setComponent(paymentQuantity);
        ////////////////////////////
        firstPaymentDate.setPlaceholder("תאריך תשלום ראשון");
        firstPaymentDate.setClearButtonVisible(true);
        firstPaymentDate.setWidth("100%");
        firstPaymentDate.setMinWidth("140px");
        firstPaymentDate.addValueChangeListener(event -> {
            if (firstPaymentDate.getValue() == null){
                grid.setItems(repositoryOrder.findAll());
            }else
                grid.setItems(repositoryOrder.getByFirstPaymentDate(firstPaymentDate.getValue()));
        });
        filterRow.getCell(grid.getColumnByKey("firstPaymentDate")).setComponent(firstPaymentDate);
        ////////////////////////////
        paymentDatesDetails.setPlaceholder( "תאריכים");
        paymentDatesDetails.setClearButtonVisible(true);
        paymentDatesDetails.setWidth("100%");
        paymentDatesDetails.setMinWidth("150px");
        paymentDatesDetails.setItems(staticOrderData.stream().map(o->o.getPaymentDatesDetails()));
        paymentDatesDetails.addValueChangeListener(event -> {
            if (paymentDatesDetails.getValue() == null){
                List<Order> allOrders = repositoryOrder.findAll();
                staticOrderData.clear();
                staticOrderData.addAll(allOrders);
                grid.setItems(allOrders);
            }else {
                List<Order> filteredOrders = repositoryOrder.findByPaymentDatesDetailsStartingWith(paymentDatesDetails.getValue());
                grid.setItems(filteredOrders);
            }
        });
        filterRow.getCell(grid.getColumnByKey("paymentDatesDetails")).setComponent(paymentDatesDetails);
        ////////////////////////////
        deliveredTo.setPlaceholder( "נמסר לה\\\"ח");
        deliveredTo.setClearButtonVisible(true);
        deliveredTo.setWidth("100%");
        deliveredTo.setMinWidth("150px");
        deliveredTo.setItems(staticOrderData.stream().map(o->o.getDeliveredTo()));
        deliveredTo.addValueChangeListener(event -> {
            if (deliveredTo.getValue() == null){
                List<Order> allOrders = repositoryOrder.findAll();
                staticOrderData.clear();
                staticOrderData.addAll(allOrders);
                grid.setItems(allOrders);
            }else {
                List<Order> filteredOrders = repositoryOrder.findByDeliveredToStartingWith(deliveredTo.getValue());
                grid.setItems(filteredOrders);
            }
        });
        filterRow.getCell(grid.getColumnByKey("deliveredTo")).setComponent(deliveredTo);
        ////////////////////////////
        complaintPay.setPlaceholder( "שולם");
        complaintPay.setClearButtonVisible(true);
        complaintPay.setWidth("100%");
        complaintPay.setMinWidth("150px");
        complaintPay.setItems(staticOrderData.stream().map(o->o.getComplaintPay()));
        complaintPay.addValueChangeListener(event -> {
            if (complaintPay.getValue() == null){
                List<Order> allOrders = repositoryOrder.findAll();
                staticOrderData.clear();
                staticOrderData.addAll(allOrders);
                grid.setItems(allOrders);
            }else {
                List<Order> filteredOrders = repositoryOrder.findByComplaintPay(complaintPay.getValue());
                grid.setItems(filteredOrders);
            }
        });
        filterRow.getCell(grid.getColumnByKey("complaintPay")).setComponent(complaintPay);
        ////////////////////////////
        payDate.setPlaceholder("תאריך תשלום");
        payDate.setClearButtonVisible(true);
        payDate.setWidth("100%");
        payDate.setMinWidth("140px");
        payDate.addValueChangeListener(event -> {
            if (payDate.getValue() == null){
                grid.setItems(repositoryOrder.findAll());
            }else
                grid.setItems(repositoryOrder.getByPayDate(payDate.getValue()));
        });
        filterRow.getCell(grid.getColumnByKey("payDate")).setComponent(payDate);
        ////////////////////////////
        dateInCheck.setPlaceholder("תאריך קבלה");
        dateInCheck.setClearButtonVisible(true);
        dateInCheck.setWidth("100%");
        dateInCheck.setMinWidth("140px");
        dateInCheck.addValueChangeListener(event -> {
            if (dateInCheck.getValue() == null){
                grid.setItems(repositoryOrder.findAll());
            }else
                grid.setItems(repositoryOrder.getByDateInCheck(dateInCheck.getValue()));
        });
        filterRow.getCell(grid.getColumnByKey("dateInCheck")).setComponent(dateInCheck);
        ////////////////////////////
        sendComplaintDate.setPlaceholder("תאריך ");
        sendComplaintDate.setClearButtonVisible(true);
        sendComplaintDate.setWidth("100%");
        sendComplaintDate.setMinWidth("140px");
        sendComplaintDate.addValueChangeListener(event -> {
            if (sendComplaintDate.getValue() == null){
                grid.setItems(repositoryOrder.findAll());
            }else
                grid.setItems(repositoryOrder.getBySendComplaintDate(sendComplaintDate.getValue()));
        });
        filterRow.getCell(grid.getColumnByKey("sendComplaintDate")).setComponent(sendComplaintDate);
        ////////////////////////////
        sendWorkerName.setPlaceholder( "עבור");
        sendWorkerName.setClearButtonVisible(true);
        sendWorkerName.setWidth("100%");
        sendWorkerName.setMinWidth("150px");
        sendWorkerName.setItems(staticOrderData.stream().map(o->o.getSendWorkerName()));
        sendWorkerName.addValueChangeListener(event -> {
            if (sendWorkerName.getValue() == null){
                List<Order> allOrders = repositoryOrder.findAll();
                staticOrderData.clear();
                staticOrderData.addAll(allOrders);
                grid.setItems(allOrders);
            }else {
                List<Order> filteredOrders = repositoryOrder.findBySendWorkerNameStartingWith(sendWorkerName.getValue());
                grid.setItems(filteredOrders);
            }
        });
        filterRow.getCell(grid.getColumnByKey("sendWorkerName")).setComponent(sendWorkerName);
        ////////////////////////////
        address.setPlaceholder( "כתובת");
        address.setClearButtonVisible(true);
        address.setWidth("100%");
        address.setMinWidth("150px");
        address.setItems(staticOrderData.stream().map(o->o.getAddress()));
        address.addValueChangeListener(event -> {
            if (address.getValue() == null){
                List<Order> allOrders = repositoryOrder.findAll();
                staticOrderData.clear();
                staticOrderData.addAll(allOrders);
                grid.setItems(allOrders);
            }else {
                List<Order> filteredOrders = repositoryOrder.findByAddressStartingWith(address.getValue());
                grid.setItems(filteredOrders);
            }
        });
        filterRow.getCell(grid.getColumnByKey("address")).setComponent(address);
        ////////////////////////////
        documentType.setPlaceholder( "סוג מסמך");
        documentType.setClearButtonVisible(true);
        documentType.setWidth("100%");
        documentType.setMinWidth("150px");
        documentType.setItems(staticOrderData.stream().map(o->o.getDocumentType()));
        documentType.addValueChangeListener(event -> {
            if (documentType.getValue() == null){
                List<Order> allOrders = repositoryOrder.findAll();
                staticOrderData.clear();
                staticOrderData.addAll(allOrders);
                grid.setItems(allOrders);
            }else {
                List<Order> filteredOrders = repositoryOrder.findByDocumentTypeStartingWith(documentType.getValue());
                grid.setItems(filteredOrders);
            }
        });
        filterRow.getCell(grid.getColumnByKey("documentType")).setComponent(documentType);
        ////////////////////////////
        internalReference.setPlaceholder( "אסמכתא פנימית");
        internalReference.setClearButtonVisible(true);
        internalReference.setWidth("100%");
        internalReference.setMinWidth("150px");
        internalReference.setItems(staticOrderData.stream().map(o->o.getInternalReference()));
        internalReference.addValueChangeListener(event -> {
            if (internalReference.getValue() == null){
                List<Order> allOrders = repositoryOrder.findAll();
                staticOrderData.clear();
                staticOrderData.addAll(allOrders);
                grid.setItems(allOrders);
            }else {
                List<Order> filteredOrders = repositoryOrder.findByInternalReferenceStartingWith(internalReference.getValue());
                grid.setItems(filteredOrders);
            }
        });
        filterRow.getCell(grid.getColumnByKey("internalReference")).setComponent(internalReference);
        ////////////////////////////
        socialWork.setPlaceholder( "ע\"ס");
        socialWork.setClearButtonVisible(true);
        socialWork.setWidth("100%");
        socialWork.setMinWidth("150px");
        socialWork.setItems(staticOrderData.stream().map(o->o.getSocialWork()));
        socialWork.addValueChangeListener(event -> {
            if (socialWork.getValue() == null){
                List<Order> allOrders = repositoryOrder.findAll();
                staticOrderData.clear();
                staticOrderData.addAll(allOrders);
                grid.setItems(allOrders);
            }else {
                List<Order> filteredOrders = repositoryOrder.findBySocialWorkStartingWith(socialWork.getValue());
                grid.setItems(filteredOrders);
            }
        });
        filterRow.getCell(grid.getColumnByKey("socialWork")).setComponent(socialWork);
        ////////////////////////////
        clientName.setPlaceholder( "שם המקבל");
        clientName.setClearButtonVisible(true);
        clientName.setWidth("100%");
        clientName.setMinWidth("150px");
        clientName.setItems(staticOrderData.stream().map(o->o.getClientName()));
        clientName.addValueChangeListener(event -> {
            if (clientName.getValue() == null){
                List<Order> allOrders = repositoryOrder.findAll();
                staticOrderData.clear();
                staticOrderData.addAll(allOrders);
                grid.setItems(allOrders);
            }else {
                List<Order> filteredOrders = repositoryOrder.findByClientNameStartingWith(clientName.getValue());
                grid.setItems(filteredOrders);
            }
        });
        filterRow.getCell(grid.getColumnByKey("clientName")).setComponent(clientName);
        ////////////////////////////
        rrIL.setPlaceholder( "RR..IL");
        rrIL.setClearButtonVisible(true);
        rrIL.setWidth("100%");
        rrIL.setMinWidth("150px");
        rrIL.setItems(staticOrderData.stream().map(o->o.getRrIL()));
        rrIL.addValueChangeListener(event -> {
            if (rrIL.getValue() == null){
                List<Order> allOrders = repositoryOrder.findAll();
                staticOrderData.clear();
                staticOrderData.addAll(allOrders);
                grid.setItems(allOrders);
            }else {
                List<Order> filteredOrders = repositoryOrder.findByRrILStartingWith(rrIL.getValue());
                grid.setItems(filteredOrders);
            }
        });
        filterRow.getCell(grid.getColumnByKey("rrIL")).setComponent(rrIL);
        ////////////////////////////
        remarks.setPlaceholder( "הערות");
        remarks.setClearButtonVisible(true);
        remarks.setWidth("100%");
        remarks.setMinWidth("150px");
        remarks.setItems(staticOrderData.stream().map(o->o.getRemarks()));
        remarks.addValueChangeListener(event -> {
            if (remarks.getValue() == null){
                List<Order> allOrders = repositoryOrder.findAll();
                staticOrderData.clear();
                staticOrderData.addAll(allOrders);
                grid.setItems(allOrders);
            }else {
                List<Order> filteredOrders = repositoryOrder.findByRemarksStartingWith(remarks.getValue());
                grid.setItems(filteredOrders);
            }
        });
        filterRow.getCell(grid.getColumnByKey("remarks")).setComponent(remarks);
    }


    private void setGarden(){
        garden.setItems(List.of("מוריה","שמחה","אגדה 1","אגדה 2","שיטון 1","שיטון קרית מוצקין","צהרון קורצ'אק",
                                "שיטון 3","דובדבנצ'יק 1","דובדבנצ'יק 3","דובדבנצ'יק 4","מעון פרחים","גן פרחים","איגום פסגת זאב",
                                "שפרינצק","דובדבנצ'יק 2","נווה שהנן","מעון איגום פרחים","גן איגום פרחים","הפלאים יובלים","יובלים","שלנו"
                                ,"שוורצמן","גן הפלאים 2","מעון הפלאים 2","מעון הפלאים 1","שיטון הדר","סקרנל 2","סקרנל 1","ביאנה"
                                ,"בית ספר לאומנות","צהרון סקרנל","סקרנל"));
    }
    private void setType(){
        type.setItems(List.of("רכישת ציוד בר-קיימא","רכישת מזון","ביצוע שיפוצים ובניה","רכישת שירות יעוץ"));
    }
    private void setDepartmentNumber(){
        departmentNumber.setItems(List.of("270","280","100","110","120","160","210","230","140","150","180","380","390",
                                    "760","240","130","200","420","430","710","300","310","370","720","730","750","170",
                                    "320","340","350","620","630","650"));
    }
    private void setPaymentMethods(){
        paymentMethods.setItems(List.of("צ'קים","הוראת קבע","העברה בנקאית","מזומן"));
    }
    private void setConfirmsName(){
        confirmsName.setItems(List.of("ליאחוביצקי","שוויביש"));
}

    private boolean areDatesEqual(Order client, DatePicker dateFilter) {
        LocalDate dateFilterValue = dateFilter.getValue();
        if (dateFilterValue != null) {
            return dateFilterValue.equals(client.getCurrentDate());
        }
        return true;
    }
//    private boolean areDatesEqual(Order order, DatePicker dateFilter) {
//        LocalDate dateFilterValue = dateFilter.getValue();
//        if (dateFilterValue != null) {
//            LocalDate clientDate = order.getCurrentDate();
//            return dateFilterValue.equals(clientDate);
//        }
//        return true;
//    }
}
