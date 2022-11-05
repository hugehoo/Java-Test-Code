package inflearn.thejavatest.domain;

import inflearn.thejavatest.StudyStatus;

public class Study {

    private StudyStatus status = StudyStatus.DRAFT;
    private int limit;
    private String name;

    public Study(int limit, String name) {
        this.limit = limit;
        this.name = name;
    }

    public Study(int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("0 미만 금지");
        }
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }

    public StudyStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Study{" +
                "limit=" + limit +
                ", name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setOwner(Member member_not_exist) {
    }
}