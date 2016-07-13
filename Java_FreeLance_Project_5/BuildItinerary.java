import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Set;

import net.datastructures.Edge;
import net.datastructures.Vertex;

public class BuildItinerary {
	
	AdjacencyListGraph<TimeAtAirport,Flight> graph;
	
	public BuildItinerary(ArrayList<Flight> flights) {
	
		buildGraph(flights);

	}
	
	private void buildGraph(ArrayList<Flight> flights) {
		graph= new AdjacencyListGraph<TimeAtAirport,Flight>(true); //empty directed graph
		
		// Insert vertices and edges for all flight legs
		Hashtable<TimeAtAirport,Vertex> vertices = new Hashtable<TimeAtAirport, Vertex>();
		
		for (Flight flightLeg: flights) {
			TimeAtAirport depart= new TimeAtAirport(flightLeg.getAirportDepart(), flightLeg.getTimeDepart());
			TimeAtAirport destin= new TimeAtAirport(flightLeg.getAirportDestin(), flightLeg.getTimeDestin());
			
			Vertex<TimeAtAirport> sourceV = vertices.get(depart);
			if ( sourceV == null ) {
				 // Source vertex not in graph -- insert
				 sourceV = graph.insertVertex(depart); 
				 vertices.put( depart, sourceV );
		    } 
		    Vertex<TimeAtAirport> destV = vertices.get( destin );
		    if ( destV == null ) {
		    	// Destination vertex not in graph -- insert
		    	destV = graph.insertVertex(destin); 
		    	vertices.put( destin, destV );
		        }
		   
		  	graph.insertEdge(sourceV, destV, flightLeg);         
		}
				
		// connect vertices in the same airport in increasing direction of time
		
		ArrayList<TimeAtAirport> allTAA = new ArrayList<TimeAtAirport>();
		for (Vertex<TimeAtAirport> v: graph.vertices()) {
			allTAA.add(v.getElement());
		}
		
		Collections.sort(allTAA); // sort according to Comparable TimeAtAirport (first airport the time)
		
		TimeAtAirport taa1=allTAA.get(0);
	    TimeAtAirport taa2;
	    
	    for (int i=1; i<allTAA.size(); i++) {
	    	taa2=allTAA.get(i);
	    	if (taa1.sameAirport(taa2)) {
	    		Flight dummyFlight = new Flight("Connection",taa1.getAirport(),taa2.getAirport(),
	    				"","",taa1.getTime(), taa2.getTime(), 0);
	    		graph.insertEdge(vertices.get(taa1), vertices.get(taa2), dummyFlight);  
	    	}
	    	taa1=taa2;
		}
		
	}	
	
	private void displayAllPathInfo(DijkstraAlgorithm.ShortestPathsInfo spInfo) {
		int[] listV=spInfo.getPrevVertex();
		int[] listD=spInfo.getDist();
		Edge[] listE=spInfo.getPrevEdge();
		
		System.out.println("\n*** Displaying All Shortest Path Info given by DijkstraAlgorithm.ShortestPathsInfo***");
		for (int i=0; i< spInfo.getNumVertices();i++) {
			System.out.print("Vertex "+i+":"+graph.getVertexAtPosition(i).getElement()+
					" Cost="+listD[i]);
			if (listV[i]!=-1)
			   System.out.print(", PrevVertex:"+graph.getVertexAtPosition(listV[i]).getElement()
					   +",PrevEdge:"+listE[i].getElement());
			else if (listD[i]==0)
				System.out.print(", PrevVertex:(source)");
			else 
				System.out.print(",(Unreachable)");			
			System.out.println();
		}
		System.out.println("*** Enf of Displaying All Shortest Path Information:***\n");
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Flight> cheapestItinerary(String airportDepart,String airportDestin,
			                                                Time minDepTime, Time maxArrTime, boolean verbose) {
		
		System.out.println("\n>>>Customer requested departure from " + airportDepart+ " after "+ minDepTime+
				              " arriving at "+airportDestin+" before "+maxArrTime);
		
		
		DijkstraAlgorithm dijk = new DijkstraAlgorithm();
		Vertex<TimeAtAirport> minVertex, maxVertex;
		
	    Time minTime=new Time(25,00); // value bigger than all times
	    Time maxTime= new Time(-1,-1); // value smaller than all times
	    
	    minVertex=maxVertex=graph.getVertexAtPosition(0);//dummy initialization used to clear compilation errors
	    
	    //This part is to be implemented by students - currently hardcoded values of minVertex and maxVertex
	    //that works with the test 'flights1.txt' but students will have to find minVertex and maxVertex 
	    //by considering 'minDepTime' and 'maxArrTime'
	    
	    //minVertex=graph.getVertexAtPosition(0); minTime=minVertex.getElement().getTime();
	    //maxVertex=graph.getVertexAtPosition(9); maxTime=maxVertex.getElement().getTime();
	  
	    /********************** CODE TO BE ADDED HERE ******************************************/
	    for (Vertex<TimeAtAirport> v: graph.vertices()) {
			//If this time is earlier than others and is the right airport, set minVertex
			if (v.getElement().getAirport().equals(airportDepart) && (v.getElement().getTime().compareTo(minDepTime) >= 0)) {
				if (v.getElement().getTime().compareTo(minVertex.getElement().getTime()) >= 0) {
					minVertex = v;
					break;
				}
			}
		}
		minTime = minVertex.getElement().getTime();
		//System.out.println("MIN VERTEX: " + minVertex.getElement().toString());

		for (Vertex<TimeAtAirport> v: graph.vertices()) {
			//If this time is later than others and is the right airport, set maxVertex
			if (v.getElement().getAirport().equals(airportDestin) && (v.getElement().getTime().compareTo(maxArrTime)) <= 0) {
				if (v.getElement().getTime().compareTo(maxVertex.getElement().getTime()) > 0) {
					maxVertex = v;
				}
			}
		}
		maxTime = maxVertex.getElement().getTime();
		//System.out.println("MAX VERTEX: " + maxVertex.getElement().toString());
		
		// if bounds for arrival and departure do not work
		if ((minTime.compareTo(new Time(25,00))==0)||(maxTime.compareTo(new Time(-1,-1))==0))
			return null;

			
		@SuppressWarnings("unchecked")
		DijkstraAlgorithm.ShortestPathsInfo spInfo = dijk.findShortestPaths(minVertex,graph);

		if (verbose)
		    displayAllPathInfo(spInfo);
				// Itinerary finally print below 
		if (spInfo.isReachable(graph.getPositionOfVertex(maxVertex))) {
		   if (verbose)	{
			   // This is the unclean itinerary that prints wait at an airport as different stops
			   System.out.println("Unclean Itinerary: from "+minVertex.getElement()+" to "+
			   maxVertex.getElement()+"\n"+spInfo.stringPathFromSourceToVertex(maxVertex, graph));
		   }   
		   
		 
		   // System.out.println("To be implemented by the student using <spInfo> (do not use the String above)");
		   System.out.print("Official Itinerary: "+minVertex.getElement()+" to "+
				   maxVertex.getElement()+"\n");
		   //System.out.println(">>>>>>>>> add code here\n");
		   // *** Here students must add a second printout of Itinerary, but without the waiting edges
		   // *** This must use spInfo path returned by the method below; not parsing the previous output!
		   int[] path=spInfo.pathFromSourceTo(graph.getPositionOfVertex(maxVertex));

		   
		   /************* add the missing code here *************************/
			Edge[] prevEdge = spInfo.getPrevEdge();
			String s = "";
			int total = 0;
			for (int i=1; i < path.length; i++) {
				Edge<Flight> f = (Edge<Flight>) prevEdge[path[i]];
				if (f.getElement().getCost() != 0) {
					s = s + prevEdge[path[i]].getElement() + "\n";
					total += f.getElement().getCost();
				}
			}
			s += "Total Cost=" + total;
			System.out.println(s);
		} else {
			System.out.println("No itineraries available; please widen your search times.");
		}
	  return null;	
	}


	public AdjacencyListGraph<TimeAtAirport,Flight>  getGraph() {
		return graph;
	}
	
	@SuppressWarnings("unchecked")
	public void whereMoneyCanGetMe(String airportDepart, Time minDepTime,int dollars, boolean verbose) {
		
		// Similar to the cheapestItinerary in the determination of the source vertex
		// Using a fixeed dummy value now.

		/*
		Vertex<TimeAtAirport> minVertex=graph.getVertexAtPosition(0);
		Time minTime=minVertex.getElement().getTime();
		
		DijkstraAlgorithm dijk = new DijkstraAlgorithm();
		DijkstraAlgorithm.ShortestPathsInfo spInfo = dijk.findShortestPaths(minVertex,graph);
		*/
		Vertex<TimeAtAirport> minVertex=graph.getVertexAtPosition(0);
		DijkstraAlgorithm dijk = new DijkstraAlgorithm();
		String s = "";

		for (Vertex<TimeAtAirport> v: graph.vertices()) {
			//If this time is earlier than others and is the right airport, set minVertex
			if (v.getElement().getAirport().equals(airportDepart) && (v.getElement().getTime().compareTo(minDepTime) >= 0)) {
				if (v.getElement().getTime().compareTo(minVertex.getElement().getTime()) >= 0) {
					minVertex = v;
					break;
				}
			}
		}
		DijkstraAlgorithm.ShortestPathsInfo spInfo = dijk.findShortestPaths(minVertex,graph);
		if (verbose)
			displayAllPathInfo(spInfo);

		for (Vertex<TimeAtAirport> dest : graph.vertices()) {
			if (dest.getElement().getAirport().equals(airportDepart)) {
				continue;
			}
			if (spInfo.isReachable(graph.getPositionOfVertex(dest))) {
				int[] path=spInfo.pathFromSourceTo(graph.getPositionOfVertex(dest));
				Edge[] prevEdge = spInfo.getPrevEdge();

				int total = 0;
				Time currTime = new Time(25,00);

				for (int i=1; i < path.length; i++) {
					Edge<Flight> f = (Edge<Flight>) prevEdge[path[i]];
					if (f.getElement().getCost() != 0) {
						total += f.getElement().getCost();
					}
					currTime = f.getElement().getTimeDestin();
				}
				if (graph.getVertexAtPosition(path[path.length - 2]).getElement().getAirport().equals(graph.getVertexAtPosition(path[path.length - 1]).getElement().getAirport())) {
					continue;
				}
				if (total <= dollars) {
					//System.out.println("(" + dest.getElement().getAirport() + "," + currTime.toString() + ")");
					s += "(" + dest.getElement().getAirport() + "," + currTime.toString() + ")\n";
				}
			}
		}
		
		//if (verbose)
		//    displayAllPathInfo(spInfo);
		
		/************ code for part 3 to be added ******************************/
		
		System.out.println(">>>> Here you will provide info on where to go under $"+dollars);
		System.out.print(s);
	}
	
	
	public class TimeAtAirport implements Comparable {
		String airport;
		Time time;
		public TimeAtAirport(String airport, Time time) {
			this.airport=airport;
			this.time=time;
		}
		Time getTime() { return time; }
		String getAirport() { return airport;}
		
		boolean sameAirport(TimeAtAirport other) {
			return (this.airport.equals(other.airport));
		}
		
		@Override
		public int compareTo(Object o) throws ClassCastException{
			if (!(o instanceof TimeAtAirport))
			      throw new ClassCastException("A TimeAtAirport object expected.");
			TimeAtAirport otherTAA= (TimeAtAirport) o;
			int result=this.airport.compareTo(otherTAA.airport);
			if (result==0)
				result=this.time.compareTo(otherTAA.time);
			return result;
		}	
		
		public String toString() {
			return airport+" "+time;
		}
		
	}

}
