package s5t2.DadosMongo.dto;

public class Resultado implements Comparable<Resultado> {

    private String nombre;
    private double probExito;

    public Resultado(String nombre, double probExito){
        this.nombre = nombre;
        this.probExito = probExito;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getProbExito() {
        return probExito;
    }

    public void setProbExito(double probExito) {
        this.probExito = probExito;
    }

    @Override
    public int compareTo(Resultado resultado) {
        return Double.compare( probExito, resultado.getProbExito() );
    }

}
