package tech.hellsoft.trading.util;


import tech.hellsoft.trading.model.Rol;

public class CalculadoraProduccion {

    public static int calcularUnidades(Rol rol){
        return calcularRecursivo(0,rol);
    }
    private static int calcularRecursivo(int nivel, Rol rol){
        if(nivel > rol.getMaxDepth()){
            return 0;
        }
        double energia = rol.getBaseEnergy() + (rol.getLevelEnergy() * nivel);
        double decay = Math.pow(rol.getDecay(), nivel);
        double branches = Math.pow(rol.getBranches(), nivel);
        double factor = decay * branches;

        int contribucion = (int) Math.round(energia * factor);

        return contribucion + calcularRecursivo(nivel+1,rol);
    }

    public static int aplicarBonusPremium(int unidadesBase, double bonus){
        return (int) Math.round(unidadesBase * bonus);
    }
}