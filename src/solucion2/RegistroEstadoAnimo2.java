
package solucion2;

public class RegistroEstadoAnimo2 {

    private String idEstudiante;
    private String fecha;   // formato YYYY-MM-DD
    private String estado;
    private String nota;

    public RegistroEstadoAnimo2(String idEstudiante, String fecha, String estado, String nota) {
        this.idEstudiante = idEstudiante;
        this.fecha = fecha;
        this.estado = estado;
        this.nota = nota;
    }

    public String getIdEstudiante() {
        return idEstudiante;
    }

    public String getFecha() {
        return fecha;
    }

    public String getEstado() {
        return estado;
    }

    public String getNota() {
        return nota;
    }

    public boolean esEstadoNegativo() {
        return "TRISTE".equals(estado) || "MUY TRISTE".equals(estado);
    }

    @Override
    public String toString() {
        return "[" + idEstudiante + "] " + fecha + " - " + estado + " - " + nota;
    }
}
