package com.pz.mysociety.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.entity.societyEntity.ParkingSlotEntity;
import com.pz.mysociety.model.Request.*;
import com.pz.mysociety.model.Request.SocietyRequestVO.ParkingSlotVO;
import com.pz.mysociety.model.Request.SocietyRequestVO.SocietyTemplateVO;
import com.pz.mysociety.model.Request.amenityRequestVO.AmenityChatRecordVO;
import com.pz.mysociety.model.Request.dashboardRequestVO.FeatureCategoryVO;
import com.pz.mysociety.model.Request.dashboardRequestVO.FeatureTypeVO;
import com.pz.mysociety.model.Request.dashboardRequestVO.SubCategoryVO;
import com.pz.mysociety.model.Request.gatewayRequestVO.GatewayVO;
import com.pz.mysociety.model.Request.maintenanceRequestVO.MntMasterVO;
import com.pz.mysociety.model.Request.maintenanceRequestVO.SocietyMntFileInvoiceVO;
import com.pz.mysociety.model.Request.maintenanceRequestVO.SocietyMntMappingVO;
import com.pz.mysociety.model.Request.productServiceRequest.ProductServiceCategoryVO;
import com.pz.mysociety.model.Request.productServiceRequest.ProductServiceMasterVO;
import com.pz.mysociety.model.Request.productServiceRequest.ProductServiceSubCategoryVO;
import com.pz.mysociety.model.Request.productServiceRequest.UserServiceMasterVO;
import com.pz.mysociety.model.Request.dashboardRequestVO.CardMasterVO;
import com.pz.mysociety.model.Request.societyUtilRequestVO.ProfessionUtilMasterVO;
import com.pz.mysociety.model.Request.societyUtilRequestVO.LanguageTypeVO;
import com.pz.mysociety.model.Request.userRequestVO.ProfessionMasterVO;
import com.pz.mysociety.model.VO.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.simple.JSONArray;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ResponseVO extends InputVO{

    private String token;
    private HttpStatus status;
    private int statusCode;
    private String message;
    private int id;
    private int amenityId;
    private int averageRating;
    private String societyRole;
    private String adminStatus;
    private String remark;
    private List<String> city;
    private int helperId;
    private int visitorLogId;
    private int loginId;
    private int count;
    private int inActiveCount;
    private int guardPin;
    private int unitId;
    private String unit;
    private boolean isVerified;

    private int pages;
    private int inActivePages;
    private Hashtable<String,Integer> attribute;


    private List<ErrorVO> errorVOList;

    private String checkUnit;

    private UserVO user;
    private List<UserVO> userList;

    private SocietyVO society;
    private List<SocietyVO> societyList;
    private List<Integer> societyId;
    private List<ParkingSlotVO> parkingSlotVOList;
    private List<SocietyUserListVO> societyUserList;

    private SocietyMappingVO societyMappingVO;

    private OwnerMasterVO owner;

    private List<UnitListVO> activeAreaList;
    private UnitListVO unitDetail;
    private List<UnitListVO> inActiveAreaList;

    private List<UnitMasterVO> activeUnitList;
    private List<UnitMasterVO> inActiveUnitList;

    private List<TypeVO> activeType;
    private List<TypeVO> inActiveType;
    private List<AreaTypeVO> areaTypeList;

    private List<SocietyDocumentTypeVO> societyDocumentTypeList;
    private List<UnitDocumentTypeVO> activeUnitDocumentType;

    private List<DashBoardVO> dashBoard;
    private DashBoardVO userDashBoard;

    private List<UnitDocumentMasterVO> unitDocument;

    private List<EmergencyNumberVO> emergencyNumberList;

    private List<SocietyDocumentVO> societyDocumentList;
    private UserVO userVO;
    private List<UserVO> userVOList;
    private List<NoticeTypeVO> activeNoticeType;
    private List<NoticeTypeVO> inActiveNoticeType;
    private List<HelperListVO> activeHelperDetail;
    private List<HelperListVO> notActiveHelperDetail;
    private List<HelperTypeVO> helperTypeList;
    private List<UserListVO> ownerList;

    private HelperListVO helper;
    private HelperVO helperDetails;
    private List<HelperListVO> helperDetail;
    private List<Object> helperListVO;
//    private List<HelperEntity> frequentVisitor;
    private List<HelperListVO> helperList;
    private List<HelperListVO> activeHelperType;
    private List<HelperListVO> inactiveHelperType;
    private List<VisitorVO> visitorList;
    private List<HelperListVO> frequentVisitorList;
    private List<HelperListVO> waitingVisitorList;
    private List<HelperListVO> insideHelperVisitorList;
    private List<HelperVO> helperListVOList;
    private Collection<String> dateList;
    private List<HelperListVO> detailList;
    private List<HelperDocumentListVO> activeHelperDocList;
    private List<HelperHistoryLogVO> monthlyCount;
    private List<HelperHistoryLogVO> weeklyCount;
    private List<HelperHistoryLogVO> yearlyCount;
    private ArrayList<HelperListVO> recentVisitor;

    private List<ComplainVO> complainMonthlyCount;
    private List<ComplainVO> complainWeeklyCount;
    private List<ComplainVO> complainYearlyCount;

    private List<AmenityVO> amenityMonthlyCount;
    private List<AmenityVO> amenityWeeklyCount;
    private List<AmenityVO> amenityYearlyCount;


    private List<GuardVO> activeGuardList;
    private List<GuardVO> inActiveGuardList;

    private List<CompanyMasterVO> activeCompany;
    private List<CompanyMasterVO> inActiveCompany;

    private List<HouseHoldVO> houseHold;

    private List<FrequentEntryVO> frequentEntry;

    private List<FamilyMemberVO> familyList;

    private ServiceTypeVO serviceType;
    private List<ServiceTypeVO> activeServiceType;
    private List<HelperTypeVO> helperTypeList1;
    private List<ServiceTypeVO> inActiveServiceType;

    private List<HelperListVO> activityList;
    private List<ActivityLogVO> activityLogList;

    private List<HelperDocumentListVO> MandatoryDocumentList;

    private GuardVO guard;

    private GuardHistoryVO guardHistory;
    private List<GuardHistoryVO> guardAttendance;

    private List<MobileProviderVO> provider;

    private List<FeatureCategoryVO> featureCategory;

    private List<SubCategoryVO> subCategory;

    private List<FeatureTypeVO> featureType;

    private List<UserDashboardVO> userDashboardList;
    private List<UserDashboardVO> inActiveDashboard;

    private List<ProductServiceCategoryVO> productCategoryList;
    private List<ProductServiceSubCategoryVO> productSubCategoryList;
    private List<ProductServiceMasterVO> productList;
    private List<UserServiceMasterVO> serviceList;
    private List<ProfessionMasterVO> userProfession;
//    private List<ProfessionMasterVO> profession;
    private Map<String, List<ProfessionMasterVO>> profession;
    private List<LanguageTypeVO> languageList;
    private LanguageTypeVO languageType;
    private List<MntMasterVO> maintenanceTypeList;
    private List<SocietyMntMappingVO> societyMntList;
    private List<SocietyMntFileInvoiceVO> invoiceList;

    private SocietyTemplateVO template;

    public ResponseVO(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseVO(HttpStatus status, String message,List<ErrorVO> errorVOList) {

        this.status = status;
        this.message = message;
        this.errorVOList =errorVOList;
    }

    public void setSuccessResponse(String message){
        this.status = status.OK;
        this.statusCode = status.value();
        this.message = message;
    }
    public void setFailResponse(String message){
        this.status = status.METHOD_NOT_ALLOWED;
        this.statusCode = status.value();
        this.message = message;
    }
    private SocietyVO societyVO;
    private List<SocietyNoticeVO> societyNoticeVO;
    private List<SocietyVO> societyVOList;
    private int complaiId;
    private List<ComplainVO> complainRecord;
    private List<AmenityChatRecordVO> amenityRecord;
    private List<AmenityTypeVO> activeAmenityTypeList ;
    private List<AmenityTypeVO> inActiveAmenityTypeList ;
    private List<AmenityListVO> amenityDetailsList ;
    private List<ComplainListVO> ComplainDetails;
    private List<NoticeTypeVO> noticeTypeVOs;
    private ComplainListVO complainList;
    private AmenityListVO amenityList;
    private HelperListVO visitorDetail;

    public boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    private List<CardMasterVO> cardList;
    private List<ProfessionUtilMasterVO> professionList;
    private List<HelperTypeVO>helperTypeVOs;

private List<HelperDocumentVO>helperDocVOs;
private GuardVO guardVOs;
    private List<OwnerMasterVO>ownerMasterList;
 private List<ParkingSlotEntity> parkingList;
  //  private List<GatewayVO> gatewayList;
}
