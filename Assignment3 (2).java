/**
 Assignment 3
 Working in a group of 2 ---> Me(ielrifai 250949412) & jlad7 250969841
 **/

import java.util.Queue;
import java.lang.System;
import java.util.*;

public class Assignment3
{
    //interface class that defines the queue class
    public interface LinkedListQueue<T>{
        public void enqueue(T element);
        public T dequeue();
        public T peek();
        public T size();
        public boolean isEmpty();
    }
    //interface that defines the vertex class
    public interface Vertex <V>
    {
        public int getLabel();
    }
    //interface that defines the edge class
    public interface Edges<E>
    {
        public void setFlow(int s);//setter function to change the flow of a particular edge
        public int flowCapacity();
        public int flow();
    }
    //interface that defines the graph ADT class
    public interface Graph <E,V> {
        public int numVertices();
        public Iterable<Vertex<V>> vertices();
        public int numEdges();
        public Iterable<Edges<E>> edges();
        public Edges<E> getEdge(Vertex u,Vertex v);
        public Iterable<Vertex<V>> endVertices(Edges<E> e);
        public Vertex<V> opposite(Vertex v, Edges e);
        public int outDegree(Vertex<V> v);
        public int inDegree(Vertex<V> v);
        public Iterable<Edges> outgoingEdges(Vertex<V> v);
        public Iterable<Edges> incomingEdges(Vertex<V> v);
        public void insertVertex(Vertex<V> v);
        public void insertEdge(Vertex<V> u, Vertex<V> v, E x);
        public void removeVertex(Vertex<V> v);
        public void removeEdge(Edges<E> e);
        public Vertex<V> getVertex(int label);
    }
    LinkedList<Vertex> list =new LinkedList<Vertex>(); //global list that hold will vertices of augmented path
    public int breadthFirstPathSearch(Graph fN, int s, int d )
    {
        Vertex startNode = fN.getVertex(s); //gets start node from int s
        Vertex endNode = fN.getVertex(d);// gets end node from t
        LinkedListQueue<Vertex> queue=null;//As an interface canot be instatiated, we have set it to null with the assumption that this is equivalent to initializing a queue with type vertex
        queue.enqueue(startNode); // enqueue starting Node
        int[] visitedNodes = new int[fN.numVertices()];//array to hold keep track of nodes visited
        int numOfVertices = fN.numVertices();
        for (int j=0;j<numOfVertices;j++)
        {
            visitedNodes[j]=0;//sets array of visited nodes to 0
        }
    Vertex v; //temp vertex to hold the dequeue of queue
    while(!queue.isEmpty())
    {
      v = queue.dequeue();
      if(visitedNodes[v.getLabel()]==0)//checks if node is visited
      {
        visitedNodes[v.getLabel()]=1;//marks node as visited
        Iterable<Edges> edgeList = fN.outgoingEdges(v);  //list of outgoing edges from the vertex (v)
       for(Edges e : edgeList)
       {
        if(e.flowCapacity()-e.flow()>0)
            {
                list.add(v);//adds node to augmented path list
                v = fN.opposite(v,e);//sets opposite to v
                queue.enqueue(v);//enqueues new node
            }
       }
      }
    }

    if(visitedNodes[endNode.getLabel()]==1)//checks of end node is visited
         return 1;//returns 1 if path exists
    else
        return 0;//returns 0 if path doesn't exist
    }


    public void maximizeFlowNetwork(Graph fN, int s, int d)
    {
        if(breadthFirstPathSearch(fN,s,d)==1)//if the breadth function returns true, then that means there is an augmented path
        {
          int Maximumflow=fN.getEdge(list.get(0),list.get(1)).flowCapacity();//int used to find the lowest flow capacity in the edges which is the maximum flow of the network
            for(int i=0;i<list.size();i++)//for-loop that goes through every edge in the augmented path and finds maximum flow increase
            {
                for(int j=i+1;j<list.size()-1;j++)
                {
                    int temp;//temp variable used to help determine max flow  in the network
                    Edges e = fN.getEdge(list.get(i), list.get(j));
                   temp = e.flowCapacity();
                    if (Maximumflow > temp)//since we want to get the max capacity of the network, we actually want to find the lowest capacity edge
                    {
                        Maximumflow = temp;//set if maximum flow is less than temp, to get actual maximum
                    }
                }
            }

            //maximum flow in the path is calculated
            for(int i=0;i<list.size();i++)//second for-loop used to iterate through the edges in the path and add the flow to each edge
            {
                for(int j=i+1;j<list.size()-1;j++)
              {
                 Edges e = fN.getEdge(list.get(i), list.get(j));
                e.setFlow(Maximumflow-e.flow());//increments the flow of the edge up to the maximum flow in the network
              }
            }
        }
    }
}


