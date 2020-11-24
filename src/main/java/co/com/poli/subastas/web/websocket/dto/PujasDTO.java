package co.com.poli.subastas.web.websocket.dto;

public class PujasDTO {

    private String idEventos;
    private String idSubasta;
    private String idLote; 
    private String valor;
    private String token;
    private String sessionId;
    private String dispositivoId;

    public PujasDTO(String idEvento, String idSubasta, String idLote, String valor) {
        this.idEventos = idEvento;
        this.idSubasta = idSubasta;
        this.idLote = idLote;
        this.valor = valor;
    }

    public PujasDTO() {
    }

    public String getIdEventos() {
        return idEventos;
    }

    public void setIdEventos(String idEvento) {
        this.idEventos = idEvento;
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

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getDispositivoId() {
        return dispositivoId;
    }

    public void setDispositivoId(String dispositivoId) {
        this.dispositivoId = dispositivoId;
    }

    @Override
    public String toString() {
        return "PujasDTO{" + "idEvento=" + idEventos + ", idSubasta=" + idSubasta + ", idLote=" + idLote + ", valor=" + valor + ", token=" + token + ", sessionId=" + sessionId + ", dispositivoId=" + dispositivoId + '}';
    }
    
    
}