package aed;

public class Estudiante implements Comparable<Estudiante> {
    private int _id;
    private double _nota;
    private Integer[] _resExamen;
    private boolean _estudianteEntrego;
    private int _cantRespuestasCorrectas;
    private boolean _estudianteCopio;

    Estudiante(int id, double nota, Integer[] resExamen , boolean estudianteEntrego , int cantRespuestas , boolean estudianteCopio){
        _id = id;
        _nota = nota;
        _resExamen = resExamen;
        _estudianteEntrego = estudianteEntrego;
        _cantRespuestasCorrectas = cantRespuestas;
        _estudianteCopio = estudianteCopio;
    }
    Estudiante (Estudiante otro) {
        _id = otro._id;
        _resExamen = otro._resExamen;
        _estudianteEntrego = otro._estudianteEntrego;
        _cantRespuestasCorrectas = otro._cantRespuestasCorrectas;
        _estudianteCopio = otro._estudianteCopio;
    }

    public int idEstudiante(){
        return this._id;
    }

    public double notaEstudiante(){
        return this._nota;
    }

    public int respuestasCorrectasEstudiante(){
        return this._cantRespuestasCorrectas;
    }

    public void cambiarCantRespuestasCorrectas(int resuelto){
        _cantRespuestasCorrectas += resuelto;
    }

    public void actualizarNota(){
        double ratio = (double) _cantRespuestasCorrectas / _resExamen.length;
        _nota = ratio * 100;

    }

    public Integer[] examenEstudiante(){
        return this._resExamen;
    }
    public Integer obtenerRespuesta(int pregunta) {
        return _resExamen[pregunta];
    }

    public void cambiarRespuesta(int pregunta, int respuesta){
        _resExamen[pregunta] = respuesta;
    }

    public boolean entregoExamen(){
        return this._estudianteEntrego;
    }

    public void entregarExamen(){
        _estudianteEntrego = true;
    }

    public boolean examenCopiado(){
        return this._estudianteCopio;
    }

    public void esCopion (boolean valor) {
        this._estudianteCopio = valor;
    }


    @Override
    public int compareTo (Estudiante otroEstudiante){

        if(this._nota != otroEstudiante._nota){
            return (int) (this._nota - otroEstudiante._nota);
        }else{
            return this._id - otroEstudiante._id;
        }
    }

    @Override
    public String toString(){
        return String.valueOf(_nota);
    }
}
