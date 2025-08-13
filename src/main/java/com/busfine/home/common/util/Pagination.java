package com.busfine.home.common.util;

import lombok.Data;

@Data
public class Pagination {
    private int pageSize;      // 한 페이지당 게시글 수
    private int pageNo;        // 현재 페이지 번호 (1부터 시작)
    private int totalCount;    // 전체 게시글 수

    private int firstPageNo;   // 첫 번째 페이지 번호 (보통 1)
    private int finalPageNo;   // 마지막 페이지 번호
    private int prevPageNo;    // 이전 페이지 번호
    private int nextPageNo;    // 다음 페이지 번호

    private int startPageNo;   // 페이징 네비 시작 페이지 번호 (예: 1, 11, 21 ...)
    private int endPageNo;     // 페이징 네비 끝 페이지 번호 (예: 10, 20, 30 ...)

    private int pageNavSize = 10; // 페이징 네비에 보여줄 페이지 개수 (기본 10)

    // 생성자에서는 totalCount 받지 않고 pageNo, pageSize만 받음
    public Pagination(int pageNo, int pageSize) {
        this.pageNo = Math.max(pageNo, 1);
        this.pageSize = Math.max(pageSize, 5);

        this.firstPageNo = 1;

        // totalCount가 0이므로 계산은 아직 하지 않음
        this.totalCount = 0;
        this.finalPageNo = 0;

        this.prevPageNo = firstPageNo;
        this.nextPageNo = firstPageNo;

        this.startPageNo = 1;
        this.endPageNo = 1;
    }

    // totalCount 세팅 시 내부 변수 다시 계산
    public void setTotalCount(int totalCount) {
        this.totalCount = Math.max(totalCount, 0);

        // 마지막 페이지 번호 계산
        this.finalPageNo = (int) Math.ceil((double) this.totalCount / this.pageSize);
        if (this.finalPageNo == 0) {
            this.finalPageNo = 1;  // 최소 1페이지는 있도록 처리
        }

        // 현재 페이지 번호 보정
        if (this.pageNo > this.finalPageNo) {
            this.pageNo = this.finalPageNo;
        }

        // 이전/다음 페이지 번호 계산
        this.prevPageNo = (this.pageNo > this.firstPageNo) ? this.pageNo - 1 : this.firstPageNo;
        this.nextPageNo = (this.pageNo < this.finalPageNo) ? this.pageNo + 1 : this.finalPageNo;

        // 페이징 네비 시작/끝 페이지 계산
        this.startPageNo = ((this.pageNo - 1) / pageNavSize) * pageNavSize + 1;
        this.endPageNo = Math.min(this.startPageNo + pageNavSize - 1, this.finalPageNo);
    }

    public int getOffset() {
        return (pageNo - 1) * pageSize;
    }

    public int getLimit() {
        return pageSize;
    }
}
