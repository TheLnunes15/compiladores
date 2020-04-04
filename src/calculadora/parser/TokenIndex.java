/* This file was generated by SableCC (http://www.sablecc.org/). */

package calculadora.parser;

import calculadora.node.*;
import calculadora.analysis.*;

class TokenIndex extends AnalysisAdapter
{
    int index;

    @Override
    public void caseTClasse(@SuppressWarnings("unused") TClasse node)
    {
        this.index = 0;
    }

    @Override
    public void caseTFilhaDaClasse(@SuppressWarnings("unused") TFilhaDaClasse node)
    {
        this.index = 1;
    }

    @Override
    public void caseTComeca(@SuppressWarnings("unused") TComeca node)
    {
        this.index = 2;
    }

    @Override
    public void caseTTermina(@SuppressWarnings("unused") TTermina node)
    {
        this.index = 3;
    }

    @Override
    public void caseTObjeto(@SuppressWarnings("unused") TObjeto node)
    {
        this.index = 4;
    }

    @Override
    public void caseTVariavel(@SuppressWarnings("unused") TVariavel node)
    {
        this.index = 5;
    }

    @Override
    public void caseTConstante(@SuppressWarnings("unused") TConstante node)
    {
        this.index = 6;
    }

    @Override
    public void caseTInteiro(@SuppressWarnings("unused") TInteiro node)
    {
        this.index = 7;
    }

    @Override
    public void caseTBool(@SuppressWarnings("unused") TBool node)
    {
        this.index = 8;
    }

    @Override
    public void caseTReal(@SuppressWarnings("unused") TReal node)
    {
        this.index = 9;
    }

    @Override
    public void caseTIniciaProcdm(@SuppressWarnings("unused") TIniciaProcdm node)
    {
        this.index = 10;
    }

    @Override
    public void caseTProcedimento(@SuppressWarnings("unused") TProcedimento node)
    {
        this.index = 11;
    }

    @Override
    public void caseTParEsquerdo(@SuppressWarnings("unused") TParEsquerdo node)
    {
        this.index = 12;
    }

    @Override
    public void caseTParDireito(@SuppressWarnings("unused") TParDireito node)
    {
        this.index = 13;
    }

    @Override
    public void caseTFuncao(@SuppressWarnings("unused") TFuncao node)
    {
        this.index = 14;
    }

    @Override
    public void caseTSe(@SuppressWarnings("unused") TSe node)
    {
        this.index = 15;
    }

    @Override
    public void caseTSenao(@SuppressWarnings("unused") TSenao node)
    {
        this.index = 16;
    }

    @Override
    public void caseTEntao(@SuppressWarnings("unused") TEntao node)
    {
        this.index = 17;
    }

    @Override
    public void caseTEnquanto(@SuppressWarnings("unused") TEnquanto node)
    {
        this.index = 18;
    }

    @Override
    public void caseTVerdadeiro(@SuppressWarnings("unused") TVerdadeiro node)
    {
        this.index = 19;
    }

    @Override
    public void caseTFalso(@SuppressWarnings("unused") TFalso node)
    {
        this.index = 20;
    }

    @Override
    public void caseTE(@SuppressWarnings("unused") TE node)
    {
        this.index = 21;
    }

    @Override
    public void caseTOu(@SuppressWarnings("unused") TOu node)
    {
        this.index = 22;
    }

    @Override
    public void caseTEComercial(@SuppressWarnings("unused") TEComercial node)
    {
        this.index = 23;
    }

    @Override
    public void caseTPontoVirgula(@SuppressWarnings("unused") TPontoVirgula node)
    {
        this.index = 24;
    }

    @Override
    public void caseTVirgula(@SuppressWarnings("unused") TVirgula node)
    {
        this.index = 25;
    }

    @Override
    public void caseTInicializacao(@SuppressWarnings("unused") TInicializacao node)
    {
        this.index = 26;
    }

    @Override
    public void caseTAtribuicao(@SuppressWarnings("unused") TAtribuicao node)
    {
        this.index = 27;
    }

    @Override
    public void caseTMenos(@SuppressWarnings("unused") TMenos node)
    {
        this.index = 28;
    }

    @Override
    public void caseTMais(@SuppressWarnings("unused") TMais node)
    {
        this.index = 29;
    }

    @Override
    public void caseTMult(@SuppressWarnings("unused") TMult node)
    {
        this.index = 30;
    }

    @Override
    public void caseTDiv(@SuppressWarnings("unused") TDiv node)
    {
        this.index = 31;
    }

    @Override
    public void caseTMod(@SuppressWarnings("unused") TMod node)
    {
        this.index = 32;
    }

    @Override
    public void caseTComp(@SuppressWarnings("unused") TComp node)
    {
        this.index = 33;
    }

    @Override
    public void caseTMenor(@SuppressWarnings("unused") TMenor node)
    {
        this.index = 34;
    }

    @Override
    public void caseTNegacao(@SuppressWarnings("unused") TNegacao node)
    {
        this.index = 35;
    }

    @Override
    public void caseTPonto(@SuppressWarnings("unused") TPonto node)
    {
        this.index = 36;
    }

    @Override
    public void caseTNumInteiro(@SuppressWarnings("unused") TNumInteiro node)
    {
        this.index = 37;
    }

    @Override
    public void caseTNumReal(@SuppressWarnings("unused") TNumReal node)
    {
        this.index = 38;
    }

    @Override
    public void caseTIdentificador(@SuppressWarnings("unused") TIdentificador node)
    {
        this.index = 39;
    }

    @Override
    public void caseTClIdentificador(@SuppressWarnings("unused") TClIdentificador node)
    {
        this.index = 40;
    }

    @Override
    public void caseEOF(@SuppressWarnings("unused") EOF node)
    {
        this.index = 41;
    }
}