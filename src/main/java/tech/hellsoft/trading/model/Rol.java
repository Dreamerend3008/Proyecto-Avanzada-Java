package tech.hellsoft.trading.model;

import java.io.Serializable;

public class Rol implements Serializable {
    private static final long serialVersionUID = 1L;

    private int branches;
    private int maxDepth;
    private double decay;
    private double baseEnergy;
    private double levelEnergy;

    public Rol(){
    }

    public Rol(int branches, int maxDepth, double decay, double baseEnergy, double levelEnergy) {
        this.branches = branches;
        this.maxDepth = maxDepth;
        this.decay = decay;
        this.baseEnergy = baseEnergy;
        this.levelEnergy = levelEnergy;
    }

    public double getBaseEnergy() {
        return baseEnergy;
    }

    public void setBaseEnergy(double baseEnergy) {
        this.baseEnergy = baseEnergy;
    }

    public int getBranches() {
        return branches;
    }

    public void setBranches(int branches) {
        this.branches = branches;
    }

    public double getDecay() {
        return decay;
    }

    public void setDecay(double decay) {
        this.decay = decay;
    }

    public double getLevelEnergy() {
        return levelEnergy;
    }

    public void setLevelEnergy(double levelEnergy) {
        this.levelEnergy = levelEnergy;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    @Override
    public String toString() {
        return String.format("Rol{branches=%d, maxDepth=%d, decay=%.4f, baseEnergy=%.1f, levelEnergy=%.1f}",
                branches, maxDepth, decay, baseEnergy, levelEnergy);
    }
}
