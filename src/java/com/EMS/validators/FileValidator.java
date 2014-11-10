/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.EMS.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Santan
 */
@FacesValidator("FileUploadValidator")
public class FileValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        FileNameExtensionFilter csvFilter = new FileNameExtensionFilter("CSV files", "csv");
//        File file = (File) value;
        Part file = (Part) value;

        String fileName = file.getName();
        if (fileName.length() == 0) {
            FacesMessage message = new FacesMessage("Error: No File Selected!!");
            throw new ValidatorException(message);
        } else if (!extractFileExtension(file).equalsIgnoreCase(".csv")) {
            FacesMessage message = new FacesMessage("Error: CSV file types are alone accepted!!");
            System.out.println("Not a CSV file!");
            throw new ValidatorException(message);
        }

    }

    private String extractFileExtension(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        String fileName;
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                fileName = s.substring(s.indexOf("=") + 2, s.length() - 1);
                return fileName.substring(fileName.lastIndexOf("."), fileName.length());
            }
        }
        return "";
    }
    
//    private String extractFileExtension(String fileName){
//        return fileName.substring(fileName.lastIndexOf(".")+1, fileName.length()-1);
//    }

}
