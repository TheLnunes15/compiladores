package calculadora;

import calculadora.analysis.DepthFirstAdapter;
import calculadora.node.*;
import java.util.*;

public class AnaliseSemantica extends DepthFirstAdapter {
	
	//Tabela de Símbolos
    private TabeladeSimbolos table = new TabeladeSimbolos();
        
    //Classe para pegar o número da linha dado um "node"
    LineNumbers lineNumbers = null;
        
    public AnaliseSemantica(LineNumbers lineNumbers) {
        this.lineNumbers = lineNumbers;
    }
	
	//Verificar elementos da tabela pelo Main
	public TabeladeSimbolos getTable() {
		return table;
	}
	
	@Override
	public void inStart(Start node)
	{
		System.out.println("-------------------------------------------------");
		System.out.println("Iniciando análise semântica...");
		System.out.println("-------------------------------------------------\n");
	}

	@Override
	public void outStart(Start node)
	{
	    System.out.println("\n-------------------------------------------------");
	    System.out.println("Fim da análise semântica");
	    System.out.println("-------------------------------------------------");
	}
	 
   /*Função para imprimir os campos da tabela. Eh soh pra checar mesmo.*/
	public void imprimeTable() {
	    System.out.println(table.getIds());
	    System.out.println(table.getTipos());
	    System.out.println(table.getValues());
	    System.out.println(table.getTams());
	    System.out.println(table.getPosicoes());	            
	}
	
	@Override
	public void inAProgramaPrograma(AProgramaPrograma node) {
        String classe = "_IO ";
        String key = classe.toString().replace(" ", "");
		table.insert(key, "print", "_IO", "0" , "procdm");
		table.insert(key, "read", "_IO", "0" , "funcao");
	//	imprimeTable();		
	}
	
	@Override
	public void inADefClasseDefClasse(ADefClasseDefClasse node)
	{
		LinkedList<PDec> ident = node.getEsq();
        String key = ident.toString().replace(" ", "");
		table.insert(key, node.getEsq().toString().replace(" ", ""), type(node.getEsq().toString().replace(" ", "")), "0", "classe");
	//	imprimeTable();
	}
	
	@Override
	public void inADecFuncaoDeclara(ADecFuncaoDeclara node)
	{
		PTipo ident = node.getEsq();
        String key = ident.toString().replace(" ", "");
		table.insert(key, node.getEsq().toString().replace(" ", ""), type(node.getEsq().toString().replace(" ", "")), "0", "funcao");
	//	imprimeTable();
	}
	
	@Override
	public void caseADecFuncaoDeclara(ADecFuncaoDeclara node)
	{
		inADecFuncaoDeclara(node);
		if (node.getDir() != null)
		{
			node.getDir().apply(this);
		}
		outADecFuncaoDeclara(node);
	}
	
	@Override
	public void caseADecProcedimentoDeclara(ADecProcedimentoDeclara node)
	{
		inADecProcedimentoDeclara(node);
		if (node.getDir() != null)
		{
			node.getDir().apply(this);
		}
		outADecProcedimentoDeclara(node);
	}
	
	@Override
	public void inADecProcedimentoDeclara(ADecProcedimentoDeclara node)
	{
		LinkedList<PParametro> ident = node.getEsq();
        String key = ident.toString().replace(" ", "");
		table.insert(key, node.getEsq().toString().replace(" ", ""), type(node.getEsq().toString().replace(" ", "")), "0", "procdm");
	//	imprimeTable();
	}
	
	@Override
	public void caseADeclaraVariavelDec(ADeclaraVariavelDec node)
	{
		inADeclaraVariavelDec(node);
		outADeclaraVariavelDec(node);
	}

    @Override
    public void outADeclaraVariavelDec(ADeclaraVariavelDec node) {        
        /*Pega o tipo do nó e o transforma em String*/
        PTipo tipo_noh = node.getEsq();
        String tipo = tipo_noh.toString().replace(" ", "");
        
        /*Checa se o tipo faz parte dos tipos existentes na linguagem */
        if (!tipo.equals("int") && !tipo.equals("real") && !tipo.equals("bool"))
            System.err.println("[Linha " + lineNumbers.getLine(node)+"] Erro Semântico: Tipo não existente.");
        
        /*Se o tipo é válido, entra nesse else*/
        else {
            /*Checa variável por variável para saber se ela já foi declarada*/
            LinkedList<PIdentifica> ident = node.getDir();
            for (PIdentifica pVar : ident) {
                if(pVar instanceof AIdentificadorIdentifica) {
                    String key = ((AIdentificadorIdentifica) pVar).getIdentificador().toString().replace(" ", "");
                    if (table.contains(key)) { // report an error
                       System.err.println("[Linha " + lineNumbers.getLine(node)+"] Erro Semântico: Variável '" + key + "' já foi definida.");
                    }
                    else {
                        table.insert(key, null, tipo, "0", "v");
                    }                    
                }
            }            
        }
      // imprimeTable();
    }
    
	@Override
	public void caseAIdentificadorIdentifica(AIdentificadorIdentifica node)
	{	
		inAIdentificadorIdentifica(node);
        outAIdentificadorIdentifica(node);
	}
	
	@Override
	public void caseADeclaraConstanteDec(ADeclaraConstanteDec node)
	{
		inADeclaraConstanteDec(node);
		outADeclaraConstanteDec(node);
	}
    
    @Override
    public void outADeclaraConstanteDec(ADeclaraConstanteDec node) {
        /*Verifica se uma constante ja foi declarada*/
        PTipoPrimitivo ident = node.getEsq();
        String key = ident.toString().replace(" ", "");
        if (table.contains(key)) { // report an error
            System.err.println("[Linha " + lineNumbers.getLine(node)+"] Erro Semântico: Constante '" + key + "' já foi definida.");
        }
        else {
            table.insert(key, node.getEsq().toString().replace(" ", ""), type(node.getEsq().toString().replace(" ", "")), "0", "cons");
        }
     // imprimeTable();
    }
    
	@Override
	public void caseADeclaraObjDec(ADeclaraObjDec node)
	{
		inADeclaraObjDec(node);
		outADeclaraObjDec(node);
	}
    
    @Override
    public void outADeclaraObjDec(ADeclaraObjDec node) {
        /*Verifica se um objeto ja foi declarado*/
    	LinkedList<PIdentifica> ident = node.getIdentifica();
        String key = ident.toString().replace(" ", "");
        if (table.contains(key)) { // report an error
            System.err.println("[Linha " + lineNumbers.getLine(node)+"] Erro Semântico: Objeto '" + key + "' já foi definido.");
        }
        else {
            table.insert(key, node.getIdentifica().toString().replace(" ", ""), type(node.getIdentifica().toString().replace(" ", "")), "0", "obj");
        }
     // imprimeTable();
    }
    
	@Override
	public void caseAIdAtributoExp(AIdAtributoExp node)
	{
		inAIdAtributoExp(node);
		outAIdAtributoExp(node);
	}	
    
    @Override
    public void outAAtributoAtributo(AAtributoAtributo node) {
        /*Pega a id que está recebendo uma atribuição e transforma ela em String*/
        LinkedList<PExp> ident = node.getExp();
        String key = ident.toString().replace(" ", "");        

        /*Se a id foi declarada, então checa agora se é "constante", pois se for o seu valor não pode ser alterado*/
        if (table.getKeyPos(key).equals("cons")) {
            System.err.println("[Linha " + lineNumbers.getLine(node)+"] Erro Semântico: Não é possível alterar o valor de uma constante.");
            
        /*Se a id existir e não for constante, entra nesse else*/    
        } else {
            /*Pega o lado direito da atribuição, isto é, a expressão que está sendo atribuída a id e transforma em String*/
        	LinkedList<PExp> exp = node.getExp();
            String e = exp.toString().replace(" ", "");
             
                /*Verifica se ela realmente foi declarada*/
                if(table.contains(e)) {
                    /*E salva o tipo dessa id de atribuição*/
                    String tipo_var_atribuicao = table.getKeyTipo(e);
                    /*Se os tipos das ids forem iguais, entra aqui*/
                    if (tipo_var_atribuicao.equals(table.getKeyTipo(key))) {
                        /* (*) Se a id de atribuição possui valor diferente de null, atribui seu valor
                        a variavel do lado esquerdo*/
                        if (table.getKeyValue(e) != null) {
                            String val = table.getKeyValue(e);
                            table.insert(key, val, table.getKeyTipo(key), "0", "-1");
                        /*Caso valor dela seja null, atribui null a var do lado esquerdo*/
                        } else table.insert(key, null, table.getKeyTipo(key), "0", "-1");
                        
                    /*Se os tipos não forem iguais, checa se são inteiro e real ao menos.
                        Se sim, o procedimento (*) se repete*/
                    } else if (tipo_var_atribuicao.equals("int") && table.getKeyTipo(key).equals("real")){
                        if (table.getKeyValue(e) != null) {
                            String val = table.getKeyValue(e)+",0";
                            table.insert(key, val, table.getKeyTipo(key), "0", "-1");
                        } else table.insert(key, null, table.getKeyTipo(key), "0", "-1");

                    } else if (tipo_var_atribuicao.equals("real") && table.getKeyTipo(key).equals("int")){
                        if (table.getKeyValue(e) != null) {
                            String[] val = table.getKeyValue(e).split(",");
                            table.insert(key, val[0], table.getKeyTipo(key), "0", "-1");
                        } else table.insert(key, null, table.getKeyTipo(key), "0", "-1");

                    /*Se os tipos não forem iguais ou ao menos númericos de ambas as variáveis, lança um erro*/
                    } else {
                       System.err.println("[Linha " + lineNumbers.getLine(node)+"] Erro Semântico: Atribuição não permitida (variável '"+key+"' é do tipo "+table.getKeyTipo(key)+" e '"+e+"' é do tipo "+tipo_var_atribuicao+").");
                    }  
                }

             /*Se a exp de atribuição não foi uma variável, só pode ser alguma expressão ou chamada que foi salva na tabela*/
                if(!table.contains(e)){
                	System.err.println("[Linha " + lineNumbers.getLine(node)+"] Erro Semântico: Atribuição inválida (a expressão que está sendo atribuída a variável '"+key+"' não é calculável).");
                
                }else {
                    String tipo_valor_atribuicao = table.getKeyTipo(e);
                    if (tipo_valor_atribuicao.equals(table.getKeyTipo(key))) 
                        table.insert(key, table.getKeyValue(e), table.getKeyTipo(key), "0", "-1");

                    else if (tipo_valor_atribuicao.equals("int") && table.getKeyTipo(key).equals("real")){
                        String val = table.getKeyValue(e)+",0";
                        table.insert(key, val, table.getKeyTipo(key), "0", "-1");

                    } else if (tipo_valor_atribuicao.equals("real") && table.getKeyTipo(key).equals("int")){
                        String[] val = table.getKeyValue(e).split(",");
                        table.insert(key, val[0], table.getKeyTipo(key), "0", "-1");

                    } else
                        System.err.println("[Linha " + lineNumbers.getLine(node)+"] Erro Semântico: Atribuição inválida (variável '"+key+"' é do tipo "+table.getKeyTipo(key)+" e variável '"+e+"' é do tipo "+tipo_valor_atribuicao+").");
                }
            }
        //imprimeTable();
    }
    
	@Override
	public void caseAAtributoAtributo(AAtributoAtributo node)
	{
		inAAtributoAtributo(node);
        List<PExp> copy = new ArrayList<PExp>(node.getExp());
        for(PExp e : copy)
        {
			e.apply(this);
		}
		outAAtributoAtributo(node);
	}
    
    /*Função para saber o tipo de um valor*/
    public String type (String s) {
        if(s != null && s.matches("[0-9]+"))
            return "int";  
        else if (s.contains(",") && !s.contains("'"))
            return "real";
        return "bool";
    }
     
    /*A Lógica empregada aqui para a expressão soma foi a mesma para subtração, multiplicação e divisão*/
    @Override
    public void outAMaisExp(AMaisExp node) {
        PExp left = node.getEsq();
        PExp right = node.getDir();
        String tipo_l = null;
        String tipo_r = null;
        boolean ocorreuErro = false;
        
        if(left instanceof PExp) {
            if(!table.contains(((PExp) left).toString().replace(" ", ""))) 
                return;
            else {
                tipo_l = table.getKeyTipo(((PExp) left).toString().replace(" ", ""));
                String val = table.getKeyValue(((PExp) left).toString().replace(" ", ""));
                if(val == null) {
                    System.err.println("[Linha " + lineNumbers.getLine(node)+"] Erro Semântico: Soma não é possível (o lado esquerdo da expressão é null).");
                    return;
                }
            }
            
        } else if (left instanceof PExp) {
            tipo_l = type(((PExp) left).toString().replace(" ", ""));
            
        } else {
            if(!table.contains(left.toString().replace(" ", ""))) {
                System.err.println("[Linha " + lineNumbers.getLine(node)+"] Erro Semântico: Soma não é possível (o lado esquerdo da expressão não é calculável).");
                return;
            }
            else 
                tipo_l = table.getKeyTipo(left.toString().replace(" ", ""));
        }        
        
        if (right instanceof PExp) {
            if(!table.contains(((PExp) right).toString().replace(" ", "")))      
                return;
            else {
                tipo_r = table.getKeyTipo(((PExp) right).toString().replace(" ", ""));
                String val = table.getKeyValue(((PExp) right).toString().replace(" ", ""));
                if(val == null) {
                    System.err.println("[Linha " + lineNumbers.getLine(node)+"] Erro Semântico: Soma não é possível (o lado direito da expressão é null).");
                    return;
                }
            }
            
        } else if (right instanceof PExp) {
            tipo_r = type(((PExp) right).toString().replace(" ", ""));
            
        } else {
            if(!table.contains(right.toString().replace(" ", ""))) {
                System.err.println("[Linha " + lineNumbers.getLine(node)+"] Erro Semântico: Soma não é possível (o lado direito da expressão não é calculável).");
                return;
            }
            else
                tipo_r = table.getKeyTipo(right.toString().replace(" ", ""));
        }        
    
        if (tipo_l.equals(tipo_r) || (tipo_l.equals("int") && tipo_r.equals("real")) || (tipo_l.equals("real") && tipo_r.equals("int"))) {
            ocorreuErro = false;
        } else {
            System.err.println("[Linha " + lineNumbers.getLine(node)+"] Erro Semântico: Tipos incompatíveis para somar.");
            ocorreuErro = true;
        }        
        
        if(!ocorreuErro) {
            if(tipo_l.equals("int") && tipo_l.equals(tipo_r)) {
                int l, r;
                
                if(!table.contains(left.toString().replace(" ", ""))) 
                    l = Integer.parseInt(left.toString().replace(" ", ""));
                else 
                    l = Integer.parseInt(table.getKeyValue(left.toString().replace(" ", "")));
                
                if(!table.contains(right.toString().replace(" ", "")))
                    r = Integer.parseInt(right.toString().replace(" ", ""));
                else
                    r = Integer.parseInt(table.getKeyValue(right.toString().replace(" ", "")));
                
                Integer soma = l + r;
                table.insert(node.toString().replace(" ", ""), soma.toString(), tipo_l, "0", "-1");
                    
            } else if (tipo_l.equals("real") && tipo_l.equals(tipo_r)) {
                double l, r;
                if(!table.contains(left.toString().replace(" ", ""))) 
                    l = Double.parseDouble(left.toString().replace(" ", ""));
                else
                    l = Double.parseDouble(table.getKeyValue(left.toString().replace(" ", "")));
                
                if(!table.contains(right.toString().replace(" ", ""))) 
                    r = Double.parseDouble(right.toString().replace(" ", ""));
                else
                    r = Double.parseDouble(table.getKeyValue(right.toString().replace(" ", "")));
                    
                Double soma = l + r;
                table.insert(node.toString().replace(" ", ""), soma.toString(), tipo_l, "0", "-1");               
                
            } else if(tipo_l.equals("bool")) {
                System.err.println("[Linha " + lineNumbers.getLine(node)+"] Erro Semântico: Booleanos não podem ser somados.");      
                
            } else if (tipo_l.equals("int")){
                int l;
                Double r;
                Integer aux2;
                
                if(!table.contains(left.toString().replace(" ", ""))) 
                    l = Integer.parseInt(left.toString().replace(" ", ""));
                else
                    l = Integer.parseInt(table.getKeyValue(left.toString().replace(" ", "")));
                
                if(!table.contains(right.toString().replace(" ", ""))) {
                    String[] aux = right.toString().replace(" ", "").split(",");
                    aux2 =  Integer.parseInt(aux[0]);
                    String a = aux2.toString() + "." + aux[1];
                    r = Double.parseDouble(a);
                }else {
                    String str = table.getKeyValue(right.toString().replace(" ", ""));
                    String[] aux = str.split(",");
                    aux2 =  Integer.parseInt(aux[0]);
                    String a = aux2+"."+aux[1];
                    r = Double.parseDouble(a);
                }
                    
                Double soma = l + r;
                String s = soma.toString().replace(".", ",");
                table.insert(node.toString().replace(" ", ""), s, "real", "0", "-1");                
                
            } else if (tipo_r.equals("int")) {
                Double l;
                int r;
                Integer aux2;
                
                if(!table.contains(left.toString().replace(" ", ""))) {
                    String[] aux = left.toString().replace(" ", "").split(",");
                    aux2 =  Integer.parseInt(aux[0]);
                    String a = aux2.toString() + "." + aux[1];
                    l = Double.parseDouble(a); 
                }else {
                    String str = table.getKeyValue(left.toString().replace(" ", ""));
                    String[] aux = str.split(",");
                    aux2 =  Integer.parseInt(aux[0]);
                    String a = aux2 + "." + aux[1];
                    l = Double.parseDouble(a);
                }
                
                if(!table.contains(right.toString().replace(" ", ""))) 
                    r = Integer.parseInt(right.toString().replace(" ", ""));
                else
                    r = Integer.parseInt(table.getKeyValue(right.toString().replace(" ", "")));
                    
                Double soma = l + r;
                String s = soma.toString().replace(".", ",");
                table.insert(node.toString().replace(" ", ""), s, "real", "0", "-1");
            }                       
        }       
        //imprimeTable();
    }    
  
    @Override
    public void outAMenosExp(AMenosExp node) {
        PExp left = node.getEsq();
        PExp right = node.getDir();
        String tipo_l = null;
        String tipo_r = null;
        boolean ocorreuErro = false;
        
        if(left instanceof PExp) {
            if(!table.contains(((PExp) left).toString().replace(" ", ""))) 
                return;
            else {
                tipo_l = table.getKeyTipo(((PExp) left).toString().replace(" ", ""));
                String val = table.getKeyValue(((PExp) left).toString().replace(" ", ""));
                if(val == null) {
                    System.err.println("[Linha " + lineNumbers.getLine(node)+"] Erro Semântico: Subtração não é possível (o lado esquerdo da expressão é null).");
                    return;
                }
            }
            
        } else if (left instanceof PExp) {
            tipo_l = type(((PExp) left).toString().replace(" ", ""));
            
        } else {
            if(!table.contains(left.toString().replace(" ", ""))) {
                System.err.println("[Linha " + lineNumbers.getLine(node)+"] Erro Semântico: Subtração não é possível (o lado esquerdo da expressão não é calculável).");
                return;
            }
            else 
                tipo_l = table.getKeyTipo(left.toString().replace(" ", ""));
        }        
        
        if (right instanceof PExp) {
            if(!table.contains(((PExp) right).toString().replace(" ", ""))) 
                return;
            
            else {
                tipo_r = table.getKeyTipo(((PExp) right).toString().replace(" ", ""));
                String val = table.getKeyValue(((PExp) right).toString().replace(" ", ""));
                if(val == null) {
                    System.err.println("[Linha " + lineNumbers.getLine(node)+"] Erro Semântico: Subtração não é possível (o lado direito da expressão é null).");
                    return;
                }
            }
        } else if (right instanceof PExp) {
            tipo_r = type(((PExp) right).toString().replace(" ", ""));
            
        } else {
            if(!table.contains(right.toString().replace(" ", ""))) {
                System.err.println("[Linha " + lineNumbers.getLine(node)+"] Erro Semântico: Subtração não é possível (o lado direito da expressão não é calculável).");
                return;
            }
            else 
                tipo_r = table.getKeyTipo(right.toString().replace(" ", ""));
        }
            
        if (tipo_l.equals(tipo_r) || (tipo_l.equals("int") && tipo_r.equals("real")) || (tipo_l.equals("real") && tipo_r.equals("int")) ) {
            ocorreuErro = false;
        } else {
            System.err.println("[Linha " + lineNumbers.getLine(node)+"] Erro Semântico: Tipos incompatíveis para realizar subtração.");
            ocorreuErro = true;
        }

        if(!ocorreuErro) {
            if(tipo_l.equals("int") && tipo_l.equals(tipo_r)) {
                int l, r;
                
                if(!table.contains(left.toString().replace(" ", ""))) 
                    l = Integer.parseInt(left.toString().replace(" ", ""));
                else 
                    l = Integer.parseInt(table.getKeyValue(left.toString().replace(" ", "")));
                
                if(!table.contains(right.toString().replace(" ", "")))
                    r = Integer.parseInt(right.toString().replace(" ", ""));
                else
                    r = Integer.parseInt(table.getKeyValue(right.toString().replace(" ", "")));
                
                Integer subtracao = l - r;
                table.insert(node.toString().replace(" ", ""), subtracao.toString(), tipo_l, "0", "-1");
                    
            } else if (tipo_l.equals("real") && tipo_l.equals(tipo_r)) {
                double l, r;
                if(!table.contains(left.toString().replace(" ", ""))) 
                    l = Double.parseDouble(left.toString().replace(" ", ""));
                else
                    l = Double.parseDouble(table.getKeyValue(left.toString().replace(" ", "")));
                
                if(!table.contains(right.toString().replace(" ", ""))) 
                    r = Double.parseDouble(right.toString().replace(" ", ""));
                else
                    r = Double.parseDouble(table.getKeyValue(right.toString().replace(" ", "")));
                    
                Double subtracao = l - r;
                table.insert(node.toString().replace(" ", ""), subtracao.toString(), tipo_l, "0", "-1");
               
                
            } else if(tipo_l.equals("bool")) {
                System.err.println("[Linha " + lineNumbers.getLine(node)+"] Erro Semântico: Booleanos não podem ser subtraídos.");
              
            } else if (tipo_l.equals("int")){
                int l;
                Double r;
                Integer aux2;
                
                if(!table.contains(left.toString().replace(" ", ""))) 
                    l = Integer.parseInt(left.toString().replace(" ", ""));
                else
                    l = Integer.parseInt(table.getKeyValue(left.toString().replace(" ", "")));
                
                if(!table.contains(right.toString().replace(" ", ""))) {
                    String[] aux = right.toString().replace(" ", "").split(",");
                    aux2 =  Integer.parseInt(aux[0]);
                    String a = aux2.toString() + "." + aux[1];
                    r = Double.parseDouble(a);
                }
                else {
                    String str = table.getKeyValue(right.toString().replace(" ", ""));
                    String[] aux = str.split(",");
                    aux2 =  Integer.parseInt(aux[0]);
                    String a = aux2+"."+aux[1];
                    r = Double.parseDouble(a);
                }

                Double subtracao = l - r;
                String s = subtracao.toString().replace(".", ",");
                table.insert(node.toString().replace(" ", ""), s, "real", "0", "-1");
                
                
            } else if (tipo_r.equals("int")) {
                Double l;
                int r;
                Integer aux2;
                
                if(!table.contains(left.toString().replace(" ", ""))) {
                    String[] aux = left.toString().replace(" ", "").split(",");
                    aux2 =  Integer.parseInt(aux[0]);
                    String a = aux2.toString() + "." + aux[1];
                    l = Double.parseDouble(a);
                }
                else {
                    String str = table.getKeyValue(left.toString().replace(" ", ""));
                    String[] aux = str.split(",");
                    aux2 =  Integer.parseInt(aux[0]);
                    String a = aux2 + "." + aux[1];
                    l = Double.parseDouble(a);
                }
                
                if(!table.contains(right.toString().replace(" ", ""))) 
                    r = Integer.parseInt(right.toString().replace(" ", ""));
                else
                    r = Integer.parseInt(table.getKeyValue(right.toString().replace(" ", "")));
                    
                Double subtracao = l - r;
                String s = subtracao.toString().replace(".", ",");
                table.insert(node.toString().replace(" ", ""), s, "real", "0", "-1");
            }                      
        }        
        //imprimeTable();
    }
    
    @Override
    public void outAMultExp(AMultExp node) {
        PExp left = node.getEsq();
        PExp right = node.getDir();
        String tipo_l = null;
        String tipo_r = null;
        boolean ocorreuErro = false;
        
        if(left instanceof PExp) {
            if(!table.contains(((PExp) left).toString().replace(" ", ""))) 
                return;
            else {
                tipo_l = table.getKeyTipo(((PExp) left).toString().replace(" ", ""));
                String val = table.getKeyValue(((PExp) left).toString().replace(" ", ""));
                if(val == null) {
                    System.err.println("[Linha " + lineNumbers.getLine(node)+"] Erro Semântico: Multiplicação não é possível (o lado esquerdo da expressão é null).");
                    return;
                }
            }
            
        } else if (left instanceof PExp) {
            tipo_l = type(((PExp) left).toString().replace(" ", ""));
            
        } else {
            if(!table.contains(left.toString().replace(" ", "")) || table.getKeyPos(tipo_l).equals("bool")) {
                System.err.println("[Linha " + lineNumbers.getLine(node)+"] Erro Semântico: Multiplicação não é possível (o lado esquerdo da expressão não é calculável).");
                return;
            }
            else
                tipo_l = table.getKeyTipo(left.toString().replace(" ", ""));
        }
        
        if (right instanceof PExp) {
            if(!table.contains(((PExp) right).toString().replace(" ", ""))) 
                return;
            else {
                tipo_r = table.getKeyTipo(((PExp) right).toString().replace(" ", ""));
                String val = table.getKeyValue(((PExp) right).toString().replace(" ", ""));
                if(val == null) {
                    System.err.println("[Linha " + lineNumbers.getLine(node)+"] Erro Semântico: Multiplicação não é possível (o lado direito da expressão é null).");
                    return;
                }
            }
        } else if (right instanceof PExp) {
            tipo_r = type(((PExp) right).toString().replace(" ", ""));
            
        } else {
            if(!table.contains(right.toString().replace(" ", "")) || table.getKeyPos(tipo_r).equals("bool")) {
                System.err.println("[Linha " + lineNumbers.getLine(node)+"] Erro Semântico: Multiplicação não é possível (o lado direito da expressão não é calculável).");
                return;
            }
            else 
                tipo_r = table.getKeyTipo(right.toString().replace(" ", ""));
        }

        if (tipo_l.equals(tipo_r) || (tipo_l.equals("int") && tipo_r.equals("real")) || (tipo_l.equals("real") && tipo_r.equals("int")) ) {
            ocorreuErro = false;
        } else {
            System.err.println("[Linha " + lineNumbers.getLine(node)+"] Erro Semântico: Tipos incompatíveis para realizar multiplicação.");
            ocorreuErro = true;
        }
        
        if(!ocorreuErro) {
            if(tipo_l.equals("int") && tipo_l.equals(tipo_r)) {
                int l, r;
                
                if(!table.contains(left.toString().replace(" ", ""))) 
                    l = Integer.parseInt(left.toString().replace(" ", ""));
                else 
                    l = Integer.parseInt(table.getKeyValue(left.toString().replace(" ", "")));

                
                if(!table.contains(right.toString().replace(" ", "")))
                    r = Integer.parseInt(right.toString().replace(" ", ""));
                else
                    r = Integer.parseInt(table.getKeyValue(right.toString().replace(" ", "")));
                
                Integer mult = l * r;
                table.insert(node.toString().replace(" ", ""), mult.toString(), tipo_l, "0", "-1");
                    
            } else if (tipo_l.equals("real") && tipo_l.equals(tipo_r)) {
                double l, r;
                if(!table.contains(left.toString().replace(" ", ""))) 
                    l = Double.parseDouble(left.toString().replace(" ", ""));
                else
                    l = Double.parseDouble(table.getKeyValue(left.toString().replace(" ", "")));
                
                if(!table.contains(right.toString().replace(" ", ""))) 
                    r = Double.parseDouble(right.toString().replace(" ", ""));
                else
                    r = Double.parseDouble(table.getKeyValue(right.toString().replace(" ", "")));
                    
                Double mult = l * r;
                table.insert(node.toString().replace(" ", ""), mult.toString(), tipo_l, "0", "-1");
               
                
            } else if(tipo_l.equals("bool")) {
                System.err.println("[Linha " + lineNumbers.getLine(node)+"] Erro Semântico: Booleanos não podem ser multiplicados.");
                
            } else if (tipo_l.equals("int")){
                int l;
                Double r;
                Integer aux2;
                
                if(!table.contains(left.toString().replace(" ", ""))) 
                    l = Integer.parseInt(left.toString().replace(" ", ""));
                else
                    l = Integer.parseInt(table.getKeyValue(left.toString().replace(" ", "")));
                
                if(!table.contains(right.toString().replace(" ", ""))) {
                    String[] aux = right.toString().replace(" ", "").split(",");
                    aux2 =  Integer.parseInt(aux[0]);
                    String a = aux2.toString() + "." + aux[1];
                    r = Double.parseDouble(a);
                }
                else {
                    String str = table.getKeyValue(right.toString().replace(" ", ""));
                    String[] aux = str.split(",");
                    aux2 =  Integer.parseInt(aux[0]);
                    String a = aux2+"."+aux[1];
                    r = Double.parseDouble(a);
                }
                    
                Double mult = l * r;
                String s = mult.toString().replace(".", ",");
                table.insert(node.toString().replace(" ", ""), s, "real", "0", "-1");                
                
            } else if (tipo_r.equals("int")) {
                Double l;
                int r;
                Integer aux2;
                
                if(!table.contains(left.toString().replace(" ", ""))) {
                    String[] aux = left.toString().replace(" ", "").split(",");
                    aux2 =  Integer.parseInt(aux[0]);
                    String a = aux2.toString() + "." + aux[1];
                    l = Double.parseDouble(a);
                    
                }
                else {
                    String str = table.getKeyValue(left.toString().replace(" ", ""));
                    String[] aux = str.split(",");
                    aux2 =  Integer.parseInt(aux[0]);
                    String a = aux2 + "." + aux[1];
                    l = Double.parseDouble(a);
                }
                
                if(!table.contains(right.toString().replace(" ", ""))) 
                    r = Integer.parseInt(right.toString().replace(" ", ""));
                else
                    r = Integer.parseInt(table.getKeyValue(right.toString().replace(" ", "")));
                
                Double mult = l * r;
                String s = mult.toString().replace(".", ",");
                table.insert(node.toString().replace(" ", ""), s, "real", "0", "-1");
            }
        }
        //imprimeTable();
    }
    
    @Override
    public void outADivExp(ADivExp node) {
        PExp left = node.getEsq();
        PExp right = node.getDir();
        String tipo_l = null;
        String tipo_r = null;
        boolean ocorreuErro = false;
        
        if(left instanceof PExp) {
            if(!table.contains(((PExp) left).toString().replace(" ", ""))) 
                return;
            else {
                tipo_l = table.getKeyTipo(((PExp) left).toString().replace(" ", ""));
                String val = table.getKeyValue(((PExp) left).toString().replace(" ", ""));
                if(val == null) {
                    System.err.println("[Linha " + lineNumbers.getLine(node)+"] Erro Semântico: Divisão não é possível (o lado esquerdo da expressão é null).");
                    return;
                }
            }
            
        } else if (left instanceof PExp) {
            tipo_l = type(((PExp) left).toString().replace(" ", ""));
            
        } else {
            if(!table.contains(left.toString().replace(" ", "")) || table.getKeyPos(tipo_l).equals("bool")) {
                System.err.println("[Linha " + lineNumbers.getLine(node)+"] Erro Semântico: Divisão não é possível (o lado esquerdo da expressão não é calculável).");
                return;
            }
            else
                tipo_l = table.getKeyTipo(left.toString().replace(" ", ""));
        }        
        
        if (right instanceof PExp) {
            if(!table.contains(((PExp) right).toString().replace(" ", "")))
                return;
            else {
                tipo_r = table.getKeyTipo(((PExp) right).toString().replace(" ", ""));
                String val = table.getKeyValue(((PExp) right).toString().replace(" ", ""));
                if(val == null) {
                    System.err.println("[Linha " + lineNumbers.getLine(node)+"] Erro Semântico: Divisão não é possível (o lado direito da expressão é null).");
                    return;
                }
            }
        } else if (right instanceof PExp) {
            tipo_r = type(((PExp) right).toString().replace(" ", ""));
            
        } else {
            if(!table.contains(right.toString().replace(" ", "")) || table.getKeyPos(tipo_r).equals("bool")) {
                System.err.println("[Linha " + lineNumbers.getLine(node)+"] Erro Semântico: Divisão não é possível (o lado direito da expressão não é calculável).");
                return;
            }
            else 
                tipo_r = table.getKeyTipo(right.toString().replace(" ", ""));
        }
        
    
        if (tipo_l.equals(tipo_r) || (tipo_l.equals("int") && tipo_r.equals("real")) || (tipo_l.equals("real") && tipo_r.equals("int")) ) {
            ocorreuErro = false;
        } else {
            System.err.println("[Linha " + lineNumbers.getLine(node)+"] Erro Semântico: Tipos incompatíveis para realizar divisão.");
            ocorreuErro = true;
        }
           
        if(!ocorreuErro) {
            if(tipo_l.equals("int") && tipo_l.equals(tipo_r)) {
                int l, r;
                
                if(!table.contains(left.toString().replace(" ", ""))) 
                    l = Integer.parseInt(left.toString().replace(" ", ""));
                else 
                    l = Integer.parseInt(table.getKeyValue(left.toString().replace(" ", "")));

                
                if(!table.contains(right.toString().replace(" ", "")))
                    r = Integer.parseInt(right.toString().replace(" ", ""));
                else
                    r = Integer.parseInt(table.getKeyValue(right.toString().replace(" ", "")));
                
                Integer div = l / r;
                table.insert(node.toString().replace(" ", ""), div.toString(), tipo_l, "0", "-1");
                    
            } else if (tipo_l.equals("real") && tipo_l.equals(tipo_r)) {
                double l, r;
                if(!table.contains(left.toString().replace(" ", ""))) 
                    l = Double.parseDouble(left.toString().replace(" ", ""));
                else
                    l = Double.parseDouble(table.getKeyValue(left.toString().replace(" ", "")));
                
                if(!table.contains(right.toString().replace(" ", ""))) 
                    r = Double.parseDouble(right.toString().replace(" ", ""));
                else
                    r = Double.parseDouble(table.getKeyValue(right.toString().replace(" ", "")));
                    
                Double div = l / r;
                table.insert(node.toString().replace(" ", ""), div.toString(), tipo_l, "0", "-1");
               
                
            } else if(tipo_l.equals("bool")) {
                System.err.println("[Linha " + lineNumbers.getLine(node)+"] Erro Semântico: Booleanos não podem ser divididos.");
                
            } else if (tipo_l.equals("int")){
                int l;
                Double r;
                Integer aux2;
                
                if(!table.contains(left.toString().replace(" ", ""))) 
                    l = Integer.parseInt(left.toString().replace(" ", ""));
                else
                    l = Integer.parseInt(table.getKeyValue(left.toString().replace(" ", "")));
                
                if(!table.contains(right.toString().replace(" ", ""))) {
                    String[] aux = right.toString().replace(" ", "").split(",");
                    aux2 =  Integer.parseInt(aux[0]);
                    String a = aux2.toString() + "." + aux[1];
                    r = Double.parseDouble(a);
                }
                else {
                    String str = table.getKeyValue(right.toString().replace(" ", ""));
                    String[] aux = str.split(",");
                    aux2 =  Integer.parseInt(aux[0]);
                    String a = aux2+"."+aux[1];
                    r = Double.parseDouble(a);
                }
              
                Double div = l / r;
                String s = div.toString().replace(".", ",");
                table.insert(node.toString().replace(" ", ""), s, "real", "0", "-1");
                
                
            } else if (tipo_r.equals("int")) {
                Double l;
                int r;
                Integer aux2;
                
                if(!table.contains(left.toString().replace(" ", ""))) {
                    String[] aux = left.toString().replace(" ", "").split(",");
                    aux2 =  Integer.parseInt(aux[0]);
                    String a = aux2.toString() + "." + aux[1];
                    l = Double.parseDouble(a);
                    
                }
                else {
                    String str = table.getKeyValue(left.toString().replace(" ", ""));
                    String[] aux = str.split(",");
                    aux2 =  Integer.parseInt(aux[0]);
                    String a = aux2 + "." + aux[1];
                    l = Double.parseDouble(a);
                }
                
                if(!table.contains(right.toString().replace(" ", ""))) 
                    r = Integer.parseInt(right.toString().replace(" ", ""));
                else
                    r = Integer.parseInt(table.getKeyValue(right.toString().replace(" ", "")));

                Double div = l / r;
                String s = div.toString().replace(".", ",");
                table.insert(node.toString().replace(" ", ""), s, "real", "0", "-1");
            }
        }
        //imprimeTable();
    }
    
    @Override
    public void outANumNegativoExp(ANumNegativoExp  node) {
        PExp exp = node.getExp();
        String tipo = null;
        boolean ocorreuErro = false;
       
        if(exp instanceof PExp) {
            if(!table.contains(((PExp) exp).toString().replace(" ", "")))                 
                return;
            else {
                tipo = table.getKeyTipo(((PExp) exp).toString().replace(" ", ""));
                String val = table.getKeyValue(((PExp) exp).toString().replace(" ", ""));
                if(val == null) {
                    System.err.println("[Linha " + lineNumbers.getLine(node)+"] Erro Semântico: 'Negativação' não é possível (o valor da expressão é null).");
                    return;
                }
            }
            
        } else if (exp instanceof PExp) 
            tipo = type(((PExp) exp).toString().replace(" ", ""));
        else {
            if(!table.contains(exp.toString().replace(" ", ""))) 
                return;
             else 
                tipo = table.getKeyTipo(exp.toString().replace(" ", ""));
        }
        
        if(!ocorreuErro) {
            if(tipo.equals("int")) {
                Integer e = 0;
                if(!table.contains(exp.toString().replace(" ", ""))) {
                    e = -Integer.parseInt(exp.toString().replace(" ", ""));
                    
                } else if(table.contains(exp.toString().replace(" ", ""))) { 
                    e = -Integer.parseInt(table.getKeyValue(exp.toString().replace(" ", "")));
                }
 
                String key = node.toString().replace(" ", "");
                table.insert(key, e.toString(), tipo, "0", "-1");
                    
            } else if (tipo.equals("real")) {
                Double e = null;
                if(!table.contains("-"+exp.toString().replace(" ", ""))) {
                    if(!table.contains(exp.toString().replace(" ", ""))) 
                        e = -Double.parseDouble(exp.toString().replace(" ", ""));
                     
                }else if(table.contains(exp.toString().replace(" ", ""))) 
                    e = -Double.parseDouble(table.getKeyValue(exp.toString().replace(" ", "")));
                    
                String key = node.toString().replace(" ", "");
                table.insert(key, e.toString(), tipo, "0", "-1");
            }
        }
        //imprimeTable();
    }
    
    @Override
    public void outACompExp(ACompExp node) {
        PExp left = node.getEsq();
        PExp right = node.getDir();
        String tipo_l = null;
        String tipo_r = null;
        boolean ocorreuErro = false;
        
        if(left instanceof PExp) {
            if(!table.contains(((PExp) left).toString().replace(" ", ""))) 
                return;
            else
                tipo_l = table.getKeyTipo(((PExp) left).toString().replace(" ", ""));
            
        } else if (left instanceof PExp) {
            tipo_l = type(((PExp) left).toString().replace(" ", ""));
            
        } else {
            if(!table.contains(left.toString().replace(" ", ""))) 
                return;
            else
                tipo_l = table.getKeyTipo(left.toString().replace(" ", "")); 
        }       
        
        if (right instanceof PExp) {
            if(!table.contains(((PExp) right).toString().replace(" ", ""))) 
                return;
            else
                tipo_r = table.getKeyTipo(((PExp) right).toString().replace(" ", ""));
          
        } else if (right instanceof PExp) {
            tipo_r = type(((PExp) right).toString().replace(" ", ""));
            
        } else {
            if(!table.contains(right.toString().replace(" ", "")))    
                return;
            else 
                tipo_r = table.getKeyTipo(right.toString().replace(" ", ""));
        }

        if (tipo_l.equals(tipo_r) || (tipo_l.equals("int") && tipo_r.equals("real")) || (tipo_l.equals("real") && tipo_r.equals("int")) ) {
            ocorreuErro = false;
        } else {
            System.err.println("[Linha " + lineNumbers.getLine(node)+"] Erro Semântico: Tipos incompatíveis para comparação.");
            ocorreuErro = true;
        }
        //imprimeTable();
    }    
  
    @Override
    public void outAMenorExp(AMenorExp node) {
        PExp left = node.getEsq();
        PExp right = node.getDir();
        String tipo_l = null;
        String tipo_r = null;
        boolean ocorreuErro = false;
      
        if(left instanceof PExp) {
            if(!table.contains(((PExp) left).toString().replace(" ", ""))) 
                return;
            else
                tipo_l = table.getKeyTipo(((PExp) left).toString().replace(" ", ""));
     
        } else if (left instanceof PExp) {
            tipo_l = type(((PExp) left).toString().replace(" ", ""));
            
        } else {
            if(!table.contains(left.toString().replace(" ", ""))) 
                return;
            else 
                tipo_l = table.getKeyTipo(left.toString().replace(" ", ""));
        }        
        
        if (right instanceof PExp) {
            if(!table.contains(((PExp) right).toString().replace(" ", ""))) 
                return;
            else 
                tipo_r = table.getKeyTipo(((PExp) right).toString().replace(" ", ""));
                
        } else if (right instanceof PExp) {
            tipo_r = type(((PExp) right).toString().replace(" ", ""));
            
        } else {
            if(!table.contains(right.toString().replace(" ", ""))) 
                return;
            else 
                tipo_r = table.getKeyTipo(right.toString().replace(" ", ""));
        }
    
        if (tipo_l.equals(tipo_r) || (tipo_l.equals("int") && tipo_r.equals("real")) || (tipo_l.equals("real") && tipo_r.equals("int")) ) {
            ocorreuErro = false;
        } else {
            System.err.println("[Linha " + lineNumbers.getLine(node)+"] Erro Semântico: Tipos incompatíveis para comparação.");
            ocorreuErro = true;
        }
    }
 
    /*Imprime os valores finais das variáveis presentes na tabela*/
    public void valoresFinaisVariaveis () {
        System.out.println("\nVALORES FINAIS DAS VARIÁVEIS: ");
        for (String id : table.getIds()) {
            if (table.getKeyPos(id).equals("v")) {
                System.out.println(id + " = " + table.getKeyValue(id));
            }
        }
    }
    
}