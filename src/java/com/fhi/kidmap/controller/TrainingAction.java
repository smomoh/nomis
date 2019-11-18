/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.Training;
import com.fhi.kidmap.business.TrainingParticipant;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.OrganizationRecordsDao;
import com.fhi.kidmap.dao.OrganizationRecordsDaoImpl;
import com.fhi.kidmap.dao.TrainingCategoryDao;
import com.fhi.kidmap.dao.TrainingCategoryDaoImpl;
import com.fhi.kidmap.dao.TrainingDao;
import com.fhi.kidmap.dao.TrainingDaoImpl;
import com.fhi.kidmap.dao.TrainingDesignationDao;
import com.fhi.kidmap.dao.TrainingDesignationDaoImpl;
import com.fhi.kidmap.dao.TrainingInformationSetupDao;
import com.fhi.kidmap.dao.TrainingInformationSetupDaoImpl;
import com.fhi.kidmap.dao.TrainingParticipantDao;
import com.fhi.kidmap.dao.TrainingParticipantDaoImpl;
import com.fhi.kidmap.dao.TrainingStatusSetupDao;
import com.fhi.kidmap.dao.TrainingStatusSetupDaoImpl;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Siaka
 */
public class TrainingAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    /**
     * This is the action called from the Struts framework.
     *
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
        TrainingForm tf=(TrainingForm)form;
        Training trn=new Training();
        TrainingParticipant tp=new TrainingParticipant();
        TrainingDao trnDao=new TrainingDaoImpl();
        OrganizationRecordsDao orgdao=new OrganizationRecordsDaoImpl();
        TrainingDesignationDao tddao=new TrainingDesignationDaoImpl();
        TrainingInformationSetupDao tisdao=new TrainingInformationSetupDaoImpl();
        TrainingStatusSetupDao tssdao=new TrainingStatusSetupDaoImpl();
        TrainingCategoryDao tcdao=new TrainingCategoryDaoImpl();
        TrainingParticipantDao tpdao=new TrainingParticipantDaoImpl();
        
        AppUtility appUtil=new AppUtility();
        DaoUtil util=new DaoUtil();
        HttpSession session=request.getSession();
        trn.setAge(tf.getAge());
        String trainingCategory=tf.getCategory();
        trn.setCategory(tf.getCategory());
        trn.setDesignation(tf.getDesignation());
        trn.setEmail(tf.getEmail());
        trn.setStartDate(appUtil.processMthDayYearToMysqlFormat(tf.getStartDate()));
        trn.setEndDate(appUtil.processMthDayYearToMysqlFormat(tf.getEndDate())); 
        trn.setGender(tf.getGender());
        trn.setTrainingId(tf.getNameOfTraining());
        trn.setPhone(tf.getPhone());
        trn.setParticipantId(tf.getParticipantId());
        trn.setTrainingStatus(tf.getTrainingStatus());
        trn.setDateModified(appUtil.getCurrentDate());
        
        String partnerCode=util.getPartnerCode(appUtil.getCurrentUser(session));
        trn.setPartnerCode(partnerCode);
        String trainingId=tf.getTrainingId();
        String hiddenId=tf.getHiddenId();

        tp.setAge(tf.getAge());
        tp.setDateModified(appUtil.getCurrentDate());
        tp.setDesignation(tf.getDesignation());
        tp.setEmail(tf.getEmail());
        tp.setGender(tf.getGender());
        tp.setOrganizationCode(tf.getOrganizationCode());
        tp.setPhone(tf.getPhone());
        tp.setTraineeId(tf.getParticipantId());
        tp.setParticipantName(tf.getParticipantName());
        
        List trnList=trnDao.getTrainings();
        List catList=tcdao.getAllTrainingCategories();
        List tdList=tddao.getTrainingDesignations(trainingCategory);
        List tisList=tisdao.getAllTrainingInformationSetups();
        List tssList=tssdao.getAllTrainingStatusSetups();
        List orgList=orgdao.getOrganizationList();

        
        tf.setTrainingCategoryList(catList);
        tf.setTrainingDesignationList(tdList);
        tf.setTrainingInfoList(tisList);
        tf.setTrainingStatusList(tssList);
        tf.setOrganizationList(orgList);

        tf.setTrainingList(trnList);
        session.setAttribute("trainingSaveBtnDisabled", "false");
        session.setAttribute("trainingModifyBtnDisabled", "true");
        String requiredAction=tf.getActionName();
        if(requiredAction==null)
        {
            tf.reset(mapping, request);
        }
        else if(requiredAction.equalsIgnoreCase("designation"))
        {
            tf.setActionName(null);
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("details"))
        {
            trn=trnDao.getTraining(trainingId);
            tf.reset(mapping, request);
            if(trn !=null)
            {
                tf.setAge(trn.getAge());
                tf.setCategory(trn.getCategory());
                tf.setDesignation(trn.getDesignation());
                tf.setEmail(trn.getEmail());
                tf.setHiddenId(trn.getRecordId());
                tf.setStartDate(appUtil.convertMysqlDateTomonthDayYear(trn.getStartDate()));
                tf.setEndDate(appUtil.convertMysqlDateTomonthDayYear(trn.getEndDate()));
                tf.setGender(trn.getGender());
                tf.setNameOfTraining(trn.getTrainingId());
                tf.setOrganizationCode(trn.getOrganizationCode());
                tf.setPhone(trn.getPhone());
                tf.setParticipantId(trn.getParticipantId());
                tf.setParticipantName(trn.getParticipantName());
                tf.setTrainingStatus(tf.getTrainingStatus());
                session.setAttribute("trainingSaveBtnDisabled", "true");
                session.setAttribute("trainingModifyBtnDisabled", "false");
            }
        }
        else if(requiredAction.equalsIgnoreCase("save"))
        {
            String traineeId=tpdao.generateUniqueTrainingId(tp);
            String participantId=tpdao.generateUniqueId();
            tp.setTraineeId(traineeId);
            trn.setParticipantId(participantId);
            tp.setParticipantId(participantId);
            trnDao.saveTraining(trn);
            tpdao.saveOrUpdateTrainingParticipant(tp);
        }
        else if(requiredAction.equalsIgnoreCase("modify"))
        {
            trn.setTrainingId(hiddenId);
            trnDao.updateTraining(trn);
            tpdao.saveOrUpdateTrainingParticipant(tp);
        }
        else if(requiredAction.equalsIgnoreCase("delete"))
        {
            trn.setTrainingId(hiddenId);
            trnDao.deleteTraining(trn);
        }
        tf.reset(mapping, request);
        trnList=trnDao.getTrainings();
        tf.setTrainingList(trnList);
        return mapping.findForward(SUCCESS);
    }
    
}
