package com.example.application.views.edit;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

import com.example.application.data.entity.Order;
import com.example.application.data.service.RepositoryOrder;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.internal.MessageDigestUtil;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.AbstractStreamResource;
import com.vaadin.flow.server.InputStreamFactory;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.StreamResourceWriter;
import elemental.json.Json;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.application.views.main.MainView;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

@Route(value = "edit", layout = MainView.class)
@PageTitle("Edit Order")
@CssImport("./styles/views/edit/edit-view.css")
@RouteAlias(value = "edit", layout = MainView.class)
public class EditView extends Div {
    private static final String PATH_TO = "\\upload";
    private DatePicker currentDate = new DatePicker();
    private ComboBox<String> garden = new ComboBox<>();// select garden
    private ComboBox<String> departmentNumber = new ComboBox<>();
    private DatePicker durationNumber = new DatePicker();
    private TextField provider = new TextField();
    private TextField checkNumber = new TextField();
    private TextField amount = new TextField();
    private ComboBox<String> type = new ComboBox<>();
    private TextField detail = new TextField();
    private ComboBox<String> paymentMethods = new ComboBox<>();
    private TextField paymentQuantity = new TextField();
    private DatePicker firstPaymentDate = new DatePicker();
    private TextField paymentDatesDetails = new TextField();
    private ComboBox<String> confirmsName = new ComboBox<>();
    private TextField deliveredTo = new TextField();

    private TextField complaintPay = new TextField();
    private DatePicker payDate = new DatePicker();
    private DatePicker dateInCheck = new DatePicker();
    private DatePicker sendComplaintDate = new DatePicker();
    private TextField sendWorkerName = new TextField();
    private TextField address = new TextField();
    private TextField documentType = new TextField();
    private TextField internalReference = new TextField();
    private TextField socialWork = new TextField();
    private TextField clientName = new TextField();
    private TextField rrIL = new TextField();
    private TextField remarks = new TextField();

    private Grid<Order> grid;
    private SplitLayout splitLayout = new SplitLayout();


    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    private Binder<Order> binder;

    private Order order = new Order();

    private RepositoryOrder repositoryOrder;
    private boolean visible = false;
    private MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
    private Upload upload = new Upload(buffer);
    private Div containerImage = new Div();


    public EditView(@Autowired RepositoryOrder repositoryOrder) {
        setId("edit-view");
        this.repositoryOrder = repositoryOrder;
        // Configure Grid
        grid = new Grid<>(Order.class);
        grid.setColumns("currentDate","garden","provider","checkNumber","amount","type","paymentMethods","confirmsName");
        grid.getColumnByKey("currentDate").setHeader("תאריך");
        grid.getColumnByKey("garden").setHeader("שם גן/מרכז");
        grid.getColumnByKey("provider").setHeader("ספק");
        grid.getColumnByKey("checkNumber").setHeader("מס חשבונית");
        grid.getColumnByKey("amount").setHeader("סכום");
        grid.getColumnByKey("type").setHeader("סוג");
        grid.getColumnByKey("paymentMethods").setHeader("עמצעי תשלום");
        grid.getColumnByKey("confirmsName").setHeader("שם מאשר");
        grid.setItems(repositoryOrder.findAll());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        setGarden();
        setType();
        setDepartmentNumber();
        setPaymentMethods();
        setConfirmsName();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            visible = true;
            setEditor();
            if (event.getValue() != null) {
                Order order = event.getValue();
                if (order != null) {
                    populateForm(order);
                    showImages(order.getUrlFiles());
                    upload.addFinishedListener(e -> {
                        order.setUrlFiles(buffer.getFiles());
                        InputStream inputStream = buffer.getInputStream(e.getFileName());
                        try {
                            Files.copy(inputStream, Paths.get(Paths.get(PATH_TO)+"\\"+e.getFileName()));
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }finally {
                            try {
                                inputStream.close();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    });
                } else {
                    refreshGrid();
                }
            } else {
                clearForm();
            }
        });
        // Configure Form
        binder = new Binder<>(Order.class);

        // Bind fields. This where you'd define e.g. validation rules
        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
            visible = false;
            setEditor();
            buffer.getFiles().clear();
            upload.getElement().setPropertyJson("files", Json.createArray());
            UI.getCurrent().getPage().reload();
        });

        delete.addClickListener(e -> {
            if (this.order == null){
                Notification.show("טעות");
            }
            try {
                binder.writeBean(this.order);
                repositoryOrder.delete(this.order);
                clearForm();
                refreshGrid();
                visible = false;
                setEditor();
                Notification.show("OK");
            } catch (ValidationException ex) {
                ex.printStackTrace();
                Notification.show("טעות");
            }

        });

        save.addClickListener(e -> {
            try {
                if (this.order == null) {
                    visible = false;
                    setEditor();
                    return;
                }
                binder.writeBean(this.order);
                repositoryOrder.save(this.order);
                this.order.setUrlFiles(buffer.getFiles());
                buffer.getFiles().clear();
                upload.getElement().setPropertyJson("files", Json.createArray());
                refreshGrid();
                visible = false;
                setEditor();
                Notification.show("שינויים נשמרו");
                clearForm();
            } catch (ValidationException validationException) {
                Notification.show("טעות");
            }
        });
        splitLayout.setSizeFull();
        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);
        add(splitLayout);
    }
    private void setEditor(){
        if (visible) {
            createEditorLayout(splitLayout);
        } if (!visible){
            createEditorLayout(splitLayout);
        }
    }
    @SneakyThrows
    public void showImages(Set<String> setUrl){
        containerImage.removeAll();
        remove(containerImage);
        VerticalLayout layout = new VerticalLayout();
        for (String set: setUrl){
            File file = new File(PATH_TO+"\\"+set);
            String type = getFileExtension(file);
            if (type.equalsIgnoreCase("jpeg") || type.equalsIgnoreCase("png") || type.equalsIgnoreCase("jpg")){
                byte[] imageBytes = new byte[(int)file.length()];
            FileInputStream fis = new FileInputStream(file);
            fis.read(imageBytes);
            fis.close();
            StreamResource res = new StreamResource(set, () -> new ByteArrayInputStream(imageBytes));
            Image image = new Image(res, "dummy image");
            image.setHeight("250px");
            image.setWidth("250px");
            layout.add(image);
            } else {
                DownloadLink downloadLink = new DownloadLink(file);
                layout.add(downloadLink);
            }
        }
        containerImage.add(layout);
        add(containerImage);
    }
    public String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0){
            return fileName.substring(fileName.lastIndexOf(".")+1);
        }
        return "";
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

    private void createEditorLayout(SplitLayout splitLayout) {

        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setId("editor-layout");
        editorLayoutDiv.setWidth("1260px");
        editorLayoutDiv.setVisible(visible);

        Div editorDiv = new Div();
        editorDiv.setId("editor");
        editorLayoutDiv.add(editorDiv);
        upload.setMinHeight("150px");
        editorLayoutDiv.add(upload);
        FormLayout formLayout = new FormLayout();
        addFormItem(editorDiv, formLayout, currentDate, "תאריך");
        addFormItem(editorDiv, formLayout, garden, "שם גן/מרכז");
        addFormItem(editorDiv, formLayout, departmentNumber, "מס מחלקה");
        addFormItem(editorDiv, formLayout,  durationNumber, "תאריך ארך");
        addFormItem(editorDiv, formLayout, provider, "ספק");
        addFormItem(editorDiv, formLayout, checkNumber, "מס חשבונית");
        addFormItem(editorDiv, formLayout, amount, "סכום");
        addFormItem(editorDiv, formLayout, type, "סוג");
        addFormItem(editorDiv, formLayout, detail, "פרוט");
        addFormItem(editorDiv, formLayout, paymentMethods, "עמצעי תשלום");
        addFormItem(editorDiv, formLayout, paymentQuantity, "מס תשלומים");
        addFormItem(editorDiv, formLayout, firstPaymentDate, "תאריך תשלום ראשון");
        addFormItem(editorDiv, formLayout, paymentDatesDetails, "תאריכים");
        addFormItem(editorDiv, formLayout, confirmsName, "שם מאשר");
        addFormItem(editorDiv, formLayout, deliveredTo, "נמסר לה\"ח");
        addFormItem(editorDiv, formLayout, complaintPay, "שולם");
        addFormItem(editorDiv, formLayout, payDate, "תאריך");
        addFormItem(editorDiv, formLayout, dateInCheck, "תאריך קבלה");
        addFormItem(editorDiv, formLayout, sendComplaintDate, "תאריך שליחה");
        addFormItem(editorDiv, formLayout, sendWorkerName, "עבור");
        addFormItem(editorDiv, formLayout, address, "כתובת");
        addFormItem(editorDiv, formLayout, documentType, "סוג מסמך");
        addFormItem(editorDiv, formLayout, internalReference, "אסמכתא פנימית");
        addFormItem(editorDiv, formLayout, socialWork, "ע\"ס");
        addFormItem(editorDiv, formLayout, clientName, "שם המקבל");
        addFormItem(editorDiv, formLayout, rrIL, "RR............IL");
        addFormItem(editorDiv, formLayout, remarks, "הערות");


        createButtonLayout(editorLayoutDiv);
        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setId("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout.setSpacing(true);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel,delete);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void addFormItem(Div wrapper, FormLayout formLayout, AbstractField field, String fieldName) {
        formLayout.addFormItem(field, fieldName);
        wrapper.add(formLayout);
        field.getElement().getClassList().add("full-width");
    }

    private void refreshGrid() {
        grid.select(null);
        grid.setItems(repositoryOrder.findAll());
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Order value) {
        this.order = value;
        binder.readBean(this.order);
    }

//
//private Component createComponent(String mimeType, String fileName,InputStream stream) {
//    if (mimeType.startsWith("text")) {
//        return createTextComponent(stream);
//    } else if (mimeType.startsWith("image")) {
//        Image image = new Image();
//        try {
//            byte[] bytes = IOUtils.toByteArray(stream);
//            image.getElement().setAttribute("src", new StreamResource(
//                    fileName, () -> new ByteArrayInputStream(bytes)));
//            try (ImageInputStream in = ImageIO.createImageInputStream(
//                    new ByteArrayInputStream(bytes))) {
//                final Iterator<ImageReader> readers = ImageIO
//                        .getImageReaders(in);
//                if (readers.hasNext()) {
//                    ImageReader reader = readers.next();
//                    try {
//                        reader.setInput(in);
//                        image.setWidth("150px");
//                        image.setHeight("100px");
//                    } finally {
//                        reader.dispose();
//                    }
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return image;
//    }
//    Div content = new Div();
//    String text = String.format("Mime type: '%s'\nSHA-256 hash: '%s'",
//            mimeType, MessageDigestUtil.sha256(stream.toString()));
//    content.setText(text);
//    return content;
//}
//
//    private Component createTextComponent(InputStream stream) {
//        String text;
//        try {
//            text = IOUtils.toString(stream, StandardCharsets.UTF_8);
//        } catch (IOException e) {
//            text = "exception reading stream";
//        }
//        return new Text(text);
//    }
//
//    private void showOutput(String text, Component content,
//                            HasComponents outputContainer) {
//        HtmlComponent p = new HtmlComponent(Tag.P);
//        p.getElement().setText(text);
//        outputContainer.add(p);
//        outputContainer.add(content);
//    }
}
