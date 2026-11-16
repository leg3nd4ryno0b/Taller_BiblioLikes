import java.time.Year;
import java.util.ArrayList;

public class CatalogoLibros {
    private ArrayList<Libro> libros;
    public CatalogoLibros(){
        libros=DatosIniciales.cargar();
        ordenarPorCodigoBurbuja();
    }

    public void ordenarPorCodigoBurbuja(){
        for(int i=0;i<libros.size()-1;i++){
            for(int j=0;j<libros.size()-1-i;j++){
                if(libros.get(j).getCodigo()>libros.get(j+1).getCodigo()){
                    Libro aux=libros.get(j);
                    libros.set(j,libros.get(j+1));
                    libros.set(j+1,aux);
                }
            }
        }
    }

    public int buscarIndicePorCodigo(int codigo) throws Exception {
        if(codigo<=0)
            throw new Exception("El codigo debe ser positivo");
        int tam=libros.size()-1;
        if(codigo<libros.get(0).getCodigo()
          ||codigo>libros.get(tam).getCodigo())
            return -1;
        int i=0;
        int s=tam;
        int c;
        while(i<=s){
            c=(i+s)/2;
            int valor=libros.get(c).getCodigo();
            if(codigo==valor){
                return c;
            }else if(codigo>valor){
                i=c+1;
            }else{
                s=c-1;
            }
        }
        return -1;
    }

    private void validar(String titulo,String autor,String genero,int anio) throws Exception {
        if(titulo.trim().isEmpty())
            throw new Exception("El titulo es obligatorio");
        if(autor.trim().isEmpty())
            throw new Exception("El autor es obligatorio");
        if(genero==null||genero.trim().isEmpty())
            throw new Exception("El genero es obligatorio");
        if(anio<1||anio>Year.now().getValue())
            throw new Exception("El año debe estar entre 1 y "+Year.now().getValue());
    }

    public void registrar(Libro libro) throws Exception {
        if(libro.getCodigo()<=0)
            throw new Exception("El codigo debe ser positivo");
        validar(libro.getTitulo(),libro.getAutor(),libro.getGenero(),libro.getAnio());
        if(buscarIndicePorCodigo(libro.getCodigo())!=-1)
            throw new Exception("Ya existe un libro con ese codigo");
        libros.add(libro);
        ordenarPorCodigoBurbuja();
    }

    public ArrayList<Libro> buscarCoincidencias(String texto) throws Exception {
        if(texto.trim().isEmpty())
            throw new Exception("Ingrese un texto para buscar");
        String t=texto.trim().toLowerCase();
        ArrayList<Libro> resultado=new ArrayList<>();
        for(Libro aux:libros){
            if(aux.getTitulo().toLowerCase().contains(t)
              ||aux.getAutor().toLowerCase().contains(t)){
                resultado.add(aux);
            }
        }
        return resultado;
    }

    public void editarLibro(int codigo,String titulo,String autor,String genero,int anio) throws Exception {
        int indice=buscarIndicePorCodigo(codigo);
        if(indice==-1)
            throw new Exception("Libro no encontrado");
        validar(titulo,autor,genero,anio);
        Libro aux=libros.get(indice);
        aux.setTitulo(titulo.trim());
        aux.setAutor(autor.trim());
        aux.setGenero(genero);
        aux.setAnio(anio);
    }

    public void darMeGusta(int codigo) throws Exception {
        int indice=buscarIndicePorCodigo(codigo);
        if(indice==-1)
            throw new Exception("Libro no encontrado");
        libros.get(indice).incrementarMeGusta();
    }

    public ArrayList<Libro> obtenerRanking(){
        ArrayList<Libro> ranking=new ArrayList<>(libros);
        for(int i=0;i<ranking.size()-1;i++){
            for(int j=0;j<ranking.size()-1-i;j++){
                if(ranking.get(j).getMeGusta()<ranking.get(j+1).getMeGusta()){
                    Libro aux=ranking.get(j);
                    ranking.set(j,ranking.get(j+1));
                    ranking.set(j+1,aux);
                }
            }
        }
        return ranking;
    }

    public ArrayList<Libro> obtener(){
        return libros;
    }

    public int size(){
        return libros.size();
    }
}
