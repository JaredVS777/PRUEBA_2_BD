public class personas {
    private String codigo;
    private String cedula;
    private String nombre;
    private String signo;
    private String fechaNacimiento;

    public Persona(String codigo, String cedula, String nombre, String signo, String fechaNacimiento) {
        this.codigo = codigo;
        this.cedula = cedula;
        this.nombre = nombre;
        this.signo = signo;
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSigno() {
        return signo;
    }

    public void setSigno(String signo) {
        this.signo = signo;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}
