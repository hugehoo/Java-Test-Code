package inflearn.thejavatest;

public class Study {

    public Study(int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("0 미만 금지");
        }
        this.limit = limit;
    }

    private StudyStatus status = StudyStatus.DRAFT;
    public int limit;

    public int getLimit() {
        return limit;
    }

    public StudyStatus getStatus() {
        return status;
    }

}