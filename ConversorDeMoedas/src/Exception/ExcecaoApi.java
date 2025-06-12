package Exception;

public class ExcecaoApi extends Exception{
    public ExcecaoApi(String mensagem) {
        super(mensagem);
    }

    public ExcecaoApi(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
