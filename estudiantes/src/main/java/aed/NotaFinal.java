package aed;

public class NotaFinal implements Comparable<NotaFinal> {
    public double _nota;
    public int _id;

    public NotaFinal(double nota, int id){
        _nota = nota;
        _id = id;
    }

    public int compareTo(NotaFinal otra){
        if (this._nota != otra._nota){
            return Double.compare(this._nota, otra._nota);
        }
        return otra._id - this._id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        NotaFinal otra = (NotaFinal) obj;

        return this._id == otra._id && this._nota == otra._nota;
    }
}
