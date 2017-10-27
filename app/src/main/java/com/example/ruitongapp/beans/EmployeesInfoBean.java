package com.example.ruitongapp.beans;

/**
 * Created by chenjun on 2017/6/1.
 */

public class EmployeesInfoBean {


    /**
     * avatar : 20170531/1496211779601.jpg
     * createTime : 1496200897000
     * department : 技术部
     * description : 梦想，3
     * dtoResult : 0
     * email : d56@gmail.com
     * gender : 0
     * id : 10000317
     * job_number : 123
     * modifyTime : 1496211583000
     * name : 王三
     * pageNum : 0
     * pageSize : 0
     * phone : 13620983749
     * photo_ids :
     * remark : 我是前端
     * sid : 0
     * status : 1
     * subject_type : 0
     * title : CEO
     */

    private String avatar;
    private long createTime;
    private String department;
    private String description;
    private int dtoResult;
    private String email;
    private int gender;
    private int id;
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
    private String entry_date;
    private String birthday;

    public String getEntry_date() {
        if (null==entry_date){
            return "";
        }else {
            return entry_date;
        }

    }

    public void setEntry_date(String entry_date) {
        this.entry_date = entry_date;
    }

    public String getBirthday() {
        if (null==birthday){
            return "";
        }else {
            return birthday;
        }

    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAvatar() {
        if (null==avatar){
            return "";
        }else {
            return avatar;
        }

    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDtoResult() {
        return dtoResult;
    }

    public void setDtoResult(int dtoResult) {
        this.dtoResult = dtoResult;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJob_number() {
        return job_number;
    }

    public void setJob_number(String job_number) {
        this.job_number = job_number;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getPhone() {
        if (null==phone){
            return "";
        }else {
            return phone;
        }

    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto_ids() {


        return photo_ids;
    }

    public void setPhoto_ids(String photo_ids) {
        this.photo_ids = photo_ids;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSubject_type() {
        return subject_type;
    }

    public void setSubject_type(int subject_type) {
        this.subject_type = subject_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
