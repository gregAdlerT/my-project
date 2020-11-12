package com.example.application.views.neworder;

import com.example.application.data.entity.Order;
import com.example.application.data.service.RepositoryOrder;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.example.application.views.main.MainView;
import com.vaadin.flow.router.RouteAlias;
import elemental.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Route(value = "new-order", layout = MainView.class)
@PageTitle("פרטי ספקים")
@CssImport("./styles/views/neworder/new-order-view.css")
@RouteAlias(value = "", layout = MainView.class)
public class NewOrderView extends Div {

    private DatePicker currentDate = new DatePicker("תאריך");
    private ComboBox<String> garden = new ComboBox<>("שם גן/מרכז");// select garden
    private ComboBox<String> departmentNumber = new ComboBox<>("מס מחלקה");
    private DatePicker durationNumber = new DatePicker("תאריך ארך");
    private TextField provider = new TextField("ספק");
    private TextField checkNumber = new TextField("מס חשבונית");
    private TextField amount = new TextField("סכום");
    private ComboBox<String> type = new ComboBox<>("סוג");
    private TextField detail = new TextField("פרוט");
    private ComboBox<String> paymentMethods = new ComboBox<>("עמצעי תשלום");
    private TextField paymentQuantity = new TextField("מס תשלומים");
    private DatePicker firstPaymentDate = new DatePicker("תאריך תשלום ראשון");
    private TextField paymentDatesDetails = new TextField("תאריכים");
    private ComboBox<String> confirmsName = new ComboBox<>("שם מאשר");
    private TextField deliveredTo = new TextField("נמסר לה\"ח");

   private MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
   private Upload upload = new Upload(buffer);



    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private Binder<Order> binder = new Binder(Order.class);
    private final String PATH_TO = "\\upload";

    public NewOrderView(@Autowired RepositoryOrder repositoryOrder) {
        setId("new-order-view");
        setGarden();
        setPaymentMethods();
        setType();
        setDepartmentNumber();
        setConfirmsName();

        File filePath = new File(PATH_TO);
        if (!filePath.exists()){
            filePath.mkdir();
        }

        add(createTitle());
        add(createFormLayout());
        add(upload);
        add(createButtonLayout());
        upload.addFinishedListener(e -> {
            InputStream inputStream = buffer.getInputStream(e.getFileName());
            try {
                Files.copy(inputStream, Paths.get(Paths.get(PATH_TO) +"\\"+ e.getFileName()));
            } catch (IOException ex) {
                Notification.show("Такой файл уже есть - "+ e.getFileName());
                ex.printStackTrace();
            }finally {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        );
        binder.bindInstanceFields(this);
        clearForm();
        cancel.addClickListener(e -> {
            clearForm();
            buffer.getFiles().clear();
            upload.getElement().setPropertyJson("files", Json.createArray());
        });
        save.addClickListener(e -> {
            Order bean = binder.getBean();
            bean.setUrlFiles(buffer.getFiles());
            buffer.getFiles().clear();
            upload.getElement().setPropertyJson("files", Json.createArray());
            repositoryOrder.save(bean);
            Notification.show("נשמר בהצלחה");
            clearForm();
        });
    }
    private void setGarden(){
        garden.setItems(List.of("מוריה","שמחה","אגדה 1","אגדה 2","שיטון 1","שיטון קרית מוצקין","צהרון קורצ'אק","שיטון 3","דובדבנצ'יק 1","דובדבנצ'יק 3","דובדבנצ'יק 4","מעון פרחים","גן פרחים","איגום פסגת זאב","שפרינצק","דובדבנצ'יק 2","נווה שהנן","מעון איגום פרחים","גן איגום פרחים","הפלאים יובלים","יובלים","שלנו","שוורצמן","גן הפלאים 2","מעון הפלאים 2","מעון הפלאים 1","שיטון הדר","סקרנל 2","סקרנל 1","ביאנה","בית ספר לאומנות","צהרון סקרנל","סקרנל"));
    }
    private void setType(){
        type.setItems(List.of("רכישת ציוד בר-קיימא","רכישת מזון","ביצוע שיפוצים ובניה","רכישת שירות יעוץ"));
    }
    private void setDepartmentNumber(){
       departmentNumber.setItems(List.of("270","280","100","110","120","160","210","230","140","150","180","380","390","760","240","130","200","420","430","710","300","310","370","720","730","750","170","320","340","350","620","630","650"));
    }
    private void setPaymentMethods(){
        paymentMethods.setItems(List.of("צ'קים","הוראת קבע","העברה בנקאית","מזומן"));
    }
    private void setConfirmsName(){
        confirmsName.setItems(List.of("ליאחוביצקי","שוויביש"));
    }

    private void clearForm() {
        binder.setBean(new Order());
    }

    private Component createTitle() {
        return new H3("מסמך חדש");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(currentDate,garden,departmentNumber,durationNumber,provider,checkNumber,amount,type,detail,paymentMethods,paymentQuantity,firstPaymentDate,paymentDatesDetails,confirmsName,deliveredTo);
        return formLayout;
    }
    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save);
        buttonLayout.add(cancel);
        return buttonLayout;
    }
}


