package solucion2;

public class CasoRiesgo2 {

    private String idEstudiante;
    private int diasRacha;
    private String motivo;
    private String ultimaFecha;

    public CasoRiesgo2(String idEstudiante, int diasRacha, String motivo, String ultimaFecha) {
        this.idEstudiante = idEstudiante;
        this.diasRacha = diasRacha;
        this.motivo = motivo;
        this.ultimaFecha = ultimaFecha;
    }

    public String getIdEstudiante() {
        return idEstudiante;
    }

    public int getDiasRacha() {
        return diasRacha;
    }

    public String getMotivo() {
        return motivo;
    }

    public String getUltimaFecha() {
        return ultimaFecha;
    }


    public void setDiasRacha(int diasRacha) {
        this.diasRacha = diasRacha;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public void setUltimaFecha(String ultimaFecha) {
        this.ultimaFecha = ultimaFecha;
    }

    @Override
    public String toString() {
        return "[" + idEstudiante + "] Racha de " + diasRacha +
                " días - Última fecha: " + ultimaFecha +
                " - " + motivo;
    }
}
