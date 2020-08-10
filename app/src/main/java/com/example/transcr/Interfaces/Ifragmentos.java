package com.example.transcr.Interfaces;

import com.example.transcr.Fragmentos.ConsultaList_Carretas;
import com.example.transcr.Fragmentos.ConsultaList_Fragment;
import com.example.transcr.Fragmentos.ConsultarFrota;
import com.example.transcr.Fragmentos.CriarVistoria;
import com.example.transcr.Fragmentos.CriarVistoriaCarreta;
import com.example.transcr.Fragmentos.CriarVistoriaCarreta_40;
import com.example.transcr.Fragmentos.CriarVistoriaCarreta_Bug20;
import com.example.transcr.Fragmentos.CriarVistoriaCarreta_Sider;
import com.example.transcr.Fragmentos.CriarVistoriaSprinterF;
import com.example.transcr.Fragmentos.CriarVistoria_Caminhao;
import com.example.transcr.Fragmentos.CriarVistoria_Carroceria;
import com.example.transcr.Fragmentos.CriarVistoria_Cavalo;
import com.example.transcr.Fragmentos.CriarVistoria_Sprinter;
import com.example.transcr.Fragmentos.EscolherCaminhao_Avarias;
import com.example.transcr.Fragmentos.EscolherCarretas_Avarias;
import com.example.transcr.Fragmentos.Parte2_CheckList_Caminhao;
import com.example.transcr.Fragmentos.RegistrarCarretas;
import com.example.transcr.Fragmentos.RegistrarFrota;
import com.example.transcr.Fragmentos.TipoDeCarreta;
import com.example.transcr.Fragmentos.TipoDeVistoria;
import com.example.transcr.ShowAvariasImages.EscolhaTipoVeiculo_Carretas;
import com.example.transcr.ShowAvariasImages.EscolherCaminhao_Avarias2;
import com.example.transcr.ShowAvariasImages.EscolherCarretas_Avarias2;

public interface Ifragmentos extends
        RegistrarFrota.OnFragmentInteractionListener,
        RegistrarCarretas.OnFragmentInteractionListener,
        ConsultarFrota.OnFragmentInteractionListener,
        ConsultaList_Fragment.OnFragmentInteractionListener,
        ConsultaList_Carretas.OnFragmentInteractionListener,
        CriarVistoria.OnFragmentInteractionListener,
        TipoDeVistoria.OnFragmentInteractionListener,
        TipoDeCarreta.OnFragmentInteractionListener,
        CriarVistoriaSprinterF.OnFragmentInteractionListener,
        CriarVistoria_Sprinter.OnFragmentInteractionListener,
        CriarVistoria_Carroceria.OnFragmentInteractionListener,
        CriarVistoria_Caminhao.OnFragmentInteractionListener,
        CriarVistoria_Cavalo.OnFragmentInteractionListener,
        CriarVistoriaCarreta.OnFragmentInteractionListener,
        CriarVistoriaCarreta_Sider.OnFragmentInteractionListener,
        CriarVistoriaCarreta_40.OnFragmentInteractionListener,
        CriarVistoriaCarreta_Bug20.OnFragmentInteractionListener,
        EscolherCaminhao_Avarias.OnFragmentInteractionListener,
        EscolherCarretas_Avarias.OnFragmentInteractionListener,
        EscolhaTipoVeiculo_Carretas.OnFragmentInteractionListener,
        EscolherCaminhao_Avarias2.OnFragmentInteractionListener,
        EscolherCarretas_Avarias2.OnFragmentInteractionListener,
        Parte2_CheckList_Caminhao.OnFragmentInteractionListener
{
}

