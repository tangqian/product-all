/**
 * @Date 2015年11月6日 上午10:49:16
 * @author tangq
 * @version V1.0
 */
package com.ofweek.live.core.modules.rpc.dto;

import java.util.Date;
import java.util.List;

/**
 *
 */
public class WebinarMeetingsDto {

    private Integer totalRows;

    private boolean success;

    private List<WebinarActivity> activity;

    public List<WebinarActivity> getActivity() {
        return activity;
    }

    public void setActivity(List<WebinarActivity> activity) {
        this.activity = activity;
    }

    public Integer getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(Integer totalRows) {
        this.totalRows = totalRows;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class WebinarActivity {

        private String coverImgUrl;

        private Date startTime;

        private Date endTime;

        private String company;

        private String summary;

        private Integer id;

        private String name;

        private String lecturer;//主讲人

        private Integer status;//0代表预告, 1代表直播中, 2表示研讨会结束

        public String getCoverImgUrl() {
            return coverImgUrl;
        }

        public void setCoverImgUrl(String coverImgUrl) {
            this.coverImgUrl = coverImgUrl;
        }

        public Date getStartTime() {
            return startTime;
        }

        public void setStartTime(Date startTime) {
            this.startTime = startTime;
        }

        public Date getEndTime() {
            return endTime;
        }

        public void setEndTime(Date endTime) {
            this.endTime = endTime;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLecturer() {
            return lecturer;
        }

        public void setLecturer(String lecturer) {
            this.lecturer = lecturer;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }
    }
}
