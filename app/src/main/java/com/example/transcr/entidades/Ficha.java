package com.example.transcr.entidades;

public class Ficha {
    private int id;
    private String Placa;
    private int extintor;
    private int capaDeChuva;
    private int Antena;
    private int Radio;
    private int Lona;
    private int ColeteRefletivo;
    private int Xrefletivo;
    private int parDeluvas;
    private int capacete;
    private int GuardaChuva;
    private int MarteloMadeira;
    private int Documentos;
    private int Acendedor;
    private int CabosForca;
    private int macaco;
    private int ferroMacaco;
    private int triangulo;
    private int ChaveRodas;
    private int Tacografo;
    private int tapetes;
    private int caboAco;
    private int catracas;
    private int catracaMovel;
    private int cordas;
    private int cinta;
    private int qtsChaves;
    private int lanterna;
    private int KitEmergenciaG;
    private int KitEmergenciaP;

    public Ficha(){

    }
    public Ficha(int id,String Placa,int extintor,int capaDeChuva,int Antena,
                 int Radio,int Lona,int ColeteRefletivo,
                 int Xrefletivo,int parDeluvas,int capacete,int GuardaChuva,int MarteloMadeira,
                 int Documentos,int Acendedor,int CabosForca,int macaco,int ferroMacaco,int triangulo,
                 int ChaveRodas,int Tacografo,int tapetes,int caboAco,int catracas,int catracaMovel,
                 int cordas,int cinta,int qtsChaves,int lanterna,int KitEmergenciaG,int KitEmergenciaP){
        this.id = id;
        this.Placa = Placa;
        this.extintor = extintor;
        this.capaDeChuva=capaDeChuva;
        this.Antena=Antena;
        this.Radio=Radio;
        this.Lona=Lona;
        this.ColeteRefletivo=ColeteRefletivo;
        this.Xrefletivo=Xrefletivo;
        this.parDeluvas=parDeluvas;
        this.capacete=capacete;
        this.GuardaChuva=GuardaChuva;
        this.MarteloMadeira=MarteloMadeira;
        this.Documentos=Documentos;
        this.Acendedor=Acendedor;
        this.CabosForca=CabosForca;
        this.macaco=macaco;
        this.ferroMacaco=ferroMacaco;
        this.triangulo=triangulo;
        this.ChaveRodas=ChaveRodas;
        this.Tacografo=Tacografo;
        this.tapetes=tapetes;
        this.caboAco=caboAco;
        this.catracas=catracas;
        this.catracaMovel=catracaMovel;
        this.cordas=cordas;
        this.cinta=cinta;
        this.qtsChaves=qtsChaves;
        this.lanterna=lanterna;
        this.KitEmergenciaG=KitEmergenciaG;
        this.KitEmergenciaP=KitEmergenciaP;
    }
    public void setSuporteP( int KitEmergenciaP){
        this.KitEmergenciaP=KitEmergenciaP;
    }
    public void setSuporteG( int KitEmergenciaG){
        this.KitEmergenciaG=KitEmergenciaG;
    }
    public void setLanterna( int lanterna){
        this.lanterna=lanterna;
    }
    public void setqtsChaves( int qtsChaves){
        this.qtsChaves=qtsChaves;
    }
    public void setCinta( int cinta){
        this.cinta=cinta;
    }
    public void setCordas( int cordas){
        this.cordas=cordas;
    }
    public void setcatracaMovel( int catracaMovel){
        this.catracaMovel=catracaMovel;
    }
    public void setCatraca(int catracas){
        this.catracas=catracas;
    }
    public void setcaboAco( int caboAco){
        this.caboAco=caboAco;
    }
    public void setTapetes( int tapetes){
        this.tapetes=tapetes;
    }
    public void setTacografo( int Tacografo){
        this.Tacografo=Tacografo;
    }
    public void setChaveRodas( int ChaveRodas){
        this.ChaveRodas=ChaveRodas;
    }
    public void setTriangulo( int triangulo){
        this.triangulo=triangulo;
    }
    public void setferroMacaco( int ferroMacaco){
        this.ferroMacaco=ferroMacaco;
    }
    public void setMacaco( int macaco){
        this.macaco=macaco;
    }
    public void setCabosForca( int CabosForca){
        this.CabosForca=CabosForca;
    }
    public void setAcendedor( int Acendedor){
        this.Acendedor=Acendedor;
    }
    public void setDocumentos( int Documentos){
        this.Documentos=Documentos;
    }
    public void setMarteloMadeira( int MarteloMadeira){
        this.MarteloMadeira=MarteloMadeira;
    }
    public void setGuardaChuva( int GuardaChuva){
        this.GuardaChuva=GuardaChuva;
    }
    public void setCapacete( int capacete){
        this.capacete=capacete;
    }
    public void setparDeluvas( int parDeluvas){
        this.parDeluvas=parDeluvas;
    }
    public void setXrefletivo( int Xrefletivo){
        this.Xrefletivo=Xrefletivo;
    }
    public void setColeteRefletivo( int ColeteRefletivo){
        this.ColeteRefletivo=ColeteRefletivo;
    }
    public void setLona( int Lona){
        this.Lona=Lona;
    }
    public void setRadio( int Radio){
        this.Radio=Radio;
    }
    public void setAntena( int Antena){
        this.Antena=Antena;
    }
    public void setCapaDeChuva( int capaDeChuva){
        this.capaDeChuva=capaDeChuva;
    }
    public void setExtintor( int extintor){
        this.extintor = extintor;
    }
    public void setPlaca( String Placa){
        this.Placa = Placa;
    }
    public void setId( int id){
        this.id = id;
    }
    public String toString(){
        return Placa;
    }

}
