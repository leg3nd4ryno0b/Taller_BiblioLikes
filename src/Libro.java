public class Libro {
    private final int codigo;
    private String titulo;
    private String autor;
    private String genero;
    private int anio;
    private int meGusta;

    public Libro(int codigo, String titulo, String autor, String genero, int anio, int meGusta) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.anio = anio;
        this.meGusta = meGusta;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getMeGusta() {
        return meGusta;
    }

    public void incrementarMeGusta() {
        meGusta++;
    }

    @Override
    public String toString() {
        return codigo + " | " + titulo + " | " + autor + " | Me gusta: " + meGusta;
    }
}
