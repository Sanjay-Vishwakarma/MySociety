package com.pz.mysociety.common.constant;


public class SwaggerMessage {

    public static final String Add_Society_Message = "Mandatory Filed = [initiatedBy, registrationNumber,billingEmail, societyName, societyBlock, address, pincode, state, city, photo] & Optional = [landmark]";
    public static final String Update_Society_Message = "Mandatory Filed = [initiatedBy, id, registrationNumber, societyName, societyBlock, address, pincode, state, city, isActive] & Optional = [landmark]";
    public static final String Get_Society_Message = "Mandatory Filed = [initiatedBy, page] & Optional = [city, societyName, pincode]";
    public static final String Get_Specify_Society_Message = "Mandatory Filed = [initiatedBy,city, societyName, page]";
    public static final String Link_Society_Message = "Mandatory Filed = [initiatedBy, user{id} , society{id} OR [initiatedBy, user{id} ,society{societyName, societyBlock, address, pincode, state, city, photo} & Optional = {landmark}]";

    public static final String Add_Area_Message = "Mandatory Filed = [initiatedBy, societyId, areaTypeId, areaName] & Optional = [floor]";
    public static final String Get_Area_List_Message = "Mandatory Filed = [initiatedBy, societyId, page] & Optional = [areaType, areaName]";
    public static final String Update_Area_Message = "Mandatory Filed = [initiatedBy,id, areaName, areaTypeId, isActive] & Optional = [floor]";

    public static final String Get_Area_Search_List_Message = "Mandatory Filed = [initiatedBy, societyId, areaType, areaName, page]";

    public static final String Add_Unit_List_Message = "Mandatory Filed = [initiatedBy,societyId, areaId, areaTypeId, unit, isParking] Optional = [floor]";
    public static final String Get_Unit_List_Message = "Mandatory Filed = [initiatedBy,societyId, areaId, areaType, page]";
    public static final String Update_Unit_Message = "Mandatory Filed = [initiatedBy,id, unit, isParking, isActive, parkingSlot[ id ] ]";
    public static final String Add_Unit_Message = "Mandatory Filed = [initiatedBy, societyId, userId, areaId, unitId, type, occupancyStatus , documentList = {societyId, userId, documentType, document}]";

    public static final String Dashboard_Message = "Mandatory Filed = [initiatedBy, userId, unitId]";

    public static final String Get_Owner_List_Message = "Mandatory Filed = [initiatedBy, societyId, page] & Optional = [userStatus, areaId, unitId, type, occupancyStatus, societyRole]";
    public static final String Update_Owner_Status_Message = "Mandatory Filed = [initiatedBy, id, userStatus]";

    public static final String Application_Reject_Message = "Mandatory Filed = [initiatedBy, id, message]";

    public static final String Get_User_Document_Message = "Mandatory Filed = [initiatedBy, userId, unitId] or [initiatedBy, userId, societyId]";
    public static final String Update_User_Document_Message = "Mandatory Filed = [initiatedBy, id, document]";

    public static final String Add_Emergency_Number_Message = "Mandatory Filed = [initiatedBy, societyId, name, mobileNumber, type] & Optional = [address, email]";
    public static final String Get_Emergency_Number_Message = "Mandatory Filed = [initiatedBy, societyId ,(OPTIONAL:isActive)]";
    public static final String Update_Emergency_Number_Message = "Mandatory Filed = [initiatedBy, id, name, mobileNumber, type, isActive] & Optional = [address, email]";

    public static final String Add_Type_Message = "Mandatory Filed = [initiatedBy, type]";
    public static final String Get_Type_Message = "Mandatory Filed = [initiatedBy(SuperAdmin,Society,Guard,User), page] & Optional = [type, isResidential, isActive]";
    public static final String Update_Type_Message = "Mandatory Filed = [initiatedBy, id, type, isActive]";

    public static final String Add_Document_Type_Message = "Mandatory Filed = [initiatedBy, type, isMandatory]";
    public static final String Update_Document_Type_Message = "Mandatory Filed = [initiatedBy, id, type, isMandatory, isActive]";

    public static final String Add_Society_Document_Type_Message = "Mandatory Filed = [initiatedBy, documentTypeId, isMandatory]";
    public static final String Get_Society_Document_Type_Message = "Mandatory Filed = [initiatedBy, page] & Optional = [action, documentTypeId, isMandatory, isActive] For isMandatory(filter = mandatory) and isActive(action = status)";
    public static final String Update_Society_Document_Type_Message = "Mandatory Filed = [initiatedBy, id, documentTypeId, isMandatory, isActive]";

    public static final String Add_Document_Message = "Mandatory Filed = [initiatedBy, type]";
    public static final String Update_Document_Message = "Mandatory Filed = [initiatedBy, id, type, isActive]";

    public static final String Add_Society_Document_Message = "Mandatory Filed = [initiatedBy, societyId, societyUserId, documentTypeId, document]";
    public static final String Get_Society_Document_Message = "Mandatory Filed = [initiatedBy, id, societyId, societyRole]";

    public static final String Add_Unit_Document_Type_Message = "Mandatory Filed = [initiatedBy, societyId, societyUserId, residentType, documentTypeId, isMandatory]";
    public static final String Get_Unit_Document_Type_Message = "Mandatory Filed = [initiatedBy, societyId, residentType, page] OR [initiatedBy, societyId, page] ";
    public static final String Update_Unit_Document_Type_Message = "Mandatory Filed = [initiatedBy, id, residentType, documentTypeId, isMandatory, isActive]";

    public static final String Admin_SignUp_Message = "Mandatory Filed = [initiatedBy, email, password, role] & Optional = [mobileNumber]";
    public static final String Admin_SignIn_Message = "Mandatory Filed = [initiatedBy, email, password]";
    public static final String User_SignUp_Message = "Mandatory Filed = [initiatedBy, name, mobileNumber, role] & Optional = [email]";
    public static final String User_Login_Message = "Mandatory Filed = [initiatedBy, mobileNumber]";

    public static final String Verify_Otp_Message = "Mandatory Filed = [initiatedBy, id, validateOtp] OR [initiatedBy, id, validateOtp, deviceToken]";

    public static final String Resend_User_Otp_Message = "Mandatory Filed = [initiatedBy, id]";

    public static final String Change_Password_Message = "Mandatory Filed = [initiatedBy, id, password, newPassword]";

    public static final String Forget_Password_Message = "Mandatory Filed = [initiatedBy, email]";

    public static final String User_Update_Profile_Message = "Mandatory Filed = [initiatedBy, id, name, email]";
    public static final String Admin_Update_Profile_Message = "Mandatory Filed = [initiatedBy, id, name, mobileNumber]";

    public static final String Add_User_Profile_Message = "Mandatory Filed = [initiatedBy, id, photo]";
    public static final String Get_User_About_Message = "Mandatory Filed = [initiatedBy, id]";
    public static final String Update_User_About_Message = "Mandatory Filed = [initiatedBy, id] & Optional = [userProfession, userSpecialization, userInterest, userAbout, userWebsite, facebookProfile, twitterProfile, linkedInProfile, instagramProfile]";

    //Complain
    public static final String Add_Complain_Message = "Mandatory Filed = [initiatedBy, societyId, userId, unitId, complainTitle,complainDate, complainContent,isPriority ] & Optional = [attachment,isPriority]";
    public static final String Update_ComplainStatus_Message = "Mandatory Filed = [initiatedBy, complainId, complainStatus] ";
    public static final String Add_ComplainRecord_Message = "Mandatory Filed = [initiatedBy, complainId, role, content, userId or adminId] & Optional = [attachment]";
    public static final String Change_Complain_Flag_Message= "Mandatory Filed = [initiatedBy, complainId,role ]";
    public static final String Get_Complain_Record_Message= "Mandatory Filed = [initiatedBy, complainId ]";
    public static final String Get_ComplainList_Message= "Mandatory Filed = [initiatedBy, UserId, unitId,page ] OR [initiatedBy, societyId, page] OR [initiatedBy, SocietyId, complainStatus, page ] Optional= [complaintDate]";
    public static final String Get_ComplainDashBoard_Message= "Mandatory Filed = [initiatedBy, societyId,fromDate,complainStatus]";

    //Amenity
    public static final String Add_Amenity_Message = "Mandatory Filed = [initiatedBy- User,amenityTypeId societyId, userId, unitId, bookingDate, description, startDate, endDate,startTime, endTime , areaId] ";
    public static final String Update_AmenityStatus_Message = "Mandatory Filed = [initiatedBy - Society, amenityId, amenityStatus] ";
    public static final String Add_Amenity_Record_Message = "Mandatory Filed = [initiatedBy - Society or User, amenityId, adminId, role,content] OR [initiatedBy, amenityId, userId, role,content] & Optional = [attachment]  ";
    public static final String Get_Amenity_Record_Message = "Mandatory Filed = [initiatedBy - Society or User, amenityId] ";
    public static final String Change_AmenityFlag_Message = "Mandatory Filed = [initiatedBy - Society or User, amenityId,role] ";
    public static final String Get_AmenityList_Message = "Mandatory Filed = [initiatedBy - Society or User, userId, unitId, page]  OR [initiatedBy, societyId, page]  OR [initiatedBy, societyId, amenityStatus, page] OR[initiatedBy, societyId,areaId] ";
    public static final String Add_AmenityType_Message = "Mandatory Filed = [initiatedBy - Society, societyId, type]";
    public static final String Get_AmenityType_Message = "Mandatory Filed = [initiatedBy - Society, societyId, page]";
    public static final String Update_AmenityType_Message = "Mandatory Filed = [initiatedBy - Society, id,isActive, type]";
    public static final String Get_Amenity_DashBoard_Message= "Mandatory Filed = [initiatedBy, societyId,fromDate]";
    public static final String Get_Amenity_TimeLine_Message= "Mandatory Filed = [initiatedBy, societyId,fromDate]";

    //Notice
    public static final String Add_Notice_Message = "Mandatory Filed = [initiatedBy, societyId, noticeFor(Society, Unit), noticeTitle, noticeContent, noticeType,attachment, startDate, endDate ,unitId[]] ";
    public static final String Get_AllNotices_Message = "Mandatory Filed = [initiatedBy, societyId, page] ";
    public static final String Update_Notice_Message = "Mandatory Filed = [initiatedBy, id, societyId, noticeTitle, noticeContent, noticeType,attachment, startDate, endDate ] ";
    public static final String Add_NoticeType_Message = "Mandatory Filed = [initiatedBy,type] ";
    public static final String Update_NoticeType_Message = "Mandatory Filed = [initiatedBy, id, type] ";
    public static final String Delete_NoticeType_Message = "Mandatory Filed = [initiatedBy, id] ";


    public static final String Get_User_Detail_Message = "Mandatory Filed = [initiatedBy, userId] ";
    public static final String Get_User_Unit_Detail_Message = "Mandatory Filed = [initiatedBy, userId, unitId] ";


    public static final String Add_Guest_Message = "Mandatory Filed = [initiatedBy, unitId, name, mobileNumber, date, startTime, validFor] OR If isFrequent = true [initiatedBy, unitId, name, mobileNumber, startDate, endDate]";
    public static final String Add_Company_Message = "Mandatory Filed = [initiatedBy, name, companyTypeId, companyLogo]";
    public static final String Add_Family_Message = "Mandatory Filed = If type = adult [initiatedBy, unitId, parentId, name, mobileNumber, type] OR If type = kid [initiatedBy, unitId, parentId, name, monitor, type]";
    public static final String Update_Family_Message = "Mandatory Filed = If type = adult [initiatedBy, id, name, mobileNumber] OR If type = kid [initiatedBy, id, name, monitor]";
    public static final String Delete_Family_Message = "Mandatory Filed = [initiatedBy, id]";
    public static final String Add_Frequent_Entry_Message = "Mandatory Filed = [initiatedBy, unitId, date, startTime, validFor, type] OR If isFrequent = true [initiatedBy, unitId, isFrequent, days, validDays, startTime, validFor, type] & Optional = [vehicleNumber, companyId]";
    public static final String Add_Review_Message = "Mandatory Filed = [initiatedBy, id, rating, review]";
    public static final String Add_Vehicle_Message = "Mandatory Filed = [initiatedBy, unitId, name, vehicleNumber, type]";
    public static final String Get_Company_Message = "Mandatory Filed = [initiatedBy, companyTypeId, page]";
    public static final String Get_Helper_Detail_Message = "Mandatory Filed = [initiatedBy, societyId, helperId]";
    public static final String Get_Household_Detail_Message = "Mandatory Filed = [initiatedBy, unitId, householdType]";
    public static final String Get_Review_Message = "Mandatory Filed = [initiatedBy, unitId, helperId]";
    public static final String Link_Helper_Message = "Mandatory Filed = [initiatedBy, societyId, unitId, helperId]";
    public static final String UnLink_Helper_Message = "Mandatory Filed = [initiatedBy, id, remark]";
    public static final String Update_Company_Message = "Mandatory Filed = [initiatedBy, id, name, companyTypeId, companyLogo, isActive]";
    public static final String Update_Vehicle_Message = "Mandatory Filed = [initiatedBy, id, name, vehicleNumber, type, isActive]";

    public static final String Get_NoticeType_Message = "Mandatory Filed = [initiatedBy,page] & Optional = [type]";
    public static final String AddHelper_Message = "Mandatory Filed = [initiatedBy(Society,Guard) : id, societyId, unitId,areaId,helperMobile,date,inTime,freeTimeSlot, helperName, addedBy, type,photo,helperTypeId, documentList[document,documentType,documentTypeId,societyId,documentNumber,isActive]] ";
    public static final String GetHelper_Message = "Mandatory Filed = [initiatedBy(Society,User), SocietyId & Optional= [helperTypeId, helperName,helperMobile,isActive(action=status)]] ";
    public static final String GetHelper_Type_Message = "Mandatory Filed = [initiatedBy(Society,User), SocietyId,type] ";
    public static final String AddHelper_Type_Message = "Mandatory Filed = [initiatedBy(SuperAdmin), serviceTypeId, type, isFullTime,iconImage] ";
    public static final String Helper_Type_Message = "Mandatory Filed = [initiatedBy(SuperAdmin,Society,Guard,User)]  & Optional = [ serviceTypeId,type,action]";
    public static final String updateHelper_Type_Message = "Mandatory Filed = [initiatedBy(SuperAdmin),id,isFullTime, isActive] & Optional = [type,iconImage] ";
    public static final String updateHelper_Message = "Mandatory Filed = [initiatedBy(Society,User),id] & Optional = [helperName,photo,helperMobile,isActive]";
    public static final String Add_Visitor_Message = "Mandatory Filed = [initiatedBy(Guard),societyId, unitIdList,visitorType, phoneNumber, name, bodyTemperature,wearingMask,companyLogo,date,inTime,companyName,companyId] & Optonal =[companyName, vehicle_number, photo]";
    public static final String Add_Visitor_HistoryLog_Message = "Mandatory Filed For In= [initiatedBy(Guard),action,societyId,passCode,unitIdList[],vehicleNumber,bodyTemperature,wearingMask,helperTypeId,companyName,helperId] & Mandatory Filed For Out= [initiatedBy, visitorLogId, action, outTime] ";
    public static final String GetFrequentVisitor = "Mandatory Filed For In= [initiatedBy(Guard),societyId, page, & Optional = [helperTypeId,helperMobile,helperName,loginStatus,companyName,type(helper,visitor), isActive (action = status)]] ";
    public static final String GetVisitor = "Mandatory Filed For In= [initiatedBy(Society),societyId, page, & Optional = [helperTypeId,helperMobile,helperName,loginStatus,companyName,type(helper,visitor), isActive (action = status)]] ";
    public static final String Get_Recent_Visitor = "Mandatory Filed For In= [initiatedBy(Society),societyId] & Optional = [areaId] ";
    public static final String Add_Helper_LogHistory = "Mandatory Filed For In= [initiatedBy(Guard),helperId,societyId,action,date,inTime,bodyTemperature,vehicleNumber,isWearingMask] & Mandatory Filed For Out= [initiatedBy, loginId ,helperId, action, outTime]";
    public static final String Helper_Document="Mandatory Filed =[initiatedBy:Society,societyId,helperId]";

    public static final String Add_Service_Type_Message = "Mandatory Filed = [initiatedBy(SuperAdmin), type, isSocietyUser, isUser, isGuard,iconImage,isAttendance]";
    public static final String Update_Service_Type_Message = "Mandatory Filed = [initiatedBy(SuperAdmin), id, type, isSocietyUser, isUser, isGuard, isActive, iconImage]";

    public static final String Update_Society_User_Status = "Mandatory Filed = [initiatedBy, id, adminStatus] & message if rejected";
    public static final String Get_Society_User_List = "Mandatory Filed = [initiatedBy, adminStatus, page] & Optional = [societyName, city, pincode, name, mobileNumber]";
    public static final String Get_Society_Admin_Message = "Mandatory Filed = [initiatedBy, id]";

    public static final String Add_Activity_Message = "Mandatory Filed = [initiatedBy, societyId, unitId, name, date, inTime, allowedBy, activityType, companyId] & Optional = [photo, outTime]";
    public static final String Recent_Activity_Message = "Mandatory Filed = [initiatedBy(User), unitId, page]";

    public static final String All_Member_Message = "Mandatory Filed = [initiatedBy, areaId, page]";
    public static final String Get_Member_Message = "Mandatory Filed = [initiatedBy, societyId]";
    public static final String Update_Society_Document_Status_Message = "Mandatory Filed = [initiatedBy, id, documentStatus]";
    public static final String Update_Unit_Document_Status_Message = "Mandatory Filed = [initiatedBy, id, status] Optional = [remark]";
    public static final String Add_Helper_PhoneNumber = "Mandatory Filed = [initiatedBy(Guard), societyId, phoneNumber]";


    public static final String Add_Guard_Message = "Mandatory Filed = [initiatedBy, societyId, helperTypeId, name, mobileNumber, role, photo]";
    public static final String Get_Guard_Attendance_Message = "Mandatory Filed = [initiatedBy, guardId, month, year]";
    public static final String Update_Guard_Message = "Mandatory Filed = [initiatedBy, id, name, mobileNumber, photo, isActive]";
    public static final String Guard_Login_Message = "Mandatory Filed = [initiatedBy, societyId, guardPin] OR [initiatedBy, societyId, mobileNumber]";
    public static final String Guard_Logout_Message = "Mandatory Filed = [initiatedBy, id]";

    public static final String Get_Provider_Message = "Mandatory Filed = [initiatedBy]";
    public static final String Change_Provider_Message = "Mandatory Filed = [initiatedBy, providerName]";

    public static final String Update_SocietyMember_Role= "Mandatory Filed = [initiatedBy, id,societyRole]";

    public static final String Get_Guard_Message = "Mandatory Filed = [initiatedBy(Society), societyId] Optional = [name, mobileNumber, email, guardPin, isActive(action=status)]";
    public static final String Get_InsideHelper_Message = "Mandatory Filed = [initiatedBy(Guard), societyId]";
    public static final String Get_ByPassCode_Message = "Mandatory Filed = [initiatedBy(Guard), passCode,societyId]";
    public static final String Get_Attendance= "Mandatory Filed = [initiatedBy(Society,User), societyId, unitId, helperId, year, month]";
    public static final String Get_WaitingList= "Mandatory Filed = [initiatedBy(Guard), societyId]";
    public static final String Get_Visitor_Type= "Mandatory Filed = [initiatedBy(Guard)]";
    public static final String Get_UnitDetails= "Mandatory Filed = [initiatedBy(Guard),societyId,loginId]";
    public static final String Get_InOutHistory= " Filed = [initiatedBy(Society,Guard),societyId,areaId,date] & Optional = [unitId]";

    public static final String Add_Helper_Document= " Filed = [initiatedBy(Society),societyId,serviceTypeId,helperTypeId,documentTypeId] ";
    public static final String Update_Helper_Document= " Filed = [initiatedBy(Society),id,societyId,serviceTypeId,helperTypeId,documentTypeId] ";
    public static final String Get_Helper_Document= " Filed = [initiatedBy(Society),societyId]";
    public static final String Get_Helper_Document_HelperTypeId= " Filed = [initiatedBy(Society,Guard),societyId,helperTypeId]";
    public static final String GetVisitorDashBoard= " Filed = [initiatedBy(Society),societyId,fromDate]";

    //Dashboard
    public static final String Add_Feature_Category = "Mandatory Filed = [initiatedBy, name, redirect]";
    public static final String Get_Feature_Category_List = "Mandatory Filed = [initiatedBy] & Optional = [name, redirect, isActive (action = status)]";
    public static final String Update_Feature_Category = "Mandatory Filed = [initiatedBy, name, redirect, isActive]";
    public static final String Add_Sub_Category = "Mandatory Filed = [initiatedBy, featureId, name, redirect]";
    public static final String Get_Sub_Category_List = "Mandatory Filed = [initiatedBy] & Optional = [featureId, name, redirect, isActive (action = status)]";
    public static final String Update_Sub_Category = "Mandatory Filed = [initiatedBy, featureId, name, redirect, isActive]";
    public static final String Add_Feature_Type = "Mandatory Filed = [initiatedBy, featureId, subTypeId, name, description, iconUrl]  & Optional = [isFavorite, isNew]";
    public static final String Get_Feature_Type_List = "Mandatory Filed = [initiatedBy] & Optional = [featureId, subCategoryId, name, redirect, isActive (action = status)]";
    public static final String Update_Feature_Type = "Mandatory Filed = [initiatedBy, featureId, subTypeId, name, description, iconUrl, isFavorite, isNew, isActive]";
    public static final String Add_Dashboard_Label = "ADD ACTION Mandatory Filed = [initiatedBy, labelId, labelType, cardName, cardSize, action, priorityOrder] & ON UPDATE ACTION Mandatory Filed = [initiatedBy, id, labelId, labelType, cardName, cardSize, action, priorityOrder, isActive] & Optional = [isPriority]";
    public static final String Get_Dashboard_List = "Mandatory Filed = [initiatedBy] & Optional = [labelId, labelType, cardName, cardSize, priorityOrder, isActive (action = status)] ";
    public static final String Get_Dashboard_Feature_List = "Mandatory Filed = [initiatedBy, name]";
    public static final String Get_User_Dashboard_List = "Mandatory Filed = [initiatedBy]";
    public static final String Add_Dashboard_Feature_Type_ = "Mandatory Filed = [initiatedBy, id, dashboardId, priorityOrder, action]";


    //Product and Service

    public static final String Add_Product_Category = "For Add Mandatory Filed = [initiatedBy, name, type, iconUrl, action] OR For Update Mandatory Filed = [initiatedBy, id, action, isActive] Optional =[name, type, iconUrl]";
    public static final String Get_Product_Category_List = "Mandatory Filed = [initiatedBy] & Optional = [name, type, action, isActive]";
    public static final String Add_Product_Sub_Category = "For Add Mandatory Filed = [initiatedBy, productCategoryId, name, type, iconUrl, action] OR For Update Mandatory Filed = [initiatedBy, id, action, isActive] Optional =[productCategoryId, name, type, iconUrl]";
    public static final String Get_Product_Sub_Category_List = "Mandatory Filed = [initiatedBy] & Optional = [productCategoryId, name, type, action, isActive]";
    public static final String Add_Product_Master = "For Add Mandatory Filed = [initiatedBy, action, societyId, unitId, userId, productCategoryId, productSubCategoryId, title, description, purchaseYear, type, mobileNumber, address, isNegotiable, isCall, isUnitVisible] & Optional = [price, originalPrice] OR For Update Mandatory Filed = [initiatedBy, action, id, isActive, isNegotiable, isCall, isUnitVisible] & Optional = [title, description, purchaseYear, type, mobileNumber, address, price, originalPrice]";
    public static final String Get_Product_Master = "For Add Mandatory Filed = [initiatedBy] & Optional = [societyId, unitId, productCategoryId, productSubCategoryId, isActive]";
    public static final String Add_Product_Master_Image = "For Add Mandatory Filed = [initiatedBy, productId, type, imageUrl]";
    public static final String Delete_Product_Master_Image = "For Add Mandatory Filed = [initiatedBy, id]";
    public static final String Get_Near_By_Product_Master = "For Add Mandatory Filed = [initiatedBy, pincode]";

    public static final String Add_Service_Master = "For Add Mandatory Filed = [initiatedBy, action, societyId, unitId, userId, serviceCategoryId, serviceSubCategoryId, title, description, mobileNumber, servicePrice, serviceType, serviceDay, serviceProvide, serviceRange, if(serviceProvide= in person) serviceLocation, isCall, isUnitVisible, productImage] & Optional = [whatsappUrl, serviceUrl] || OR  || For Update Mandatory Filed = [initiatedBy, action, id, isActive, isCall, isUnitVisible] & Optional = [title, description, mobileNumber, servicePrice, serviceType, serviceDay, serviceProvide, serviceRange, if(serviceProvide= in person) serviceLocation, whatsappUrl, serviceUrl]";
    public static final String Get_Service_Master = "For Add Mandatory Filed = [initiatedBy] & Optional = [societyId, unitId, serviceCategoryId, serviceSubCategoryId, isActive]";


    //Profession
    public static final String Add_User_Profession = "For Add Mandatory Filed = [initiatedBy, societyId, userId, unitId, name, profession, mobileNumber, action] OR For Update Mandatory Filed = [initiatedBy, id, action] Optional =[name, profession, mobileNumber]";
    public static final String Get_User_Profession_List = "Mandatory Filed = [initiatedBy, societyId, userId, unitId]";


    //mastercard
    public static final String Add_card_Master ="For Add Mandatory Filed = [initiatedBy,cardName,action] OR for Update Mandatory Filed =[InitiatedBy,id,cardName,Action,IsActive]";
    public static final String Get_card_Master ="For Get Mandatory Filed =[initiatedBy,cardName,id]";



    //Profession master

    public static final String Add_profession_Master ="For Add Mandatory Filed = [initiatedBy,Profession,action] OR for Update Mandatory Filed =[InitiatedBy,id,Profession,Action,IsActive]";
    public static final String Get_profession_Master ="For Get Mandatory Filed =[initiatedBy,Profession,id]";
    //Language
    public static final String Add_Language ="For Add Mandatory Filed = [initiatedBy,action, serviceId, type, serviceName] OR for Update Mandatory Filed =[initiatedBy,id, action] Optional = [type, hin, mar]";
    public static final String Get_Language_List ="Mandatory Filed = [initiatedBy]";

    //Maintenance
    public static final String Add_Mnt_Type = "For Add Mandatory Filed = [initiatedBy,action,name] & Optional = [isFee] OR for Update Mandatory Filed =[initiatedBy,id, action, isActive, isFee] & Optional = [name]";
    public static final String Get_Mnt_Type_List ="Mandatory Filed = [initiatedBy]";
    public static final String Add_Society_Mnt_Type = "For Add Mandatory Filed = [initiatedBy,action, societyId, mntTypeId,name, columnType(master,fee), columnNumber, calculationLogic, totalAmount] & Optional = [isMonthly] OR for Update Mandatory Filed =[initiatedBy,id, action, isMonthly] & Optional = [mntTypeId,name, columnNumber, calculationLogic, totalAmount]";
    public static final String Get_Society_Mnt_Type_List ="Mandatory Filed = [initiatedBy, societyId, columnType(master,fee)]";
    public static final String Add_Mnt_Invoice ="Mandatory Filed = [initiatedBy, societyId, type, file]";
    public static final String Get_Mnt_Invoice ="Mandatory Filed = [initiatedBy, societyId] & Optional = [area, unit,month,year,invoiceStatus]";
    public static final String Update_Invoice_Status ="Mandatory Filed = [initiatedBy, invoiceId]";
    public static final String Delete_Invoice ="Mandatory Filed = [initiatedBy, invoiceId]";
    public static final String Add_Invoice ="For Add Mandatory Filed = [initiatedBy, societyId, unitId, invoice, name, mobileNumber, month, year, area, unit] && For Update Mandatory Filed = [initiatedBy, societyId, id]";
    public static final String Del_Mnt_Type="Mandatory Filed=[initiatedBy,id]";

    //SocietyReview

    public static final String Society_Review ="Mandatory Filed=[initiatedBy,ownerId,societyId,action= Update/ADD]& Optional=[commute,clean,safe,noise,amenityQuality,eventFull,construction,rating,prominent,friction,nameDisplay,description]";

    //ParkingSlotMaster

    public static final String Parking_Slot = "Mandatory Filed =[initiatedBy,areaId,unitId,societyId,name] Optional[isVisitor,isOccupied,isActive]  Upadate=[Mandatory Filed =Id] Optional[name,isOccupied,isActive]";
    public static final String Get_Parking_Slot= "Mandatory Filed=[initiatedBy,SocietyId] Optional [isOccuppied,unitId,isVisitor,isActive]";

    public static final String Add_Society_Template = "Mandatory Filed =[initiatedBy, societyId, templateType] OR For Update Mandatory Filed =[initiatedBy,id]";
    public static final String Get_Society_Template = "Mandatory Filed =[initiatedBy, societyId] Optional[isOccupied,isVisitor,UnitId,AreaId,name]";


}
