package com.example.ruitongapp.beans;

import java.util.List;

/**
 * Created by Administrator on 2017/10/10.
 */

public class YuanGongBean {


    /**
     * createTime : 1507972942742
     * dtoResult : 0
     * modifyTime : 1507972942742
     * objects : [{"accountId":10000005,"accountName":"市电","avatar":"","createTime":1507775759000,"department":"技术部门","description":"","dtoResult":0,"email":"","gender":1,"id":10000132,"job_number":"","modifyTime":1507775786000,"name":"王总","pageNum":0,"pageSize":0,"phone":"","photo_ids":"20171012/1507775744274.jpg,","remark":"","sid":0,"status":1,"subject_type":0,"title":""},{"accountId":10000005,"accountName":"市电","avatar":"","createTime":1504713172000,"department":"","description":"","dtoResult":0,"email":"","gender":0,"id":10000057,"job_number":"","modifyTime":1504713624000,"name":"张总","pageNum":0,"pageSize":0,"phone":"","photo_ids":"20170906/1504713172770.jpg,","remark":"","sid":0,"status":1,"subject_type":0,"title":""},{"accountId":10000005,"accountName":"市电","avatar":"","createTime":1504713193000,"department":"","description":"","dtoResult":0,"email":"","gender":0,"id":10000060,"job_number":"","modifyTime":1504713598000,"name":"水波总","pageNum":0,"pageSize":0,"phone":"","photo_ids":"20170906/1504713193810.jpg,","remark":"","sid":0,"status":1,"subject_type":0,"title":""}]
     * pageIndex : 0
     * pageNum : 1
     * pageSize : 3
     * sid : 0
     * totalNum : 15
     */

    private long createTime;
    private int dtoResult;
    private long modifyTime;
    private int pageIndex;
    private int pageNum;
    private int pageSize;
    private int sid;
    private int totalNum;
    private List<ObjectsBean> objects;

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getDtoResult() {
        return dtoResult;
    }

    public void setDtoResult(int dtoResult) {
        this.dtoResult = dtoResult;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
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

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public List<ObjectsBean> getObjects() {
        return objects;
    }

    public void setObjects(List<ObjectsBean> objects) {
        this.objects = objects;
    }

    public static class ObjectsBean {
        /**
         * accountId : 10000005
         * accountName : 市电
         * avatar :
         * createTime : 1507775759000
         * department : 技术部门
         * description :
         * dtoResult : 0
         * email :
         * gender : 1
         * id : 10000132
         * job_number :
         * modifyTime : 1507775786000
         * name : 王总
         * pageNum : 0
         * pageSize : 0
         * phone :
         * photo_ids : 20171012/1507775744274.jpg,
         * remark :
         * sid : 0
         * status : 1
         * subject_type : 0
         * title :
         */

        private int accountId;
        private String accountName;
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
        private String firstLetter;

        public String getFirstLetter() {
            return firstLetter;
        }

        public void setFirstLetter(String firstLetter) {
            this.firstLetter = firstLetter;
        }

        public int getAccountId() {
            return accountId;
        }

        public void setAccountId(int accountId) {
            this.accountId = accountId;
        }

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public String getAvatar() {
            return avatar;
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
            return phone;
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
}
