package com.example.ruitongapp.fangkebean;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by Administrator on 2017/10/18.
 */
@Parcel
public class FangKeBean {

    /**
     * createTime : 1508293484427
     * dtoResult : 0
     * modifyTime : 1508293484427
     * objects : [{"accountId":10000005,"accountName":"市电","address":"","audit":"1","cardNumber":"rt1506412259154","cardPhoto":"","countType":0,"createTime":1506412224000,"dtoResult":0,"gender":1,"id":10000225,"level":"2","modifyTime":1506412224000,"name":"yyy","organ":"","pageNum":0,"pageSize":0,"phone":"3","scanPhoto":"20170926/1506412200149.jpg","score":0,"sid":0,"source":"手动来源","status":1,"subjectType":1,"visitDate":1506412200000,"visitDepartment":"2","visitIncident":"已确认,电话联系被访人","visitNum":1,"visitPerson":"yyy and"},{"accountId":10000005,"accountName":"市电","address":"","audit":"1","cardNumber":"rt1506412040192","cardPhoto":"","countType":0,"createTime":1506412005000,"dtoResult":0,"gender":1,"id":10000224,"level":"2","modifyTime":1506412005000,"name":"high","organ":"","pageNum":0,"pageSize":0,"phone":"","scanPhoto":"20170926/1506411999491.jpg","score":0,"sid":0,"source":"手动来源","status":1,"subjectType":1,"visitDate":1506411960000,"visitDepartment":"","visitIncident":"已确认,电话联系被访人","visitNum":1,"visitPerson":""}]
     * pageIndex : 0
     * pageNum : 1
     * pageSize : 2
     * sid : 0
     * totalNum : 73
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
         * address :
         * audit : 1
         * cardNumber : rt1506412259154
         * cardPhoto :
         * countType : 0
         * createTime : 1506412224000
         * dtoResult : 0
         * gender : 1
         * id : 10000225
         * level : 2
         * modifyTime : 1506412224000
         * name : yyy
         * organ :
         * pageNum : 0
         * pageSize : 0
         * phone : 3
         * scanPhoto : 20170926/1506412200149.jpg
         * score : 0
         * sid : 0
         * source : 手动来源
         * status : 1
         * subjectType : 1
         * visitDate : 1506412200000
         * visitDepartment : 2
         * visitIncident : 已确认,电话联系被访人
         * visitNum : 1
         * visitPerson : yyy and
         */
        public String firstLetter;
        public String avatar;
        public int accountId;
        public String accountName;
        public String address;
        public String audit;
        public String cardNumber;
        public String cardPhoto;
        public int countType;
        public long createTime;
        public int dtoResult;
        public int gender;
        public Long id;
        public String level;
        public long modifyTime;
        public String name;
        public String organ;
        public int pageNum;
        public int pageSize;
        public String phone;
        public String scanPhoto;
        public int score;
        public int sid;
        public String source;
        public int status;
        public int subjectType;
        public long visitDate;
        public String visitDepartment;
        public String visitIncident;
        public int visitNum;
        public String visitPerson;
        public String homeNumber;
        public long visitEndDate;
        public String companyName;

        public long getVisitEndDate() {
            return visitEndDate;
        }

        public void setVisitEndDate(long visitEndDate) {
            this.visitEndDate = visitEndDate;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getHomeNumber() {
            return homeNumber;
        }

        public void setHomeNumber(String homeNumber) {
            this.homeNumber = homeNumber;
        }

        public String getFirstLetter() {
            return firstLetter;
        }

        public void setFirstLetter(String firstLetter) {
            this.firstLetter = firstLetter;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAudit() {
            return audit;
        }

        public void setAudit(String audit) {
            this.audit = audit;
        }

        public String getCardNumber() {
            return cardNumber;
        }

        public void setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
        }

        public String getCardPhoto() {
            return cardPhoto;
        }

        public void setCardPhoto(String cardPhoto) {
            this.cardPhoto = cardPhoto;
        }

        public int getCountType() {
            return countType;
        }

        public void setCountType(int countType) {
            this.countType = countType;
        }

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

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
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

        public String getOrgan() {
            return organ;
        }

        public void setOrgan(String organ) {
            this.organ = organ;
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

        public String getScanPhoto() {
            return scanPhoto;
        }

        public void setScanPhoto(String scanPhoto) {
            this.scanPhoto = scanPhoto;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getSid() {
            return sid;
        }

        public void setSid(int sid) {
            this.sid = sid;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getSubjectType() {
            return subjectType;
        }

        public void setSubjectType(int subjectType) {
            this.subjectType = subjectType;
        }

        public long getVisitDate() {
            return visitDate;
        }

        public void setVisitDate(long visitDate) {
            this.visitDate = visitDate;
        }

        public String getVisitDepartment() {
            return visitDepartment;
        }

        public void setVisitDepartment(String visitDepartment) {
            this.visitDepartment = visitDepartment;
        }

        public String getVisitIncident() {
            return visitIncident;
        }

        public void setVisitIncident(String visitIncident) {
            this.visitIncident = visitIncident;
        }

        public int getVisitNum() {
            return visitNum;
        }

        public void setVisitNum(int visitNum) {
            this.visitNum = visitNum;
        }

        public String getVisitPerson() {
            return visitPerson;
        }

        public void setVisitPerson(String visitPerson) {
            this.visitPerson = visitPerson;
        }
    }
}
