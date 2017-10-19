package com.example.ruitongapp.gonggaobean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/19.
 */

public class GongGaoBean {

    /**
     * createTime : 1508379342549
     * dtoResult : 0
     * modifyTime : 1508379342549
     * objects : [{"accountId":10000000,"content":"121211111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","createTime":1508379342428,"dtoResult":0,"id":10000008,"issueTime":1506600180000,"issueType":0,"modifyTime":1508379342428,"pageNum":0,"pageSize":0,"sid":0,"title":"12121"},{"accountId":10000000,"content":"111","createTime":1508379342431,"dtoResult":0,"id":10000012,"issueTime":1506564420000,"issueType":0,"modifyTime":1508379342431,"pageNum":0,"pageSize":0,"sid":0,"title":"111"},{"accountId":10000000,"content":"444444444444444","createTime":1508379342432,"dtoResult":0,"id":10000011,"issueTime":1506504540000,"issueType":0,"modifyTime":1508379342432,"pageNum":0,"pageSize":0,"sid":0,"title":"44444444444444"}]
     * pageIndex : 0
     * pageNum : 1
     * pageSize : 20
     * sid : 0
     * totalNum : 3
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
         * accountId : 10000000
         * content : 121211111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111
         * createTime : 1508379342428
         * dtoResult : 0
         * id : 10000008
         * issueTime : 1506600180000
         * issueType : 0
         * modifyTime : 1508379342428
         * pageNum : 0
         * pageSize : 0
         * sid : 0
         * title : 12121
         */
        private boolean isShanChu2=false;
        private boolean shanchu=false;
        private int accountId;
        private String content;
        private long createTime;
        private int dtoResult;
        private int id;
        private long issueTime;
        private int issueType;
        private long modifyTime;
        private int pageNum;
        private int pageSize;
        private int sid;
        private String title;

        public boolean isShanChu2() {
            return isShanChu2;
        }

        public void setShanChu2(boolean shanChu2) {
            isShanChu2 = shanChu2;
        }

        public boolean isShanchu() {
            return shanchu;
        }

        public void setShanchu(boolean shanchu) {
            this.shanchu = shanchu;
        }

        public int getAccountId() {
            return accountId;
        }

        public void setAccountId(int accountId) {
            this.accountId = accountId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getIssueTime() {
            return issueTime;
        }

        public void setIssueTime(long issueTime) {
            this.issueTime = issueTime;
        }

        public int getIssueType() {
            return issueType;
        }

        public void setIssueType(int issueType) {
            this.issueType = issueType;
        }

        public long getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(long modifyTime) {
            this.modifyTime = modifyTime;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
