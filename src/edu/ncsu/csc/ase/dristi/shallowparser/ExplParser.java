package edu.ncsu.csc.ase.dristi.shallowparser;

import java.util.Set;

import edu.ncsu.csc.ase.dristi.datastructure.Relation;
import edu.ncsu.csc.ase.dristi.datastructure.Tuple;
import edu.ncsu.csc.ase.dristi.util.TupleUtil;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.trees.semgraph.SemanticGraph;

public class ExplParser extends AbstractParser
{

	private static AbstractParser instance;
	
	public static synchronized AbstractParser getInstance() {
		if(instance == null)
		{
			instance = new ExplParser();
		}
		return instance;
	}

	@Override
	public Tuple parse(IndexedWord gov, IndexedWord dep, SemanticGraph depGraph, Tuple t, Set<IndexedWord> visited) 
	{
		Tuple t1;
		/*
		 * Check for LeafNode
		 */
		if(depGraph.getChildren(dep).size()>0)
		{
			t1 = parse(dep, depGraph, visited);
			t1 = new Tuple(new Relation(gov), t1);
		}
		else
		{
			t1 = new Tuple(new Relation(dep, gov));
			
		}
		
		t = (Tuple)TupleUtil.merge(t, t1 , gov.word());
		return t;
	}

	@Override
	public Tuple parse(IndexedWord gov, SemanticGraph dependencies, Set<IndexedWord> visited) {
		return null;
	}
	
}
