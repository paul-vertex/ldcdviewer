/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.GenderType;
import de.sbe.ldc.domain.rpc.Impersonator;
import de.sbe.ldc.domain.rpc.RpcObject;
import de.sbe.ldc.persistence.morpher.RpcScope;
import de.sbe.ldc.persistence.morpher.SerializableProperty;

public class Contact
extends RpcObject
implements Impersonator {
    public static final String PROPERTYNAME_BUSINESS_CELL_PHONE = "businessCellPhone";
    public static final String PROPERTYNAME_BUSINESS_CITY = "businessCity";
    public static final String PROPERTYNAME_BUSINESS_COUNTRY = "businessCountry";
    public static final String PROPERTYNAME_BUSINESS_DEPARTMENT = "businessDepartment";
    public static final String PROPERTYNAME_BUSINESS_EMAIL = "businessEmail";
    public static final String PROPERTYNAME_BUSINESS_FAX = "businessFax";
    public static final String PROPERTYNAME_BUSINESS_ORGANIZATION = "businessOrganization";
    public static final String PROPERTYNAME_BUSINESS_PHONE = "businessPhone";
    public static final String PROPERTYNAME_BUSINESS_POSTAL_CODE = "businessPostalCode";
    public static final String PROPERTYNAME_BUSINESS_STATE = "businessState";
    public static final String PROPERTYNAME_BUSINESS_STREET_NAME = "businessStreetName";
    public static final String PROPERTYNAME_BUSINESS_STREET_NUMBER = "businessStreetNumber";
    public static final String PROPERTYNAME_BUSINESS_WEBSITE = "businessWebsite";
    public static final String PROPERTYNAME_COMMON_DISPLAY_NAME = "commonDisplayName";
    public static final String PROPERTYNAME_COMMON_GENDER = "commonGender";
    public static final String PROPERTYNAME_COMMON_GIVEN_NAME = "commonGivenName";
    public static final String PROPERTYNAME_COMMON_PHOTO = "commonPhoto";
    public static final String PROPERTYNAME_COMMON_SURNAME = "commonSurname";
    public static final String PROPERTYNAME_COMMON_TITLE = "commonTitle";
    public static final String PROPERTYNAME_PRIVATE_CELL_PHONE = "privateCellPhone";
    public static final String PROPERTYNAME_PRIVATE_CITY = "privateCity";
    public static final String PROPERTYNAME_PRIVATE_COUNTRY = "privateCountry";
    public static final String PROPERTYNAME_PRIVATE_EMAIL = "privateEmail";
    public static final String PROPERTYNAME_PRIVATE_FAX = "privateFax";
    public static final String PROPERTYNAME_PRIVATE_PHONE = "privatePhone";
    public static final String PROPERTYNAME_PRIVATE_POSTAL_CODE = "privatePostalCode";
    public static final String PROPERTYNAME_PRIVATE_STATE = "privateState";
    public static final String PROPERTYNAME_PRIVATE_STREET_NAME = "privateStreetName";
    public static final String PROPERTYNAME_PRIVATE_STREET_NUMBER = "privateStreetNumber";
    public static final String PROPERTYNAME_PRIVATE_WEBSITE = "privateWebsite";
    public static final String SCOPE_BUSINESS = "business";
    public static final String SCOPE_COMMON = "common";
    public static final String SCOPE_PRIVATE = "private";
    private static final long serialVersionUID = 1L;
    @RpcScope(scope="business")
    @SerializableProperty
    private String businessCellPhone;
    @RpcScope(scope="business")
    @SerializableProperty
    private String businessCity;
    @RpcScope(scope="business")
    @SerializableProperty
    private String businessCountry;
    @RpcScope(scope="business")
    @SerializableProperty
    private String businessDepartment;
    @RpcScope(scope="business")
    @SerializableProperty
    private String businessEmail;
    @RpcScope(scope="business")
    @SerializableProperty
    private String businessFax;
    @RpcScope(scope="business")
    @SerializableProperty
    private String businessOrganization;
    @RpcScope(scope="business")
    @SerializableProperty
    private String businessPhone;
    @RpcScope(scope="business")
    @SerializableProperty
    private String businessPostalCode;
    @RpcScope(scope="business")
    @SerializableProperty
    private String businessState;
    @RpcScope(scope="business")
    @SerializableProperty
    private String businessStreetName;
    @RpcScope(scope="business")
    @SerializableProperty
    private String businessStreetNumber;
    @RpcScope(scope="business")
    @SerializableProperty
    private String businessWebsite;
    @RpcScope(scope="common")
    @SerializableProperty
    private String commonDisplayName;
    @RpcScope(scope="common")
    @SerializableProperty
    private GenderType commonGender;
    @RpcScope(scope="common")
    @SerializableProperty
    private String commonGivenName;
    @RpcScope(scope="common")
    @SerializableProperty
    private String commonPhoto;
    @RpcScope(scope="common")
    @SerializableProperty
    private String commonSurname;
    @RpcScope(scope="common")
    @SerializableProperty
    private String commonTitle;
    @RpcScope(scope="private")
    @SerializableProperty
    private String privateCellPhone;
    @RpcScope(scope="private")
    @SerializableProperty
    private String privateCity;
    @RpcScope(scope="private")
    @SerializableProperty
    private String privateCountry;
    @RpcScope(scope="private")
    @SerializableProperty
    private String privateEmail;
    @RpcScope(scope="private")
    @SerializableProperty
    private String privateFax;
    @RpcScope(scope="private")
    @SerializableProperty
    private String privatePhone;
    @RpcScope(scope="private")
    @SerializableProperty
    private String privatePostalCode;
    @RpcScope(scope="private")
    @SerializableProperty
    private String privateState;
    @RpcScope(scope="private")
    @SerializableProperty
    private String privateStreetName;
    @RpcScope(scope="private")
    @SerializableProperty
    private String privateStreetNumber;
    @RpcScope(scope="private")
    @SerializableProperty
    private String privateWebsite;

    public String getBusinessCellPhone() {
        return this.businessCellPhone;
    }

    public String getBusinessCity() {
        return this.businessCity;
    }

    public String getBusinessCountry() {
        return this.businessCountry;
    }

    public String getBusinessDepartment() {
        return this.businessDepartment;
    }

    public String getBusinessEmail() {
        return this.businessEmail;
    }

    public String getBusinessFax() {
        return this.businessFax;
    }

    public String getBusinessOrganization() {
        return this.businessOrganization;
    }

    public String getBusinessPhone() {
        return this.businessPhone;
    }

    public String getBusinessPostalCode() {
        return this.businessPostalCode;
    }

    public String getBusinessState() {
        return this.businessState;
    }

    public String getBusinessStreetName() {
        return this.businessStreetName;
    }

    public String getBusinessStreetNumber() {
        return this.businessStreetNumber;
    }

    public String getBusinessWebsite() {
        return this.businessWebsite;
    }

    public String getCommonDisplayName() {
        return this.commonDisplayName;
    }

    public GenderType getCommonGender() {
        return this.commonGender;
    }

    public String getCommonGivenName() {
        return this.commonGivenName;
    }

    public String getCommonPhoto() {
        return this.commonPhoto;
    }

    public String getCommonSurname() {
        return this.commonSurname;
    }

    public String getCommonTitle() {
        return this.commonTitle;
    }

    @Override
    public String getDisplayName() {
        return this.getCommonDisplayName();
    }

    public String getPrivateCellPhone() {
        return this.privateCellPhone;
    }

    public String getPrivateCity() {
        return this.privateCity;
    }

    public String getPrivateCountry() {
        return this.privateCountry;
    }

    public String getPrivateEmail() {
        return this.privateEmail;
    }

    public String getPrivateFax() {
        return this.privateFax;
    }

    public String getPrivatePhone() {
        return this.privatePhone;
    }

    public String getPrivatePostalCode() {
        return this.privatePostalCode;
    }

    public String getPrivateState() {
        return this.privateState;
    }

    public String getPrivateStreetName() {
        return this.privateStreetName;
    }

    public String getPrivateStreetNumber() {
        return this.privateStreetNumber;
    }

    public String getPrivateWebsite() {
        return this.privateWebsite;
    }

    public void setBusinessCellPhone(String _businessCellPhone) {
        this.businessCellPhone = _businessCellPhone;
        this.firePropertyChange(PROPERTYNAME_BUSINESS_CELL_PHONE, (Object)this.businessCellPhone, (Object)this.businessCellPhone);
    }

    public void setBusinessCity(String _businessCity) {
        this.businessCity = _businessCity;
        this.firePropertyChange(PROPERTYNAME_BUSINESS_CITY, (Object)this.businessCity, (Object)this.businessCity);
    }

    public void setBusinessCountry(String _businessCountry) {
        this.businessCountry = _businessCountry;
        this.firePropertyChange(PROPERTYNAME_BUSINESS_COUNTRY, (Object)this.businessCountry, (Object)this.businessCountry);
    }

    public void setBusinessDepartment(String _businessDepartment) {
        this.businessDepartment = _businessDepartment;
        this.firePropertyChange(PROPERTYNAME_BUSINESS_DEPARTMENT, (Object)this.businessDepartment, (Object)this.businessDepartment);
    }

    public void setBusinessEmail(String _businessEmail) {
        this.businessEmail = _businessEmail;
        this.firePropertyChange(PROPERTYNAME_BUSINESS_EMAIL, (Object)this.businessEmail, (Object)this.businessEmail);
    }

    public void setBusinessFax(String _businessFax) {
        this.businessFax = _businessFax;
        this.firePropertyChange(PROPERTYNAME_BUSINESS_FAX, (Object)this.businessFax, (Object)this.businessFax);
    }

    public void setBusinessOrganization(String _businessOrganization) {
        this.businessOrganization = _businessOrganization;
        this.firePropertyChange(PROPERTYNAME_BUSINESS_ORGANIZATION, (Object)this.businessOrganization, (Object)this.businessOrganization);
    }

    public void setBusinessPhone(String _businessPhone) {
        this.businessPhone = _businessPhone;
        this.firePropertyChange(PROPERTYNAME_BUSINESS_PHONE, (Object)this.businessPhone, (Object)this.businessPhone);
    }

    public void setBusinessPostalCode(String _businessPostalCode) {
        this.businessPostalCode = _businessPostalCode;
        this.firePropertyChange(PROPERTYNAME_BUSINESS_POSTAL_CODE, (Object)this.businessPostalCode, (Object)this.businessPostalCode);
    }

    public void setBusinessState(String _businessState) {
        this.businessState = _businessState;
        this.firePropertyChange(PROPERTYNAME_BUSINESS_STATE, (Object)this.businessState, (Object)this.businessState);
    }

    public void setBusinessStreetName(String _businessStreetName) {
        this.businessStreetName = _businessStreetName;
        this.firePropertyChange(PROPERTYNAME_BUSINESS_STREET_NAME, (Object)this.businessStreetName, (Object)this.businessStreetName);
    }

    public void setBusinessStreetNumber(String _businessStreetNumber) {
        this.businessStreetNumber = _businessStreetNumber;
        this.firePropertyChange(PROPERTYNAME_BUSINESS_STREET_NUMBER, (Object)this.businessStreetNumber, (Object)this.businessStreetNumber);
    }

    public void setBusinessWebsite(String _businessWebsite) {
        this.businessWebsite = _businessWebsite;
        this.firePropertyChange(PROPERTYNAME_BUSINESS_WEBSITE, (Object)this.businessWebsite, (Object)this.businessWebsite);
    }

    public void setCommonDisplayName(String _commonDisplayName) {
        this.commonDisplayName = _commonDisplayName;
        this.firePropertyChange(PROPERTYNAME_COMMON_DISPLAY_NAME, (Object)this.commonDisplayName, (Object)this.commonDisplayName);
    }

    public void setCommonGender(GenderType _commonGender) {
        this.commonGender = _commonGender;
        this.firePropertyChange(PROPERTYNAME_COMMON_GENDER, (Object)this.commonGender, (Object)this.commonGender);
    }

    public void setCommonGivenName(String _commonGivenName) {
        this.commonGivenName = _commonGivenName;
        this.firePropertyChange(PROPERTYNAME_COMMON_GIVEN_NAME, (Object)this.commonGivenName, (Object)this.commonGivenName);
    }

    public void setCommonPhoto(String _commonPhoto) {
        this.commonPhoto = _commonPhoto;
        this.firePropertyChange(PROPERTYNAME_COMMON_PHOTO, (Object)this.commonPhoto, (Object)this.commonPhoto);
    }

    public void setCommonSurname(String _commonSurname) {
        this.commonSurname = _commonSurname;
        this.firePropertyChange(PROPERTYNAME_COMMON_SURNAME, (Object)this.commonSurname, (Object)this.commonSurname);
    }

    public void setCommonTitle(String _commonTitle) {
        this.commonTitle = _commonTitle;
        this.firePropertyChange(PROPERTYNAME_COMMON_TITLE, (Object)this.commonTitle, (Object)this.commonTitle);
    }

    public void setPrivateCellPhone(String _privateCellPhone) {
        this.privateCellPhone = _privateCellPhone;
        this.firePropertyChange(PROPERTYNAME_PRIVATE_CELL_PHONE, (Object)this.privateCellPhone, (Object)this.privateCellPhone);
    }

    public void setPrivateCity(String _privateCity) {
        this.privateCity = _privateCity;
        this.firePropertyChange(PROPERTYNAME_PRIVATE_CITY, (Object)this.privateCity, (Object)this.privateCity);
    }

    public void setPrivateCountry(String _privateCountry) {
        this.privateCountry = _privateCountry;
        this.firePropertyChange(PROPERTYNAME_PRIVATE_COUNTRY, (Object)this.privateCountry, (Object)this.privateCountry);
    }

    public void setPrivateEmail(String _privateEmail) {
        this.privateEmail = _privateEmail;
        this.firePropertyChange(PROPERTYNAME_PRIVATE_EMAIL, (Object)this.privateEmail, (Object)this.privateEmail);
    }

    public void setPrivateFax(String _privateFax) {
        this.privateFax = _privateFax;
        this.firePropertyChange(PROPERTYNAME_PRIVATE_FAX, (Object)this.privateFax, (Object)this.privateFax);
    }

    public void setPrivatePhone(String _privatePhone) {
        this.privatePhone = _privatePhone;
        this.firePropertyChange(PROPERTYNAME_PRIVATE_PHONE, (Object)this.privatePhone, (Object)this.privatePhone);
    }

    public void setPrivatePostalCode(String _privatePostalCode) {
        this.privatePostalCode = _privatePostalCode;
        this.firePropertyChange(PROPERTYNAME_PRIVATE_POSTAL_CODE, (Object)this.privatePostalCode, (Object)this.privatePostalCode);
    }

    public void setPrivateState(String _privateState) {
        this.privateState = _privateState;
        this.firePropertyChange(PROPERTYNAME_PRIVATE_STATE, (Object)this.privateState, (Object)this.privateState);
    }

    public void setPrivateStreetName(String _privateStreetName) {
        this.privateStreetName = _privateStreetName;
        this.firePropertyChange(PROPERTYNAME_PRIVATE_STREET_NAME, (Object)this.privateStreetName, (Object)this.privateStreetName);
    }

    public void setPrivateStreetNumber(String _privateStreetNumber) {
        this.privateStreetNumber = _privateStreetNumber;
        this.firePropertyChange(PROPERTYNAME_PRIVATE_STREET_NUMBER, (Object)this.privateStreetNumber, (Object)this.privateStreetNumber);
    }

    public void setPrivateWebsite(String _privateWebsite) {
        this.privateWebsite = _privateWebsite;
        this.firePropertyChange(PROPERTYNAME_PRIVATE_WEBSITE, (Object)this.privateWebsite, (Object)this.privateWebsite);
    }
}

