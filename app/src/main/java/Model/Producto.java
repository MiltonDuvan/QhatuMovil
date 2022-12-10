package Model;

public class Producto {
    private String id;
    private String foto;
    private String descripcion;
    private String nombre;
    private String fecha_inicio;
    private String fecha_cierre;
    private String hora_cierre;
    private String oferta_inicial;

    public Producto() {
    }

    public Producto(String id, String foto, String descripcion, String nombre, String fecha_inicio, String fecha_cierre, String hora_cierre, String oferta_inicial) {
        this.id = id;
        this.foto = foto;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.fecha_inicio = fecha_inicio;
        this.fecha_cierre = fecha_cierre;
        this.hora_cierre = hora_cierre;
        this.oferta_inicial = oferta_inicial;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_cierre() {
        return fecha_cierre;
    }

    public void setFecha_cierre(String fecha_cierre) {
        this.fecha_cierre = fecha_cierre;
    }

    public String getHora_cierre() {
        return hora_cierre;
    }

    public void setHora_cierre(String hora_cierre) {
        this.hora_cierre = hora_cierre;
    }

    public String getOferta_inicial() {
        return oferta_inicial;
    }

    public void setOferta_inicial(String oferta_inicial) {
        this.oferta_inicial = oferta_inicial;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id='" + id + '\'' +
                ", foto='" + foto + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", nombre='" + nombre + '\'' +
                ", fecha_inicio='" + fecha_inicio + '\'' +
                ", fecha_cierre='" + fecha_cierre + '\'' +
                ", hora_cierre='" + hora_cierre + '\'' +
                ", oferta_inicial='" + oferta_inicial + '\'' +
                '}';
    }
}
