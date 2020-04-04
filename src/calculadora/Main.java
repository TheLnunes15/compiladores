package calculadora;

import calculadora.parser.*;
import calculadora.lexer.*;
import calculadora.node.*;
import java.io.*;

import calculadora.AnaliseSemantica;
import calculadora.LineNumbers;

public class Main {

	public static void main(String[] args) {
		try
		{
			String arquivo = "codigos/codigo_01.quase";

			Lexer lexer = new Lexer(new PushbackReader(new FileReader(arquivo), 1024));
			Token token;
			while(!((token = lexer.next()) instanceof EOF))
			{
				System.out.print(token.getClass());
				System.out.println(" ( "+token.toString()+")");
			}

			System.out.println("");

  			Parser p = new Parser(new Lexer(new PushbackReader(new FileReader(arquivo), 1024)));

			Start tree = p.parse();
   			tree.apply(new ASTPrinter());
   			tree.apply(new ASTDisplay());
   			
            LineNumbers lineNumbers = new LineNumbers();
            tree.apply(lineNumbers);
            AnaliseSemantica a = new AnaliseSemantica(lineNumbers);
            tree.apply(a);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}
