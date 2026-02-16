package com.codewithmosh.store.dtos;



public class ChangePasswordRequest {

    private String oldPassword;
    private String newPassword;

    public ChangePasswordRequest() {}
    public ChangePasswordRequest(String oldPassword , String newPassword ){
        this.oldPassword = oldPassword ;
        this.newPassword = newPassword ;
    }

    public void setOldPassword(String oldPassword ){this.oldPassword = oldPassword ; }
    public void setNewPassword(String newPassword ){ this.newPassword = newPassword ;}


    public String getOldPassword(){ return this.oldPassword ; }
    public String getNewPassword(){return this.newPassword ; }   



}
