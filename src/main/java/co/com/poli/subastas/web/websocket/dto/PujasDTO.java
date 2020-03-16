package co.com.poli.subastas.web.websocket.dto;

public class PujasDTO {

    private String idEvento;
    private String idSubasta;
    private String idLote; 
    private String valor;
    private String token;

    public PujasDTO(String idEvento, String idSubasta, String idLote, String valor) {
        this.idEvento = idEvento;
        this.idSubasta = idSubasta;
        this.idLote = idLote;
        this.valor = valor;
    }

    public PujasDTO() {
    }

    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }

    public String getIdSubasta() {
        return idSubasta;
    }

    public void setIdSubasta(String idSubasta) {
        this.idSubasta = idSubasta;
    }

    public String getIdLote() {
        return idLote;
    }

    public void setIdLote(String idLote) {
        this.idLote = idLote;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}