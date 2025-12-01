package solucion2;

import java.time.LocalDate;
import java.util.*;

public class DetectorRiesgoService2 {

    private List<RegistroEstadoAnimo2> historial;
    private List<String> fechasDisponibles;


    private Queue<CasoRiesgo2> colaRiesgo;


    private Map<String, CasoRiesgo2> casosPorEstudiante;


    private CasoRiesgo2 ultimoCasoDetectado;

    public DetectorRiesgoService2() {
        historial = new ArrayList<>();
        fechasDisponibles = new ArrayList<>();
        colaRiesgo = new LinkedList<>();
        casosPorEstudiante = new HashMap<>();
    }

    public CasoRiesgo2 getUltimoCasoDetectado() {
        return ultimoCasoDetectado;
    }

    public boolean agregarFecha(String fecha) {
        try {
            LocalDate.parse(fecha);
        } catch (Exception ex) {
            return false;
        }

        if (!fechasDisponibles.contains(fecha)) {
            fechasDisponibles.add(fecha);
            Collections.sort(fechasDisponibles);
        }
        return true;
    }

    public List<String> getFechasDisponibles() {
        return fechasDisponibles;
    }

    public List<RegistroEstadoAnimo2> getHistorial() {
        return historial;
    }


    public List<CasoRiesgo2> getCasosPendientesSnapshot() {
        return new ArrayList<>(casosPorEstudiante.values());
    }

    public CasoRiesgo2 obtenerSiguienteCaso() {

        CasoRiesgo2 c = colaRiesgo.poll();
        if (c != null) {
            casosPorEstudiante.remove(c.getIdEstudiante());
        }
        return c;
    }

    public void registrarEstado(String idEstudiante, String fecha, String estado, String nota) {
        if (idEstudiante == null || idEstudiante.trim().isEmpty()) return;
        if (fecha == null || fecha.trim().isEmpty()) return;
        if (estado == null || estado.trim().isEmpty()) return;

        idEstudiante = idEstudiante.trim();
        fecha = fecha.trim();
        estado = estado.trim();

        RegistroEstadoAnimo2 reg = new RegistroEstadoAnimo2(idEstudiante, fecha, estado, nota);
        historial.add(reg);

        if (!fechasDisponibles.contains(fecha)) {
            fechasDisponibles.add(fecha);
        }


        ultimoCasoDetectado = null;

        evaluarRachasConPila(idEstudiante);
    }

    private void evaluarRachasConPila(String idEstudiante) {

        List<String> fechasTristes = new ArrayList<>();

        for (RegistroEstadoAnimo2 r : historial) {
            if (r.getIdEstudiante().equals(idEstudiante) && r.esEstadoNegativo()) {
                if (!fechasTristes.contains(r.getFecha())) {
                    fechasTristes.add(r.getFecha());
                }
            }
        }

        if (fechasTristes.size() < 3) {
            return;
        }


        Collections.sort(fechasTristes);


        Stack<String> pilaRacha = new Stack<>();
        int maxRacha = 0;
        String ultimaFechaRacha = null;

        for (String f : fechasTristes) {
            if (pilaRacha.isEmpty()) {
                pilaRacha.push(f);
            } else {
                String ultima = pilaRacha.peek();
                LocalDate dUltima = LocalDate.parse(ultima);
                LocalDate dActual = LocalDate.parse(f);

                if (dActual.minusDays(1).equals(dUltima)) {
                    pilaRacha.push(f);
                } else {
                    if (pilaRacha.size() > maxRacha) {
                        maxRacha = pilaRacha.size();
                        ultimaFechaRacha = pilaRacha.peek();
                    }
                    pilaRacha.clear();
                    pilaRacha.push(f);
                }
            }
        }

        if (!pilaRacha.isEmpty() && pilaRacha.size() > maxRacha) {
            maxRacha = pilaRacha.size();
            ultimaFechaRacha = pilaRacha.peek();
        }

        if (maxRacha < 3) {

            return;
        }


        String motivo = "Racha de " + maxRacha + " días con estado TRISTE/MUY TRISTE";

        CasoRiesgo2 existente = casosPorEstudiante.get(idEstudiante);

        if (existente == null) {

            CasoRiesgo2 nuevo = new CasoRiesgo2(idEstudiante, maxRacha, motivo, ultimaFechaRacha);
            casosPorEstudiante.put(idEstudiante, nuevo);
            colaRiesgo.add(nuevo);
            ultimoCasoDetectado = nuevo;
        } else {

            if (maxRacha > existente.getDiasRacha()) {
                existente.setDiasRacha(maxRacha);
                existente.setUltimaFecha(ultimaFechaRacha);
                existente.setMotivo(motivo);
                ultimoCasoDetectado = existente;
            } else {

                ultimoCasoDetectado = null;
            }
        }
    }
}
