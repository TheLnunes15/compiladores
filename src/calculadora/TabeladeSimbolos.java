package calculadora;

import java.util.ArrayList;

public class TabeladeSimbolos {
    private ArrayList<String> ids = new ArrayList<>();
    private ArrayList<String> values = new ArrayList<>();
    private ArrayList<String> tipos = new ArrayList<>();
    private ArrayList<String> tams = new ArrayList<>();
    private ArrayList<String> posicoes = new ArrayList<>();
    

    public void insert(String id, Object value, String tipo, String tam, String pos) {
        if (ids.contains(id)) {
            int index = ids.indexOf(id);
            if(index == -1) index = 0;
            values.remove(index);
            values.add(index, (String) value);
        } else {
            ids.add(id);
            values.add((String) value);
            tipos.add(tipo);
            tams.add(tam);
            posicoes.add(pos);
        }
    }
    
    public boolean contains(String id) {
        return ids.contains(id);
    }

    public ArrayList<String> getPosicoes() {
        return posicoes;
    }

    public void setPosicoes(ArrayList<String> posicoes) {
        this.posicoes = posicoes;
    }    

    public ArrayList<String> getTams() {
        return tams;
    }

    public void setTams(ArrayList<String> tams) {
        this.tams = tams;
    }

    public ArrayList<String> getIds() {
        return ids;
    }

    public void setIds(ArrayList<String> ids) {
        this.ids = ids;
    }

    public ArrayList<String> getValues() {
        return values;
    }

    public void setValues(ArrayList<String> values) {
        this.values = values;
    }

    public ArrayList<String> getTipos() {
        return tipos;
    }

    public void setTipos(ArrayList<String> tipos) {
        this.tipos = tipos;
    }
    
    public String getKeyTipo (String key) {
        int index = ids.indexOf(key);
        if(index == -1) index = 0;
        return tipos.get(index);
    }
	
    public String getKeyValue (String key) {
        int index = ids.indexOf(key);
        if(index == -1) index = 0;
        return values.get(index);
    }
    
    public String getKeyTam (String key) {
        int index = ids.indexOf(key);
        if(index == -1) index = 0;
        return tams.get(index);
    }
    
    public String getKeyPos (String key) {
        int index = ids.indexOf(key);
        if(index == -1) index = 0;
        return posicoes.get(index);
    }
}
