package tech.hellsoft.trading.exception.configuracion;

public class SnapshotCorruptoException extends ConfiguracionException {

    private final String archivo;

    public SnapshotCorruptoException(String archivo, String razon) {
        super(String.format("Snapshot corrupto: %s. Razón: %s", archivo, razon));
        this.archivo = archivo;
    }

    public SnapshotCorruptoException(String archivo, Throwable causa) {
        super(String.format("Snapshot corrupto: %s. Causa: %s", archivo, causa.getMessage()), causa);
        this.archivo = archivo;
    }

    public String getArchivo() {
        return archivo;
    }
}

