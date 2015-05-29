package engine;

import java.util.ArrayList;

public class BruteForcer {

	private ArrayList<Tabellone> queue;

	public BruteForcer(Tabellone n) {
		queue = new ArrayList<Tabellone>();

		addPossibility(n);

	}

	public Tabellone risolvi()
	{
		while(!queue.isEmpty())
		{
			Tabellone current=queue.get((queue.size()-1));
			do{
				
			}while(current.semplifica());
			
			if(current.isSolved())
				{
					return current;
				}	
					else
					{
						if(current.isOk()){
						addPossibility(current);
						queue.remove(current);
						return risolvi();
						}
							else
								queue.remove(current);
								
					}
		}
		
		return queue.get(0);
	}

	private void addPossibility(Tabellone n) {
		int pos[] = n.getCasellaMinima();
		int incertezza = n.getGriglia()[pos[0]][pos[1]].possibiliValoriCount();
		//System.out.println("Aggiungo possibilitˆ con incertezza "+incertezza);
		for (int i = 0; i < incertezza; i++) {

			queue.add(new Tabellone(n).forzaTabelloneGrado(i));
		}
	}
}
