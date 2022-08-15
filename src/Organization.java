import java.io.Serializable;

public class Organization implements Serializable {

    private static final long serialVersionUID = 273448023018662405L;

    private int orgId;

    private String name;

    public Organization(int orgId, String name) {
        this.orgId = orgId;
        this.name = name;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
