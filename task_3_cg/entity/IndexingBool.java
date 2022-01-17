package task_3_cg.entity;

public class IndexingBool {
    private Integer index;
    private boolean status;

    public IndexingBool(Integer index) {
        this.index = index;
        this.status = true;
    }

    public Integer getIndex() {
        if(status = true) {
            status = false;
            return index;
        } else {
            return null;
        }
    }
}
