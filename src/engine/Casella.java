package engine;

import java.util.ArrayList;

public class Casella {

	public Boolean[] valori=new Boolean[9];

	public Casella(int n) {
		//Riceve in ingresso il numero che la casella contiene. Se zero imposta tutti i possibili valori
		//Se n=0 la casella è vuota, posso quindi inserire tutti i valori possibili
		if(n==0){
		for(int i=0;i<9;i++)
			valori[i]=true;
		}
		else{
			for(int i=0;i<9;i++)
				valori[i]=false;
		
			valori[n-1]=true;
		}
	}
	public Casella(Casella n)
	{
		for(int i=0;i<9;i++)
			valori[i]=n.valori[i];
		
	
	}
	public Boolean[] getValori() {
		return valori;
	}

	public ArrayList<Integer> getPossibiliValori(){
		ArrayList<Integer> result=new ArrayList<Integer>();
		for(int i=0;i<9;i++){
		if(valori[i]==true)
			result.add(i+1);
		}
		return result;
	}
	public void setValori(Boolean[] valori) {
		this.valori = valori;
	}
	
	public Boolean rimuoviNumero(int n) {
		if(valori[n-1]==false)
			return false; //il valore era già rimosso.
		valori[n-1]=false;
		return true;
		
	}
	
	public Boolean impostaNumero(int n) {
		if(n>9 || n<=0)
			return false;
		Boolean result=true;
		for(int i=0;i<9;i++)
		{
				valori[i]=false;
			
		}
		valori[n-1]=true;
		return result;
		
	}
	//Ritorna true se il numero possibile è solo uno, false altrimenti
	public Boolean isCerta() 
	{
		int count=0;
		for(int i=0;i<9;i++)
		{
			if(valori[i]==true)
				count++;
		}
		if(count==1)
			return true;
		else
			return false;
	
	}
	
	public int possibiliValoriCount()
	{
		int count=0;
		for(int i=0;i<9;i++)
		{
			if(valori[i]==true)
				count++;
		}
		
		return count;
	
	
	}
	
	int ottieniValoreCerto(){
		
		if(this.isCerta())
		{
			for(int j=0;j<9;j++){
				if(this.getValori()[j]==true)
					return j+1;
			}	
		}
		return 0;
	}

}