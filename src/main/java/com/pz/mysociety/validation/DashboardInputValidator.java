package com.pz.mysociety.validation;

import com.pz.mysociety.common.constant.Status;
import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.exception.PZConstraintViolationException;
import com.pz.mysociety.common.exception.constraint.PZConstraint;
import com.pz.mysociety.common.exception.constraintType.PZConstraintExceptionEnum;
import com.pz.mysociety.common.util.Functions;
import com.pz.mysociety.model.Request.dashboardRequestVO.DashboardMasterVO;
import com.pz.mysociety.model.Request.dashboardRequestVO.FeatureCategoryVO;
import com.pz.mysociety.model.Request.dashboardRequestVO.FeatureTypeVO;
import com.pz.mysociety.model.Request.dashboardRequestVO.SubCategoryVO;
import com.pz.mysociety.model.Request.dashboardRequestVO.CardMasterVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class DashboardInputValidator {

    public void addFeatureCategoryInputValidation(FeatureCategoryVO featureCategoryVO) throws PZConstraintViolationException{
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if(!Functions.nonNullString(featureCategoryVO.getName())){
            PZConstraint pzConstraint = new PZConstraint("DashboardInputValidator","addFeatureCategoryInputValidation", "Name", "DashboardService", ValidationMessages.Name_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if(!Functions.nonNullString(featureCategoryVO.getRedirect())){
            PZConstraint pzConstraint = new PZConstraint("DashboardInputValidator","addFeatureCategoryInputValidation", "Redirect", "DashboardService", ValidationMessages.Redirect_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if(pzConstraints.size() > 0){
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }

    public void updateFeatureCategoryInputValidation(FeatureCategoryVO featureCategoryVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if(featureCategoryVO.getId() == 0){
            PZConstraint pzConstraint = new PZConstraint("DashboardInputValidator","updateFeatureCategoryInputValidation", "Id", "DashboardService", ValidationMessages.ID_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if(!Functions.nonNullString(featureCategoryVO.getRedirect())){
            PZConstraint pzConstraint = new PZConstraint("DashboardInputValidator","updateFeatureCategoryInputValidation", "Redirect", "DashboardService", ValidationMessages.Redirect_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if(!Functions.nonNullString(featureCategoryVO.getName())){
            PZConstraint pzConstraint = new PZConstraint("DashboardInputValidator","updateFeatureCategoryInputValidation", "Name", "DashboardService", ValidationMessages.Name_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }


        if(pzConstraints.size() > 0){
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void addSubCategoryInputValidation(SubCategoryVO subCategoryVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if(subCategoryVO.getFeatureId() == 0){
            PZConstraint pzConstraint = new PZConstraint("DashboardInputValidator","addSubCategoryInputValidation", "Id", "DashboardService", ValidationMessages.Feature_Category_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if(!Functions.nonNullString(subCategoryVO.getName())){
            PZConstraint pzConstraint = new PZConstraint("DashboardInputValidator","addSubCategoryInputValidation", "Name", "DashboardService", ValidationMessages.Name_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if(!Functions.nonNullString(subCategoryVO.getRedirect())){
            PZConstraint pzConstraint = new PZConstraint("DashboardInputValidator","addSubCategoryInputValidation", "Redirect", "DashboardService", ValidationMessages.Redirect_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if(pzConstraints.size() > 0){
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void updateSubCategoryInputValidation(SubCategoryVO subCategoryVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if(subCategoryVO.getId() == 0){
            PZConstraint pzConstraint = new PZConstraint("DashboardInputValidator","updateSubCategoryInputValidation", "Id", "DashboardService", ValidationMessages.ID_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if(subCategoryVO.getFeatureId() == 0){
            PZConstraint pzConstraint = new PZConstraint("DashboardInputValidator","updateSubCategoryInputValidation", "Id", "DashboardService", ValidationMessages.Feature_Category_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if(!Functions.nonNullString(subCategoryVO.getRedirect())){
            PZConstraint pzConstraint = new PZConstraint("DashboardInputValidator","updateSubCategoryInputValidation", "Redirect", "DashboardService", ValidationMessages.Redirect_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if(!Functions.nonNullString(subCategoryVO.getName())){
            PZConstraint pzConstraint = new PZConstraint("DashboardInputValidator","updateSubCategoryInputValidation", "Name", "DashboardService", ValidationMessages.Name_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }


        if(pzConstraints.size() > 0){
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void addFeatureTypeCategoryInputValidation(FeatureTypeVO featureTypeVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();


        if(featureTypeVO.getFeatureId() == 0){
            PZConstraint pzConstraint = new PZConstraint("DashboardInputValidator","addFeatureTypeCategoryInputValidation", "Id", "DashboardService", ValidationMessages.Feature_Category_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if(!Functions.nonNullString(featureTypeVO.getName())){
            PZConstraint pzConstraint = new PZConstraint("DashboardInputValidator","addFeatureTypeCategoryInputValidation", "Name", "DashboardService", ValidationMessages.Name_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if(!Functions.nonNullString(featureTypeVO.getDescription())){
            PZConstraint pzConstraint = new PZConstraint("DashboardInputValidator","addFeatureTypeCategoryInputValidation", "Description", "DashboardService", ValidationMessages.Description_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if(!Functions.nonNullString(featureTypeVO.getRedirect())){
            PZConstraint pzConstraint = new PZConstraint("DashboardInputValidator","addFeatureTypeCategoryInputValidation", "Redirect", "DashboardService", ValidationMessages.Redirect_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }


        if(pzConstraints.size() > 0){
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void updateFeatureTypeCategoryInputValidation(FeatureTypeVO featureTypeVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();


        if(featureTypeVO.getFeatureId() == 0){
            PZConstraint pzConstraint = new PZConstraint("DashboardInputValidator","updateFeatureTypeCategoryInputValidation", "Id", "DashboardService", ValidationMessages.Feature_Category_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if(!Functions.nonNullString(featureTypeVO.getName())){
            PZConstraint pzConstraint = new PZConstraint("DashboardInputValidator","updateFeatureTypeCategoryInputValidation", "Name", "DashboardService", ValidationMessages.Name_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if(!Functions.nonNullString(featureTypeVO.getDescription())){
            PZConstraint pzConstraint = new PZConstraint("DashboardInputValidator","updateFeatureTypeCategoryInputValidation", "Description", "DashboardService", ValidationMessages.Description_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if(!Functions.nonNullString(featureTypeVO.getRedirect())){
            PZConstraint pzConstraint = new PZConstraint("DashboardInputValidator","updateFeatureTypeCategoryInputValidation", "Redirect", "DashboardService", ValidationMessages.Redirect_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }


        if(pzConstraints.size() > 0){
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }


    public void addDashboardFeatureTypeInputValidation(FeatureTypeVO featureTypeVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();


        if(featureTypeVO.getId() == 0){
            PZConstraint pzConstraint = new PZConstraint("DashboardInputValidator","addDashboardFeatureTypeInputValidation", "Id", "DashboardService", ValidationMessages.ID_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if(featureTypeVO.getAction().equalsIgnoreCase(Status.ADD)){
            if(featureTypeVO.getDashboardId() == 0){
                PZConstraint pzConstraint = new PZConstraint("DashboardInputValidator","addDashboardFeatureTypeInputValidation", "Id", "DashboardService", ValidationMessages.Dashboard_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if(featureTypeVO.getPriorityOrder() == 0){
                PZConstraint pzConstraint = new PZConstraint("DashboardInputValidator","addDashboardFeatureTypeInputValidation", "Id", "DashboardService", ValidationMessages.Priority_Order_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }
        }


        if(pzConstraints.size() > 0){
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void addDashboardInputValidation(DashboardMasterVO dashboardMasterVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();


        if(dashboardMasterVO.getAction().equalsIgnoreCase(Status.UPDATE)) {
            if (dashboardMasterVO.getId() == 0) {
                PZConstraint pzConstraint = new PZConstraint("DashboardInputValidator", "addDashboardInputValidation", "Id", "DashboardService", ValidationMessages.ID_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }
        }

        if(dashboardMasterVO.getLabelId() == 0){
            PZConstraint pzConstraint = new PZConstraint("DashboardInputValidator","addDashboardInputValidation", "Id", "DashboardService", ValidationMessages.Label_Type_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if(!Functions.nonNullString(dashboardMasterVO.getLabelType())){
            PZConstraint pzConstraint = new PZConstraint("DashboardInputValidator","addDashboardInputValidation", "Label", "DashboardService", ValidationMessages.Label_Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if(dashboardMasterVO.getPriorityOrder() == 0){
            PZConstraint pzConstraint = new PZConstraint("DashboardInputValidator","addDashboardInputValidation", "Priority", "DashboardService", ValidationMessages.Priority_Order_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if(!Functions.nonNullString(dashboardMasterVO.getCardName())){
            PZConstraint pzConstraint = new PZConstraint("DashboardInputValidator","addDashboardInputValidation", "Name", "DashboardService", ValidationMessages.Card_Name_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if(dashboardMasterVO.getCardSize() == 0){
            PZConstraint pzConstraint = new PZConstraint("DashboardInputValidator","addDashboardInputValidation", "Size", "DashboardService", ValidationMessages.Card_Name_Format_Error, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }


        if(pzConstraints.size() > 0){
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void getDashboardFeatureListInputValidation(FeatureCategoryVO featureCategoryVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();


        if(!Functions.nonNullString(featureCategoryVO.getName())){
            PZConstraint pzConstraint = new PZConstraint("DashboardInputValidator","getDashboardFeatureListInputValidation", "Name", "DashboardService", ValidationMessages.Name_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }


        if(pzConstraints.size() > 0){
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void addCardMaster(CardMasterVO cardMasterVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (cardMasterVO.getAction().equalsIgnoreCase(Status.UPDATE)) {

            if (cardMasterVO.getId() == 0) {
                PZConstraint pzConstraint = new PZConstraint("DashboardInputValidator", "addDashBoardInputValidator", "Id", "CardMasterService", ValidationMessages.Product_Service_Category_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);

                pzConstraints.add(pzConstraint);
            }
        } else {

            if (!Functions.nonNullString(cardMasterVO.getCardName())) {
                PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "addDashboardInputValidator", "Name", "CardMasterService", ValidationMessages.Name_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);

            }
        }
    }








}
