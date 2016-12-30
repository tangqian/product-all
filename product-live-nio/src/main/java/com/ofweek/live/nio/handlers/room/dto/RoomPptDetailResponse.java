package com.ofweek.live.nio.handlers.room.dto;

import java.io.Serializable;
import java.util.List;

public class RoomPptDetailResponse {

    private String prefix;

    private List<PptPage> pages;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public List<PptPage> getPages() {
        return pages;
    }

    public void setPages(List<PptPage> pages) {
        this.pages = pages;
    }

    public static class PptPage {

        private Integer page;
        private String url;

        public PptPage() {
        }

        public PptPage(Integer page, String url) {
            this.page = page;
            this.url = url;
        }

        public Integer getPage() {
            return page;
        }

        public void setPage(Integer page) {
            this.page = page;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

    }

}
