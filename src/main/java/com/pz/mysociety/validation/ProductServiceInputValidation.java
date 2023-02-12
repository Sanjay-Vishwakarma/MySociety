package com.pz.mysociety.validation;

import com.pz.mysociety.common.constant.Status;
import com.pz.mysociety.common.constant.Types;
import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.exception.PZConstraintViolationException;
import com.pz.mysociety.common.exception.constraint.PZConstraint;
import com.pz.mysociety.common.exception.constraintType.PZConstraintExceptionEnum;
import com.pz.mysociety.common.util.Functions;
import com.pz.mysociety.model.Request.productServiceRequest.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ProductServiceInputValidation {
    public void addProductServiceCategoryInputValidation(ProductServiceCategoryVO productServiceCategoryVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if(productServiceCategoryVO.getAction().equalsIgnoreCase(Status.UPDATE)) {
            if (productServiceCategoryVO.getId() == 0) {
                PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "updateProductServiceCategoryInputValidation", "Id", "ProductMasterService", ValidationMessages.ID_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }
        }else {

            if (!Functions.nonNullString(productServiceCategoryVO.getName())) {
                PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "addProductServiceCategoryInputValidation", "Name", "ProductMasterService", ValidationMessages.Name_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);

            }

            if (!Functions.nonNullString(productServiceCategoryVO.getType())) {
                PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "addProductServiceCategoryInputValidation", "Type", "ProductMasterService", ValidationMessages.Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);

            }

//            if (!Functions.nonNullString(productServiceCategoryVO.getIconUrl())) {
//                PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "addProductServiceCategoryInputValidation", "Icon", "ProductMasterService", ValidationMessages.Icon_Url_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
//                pzConstraints.add(pzConstraint);
//
//            }

            if (pzConstraints.size() > 0) {
                throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
            }
        }

    }

    public void addProductServiceSubCategoryInputValidation(ProductServiceSubCategoryVO productServiceSubCategoryVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if(productServiceSubCategoryVO.getAction().equalsIgnoreCase(Status.UPDATE)) {
            if (productServiceSubCategoryVO.getId() == 0) {
                PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "addProductServiceSubCategoryInputValidation", "Id", "ProductMasterService", ValidationMessages.ID_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }
        }else {


            if (productServiceSubCategoryVO.getProductCategoryId() == 0) {
                PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "addProductServiceSubCategoryInputValidation", "Id", "ProductMasterService", ValidationMessages.Product_Service_Category_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if (!Functions.nonNullString(productServiceSubCategoryVO.getName())) {
                PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "addProductServiceSubCategoryInputValidation", "Name", "ProductMasterService", ValidationMessages.Name_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);

            }

            if (!Functions.nonNullString(productServiceSubCategoryVO.getType())) {
                PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "addProductServiceSubCategoryInputValidation", "Type", "ProductMasterService", ValidationMessages.Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);

            }

            if (!Functions.nonNullString(productServiceSubCategoryVO.getIconUrl())) {
                PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "addProductServiceSubCategoryInputValidation", "Icon", "ProductMasterService", ValidationMessages.Icon_Url_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);

            }

            if (pzConstraints.size() > 0) {
                throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
            }
        }
    }

    public void addProductInputValidation(ProductServiceMasterVO productServiceMasterVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if(productServiceMasterVO.getAction().equalsIgnoreCase(Status.UPDATE)) {
            if (productServiceMasterVO.getId() == 0) {
                PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "addProductInputValidation", "Id", "ProductMasterService", ValidationMessages.ID_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }
        }else {

            if (productServiceMasterVO.getSocietyId() == 0) {
                PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "addProductInputValidation", "Id", "ProductMasterService", ValidationMessages.Society_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if (productServiceMasterVO.getUnitId() == 0) {
                PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "addProductInputValidation", "Id", "ProductMasterService", ValidationMessages.Unit_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if (productServiceMasterVO.getUserId() == 0) {
                PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "addProductInputValidation", "Id", "ProductMasterService", ValidationMessages.User_Id_Requires, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if (productServiceMasterVO.getProductCategoryId() == 0) {
                PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "addProductInputValidation", "Id", "ProductMasterService", ValidationMessages.Product_Service_Category_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

//            if (productServiceMasterVO.getProductSubCategoryId() == 0) {
//                PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "addProductInputValidation", "Id", "ProductMasterService", ValidationMessages.Product_Service_Sub_Category_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
//                pzConstraints.add(pzConstraint);
//            }

            if (!Functions.nonNullString(productServiceMasterVO.getTitle())) {
                PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "addProductInputValidation", "Title", "ProductMasterService", ValidationMessages.Title_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if (!Functions.nonNullString(productServiceMasterVO.getDescription())) {
                PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "addProductInputValidation", "Description", "ProductMasterService", ValidationMessages.Description_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if (!Functions.nonNullString(productServiceMasterVO.getPurchaseYear())) {
                PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "addProductInputValidation", "Purchase", "ProductMasterService", ValidationMessages.Purchase_Year_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if (!Functions.nonNullString(productServiceMasterVO.getType())) {
                PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "addProductInputValidation", "Type", "ProductMasterService", ValidationMessages.Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if (productServiceMasterVO.getType().equalsIgnoreCase(Status.SALE)) {
                if (productServiceMasterVO.getPrice() == 0) {
                    PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "addProductInputValidation", "Price", "ProductMasterService", ValidationMessages.Price_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                    pzConstraints.add(pzConstraint);
                }
            }

            if (!Functions.nonNullString(productServiceMasterVO.getMobileNumber())) {
                PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "addProductInputValidation", "Mobile", "ProductMasterService", ValidationMessages.Mobile_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if (!Functions.nonNullString(productServiceMasterVO.getAddress())) {
                PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "addProductInputValidation", "Address", "ProductMasterService", ValidationMessages.Address_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if (productServiceMasterVO.getProductImage().size() <= 0) {
                PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "addProductInputValidation", "Image", "ProductMasterService", ValidationMessages.Product_Service_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }



            if (pzConstraints.size() > 0) {
                throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
            }
        }

    }


    public void addProductImageInputValidation(ProductServiceImageMasterVO productServiceImageMasterVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (productServiceImageMasterVO.getProductId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "addProductImageInputValidation", "ID", "ProductMasterService", ValidationMessages.ID_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(productServiceImageMasterVO.getType())) {
            PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "addProductImageInputValidation", "Type", "ProductMasterService", ValidationMessages.Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!productServiceImageMasterVO.getType().equalsIgnoreCase(Types.PRODUCT) && !productServiceImageMasterVO.getType().equalsIgnoreCase(Types.SERVICE)) {
            PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "addProductImageInputValidation", "Type", "ProductMasterService", ValidationMessages.Invalid_Type_Error, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(productServiceImageMasterVO.getImageUrl())) {
            PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "addProductImageInputValidation", "Image", "ProductMasterService", ValidationMessages.Product_Service_Image_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void deleteProductImageInputValidation(ProductServiceImageMasterVO productServiceImageMasterVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (productServiceImageMasterVO.getId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "deleteProductImageInputValidation", "ID", "ProductMasterService", ValidationMessages.ID_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }

    public void getNearByProduct(ProductServiceMasterVO productServiceMasterVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (!Functions.nonNullString(productServiceMasterVO.getType())) {
            PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "getNearByProduct", "Type", "ProductMasterService", ValidationMessages.Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!productServiceMasterVO.getType().equalsIgnoreCase(Types.PRODUCT) && !productServiceMasterVO.getType().equalsIgnoreCase(Types.SERVICE)) {
            PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "getNearByProduct", "Type", "ProductMasterService", ValidationMessages.Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if(productServiceMasterVO.getType().equalsIgnoreCase(Types.PRODUCT) && productServiceMasterVO.getProductCategoryId() ==0){
            PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "getNearByProduct", "Type", "ProductMasterService", ValidationMessages.Product_Service_Category_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(productServiceMasterVO.getPincode())) {
            PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "getNearByProduct", "Pincode", "ProductMasterService", ValidationMessages.Pincode_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }

    public void addServiceInputValidation(UserServiceMasterVO serviceMasterVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<>();

        if(serviceMasterVO.getAction().equalsIgnoreCase(Status.UPDATE)) {
            if (serviceMasterVO.getId() == 0) {
                PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "addServiceInputValidation", "Id", "ProductMasterService", ValidationMessages.ID_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }
        }else {

            if (serviceMasterVO.getSocietyId() == 0) {
                PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "addServiceInputValidation", "Id", "ProductMasterService", ValidationMessages.Society_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if (serviceMasterVO.getUnitId() == 0) {
                PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "addServiceInputValidation", "Id", "ProductMasterService", ValidationMessages.Unit_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if (serviceMasterVO.getUserId() == 0) {
                PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "addServiceInputValidation", "Id", "ProductMasterService", ValidationMessages.User_Id_Requires, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if (serviceMasterVO.getServiceCategoryId() == 0) {
                PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "addServiceInputValidation", "Id", "ProductMasterService", ValidationMessages.Service_Category_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

//            if (productServiceMasterVO.getProductSubCategoryId() == 0) {
//                PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "addServiceInputValidation", "Id", "ProductMasterService", ValidationMessages.Product_Service_Sub_Category_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
//                pzConstraints.add(pzConstraint);
//            }

            if (!Functions.nonNullString(serviceMasterVO.getTitle())) {
                PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "addProductInputValidation", "Title", "ProductMasterService", ValidationMessages.Title_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if (!Functions.nonNullString(serviceMasterVO.getDescription())) {
                PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "addServiceInputValidation", "Description", "ProductMasterService", ValidationMessages.Description_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if (!Functions.nonNullString(serviceMasterVO.getMobileNumber())) {
                PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "addServiceInputValidation", "Mobile", "ProductMasterService", ValidationMessages.Mobile_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if (serviceMasterVO.getServicePrice() == 0) {
                PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "addServiceInputValidation", "Price", "ProductMasterService", ValidationMessages.Price_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if (!Functions.nonNullString(serviceMasterVO.getServiceType())) {
                PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "addServiceInputValidation", "Type", "ProductMasterService", ValidationMessages.Service_Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if (!Functions.nonNullString(serviceMasterVO.getServiceDay())) {
                PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "addServiceInputValidation", "Day", "ProductMasterService", ValidationMessages.Service_Day_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if (!Functions.nonNullString(serviceMasterVO.getServiceRange())) {
                PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "addServiceInputValidation", "Range", "ProductMasterService", ValidationMessages.Service_Range_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if (serviceMasterVO.getServiceProvide().equalsIgnoreCase(Types.IN_PERSON)) {
                if (!Functions.nonNullString(serviceMasterVO.getServiceLocation())) {
                    PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "addServiceInputValidation", "Location", "ProductMasterService", ValidationMessages.Service_Location_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                    pzConstraints.add(pzConstraint);
                }
            }

            if (serviceMasterVO.getProductImage() == null) {
                PZConstraint pzConstraint = new PZConstraint("ProductServiceInputValidation", "addServiceInputValidation", "Image", "ProductMasterService", ValidationMessages.Product_Service_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if (pzConstraints.size() > 0) {
                throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
            }
        }


    }
}
