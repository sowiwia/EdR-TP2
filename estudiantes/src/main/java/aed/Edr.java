package aed;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import aed.Heap.HandleHeap;

public class Edr {
    private int _cantidad_estudiantes;
    private int[] _examenCanonico;
    private int _dimension;
    private Heap<Estudiante> _estudiantePorNota;
    private ArrayList<Heap<Estudiante>.HandleHeap> _estudiantePorId;

    public Edr(int LadoAula, int Cant_estudiantes, int[] ExamenCanonico) {
        _cantidad_estudiantes = Cant_estudiantes;
        _examenCanonico = ExamenCanonico;
        _dimension = LadoAula;

        _estudiantePorNota = new Heap<>(); // Crear Array de E Elementos: O(E)
        _estudiantePorId = new ArrayList<>(Cant_estudiantes); // Crear Array de E elementos: O(E)

        int i = 0;

        while (i < Cant_estudiantes) { // Recorro E estudiantes: O(E)
            Integer[] examen = new Integer[_examenCanonico.length]; // Crea un Array con las R respuestas del examen canónico: O(R)
            Estudiante estudiante = new Estudiante(i, 0.0, examen, false, 0, false);
            // Conectamos los estudiantes del Heap con los del Array
            Heap<Estudiante>.HandleHeap handleEstudiante = _estudiantePorNota.encolar(estudiante); //  El costo de encolar en el Heap es: O(1) dado que encolo estudiantes con nota constante y ordenados por ID
            _estudiantePorId.add(handleEstudiante); // Como el tamaño del arraylist ya está definido, elcosto de agregar un elemento al array es O(1)
            i = i + 1;
        } // Costo del ciclo: (O (E*R))
        
        // Complejidad total = O(E*R) + O(E) = O(max(E*R,E)) = O(E*R)
    }

    // -------------------------------------------------NOTAS--------------------------------------------------------------------------

    public double[] notas() {

        int i = 0;

        //Creo un array de tamaño E para almacenar las notas: O(E)
        double[] res = new double[_cantidad_estudiantes];

        // Accede a las notas de los estudiantes y los agrega en el array: O(E)
        while (i < _cantidad_estudiantes) {
            Heap<Estudiante>.HandleHeap handleEstudiante = _estudiantePorId.get(i);
            Estudiante infoEstudiante = handleEstudiante.valor();
            double nota = infoEstudiante.notaEstudiante();
            res[i] = nota;
            i = i + 1;
        }
        return res;

        // Complejidad total = O(E) + O(E) = O(E)
    }

    // ------------------------------------------------COPIARSE------------------------------------------------------------------------

    public void copiarse(int estudiante) {

        ArrayList<Estudiante> candidatosCopia = new ArrayList<>(); //O(cantidad de candidatos <= 3) = O(1)
        int sentados_por_fila = Math.ceilDiv(_dimension, 2); // Cantidad de sentados por fila

        boolean esta_en_columna_izquierda = estudiante % sentados_por_fila == 0;
        boolean esta_en_columna_derecha = (estudiante % sentados_por_fila) == sentados_por_fila - 1;
        boolean esta_en_primera_fila = estudiante < sentados_por_fila;

        Estudiante estudianteDer = null;
        Estudiante estudianteIzq = null;
        Estudiante estudianteFrente = null;

        // Chequear candidatos a copia cuesta: O(1)

        if (esta_en_columna_izquierda) {
            estudianteDer = _estudiantePorId.get(estudiante + 1).valor();
            if (!esta_en_primera_fila) {
                // Esta en columna izquierda
                estudianteFrente = _estudiantePorId.get(estudiante - sentados_por_fila).valor();
            }
            // Esta en esquina superior izq
        } else if (esta_en_columna_derecha) {
            estudianteIzq = _estudiantePorId.get(estudiante - 1).valor();
            if (!esta_en_primera_fila) {
                // Esta en columna derecha
                estudianteFrente = _estudiantePorId.get(estudiante - sentados_por_fila).valor();
            }
            // Esta en esquina superior der
        } else {
            estudianteDer = _estudiantePorId.get(estudiante + 1).valor();
            estudianteIzq = _estudiantePorId.get(estudiante - 1).valor();
            if (!esta_en_primera_fila) {
                // Esta en el medio
                estudianteFrente = _estudiantePorId.get(estudiante - sentados_por_fila).valor();
            }
            // medio primera fila
        }

        // O(1)
        agregarCandidatos(estudianteDer, candidatosCopia);
        agregarCandidatos(estudianteIzq, candidatosCopia);
        agregarCandidatos(estudianteFrente, candidatosCopia);

        Estudiante infoEstudiante = _estudiantePorId.get(estudiante).valor();
        int maxRespuestas = 0;
        Estudiante copiado = null;
        
        // Recorre los examenes de los candidatos a copia para contar el total de respuestas que tiene el candidato que el estudiante no tiene, se ejecuta 3 veces: O(3*R) = O(R) 

        for (int j = 0; j < candidatosCopia.size(); j++) { // candidatosCopia son acotados (<= 3) entonces: O(1)

            Estudiante candidatoCopia = candidatosCopia.get(j);
            int cantRespuestas = 0;

            // Recorre examen O(R)
            for (int pregunta = 0; pregunta < _examenCanonico.length; pregunta++) {
                boolean estudianteResolvio = infoEstudiante.obtenerRespuesta(pregunta) != null;
                boolean candidatoResolvio = candidatoCopia.obtenerRespuesta(pregunta) != null;

                if (!estudianteResolvio && candidatoResolvio) {
                    cantRespuestas += 1;
                }
                // Veo si es examen con mas respuestas 
                if (cantRespuestas > maxRespuestas) {
                    maxRespuestas = cantRespuestas;
                    copiado = candidatoCopia;
                }
            }
        }

        // no se puede copiar: O(1)
        if (maxRespuestas == 0) {
            return;
        }

        int preguntaCopiada = 0;

        // Teniendo el examen a copiar, en el peor caso recorre el examen completo hasta encontrar la primer pregunta que tienen diferente el examen del estudiante y el examen a copiar: O(R)

        for (int pregunta = 0; pregunta < _examenCanonico.length; pregunta++) { // Recorre examen O(R)
            boolean estudianteResolvio = infoEstudiante.obtenerRespuesta(pregunta) != null;
            boolean copiadoResolvio = copiado.obtenerRespuesta(pregunta) != null;

            // O(1)
            if (!estudianteResolvio && copiadoResolvio) {
                int respuesta_copiada = copiado.obtenerRespuesta(pregunta);
                infoEstudiante.cambiarRespuesta(pregunta, respuesta_copiada);
                preguntaCopiada = pregunta;
                // Solo copia una pregunta
                break;
            }
        }

        Estudiante copiaEstudiante = new Estudiante(infoEstudiante); // O(1) dado que se pasa referencia del Array que representa examenes y demas atributos son tipos primitivos

        // Cambiar nota en heap O(log E)
        boolean es_correcta_pregunta_copiada = infoEstudiante.obtenerRespuesta(preguntaCopiada) == _examenCanonico[preguntaCopiada];

        if (es_correcta_pregunta_copiada) { //O(1)

            // O (log E)
            copiaEstudiante.cambiarCantRespuestasCorrectas(1);
            copiaEstudiante.actualizarNota(); 
            
            _estudiantePorId.get(estudiante).actualizarValor(copiaEstudiante);        
        }
    }

    private void agregarCandidatos(Estudiante cercano, ArrayList<Estudiante> candidatosCopia) {
        boolean es_valido = cercano != null;
        if (es_valido) {
            candidatosCopia.add(cercano); //O(1)
        }
    }
        // Complejidad total: O(R) + O(R) + O(log E) = O(R + log E)

    // -----------------------------------------------RESOLVER----------------------------------------------------------------

    public void resolver(int estudiante, int NroEjercicio, int res) {

        // Accedo al estudiante por ID O(1)
        Estudiante infoEstudiante = _estudiantePorId.get(estudiante).valor();

        boolean eraCorrecta = (infoEstudiante.obtenerRespuesta(NroEjercicio) != null && infoEstudiante.obtenerRespuesta(NroEjercicio) == _examenCanonico[NroEjercicio]);

        boolean resEsCorrecta = (res == _examenCanonico[NroEjercicio]);

        //Actualizar respuesta O(1)
        infoEstudiante.cambiarRespuesta(NroEjercicio, res);

        boolean cambioNota = false;

        //Cambiar la nota O(1)
        if (eraCorrecta && !resEsCorrecta) {
            infoEstudiante.cambiarCantRespuestasCorrectas(-1);
            cambioNota = true;

        } else if (!eraCorrecta && resEsCorrecta) {
            infoEstudiante.cambiarCantRespuestasCorrectas(1);
            cambioNota = true;
        }

        // Cambiar la posición de el estudiante en el Heap O(log E)
        if (cambioNota) { //O(1)
            // O(log E)
            Estudiante copiaEstudiante = new Estudiante(infoEstudiante); // O(1) dado que se pasa referencia del Array que representa examenes y demas atributos son tipos primitivos
            copiaEstudiante.actualizarNota();
            _estudiantePorId.get(estudiante).actualizarValor(copiaEstudiante);
        }
        //Complejidad total: O(log E)
    }


    // ------------------------------------------------CONSULTAR DARK
    // WEB-------------------------------------------------------

    public void consultarDarkWeb(int k, int[] examenDW) {
        int cantidad_PeoresEstudiantes = 0;
        ArrayList<Estudiante> peoresEstudiantes = new ArrayList<>();
        ArrayList<Estudiante> estudiantesEntregados = new ArrayList<>();

        // Extrae k veces el estudiante de máxima prioridad en el Heap O(k * log E)
        while (cantidad_PeoresEstudiantes < k && _estudiantePorNota.longitud() > 0) { //O(k)
            Estudiante estudianteActual = _estudiantePorNota.desencolar(); // O (log E)
            boolean estudianteEntrego = estudianteActual.entregoExamen();  // O(1)

            // O(1)
            if (!estudianteEntrego) {
                peoresEstudiantes.add(estudianteActual);
                cantidad_PeoresEstudiantes++;
            } else {
                estudiantesEntregados.add(estudianteActual);
            }
        }

        // O(k * R) porque recorre los k peores estudiantes y cada uno de sus examenes
        for (Estudiante estudiante : peoresEstudiantes) { //O(k)
            Estudiante estudianteMalo = estudiante;

            // Fijo cantidad de respuestas correctas a cero, ignorando las respuestas anteriores
            estudianteMalo.cambiarCantRespuestasCorrectas((-1) * estudianteMalo.respuestasCorrectasEstudiante());

            // Recorre cada examen de los peores estudiantes y cambia sus respuestas O(R)
            for (int pregunta = 0; pregunta < examenDW.length; pregunta++) {
                int respuesta_DW = examenDW[pregunta];
                estudianteMalo.cambiarRespuesta(pregunta, respuesta_DW);

                if (respuesta_DW == _examenCanonico[pregunta]) { // O(1)
                    estudianteMalo.cambiarCantRespuestasCorrectas(1); 
                }
            }

            estudianteMalo.actualizarNota(); // O(1)
            Heap<Estudiante>.HandleHeap handleEstudiante = _estudiantePorNota.encolar(estudianteMalo); // O(log E)
            _estudiantePorId.set(estudianteMalo.idEstudiante(), handleEstudiante);
        }

        // O(log E)
        for (Estudiante estudiante : estudiantesEntregados) { // estudiantesEntregados acotados <= k : O(1)
            Heap<Estudiante>.HandleHeap handleEstudiante = _estudiantePorNota.encolar(estudiante); //O(log E)
            _estudiantePorId.set(estudiante.idEstudiante(), handleEstudiante);
        }
    }
        // Complejidad total: O(k * log E) + O(k * R) + O(log E) + O(log E) = O(k * (R + log E))

    // -------------------------------------------------ENTREGAR-------------------------------------------------------------

    public void entregar(int estudiante) {

        Heap<Estudiante>.HandleHeap handleEstudiante = _estudiantePorId.get(estudiante);
        Estudiante infoEstudiante = handleEstudiante.valor();
        infoEstudiante.entregarExamen();
        
        // Complejidad total: O(log E)
    }

    // -----------------------------------------------------CORREGIR---------------------------------------------------------

    public NotaFinal[] corregir() { // Peor caso: ningun estudiante se copia, corrijo a todos los estudiantes
        
        Heap<Estudiante> heapTemporal = new Heap<>();

        // encolar estudiantes que entregaron y no copiaron en el heap temporal
        // O(log E), E veces, total O(E * log E)

        for (int id = 0; id < _cantidad_estudiantes; id++) { // Recorro todos los estudiantes O(E)
            Estudiante estudiante = _estudiantePorId.get(id).valor();

            if (estudiante.entregoExamen() == true && estudiante.examenCopiado() == false) {
                heapTemporal.encolar(estudiante); // encolo O(log E)
            }
        }

        NotaFinal[] res = new NotaFinal[heapTemporal.longitud()]; // O(E)
        
        // desarmo el heap temporal para tener res O(E * log E)
        for (int i = 1; i < res.length + 1; i++) { // O(E) 
            Estudiante estudiante = heapTemporal.desencolar(); // O(log E) 
            NotaFinal nota = new NotaFinal(estudiante.notaEstudiante(), estudiante.idEstudiante());
            res[res.length - i] = nota; // O(1)
        }

        return res;

        // Complejidad total: O(E * log E) + O(E) + O(E * log E) = O(E * log E)
    }

    // -------------------------------------------------------CHEQUEAR
    // COPIAS-------------------------------------------------

    public int[] chequearCopias() {
        int cantidadPreguntas = _examenCanonico.length;

        Integer[][] respuestas = new Integer[cantidadPreguntas][_cantidad_estudiantes]; // O(E * R)

        // Recorre los examenes de cada estudiante y guarda sus respuestas

        for (int id_estudiante = 0; id_estudiante < _cantidad_estudiantes; id_estudiante++) { // O(E * R)
            Estudiante estudianteActual = _estudiantePorId.get(id_estudiante).valor();
            for (int pregunta = 0; pregunta < cantidadPreguntas; pregunta++) {
                respuestas[pregunta][id_estudiante] = estudianteActual.obtenerRespuesta(pregunta);
            }
        }

        int maximo_respuestas = 10;
        int[][] frecuencia = new int[cantidadPreguntas][maximo_respuestas]; // O(R * 10) = O(R) 

        // Recorro todos los estudiantes E y sus respuestas R, para contar cuanto se repite cada respuesta en cada ejercicio
        // O(R*E)

        for (int pregunta = 0; pregunta < cantidadPreguntas; pregunta++) {
            for (int estudiante = 0; estudiante < _cantidad_estudiantes; estudiante++) {
                Integer respuesta = respuestas[pregunta][estudiante];

                if (respuesta != null && respuesta >= 0) {
                    frecuencia[pregunta][respuesta]++;
                }
            }
        }

        ArrayList<Integer> res_aux = new ArrayList<>();

        // Calculo el numero minimo de coincidencias para sospechar (25%)
        double minimoCoincidencias = Math.ceil((_cantidad_estudiantes - 1) * 0.25);

        // Reviso cada estudiante para contar cuanto se repite cada respuesta en cada ejercicio: O(E*(R + Log E))
        for (int id_actual = 0; id_actual < _cantidad_estudiantes; id_actual++) { //O(E)
            boolean esSospechoso = true;
            boolean entregoEnBlanco = true;

            for (int pregunta = 0; pregunta < cantidadPreguntas; pregunta++) { //O(R)
                Integer respuestaActual = respuestas[pregunta][id_actual];

                //O(1)
                if (respuestaActual == null) {
                    continue;
                }

                entregoEnBlanco = false;

                // chequeo cuantos respondieron lo mismo, sin contarlo a el mismo, y si una respuesta no cumple el minimo deja de ser sospechoso

                int conteoTotal = frecuencia[pregunta][respuestaActual];

                // O(1)
                if (conteoTotal - 1 < minimoCoincidencias) {
                    esSospechoso = false;
                    break;
                }
            }

            if (esSospechoso && !entregoEnBlanco) {
                res_aux.add(id_actual);
                Estudiante infoEstudiante =_estudiantePorId.get(id_actual).valor();
                infoEstudiante.esCopion(true);
            }
        }

        // O(E) si todos son sospechosos (peor caso)
        int[] res = new int[res_aux.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = res_aux.get(i);
        }
        return res;
    }
    // Complejidad total: O(E*R) + O(E*R) + O(R) + O(E*R) + O(E) = O(E*R)
}