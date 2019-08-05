package parseXml;

public class SecDef {
    private String desc;//
    private String CentraQuoteBondIndic;
    private Instrmt instrmt;

    public SecDef() {

    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCentraQuoteBondIndic() {
        return CentraQuoteBondIndic;
    }

    public void setCentraQuoteBondIndic(String centraQuoteBondIndic) {
        CentraQuoteBondIndic = centraQuoteBondIndic;
    }

    public Instrmt getInstrmt() {
        return instrmt;
    }

    public void setInstrmt(Instrmt instrmt) {
        this.instrmt = instrmt;
    }

    @Override
    public String toString() {
        return "SecDef{" +
                "desc='" + desc + '\'' +
                ", CentraQuoteBondIndic='" + CentraQuoteBondIndic + '\'' +
                ", instrmt=" + instrmt +
                '}';
    }
}

