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

    public ArrayList<Libro> obtener(){
        return libros;
    }

    public int size(){
        return libros.size();
    }
}
