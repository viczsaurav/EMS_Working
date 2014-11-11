package com.EMS.controllers;

import com.EMS.ejb.CourseModuleFacade;
import com.EMS.ejb.ExamPaperFacade;
import com.EMS.ejb.QuestionFacade;
import com.EMS.entities.CourseModule;
import com.EMS.entities.ExamPaper;
import com.EMS.entities.Question;
import com.EMS.entities.Section;
import com.EMS.entities.util.JsfUtil;
import com.EMS.entities.util.PaginationHelper;
import com.EMS.enums.SectionTypes;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;

@Named("examPaperController")
@ViewScoped
public class ExamPaperController implements Serializable {

    private ExamPaper current;
    private DataModel items = null;
    @EJB
    private QuestionFacade questionFacade;
    @EJB
    private ExamPaperFacade ejbFacade;
    private PaginationHelper pagination;

    private int selectedItemIndex;
    private CourseModule selectedModule;
    private List<Section> sections;
    private List<Question> questions;
    @EJB
    private CourseModuleFacade courseModuleFacade;
    private List<CourseModule> courseModules;
//    private Section selectedSection;
    @Inject private Section section;
    
//    private String sectionName;
    private SectionTypes sectionType;
    private int sectionMarks;
    private List<Question> selectedQuestions;

    public SectionTypes[] getSectionTypes() {
        return SectionTypes.values();
    }
    
    public SectionTypes getSectionAutomatic(){
        return SectionTypes.AUTOMATIC;
    }
    
    public SectionTypes getSectionManual(){
        return SectionTypes.MANUAL;
    }

    @PostConstruct
    public void init() {
        current = new ExamPaper();
        sections = new ArrayList<Section>();
        courseModules = new ArrayList<CourseModule>();
        questions = new ArrayList<Question>();
//        section = new Section();
        courseModules = courseModuleFacade.findAll();
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public List<CourseModule> getCourseModules() {
        return courseModules;
    }

    public SectionTypes getSectionType() {
        return sectionType;
    }

    public void setSectionType(SectionTypes sectionType) {
        this.sectionType = sectionType;
    }

    public int getSectionMarks() {
        return sectionMarks;
    }

    public void setSectionMarks(int sectionMarks) {
        this.sectionMarks = sectionMarks;
    }

    public List<Question> getSelectedQuestions() {
        return selectedQuestions;
    }

    public void setSelectedQuestions(List<Question> selectedQuestions) {
        this.selectedQuestions = selectedQuestions;
    }

    public List<Section> getSections() {
        return sections;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public void setCourseModules(List<CourseModule> courseModules) {
        this.courseModules = courseModules;
    }

    public void addSectionToExam() {
//        section.setQuestions(selectedQuestions);
//        section.setSectionType(sectionType);
        if(section.getSectionType().equals(SectionTypes.AUTOMATIC)
                &&(section.getSectionTotalMarks()>0)){
            System.out.println("Automatic Selected");
            section.setSectionTotalMarks(sectionMarks);
        }
        for(Question q:section.getQuestions()){
            section.setSectionTotalMarks(
                    section.getSectionTotalMarks()+q.getMarks());
            questions.remove(q);
        }
        Section addSection = new Section();
        addSection.setQuestions(section.getQuestions());
        addSection.setSectionName(section.getSectionName());
        addSection.setSectionTotalMarks(section.getSectionTotalMarks());
        addSection.setSectionType(section.getSectionType());
        sections.add(addSection);
        section.setQuestions(null);
        selectedQuestions = null;
        sectionType = null;
        sectionMarks = 0;
        section.setSectionName("Section "+(sections.size()+1));
    }

    public void onSelectSectionType() {
        System.out.println(section.getSectionType());
//        System.out.println(e.getObject());
        /*if(e.getComponent().getAttributes().get("sectiontypeValue")=="AUTOMATIC"){
         System.out.println("Automatic Selected");
         }*/
    }

    public CourseModule getSelectedModule() {
        return selectedModule;
    }

    public void setSelectedModule(CourseModule selectedModule) {
        this.selectedModule = selectedModule;
    }

    public ExamPaperController() {
    }

    public ExamPaper getSelected() {
        if (current == null) {
            current = new ExamPaper();
            selectedItemIndex = -1;
        }
        return current;
    }

    public ExamPaper getCurrent() {
        return current;
    }

    public void setCurrent(ExamPaper current) {
        this.current = current;
    }

    private ExamPaperFacade getFacade() {
        return ejbFacade;
    }

    public String save() {
        current.setModule(selectedModule);
        current.setSection(sections);
        return create();
    }

    public void onSelectedModuleChange() {
        System.out.println(">>ExamPaperController examname:" + current.getName());
        sections.clear();
        section.setSectionName("Section " + (sections.size() + 1));
        section.setSectionTotalMarks(0);
        if (selectedModule != null) 
            questions = selectedModule.getQuestions();
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
        current = (ExamPaper) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new ExamPaper();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ExamPaperCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (ExamPaper) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ExamPaperUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (ExamPaper) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ExamPaperDeleted"));
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

    public ExamPaper getExamPaper(java.lang.Long id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = ExamPaper.class)
    public static class ExamPaperControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ExamPaperController controller = (ExamPaperController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "examPaperController");
            return controller.getExamPaper(getKey(value));
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
            if (object instanceof ExamPaper) {
                ExamPaper o = (ExamPaper) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + ExamPaper.class.getName());
            }
        }

    }

}
