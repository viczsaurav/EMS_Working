package com.EMS.controllers;

import com.EMS.entities.Question;
import com.EMS.ejb.QuestionFacade;
import com.EMS.entities.util.JsfUtil;
import com.EMS.entities.util.PaginationHelper;
import com.EMS.enums.QuestionTypes;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

@Named("questionController")
@javax.faces.view.ViewScoped
public class QuestionController implements Serializable {

    private Question current;
    private DataModel items = null;
    @EJB
    private com.EMS.ejb.QuestionFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private String createquestionTypeView="/templates/test.xhtml";
    private String editquestionTypeView="/templates/test.xhtml";
    private String viewquestionTypeView="/templates/test.xhtml";

    public String getEditquestionTypeView() {
        return editquestionTypeView;
    }

    public void setEditquestionTypeView(String editquestionTypeView) {
        this.editquestionTypeView = editquestionTypeView;
    }

    public String getViewquestionTypeView() {
        return viewquestionTypeView;
    }

    public void setViewquestionTypeView(String viewquestionTypeView) {
        this.viewquestionTypeView = viewquestionTypeView;
    }
    private QuestionTypes questionType;

    public String getCreatequestionTypeView() {
        return createquestionTypeView;
    }

    public void setCreatequestionTypeView(String createquestionTypeView) {
        this.createquestionTypeView = createquestionTypeView;
    }

    public QuestionTypes getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionTypes questionType) {
        this.questionType = questionType;
    }

    public QuestionController() {
    }
    
    public void onQuestionTypeChange()
    {
        this.createquestionTypeView = ResourceBundle.getBundle("/Bundle").getString("createQuestionTemplatePath")+questionType.toString().toLowerCase()+".xhtml";
    }
    
    public QuestionTypes[] QuestionTypesArray()
    {
        return QuestionTypes.values();
    }

    public Question getSelected() {
        if (current == null) {
            current = new Question();
            selectedItemIndex = -1;
        }
        return current;
    }
    public void setSelected(Question selected) {
       current = selected;
    }

    private QuestionFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Question) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Question();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.setTypeOfQuestion(QuestionTypes.ESSAY);
            getFacade().create(current);
            getFacade().flushQuestions();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("QuestionCreated"));
            recreateModel();
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Question) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return ResourceBundle.getBundle("/Bundle").getString("editQuestionTemplatePath")+current.getTypeOfQuestion().toString().toLowerCase()+".xhtml";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("QuestionUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("QuestionDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Question getQuestion(java.lang.Long id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Question.class)
    public static class QuestionControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            QuestionController controller = (QuestionController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "questionController");
            return controller.getQuestion(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Question) {
                Question o = (Question) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Question.class.getName());
            }
        }

    }

}
