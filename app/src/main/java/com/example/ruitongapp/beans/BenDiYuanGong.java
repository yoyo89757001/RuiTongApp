package com.example.ruitongapp.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/10/16.
 */
@Entity
public class BenDiYuanGong {


    /**
     * accountId : 10000005
     * accountName : 市电
     * avatar :
     * createTime : 1504713243000
     * department :
     * description :
     * dtoResult : 0
     * email :
     * gender : 0
     * id : 10000065
     * job_number :
     * modifyTime : 1504713541000
     * name : 蒋总
     * pageNum : 0
     * pageSize : 0
     * phone :
     * photo_ids : 20170906/1504713243654.jpg,
     * remark :
     * sid : 0
     * status : 1
     * subject_type : 0
     * title :
     */
    private String entry_date2;
    private String birthday2;
    private int accountId;
    private String accountName;
    private String avatar;
    private long createTime;
    private String department;
    private String description;
    private int dtoResult;
    private String email;
    private int gender;
    @Id
    @NotNull
    private Long id;
    private String job_number;
    private long modifyTime;
    private String name;
    private int pageNum;
    private int pageSize;
    private String phone;
    private String photo_ids;
    private String remark;
    private int sid;
    private int status;
    private int subject_type;
    private String title;
    @Generated(hash = 2066562214)
    public BenDiYuanGong(String entry_date2, String birthday2, int accountId,
            String accountName, String avatar, long createTime, String department,
            String description, int dtoResult, String email, int gender,
            @NotNull Long id, String job_number, long modifyTime, String name,
            int pageNum, int pageSize, String phone, String photo_ids,
            String remark, int sid, int status, int subject_type, String title) {
        this.entry_date2 = entry_date2;
        this.birthday2 = birthday2;
        this.accountId = accountId;
        this.accountName = accountName;
        this.avatar = avatar;
        this.createTime = createTime;
        this.department = department;
        this.description = description;
        this.dtoResult = dtoResult;
        this.email = email;
        this.gender = gender;
        this.id = id;
        this.job_number = job_number;
        this.modifyTime = modifyTime;
        this.name = name;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.phone = phone;
        this.photo_ids = photo_ids;
        this.remark = remark;
        this.sid = sid;
        this.status = status;
        this.subject_type = subject_type;
        this.title = title;
    }
    @Generated(hash = 2050173351)
    public BenDiYuanGong() {
    }
    public String getEntry_date2() {
        return this.entry_date2;
    }
    public void setEntry_date2(String entry_date2) {
        this.entry_date2 = entry_date2;
    }
    public String getBirthday2() {
        return this.birthday2;
    }
    public void setBirthday2(String birthday2) {
        this.birthday2 = birthday2;
    }
    public int getAccountId() {
        return this.accountId;
    }
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
    public String getAccountName() {
        return this.accountName;
    }
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    public String getAvatar() {
        return this.avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public long getCreateTime() {
        return this.createTime;
    }
    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
    public String getDepartment() {
        return this.department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getDtoResult() {
        return this.dtoResult;
    }
    public void setDtoResult(int dtoResult) {
        this.dtoResult = dtoResult;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getGender() {
        return this.gender;
    }
    public void setGender(int gender) {
        this.gender = gender;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getJob_number() {
        return this.job_number;
    }
    public void setJob_number(String job_number) {
        this.job_number = job_number;
    }
    public long getModifyTime() {
        return this.modifyTime;
    }
    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getPageNum() {
        return this.pageNum;
    }
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
    public int getPageSize() {
        return this.pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPhoto_ids() {
        return this.photo_ids;
    }
    public void setPhoto_ids(String photo_ids) {
        this.photo_ids = photo_ids;
    }
    public String getRemark() {
        return this.remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public int getSid() {
        return this.sid;
    }
    public void setSid(int sid) {
        this.sid = sid;
    }
    public int getStatus() {
        return this.status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public int getSubject_type() {
        return this.subject_type;
    }
    public void setSubject_type(int subject_type) {
        this.subject_type = subject_type;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }


}
