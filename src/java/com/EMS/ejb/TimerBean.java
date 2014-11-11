/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.EMS.ejb;

import com.EMS.entities.ExamSession;
import java.io.IOException;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;

/**
 *
 * @author Saurav
 */
@Stateless
public class TimerBean implements AsyncListener{

    private final long start;
    @Resource
    TimerService timerService;

    public TimerBean() {
        this.start = System.currentTimeMillis();
    }

    public TimerBean(long start) {
        this.start = start;
    }
    
    public void createSessionTimer(ExamSession es){
        long interval = es.getExamPaper().getModule().getDuration()*60*60*1000;
        timerService.createTimer(interval, es);
    }
    
    
    public double elapsedTime() {
        long now = System.currentTimeMillis();
        return (now - start) / 1000.0;
    }
    
    @Timeout
    public void invalidateSessions(Timer timer){
        ExamSession es = (ExamSession)timer.getInfo();
        
    }

    @Override
    public void onComplete(AsyncEvent event) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onTimeout(AsyncEvent event) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onError(AsyncEvent event) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onStartAsync(AsyncEvent event) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
