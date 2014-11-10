package com.EMS.controllers;

import com.EMS.entities.QuestionMultiChoice;
import com.EMS.ejb.QuestionMultiChoiceFacade;
import com.EMS.entities.util.JsfUtil;
import com.EMS.entities.util.JsfUtil.PersistAction;
import com.EMS.enums.QuestionTypes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("questionMultiChoiceController")
@SessionScoped
public class QuestionMultiChoiceController implements Serializable {

    @EJB
    private com.EMS.ejb.QuestionMultiChoiceFacade ejbFacade;
    private List<QuestionMultiChoice> items = null;
    private QuestionMultiChoice selected;

    public QuestionMultiChoiceController() {
    }

    public QuestionMultiChoice getSelected() {
        return selected;
    }
    
    @PostConstruct
    public void init()
    {
     selected = new QuestionMultiChoice();       
    }
    
     public void removeChoice(int index)
    {
        List<String> choices = selected.getChoices();
        if(choices != null)
        {
        choices.remove(index);
        selected.setChoices(choices);
        }
              
    }
    public void addChoice()
    {
         List<String> choices = selected.getChoices();
      
        if(choices==null)
        {
            choices = new ArrayList<String>();
        }
        choices.add("Choice "+choices.size());
        
        selected.setChoices(choices);      
    }

    public void setSelected(QuestionMultiChoice selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private QuestionMultiChoiceFacade getFacade() {
        return ejbFacade;
    }

    public QuestionMultiChoice prepareCreate() {
        selected = new QuestionMultiChoice();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("QuestionMultiChoiceCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("QuestionMultiChoiceUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("QuestionMultiChoiceDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<QuestionMultiChoice> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                 if(persistAction == PersistAction.CREATE)
                {
                    selected.setTypeOfQuestion(QuestionTypes.MULTI_CHOICE);
                    getFacade().create(selected);
                }   if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public QuestionMultiChoice getQuestionMultiChoice(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<QuestionMultiChoice> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<QuestionMultiChoice> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = QuestionMultiChoice.class)
    public static class QuestionMultiChoiceControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            QuestionMultiChoiceController controller = (QuestionMultiChoiceController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "questionMultiChoiceController");
            return controller.getQuestionMultiChoice(getKey(value));
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
            if (object instanceof QuestionMultiChoice) {
                QuestionMultiChoice o = (QuestionMultiChoice) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), QuestionMultiChoice.class.getName()});
                return null;
            }
        }

    }

}
