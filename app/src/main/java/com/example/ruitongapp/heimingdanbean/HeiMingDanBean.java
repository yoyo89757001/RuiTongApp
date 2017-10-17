package com.example.ruitongapp.heimingdanbean;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by Administrator on 2017/10/17.
 */
@Parcel
public class HeiMingDanBean {


    /**
     * createTime : 1508210006110
     * dtoResult : 0
     * modifyTime : 1508210006110
     * objects : [{"accountId":10000005,"accountName":"市电","avatar":"","birthday2":"","createTime":1508209229000,"department":"黑名单","dtoResult":0,"entry_date2":"","gender":0,"id":10000138,"modifyTime":1508209229000,"name":"211","pageNum":0,"pageSize":0,"photo_ids":"20171017/1508209162651.jpg","remark":"","sid":0,"status":1,"subject_type":0},{"accountId":10000005,"accountName":"市电","avatar":"","birthday2":"","createTime":1508208886000,"department":"黑名单","dtoResult":0,"entry_date2":"","gender":0,"id":10000137,"modifyTime":1508208886000,"name":"1","pageNum":0,"pageSize":0,"photo_ids":"20171017/1508208880752.jpg,","remark":"","sid":0,"status":1,"subject_type":0}]
     * pageIndex : 0
     * pageNum : 1
     * pageSize : 20
     * sid : 0
     * totalNum : 2
     */

    public long createTime;
    public int dtoResult;
    public long modifyTime;
    public int pageIndex;
    public int pageNum;
    public int pageSize;
    public int sid;
    public int totalNum;
    public List<ObjectsBean> objects;

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
    
    @Parcel
    public static class ObjectsBean {
        /**
         * accountId : 10000005
         * accountName : 市电
         * avatar :
         * birthday2 :
         * createTime : 1508209229000
         * department : 黑名单
         * dtoResult : 0
         * entry_date2 :
         * gender : 0
         * id : 10000138
         * modifyTime : 1508209229000
         * name : 211
         * pageNum : 0
         * pageSize : 0
         * photo_ids : 20171017/1508209162651.jpg
         * remark :
         * sid : 0
         * status : 1
         * subject_type : 0
         */

        public int accountId;
        public String accountName;
        public String avatar;
        public String birthday2;
        public long createTime;
        public String department;
        public int dtoResult;
        public String entry_date2;
        public int gender;
        public Long id;
        public long modifyTime;
        public String name;
        public int pageNum;
        public int pageSize;
        public String photo_ids;
        public String remark;
        public int sid;
        public int status;
        public int subject_type;
        public String firstLetter;

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

        public String getBirthday2() {
            return birthday2;
        }

        public void setBirthday2(String birthday2) {
            this.birthday2 = birthday2;
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

        public int getDtoResult() {
            return dtoResult;
        }

        public void setDtoResult(int dtoResult) {
            this.dtoResult = dtoResult;
        }

        public String getEntry_date2() {
            return entry_date2;
        }

        public void setEntry_date2(String entry_date2) {
            this.entry_date2 = entry_date2;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
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
    }
}
