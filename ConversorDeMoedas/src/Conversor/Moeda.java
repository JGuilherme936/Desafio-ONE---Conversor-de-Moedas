package Conversor;

public record Moeda(String codigo, String nome) {
    public Moeda(String codigo, String nome) {
        this.codigo = codigo.toUpperCase();
        this.nome = nome;
    }

    @Override
    public String toString() {
        return codigo + " - " + nome;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Moeda moeda = (Moeda) obj;
        return codigo.equals(moeda.codigo);
    }

    @Override
    public int hashCode() {
        return codigo.hashCode();
    }
}
