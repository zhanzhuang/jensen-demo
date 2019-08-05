package parseXml;

public class Instrmt {
    private String id;
    private String sym;

    public Instrmt() {

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSym() {
        return sym;
    }

    public void setSym(String sym) {
        this.sym = sym;
    }

    @Override
    public String toString() {
        return "Instrmt{" +
                "id='" + id + '\'' +
                ", sym='" + sym + '\'' +
                '}';
    }
}
