package aed;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.Arrays;


class EdrTests_Propios {
    Edr edr;
    int d_aula;
    int cant_alumnos;
    int[] solucion;

    @BeforeEach
    void setUp(){
        d_aula = 5;
        cant_alumnos = 6;
        solucion = new int[]{0,1,2,3,4,5,6,7,8,9};

        edr = new Edr(d_aula, cant_alumnos, solucion);
    }

    @Test
    void nuevo_edr() {
        double[] notas = edr.notas();
        double[] notas_esperadas = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));
    }
    
    @Test
    void alumnos_no_pueden_copiarse(){
        edr = new Edr (5, cant_alumnos,solucion);
        double[] notas;
        double[] notas_esperadas;

        edr.resolver(0,0,1);
        edr.resolver(1,0,2);
        edr.resolver(2,0,6);
        edr.resolver(3,0,2);
        edr.resolver(4,0,3);
        edr.resolver(5,0,0);

        notas = edr.notas();
        notas_esperadas = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 10.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));

        edr.copiarse(0);

        notas = edr.notas();
        notas_esperadas = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 10.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));

        edr.copiarse(1);
        
        notas = edr.notas();
        notas_esperadas = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 10.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));

        edr.copiarse(2);

        notas = edr.notas();
        notas_esperadas = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 10.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));

        edr.copiarse(3);

        notas = edr.notas();
        notas_esperadas = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 10.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));

        edr.copiarse(4);

        notas = edr.notas();
        notas_esperadas = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 10.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));

        edr.copiarse(5);

        notas = edr.notas();
        notas_esperadas = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 10.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));

        for(int alumno = 0; alumno < 6; alumno++){
            edr.entregar(alumno);
        }

        int[] copiones = edr.chequearCopias();
        int[] copiones_esperados = new int[]{};
        assertTrue(Arrays.equals(copiones_esperados, copiones));

        NotaFinal[] notas_finales = edr.corregir();
        NotaFinal[] notas_finales_esperadas = new NotaFinal[]{
            new NotaFinal(10.0, 5),
            new NotaFinal(0.0, 4),
            new NotaFinal(0.0, 3),
            new NotaFinal(0.0, 2),
            new NotaFinal(0.0, 1),
            new NotaFinal(0.0,0)
        };

        assertTrue(Arrays.equals(notas_finales_esperadas, notas_finales));
    }

    @Test
    void cambia_respuesta_correcta_por_incorrecta(){
        double[] notas;
        double[] notas_esperadas;

        edr.resolver(0,0,0);
        notas = edr.notas();
        notas_esperadas = new double[]{10.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));

        edr.resolver(0, 0,3);
        notas = edr.notas();
        notas_esperadas = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));
    }

    @Test
    void cambia_respuesta_incorrecta_por_correcta(){
        double[] notas;
        double[] notas_esperadas;

        edr.resolver(3,4,6);
        notas = edr.notas();
        notas_esperadas = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));

        edr.resolver(3, 4, 4);
        notas = edr.notas();
        notas_esperadas = new double[]{0.0, 0.0, 0.0, 10.0, 0.0, 0.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));
    }

    @Test
    void ningun_alumno_copia() {
        double[] notas;
        double[] notas_esperadas;

        edr.resolver(0, 0, 0); 
        edr.resolver(1, 1, 1); 
        edr.resolver(2, 2, 2); 
        edr.resolver(3, 3, 3); 
        edr.resolver(4, 4, 4); 
        edr.resolver(5, 5, 5); 

        notas = edr.notas();
        notas_esperadas = new double[]{10.0, 10.0, 10.0, 10.0, 10.0, 10.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));
        
        // entrega
        for(int alumno = 0; alumno < 6; alumno++){
            edr.entregar(alumno);
        }

        // chequeo chequearCopias
        int[] copiones = edr.chequearCopias();
        int[] copiones_esperados = new int[]{}; 
        assertTrue(Arrays.equals(copiones_esperados, copiones));

        // chequeo corregir
        NotaFinal[] notas_finales = edr.corregir();
        NotaFinal[] notas_finales_esperadas = new NotaFinal[]{
            new NotaFinal(10.0, 5),
            new NotaFinal(10.0, 4),
            new NotaFinal(10.0, 3),
            new NotaFinal(10.0, 2),
            new NotaFinal(10.0, 1),
            new NotaFinal(10.0, 0)
        };
        assertTrue(Arrays.equals(notas_finales_esperadas, notas_finales));
    }

    @Test
    void estudiante_se_copia_en_la_esquina_superior_izquierda() {
        Edr edr_9 = new Edr(d_aula, 9, solucion);
        double[] notas;
        double[] notas_esperadas;

        edr_9.resolver(7,0,0);
        edr_9.resolver(7,1,1);

        edr_9.resolver(5,5,5);
        edr_9.resolver(5,6,6);
        edr_9.resolver(5,7,7);

        edr_9.resolver(1, 0, 0);
        edr_9.resolver(1, 1, 1);
        edr_9.resolver(1, 2, 2);
        edr_9.resolver(1, 3, 3);
        edr_9.resolver(1, 4, 4);

        notas = edr_9.notas();
        notas_esperadas = new double[]{0.0, 50.0, 0.0, 0.0, 0.0, 30.0, 0.0, 20.0, 0.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));

        // chequeo copiarse
        edr_9.copiarse(8);

        notas = edr_9.notas();
        notas_esperadas = new double[]{0.0, 50.0, 0.0, 0.0, 0.0, 30.0, 0.0, 20.0, 10.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));

        // entrega
        for(int alumno = 0; alumno < 9; alumno++) {
            edr_9.entregar(alumno);
        }

        // chequeo chequearCopias, el que se copió no va a ser sospechoso porque no cumple con el porcentaje
        int[] copiones = edr_9.chequearCopias();
        int[] copiones_esperados = new int[]{};
        assertTrue(Arrays.equals(copiones_esperados,copiones));

        // chequeo corregir
        NotaFinal[] notas_finales = edr_9.corregir();
        NotaFinal[] notas_finales_esperadas = new NotaFinal[]{
            new NotaFinal(50.0, 1),
            new NotaFinal(30.0, 5),
            new NotaFinal(20.0, 7),
            new NotaFinal(10.0, 8),
            new NotaFinal(0.0, 6),
            new NotaFinal(0.0, 4),
            new NotaFinal(0.0, 3),
            new NotaFinal(0.0, 2),
            new NotaFinal(0.0, 0)
        };

        assertTrue(Arrays.equals(notas_finales_esperadas, notas_finales));
    }

    @Test
    void alumno_copia_al_del_frente() {
        edr.resolver(1,0,0);
        double[] notas = edr.notas();
        double[] notas_esperadas = new double[]{0.0, 10.0, 0.0, 0.0, 0.0, 0.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));

        // El estudiante 4 solo puede copiarse del estudiante 2
        edr.copiarse(4);

        notas = edr.notas();
        notas_esperadas = new double[]{0.0, 10.0, 0.0, 0.0, 10.0, 0.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));
        
    }

    @Test 
    void mismas_notas_dark_web(){

        edr.resolver(0,0,0);
        edr.resolver(1, 0, 0);

        for (int estudiante = 2; estudiante < 4; estudiante++) {
            for (int pregunta = 0; pregunta < 2; pregunta ++)
            edr.resolver(estudiante, pregunta, pregunta);
        }

        for (int estudiante = 4; estudiante < 6; estudiante++) {
            for (int pregunta = 0; pregunta < 3; pregunta ++)
                edr.resolver(estudiante, pregunta, pregunta);
        }
        double[] notas = edr.notas();
        double[] notas_esperadas = new double[]{10.0, 10.0, 20.0, 20.0, 30.0, 30.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));
        
        edr.consultarDarkWeb(3, solucion);

        notas = edr.notas();
        notas_esperadas = new double[]{
            100.0,
            10.0,
            100.0,
            20.0,
            100.0,
            30.0
        };
        
    }

    @Test
    void todos_sospechosos_excepto_uno(){
        Edr edr_12 = new Edr(5, 12, solucion);
        edr_12.resolver(11, 0,0 );
        double[] notas = edr_12.notas();
        double[] notas_esperadas = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 10.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));

        edr_12.consultarDarkWeb(11, solucion);
        notas = edr_12.notas();
        notas_esperadas = new double[]{100.0, 100.0, 100.0, 100.0, 100.0, 100.0, 100.0, 100.0, 100.0, 100.0, 100.0, 10.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));

        edr_12.resolver(11, 0, 1);
        notas = edr_12.notas();
        notas_esperadas = new double[]{100.0, 100.0, 100.0, 100.0, 100.0, 100.0, 100.0, 100.0, 100.0, 100.0, 100.0, 0.0};

        for (int estudiante = 0; estudiante < 12; estudiante++) {
            edr_12.entregar(estudiante);
        }

        int[] copiones = edr_12.chequearCopias();
        int[] copiones_esperados = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        assertTrue(Arrays.equals(copiones_esperados, copiones));
        
        NotaFinal[] corregidos = edr_12.corregir();
        NotaFinal[] corregidos_esperados = new NotaFinal[]{
            new NotaFinal(0.0, 11)
        };
        assertTrue(Arrays.equals(corregidos_esperados,corregidos));

    }
    
    @Test
    void alumno_en_primera_fila() {
        double[] notas;
        double[] notas_esperadas;

        edr.resolver(0, 0, 0);
        edr.resolver(1, 2, 1);

        notas = edr.notas();
        notas_esperadas = new double[]{10.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));

        edr.copiarse(1);

        notas = edr.notas();
        notas_esperadas = new double[]{10.0, 10.0, 0.0, 0.0, 0.0, 0.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));

        for(int alumno = 0; alumno < 6; alumno ++){
            edr.entregar(alumno);
        }

        int[] copiones = edr.chequearCopias();
        int[] copiones_esperados = new int[]{};
        assertTrue(Arrays.equals(copiones_esperados, copiones));
    }


    @Test
    void se_copian_todos() {
        double[] notas;
        double[] notas_esperadas;

        for(int pregunta = 0; pregunta < 5; pregunta ++){
            edr.resolver(0, pregunta, 0);
            edr.resolver(1, pregunta, 0);
            edr.resolver(2, pregunta, 0);
            edr.resolver(3, pregunta, 0);
            edr.resolver(4, pregunta, 0);
            edr.resolver(5, pregunta, 0);
        }

        notas = edr.notas();
        notas_esperadas = new double[]{10.0, 10.0, 10.0, 10.0, 10.0, 10.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));

        for(int alumno = 0; alumno < 6; alumno ++){
            edr.entregar(alumno);
        }

        int[] copiones = edr.chequearCopias();
        int[] copiones_esperados = new int[]{0,1,2,3,4,5};
        assertTrue(Arrays.equals(copiones_esperados, copiones));

        NotaFinal[] notas_finales = edr.corregir();
        NotaFinal[] notas_finales_esperadas = new NotaFinal[]{};

        assertTrue(Arrays.equals(notas_finales_esperadas, notas_finales));
    }


    @Test
    void no_es_copion() {
        double[] notas;
        double[] notas_esperadas;

        for (int pregunta = 0; pregunta < 9; pregunta ++){
            edr.resolver(0, pregunta, 0);
        }
        edr.resolver(1, 0, 0);

        notas = edr.notas();
        notas_esperadas = new double[]{10.0, 10.0, 0.0, 0.0, 0.0, 0.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));

        for(int alumno = 0; alumno < 6; alumno ++){
            edr.entregar(alumno);
        }

        int[] copiones = edr.chequearCopias();
        int[] copiones_esperados = new int[]{};
        assertTrue(Arrays.equals(copiones_esperados, copiones));
    }

}