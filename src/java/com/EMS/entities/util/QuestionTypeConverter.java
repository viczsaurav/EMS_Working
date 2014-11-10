package com.EMS.entities.util;

import com.EMS.enums.QuestionTypes;
import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value="com.EMS.entities.util.QuestionTypeConverter")
public class QuestionTypeConverter extends EnumConverter {

    public QuestionTypeConverter() {
        super(QuestionTypes.class);
    }

}