package Model;

public class Oferta {
    private String id;
    private String oferta;

    public Oferta() {
    }

    public Oferta(String id, String oferta) {
        this.id = id;
        this.oferta = oferta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOferta() {
        return oferta;
    }

    public void setOferta(String oferta) {
        this.oferta = oferta;
    }

    @Override
    public String toString() {
        return "Subasta{" +
                "id='" + id + '\'' +
                ", oferta='" + oferta + '\'' +
                '}';
    }
}
