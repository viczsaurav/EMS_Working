package com.EMS.controllers;

import com.EMS.ejb.ExamAnswersFacade;
import com.EMS.entities.ExamPaper;
import com.EMS.entities.ExamSession;
import com.EMS.ejb.ExamSessionFacade;
import com.EMS.ejb.QuestionFacade;
import com.EMS.ejb.QuestionMultiAnswerFacade;
import com.EMS.ejb.QuestionMultiChoiceFacade;
import com.EMS.entities.ExamAnswers;
import com.EMS.entities.Question;
import com.EMS.entities.util.JsfUtil;
import com.EMS.entities.util.PaginationHelper;
import com.EMS.enums.QuestionTypes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

@Named("examSessionController")
@SessionScoped
public class ExamSessionController implements Serializable {

    private ExamSession current;
    private DataModel items = null;
    @EJB
    private com.EMS.ejb.ExamSessionFacade ejbFacade;
    @EJB
    QuestionFacade questionEjb;
    @EJB
    QuestionMultiAnswerFacade multAnsEjb;
    @EJB
    QuestionMultiChoiceFacade multChoiceEjb;
    @EJB 
    ExamAnswersFacade examAnswersEjb;

    private PaginationHelper pagination;
    private int selectedItemIndex;
    @Inject
    private ExamPaper ePaper;
    @Inject
    private LoginBean logged;

    private List<Question> listOfQuestions;
    private Iterator<Question> itrQuestion;
    private Question currentQuestion;
    private ExamAnswers examAnswers;
    private String singleAnswer;
    private List<String> multiAnswers;

    public List<String> getMultiAnswers() {
        return multiAnswers;
    }

    public void setMultiAnswers(List<String> multiAnswers) {
        this.multiAnswers = multiAnswers;
    }
    private ExternalContext externalContext;

    public ExamSessionController() {
        externalContext = FacesContext.getCurrentInstance().getExternalContext();
        
    }


    public String getSingleAnswer() {
        return singleAnswer;
    }

    public void setSingleAnswer(String singleAnswer) {
        this.singleAnswer = singleAnswer;
    }

    public ExamAnswers getExamAnswers() {
        return examAnswers;
    }

    public void setExamAnswers(ExamAnswers examAnswers) {
        this.examAnswers = examAnswers;
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(Question currentQuestion) {
        this.currentQuestion = currentQuestion;
    }


    public String startExam() {
        System.out.println(">>>Epaper Module>>>> " + ePaper.getModule().getName());
        System.out.println(">> Student Logged in " + logged.getAppUser().getStudent().getName());
        externalContext.addResponseHeader("Test", "test");
        listOfQuestions = questionEjb.findAll();
        itrQuestion = listOfQuestions.iterator();
        currentQuestion = itrQuestion.next();
        singleAnswer = "";
        
        examAnswers = new ExamAnswers();
        examAnswers.setExamSession(current);
        
        
        return "LiveExamPage";
    }

    public List<Question> getListOfQuestions() {
        return listOfQuestions;
    }

    public void setListOfQuestions(List<Question> listOfQuestions) {
        this.listOfQuestions = listOfQuestions;
    }

    public void retrieveNextQuestion() {
        if (itrQuestion.hasNext()) {
            examAnswers = new ExamAnswers();
            examAnswers.setQuestion(currentQuestion);
            examAnswers.setExamSession(current);
            examAnswers.setAnswers(answerType(currentQuestion));
            System.out.println("question :"+examAnswers.getQuestion().getText());
            System.out.println("Session :"+examAnswers.getExamSession().getStudent().getName());
            System.out.println("Answer :"+examAnswers.getAnswers().toString());
//            examAnswersEjb.create(examAnswers);    
            currentQuestion = itrQuestion.next();
        } else {
            currentQuestion = null;
        }
    }
    
    //Helper to choose the type of answer based on the question;
    private List<String> answerType(Question currentQuestion) {
        List<String> answers = new ArrayList<String>();
        
        if(currentQuestion.getTypeOfQuestion() == QuestionTypes.ESSAY || currentQuestion.getTypeOfQuestion() == QuestionTypes.MULTI_CHOICE)
        {
             System.out.println("Single Answer :"+singleAnswer);
            answers.add(singleAnswer);
        }
        else
        {
              System.out.println("Multi Answer :"+multiAnswers);
            answers = multiAnswers;
        }
        return answers;
    }

    public List<String> getQuesOptions() {
        switch (currentQuestion.getTypeOfQuestion()) {
            case ESSAY:
                return null;
            case MULTI_CHOICE:
                return multChoiceEjb.find(currentQuestion.getId()).getChoices();
            case MULTI_ANSWER:
                return multAnsEjb.find(currentQuestion.getId()).getChoices();
            default:
                return null;
        }
    }

    public ExamSession getSelected() {
        if (current == null) {
            current = new ExamSession();
            selectedItemIndex = -1;
        }
        return current;
    }

    private ExamSessionFacade getFacade() {
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
        current = (ExamSession) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new ExamSession();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ExamSessionCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (ExamSession) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ExamSessionUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (ExamSession) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ExamSessionDeleted"));
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

    public ExamSession getExamSession(java.lang.Long id) {
        return ejbFacade.find(id);
    }


    @FacesConverter(forClass = ExamSession.class)
    public static class ExamSessionControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ExamSessionController controller = (ExamSessionController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "examSessionController");
            return controller.getExamSession(getKey(value));
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
            if (object instanceof ExamSession) {
                ExamSession o = (ExamSession) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + ExamSession.class.getName());
            }
        }

    }

}
